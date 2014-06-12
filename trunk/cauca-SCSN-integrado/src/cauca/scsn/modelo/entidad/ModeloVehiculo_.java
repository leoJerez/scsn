package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-28T11:02:10.637-0430")
@StaticMetamodel(ModeloVehiculo.class)
public class ModeloVehiculo_ {
	public static volatile SingularAttribute<ModeloVehiculo, Integer> idModeloVehiculo;
	public static volatile SingularAttribute<ModeloVehiculo, String> descripcion;
	public static volatile SingularAttribute<ModeloVehiculo, String> nombre;
	public static volatile SingularAttribute<ModeloVehiculo, String> status;
	public static volatile SingularAttribute<ModeloVehiculo, MarcaVehiculo> marcaVehiculo;
	public static volatile SingularAttribute<ModeloVehiculo, TipoVehiculo> tipoVehiculo;
	public static volatile SetAttribute<ModeloVehiculo, Vehiculo> vehiculos;
}
