package pe.edu.upeu.CafeSnoopy.control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class MainguiController {
    @FXML
    private BorderPane bp;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;
    @FXML
    private Menu menu1, menu2 = new Menu("Cambiar Estilo");
    @FXML
    private MenuItem menuItem1, menuItem2, menuItemC;

    private ComboBox<String> comboBoxEstilo = new ComboBox<>();
    private CustomMenuItem customMenuEstilo = new CustomMenuItem(comboBoxEstilo);

    @Autowired
    protected ApplicationContext context;

    @FXML
    public void initialize() {
        comboBoxEstilo.getItems().addAll("Estilo por Defecto", "Estilo Oscuro",
                "Estilo Azul", "Estilo Verde", "Estilo Rosado");
        comboBoxEstilo.setOnAction(event -> cambiarEstilo());
        customMenuEstilo.setHideOnClick(false);
        menu2.getItems().add(customMenuEstilo);
        menuBar.getMenus().add(menu2);

        MenuListener mL = new MenuListener();
        MenuItemListener mIL = new MenuItemListener();
        menuItem1.setOnAction(mIL::handle);
        menuItem2.setOnAction(mIL::handle);
        menuItemC.setOnAction(mIL::handle);
    }

    // Métodos para manejar los botones de los módulos
    @FXML
    private void handleUsuariosButton(ActionEvent event) {
        abrirArchivoFxml("/fxml/main_usuario.fxml", "👥 Gestión de Usuarios");
    }

    @FXML
    private void handleProductosButton(ActionEvent event) {
        abrirArchivoFxml("/fxml/main_producto.fxml", "📦 Gestión de Productos");
    }

    @FXML
    private void handleVentasButton(ActionEvent event) {
        abrirArchivoFxml("/fxml/main_ventas.fxml", "💰 Sistema de Ventas");
    }

    @FXML
    private void handleReportesButton(ActionEvent event) {
        abrirArchivoFxml("/fxml/main_reportes.fxml", "📊 Reportes y Estadísticas");
    }

    @FXML
    private void handleInventarioButton(ActionEvent event) {
        abrirArchivoFxml("/fxml/main_inventario.fxml", "🔄 Control de Inventario");
    }

    public void cambiarEstilo() {
        String estilo = comboBoxEstilo.getSelectionModel().getSelectedItem();
        Scene scene = bp.getScene();
        scene.getStylesheets().clear();
        switch (estilo) {
            case "Estilo Oscuro":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-oscuro.css").toExternalForm());
                break;
            case "Estilo Azul":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());
                break;
            case "Estilo Verde":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-verde.css").toExternalForm());
                break;
            case "Estilo Rosado":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-rosado.css").toExternalForm());
                break;
            default:
                break;
        }
    }

    class MenuItemListener {
        Map<String, String[]> menuConfig = Map.of(
                "menuItem1", new String[]{"/fxml/main_asistencia.fxml", "Gestión Asistencia", "T"},
                "menuItem2", new String[]{"/fxml/main_participante.fxml", "En Proceso de Café", "T"},
                "menuItemC", new String[]{"/fxml/login.fxml", "Salir", "C"}
        );

        public void handle(ActionEvent e) {
            String id = ((MenuItem) e.getSource()).getId();
            if (menuConfig.containsKey(id)) {
                String[] items = menuConfig.get(id);
                if (items[2].equals("C")) {
                    Platform.exit();
                    System.exit(0);
                } else {
                    abrirArchivoFxml(items[0], items[1]);
                }
            }
        }
    }

    public void abrirArchivoFxml(String rutaArchivo, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(rutaArchivo));
            fxmlLoader.setControllerFactory(context::getBean);
            Parent root = fxmlLoader.load();

            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefViewportWidth(1000);
            scrollPane.setPrefViewportHeight(650);

            Tab newTab = new Tab(titulo, scrollPane);
            newTab.setClosable(true);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);

        } catch (Exception ex) {
            ex.printStackTrace();
            crearInterfazPorDefecto(titulo);
        }
    }

    private void crearInterfazPorDefecto(String titulo) {
        Label mensaje = new Label("Módulo " + titulo + " - En desarrollo...\n\n" +
                "🔧 Esta funcionalidad está en proceso de implementación.\n" +
                "📅 Próximamente disponible en una actualización futura.");
        mensaje.setStyle("-fx-font-size: 16px; -fx-padding: 20px; -fx-text-fill: #7f8c8d;");

        ScrollPane scrollPane = new ScrollPane(mensaje);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Tab newTab = new Tab(titulo, scrollPane);
        newTab.setClosable(true);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    class MenuListener {
        public void menuSelected(Event e) {
        }
    }

    // ✅ NUEVO MÉTODO PARA EL BOTÓN "MANUAL DE USUARIO"
    @FXML
    public void abrirManual(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Manual de Usuario");
        alert.setHeaderText("📘 GUÍA RÁPIDA - CAFE SNOOPY v1.0");

        // Texto completo del manual
        String contenidoManual = """
            1. 🚀 INICIO DE SESIÓN
               - Use su usuario y contraseña asignados.
               - Si falla, contacte al administrador.

            2. 🛒 VENTAS (CAJA)
               - Cliente: Escriba DNI y use el botón '🔍 RENIEC'.
               - Productos: Busque y agregue con el botón verde (+).
               - Finalizar: Presione 'PROCESAR VENTA'.
               - Ticket: Se imprime y muestra QR automáticamente.

            3. 📦 PRODUCTOS
               - El código se genera solo (PROD-XXX).
               - Llene Nombre, Precio y Stock inicial.
               - Use 'Guardar' para registrar cambios.

            4. 📊 REPORTES
               - Seleccione Fecha Inicio y Fin.
               - Click en 'Generar Reporte' para ver gráficos.
               - Use 'Exportar PDF' para guardar el archivo.

            5. 👥 USUARIOS
               - Solo el admin puede crear cuentas.
               - Marque 'Activo' para permitir el acceso.

            ❓ SOLUCIÓN RÁPIDA
               - ¿No busca DNI? Verifique su internet.
               - ¿No imprime? Revise que la impresora se llame 'POS-80'.
            """;

        TextArea textArea = new TextArea(contenidoManual);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(500, 400);

        alert.showAndWait();
    }
}