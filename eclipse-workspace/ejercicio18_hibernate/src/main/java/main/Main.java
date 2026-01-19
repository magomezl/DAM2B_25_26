package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		/**
		 *  lanzar la aplicación JavaFX, iniciar el entorno gráfico y llama automaticamente al método start
		 */
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/**
		 *este método se ejecuta automaticamente cuando se lanza la aplicacion 
		 *el parámetro Stage representa la ventana principal de la aplicación 
		 */
		
		// Carga la vista FXML 
		Parent root = FXMLLoader.load(getClass().getResource("/vista.fxml"));
		// Crea la escena
		Scene scene = new Scene(root);
		//Configuramos y hacemos visible la ventana
		primaryStage.setScene(scene);
		primaryStage.setTitle("Gestión de empresa");
		primaryStage.show();
	}
}
