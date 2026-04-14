package ut_1.ejercicio1_SAX_Excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

public class ParseoSAXExcel {

	public static void main(String[] args) {
		
		try {
			SAXParser parseador = SAXParserFactory.newInstance().newSAXParser();
			Manejador handler = new Manejador();
			
			parseador.parse(new File("src/main/resources/Files/academia.xml"), handler);
			
//			System.out.println(handler.getLista());
			
			creaExcel(handler.getLista());
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void creaExcel(List<AlumnoDTO> lista) throws FileNotFoundException, IOException {
		Workbook libro = new XSSFWorkbook();
		Sheet hoja = libro.createSheet("datos_alumnos");	
		
		String[] cabecera = { "ID Alumno", "Nombre", "Curso", "Cuota Mensual", "Número de cuenta", "Banco", "Total Pagado", "Total Pendiente", "Cuotas Pendientes"};
		Row fila0 = hoja.createRow(0);
		for(int i=0; i<cabecera.length; i++) {
			Cell celda = fila0.createCell(i);
			celda.setCellValue(cabecera[i]);
		}
		
		int numfila = 1;
		for(AlumnoDTO alumno: lista) {
			Row fila = hoja.createRow(numfila++);
			fila.createCell(0).setCellValue(alumno.getId());
			fila.createCell(1).setCellValue(alumno.getNombre());
			fila.createCell(2).setCellValue(alumno.getCurso());
			fila.createCell(3).setCellValue(alumno.getCuota());
			fila.createCell(4).setCellValue(alumno.getIban());
			fila.createCell(5).setCellValue(alumno.getBanco());
			fila.createCell(6).setCellValue(alumno.getTotalPagado());
			fila.createCell(7).setCellValue(alumno.getTotalPendiente());
			fila.createCell(8).setCellValue(alumno.getCuotasPendientes());
		}
		libro.write(new FileOutputStream("src/main/resources/Files/alumnos.xlsx"));
		libro.close();
	}

}
