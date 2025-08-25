package pe.edu.upeu.encapsulamiento;

public class ClaseGeneral {
    public static void probarJugador(){
        Jugador jugador=new Jugador();

        jugador.setNombre("Messi");
        jugador.setApellido ("Argentino");
        jugador.setEdad (25);
        jugador.setPosicion("Delantero");
        jugador.setNumeroCam (10);
        System.out.println(jugador);


    }

    public static void probar(){
        Estudiante estudiante = new Estudiante();
        estudiante.setCarrera("Ing. Sistemas");
        estudiante.setCodigo("202510050");
        estudiante.trabajo();
    }


    public static void main(String[] args){
        Persona persona = new Persona(); //objeto
        persona.setNombre("wagner");
        persona.setEdad(18);
        persona.setGenero('M');
        persona.saludo();
        System.out.println("Genero"+persona.getGenero());

        probar();
        probarJugador();
    }
}
