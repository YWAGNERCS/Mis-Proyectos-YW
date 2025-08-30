package pe.edu.upeu.enums;

enum FACULTAD {
    FIA,
    FCE,
    FACIHED,
    FCS,
}
public enum Carrera {
    Sistemas(FACULTAD.FIA),
    Civil(FACULTAD.FIA),
    Ambiental(FACULTAD.FIA),
    Arquitectura(FACULTAD.FIA),

    Administracion(FACULTAD.FIA),
    Contabilidad(FACULTAD.FIA);

    FACULTAD facultad;
    Carrera(FACULTAD facultad){
        this.facultad=facultad;
    }
}
