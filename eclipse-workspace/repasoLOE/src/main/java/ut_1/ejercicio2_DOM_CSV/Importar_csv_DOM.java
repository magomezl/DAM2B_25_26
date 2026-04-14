package ut_1.ejercicio2_DOM_CSV;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.opencsv.CSVReader;

public class Importar_csv_DOM {


	public static void main(String[] args) {

		try {
			// Cargar XML en memoria
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			InputStream is = Importar_csv_DOM.class
					.getClassLoader()
					.getResourceAsStream("resources/Files/academia.xml");

			if (is == null) {
				throw new RuntimeException("No se encuentra academia.xml en el JAR");
			}
			
			Document doc = builder.parse("src/main/resources/Files/alumnos_actualizado.xml");
//			Document doc = builder.parse(is);

			// normalize() normaliza el documento xml eliminando nodos de texto vacíos o innecesarios: tabulaciones, saltos de línea que considera como nodos
			doc.getDocumentElement().normalize();

			// Para evitar alumnos en el nuevo documento con IDs duplicados
			Set<String> idsExistentes = new HashSet<>();
			NodeList listaAlumnos = doc.getElementsByTagName("alumno");
			for (int i = 0; i < listaAlumnos.getLength(); i++) {
				Element alumno = (Element) listaAlumnos.item(i);
				idsExistentes.add(alumno.getAttribute("id"));
			}
			
			// Tomamos el elemento raíz
			Element raiz = doc.getDocumentElement(); // <academia>

			//Leer CSV con OpenCSV
			InputStream isCSV = Importar_csv_DOM.class
					.getClassLoader()
					.getResourceAsStream("resources/Files/nuevos_alumnos.csv");

			if (isCSV == null) {
				throw new RuntimeException("No se encuentra nuevos_alumnos.csv en el JAR");
			}
			
//			CSVReader reader = new CSVReader(new FileReader("src/main/resources/Files/nuevos_alumnos.csv"));

			CSVReader reader = new CSVReader(new InputStreamReader(isCSV));
			// Lee la cabecera pero no hacemos nada con ella, la saltamos
			reader.readNext();
			
			String[] linea;
			while ((linea = reader.readNext()) != null) {
				String id = linea[0];

				if (idsExistentes.contains(id)) {
					System.out.println("ID duplicado: " + id + " (alumno no insertado)");
					continue; // salta a la siguiente línea del CSV
				}
				idsExistentes.add(id);
				String nombre = linea[1];
				String curso = linea[2];
				String iban = linea[3];
				String banco = linea[4];

				// Creamos el nodo <alumno>
				Element alumno = doc.createElement("alumno");
				alumno.setAttribute("id", id);

				alumno.appendChild(crearElemento(doc, "nombre", nombre));
				alumno.appendChild(crearElemento(doc, "curso", curso));
				alumno.appendChild(crearElemento(doc, "cuota", String.valueOf(obtenerCuota(curso))));

				// Creamos elmento domiciliación
				Element domiciliacion = doc.createElement("domiciliacion");
				domiciliacion.appendChild(crearElemento(doc, "iban", iban));
				domiciliacion.appendChild(crearElemento(doc, "banco", banco));
				alumno.appendChild(domiciliacion);

				// Creamos elemento pagos vacío 
				Element pagos = doc.createElement("pagos");
				alumno.appendChild(pagos);

				// Añadir alumno al XML
				raiz.appendChild(alumno);
			}

			reader.close();

			// Guardar XML actualizado
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult("src/main/resources/Files/alumnos_actualizado.xml");
			transformer.transform(source, result);

			System.out.println("Documento alumnos_actualizado.xml creado correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para crear etiquetas simples
	private static Element crearElemento(Document doc, String nombre, String valor) {
		Element elemento = doc.createElement(nombre);
		elemento.setTextContent(valor);
		return elemento;
	}

	// Método para obtener la cuota según del curso
	private static int obtenerCuota(String curso) {
		switch (curso.toLowerCase()) {
		case "canto":
			return 120;
		case "guitarra":
			return 150;
		case "piano":
			return 100;
		case "batería":
			return 180;
		default:
			return 0;
		}
	}

}
