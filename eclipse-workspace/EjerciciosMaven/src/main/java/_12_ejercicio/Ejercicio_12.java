package _12_ejercicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilidades.Utilidades;

public class Ejercicio_12 {
	private final static String DOCTRABAJO_IN = "vehiculosElectricos.xlsx";
	private static Workbook wb; 
	
	public static void main(String[] args) {
		buscarEstacionesPorOperador("Wenea");
		buscarEstacionesPorProvincia("salamanca");
		buscarEstacionesPorProvincia("valladolid");
		modificaOperador("Wenea", "WNA");
		
	}

	private static void modificaOperador(String antiguoNombre, String nuevoNombre) {
		// TODO Auto-generated method stub
		
	}

	private static void buscarEstacionesPorProvincia(String provincia) {
		
	}

	private static void buscarEstacionesPorOperador(String operador) {
		
	}

}
