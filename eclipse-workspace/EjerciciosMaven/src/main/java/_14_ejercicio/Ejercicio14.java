package _14_ejercicio;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import ejercicio5.Ejercicio5;
import ejercicio5.Persona;
import utilidades.Utilidades;


public class Ejercicio14 {
	private final static String FICHEROTRABAJO_IN  = "/data/FicheroSerializacion.bin";
	private final static String FICHEROTRABAJO_OUT = "Ejercicio14.xml";
	private final static String FICHEROTRABAJO_IN_2 = "Ejercicio14_in.xml";
	
	private static XStream xS = new XStream(new DomDriver("UTF-8"));

	public static void main(String[] args) {
		
		xS.addPermission(AnyTypePermission.ANY);
//		deserializar_a_XML();
		serializa_desde_XML();
	}

	private static void deserializar_a_XML() {
		ListaPersonas lP = new ListaPersonas();  
		defineEstructuraXML();
		try (ObjectInputStream oIS = new ObjectInputStream(
				Ejercicio14.class.getResourceAsStream(FICHEROTRABAJO_IN))) {
			while(true) {
				Persona person = (Persona) oIS.readObject();
				lP.anadir(person);
			}
		}catch (EOFException e) { 
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
	
	private static void serializa_desde_XML() {
		try {
			ListaPersonas lP = new ListaPersonas();
			defineEstructuraXML();
			lP = (ListaPersonas) xS.fromXML(new FileInputStream(Utilidades.getRuta() + Utilidades.getRutaEjer14() + FICHEROTRABAJO_IN_2));
			Iterator<Persona> it = lP.getLista().iterator();
			Persona p = new Persona();
			ObjectOutputStream oOS = Ejercicio5.inicializar("C:\\Users\\magomezl\\eclipse-workspace\\Ficheros_1\\data\\FicheroSerializacion.bin");
			while(it.hasNext()) {
				p = it.next();
				Ejercicio5.escribirObjeto(oOS, p);
			}
			Ejercicio5.leerObjetos("C:\\Users\\magomezl\\eclipse-workspace\\Ficheros_1\\data\\FicheroSerializacion.bin");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void defineEstructuraXML() {
		xS.addImplicitCollection(ListaPersonas.class, "lista");
		xS.alias("familia", ListaPersonas.class);
		xS.alias("miembro", Persona.class);
		xS.aliasField("primerApellido", Persona.class, "apellido1");
		xS.aliasField("segundoApellido", Persona.class, "apellido2");
		xS.aliasField("name", Persona.class, "nombre");
		xS.useAttributeFor(Persona.class, "nombre");
	}

}
