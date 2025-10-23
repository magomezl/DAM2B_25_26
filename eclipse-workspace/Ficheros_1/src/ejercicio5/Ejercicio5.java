package ejercicio5;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Scanner;

import paquete_1.Utilidades;

public class Ejercicio5 {
	private final static String DATOSFILEOUT = "FicheroSerializacion.bin";
	private static ObjectOutputStream oOS;
	private static Scanner sn = new Scanner(System.in);

	public static void main(String[] args) {
		oOS = inicializar(Utilidades.RUTA+DATOSFILEOUT);
//		escribirObjeto(obtenerDatos());
		escribirObjeto(oOS, new Persona(new StringBuilder("Alfonso"), new StringBuilder("Gómez"), 
				new StringBuilder("Borrego"), LocalDate.of(2020, 12, 21)));
		System.out.println("Elemento contenidos en el fichero " + leerObjetos(Utilidades.RUTA+DATOSFILEOUT));
	}
	
	public static int leerObjetos(String ruta) {
		int contador = 0;
		try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream
					(new File(ruta)))){
			
			while(true) {
				System.out.println((Persona) oIS.readObject());
				++contador;
			}
		}catch (EOFException e) {
			System.out.println("Fin de fichero");
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}	
		return contador;
	}

	public static Persona obtenerDatos() {
		Persona persona = new Persona();
		System.out.println("DATOS DE USUARIO:");
		System.out.println("\tNombre:");
		persona.setNombre(new StringBuilder(sn.nextLine()));
		System.out.println("\tPrimer Apellido:");
		persona.setApellido1(new StringBuilder(sn.nextLine()));
		System.out.println("\tSegundo Apellido:");
		persona.setApellido2(new StringBuilder(sn.nextLine()));
		return persona;
	}

	public static void escribirObjeto(ObjectOutputStream oOS, Persona persona) {
		try {
			oOS.writeObject(persona);
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
	}

	public static ObjectOutputStream inicializar(String ruta) {
		try {
			File file = new File(ruta);
			 if (file.exists() && file.length()>0) {
//				oOS = new MyObjectOutputStream(new FileOutputStream(file, true));
				oOS = new ObjectOutputStream(new FileOutputStream(file, true)) {
					@Override
					protected void writeStreamHeader() throws IOException {
						}
				};
			}else {
				oOS = new ObjectOutputStream(new FileOutputStream(file));
			}
			 
		} catch (FileNotFoundException e) {
			System.out.println("No se encuentra el fichero");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de entrada/salida");
			e.printStackTrace();
		}
		return oOS;
	}

	public static ObjectOutputStream getoOS() {
		return oOS;
	}

	
}
