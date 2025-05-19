package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.DTO.MateriasProveedorRelProvDTO;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProducto;
import com.java.model.modeloProveedor;
import com.java.service.impl.MateriasPrimasServiceImpl;
import com.java.service.impl.ProductoServiceImpl;
import com.java.service.impl.ProveedorServiceImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

@Component
public class ControladorProducto implements Initializable{
	@FXML
	private TableView<modeloProducto> tablaProductos;
	@FXML
    private TableColumn<modeloProducto, Integer> colCodProd;
	@FXML
	private TableColumn<modeloProducto, String> colNombreProd;
	@FXML
	private TableColumn<modeloProducto, String> colDescripcion;
	@FXML
	private TableColumn<modeloProducto, Double> colPeso;
	@FXML
	private TableColumn<modeloProducto, String> colCatLegal;
	@FXML
	private TableColumn<modeloProducto, String> colDurabilidad;
	@FXML
	private TableColumn<modeloProducto, String> colTransporte;
	@FXML
	private TableColumn<modeloProducto, String> colCondAlmac;
	@FXML
	private TableColumn<modeloProducto, String> colComposicion;
	@FXML
	private TableColumn<modeloProducto, Integer> colUnidadCaja;
	@FXML
	private TableColumn<modeloProducto, Double> colPesoCaja;
	@FXML
	private TableColumn<modeloProducto, String> colCodBarras;
	@FXML
    private GridPane formAggProd;
	@FXML
	private ComboBox<MateriasProveedorRelProvDTO> materias;
	@FXML
	private ComboBox<modeloMateriasPrimas> comboUnidadMedida;
	@FXML
	private TextField txtUnidadMedida, txtCantidadMateria, txtNuevaMedida;
	@FXML
	private ListView<String> listaMateriasPrimas;
	@FXML
	private CheckBox nuevaMateria;
	
	@Autowired
	private ProductoServiceImpl servicioProducto;
	@Autowired
	private MateriasPrimasServiceImpl servicioMaterias;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rellenarTabla();
		txtUnidadMedida.setDisable(true);
		List<MateriasProveedorRelProvDTO> mat = servicioMaterias.datosMatP();
	    materias.setItems(FXCollections.observableArrayList(mat));
	    materias.getSelectionModel().selectedItemProperty().addListener((unidad, valorViejo, valorNuevo) -> {
	    	if (valorNuevo != null) {
	    		txtUnidadMedida.setText(valorNuevo.getUnidadMedida());
	    	}
	    });
	    
	    nuevaMateria.selectedProperty().addListener((propiedad, seleccion, estaSeleccionada) -> {
	    	materias.setVisible(!estaSeleccionada);
	    	txtUnidadMedida.setDisable(!estaSeleccionada);
	    	txtNuevaMedida.setDisable(!estaSeleccionada);
	    	comboUnidadMedida.setVisible(estaSeleccionada);
	    	txtUnidadMedida.setVisible(!estaSeleccionada);
	    });
	    
	    
	}
	
	public void rellenarTabla() {
		this.colCodProd.setCellValueFactory(new PropertyValueFactory<>("cod_prod"));
		this.colNombreProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		this.colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		this.colCatLegal.setCellValueFactory(new PropertyValueFactory<>("cat_legal"));
		this.colDurabilidad.setCellValueFactory(new PropertyValueFactory<>("durabilidad"));
		this.colTransporte.setCellValueFactory(new PropertyValueFactory<>("transporte"));
		this.colCondAlmac.setCellValueFactory(new PropertyValueFactory<>("cond_almac"));
		this.colComposicion.setCellValueFactory(new PropertyValueFactory<>("composicion"));
		this.colUnidadCaja.setCellValueFactory(new PropertyValueFactory<>("unidad_caja"));
		this.colPesoCaja.setCellValueFactory(new PropertyValueFactory<>("peso_caja"));
		this.colCodBarras.setCellValueFactory(new PropertyValueFactory<>("cod_barras"));
		List<modeloProducto> datos = servicioProducto.findAll();
		tablaProductos.setItems(FXCollections.observableArrayList(datos));	
	}
	
	@FXML
	private void agregarMateriaPrimaAProducto() {
		MateriasProveedorRelProvDTO materiaSeleccionada = materias.getValue();
		String unidad = txtUnidadMedida.getText();
		String cantidad = txtCantidadMateria.getText();
		
		if (!nuevaMateria.isSelected()) {
			if (materiaSeleccionada != null && !unidad.isEmpty() && !cantidad.isEmpty()) {
				String materia = cantidad + " " + unidad + " de " + materiaSeleccionada.getNombre();
				
				 boolean yaExiste = listaMateriasPrimas.getItems().stream()
				            .anyMatch(item -> item.startsWith(materiaSeleccionada.getNombre()));
				 
				 if (yaExiste) {
					System.out.println("ya existe");
					materias.getSelectionModel().clearSelection();
					txtCantidadMateria.clear();
					txtUnidadMedida.clear();
				 }else {
					listaMateriasPrimas.getItems().add(materia);
					materias.getSelectionModel().clearSelection();
					txtCantidadMateria.clear();
					txtUnidadMedida.clear();
				 }
			}
		} else {
			if (txtNuevaMedida != null && !unidad.isEmpty() && !cantidad.isEmpty()) {
				String materia = cantidad + " " + unidad + " de " + txtNuevaMedida.getText();
				modeloMateriasPrimas mat = null;
				mat.setNombre(txtNuevaMedida.getText());
				mat.setUnidad_medida(unidad);
				
				boolean yaExiste = listaMateriasPrimas.getItems().stream()
			            .anyMatch(item -> item.startsWith(txtNuevaMedida.getText()));
			 
			 if (yaExiste) {
				System.out.println("ya existe");
				materias.getSelectionModel().clearSelection();
				txtCantidadMateria.clear();
				txtUnidadMedida.clear();
			 }else {
				listaMateriasPrimas.getItems().add(materia);
				materias.getSelectionModel().clearSelection();
				txtCantidadMateria.clear();
				txtUnidadMedida.clear();
				servicioMaterias.save(mat);
			 }
			}
		}
	}
	
	@FXML
	private void eliminarProducto() {
		modeloProducto p = this.tablaProductos.getSelectionModel().getSelectedItem();
		if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un producto"); 
            alert.showAndWait();
		}else{
			int id = p.getId();
			servicioProducto.deleteById(id);
			rellenarTabla();
		}
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
	private void formularioAgg() {
		formAggProd.setVisible(true);
	}
	
	@FXML
	private void CerrarFormularios() {
		formAggProd.setVisible(false);
	}
}
