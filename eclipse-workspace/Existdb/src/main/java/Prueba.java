import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.exist.ExistXQDataSource;

public class Prueba {
	private static XQDataSource xqs = new ExistXQDataSource();
	private static XQConnection conn; 

	// TODO Error de conexion
	public static void main(String[] args) {
		try {
			xqs.setProperty("serverName", "10.196.55.168");
			xqs.setProperty("port", "8080");
			xqs.setProperty("user", "admin");
			xqs.setProperty("password", "toor");
			conn = xqs.getConnection();
			
			consultaModulosCiclo("DAM");
			
		} catch (XQException e) {
			
			e.printStackTrace();
		}
	}
	private static void consultaModulosCiclo(String siglas) {
		String consultaNombreCiclo = "doc('/db/documentos/ciclos.xml')/f/ciclos/ciclo[@siglas='" + siglas + "']/nombre";
		XQExpression xqe;
		try {
			xqe = conn.createExpression();
			XQResultSequence xqRS = xqe.executeQuery(consultaNombreCiclo);
			XMLStreamReader xSR = xqRS.getItemAsStream();
			
			while(xSR.hasNext()) {
				if (xSR.getEventType() == XMLStreamConstants.END_ELEMENT) {
					System.out.println("Final del elemento");
				}
				if (xSR.getEventType() == XMLStreamConstants.CHARACTERS) {
					System.out.println(xSR.getText());
				}
				xSR.next();
			}
		} catch (XQException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
