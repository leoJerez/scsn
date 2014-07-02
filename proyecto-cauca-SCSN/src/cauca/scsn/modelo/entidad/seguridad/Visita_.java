package cauca.scsn.modelo.entidad.seguridad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T10:16:02.212-0430")
@StaticMetamodel(Visita.class)
public class Visita_ {
	public static volatile SingularAttribute<Visita, Integer> idVisita;
	public static volatile SingularAttribute<Visita, String> fechaVisita;
	public static volatile SingularAttribute<Visita, Usuario> usuario;
	public static volatile SingularAttribute<Visita, String> status;
	public static volatile SingularAttribute<Visita, String> mac;
	public static volatile SingularAttribute<Visita, String> observaciones;
}
