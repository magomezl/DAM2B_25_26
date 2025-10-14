package utilidades;

public class Utilidades {
	private final static String RUTA = System.getProperty("user.dir") + System.getProperty("file.separator") +
			"src" + System.getProperty("file.separator") +
			"main" + System.getProperty("file.separator") +
			"resources" + System.getProperty("file.separator");

	private final static String RUTA_EJER_10 =  "_10_ejercicio" + System.getProperty("file.separator");
	
	private final static String RUTA_EJER_11 =  "_11_ejercicio" + System.getProperty("file.separator");
		
	private final static String RUTA_EJER_12 =  "_12_ejercicio" + System.getProperty("file.separator");
	public static String getRuta() {
		return RUTA;
	}

	public static String getRutaEjer11() {
		return RUTA_EJER_11;
	}

	public static String getRutaEjer10() {
		return RUTA_EJER_10;
	}

	public static String getRutaEjer12() {
		return RUTA_EJER_12;
	}
	
	
}
