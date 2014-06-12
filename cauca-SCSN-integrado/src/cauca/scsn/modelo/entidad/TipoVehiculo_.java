package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-25T09:18:14.420-0430")
@StaticMetamodel(TipoVehiculo.class)
public class TipoVehiculo_ {
	public static volatile SingularAttribute<TipoVehiculo, Integer> idTipoVehiculo;
	public static volatile SingularAttribute<TipoVehiculo, String> dentroCarretera;
	public static volatile SingularAttribute<TipoVehiculo, String> descripcion;
	public static volatile SingularAttribute<TipoVehiculo, String> nombre;
	public static volatile SingularAttribute<TipoVehiculo, String> status;
	public static volatile SetAttribute<TipoVehiculo, ModeloVehiculo> modeloVehiculos;
}
