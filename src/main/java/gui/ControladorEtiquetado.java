package gui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProducto;
import com.java.model.modeloRelAlergeno;
import com.java.model.modeloRelConsejo;
import com.java.model.modeloRelIngrediente;
import com.java.model.modeloRelMateria;
import com.java.model.modeloRelProveedor;
import com.java.service.impl.ProductoServiceImpl;
import com.java.service.impl.RelAlergenoServiceImpl;
import com.java.service.impl.RelConsejoServiceImpl;
import com.java.service.impl.RelIngredienteServiceImpl;
import com.java.service.impl.RelMateriaServiceImpl;
import com.java.service.impl.RelProveedorServiceImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import javafx.scene.SnapshotParameters;

import javax.imageio.ImageIO;
import java.io.File;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;


@Component
public class ControladorEtiquetado implements Initializable {

    @FXML private ListView<modeloProducto> listaProductos;
    @FXML private Label labelMateriasOrdenadas, labelNomCom, labelFechaActual, labelFechaConsumo, labelHoraActual;
    @FXML private Label labelKcal, labelKJ, labelHidratos, labelAzucares, labelGrasas, labelSaturadas;
    @FXML private Label labelProteinas, labelSal, labelFibra, labelPesoProd, labelUnidades, labelPesoNeto;
    @FXML private Label labelCodBarras, labelLote, labelConsejos;
    @FXML private Pane paneEtiqueta;
    @FXML private ImageView barcodeImageView;

    @Autowired private ProductoServiceImpl servicioProducto;
    @Autowired private RelMateriaServiceImpl servicioRelMaterias;
    @Autowired private RelProveedorServiceImpl servicioRelProveedor;
    @Autowired private RelIngredienteServiceImpl servicioRelIngredientes;
    @Autowired private RelAlergenoServiceImpl servicioRelAlergenoMaterias;
    @Autowired private RelConsejoServiceImpl servicioRelConsejo;

    String nombreArchivo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaProductos.setItems(FXCollections.observableArrayList(servicioProducto.findAll()));

        listaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;

            int idProd = newVal.getId();
            labelFechaActual.setText("FECHA DE ENVASADO: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            labelFechaConsumo.setText("CONSUMIR PREFERENTEMENTE ANTES DE: " + LocalDate.now().plusMonths(6).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            labelHoraActual.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            labelNomCom.setText(newVal.getCod_prod() + " " + newVal.getNombre() + " " + (int)newVal.getPeso() + " g");

            StringBuilder alergenosContiene = new StringBuilder();
            StringBuilder trazasContiene = new StringBuilder();
            StringBuilder sb = new StringBuilder();

            double kcal = 0, hidratos = 0, azucares = 0, grasas = 0, saturadas = 0, proteinas = 0, sal = 0, fibra = 0;
            double kilostotales = 0;

            List<modeloRelMateria> materias = servicioRelMaterias.findByProductoId(idProd);
            materias.sort(Comparator.comparingDouble(modeloRelMateria::getCantidad).reversed());

            for (modeloRelMateria rel : materias) {
                modeloMateriasPrimas mat = rel.getMateriaPrima();

                modeloRelProveedor infoNutri = servicioRelProveedor.obtenerRelacionPorMateriaPrima(mat.getId());
                if (infoNutri != null) {
                    double cantidad = rel.getCantidad() * 10;
                    hidratos += convertirAGramos(infoNutri.getHidratos() * cantidad);
                    azucares += convertirAGramos(infoNutri.getAzucares() * cantidad);
                    grasas += convertirAGramos(infoNutri.getGrasas() * cantidad);
                    saturadas += convertirAGramos(infoNutri.getSaturadas() * cantidad);
                    proteinas += convertirAGramos(infoNutri.getProteinas() * cantidad);
                    sal += convertirAGramos(infoNutri.getSal() * cantidad);
                    fibra += convertirAGramos(infoNutri.getFibra() * cantidad);
                }

                kilostotales += rel.getCantidad();

                List<modeloRelAlergeno> alergenos = servicioRelAlergenoMaterias.findByMateriaPrimaId(mat.getId());
                for (modeloRelAlergeno a : alergenos) {
                	String nombreLargo = a.getAlergeno().getNombre();
                	String nombre = ALERGENOS_ABREVIADOS.getOrDefault(nombreLargo, nombreLargo).toUpperCase();
                    if (a.getCantidad() == 1 && !alergenosContiene.toString().contains(nombre)) {
                        if (!alergenosContiene.isEmpty()) alergenosContiene.append(", ");
                        alergenosContiene.append(nombre);
                    }
                    if (a.getCantidad() == 2 && !trazasContiene.toString().contains(nombre)) {
                        if (!trazasContiene.isEmpty()) trazasContiene.append(", ");
                        trazasContiene.append(nombre);
                    }
                }

                for (modeloRelIngrediente relIng : servicioRelIngredientes.findByMateriaPrimaId(mat.getId())) {
                	String nombreIngrediente = relIng.getIngrediente().getNombre();
                	for (String alergeno : alergenosM) {
                	    nombreIngrediente = nombreIngrediente.replaceAll("(?i)\\b" + alergeno.toLowerCase() + "\\b", alergeno);
                	}
                    if (!sb.toString().contains(nombreIngrediente)) {
                        if (!sb.isEmpty()) sb.append(", ");
                        sb.append(nombreIngrediente);
                    }
                }
            }

            kcal = (((grasas * 100) / kilostotales) * 9) +
                   (((hidratos * 100) / kilostotales) * 4) +
                   (((fibra * 100) / kilostotales) * 2) +
                   (((proteinas * 100) / kilostotales) * 4);

            labelKcal.setText(String.format("%.2f KCal", kcal));
            labelKJ.setText(String.format("%.2f KJ", kcal * 4.184));
            labelHidratos.setText(String.format("%.2f g", ((hidratos * 100) / kilostotales)));
            labelAzucares.setText(String.format("%.2f g", ((azucares * 100) / kilostotales)));
            labelGrasas.setText(String.format("%.2f g", ((grasas * 100) / kilostotales)));
            labelSaturadas.setText(String.format("%.2f g", ((saturadas * 100) / kilostotales)));
            labelProteinas.setText(String.format("%.2f g", ((proteinas * 100) / kilostotales)));
            labelSal.setText(String.format("%.2f g", ((sal * 100) / kilostotales)));
            labelFibra.setText(String.format("%.2f g", ((fibra * 100) / kilostotales)));

            labelPesoProd.setText((int)newVal.getPeso() + " g/Ud");
            labelUnidades.setText(String.valueOf(newVal.getUnidad_caja()) + " Unidades.");
            labelPesoNeto.setText("PESO NETO: " + newVal.getPeso_caja() + "KG");

            String contiene = alergenosContiene.toString().replaceAll(", $", "");
            String trazas = trazasContiene.toString().replaceAll(", $", "");

            if (!contiene.isEmpty()) sb.append("\nContiene: ").append(contiene);
            if (!trazas.isEmpty()) sb.append("\nPuede contener trazas de: ").append(trazas);
            labelMateriasOrdenadas.setText(sb.toString());

            String codigoFormateado = String.format("%04d", newVal.getCod_prod());
            String contenido12Digitos = "84012345" + codigoFormateado;
            labelCodBarras.setText(contenido12Digitos);

            try {
                BufferedImage buffered = BarcodeGenerator.generateEAN13BarcodeImage(contenido12Digitos, 700, 200);
                WritableImage fxImage = SwingFXUtils.toFXImage(buffered, null);
                barcodeImageView.setImage(fxImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();
            String anio = String.valueOf(fechaActual.getYear()).substring(2);
            String diaJuliano = String.format("%03d", fechaActual.getDayOfYear());
            String hora = String.format("%02d%02d", horaActual.getHour(), horaActual.getMinute());
            String cadenaFinal = anio + diaJuliano + hora;
            labelLote.setText("LOTE: " + cadenaFinal);

            // Consejos
            List<modeloRelConsejo> relConsejos = servicioRelConsejo.findByProductoId(idProd);
            StringBuilder textoConsejos = new StringBuilder();
            for (modeloRelConsejo rc : relConsejos) {
                if (!textoConsejos.isEmpty()) textoConsejos.append("\n");
                textoConsejos.append(rc.getConsejo().getConsejo());
            }
            labelConsejos.setText(textoConsejos.toString());
        });
    }

    Set<String> alergenosM = new HashSet<>(Arrays.asList(
    		 "TRIGO", "CENTENO", "CEBADA", "AVENA", "ESPELTA", "KAMUT", "SÉMOLA", "GAMBAS", "LANGOSTINOS", "CANGREJO",
    		    "HUEVO", "YEMA", "CLARA", "ALBÚMINA", "OVOPRODUCTOS", "HUEVO DESHIDRATADO", "SURIMI", "GELATINA DE PESCADO",
    		    "CACAHUETE", "SOJA", "LECHE", "MANTEQUILLA", "YOGUR", "NATA", "QUESO", "CASEÍNA", "LACTOSA",
    		    "ALMENDRAS", "NUECES", "AVELLANAS", "PISTACHOS", "ANACARDOS", "PRALINÉ", "FRUTOS SECOS", "APIO",
    		    "MOSTAZA", "SÉSAMO", "TAHINI", "FRUTAS ESCARCHADAS", "PASAS TRATADAS", "CONSERVANTES (E220-E228)",
    		    "VINAGRE DE VINO", "ALTRAMUCES", "GRANO DE ALTRAMUZ", "MEJILLÓN", "CALAMAR", "OSTRA"
    	));
    
    private double convertirAGramos(double cantidad) {
        return cantidad / 1000;
    }

    public static class BarcodeGenerator {
        public static BufferedImage generateEAN13BarcodeImage(String content, int width, int height) throws Exception {
            if (!content.matches("\\d{12}")) {
                throw new IllegalArgumentException("El contenido debe tener 12 dígitos numéricos");
            }
            Map<EncodeHintType,Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 10);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.EAN_13, width, height, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
    }

    @FXML
    private void imprimirEtiqueta() {
        Window window = paneEtiqueta.getScene().getWindow();
        imprimirPaneComoPDFConDialogo(paneEtiqueta, window);
    }

    public void imprimirPaneComoPDFConDialogo(Pane paneEtiqueta, Window window) {
        try {
            WritableImage snapshot = paneEtiqueta.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
            File imagenTemp = File.createTempFile("etiqueta", ".png");
            ImageIO.write(bufferedImage, "png", imagenTemp);
            File pdfTemp = File.createTempFile("etiqueta", ".pdf");
            PdfWriter writer = new PdfWriter(pdfTemp);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            Image img = new Image(ImageDataFactory.create(imagenTemp.getAbsolutePath()));
            img.scaleToFit(500, 800);
            document.add(img);
            document.close();

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(window)) {
                boolean success = job.printPage(paneEtiqueta);
                if (success) job.endJob();
            }

            imagenTemp.deleteOnExit();
            pdfTemp.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Map<String, String> ALERGENOS_ABREVIADOS = Map.ofEntries(
    	    Map.entry("Cereales que contengan gluten", "GLUTEN"),
    	    Map.entry("Crustáceos y productos a base de crustáceos.", "CRUSTÁCEOS"),
    	    Map.entry("Huevos y productos a base de huevo.", "HUEVO"),
    	    Map.entry("Pescado y productos a base de pescado", "PESCADO"),
    	    Map.entry("Cacahuetes y productos a base de cacahuetes", "CACAHUETES"),
    	    Map.entry("Soja y productos a base de soja", "SOJA"),
    	    Map.entry("Leche y sus derivados (incluyendo la lactosa)", "LECHE"),
    	    Map.entry("Frutos de cáscara", "FRUTOS DE CÁSCARA"),
    	    Map.entry("Apio y productos derivados.", "APIO"),
    	    Map.entry("Mostaza y productos derivados.", "MOSTAZA"),
    	    Map.entry("Granos de sésamo y productos a base de granos de s...", "SÉSAMO"),
    	    Map.entry("Dióxido de azufre y sulfitos", "SULFITOS"),
    	    Map.entry("Altramuces y productos a base de altramuces.", "ALTRAMUCES"),
    	    Map.entry("Moluscos y productos a base de moluscos.", "MOLUSCOS")
    	);

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
}
