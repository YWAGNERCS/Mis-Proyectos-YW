package pe.edu.upeu.CafeSnoopy.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.CafeSnoopy.modelo.Usuario;
import pe.edu.upeu.CafeSnoopy.repositorio.UsuarioRepository;
import pe.edu.upeu.CafeSnoopy.servicio.UsuarioServicioI;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {


    @FXML private TextField txtUsername, txtNombres, txtApellidos, txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkEstado;
    @FXML private TableView<Usuario> tableUsuarios;


    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblUsuariosActivos;
    @FXML private Label lblRegistrosHoy;

    private ObservableList<Usuario> usuarios;
    private Integer idEdit = null;

    @Autowired
    private UsuarioServicioI usuarioServicio;

    @Autowired
    private UsuarioRepository usuarioRepo;


    private TableColumn<Usuario, String> colUsername, colNombres, colApellidos, colEmail;
    private TableColumn<Usuario, Void> colEstado, colAcciones;

    @FXML
    public void initialize() {
        configurarTabla();
        listarUsuarios();
        actualizarEstadisticas();
    }

    private void configurarTabla() {
        colUsername = new TableColumn<>("👤 Username");
        colNombres = new TableColumn<>("📛 Nombres");
        colApellidos = new TableColumn<>("📛 Apellidos");
        colEmail = new TableColumn<>("📧 Email");
        colEstado = new TableColumn<>("✅ Estado");
        colAcciones = new TableColumn<>("⚙️ Acciones");

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // columna Estado (Verde/Rojo)
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setText(null);
                } else {
                    boolean activo = getTableView().getItems().get(getIndex()).getEstado();
                    setText(activo ? "✅ Activo" : "❌ Inactivo");
                    setStyle(activo ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        colUsername.setPrefWidth(120);
        colNombres.setPrefWidth(150);
        colApellidos.setPrefWidth(150);
        colEmail.setPrefWidth(180);
        colEstado.setPrefWidth(100);
        colAcciones.setPrefWidth(120);

        tableUsuarios.getColumns().setAll(colUsername, colNombres, colApellidos, colEmail, colEstado, colAcciones);
    }

    @FXML
    public void guardarUsuario() {
        if (validarCampos()) {
            Usuario usuario = new Usuario();


            usuario.setUsername(txtUsername.getText().trim());
            usuario.setNombres(txtNombres.getText().trim());
            usuario.setApellidos(txtApellidos.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setEstado(chkEstado.isSelected());
            usuario.setRol("VENDEDOR");

            try {
                if (idEdit == null) {

                    usuario.setPassword(txtPassword.getText());

                    usuarioServicio.save(usuario);
                    mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                } else {

                    usuario.setIdUsuario(idEdit);


                    String nuevaPass = txtPassword.getText();
                    if (nuevaPass.isEmpty()) {
                        Optional<Usuario> actual = usuarioServicio.findById(idEdit);
                        if (actual.isPresent()) {
                            usuario.setPassword(actual.get().getPassword());

                            if(actual.get().getFechaRegistro() != null) {
                                usuario.setFechaRegistro(actual.get().getFechaRegistro());
                            }
                        }
                    } else {
                        usuario.setPassword(nuevaPass);

                        Optional<Usuario> actual = usuarioServicio.findById(idEdit);
                        actual.ifPresent(value -> usuario.setFechaRegistro(value.getFechaRegistro()));
                    }

                    usuarioServicio.update(usuario);
                    mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
                }

                limpiarFormulario();
                listarUsuarios();
                actualizarEstadisticas();

            } catch (Exception e) {
                mostrarAlerta("Error", "Error al procesar: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }


    private void actualizarEstadisticas() {
        if (usuarioRepo == null) return;

        try {
            // 1. Total Usuarios
            long total = usuarioRepo.count();
            if (lblTotalUsuarios != null) lblTotalUsuarios.setText(String.valueOf(total));

            // 2. Usuarios Activos
            long activos = usuarioRepo.countByEstado(true);
            if (lblUsuariosActivos != null) lblUsuariosActivos.setText(String.valueOf(activos));


            long nuevosHoy = usuarioRepo.countByFechaRegistro(LocalDate.now());
            if (lblRegistrosHoy != null) lblRegistrosHoy.setText(String.valueOf(nuevosHoy));

        } catch (Exception e) {
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtUsername.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "El username es obligatorio", Alert.AlertType.WARNING);
            txtUsername.requestFocus();
            return false;
        }


        if (idEdit == null && txtPassword.getText().isEmpty()) {
            mostrarAlerta("Validación", "La contraseña es obligatoria para nuevos usuarios", Alert.AlertType.WARNING);
            txtPassword.requestFocus();
            return false;
        }

        if (txtNombres.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "Los nombres son obligatorios", Alert.AlertType.WARNING);
            txtNombres.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "Los apellidos son obligatorios", Alert.AlertType.WARNING);
            txtApellidos.requestFocus();
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void limpiarFormulario() {
        txtUsername.clear();
        txtPassword.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
        chkEstado.setSelected(true);
        idEdit = null;
        txtUsername.requestFocus();
    }

    @FXML
    public void cancelarEdicion() {
        limpiarFormulario();
    }

    public void listarUsuarios() {
        List<Usuario> listaJPA = usuarioServicio.findAll();
        usuarios = FXCollections.observableArrayList(listaJPA);
        tableUsuarios.setItems(usuarios);
        agregarBotonesAcciones();
    }

    private void agregarBotonesAcciones() {
        Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Usuario, Void> call(final TableColumn<Usuario, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("✏️");
                    private final Button btnDelete = new Button("🗑️");

                    {
                        btnEdit.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5 10;");
                        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5 10;");

                        btnEdit.setOnAction(event -> {
                            Usuario usuario = getTableView().getItems().get(getIndex());
                            editarUsuario(usuario);
                        });

                        btnDelete.setOnAction(event -> {
                            eliminarUsuario(getTableView().getItems().get(getIndex()));
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5, btnEdit, btnDelete);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        };
        colAcciones.setCellFactory(cellFactory);
    }

    private void editarUsuario(Usuario usuario) {
        txtUsername.setText(usuario.getUsername());
        txtPassword.setText("");
        txtNombres.setText(usuario.getNombres());
        txtApellidos.setText(usuario.getApellidos());
        txtEmail.setText(usuario.getEmail());
        chkEstado.setSelected(usuario.getEstado());
        idEdit = usuario.getIdUsuario();
        txtUsername.requestFocus();
    }

    private void eliminarUsuario(Usuario usuario) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar a " + usuario.getNombres() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    usuarioServicio.delete(usuario.getIdUsuario());
                    mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
                    listarUsuarios();
                    actualizarEstadisticas(); // Actualizar contadores tras eliminar
                } catch (Exception e) {
                    mostrarAlerta("Error", "Error al eliminar el usuario: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
}