package com.java.controlador;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ControladorProducto {
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
	
	@FXML
	private void CerrarSesion() throws Exception {
		Main.setRoot("Inicio");
	}
	
	@FXML
	private void MateriaPrima() throws Exception {
		Main.setRoot("materiaprima");
	}
}
