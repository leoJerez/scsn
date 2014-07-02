package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.RutaVehiculoId;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-12-06T08:23:47.844-0430")
@StaticMetamodel(RutaVehiculo.class)
public class RutaVehiculo_ {
	public static volatile SingularAttribute<RutaVehiculo, RutaVehiculoId> id;
	public static volatile SingularAttribute<RutaVehiculo, Vehiculo> vehiculo;
	public static volatile SingularAttribute<RutaVehiculo, Ruta> ruta;
	public static volatile SingularAttribute<RutaVehiculo, String> status;
}
