package _14_ejercicio;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import ejercicio5.Persona;
import utilidades.Utilidades;


public class Ejercicio14 {
	private final static String FICHEROTRABAJO_IN  = "/data/FicheroSerializacion.bin";
	private final static String FICHEROTRABAJO_OUT = "Ejercicio14.xml";
	
	private static XStream xS = new XStream(new DomDriver("UTF-8"));

	public static void main(String[] args) {
		
		xS.addPermission(AnyTypePermission.ANY);
		deserializar_a_XML();
	}

	private static void deserializar_a_XML() {
		ListaPersonas lP = null;  
		try (ObjectInputStream oIS = new ObjectInputStream(
				Ejercicio14.class.getResourceAsStream(FICHEROTRABAJO_IN))) {
			lP = new ListaPersonas();
			while(true) {
				Persona person = (Persona) oIS.readObject();
				lP.anadir(person);
			}
			
		}catch (EOFException e) { 
			xS.alias("familia", ListaPersonas.class);
			xS.addImplicitCollection(ListaPersonas.class, "lista");
			xS.alias("miembro", Persona.class);
			xS.aliasField("primerApellido", Persona.class, "apellido1");
			xS.aliasField("segundoApellido", Persona.class, "apellido2");
			xS.aliasField("name", Persona.class, "nombre");
			xS.useAttributeFor(Persona.class, "nombre");
			try {
				xS.toXML(lP.getLista(), new FileOutputStream(Utilidades.getRuta() + Utilidades.getRutaEjer14()+FICHEROTRABAJO_OUT));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
