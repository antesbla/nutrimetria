package gui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class ControladorInicio {
	
	@FXML
	private TextField nombreUsuario;
	@FXML
	private PasswordField password;
	@Autowired
	private AuthenticationManager authManager;
	
	@FXML
	private void CambiarVentana() throws Exception {
		JavaFxApp.setRoot("materiaprima");
	}
	
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
	
	@FXML
	private void IniciarSesion() {
        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    nombreUsuario.getText(),
                    password.getText()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            CambiarVentana();
        } catch (AuthenticationException e) {
        	mostrarAlertaError("Credenciales incorrectas", "Usuario o contraseña inválidos.");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void mostrarAlertaError(String titulo, String mensaje) {
	    Alert alerta = new Alert(Alert.AlertType.ERROR);
	    alerta.setTitle("Error de inicio de sesión");
	    alerta.setHeaderText(titulo);
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	}
}
