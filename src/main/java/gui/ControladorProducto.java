package gui;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;

@Component
public class ControladorProducto {
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
	
	@FXML
	private void CerrarSesion() throws Exception {
		JavaFxApp.setRoot("Inicio");
	}
	
	@FXML
	private void MateriaPrima() throws Exception {
		JavaFxApp.setRoot("materiaprima");
	}
}
