package modelo.bd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConexionDOC {
	private static ConexionDOC instance;
	private Workbook wb;
	
	private ConexionDOC() {
		try {
			wb = new XSSFWorkbook(ConexionDOC.class.getResourceAsStream("/resources/datosFFE.xlsx"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static ConexionDOC getInstance() {
		if (instance == null) {
			instance = new ConexionDOC();
		}
		return instance;
	}

	public Workbook getCon() {
		return wb;
	}
	
	public void cerrarConexion() {

		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
