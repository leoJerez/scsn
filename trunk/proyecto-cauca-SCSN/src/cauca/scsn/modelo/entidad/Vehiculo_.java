package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-16T22:38:19.937-0400")
@StaticMetamodel(Vehiculo.class)
public class Vehiculo_ {
	public static volatile SingularAttribute<Vehiculo, Integer> idVehiculo;
	public static volatile SingularAttribute<Vehiculo, String> anoFabricacion;
	public static volatile SingularAttribute<Vehiculo, Empresa> empresa;
	public static volatile SingularAttribute<Vehiculo, EsquemaEje> esquemaEjes;
	public static volatile SingularAttribute<Vehiculo, Double> kilometraje;
	public static volatile SingularAttribute<Vehiculo, String> placa;
	public static volatile SingularAttribute<Vehiculo, String> status;
	public static volatile SingularAttribute<Vehiculo, ModeloVehiculo> modeloVehiculo;
	public static volatile SetAttribute<Vehiculo, NeumaticoVehiculo> neumaticoVehiculo;
	public static volatile SetAttribute<Vehiculo, EjeTraccion> ejeTraccion;
}
