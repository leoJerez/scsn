package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-21T20:23:50.968-0400")
@StaticMetamodel(EjeTraccion.class)
public class EjeTraccion_ {
	public static volatile SingularAttribute<EjeTraccion, Integer> idEjeTraccion;
	public static volatile SingularAttribute<EjeTraccion, Vehiculo> vehiculo;
	public static volatile SingularAttribute<EjeTraccion, String> nombre;
	public static volatile SingularAttribute<EjeTraccion, String> status;
}
