package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.model.modeloProveedor;
import com.java.service.impl.ProveedorServiceImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class ControladorProveedor implements Initializable{
	@FXML private TableView<modeloProveedor> tablaProveedores;
	@FXML private TableColumn<modeloProveedor, Integer> colID;
	@FXML private TableColumn<modeloProveedor, String> colNombre;
	@FXML private TableColumn<modeloProveedor, String> colCIF;
	@FXML private TableColumn<modeloProveedor, Integer> colTelefono;
	@FXML private TableColumn<modeloProveedor, String> colEmail;
	@FXML private TableColumn<modeloProveedor, String> colDireccion;
	@FXML private TextField campoBusqueda;
	
	@Autowired private ProveedorServiceImpl servicioProveedor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    rellenarTabla();

	    campoBusqueda.textProperty().addListener((obs, oldText, newText) -> {
	        filtrarProveedores(newText);
	    });
	}

	
	public void rellenarTabla() {
		this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.colCIF.setCellValueFactory(new PropertyValueFactory<>("cif"));
		this.colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		this.colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		List<modeloProveedor> datos = servicioProveedor.findAll();
		tablaProveedores.setItems(FXCollections.observableArrayList(datos));	
	}
	
	@FXML
	private void formularioAgregarProv() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAltaProveedor.fxml"));
		loader.setControllerFactory(JavaFxApp::getBean);
		Parent root = loader.load();
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Agregar nuevo proveedor");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	
	@FXML
	private void eliminarProveedor() {
		modeloProveedor p = this.tablaProveedores.getSelectionModel().getSelectedItem();
		if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un proveedor"); 
            alert.showAndWait();
		}else{
			int id = p.getId();
			servicioProveedor.deleteById(id);
			rellenarTabla();
		}
	}
	
	@FXML
	private void modificarProveedor() throws IOException {
	    modeloProveedor proveedorSeleccionado = this.tablaProveedores.getSelectionModel().getSelectedItem();

	    if (proveedorSeleccionado == null) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.setTitle("Error");
	        alert.setContentText("Debes seleccionar un proveedor para modificar");
	        alert.showAndWait();
	        return;
	    }

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAltaProveedor.fxml"));
	    loader.setControllerFactory(JavaFxApp::getBean);
	    Parent root = loader.load();

	    ControladorFormAltaProv controladorForm = loader.getController();
	    controladorForm.setProveedor(proveedorSeleccionado);

	    Stage stage = new Stage();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Modificar Proveedor");
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.showAndWait();

	    rellenarTabla();
	}

	private void filtrarProveedores(String filtro) {
	    List<modeloProveedor> todos = servicioProveedor.findAll();

	    if (filtro == null || filtro.isEmpty()) {
	        tablaProveedores.setItems(FXCollections.observableArrayList(todos));
	        return;
	    }

	    String filtroLower = filtro.toLowerCase();

	    List<modeloProveedor> filtrados = todos.stream()
	        .filter(p -> p.getNombre().toLowerCase().contains(filtroLower))
	        .toList();

	    tablaProveedores.setItems(FXCollections.observableArrayList(filtrados));
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
	private void Producto() throws Exception {
		JavaFxApp.setRoot("productos");
	}
	
	@FXML
	private void FichaTecnica() throws Exception {
		JavaFxApp.setRoot("fichaTecnica");
	}
	
	@FXML
	private void Etiquetado() throws Exception {
		JavaFxApp.setRoot("etiquetado");
	}
	
}
