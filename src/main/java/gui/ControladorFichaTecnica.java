package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Image;
import com.java.model.DatosFijosFicha;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProducto;
import com.java.model.modeloRelAlergeno;
import com.java.model.modeloRelMateria;
import com.java.model.modeloRelProveedor;
import com.java.service.impl.DatosFicTecServiceImpl;
import com.java.service.impl.ProductoServiceImpl;
import com.java.service.impl.RelAlergenoServiceImpl;
import com.java.service.impl.RelMateriaServiceImpl;
import com.java.service.impl.RelProveedorServiceImpl;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;

@Component
public class ControladorFichaTecnica implements Initializable{
	
    @FXML private GridPane gridPaneFichaTecnica, gridPaneFichaTecnica2;
    
    @FXML private ListView<modeloProducto> listaProductos;
    
    @Autowired private ProductoServiceImpl servicioProducto;
    @Autowired private RelAlergenoServiceImpl servicioRelAlergenoMaterias;
    @Autowired private RelMateriaServiceImpl servicioRelMaterias;
    @Autowired private RelProveedorServiceImpl servicioRelProveedor;
    @Autowired private DatosFicTecServiceImpl servicioFichaTec;
    
    @FXML private Label labelFechaActual, labelNomCom, labelComposicion, labelKcal, labelKJ, labelHidratos, labelAzucares,
     					labelGrasas, labelSaturadas, labelProteinas, labelSal, labelFibra, labelPesoProd, labelEtiquetado,
     					labelAlergeno1, labelAlergeno2, labelAlergeno3, labelAlergeno4, labelAlergeno5, labelAlergeno6, labelAlergeno7,
     					labelAlergeno8, labelAlergeno9, labelAlergeno10, labelAlergeno11, labelAlergeno12, labelAlergeno13, labelAlergeno14,
     					labelTraza1, labelTraza2, labelTraza3, labelTraza4, labelTraza5, labelTraza6, labelTraza7,
     					labelTraza8, labelTraza9, labelTraza10, labelTraza11, labelTraza12, labelTraza13, labelTraza14,
     					labelCondAlmac, labelTransporte, labelDurabilidad, labelProcesado, labelMicrobiolog, 
     					labelEnvasado, labelNombreEmpresaEditable, labelDireccionEditable, labelOgmEditable, labelMateriasOrdenadas,
     		            labelLoteadoEditable, labelClasifLegal, labelMarcasComerc, labelOtrosEditable, labelReglasLoteado;
    
    @FXML private TextArea txtEtiquetado;
    
    private final Map<Label, String> campoLabelMap = new HashMap();

    String nombreArchivo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaProductos.setItems(FXCollections.observableArrayList(servicioProducto.findAll()));
        DatosFijosFicha datos = servicioFichaTec.obtener();

        setLabelSiTieneValor(labelProcesado, datos.getProcesado());
        labelMicrobiolog.setText(datos.getMicrobiolog().replace("/n", "\n"));
        setLabelSiTieneValor(labelEnvasado, datos.getEnvasado());
        setLabelSiTieneValor(labelNombreEmpresaEditable, datos.getNombreEmpresa());
        setLabelSiTieneValor(labelDireccionEditable, datos.getDireccion());
        setLabelSiTieneValor(labelOgmEditable, datos.getTextoOgm());
        setLabelSiTieneValor(labelLoteadoEditable, datos.getLoteadoDescripcion());
        setLabelSiTieneValor(labelClasifLegal, datos.getClasifLegal());
        setLabelSiTieneValor(labelMarcasComerc, datos.getEtiquetadoBase());
        setLabelSiTieneValor(labelOtrosEditable, datos.getTextoOtros());
        setLabelSiTieneValor(labelReglasLoteado, datos.getReglasLoteado());

        listaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
            	alergenosContiene = new StringBuilder();
            	trazasContiene = new StringBuilder();

                limpiarLabelsAlergenos();
                int idProd = newVal.getId();
                labelFechaActual.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                labelNomCom.setText(newVal.getCod_prod() + " " + newVal.getNombre() + " " + newVal.getPeso() + " g");
                labelComposicion.setText(newVal.getComposicion());

                double kcal = 0, hidratos = 0, azucares = 0, grasas = 0, saturadas = 0, proteinas = 0, sal = 0, fibra = 0;

                double kilostotales = 0;
                List<modeloRelMateria> relaciones = servicioRelMaterias.findByProductoId(idProd);
                for (modeloRelMateria rel : relaciones) {
                    modeloMateriasPrimas mat = rel.getMateriaPrima();

                    modeloRelProveedor infoNutri = servicioRelProveedor.obtenerRelacionPorMateriaPrima(mat.getId());
                    if (infoNutri == null) continue;

                    double cantidad = rel.getCantidad() * 10;
                    
                    hidratos += convertirAGramos(infoNutri.getHidratos() * cantidad);
                    azucares += convertirAGramos(infoNutri.getAzucares() * cantidad);
                    grasas += convertirAGramos(infoNutri.getGrasas() * cantidad);
                    saturadas += convertirAGramos(infoNutri.getSaturadas() * cantidad);
                    proteinas += convertirAGramos(infoNutri.getProteinas() * cantidad);
                    sal += convertirAGramos(infoNutri.getSal() * cantidad);
                    fibra += convertirAGramos(infoNutri.getFibra() * cantidad);
                    
                    kilostotales += rel.getCantidad();
                    
                    List<modeloRelAlergeno> alergenos = servicioRelAlergenoMaterias.findByMateriaPrimaId(mat.getId());
              
                    for (modeloRelAlergeno a : alergenos) {
                        marcarLabelAlergeno(a.getAlergeno().getId(), a.getCantidad() == 1);
                    }
                }
                kcal = (((grasas*100)/kilostotales)*9) + (((hidratos*100)/kilostotales) * 4) + (((fibra*100)/kilostotales) * 2) + (((proteinas*100)/kilostotales) *4);

                Set<String> alergenos = new HashSet<>(Arrays.asList(
                	    "trigo", "centeno", "cebada", "avena", "espelta", "kamut", "sémola", "gambas", "langostinos", "cangrejo",
                	    "huevo", "yema", "clara", "albúmina", "ovoproductos", "huevo deshidratado", "surimi", "gelatina de pescado",
                	    "cacahuete", "soja", "leche", "mantequilla", "yogur", "nata", "queso", "caseína", "lactosa",
                	    "almendras", "nueces", "avellanas", "pistachos", "anacardos", "praliné", "frutos secos", "apio",
                	    "mostaza", "sésamo", "tahini", "frutas escarchadas", "pasas tratadas", "conservantes (e220-e228)",
                	    "vinagre de vino", "altramuces", "grano de altramuz", "mejillón", "calamar", "ostra"
                	));

                List<modeloRelMateria> materias = servicioRelMaterias.findByProductoId(idProd);
                materias.sort(Comparator.comparingDouble(modeloRelMateria::getCantidad).reversed());
                
                	StringBuilder sb = new StringBuilder();
                	for (modeloRelMateria rel : materias) {
                	    String nombreMateria = rel.getMateriaPrima().getNombre();

                	    // Buscar y poner en mayúsculas si contiene alguna palabra de alérgeno
                	    for (String alergeno : alergenos) {
                	        if (nombreMateria.toLowerCase().contains(alergeno)) {
                	            nombreMateria = nombreMateria.replaceAll("(?i)\\b" + alergeno + "\\b", alergeno.toUpperCase());
                	        }
                	    }

                	    if (!sb.isEmpty()) sb.append(", ");
                	    sb.append(nombreMateria);
                	}

                
                labelKcal.setText(String.format("%.2f KCal", kcal));
                labelKJ.setText(String.format("%.2f KJ", kcal * 4.184));
                labelHidratos.setText(String.format("%.2f g", ((hidratos * 100) / kilostotales)));
                labelAzucares.setText(String.format("%.2f g", ((azucares * 100) / kilostotales)));
                labelGrasas.setText(String.format("%.2f g", ((grasas * 100) / kilostotales)));
                labelSaturadas.setText(String.format("%.2f g", ((saturadas * 100) / kilostotales)));
                labelProteinas.setText(String.format("%.2f g", ((proteinas * 100) / kilostotales )));
                labelSal.setText(String.format("%.2f g", ((sal * 100) / kilostotales )));
                labelFibra.setText(String.format("%.2f g", ((fibra * 100) / kilostotales )));

                labelPesoProd.setText(newVal.getUnidad_caja() + " unidades de " + newVal.getPeso() + " g");
                labelCondAlmac.setText(newVal.getCond_almac());
                labelTransporte.setText(newVal.getTransporte());
                labelDurabilidad.setText(newVal.getDurabilidad());
                labelEtiquetado.setText(newVal.getCod_prod() + " " + newVal.getNombre() + " " + newVal.getCat_legal());
                nombreArchivo = ("fichatecnica_"+newVal.getCod_prod()+"_"+newVal.getNombre()+".pdf");
                String contiene = alergenosContiene.toString().replaceAll(", $", "");
                String trazas = trazasContiene.toString().replaceAll(", $", "");
                
                labelMateriasOrdenadas.setText(sb.toString() + "\nContiene: " + contiene + "\nPuede contener trazas de: " + trazas);

                
                campoLabelMap.put(labelOgmEditable, "textoOgm");
                campoLabelMap.put(labelNombreEmpresaEditable, "nombreEmpresa");
                campoLabelMap.put(labelDireccionEditable, "direccion");
                campoLabelMap.put(labelLoteadoEditable, "loteadoDescripcion");
                campoLabelMap.put(labelClasifLegal, "clasifLegal");
                campoLabelMap.put(labelMarcasComerc, "etiquetadoBase");
                campoLabelMap.put(labelOtrosEditable, "textoOtros");
                campoLabelMap.put(labelReglasLoteado, "reglasLoteado");
                campoLabelMap.put(labelProcesado, "procesado");
                campoLabelMap.put(labelEnvasado, "envasado");
                campoLabelMap.put(labelMicrobiolog, "microbiolog");

            }
        });
    }
    
	
    @FXML
    private void exportarFichaTecnica(){
        try {
            // Abrir FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
            
            fileChooser.setInitialFileName(nombreArchivo);

            // Obtener ventana actual desde cualquier GridPane
            Stage stage = (Stage) gridPaneFichaTecnica.getScene().getWindow();
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile == null) {
                return; // Usuario canceló
            }

            double scaleFactor = 3.0;
            SnapshotParameters params = new SnapshotParameters();
            params.setTransform(javafx.scene.transform.Transform.scale(scaleFactor, scaleFactor));

            // Snapshot de los dos GridPane
            WritableImage highResImage1 = new WritableImage(
                (int) ((gridPaneFichaTecnica.getWidth() + 20) * scaleFactor),
                (int) ((gridPaneFichaTecnica.getHeight() + 20) * scaleFactor)
            );
            gridPaneFichaTecnica.snapshot(params, highResImage1);

            WritableImage highResImage2 = new WritableImage(
                (int) ((gridPaneFichaTecnica2.getWidth() + 20) * scaleFactor),
                (int) ((gridPaneFichaTecnica2.getHeight() + 20) * scaleFactor)
            );
            gridPaneFichaTecnica2.snapshot(params, highResImage2);

            // Convertir imágenes a bytes en memoria
            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(highResImage1, null), "png", outputStream1);
            byte[] imageBytes1 = outputStream1.toByteArray();

            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(highResImage2, null), "png", outputStream2);
            byte[] imageBytes2 = outputStream2.toByteArray();

            // Crear PDF en la ubicación seleccionada
            PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Image img1 = new Image(ImageDataFactory.create(imageBytes1));
            Image img2 = new Image(ImageDataFactory.create(imageBytes2));

            img1.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight() - 70);
            img2.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight() - 70);

            document.add(img1);
            document.add(new com.itextpdf.layout.element.AreaBreak()); // Página nueva
            document.add(img2);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private double convertirAGramos(double cantidad) {
                return cantidad / 1000;
    }
    
    private void appendWithComma(StringBuilder sb, String text) {
        if (sb.length() > 0) sb.append(", ");
        sb.append(text);
    }

    StringBuilder alergenosContiene, trazasContiene;

	private void marcarLabelAlergeno(int id, boolean esAlergeno) {
		switch (id) {
		case 1:
			if (esAlergeno) {
				labelAlergeno1.setText("X");
				if (!alergenosContiene.toString().contains("GLUTEN")) {
				appendWithComma(alergenosContiene, "GLUTEN");
				}
			} else {
				labelTraza1.setText("X");
				if (!trazasContiene.toString().contains("GLUTEN")) {
				appendWithComma(trazasContiene, "GLUTEN");
				}
			}
			break;
		case 2:
			if (esAlergeno) {
				labelAlergeno2.setText("X");
				if (!alergenosContiene.toString().contains("CRUSTÁCEOS")) {
				appendWithComma(alergenosContiene, "CRUSTÁCEOS");
				}
			} else {
				labelTraza2.setText("X");
				if (!trazasContiene.toString().contains("CRUSTÁCEOS")) {
				appendWithComma(trazasContiene, "CRUSTÁCEOS");
				}
			}
			break;
		case 3:
			if (esAlergeno) {
				labelAlergeno3.setText("X");
				if (!alergenosContiene.toString().contains("HUEVO")) {
					appendWithComma(alergenosContiene, "HUEVO");
					}
			} else {
				labelTraza3.setText("X");
				if (!trazasContiene.toString().contains("HUEVO")) {
					appendWithComma(trazasContiene, "HUEVO");
					}
			}
			break;
		case 4:
			if (esAlergeno) {
				labelAlergeno4.setText("X");
				if (!alergenosContiene.toString().contains("PESCADO")) {
					appendWithComma(alergenosContiene, "PESCADO");
					}
			} else {
				labelTraza4.setText("X");
				if (!trazasContiene.toString().contains("PESCADO")) {
					appendWithComma(trazasContiene, "PESCADO");
					}
			}
			break;
		case 5:
			if (esAlergeno) {
				labelAlergeno5.setText("X");
				if (!alergenosContiene.toString().contains("CACAHUETES")) {
					appendWithComma(alergenosContiene, "CACAHUETES");
					}
			} else {
				labelTraza5.setText("X");
				if (!trazasContiene.toString().contains("CACAHUETES")) {
					appendWithComma(trazasContiene, "CACAHUETES");
					}
			}
			break;
		case 6:
			if (esAlergeno) {
				labelAlergeno6.setText("X");
				if (!alergenosContiene.toString().contains("SOJA")) {
					appendWithComma(alergenosContiene, "SOJA");
					}
			} else {
				labelTraza6.setText("X");
				if (!trazasContiene.toString().contains("SOJA")) {
					appendWithComma(trazasContiene, "SOJA");
					}
			}
			break;
		case 7:
			if (esAlergeno) {
				labelAlergeno7.setText("X");
				if (!alergenosContiene.toString().contains("LECHE")) {
					appendWithComma(alergenosContiene, "LECHE");
					}
			} else {
				labelTraza7.setText("X");
				if (!trazasContiene.toString().contains("LECHE")) {
					appendWithComma(trazasContiene, "LECHE");
					}
			}
			break;
		case 8:
			if (esAlergeno) {
				labelAlergeno8.setText("X");
				if (!alergenosContiene.toString().contains("FRUTOS DE CÁSCARA")) {
					appendWithComma(alergenosContiene, "FRUTOS DE CÁSCARA");
					}
			} else {
				labelTraza8.setText("X");
				if (!trazasContiene.toString().contains("FRUTOS DE CÁSCARA")) {
					appendWithComma(trazasContiene, "FRUTOS DE CÁSCARA");
					}
			}
			break;
		case 9:
			if (esAlergeno) {
				labelAlergeno9.setText("X");
				if (!alergenosContiene.toString().contains("APIO")) {
					appendWithComma(alergenosContiene, "APIO");
					}
			} else {
				labelTraza9.setText("X");
				if (!trazasContiene.toString().contains("APIO")) {
					appendWithComma(trazasContiene, "APIO");
					}
			}
			break;
		case 10:
			if (esAlergeno) {
				labelAlergeno10.setText("X");
				if (!alergenosContiene.toString().contains("MOSTAZA")) {
					appendWithComma(alergenosContiene, "MOSTAZA");
					}
			} else {
				labelTraza10.setText("X");
				if (!trazasContiene.toString().contains("MOSTAZA")) {
					appendWithComma(trazasContiene, "MOSTAZA");
					}
			}
			break;
		case 11:
			if (esAlergeno) {
				labelAlergeno11.setText("X");
				if (!alergenosContiene.toString().contains("SÉSAMO")) {
					appendWithComma(alergenosContiene, "SÉSAMO");
					}
			} else {
				labelTraza11.setText("X");
				if (!trazasContiene.toString().contains("SÉSAMO")) {
					appendWithComma(trazasContiene, "SÉSAMO");
					}
			}
			break;
		case 12:
			if (esAlergeno) {
				labelAlergeno12.setText("X");
				if (!alergenosContiene.toString().contains("SULFITOS")) {
					appendWithComma(alergenosContiene, "SULFITOS");
					}
			} else {
				labelTraza12.setText("X");
				if (!trazasContiene.toString().contains("SULFITOS")) {
					appendWithComma(trazasContiene, "SULFITOS");
					}
			}
			break;
		case 13:
			if (esAlergeno) {
				labelAlergeno13.setText("X");
				if (!alergenosContiene.toString().contains("ALTRAMUCES")) {
					appendWithComma(alergenosContiene, "ALTRAMUCES");
					}
			} else {
				labelTraza13.setText("X");
				if (!trazasContiene.toString().contains("ALTRAMUCES")) {
					appendWithComma(trazasContiene, "ALTRAMUCES");
					}
			}
			break;
		case 14:
			if (esAlergeno) {
				labelAlergeno14.setText("X");
				if (!alergenosContiene.toString().contains("MOLUSCOS")) {
					appendWithComma(alergenosContiene, "MOLUSCOS");
					}
			} else {
				labelTraza14.setText("X");
				if (!trazasContiene.toString().contains("MOLUSCOS")) {
					appendWithComma(trazasContiene, "MOLUSCOS");
					}
			}
			break;
		default:
			break;
		}
	}

    private void limpiarLabelsAlergenos() {
        for (int i = 1; i <= 14; i++) {
            getLabelByName("labelAlergeno" + i).setText(" ");
            getLabelByName("labelTraza" + i).setText(" ");
        }
    }

    private Label getLabelByName(String name) {
        try {
            return (Label) this.getClass().getDeclaredField(name).get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return new Label();
        }
    }
    
    private String obtenerCampoBaseDatos(Label label) {
        return campoLabelMap.getOrDefault(label, "campo_desconocido");
    }

    @FXML
    private void abrirEditorTexto(javafx.scene.input.MouseEvent event) {
        Label label = (Label) event.getSource();
        String campoBaseDatos = obtenerCampoBaseDatos(label);

        TextInputDialog dialog = new TextInputDialog(label.getText());
        dialog.setTitle("Editar campo");
        dialog.setHeaderText("Modificar " + campoBaseDatos);
        dialog.setContentText("Nuevo valor:");

        dialog.showAndWait().ifPresent(nuevoTexto -> {
            servicioFichaTec.actualizarCampo(campoBaseDatos, nuevoTexto);
            label.setText(nuevoTexto);
        });
    }

    
    private void setLabelSiTieneValor(Label label, String valorBBDD) {
        if (valorBBDD != null && !valorBBDD.trim().isEmpty()) {
            label.setText(valorBBDD);
        }
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
	private void Materias() throws Exception {
		JavaFxApp.setRoot("materiaprima");
	}
	
	@FXML
	private void Etiquetado() throws Exception {
		JavaFxApp.setRoot("etiquetado");
	}

    @FXML
    private void CerrarApp() {
        Platform.exit();
    }

    @FXML
    private void CerrarSesion() throws Exception {
        JavaFxApp.setRoot("Inicio");
    }
}
