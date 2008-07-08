package org.boehn.gef.examples;

import java.io.IOException;

import org.boehn.gef.MapObject;
import org.boehn.gef.Model;
import org.boehn.gef.ModelException;
import org.boehn.gef.coordinates.EarthCoordinate;

public class SimpleExample {

	public static void main(String[] args) throws ModelException, IOException {
		
		// We create a model
		Model model = new Model();
		
		// We create an object for the Department of Informatics at the university of Oslo
		MapObject ifi = new MapObject("Department of Informatics");
		ifi.setDescription("Web: http://www.ifi.uio.no<br/>Phone: +47 22852410");
		ifi.setLocation(new EarthCoordinate(59.943355, 10.717344));
		
		// We add the object to the model
		model.add(ifi);
		
		// We generate the kml file
		model.write("Ifi.kml");
		
		System.out.println("The kml file was generated.");
	}

}
