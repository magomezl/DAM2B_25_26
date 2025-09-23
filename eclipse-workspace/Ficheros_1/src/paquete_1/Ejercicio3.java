package paquete_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ejercicio3 {
	public final static String RUTA = System.getProperty("user.dir") + System.getProperty("file.separator") +
			"data" + System.getProperty("file.separator");
	
	private final static String DOCTRABAJO = "3_Ejercicio.csv";
	
	public static void main(String[] args) {
		BufferedReader bfRd = null;
		PrintWriter pW = null;
		try {
			bfRd = new BufferedReader(new FileReader(RUTA+DOCTRABAJO));
			String linea;
			while ((linea = bfRd.readLine())!=null) {
				System.out.println(linea);
			}
			bfRd.close();
			pW = new PrintWriter(new FileWriter(RUTA+DOCTRABAJO, true));
			pW.println("Aurora;Gómez;DAM;2");
			pW.flush();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de lectura");
			e.printStackTrace();
		} finally {
			try {
				bfRd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pW.close();
			
		}

	}
}
