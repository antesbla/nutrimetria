package gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


@Component
public class ControladorEtiquetado implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
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
	@FXML
	private void Proveedor() throws Exception {
		JavaFxApp.setRoot("proveedor");
	}
	@FXML
	private void FichaTecnica() throws Exception {
		JavaFxApp.setRoot("fichaTecnica");
	}
	@FXML
	private void Productos() throws Exception {
		JavaFxApp.setRoot("productos");
	}
	
	/*private void mostrarAdvertencia(String mensaje) {
        txtAlerta.setText(mensaje);
        txtAlerta.setVisible(true);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> txtAlerta.setVisible(false))
        );
        timeline.setCycleCount(1);
        timeline.play();        
    }*/
}
