package ut_1.ejercicio1_SAX_Excel;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Manejador extends DefaultHandler {
	private AlumnoDTO alumnoDTO;
	private List<AlumnoDTO> lista = new ArrayList<AlumnoDTO>();
	private StringBuilder texto = new StringBuilder();

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		texto.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		switch(qName) {
		case "nombre":
			alumnoDTO.setNombre(texto.toString().trim());
			break;
		case "curso":
			alumnoDTO.setCurso(texto.toString().trim());
			break;
		case "cuota":
			alumnoDTO.setCuota(Double.parseDouble(texto.toString().trim()));
			break;
		case "iban":
			alumnoDTO.setIban(texto.toString().trim());
			break;
		case "banco":
			alumnoDTO.setBanco(texto.toString().trim());
			break;
		case "alumno":
			lista.add(alumnoDTO); 
			break;
		}
		texto.setLength(0);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equalsIgnoreCase("alumno")) {
			alumnoDTO = new AlumnoDTO();
			alumnoDTO.setId(attributes.getValue("id"));
		}else if (qName.equalsIgnoreCase("pago")) {
			if (attributes.getValue("estado").equalsIgnoreCase("pagado")) {
				alumnoDTO.setTotalPagado(alumnoDTO.getTotalPagado() + Double.parseDouble(attributes.getValue("importe")));
			}else {
				alumnoDTO.setTotalPendiente(alumnoDTO.getTotalPendiente() + Double.parseDouble(attributes.getValue("importe")));
				alumnoDTO.setCuotasPendientes(alumnoDTO.getCuotasPendientes()+1);
			}
		}
		
	}

	public List<AlumnoDTO> getLista() {
		return lista;
	}
}
