package gui;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;

@Component
public class ControladorInicio {
	@FXML
	private void CambiarVentana() throws Exception {
		JavaFxApp.setRoot("materiaprima");
	}
	
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
}
