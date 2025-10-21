package _13_ejercicio;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import utilidades.Utilidades;

public class Ejercicio13 {
	
	private final static String FICHEROTRABAJO_XSL = "ejercicio13.xsl";
	private final static String FICHEROTRABAJO_XML = "ejercicio13.xml";
	
	private final static String FICHEROSALIDA_HTML = "index.html";

	public static void main(String[] args) {
		try {
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer(new StreamSource(Utilidades.getRuta() + Utilidades.getRutaEjer13()+FICHEROTRABAJO_XSL));
			t.transform(new StreamSource(Utilidades.getRuta()+ Utilidades.getRutaEjer13()+ FICHEROTRABAJO_XML), 
					new StreamResult(Utilidades.getRuta()+ Utilidades.getRutaEjer13()+ FICHEROSALIDA_HTML));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
