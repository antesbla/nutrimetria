package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.DTO.IngredientesDTO;
import com.java.DTO.MateriaCantidadDTO;
import com.java.DTO.MateriasProveedorRelProvDTO;
import com.java.model.RelConsejoId;
import com.java.model.RelMateriaId;
import com.java.model.modeloConsejo;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProducto;
import com.java.model.modeloProveedor;
import com.java.model.modeloRelConsejo;
import com.java.model.modeloRelMateria;
import com.java.service.RelConsejoService;
import com.java.service.impl.ConsejoServiceImpl;
import com.java.service.impl.MateriasPrimasServiceImpl;
import com.java.service.impl.ProductoServiceImpl;
import com.java.service.impl.RelMateriaServiceImpl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.CheckBox;


@Component
public class ControladorProducto implements Initializable{
	@FXML private TableView<IngredientesDTO> tablaProductos;
	@FXML private TableColumn<IngredientesDTO, Integer> colCodProd;
	@FXML private TableColumn<IngredientesDTO, String> colNombreProd;
	@FXML private TableColumn<IngredientesDTO, Double> colPeso;
	@FXML private TableColumn<IngredientesDTO, String> colCatLegal;
	@FXML private TableColumn<IngredientesDTO, String> colDurabilidad;
	@FXML private TableColumn<IngredientesDTO, String> colTransporte;
	@FXML private TableColumn<IngredientesDTO, String> colCondAlmac;
	@FXML private TableColumn<IngredientesDTO, String> colComposicion;
	@FXML private TableColumn<IngredientesDTO, Integer> colUnidadCaja;
	@FXML private TableColumn<IngredientesDTO, Double> colPesoCaja;
	@FXML private TableColumn<IngredientesDTO, String> colCodBarras;
	@FXML private TableColumn<IngredientesDTO, String> colIngredientes;
	
	@FXML private GridPane formAggProd;
	
	@FXML private ComboBox<MateriasProveedorRelProvDTO> materias;
	
	@FXML private TextField txtCodProd, txtNombre, txtDescripcion, txtPesoProd, txtCatLegal, txtCantidadMateria,  campoBusqueda;

	@FXML private TextField txtDurabilidad, txtTransporte, txtCondAlmac, txtComposicion, txtUniCajas, txtPesoCaja, txtCodBarras;
	
	@FXML private ListView<MateriaCantidadDTO> listaMateriasPrimas;
	@FXML private ListView<String> listaConsejos;
	
    @FXML private AnchorPane root;
	@FXML private Label txtAlerta;
	
	@FXML private CheckBox checkNuevoConsejo;
	@FXML private TextField txtNuevoConsejo;
	@FXML private ComboBox<modeloConsejo> consejos;
	
	@Autowired private ProductoServiceImpl servicioProducto;
	@Autowired private MateriasPrimasServiceImpl servicioMaterias;
	@Autowired private RelMateriaServiceImpl servicioRelMateria;
	@Autowired private ConsejoServiceImpl servicioConsejos;
	@Autowired private RelConsejoService servicioRelConsejo;


	private modeloProducto productoEnEdicion = null;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rellenarTabla();
		String css = getClass().getResource("/css/materiaPrimaCSS.css").toExternalForm();
		root.getStylesheets().add(css);
		List<MateriasProveedorRelProvDTO> cons = servicioMaterias.datosMatP();
	    materias.setItems(FXCollections.observableArrayList(cons));
	    
	    
	    List<modeloConsejo> prov = servicioConsejos.findAll();
	    consejos.setItems(FXCollections.observableArrayList(prov));
	    
	    materias.getSelectionModel().selectedItemProperty().addListener((unidad, valorViejo, valorNuevo) -> {
	    });
	    
		campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filtrarTablaProductos(newValue);
		});
		
		
		checkNuevoConsejo.selectedProperty().addListener((prop, oldVal, nuevaSeleccion) -> {
            consejos.setVisible(!nuevaSeleccion);
            consejos.setManaged(!nuevaSeleccion);
            consejos.setDisable(nuevaSeleccion);

            txtNuevoConsejo.setVisible(nuevaSeleccion);
            txtNuevoConsejo.setManaged(nuevaSeleccion);
            txtNuevoConsejo.setDisable(!nuevaSeleccion);
        });

	}
	
	@FXML
    private void agregarConsejo() {

        if (!checkNuevoConsejo.isSelected()) {
            modeloConsejo seleccionado = consejos.getValue();
            if (seleccionado == null) {
            	mostrarAdvertencia("No has seleccionado ningún ingrediente.");
                return;
            }

            String nombre = seleccionado.getConsejo();
            String texto =  nombre;

            if (!listaConsejos.getItems().contains(texto)) {
                listaConsejos.getItems().add(texto);
                consejos.getItems().remove(seleccionado);
            }

            consejos.getSelectionModel().clearSelection();
            checkNuevoConsejo.setSelected(false);
        } else {
            String nombreNuevo = txtNuevoConsejo.getText().trim();
            if (nombreNuevo.isEmpty()) {
            	txtAlerta.setText("Nombre de ingrediente vacío.");
                return;
            }

            String texto = nombreNuevo;

            if (!listaConsejos.getItems().contains(texto)) {
                listaConsejos.getItems().add(texto);
            }

            txtNuevoConsejo.clear();
            checkNuevoConsejo.setSelected(false);
        }
    }
	
	 @FXML
	    private void eliminarConsejo() {
	        String seleccionado = listaConsejos.getSelectionModel().getSelectedItem();
	        if (seleccionado != null) {
	            listaConsejos.getItems().remove(seleccionado);

	            String nombre = seleccionado.contains("(")
	                    ? seleccionado.substring(0, seleccionado.indexOf(" (")).trim()
	                    : seleccionado.trim();

	            servicioConsejos.findAll().stream()
	                .filter(ing -> ing.getConsejo().equals(nombre))
	                .findFirst()
	                .ifPresent(ing -> {
	                    if (!consejos.getItems().contains(ing)) {
	                        consejos.getItems().add(ing);
	                    }
	                });
	        } else {
	        	mostrarAdvertencia("No has seleccionado ningun ingrediente para eliminar");
	        }
	    }
	
	public void rellenarTabla() {
		this.colCodProd.setCellValueFactory(new PropertyValueFactory<>("cod_prod"));
		this.colNombreProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		this.colCatLegal.setCellValueFactory(new PropertyValueFactory<>("Cat_Legal"));
		this.colDurabilidad.setCellValueFactory(new PropertyValueFactory<>("durabilidad"));
		this.colTransporte.setCellValueFactory(new PropertyValueFactory<>("transporte"));
		this.colCondAlmac.setCellValueFactory(new PropertyValueFactory<>("cond_almac"));
		this.colComposicion.setCellValueFactory(new PropertyValueFactory<>("composicion"));
		this.colUnidadCaja.setCellValueFactory(new PropertyValueFactory<>("Unidad_Caja"));
		this.colPesoCaja.setCellValueFactory(new PropertyValueFactory<>("PesoCaja"));
		List<IngredientesDTO> datos = new ArrayList<>();

		for (modeloProducto p : servicioProducto.findAll()) {
			List<modeloRelMateria> relaciones = servicioRelMateria.findByProductoId(p.getId());

			List<String> nombresIngredientes = relaciones.stream()
				.map(rel -> rel.getMateriaPrima().getNombre())
				.toList();

			String ingredientesConcatenados = String.join(", ", nombresIngredientes);

			datos.add(new IngredientesDTO(p, ingredientesConcatenados));
		}

		tablaProductos.setItems(FXCollections.observableArrayList(datos));
		colIngredientes.setCellValueFactory(new PropertyValueFactory<>("ingredientes"));	
	}
	
	@FXML
	private void agregarMateriaPrimaAProducto() {
	    MateriasProveedorRelProvDTO seleccion = materias.getValue();
	    String cantidadStr = txtCantidadMateria.getText();

	    if (seleccion != null && !cantidadStr.isEmpty()) {
	        try {
	            double cantidad = Double.parseDouble(cantidadStr);

	            modeloMateriasPrimas materia = servicioMaterias.findById(seleccion.getId());
	            if (materia == null) {
	                mostrarAdvertencia("Materia no encontrada en la BBDD.");
	                return;
	            }

	            boolean yaExiste = listaMateriasPrimas.getItems().stream()
	            	    .anyMatch(m -> m.getMateria().getId() == materia.getId());

	            if (yaExiste) {
	                mostrarAdvertencia("La materia prima ya ha sido añadida.");
	                return;
	            }

	            MateriaCantidadDTO mc = new MateriaCantidadDTO(materia, cantidad);
	            listaMateriasPrimas.getItems().add(mc);
	            
	            materias.getSelectionModel().clearSelection();
	            txtCantidadMateria.clear();

	        } catch (NumberFormatException e) {
	            mostrarAdvertencia("Cantidad inválida.");
	        }
	    } else {
	        mostrarAdvertencia("Debes seleccionar una materia y escribir la cantidad.");
	    }
	}
	
	@FXML
	private void eliminarMateriaPrimaDelProducto() {
	    MateriaCantidadDTO seleccionada = listaMateriasPrimas.getSelectionModel().getSelectedItem();
	    if (seleccionada != null) {
	        listaMateriasPrimas.getItems().remove(seleccionada);
	    } else {
	        mostrarAdvertencia("Debes seleccionar una materia prima para eliminar.");
	    }
	}


	@FXML
	private void guardarProducto() {
	    try {
	        modeloProducto mp;
	        boolean esNuevo = (productoEnEdicion == null);

	        if (esNuevo) {
	            mp = new modeloProducto();
	        } else {
	            mp = servicioProducto.findById(productoEnEdicion.getId());
	            servicioRelMateria.deleteByProductoId(mp.getId());
	        }

	        mp.setCod_prod(Integer.parseInt(txtCodProd.getText()));
	        mp.setNombre(txtNombre.getText());
	        mp.setPeso(Double.parseDouble(txtPesoProd.getText()));
	        mp.setCat_legal(txtCatLegal.getText());
	        mp.setDurabilidad(txtDurabilidad.getText());
	        mp.setTransporte(txtTransporte.getText());
	        mp.setCond_almac(txtCondAlmac.getText());
	        mp.setComposicion(txtComposicion.getText());
	        mp.setUnidad_caja(Integer.parseInt(txtUniCajas.getText()));
	        mp.setPeso_caja(Double.parseDouble(txtPesoCaja.getText()));

	        servicioProducto.save(mp);

	        for (MateriaCantidadDTO mc : listaMateriasPrimas.getItems()) {
	            modeloMateriasPrimas materia = mc.getMateria();
	            double cantidad = mc.getCantidad();
	            modeloProveedor proveedor = mc.getProveedor();

	            RelMateriaId id = new RelMateriaId(mp.getId(), materia.getId());

	            modeloRelMateria rel = new modeloRelMateria();
	            rel.setId(id);
	            rel.setProducto(mp);
	            rel.setMateriaPrima(materia);
	            rel.setCantidad(cantidad);
	            rel.setProveedor(proveedor);

	            servicioRelMateria.save(rel);
	        }
	        
	     // Eliminar relaciones anteriores
	        servicioRelConsejo.eliminarRelacionesPorProducto(mp.getId());

	        // Recorrer consejos actuales
	        for (String textoConsejo : listaConsejos.getItems()) {
	            // Buscar consejo existente o crear uno nuevo
	            modeloConsejo consejo = servicioConsejos.findByConsejo(textoConsejo.trim());
	            if (consejo == null) {
	                consejo = new modeloConsejo();
	                consejo.setConsejo(textoConsejo.trim());
	                consejo = servicioConsejos.save(consejo);
	            }

	            // Crear relación
	            RelConsejoId relId = new RelConsejoId();
	            relId.setProducto(mp.getId());
	            relId.setConsejo(consejo.getId());

	            modeloRelConsejo rel = new modeloRelConsejo();
	            rel.setId(relId);
	            rel.setProducto(mp);
	            rel.setConsejo(consejo);

	            servicioRelConsejo.save(rel);
	        }


	        mostrarAdvertencia(esNuevo ? "Producto guardado correctamente." : "Producto modificado correctamente.");
	        rellenarTabla();
	        formAggProd.setVisible(false);
	        productoEnEdicion = null; // limpiamos estado

	    } catch (Exception e) {
	        mostrarAdvertencia("Error al guardar el producto: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void eliminarProducto() {
		IngredientesDTO p = this.tablaProductos.getSelectionModel().getSelectedItem();
		if (p == null) {
			mostrarAdvertencia("Debes seleccionar un producto"); 
		}else{
			int id = p.getId();
			servicioProducto.deleteById(id);
			rellenarTabla();
		}
	}
	
	@FXML
	private void mostrarFormularioModificar() {
	    IngredientesDTO dto = tablaProductos.getSelectionModel().getSelectedItem();
	    if (dto == null) {
	        mostrarAdvertencia("Debes seleccionar un producto para modificar.");
	        return;
	    }

	    modeloProducto producto = servicioProducto.findById(dto.getId());
	    if (producto == null) {
	        mostrarAdvertencia("Producto no encontrado.");
	        return;
	    }
	    
	    // Limpiar consejos anteriores
	    listaConsejos.getItems().clear();

	    // Cargar relaciones desde BBDD
	    List<modeloRelConsejo> relacionesConsejos = servicioRelConsejo.findByProductoId(producto.getId());

	    for (modeloRelConsejo rel : relacionesConsejos) {
	        listaConsejos.getItems().add(rel.getConsejo().getConsejo());

	        // Para evitar duplicados en el combo
	        consejos.getItems().removeIf(c -> c.getId() == rel.getConsejo().getId());
	    }


	    formAggProd.setVisible(true);

	    txtCodProd.setText(String.valueOf(producto.getCod_prod()));
	    txtNombre.setText(producto.getNombre());
	    txtPesoProd.setText(String.valueOf(producto.getPeso()));
	    txtCatLegal.setText(producto.getCat_legal());
	    txtDurabilidad.setText(producto.getDurabilidad());
	    txtTransporte.setText(producto.getTransporte());
	    txtCondAlmac.setText(producto.getCond_almac());
	    txtComposicion.setText(producto.getComposicion());
	    txtUniCajas.setText(String.valueOf(producto.getUnidad_caja()));
	    txtPesoCaja.setText(String.valueOf(producto.getPeso_caja()));

	    listaMateriasPrimas.getItems().clear();
	    List<modeloRelMateria> relaciones = servicioRelMateria.findByProductoId(producto.getId());
	    for (modeloRelMateria rel : relaciones) {
	        listaMateriasPrimas.getItems().add(new MateriaCantidadDTO(rel.getMateriaPrima(), rel.getCantidad()));
	    }
	}
		
	@FXML
	private void modificarProducto() {
	    IngredientesDTO seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
	    if (seleccionado == null) {
	        mostrarAdvertencia("Selecciona un producto para modificar");
	        return;
	    }

	    productoEnEdicion = servicioProducto.findById(seleccionado.getId());
	    if (productoEnEdicion == null) {
	        mostrarAdvertencia("Producto no encontrado");
	        return;
	    } else {
	    	mostrarFormularioModificar();
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
	private void formularioAgg() {
	    productoEnEdicion = null;
	    formAggProd.setVisible(true);

	    txtCodProd.clear();
	    txtNombre.clear();
	    txtPesoProd.clear();
	    txtCatLegal.clear();
	    txtDurabilidad.clear();
	    txtTransporte.clear();
	    txtCondAlmac.clear();
	    txtComposicion.clear();
	    txtUniCajas.clear();
	    txtPesoCaja.clear();
	    txtCodBarras.clear();

	    listaMateriasPrimas.getItems().clear();
	    materias.getSelectionModel().clearSelection();
	    txtCantidadMateria.clear();
	}
	
	private void filtrarTablaProductos(String filtro) {
		List<IngredientesDTO> datos = new ArrayList<>();

		for (modeloProducto p : servicioProducto.findAll()) {
			if (filtro == null || filtro.trim().isEmpty() || 
			    p.getNombre().toLowerCase().contains(filtro.toLowerCase())) {

				List<modeloRelMateria> relaciones = servicioRelMateria.findByProductoId(p.getId());

				List<String> nombresIngredientes = relaciones.stream()
					.map(rel -> rel.getMateriaPrima().getNombre())
					.toList();

				String ingredientesConcatenados = String.join(", ", nombresIngredientes);
				datos.add(new IngredientesDTO(p, ingredientesConcatenados));
			}
		}

		tablaProductos.setItems(FXCollections.observableArrayList(datos));
	}


	@FXML
	private void formularioMateriaPrima() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAggMatPrim.fxml"));
		loader.setControllerFactory(JavaFxApp::getBean);
		Parent root = loader.load();
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Agregar nueva materia prima");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	
	private void mostrarAdvertencia(String mensaje) {
        txtAlerta.setText(mensaje);
        txtAlerta.setVisible(true);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> txtAlerta.setVisible(false))
        );
        timeline.setCycleCount(1);
        timeline.play();        
    }
	
	@FXML
	private void CerrarFormularios() {
		formAggProd.setVisible(false);
	}
}
