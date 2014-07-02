package cauca.scsn.modelo.entidad.seguridad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-05-07T15:39:02.125-0400")
@StaticMetamodel(Interfaz.class)
public class Interfaz_ {
	public static volatile SingularAttribute<Interfaz, Integer> idInterfaz;
	public static volatile SingularAttribute<Interfaz, String> nombreInterfaz;
	public static volatile SingularAttribute<Interfaz, String> status;
	public static volatile SetAttribute<Interfaz, Operacion> operacion;
}
