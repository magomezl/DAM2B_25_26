package _12_ejercicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilidades.Utilidades;

public class Ejercicio_12 {
	private final static String DOCTRABAJO_IN = "vehiculosElectricos.xlsx";
	private static Workbook wb; 
	
	public static void main(String[] args) {
		
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.getRuta() + Utilidades.getRutaEjer12()+ DOCTRABAJO_IN)));
			buscarEstacionesPorOperador("Wenea");
			buscarEstacionesPorProvincia("salamanca");
			buscarEstacionesPorProvincia("valladolid");
//			modificaOperador("Wenea", "WNA");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void modificaOperador(String antiguoNombre, String nuevoNombre) {
		//Para casa 
		
	}

	private static void buscarEstacionesPorProvincia(String provincia) {
		Scanner sc = new Scanner(System.in);
		try {
			Sheet hoja = wb.getSheetAt(0);
			Sheet newHoja = wb.createSheet(provincia);
			int numFila = 0, newNumFila=0 ;
			Row fila = hoja.getRow(numFila);
			Row newFila = newHoja.createRow(newNumFila++); 
			newFila.createCell(0).setCellValue(fila.getCell(0).getStringCellValue());
			newFila.createCell(1).setCellValue(fila.getCell(1).getStringCellValue());
			// Muestro las localizaciones de los puntos de carga del operador pasado como parámetro
			System.out.println("PUNTOS DE RECARGA EN " + provincia );

			while(fila!=null) {
				Cell celdaBusqueda = fila.getCell(1);
				if (celdaBusqueda!=null) {
					if (celdaBusqueda.getStringCellValue().toLowerCase().contains("("+provincia.toLowerCase()+")")) {
						System.out.println("----->" + fila.getCell(0).getStringCellValue() + "(" + 
								fila.getCell(1).getStringCellValue());
						newFila = newHoja.createRow(newNumFila++);
						newFila.createCell(0).setCellValue(fila.getCell(0).getStringCellValue());
						newFila.createCell(1).setCellValue(fila.getCell(1).getStringCellValue());
					}
				}
				fila = hoja.getRow(numFila++);
			}
			try {
				wb.write(new FileOutputStream(Utilidades.getRuta() + Utilidades.getRutaEjer12()+ DOCTRABAJO_IN));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}catch(IllegalArgumentException e) {
			System.out.println("Ya se han encontrado los datos de " + provincia + "¿Desea sobreescribir? (S/N): ");
			if (sc.next().toUpperCase().equals("S")){
				wb.removeSheetAt(wb.getSheetIndex(wb.getSheet(provincia)));
				buscarEstacionesPorProvincia(provincia);
			}
		}

	}

	private static void buscarEstacionesPorOperador(String operador) {
		Sheet hoja = wb.getSheetAt(0);
		int numFila = 1;
		Row fila = hoja.getRow(numFila);
		
		System.out.println("PUNTOS DE RECARGA " + operador);
		
		while(fila!=null) {
			Cell celdaBusqueda = fila.getCell(2);
			if (celdaBusqueda!=null) {
				if (celdaBusqueda.getStringCellValue().contains(operador)) {
					System.out.println("----->" + fila.getCell(0).getStringCellValue() + "(" + 
							fila.getCell(1).getStringCellValue());
				}
			}
			fila = hoja.getRow(numFila++);
		} 
	}

}
