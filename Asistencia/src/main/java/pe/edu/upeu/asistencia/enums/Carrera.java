package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Carrera {
        SISTEMAS(Facultad.FIA, "Sistemas"),
        CIVIL(Facultad.FIA, "Civíl"),
        AMBIENTAL(Facultad.FIA, "Ambiental"),

        ADMINISTRACION(Facultad.FCE, "Administración"),
        CONTABILIDAD(Facultad.FCE, "Contabilidad"),

        NUTRICION(Facultad.FCS, "Nutrición"),
        FARMACIA(Facultad.FCS, "Farmacia"),

        EDUCACION(Facultad.FACIHED, "Educación"),

        GENERAL(Facultad.GENERAL, "Generál")
        ;

        private Facultad facultad;
        private String descripcion;
        @Override
        public String toString() {
            return descripcion;
        }


}
