package _11_ejercicio;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import utilidades.Utilidades;

public class Ejercicio11 {
	
	public final static String FICHEROTRABAJO = "ejercicio11.xml";
	public static void main(String[] args) {
		
		try {
			SAXParserFactory sPF = SAXParserFactory.newInstance();
			SAXParser sP = sPF.newSAXParser();
			sP.parse(new InputSource(Utilidades.getRuta()+ Utilidades.getRutaEjer11()+FICHEROTRABAJO), new Manejador());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
