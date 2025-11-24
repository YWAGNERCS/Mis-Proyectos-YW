package pe.edu.upeu.CafeSnoopy.control;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.CafeSnoopy.modelo.DetalleVenta;
import pe.edu.upeu.CafeSnoopy.modelo.Venta;
import pe.edu.upeu.CafeSnoopy.repositorio.VentaRepository;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReportesController {

    // --- Componentes FXML ---
    @FXML private DatePicker dateInicio;
    @FXML private DatePicker dateFin;
    @FXML private ComboBox<String> comboTipoReporte;

    // Gráficos
    @FXML private BarChart<String, Number> barChartVentas;
    @FXML private PieChart pieChartCategorias;
    @FXML private LineChart<String, Number> lineChartTendencia;

    // Tabla de Reportes
    @FXML private TableView<Venta> tableReportes;

    @Autowired
    private VentaRepository ventaRepo;

    private ObservableList<Venta> listaVentas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("⚡ INICIANDO CONTROLADOR DE REPORTES...");

        // 1. Diagnóstico de conexión con FXML
        if (tableReportes == null) {
            System.err.println("❌ ERROR CRÍTICO: No se encuentra 'tableReportes'. Verifica el fx:id en el FXML.");
        } else {
            System.out.println("✅ Tabla conectada correctamente.");
            configurarTabla();
        }

        // 2. Configurar Combo
        if (comboTipoReporte != null) {
            comboTipoReporte.setItems(FXCollections.observableArrayList("Todas las Ventas", "Ventas > S/50"));
            comboTipoReporte.getSelectionModel().selectFirst();
        }

        // 3. Configurar Fechas por defecto (Mes actual)
        if (dateInicio != null && dateFin != null) {
            dateInicio.setValue(LocalDate.now().withDayOfMonth(1));
            dateFin.setValue(LocalDate.now());
            generarReporte(); // Cargar datos al iniciar
        }
    }

    private void configurarTabla() {
        // Columnas
        TableColumn<Venta, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idVenta"));

        TableColumn<Venta, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        ));

        TableColumn<Venta, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getNombreCliente() == null ? "General" : c.getValue().getNombreCliente()
        ));

        TableColumn<Venta, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                "S/ " + c.getValue().getTotalVenta()
        ));

        // Agregar columnas a la tabla
        tableReportes.getColumns().setAll(colId, colFecha, colCliente, colTotal);
        tableReportes.setItems(listaVentas);
    }

    @FXML
    public void generarReporte() {
        if (dateInicio.getValue() == null || dateFin.getValue() == null) {
            mostrarAlerta("Error", "Seleccione ambas fechas.");
            return;
        }

        LocalDateTime inicio = dateInicio.getValue().atStartOfDay();
        LocalDateTime fin = dateFin.getValue().atTime(LocalTime.MAX);

        try {
            // Buscar en BD
            List<Venta> resultados = ventaRepo.findByFechaVentaBetween(inicio, fin);
            System.out.println("📊 Ventas encontradas: " + resultados.size());

            // Filtrar si es necesario
            if (comboTipoReporte.getValue() != null && comboTipoReporte.getValue().contains("> S/50")) {
                resultados = resultados.stream()
                        .filter(v -> v.getTotalVenta().doubleValue() > 50)
                        .collect(Collectors.toList());
            }

            // Actualizar Tabla
            listaVentas.setAll(resultados);

            // Actualizar Gráficos
            actualizarGraficos(resultados);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Fallo al generar reporte: " + e.getMessage());
        }
    }

    private void actualizarGraficos(List<Venta> ventas) {
        if (barChartVentas == null) return;

        // 1. Barras (Ventas por Día)
        XYChart.Series<String, Number> seriesBarras = new XYChart.Series<>();
        seriesBarras.setName("Ventas");
        Map<String, Double> porDia = new HashMap<>();
        for (Venta v : ventas) {
            String fecha = v.getFechaVenta().toLocalDate().toString();
            porDia.put(fecha, porDia.getOrDefault(fecha, 0.0) + v.getTotalVenta().doubleValue());
        }
        porDia.forEach((k, v) -> seriesBarras.getData().add(new XYChart.Data<>(k, v)));
        barChartVentas.getData().clear();
        barChartVentas.getData().add(seriesBarras);

        // 2. Pie (Top Productos)
        Map<String, Integer> prodCount = new HashMap<>();
        for (Venta v : ventas) {
            for(DetalleVenta d : v.getDetalles()){
                String nombre = d.getProducto().getNombre();
                prodCount.put(nombre, prodCount.getOrDefault(nombre, 0) + d.getCantidad());
            }
        }
        pieChartCategorias.getData().clear();
        prodCount.entrySet().stream().limit(5)
                .forEach(e -> pieChartCategorias.getData().add(new PieChart.Data(e.getKey(), e.getValue())));
    }

    @FXML
    public void exportarPDF() {
        if (listaVentas.isEmpty()) {
            mostrarAlerta("Sin datos", "No hay ventas para exportar.");
            return;
        }
        try {
            String archivo = "Reporte_Snoopy_" + System.currentTimeMillis() + ".pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(archivo));
            doc.open();

            doc.add(new Paragraph("REPORTE DE VENTAS - CAFE SNOOPY"));
            doc.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
            doc.add(new Paragraph("Rango: " + dateInicio.getValue() + " al " + dateFin.getValue()));
            doc.add(new Paragraph("------------------------------------------------\n\n"));

            PdfPTable table = new PdfPTable(4);
            table.addCell("ID"); table.addCell("Fecha"); table.addCell("Cliente"); table.addCell("Total");

            for (Venta v : listaVentas) {
                table.addCell(v.getIdVenta().toString());
                table.addCell(v.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                table.addCell(v.getNombreCliente() == null ? "-" : v.getNombreCliente());
                table.addCell("S/ " + v.getTotalVenta());
            }
            doc.add(table);
            doc.close();
            mostrarAlerta("Éxito", "PDF guardado como: " + archivo);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear el PDF.");
            e.printStackTrace();
        }
    }

    @FXML
    public void exportarExcel() {
        if (listaVentas.isEmpty()) {
            mostrarAlerta("Sin datos", "No hay ventas para exportar.");
            return;
        }
        try {
            String archivo = "Reporte_" + System.currentTimeMillis() + ".csv";
            FileWriter writer = new FileWriter(archivo);
            writer.append("ID,Fecha,Cliente,Total\n");
            for (Venta v : listaVentas) {
                writer.append(v.getIdVenta() + "," + v.getFechaVenta() + "," + v.getNombreCliente() + "," + v.getTotalVenta() + "\n");
            }
            writer.close();
            mostrarAlerta("Éxito", "Excel (CSV) guardado como: " + archivo);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo exportar.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}