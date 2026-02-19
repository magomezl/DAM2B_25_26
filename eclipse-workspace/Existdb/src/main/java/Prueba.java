import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import net.xqj.exist.ExistXQDataSource;

public class Prueba {
	// XQJ
	private static XQDataSource xqs = new ExistXQDataSource();
	private static XQConnection conn; 
	private static Collection col;
	//XML:DB

	// TODO Error de conexion
	public static void main(String[] args) {
		// XQJ
		try {
			xqs.setProperty("serverName", "10.196.55.168");
			xqs.setProperty("port", "8080");
			xqs.setProperty("user", "admin");
			xqs.setProperty("password", "toor");
			conn = xqs.getConnection();
			
//			consultaModulosCiclo("DAM");
//			anadirAutor("Aurora", "Gómez López", "El Código Da Vinci");
//			eliminarAutor("Aurora", "Gómez López");
		} catch (XQException e) {
			
			e.printStackTrace();
		}
		
		
		//XML:DB
		
		//Cargamos el driver eXist
		Class cl;
		try {
			cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			// Registro del driver
			DatabaseManager.registerDatabase(database);
			
			col = DatabaseManager.getCollection("xmldb:exist://10.196.55.168:8080/exist/xmlrpc/db/documentos", "admin", "toor");
			
			CollectionManagementService servicio = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			
			subirDocumento(new File(System.getProperty("user.dir") + "/src/main/resources/documento.xml"));
			descargarDocumento(col, "ciclos.xml", System.getProperty("user.dir") + "/src/main/resources");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	// XQJ
	private static void consultaModulosCiclo(String siglas) {
		String consultaNombreCiclo = "doc('/db/documentos/ciclos.xml')//ciclo/nombre";
		XQExpression xqe;
		try {
			xqe = conn.createExpression();
			XQResultSequence xqRS = xqe.executeQuery(consultaNombreCiclo);
			while(xqRS.next()) {
				XMLStreamReader xSR = xqRS.getItemAsStream();
				while(xSR.hasNext()) {
					if (xSR.getEventType() == XMLStreamConstants.END_ELEMENT) {
						System.out.println("Final del elemento");
					}else if (xSR.getEventType() == XMLStreamConstants.CHARACTERS) {
						System.out.println(xSR.getText());
					}else if (xSR.getEventType() == XMLStreamConstants.ATTRIBUTE) {
						System.out.println(xSR.getAttributeLocalName(0));
					}
					xSR.next();
				}
			}
		} catch (XQException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	// XQJ
	private static void anadirAutor(String nombreAutor, String apellidosAutor, String tituloLibro) {
		String consulta = "update insert "
				+ "<Autor> "
				+ "<Nombre>" + nombreAutor + "</Nombre>"
				+ "<Apellido>" + apellidosAutor + "</Apellido>" 
				+ "</Autor> into doc('/db/documentos/Libros.xml')/Libros/Libro[Titulo='" + tituloLibro + "']/Autores";
		
		try {
			XQExpression xqe = conn.createExpression();
			xqe.executeCommand(consulta);
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
	
	// XQJ
	private static void eliminarAutor(String nombreAutor, String apellidosAutor) {
		String consulta = "for $autor in doc('/db/documentos/Libros.xml')/Libros/Libro/Autores/Autor[Nombre='" + nombreAutor
				+ "'][Apellido='" + apellidosAutor + "'] return update delete $autor";
		
		try {
			XQExpression xqe = conn.createExpression();
			xqe.executeCommand(consulta);
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
	
	//XML:DB
	private static void subirDocumento(File archivo) {
		try {
			if (archivo.canRead()){
				Resource recurso = col.createResource(archivo.getName(), "XMLResource");
				recurso.setContent(archivo);
				col.storeResource(recurso);
			}
		} catch (XMLDBException e) {
		
			e.printStackTrace();
		}
	}
	//XML:DB
	private static void descargarDocumento(Collection col, String docName, String URIDestino) {
		try {
			XMLResource res = (XMLResource) col.getResource(docName);
			// Paso el contenido del recurso a un árbol DOM en memoria
			Node documento = res.getContentAsDOM();
			File archivo = new File(URIDestino+"/"+docName);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(documento), new StreamResult(archivo));
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
