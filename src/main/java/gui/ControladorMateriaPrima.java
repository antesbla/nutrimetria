package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.DTO.MateriaPrimaCompletaDTO;
import com.java.DTO.MateriasProveedorRelProvDTO;
import com.java.model.modeloProveedor;
import com.java.service.impl.MateriasPrimasServiceImpl;
import com.java.service.impl.ProveedorServiceImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class ControladorMateriaPrima implements Initializable{
	
	@FXML private TableView<MateriasProveedorRelProvDTO> tablaMateriasPrimas;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, String> colId;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, String> colNombre;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, String> colProveedor;  
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colPrecio;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colKcal;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colHidratos;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colAzucares;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colGrasas;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colSaturadas;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colProteinas;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colSal;
    @FXML private TableColumn<MateriasProveedorRelProvDTO, Double> colFibra;
    @FXML private AnchorPane root;
    @FXML private GridPane formAggMatPrim;
    @FXML private ChoiceBox<modeloProveedor> proveedores;
    @FXML private TextField FAGnombre, FAGprecio, FAGkcal, FAGhidratos, FAGazucares, FAGgrasas, FAGsaturadas, FAGproteinas, FAGsal, FAGfibra, campoBusqueda;
    @Autowired private MateriasPrimasServiceImpl servicioMateriasPrimas;
    @Autowired private ProveedorServiceImpl servicioProveedores;
    
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		rellenarTabla();
		String css = getClass().getResource("/css/materiaPrimaCSS.css").toExternalForm();
	    root.getStylesheets().add(css);
	    List<modeloProveedor> prov = servicioProveedores.findAll();
	    proveedores.setItems(FXCollections.observableArrayList(prov));
	
	    campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
	        filtrarTabla(newValue);
	    });
	}
	
	@FXML
	public void rellenarTabla() {
		this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
		this.colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		this.colKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
		this.colHidratos.setCellValueFactory(new PropertyValueFactory<>("hidratos"));
		this.colAzucares.setCellValueFactory(new PropertyValueFactory<>("azucares"));
		this.colGrasas.setCellValueFactory(new PropertyValueFactory<>("grasas"));
		this.colSaturadas.setCellValueFactory(new PropertyValueFactory<>("saturadas"));
		this.colProteinas.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
		this.colSal.setCellValueFactory(new PropertyValueFactory<>("sal"));
		this.colFibra.setCellValueFactory(new PropertyValueFactory<>("fibra"));
		List<MateriasProveedorRelProvDTO> datos = servicioMateriasPrimas.datosMatP();
		tablaMateriasPrimas.setItems(FXCollections.observableArrayList(datos));
	}
	
	@FXML
	private void eliminarMateriaPrima() {
		MateriasProveedorRelProvDTO m = this.tablaMateriasPrimas.getSelectionModel().getSelectedItem();
		if (m == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una materia prima"); 
            alert.showAndWait();
		}else{
			int id = m.getId();
			servicioMateriasPrimas.deleteById(id);
			rellenarTabla();
		}
	}
	
	@FXML
	private void modificarMateriaPrima() throws IOException {
	    MateriaPrimaCompletaDTO seleccionada = servicioMateriasPrimas.obtenerDTOCompletoPorId(
	        tablaMateriasPrimas.getSelectionModel().getSelectedItem().getId());

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAggMatPrim.fxml"));
	    loader.setControllerFactory(JavaFxApp::getBean);
	    Parent root = loader.load();

	    ControladorFormAggMatPrim controlador = loader.getController();
	    controlador.setMateriaEditar(seleccionada);

	    Stage stage = new Stage();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Modificar Materia Prima");
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.show();
	}

	private void filtrarTabla(String filtro) {
	    List<MateriasProveedorRelProvDTO> datos = servicioMateriasPrimas.datosMatP();

	    if (filtro == null || filtro.trim().isEmpty()) {
	        tablaMateriasPrimas.setItems(FXCollections.observableArrayList(datos));
	        return;
	    }

	    String filtroLower = filtro.toLowerCase();

	    List<MateriasProveedorRelProvDTO> filtrado = datos.stream()
	        .filter(m -> m.getNombre().toLowerCase().contains(filtroLower))
	        .toList();

	    tablaMateriasPrimas.setItems(FXCollections.observableArrayList(filtrado));
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
	private void Productos() throws Exception {
		JavaFxApp.setRoot("productos");
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
	private void Etiquetado() throws Exception {
		JavaFxApp.setRoot("etiquetado");
	}
	
	@FXML
	private void formularioAgg() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAggMatPrim.fxml"));
		loader.setControllerFactory(JavaFxApp::getBean);
		Parent root = loader.load();
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Agregar nueva materia prima");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	@FXML
	private void Cerrarformularios() {
		formAggMatPrim.setVisible(false);
	}
}
