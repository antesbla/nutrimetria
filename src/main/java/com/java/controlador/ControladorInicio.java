package com.java.controlador;

import javafx.application.Platform;
import javafx.fxml.FXML;


public class ControladorInicio {
	@FXML
	private void CambiarVentana() throws Exception {
		Main.setRoot("materiaprima");
	}
	
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
}
