package _7_bis_ejercicio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import utilidades.Utilidades;

public class Ejercicio_7_bis {
	private static final char SEPARADORVALORES= ',';
	private static final char DELIMITADORVALORES= '"';
	private final static String FICHEROTRABAJO = "_7_bis_ejercicio" + 
			System.getProperty("file.separator") + "ejercicio07.csv";
	
	public static void main(String[] args) {
		try (CSVReader reader = new CSVReader(new FileReader(Utilidades.getRuta()+FICHEROTRABAJO),
				SEPARADORVALORES, DELIMITADORVALORES)){
			String[] nextLine = null;
			while((nextLine=reader.readNext())!=null){
				mostrarValores(nextLine);
			}
		
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
		} catch (IOException e) {
			System.out.println("Error de lectura");
		}

	}

	private static void mostrarValores(String[] fields) {
		for(int i=0; i<fields.length; i++) {
			System.out.printf("%-30s|", fields[i]);
		}
		System.out.println();
	}
}
