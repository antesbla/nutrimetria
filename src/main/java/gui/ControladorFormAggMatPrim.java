package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.DTO.MateriaPrimaCompletaDTO;
import com.java.model.modeloAlergeno;
import com.java.model.modeloIngredientes;
import com.java.model.modeloProveedor;
import com.java.service.impl.AlergenoServiceImpl;
import com.java.service.impl.IngredientesServiceImpl;
import com.java.service.impl.MateriasPrimasServiceImpl;
import com.java.service.impl.ProveedorServiceImpl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

@Component
public class ControladorFormAggMatPrim {

    @FXML private TableView<NutrienteRow> tablaValoresNutricionales;
    @FXML private TableColumn<NutrienteRow, String> colValorNutricional;
    @FXML private TableColumn<NutrienteRow, String> colUnidadMedida;
    @FXML private TableColumn<NutrienteRow, TextField> colValor;

    @FXML private ComboBox<modeloAlergeno> comboBoxAlergenos;
    @FXML private ComboBox<modeloProveedor> comboBoxProveedor;
    @FXML private ComboBox<modeloAlergeno> comboBoxTrazas;
    @FXML private ComboBox<modeloIngredientes> comboBoxIngredientes;
    @FXML private ComboBox<String> comboBoxUnidadMedida;
    
    @FXML private CheckBox checkNuevoIngrediente;
    @FXML private TextField porcentajeIngrediente, txtNuevoIngrediente, txtPrecio, txtNombre;
    @FXML private ListView<String> listaIngredientes, listaAlergenos, listaTrazas;
    @FXML private Label txtAlerta;

    @Autowired private AlergenoServiceImpl servicioAlergenos;
    @Autowired private ProveedorServiceImpl servicioProveedor;
    @Autowired private IngredientesServiceImpl servicioIngredientes;
    @Autowired private MateriasPrimasServiceImpl servicioMateriasPrimas;
    
	private MateriaPrimaCompletaDTO materiaEditar;


    public static class NutrienteRow {
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty unidad;
        private final TextField input;

        public NutrienteRow(String nombre, String unidad) {
            this.nombre = new SimpleStringProperty(nombre);
            this.unidad = new SimpleStringProperty(unidad);
            this.input = new TextField();
        }

        public String getNombre() { return nombre.get(); }
        public String getUnidad() { return unidad.get(); }
        public TextField getInput() { return input; }

        public StringProperty nombreProperty() { return nombre; }
        public StringProperty unidadProperty() { return unidad; }
    }

    @FXML
    public void initialize() {
        colValorNutricional.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colUnidadMedida.setCellValueFactory(cellData -> cellData.getValue().unidadProperty());
        colValor.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getInput()));
        colValor.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(TextField item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : item);
            }
        });

        checkNuevoIngrediente.selectedProperty().addListener((prop, oldVal, nuevaSeleccion) -> {
            comboBoxIngredientes.setVisible(!nuevaSeleccion);
            comboBoxIngredientes.setManaged(!nuevaSeleccion);
            comboBoxIngredientes.setDisable(nuevaSeleccion);

            txtNuevoIngrediente.setVisible(nuevaSeleccion);
            txtNuevoIngrediente.setManaged(nuevaSeleccion);
            txtNuevoIngrediente.setDisable(!nuevaSeleccion);

            porcentajeIngrediente.setDisable(!nuevaSeleccion);
        });

        tablaValoresNutricionales.setItems(FXCollections.observableArrayList(
            new NutrienteRow("Calorías", "kcal"),
            new NutrienteRow("Hidratos", "g"),
            new NutrienteRow("Azúcares", "g"),
            new NutrienteRow("Grasas", "g"),
            new NutrienteRow("Saturadas", "g"),
            new NutrienteRow("Proteínas", "g"),
            new NutrienteRow("Sal", "g"),
            new NutrienteRow("Fibra", "g")
        ));

        comboBoxAlergenos.setItems(FXCollections.observableArrayList(servicioAlergenos.findAll()));
        comboBoxTrazas.setItems(FXCollections.observableArrayList(servicioAlergenos.findAll()));
        comboBoxProveedor.setItems(FXCollections.observableArrayList(servicioProveedor.findAll()));
        comboBoxIngredientes.setItems(FXCollections.observableArrayList(servicioIngredientes.findAll()));
        comboBoxUnidadMedida.setItems(FXCollections.observableArrayList("Gramos", "Kilogramos"));
        
        comboBoxIngredientes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, nuevoIngrediente) -> {
            if (nuevoIngrediente != null && !checkNuevoIngrediente.isSelected()) {
                Double porcentaje = nuevoIngrediente.getPorcentaje();
                porcentajeIngrediente.setText(porcentaje != null ? porcentaje.toString() : "");
            }
        });
        
        listaAlergenos.getItems().addListener((javafx.collections.ListChangeListener<String>) change -> {
            List<modeloAlergeno> todos = servicioAlergenos.findAll();

            List<modeloAlergeno> disponibles = todos.stream()
                .filter(al -> !listaAlergenos.getItems().contains(al.getNombre()))
                .sorted((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()))
                .toList();

            comboBoxAlergenos.getItems().setAll(disponibles);

            List<modeloAlergeno> trazasDisponibles = disponibles.stream()
                .filter(al -> !listaTrazas.getItems().contains(al.getNombre()))
                .toList();

            comboBoxTrazas.getItems().setAll(trazasDisponibles);
        });

    }

    @FXML
    private void agregarIngredienteAMateriaPrima() {
        Double porcentaje = null;

        if (!porcentajeIngrediente.getText().trim().isEmpty()) {
            try {
                porcentaje = Double.parseDouble(porcentajeIngrediente.getText().trim());
            } catch (NumberFormatException e) {
            	mostrarAdvertencia("Porcentaje no válido (solo números).");
                return;
            }
        }

        if (!checkNuevoIngrediente.isSelected()) {
            modeloIngredientes seleccionado = comboBoxIngredientes.getValue();
            if (seleccionado == null) {
            	mostrarAdvertencia("No has seleccionado ningún ingrediente.");
                return;
            }

            String nombre = seleccionado.getNombre();
            Double porcentajeBD = seleccionado.getPorcentaje();
            String texto = (porcentajeBD != null) ? nombre + " (" + porcentajeBD + "%)" : nombre;

            if (!listaIngredientes.getItems().contains(texto)) {
                listaIngredientes.getItems().add(texto);
                comboBoxIngredientes.getItems().remove(seleccionado);
            }

            comboBoxIngredientes.getSelectionModel().clearSelection();
            porcentajeIngrediente.clear();

        } else {
            String nombreNuevo = txtNuevoIngrediente.getText().trim();
            if (nombreNuevo.isEmpty()) {
            	txtAlerta.setText("Nombre de ingrediente vacío.");
                return;
            }

            String texto = (porcentaje != null) ? nombreNuevo + " (" + porcentaje + "%)" : nombreNuevo;

            if (!listaIngredientes.getItems().contains(texto)) {
                listaIngredientes.getItems().add(texto);
            }

            txtNuevoIngrediente.clear();
            porcentajeIngrediente.clear();
        }
    }

    @FXML
    private void agregarAlergenoAMateria() {
        modeloAlergeno seleccionado = comboBoxAlergenos.getValue();

        if (seleccionado == null) {
        	mostrarAdvertencia("Selecciona un alérgeno.");
            return;
        }

        String nombre = seleccionado.getNombre();

        if (listaAlergenos.getItems().contains(nombre)) {
        	txtAlerta.setText("Ese alérgeno ya está en la lista.");
        } else {
            listaAlergenos.getItems().add(nombre);

            if (listaTrazas.getItems().contains(nombre)) {
                listaTrazas.getItems().remove(nombre);

                if (!comboBoxTrazas.getItems().contains(seleccionado)) {
                    comboBoxTrazas.getItems().add(seleccionado);
                }
            }
        }

        comboBoxAlergenos.getSelectionModel().clearSelection();
    }

    @FXML
    private void agregarTrazasAMateria() {
        modeloAlergeno seleccionado = comboBoxTrazas.getValue();

        if (seleccionado == null) {
        	mostrarAdvertencia("Selecciona una traza de alérgeno.");
            return;
        }

        String nombre = seleccionado.getNombre();
        
        if (listaAlergenos.getItems().contains(nombre)) {
            mostrarAdvertencia("Este alérgeno ya ha sido añadido como alérgeno principal.");
            return;
        }

        if (listaTrazas.getItems().contains(nombre)) {
            mostrarAdvertencia("Esta traza ya fue añadida.");
            return;
        }

        if (!listaTrazas.getItems().contains(nombre)) {
            listaTrazas.getItems().add(nombre);
            comboBoxTrazas.getItems().remove(seleccionado);
        }

        comboBoxTrazas.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void eliminarIngredienteMateria() {
        String seleccionado = listaIngredientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaIngredientes.getItems().remove(seleccionado);

            String nombre = seleccionado.contains("(")
                    ? seleccionado.substring(0, seleccionado.indexOf(" (")).trim()
                    : seleccionado.trim();

            servicioIngredientes.findAll().stream()
                .filter(ing -> ing.getNombre().equals(nombre))
                .findFirst()
                .ifPresent(ing -> {
                    if (!comboBoxIngredientes.getItems().contains(ing)) {
                        comboBoxIngredientes.getItems().add(ing);
                    }
                });
        } else {
        	mostrarAdvertencia("No has seleccionado ningun ingrediente para eliminar");
        }
    }

    @FXML
    private void eliminarAlergenoMateria() {
        String seleccionado = listaAlergenos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaAlergenos.getItems().remove(seleccionado);

            servicioAlergenos.findAll().stream()
                .filter(al -> al.getNombre().equals(seleccionado))
                .findFirst()
                .ifPresent(al -> {
                    if (!comboBoxAlergenos.getItems().contains(al)) {
                        comboBoxAlergenos.getItems().add(al);
                    }
                    if (!comboBoxTrazas.getItems().contains(al)) {
                        comboBoxTrazas.getItems().add(al);
                    }
                });
        } else {
        	mostrarAdvertencia("No has seleccionado ningun alergeno para eliminar");
        }
    }

    @FXML
    private void eliminarTrazaMateria() {
        String seleccionado = listaTrazas.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaTrazas.getItems().remove(seleccionado);

            servicioAlergenos.findAll().stream()
                .filter(al -> al.getNombre().equals(seleccionado))
                .findFirst()
                .ifPresent(al -> {
                    if (!comboBoxTrazas.getItems().contains(al)) {
                        comboBoxTrazas.getItems().add(al);
                    }
                });
        } else {
        	mostrarAdvertencia("No has seleccionado ninguna traza de alergeno para eliminar");
        }
    }

    @FXML
    private void agregarMateriaPrima() {
        String nombre = txtNombre.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAdvertencia("El nombre de la materia prima es obligatorio.");
            return;
        }

        Double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("El precio debe ser un número.");
            return;
        }

        String unidadSeleccionada = comboBoxUnidadMedida.getValue();
        /*nombre.toLowerCase();
        if (nombre == "agua") {
        	unidadSeleccionada = "Litros";
        }*/
        
        
        modeloProveedor proveedor = comboBoxProveedor.getValue();
        if (proveedor == null) {
            mostrarAdvertencia("Selecciona un proveedor.");
            return;
        }

        MateriaPrimaCompletaDTO mat = new MateriaPrimaCompletaDTO();
        mat.setNombre(nombre);
        mat.setPrecio(precio);
        mat.setUnidadMedida(unidadSeleccionada);
        mat.setIdProveedor(proveedor.getId());

        for (NutrienteRow row : tablaValoresNutricionales.getItems()) {
            String nombreNutriente = row.getNombre().toLowerCase();
            String textoValor = row.getInput().getText().trim();
            if (textoValor.isEmpty()) continue;

            try {
                double valor = Double.parseDouble(textoValor);
                switch (nombreNutriente) {
                    case "calorías": mat.setKcal(valor); break;
                    case "hidratos": mat.setHidratos(valor); break;
                    case "azúcares": mat.setAzucares(valor); break;
                    case "grasas": mat.setGrasas(valor); break;
                    case "saturadas": mat.setSaturadas(valor); break;
                    case "proteínas": mat.setProteinas(valor); break;
                    case "sal": mat.setSal(valor); break;
                    case "fibra": mat.setFibra(valor); break;
                }
            } catch (NumberFormatException e) {
                mostrarAdvertencia("Valor no numérico en '" + row.getNombre() + "'");
                return;
            }
        }

        List<MateriaPrimaCompletaDTO.AlérgenoDTO> alergenos = new ArrayList<>(
        	    listaAlergenos.getItems().stream()
        	        .map(nombreAlergeno -> {
        	            MateriaPrimaCompletaDTO.AlérgenoDTO dto = new MateriaPrimaCompletaDTO.AlérgenoDTO();
        	            dto.setNombre(nombreAlergeno);
        	            dto.setTipo(1);
        	            return dto;
        	        }).toList()
        	);

        	List<MateriaPrimaCompletaDTO.AlérgenoDTO> trazas = listaTrazas.getItems().stream()
        	    .map(nombreTraza -> {
        	        MateriaPrimaCompletaDTO.AlérgenoDTO dto = new MateriaPrimaCompletaDTO.AlérgenoDTO();
        	        dto.setNombre(nombreTraza);
        	        dto.setTipo(2);
        	        return dto;
        	    }).toList();

        	alergenos.addAll(trazas);

        	mat.setAlergenos(alergenos);

        List<MateriaPrimaCompletaDTO.IngredienteDTO> ingredientes = listaIngredientes.getItems().stream()
            .map(texto -> {
                MateriaPrimaCompletaDTO.IngredienteDTO dto = new MateriaPrimaCompletaDTO.IngredienteDTO();
                try {
                    if (texto.contains("(") && texto.contains("%")) {
                        String nombreIng = texto.substring(0, texto.indexOf(" (")).trim();
                        String porcentajeStr = texto.substring(texto.indexOf("(") + 1, texto.indexOf("%")).trim();
                        dto.setNombre(nombreIng);
                        dto.setPorcentaje(Double.parseDouble(porcentajeStr));
                    } else {
                        dto.setNombre(texto.trim());
                        dto.setPorcentaje(null);
                    }
                } catch (Exception e) {
                    mostrarAdvertencia("Error al procesar ingrediente: " + texto);
                }
                return dto;
            }).toList();

        List<MateriaPrimaCompletaDTO.IngredienteDTO> ingredientesDefinitivos = new ArrayList<>();

        for (MateriaPrimaCompletaDTO.IngredienteDTO ingDTO : ingredientes) {
            String nombreIngrediente = ingDTO.getNombre();

            modeloIngredientes existente = servicioIngredientes.findByNombre(nombreIngrediente);
            if (existente == null) {
                modeloIngredientes nuevo = new modeloIngredientes();
                nuevo.setNombre(nombreIngrediente);
                nuevo.setPorcentaje(ingDTO.getPorcentaje());
                servicioIngredientes.save(nuevo);
            }

            ingredientesDefinitivos.add(ingDTO);
        }

        mat.setIngredientes(ingredientesDefinitivos);
        if (esEdicion) {
            mat.setId(idMateriaEditar);
            servicioMateriasPrimas.actualizarMateriaPrimaDesdeDTO(mat);
        } else {
            servicioMateriasPrimas.guardarMateriaPrimaDesdeDTO(mat);
        }


        Stage stage = (Stage) txtAlerta.getScene().getWindow();
        stage.close();
        
        try {
 			JavaFxApp.setRoot("productos");
 			JavaFxApp.setRoot("materiaprima");
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
        
        }
    
	@FXML
	private void formularioProveedor() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormAltaProveedor.fxml"));
		loader.setControllerFactory(JavaFxApp::getBean);
		Parent root = loader.load();
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Agregar nuevo Proveedor");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	

	private boolean esEdicion = false;
	private int idMateriaEditar = -1;
	
	public void setMateriaEditar(MateriaPrimaCompletaDTO materiaEditar) {
	    this.esEdicion = true;
	    this.idMateriaEditar = materiaEditar.getId();
	    this.materiaEditar = materiaEditar;
	    cargarDatosEdicion();
	}
	
	private void cargarDatosEdicion() {
	    if (materiaEditar == null) return;

	    txtNombre.setText(materiaEditar.getNombre());
	    txtPrecio.setText(String.valueOf(materiaEditar.getPrecio()));
	    comboBoxUnidadMedida.setValue(materiaEditar.getUnidadMedida());

	    modeloProveedor proveedor = servicioProveedor.findById(materiaEditar.getIdProveedor());
	    comboBoxProveedor.setValue(proveedor);

	    tablaValoresNutricionales.getItems().forEach(row -> {
	        switch (row.getNombre().toLowerCase()) {
	            case "calorías": row.getInput().setText(String.valueOf(materiaEditar.getKcal())); break;
	            case "hidratos": row.getInput().setText(String.valueOf(materiaEditar.getHidratos())); break;
	            case "azúcares": row.getInput().setText(String.valueOf(materiaEditar.getAzucares())); break;
	            case "grasas": row.getInput().setText(String.valueOf(materiaEditar.getGrasas())); break;
	            case "saturadas": row.getInput().setText(String.valueOf(materiaEditar.getSaturadas())); break;
	            case "proteínas": row.getInput().setText(String.valueOf(materiaEditar.getProteinas())); break;
	            case "sal": row.getInput().setText(String.valueOf(materiaEditar.getSal())); break;
	            case "fibra": row.getInput().setText(String.valueOf(materiaEditar.getFibra())); break;
	        }
	    });

	    materiaEditar.getAlergenos().forEach(a -> {
	        if (a.getTipo() == 1) {
	            listaAlergenos.getItems().add(a.getNombre());
	        } else if (a.getTipo() == 2) {
	            listaTrazas.getItems().add(a.getNombre());
	        }
	    });

	    materiaEditar.getIngredientes().forEach(ing -> {
	        String texto = ing.getPorcentaje() != null
	                ? ing.getNombre() + " (" + ing.getPorcentaje() + "%)"
	                : ing.getNombre();
	        listaIngredientes.getItems().add(texto);
	    });
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
    private void botonCancelar() {
        Stage stage = (Stage) txtAlerta.getScene().getWindow(); 
        stage.close();
    }
}