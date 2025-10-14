package _10_ejercicio;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import ejercicio5.Persona;
import utilidades.Utilidades;



public class Ejercicio10 {
	private final static String DOCTRABAJO_IN = "/data/FicheroSerializacion.bin";
	private final static String DOCTRABAJO_OUT = Utilidades.getRuta() + Utilidades.getRutaEjer10() + "FicheroPersonasSerializado.xml";

	public static void main(String[] args) {
		Document doc = null;
		Transformer t = null;
		try (ObjectInputStream oIS = new ObjectInputStream(
				Ejercicio10.class.getResourceAsStream(DOCTRABAJO_IN))){
			
			DocumentBuilder dB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dB.newDocument();
			
			t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			
			Element elementoRaiz = doc.createElement("personas");
			doc.appendChild(elementoRaiz);
			
			Persona persona = new Persona();
			while(true) {
				persona = (Persona) oIS.readObject();
				Element elementoPersona = doc.createElement("persona");
				CreaElemento("nombre", persona.getNombre().toString(), elementoPersona, doc);
				CreaElemento("apellido1", persona.getApellido1().toString(), elementoPersona, doc);
				CreaElemento("apellido2", persona.getApellido2().toString(), elementoPersona, doc);
//				CreaElemento("nacimiento", persona.getNacimiento().toString(), elementoPersona, doc);
				elementoRaiz.appendChild(elementoPersona);
			}
			
			
		}catch (EOFException e) {
			System.out.println("Fin de fichero");
			try {
				t.transform(new DOMSource(doc), new StreamResult(new File(DOCTRABAJO_OUT)));
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void CreaElemento(String etiqueta, String valor, Element padre, Document doc) {
		Element elem = doc.createElement(etiqueta);
		Text texto = doc.createTextNode(valor);
		padre.appendChild(elem);
		elem.appendChild(texto);
	}

}
