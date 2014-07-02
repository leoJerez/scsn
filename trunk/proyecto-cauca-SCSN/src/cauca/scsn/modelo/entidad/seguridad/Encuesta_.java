package cauca.scsn.modelo.entidad.seguridad;

import cauca.scsn.modelo.entidad.EmpleadoCauca;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T10:16:02.164-0430")
@StaticMetamodel(Encuesta.class)
public class Encuesta_ {
	public static volatile SingularAttribute<Encuesta, Integer> idEncuesta;
	public static volatile SingularAttribute<Encuesta, Date> fechaEncuesta;
	public static volatile SingularAttribute<Encuesta, String> fechaVisita;
	public static volatile SingularAttribute<Encuesta, String> horaVisita;
	public static volatile SingularAttribute<Encuesta, EmpleadoCauca> empleadoCauca;
	public static volatile SingularAttribute<Encuesta, Usuario> usuario;
	public static volatile SingularAttribute<Encuesta, String> status;
	public static volatile SingularAttribute<Encuesta, String> observaciones;
	public static volatile SingularAttribute<Encuesta, String> visita;
}
