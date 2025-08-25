package pe.edu.upeu.encapsulamiento;

public class Estudiante {
    private String codigo;
    private String Carrera;


    public void trabajo(){
        System.out.println("Estudiar, invetigar" + "en la carrera de: "+Carrera);
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }
}
