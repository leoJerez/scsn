package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.seguridad.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-02T08:58:44.953-0400")
@StaticMetamodel(Empleado.class)
public class Empleado_ {
	public static volatile SingularAttribute<Empleado, String> cedulaEmpleado;
	public static volatile SingularAttribute<Empleado, Empresa> empresa;
	public static volatile SingularAttribute<Empleado, Cargo> cargo;
	public static volatile SingularAttribute<Empleado, String> nombre;
	public static volatile SingularAttribute<Empleado, String> apellido;
	public static volatile SingularAttribute<Empleado, String> direccion;
	public static volatile SingularAttribute<Empleado, String> telefono;
	public static volatile SingularAttribute<Empleado, String> celular;
	public static volatile SingularAttribute<Empleado, String> correo;
	public static volatile SingularAttribute<Empleado, Usuario> usuario;
	public static volatile SingularAttribute<Empleado, String> status;
	public static volatile SetAttribute<Empleado, VehiculoEmpleado> vehiculoEmpleados;
}
