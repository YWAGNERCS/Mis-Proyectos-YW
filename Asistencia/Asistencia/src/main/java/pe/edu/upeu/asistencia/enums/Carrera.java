package pe.edu.upeu.asistencia.enums;

public enum Carrera {
        SISTEMAS(Facultad.FIA),
        CIVIL(Facultad.FIA),
        AMBIENTAL(Facultad.FIA),

        ADMINISTRACION(Facultad.FCE),
        CONTABILIDAD(Facultad.FCE),

        NUTRICION(Facultad.FCS),
        FARMACIA(Facultad.FCS),

        EDUCACION(Facultad.FACIHED),

        GENERAL(Facultad.GENERAL)
        ;

        private Facultad facultad;

        Carrera(Facultad facultad){
            this.facultad=facultad;
        }

        public Facultad getFacultad() {
            return facultad;
        }
}
