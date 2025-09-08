package pe.edu.upeu.asistencia.modelo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import lombok.*;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Setter
@Getter
public class Participante {
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty dni;
    private Carrera carrera;
    private TipoParticipante tipoParticipante;
    private BooleanProperty estado;





}
