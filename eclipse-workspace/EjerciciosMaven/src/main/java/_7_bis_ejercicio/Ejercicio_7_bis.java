package _7_bis_ejercicio;

public class Ejercicio_7_bis {
	private static final char SEPARADOR= ',';
	private static final char COMILLA= '"';
	private final static String FICHEROTRABAJO = "ejercicio08.csv";
	
	public static void main(String[] args) {
		

	}

	private static void mostrarValores(String[] fields) {
		for(int i=0; i<fields.length; i++) {
			System.out.println("%-30s|" + fields[i]);
		}
		System.out.println();
	}
}
