package paquete_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio3_v2 {
	
	private final static String DOCTRABAJO = "3_Ejercicio.csv";
	
	public static void main(String[] args) {
		
		try (BufferedReader bfRd = new BufferedReader(new FileReader(Utilidades.RUTA+DOCTRABAJO))){
			String linea;
			while ((linea = bfRd.readLine())!=null) {
				System.out.println(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedWriter bfWr= new BufferedWriter(new FileWriter(Utilidades.RUTA+DOCTRABAJO, true))){
			bfWr.write("Aurora;Gómez;DAM;2"+System.getProperty("line.separator"));
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de lectura");
			e.printStackTrace();
		}

	}
}
