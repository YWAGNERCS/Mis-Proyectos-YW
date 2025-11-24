package pe.edu.upeu.CafeSnoopy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.upeu.CafeSnoopy.modelo.Producto;
import pe.edu.upeu.CafeSnoopy.repositorio.ProductoRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepo;

    @Override
    public void run(String... args) throws Exception {
        // Solo llenamos si la base de datos está vacía
        if (productoRepo.count() == 0) {
            System.out.println("🚀 CARGANDO MENÚ COMPLETO DE CAFE SNOOPY...");

            List<Producto> menu = Arrays.asList(
                    // ==================== CAFÉS CALIENTES ====================
                    crear("PROD-001", "Espresso", "Café intenso y concentrado (30ml)", 5.00, 100),
                    crear("PROD-002", "Espresso Doble", "Doble carga de café intenso", 7.50, 100),
                    crear("PROD-003", "Americano", "Espresso diluido en agua caliente", 6.00, 100),
                    crear("PROD-004", "Cappuccino", "Espresso, leche vaporizada y mucha espuma", 8.50, 80),
                    crear("PROD-005", "Latte", "Café con leche suave y poca espuma", 8.50, 90),
                    crear("PROD-006", "Moccaccino", "Café, leche y salsa de chocolate", 9.50, 70),
                    crear("PROD-007", "Macchiato", "Espresso manchado con un toque de leche", 6.00, 50),
                    crear("PROD-008", "Caramel Macchiato", "Vainilla, leche, espresso y caramelo", 10.50, 60),
                    crear("PROD-009", "Flat White", "Doble espresso con capa fina de leche", 9.00, 50),
                    crear("PROD-010", "Café Bombón", "Espresso con leche condensada dulce", 8.00, 40),

                    // ==================== BEBIDAS FRÍAS / FRAPPÉS ====================
                    crear("PROD-011", "Iced Americano", "Americano servido con hielo", 7.00, 80),
                    crear("PROD-012", "Iced Latte", "Latte frío refrescante", 9.00, 80),
                    crear("PROD-013", "Frappuccino Clásico", "Café frozen con crema chantilly", 12.00, 50),
                    crear("PROD-014", "Frappuccino Mocca", "Café, chocolate, hielo y crema", 13.50, 50),
                    crear("PROD-015", "Frappuccino Caramelo", "Dulce de leche, café y crema", 13.50, 50),
                    crear("PROD-016", "Frappuccino Oreo", "Con galleta oreo, fudge y crema", 14.50, 40),
                    crear("PROD-017", "Frappuccino Fresa", "Base de crema (sin café) y fresas", 14.00, 40),
                    crear("PROD-018", "Limonada Frozen", "Limón natural con hielo granizado", 8.00, 60),
                    crear("PROD-019", "Limonada de Hierbabuena", "Frozen de limón con hierbabuena", 9.00, 50),
                    crear("PROD-020", "Jugo de Naranja", "Recién exprimido 100% natural", 9.00, 30),
                    crear("PROD-021", "Jugo de Fresa con Leche", "Batido cremoso de fresa", 10.00, 30),
                    crear("PROD-022", "Batido de Plátano", "Con leche, avena y miel", 9.50, 30),

                    // ==================== INFUSIONES Y CHOCOLATES ====================
                    crear("PROD-023", "Té Clásico", "Filtrante negro selecto", 4.00, 100),
                    crear("PROD-024", "Té Verde", "Antioxidante y natural", 4.50, 80),
                    crear("PROD-025", "Manzanilla", "Infusión relajante", 4.00, 100),
                    crear("PROD-026", "Anís", "Digestivo natural", 4.00, 100),
                    crear("PROD-027", "Té Chai Latte", "Té especiado con leche caliente", 9.00, 40),
                    crear("PROD-028", "Chocolate Caliente", "Cacao peruano artesanal", 8.00, 60),
                    crear("PROD-029", "Chocolate con Marshmallows", "Chocolate caliente con malvaviscos", 9.50, 50),

                    // ==================== PASTELERÍA Y POSTRES ====================
                    crear("PROD-030", "Torta de Chocolate", "Bizcocho húmedo con fudge casero", 10.00, 20),
                    crear("PROD-031", "Cheesecake de Fresa", "Queso crema horneado con jalea", 12.00, 15),
                    crear("PROD-032", "Cheesecake de Maracuyá", "Toque ácido y dulce", 12.00, 15),
                    crear("PROD-033", "Pie de Limón", "Base de galleta y merengue italiano", 9.00, 20),
                    crear("PROD-034", "Tres Leches", "Clásico bizcocho bañado", 9.00, 15),
                    crear("PROD-035", "Alfajor de Maicena", "Relleno de manjar blanco", 3.50, 50),
                    crear("PROD-036", "Brownie con Nueces", "Melcochudo y caliente", 5.00, 40),
                    crear("PROD-037", "Brownie con Helado", "Servido con bola de vainilla", 10.00, 30),
                    crear("PROD-038", "Muffin de Arándanos", "Keke esponjoso con fruta", 4.50, 25),
                    crear("PROD-039", "Muffin de Chocochips", "Keke de vainilla con chocolate", 4.50, 25),
                    crear("PROD-040", "Croissant de Mantequilla", "Masa hojaldre dorada", 4.00, 20),
                    crear("PROD-041", "Galleta de Avena", "Con pasas y miel", 3.00, 40),
                    crear("PROD-042", "Tiramisú", "Postre italiano con café y mascarpone", 14.00, 10),

                    // ==================== SÁNDWICHES Y SALADOS ====================
                    crear("PROD-043", "Empanada de Carne", "Horneada, relleno clásico", 5.00, 30),
                    crear("PROD-044", "Empanada de Pollo", "Relleno cremoso de pollo", 5.00, 30),
                    crear("PROD-045", "Empanada de Queso", "Masa hojaldre con queso", 5.00, 20),
                    crear("PROD-046", "Sándwich Mixto", "Jamón inglés y queso edam", 8.00, 30),
                    crear("PROD-047", "Sándwich Caprese", "Queso, tomate y albahaca", 9.00, 20),
                    crear("PROD-048", "Sándwich de Pollo", "Pollo deshilachado con apio y mayo", 9.50, 25),
                    crear("PROD-049", "Butifarra", "Jamón del país y salsa criolla", 12.00, 20),
                    crear("PROD-050", "Croissant Mixto", "Croissant relleno de jamón y queso", 9.00, 15),
                    crear("PROD-051", "Quiche de Verduras", "Pastel salado de espinaca y queso", 8.00, 10),
                    // ==================== 20 PLATOS TÍPICOS DEL PERÚ (NUEVO) ====================
                    crear("PROD-052", "Lomo Saltado", "Trozos de lomo fino, cebolla, tomate y papas fritas", 35.00, 20),
                    crear("PROD-053", "Ají de Gallina", "Pechuga deshilachada en crema de ají amarillo", 28.00, 25),
                    crear("PROD-054", "Ceviche Clásico", "Pescado fresco marinado en limón y ají", 38.00, 30),
                    crear("PROD-055", "Causa Limeña", "Masa de papa amarilla rellena de pollo y palta", 22.00, 20),
                    crear("PROD-056", "Papa a la Huancaina", "Papas sancochadas bañadas en salsa de queso", 18.00, 25),
                    crear("PROD-057", "Anticuchos", "Corazón de res a la parrilla con papas doradas", 25.00, 30),
                    crear("PROD-058", "Arroz con Pollo", "Arroz verde al culantro con presa de pollo", 26.00, 30),
                    crear("PROD-059", "Rocoto Relleno", "Rocoto horneado relleno de carne y queso", 30.00, 15),
                    crear("PROD-060", "Tacu Tacu con Lomo", "Mezcla de frijoles y arroz con lomo saltado", 38.00, 20),
                    crear("PROD-061", "Seco de Res", "Guiso de carne al culantro con frijoles", 32.00, 25),
                    crear("PROD-062", "Carapulcra con Sopa Seca", "Guiso de papa seca con fideos", 28.00, 30),
                    crear("PROD-063", "Olluquito con Carne", "Olluco picado con carne y charqui", 24.00, 20),
                    crear("PROD-064", "Juane de Gallina", "Arroz con especias envuelto en hoja de bijao", 25.00, 15),
                    crear("PROD-065", "Adobo de Cerdo", "Carne de cerdo macerada en chicha de jora", 30.00, 20),
                    crear("PROD-066", "Chupe de Camarones", "Sopa espesa con camarones, leche y queso", 40.00, 15),
                    crear("PROD-067", "Arroz Chaufa de Pollo", "Arroz frito al wok con pollo y sillao", 22.00, 40),
                    crear("PROD-068", "Tallarines Verdes", "Pasta en salsa de albahaca con bistec apanado", 30.00, 25),
                    crear("PROD-069", "Papa Rellena", "Masa de papa frita rellena de carne", 12.00, 30),
                    crear("PROD-070", "Tamal Criollo", "Maíz molido con pollo o chancho", 10.00, 30),
                    crear("PROD-071", "Caldo de Gallina", "Sopa reparadora con presa y huevo", 20.00, 40),

                    // ==================== 20 BEBIDAS PERUANAS (NUEVO) ====================
                    crear("PROD-072", "Chicha Morada (Jarra)", "Maíz morado natural con piña y canela (1L)", 15.00, 20),
                    crear("PROD-073", "Chicha Morada (Vaso)", "Vaso personal bien helada", 5.00, 50),
                    crear("PROD-074", "Inca Kola (Botella)", "La bebida de sabor nacional (500ml)", 4.50, 100),
                    crear("PROD-075", "Inca Kola (Vaso)", "Gaseosa servida con hielo", 3.50, 100),
                    crear("PROD-076", "Emoliente Caliente", "Bebida medicinal de hierbas y cebada", 4.00, 50),
                    crear("PROD-077", "Mate de Coca", "Infusión andina para la energía", 4.50, 80),
                    crear("PROD-078", "Infusión de Muña", "Digestivo natural de la sierra", 4.00, 80),
                    crear("PROD-079", "Refresco de Maracuyá", "Jugo natural de la fruta de la pasión", 6.00, 40),
                    crear("PROD-080", "Refresco de Camu Camu", "Alto contenido de vitamina C", 7.00, 30),
                    crear("PROD-081", "Refresco de Cocona", "Fruta amazónica refrescante", 7.00, 30),
                    crear("PROD-082", "Jugo de Lúcuma con Leche", "Batido cremoso de fruta de oro", 12.00, 20),
                    crear("PROD-083", "Jugo de Chirimoya", "Dulce y natural", 12.00, 20),
                    crear("PROD-084", "Pisco Sour Clásico", "Pisco, limón, jarabe y clara de huevo", 22.00, 30),
                    crear("PROD-085", "Chilcano de Pisco", "Pisco con ginger ale y limón", 18.00, 40),
                    crear("PROD-086", "Cóctel de Algarrobina", "Pisco, algarrobina y leche", 20.00, 30),
                    crear("PROD-087", "Chicha de Jora", "Bebida ancestral fermentada de maíz", 8.00, 20),
                    crear("PROD-088", "Frutillada", "Chicha de jora con fresas (Estilo Cusco)", 10.00, 20),
                    crear("PROD-089", "Ponche de Habas", "Bebida caliente y dulce", 6.00, 30),
                    crear("PROD-090", "Café Pasado Gota a Gota", "Método tradicional peruano", 7.00, 50),
                    crear("PROD-091", "Ponche de Maca", "Energizante natural caliente", 6.50, 40)
            );

            productoRepo.saveAll(menu);
            System.out.println("✅ ¡MENÚ PERUANO CARGADO! Total: " + menu.size() + " productos listos.");
        }
    }

    private Producto crear(String codigo, String nombre, String desc, double precio, int stock) {
        Producto p = new Producto();
        p.setCodigo(codigo);
        p.setNombre(nombre);
        p.setDescripcion(desc);
        p.setPrecio(BigDecimal.valueOf(precio));
        p.setStock(stock);
        p.setEstado(true);
        return p;
    }
}