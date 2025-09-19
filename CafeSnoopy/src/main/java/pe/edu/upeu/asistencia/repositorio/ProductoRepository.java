package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.*;
import pe.edu.upeu.asistencia.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public abstract class ProductoRepository {
    protected List<Producto> productos = new ArrayList<>();

    public List<Producto> findAll() {
        if (productos.isEmpty()) {
            // Datos de ejemplo
            productos.add(new Producto(
                    new SimpleStringProperty("PROD001"),
                    new SimpleStringProperty("Brownie de Chocolate"),
                    new SimpleStringProperty("Bizcocho húmedo y compacto, elaborado con cacao puro y trozos de nuez. " +
                            "Servido tibio para resaltar su sabor intenso. Porción: 100 g"),
                    new SimpleDoubleProperty(8.00),
                    new SimpleIntegerProperty(10),
                    new SimpleBooleanProperty(true)
            ));
            productos.add(new Producto(
                    new SimpleStringProperty("PROD002"),
                    new SimpleStringProperty("Cafe Americano"),
                    new SimpleStringProperty("Clásico y aromático, preparado con unespresso y agua caliente. " +
                            "Una bebida ligera y equilibrada.180 ml"),
                    new SimpleDoubleProperty(12.00),
                    new SimpleIntegerProperty(25),
                    new SimpleBooleanProperty(true)
            ));
        }
        return productos;
    }
}