package pe.edu.upeu.CafeSnoopy.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.CafeSnoopy.modelo.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    // ✅ MÉTODO CLAVE PARA REPORTES
    // Busca ventas dentro de un rango de fechas (Inicio a Fin)
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // ✅ MÉTODOS PARA EL DASHBOARD (MÉTRICAS EN TIEMPO REAL)
    // Estas consultas están optimizadas para SQLite

    // 1. Suma total de dinero vendido HOY
    // Usamos COALESCE para que devuelva 0 si no hay ventas, en lugar de null
    @Query(value = "SELECT COALESCE(SUM(total_venta), 0) FROM Venta WHERE date(fecha_venta) = date('now')", nativeQuery = true)
    BigDecimal sumaVentasHoy();

    // 2. Cantidad de ventas (tickets) emitidos HOY
    @Query(value = "SELECT COUNT(*) FROM Venta WHERE date(fecha_venta) = date('now')", nativeQuery = true)
    Integer contarVentasHoy();
}