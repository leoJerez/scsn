package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-28T14:38:58.797-0430")
@StaticMetamodel(MarcaVehiculo.class)
public class MarcaVehiculo_ {
	public static volatile SingularAttribute<MarcaVehiculo, Integer> idMarcaVehiculo;
	public static volatile SingularAttribute<MarcaVehiculo, String> descripcion;
	public static volatile SingularAttribute<MarcaVehiculo, Empresa> empresa;
	public static volatile SingularAttribute<MarcaVehiculo, String> nombre;
	public static volatile SingularAttribute<MarcaVehiculo, String> status;
	public static volatile SetAttribute<MarcaVehiculo, ModeloVehiculo> modeloVehiculos;
}
