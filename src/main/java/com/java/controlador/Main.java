package com.java.controlador;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {
private static Scene scene;

	
	
	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("Inicio"));
		stage.setScene(scene);
		var context = SpringApplication.run(Main.class);
		stage.sizeToScene();
		//stage.setFullScreen(true);
		stage.setResizable(false);
		stage.show();
	}

	public static void setRoot(String fxml) throws IOException {
		Parent root = loadFXML(fxml);

		scene.setRoot(root);

		Stage stage = (Stage) scene.getWindow();
		stage.sizeToScene();
	}
	

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Vista/"+ fxml + ".fxml"));
		
		return fxmlLoader.load();
	}
	

	public static void main(String[] args) {
		launch();
	}
	

	
}