package gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.java.model.modeloProveedor;
import com.java.service.impl.ProveedorServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class ControladorFormAltaProv {
	@FXML private TextField txtNombre, txtEmail, txtCIF, txtTelefono, txtDireccion;
	@FXML private Label txtAlerta, labelTitulo;
	
	@Autowired ProveedorServiceImpl servicioProveedor;
	
    private modeloProveedor proveedorActual;
	

    public void setProveedor(modeloProveedor proveedor) {
        this.proveedorActual = proveedor;
        if (proveedor != null) {
            txtNombre.setText(proveedor.getNombre());
            txtCIF.setText(proveedor.getCif());
            txtTelefono.setText(String.valueOf(proveedor.getTelefono()));
            txtEmail.setText(proveedor.getEmail());
            txtDireccion.setText(proveedor.getDireccion());
        }
    }

    @FXML
    private void guardarProveedor() {
        if (proveedorActual == null) {
            proveedorActual = new modeloProveedor();
        }

        proveedorActual.setNombre(txtNombre.getText());
        proveedorActual.setCif(txtCIF.getText());
        proveedorActual.setTelefono(Integer.parseInt(txtTelefono.getText()));
        proveedorActual.setEmail(txtEmail.getText());
        proveedorActual.setDireccion(txtDireccion.getText());

        try {
            servicioProveedor.save(proveedorActual);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Éxito");
            alert.setContentText("Proveedor guardado correctamente.");
            alert.showAndWait();

            // Cerrar la ventana después de guardar
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();
        } catch (DataIntegrityViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("El CIF ya existe. Por favor, ingresa un CIF único.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ocurrió un error al guardar el proveedor.");
            alert.showAndWait();
        }
    }

	
    @FXML
    private void botonCancelar() {
        Stage stage = (Stage) txtAlerta.getScene().getWindow(); 
        stage.close();
    }
}
