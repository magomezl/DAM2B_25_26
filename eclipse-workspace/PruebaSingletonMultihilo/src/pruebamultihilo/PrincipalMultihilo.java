package pruebamultihilo;

public class PrincipalMultihilo {

	static class HiloRojo implements Runnable{

		@Override
		public void run() {
			Singleton singleton = Singleton.getInstance("Rojo");
			System.out.println("Singleton " + singleton.getColor());
		}
		
	}
	
	static class HiloAzul implements Runnable{

		@Override
		public void run() {
			Singleton singleton = Singleton.getInstance("Azul");
			System.out.println("Singleton " + singleton.getColor());
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		Thread hiloRojo = new Thread(new HiloRojo());
		Thread hiloAzul = new Thread(new HiloAzul());
		
		hiloRojo.start();
		hiloAzul.start();
		hiloRojo.sleep(10);
	}
}
