package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.NeumaticoVehiculoID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-31T08:22:11.718-0400")
@StaticMetamodel(NeumaticoVehiculo.class)
public class NeumaticoVehiculo_ {
	public static volatile SingularAttribute<NeumaticoVehiculo, NeumaticoVehiculoID> id;
	public static volatile SingularAttribute<NeumaticoVehiculo, Neumatico> neumatico;
	public static volatile SingularAttribute<NeumaticoVehiculo, Vehiculo> vehiculo;
	public static volatile SingularAttribute<NeumaticoVehiculo, String> posicion;
	public static volatile SingularAttribute<NeumaticoVehiculo, String> status;
}
