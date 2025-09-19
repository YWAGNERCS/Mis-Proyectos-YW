package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public abstract class UsuarioRepository {
    protected List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> findAll() {
        if (usuarios.isEmpty()) {
            // Datos de ejemplo
            usuarios.add(new Usuario(
                    new SimpleStringProperty("admin"),
                    new SimpleStringProperty("admin123"),
                    new SimpleStringProperty("Juan"),
                    new SimpleStringProperty("Pérez"),
                    new SimpleStringProperty("juan@upeu.edu.pe"),
                    new SimpleBooleanProperty(true)
            ));
            usuarios.add(new Usuario(
                    new SimpleStringProperty("maria"),
                    new SimpleStringProperty("maria123"),
                    new SimpleStringProperty("María"),
                    new SimpleStringProperty("Gómez"),
                    new SimpleStringProperty("maria@upeu.edu.pe"),
                    new SimpleBooleanProperty(true)
            ));
        }
        return usuarios;
    }
}