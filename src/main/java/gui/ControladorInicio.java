package gui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.java.DTO.UsuarioDetalleDTO;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class ControladorInicio {
	
	@FXML private TextField nombreUsuario;
	@FXML private PasswordField password;
	@Autowired private AuthenticationManager authManager;
	
	@FXML
	private void CambiarVentana() throws Exception {
		JavaFxApp.setRoot("fichaTecnica");
	}
	
	@FXML
	private void CerrarApp() {
		Platform.exit();
	}
	
	@FXML
	private void IniciarSesion() {
		Authentication auth = authManager.authenticate(
			    new UsernamePasswordAuthenticationToken(nombreUsuario.getText(), password.getText())
			);
			SecurityContextHolder.getContext().setAuthentication(auth);

			// Acceder al permiso directamente
			UsuarioDetalleDTO detalles = (UsuarioDetalleDTO) auth.getPrincipal();
			int permiso = detalles.getPermisos();

			System.out.println("Permiso del usuario logueado: " + permiso);

			try {
				CambiarVentana();
			} catch (Exception e) {
				e.printStackTrace();
				mostrarAlertaError("Login incorrecto", "Usuario o contraseña incorrecto");
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
