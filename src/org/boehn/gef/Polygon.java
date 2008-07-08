package org.boehn.gef;

import java.util.List;

import org.boehn.gef.coordinates.CartesianCoordinate;
import org.boehn.gef.coordinates.Coordinate;
import org.boehn.gef.coordinates.EarthCoordinate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Polygon extends Path {

	private Polygon subtractionPolygon;

	public Polygon() {}
	
	public Polygon(List<Coordinate> points) {
		this(points, null, null, null, null);
	}
	
	public Polygon(List<Coordinate> points, Polygon subtractionPolygon, Boolean extrude, Boolean tessellate, AltitudeModes altitudeMode) {
		super(points, extrude, tessellate, altitudeMode);
		this.subtractionPolygon = subtractionPolygon;
	}
	
	public Polygon getSubtractionPolygon() {
		return subtractionPolygon;
	}

	public void setSubtractionPolygon(Polygon subtractionPolygon) {
		this.subtractionPolygon = subtractionPolygon;
	}

	public Element getLinearRing(Document xmlDocument, EarthCoordinate location, Double rotation, CartesianCoordinate localReferenceCoordinate, CartesianCoordinate scale) {
		Element linearRingElement = xmlDocument.createElement("LinearRing");
		linearRingElement.appendChild(getCoordinates(xmlDocument, location, rotation, localReferenceCoordinate, scale));
		return linearRingElement;
	}
	
	@Override
	public void addKml(Element parentElement, Model model, Document xmlDocument, EarthCoordinate location, Double rotation, CartesianCoordinate localReferenceCoordinate, CartesianCoordinate scale) {
		Element polygonElement = xmlDocument.createElement("Polygon");
		
		if (getCoordinates() != null) {
			Element outerBoundaryIsElement = xmlDocument.createElement("outerBoundaryIs");
			outerBoundaryIsElement.appendChild(getLinearRing(xmlDocument, location, rotation, localReferenceCoordinate, scale));
			polygonElement.appendChild(outerBoundaryIsElement);
		}
		
		if (subtractionPolygon != null) {
			Element innerBoundaryIsElement = xmlDocument.createElement("innerBoundaryIs");
			innerBoundaryIsElement.appendChild(subtractionPolygon.getLinearRing(xmlDocument, location, rotation, localReferenceCoordinate, scale));
			polygonElement.appendChild(innerBoundaryIsElement);
		}
		
		if (getExtrude() != null) {
			Element extrudeElement = xmlDocument.createElement("extrude");
			extrudeElement.appendChild(xmlDocument.createTextNode((getExtrude()) ? "1" : "0"));
			polygonElement.appendChild(extrudeElement);
		}
		
		if (getTessellate() != null) {
			Element tessellateElement = xmlDocument.createElement("tessellate");
			tessellateElement.appendChild(xmlDocument.createTextNode((getTessellate()) ? "1" : "0"));
			polygonElement.appendChild(tessellateElement);
		}
		
		if (getAltitudeMode() != null) {
			Element altitudeModeElement = xmlDocument.createElement("altitudeMode");
			altitudeModeElement.appendChild(xmlDocument.createTextNode(getAltitudeMode().toString()));
			polygonElement.appendChild(altitudeModeElement);
		}

		parentElement.appendChild(polygonElement);
	}
	
	public String toString() {
		StringBuffer text = new StringBuffer("Path");
		text.append("altitudeMode: " + getAltitudeMode() + "\n");
		text.append("extrude: " + getExtrude() + "\n");
		text.append("tessellate: " + getTessellate() + "\n");
		text.append("coordinates: " + getCoordinates() + "\n");
		text.append("subtractionPolygon: " + subtractionPolygon + "\n");
		return text.toString();
	}
	
}
