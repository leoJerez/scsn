package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-03T14:23:59.125-0400")
@StaticMetamodel(Empresa.class)
public class Empresa_ {
	public static volatile SingularAttribute<Empresa, Integer> idEmpresa;
	public static volatile SingularAttribute<Empresa, String> rif;
	public static volatile SingularAttribute<Empresa, String> nombre;
	public static volatile SingularAttribute<Empresa, String> direccion;
	public static volatile SingularAttribute<Empresa, String> telefono;
	public static volatile SingularAttribute<Empresa, String> celular;
	public static volatile SingularAttribute<Empresa, String> fax;
	public static volatile SingularAttribute<Empresa, String> correo;
	public static volatile SingularAttribute<Empresa, Double> remanenteRetiro;
	public static volatile SingularAttribute<Empresa, String> status;
	public static volatile SetAttribute<Empresa, Ruta> rutas;
	public static volatile SetAttribute<Empresa, Vehiculo> vehiculos;
	public static volatile SetAttribute<Empresa, MarcaVehiculo> marcaVehiculos;
	public static volatile SetAttribute<Empresa, Empleado> empleados;
	public static volatile SetAttribute<Empresa, Cargo> cargos;
	public static volatile SetAttribute<Empresa, Medida> medidas;
	public static volatile SetAttribute<Empresa, Neumatico> neumaticos;
	public static volatile SetAttribute<Empresa, MarcaNeumatico> marcaNeumaticos;
	public static volatile SetAttribute<Empresa, Proveedor> proveedors;
	public static volatile SetAttribute<Empresa, EsquemaEje> esquemaEjes;
	public static volatile SingularAttribute<Empresa, EmpleadoCauca> empleadoCauca;
}
