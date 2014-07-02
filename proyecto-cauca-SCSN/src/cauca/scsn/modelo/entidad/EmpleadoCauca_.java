package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T10:16:02.093-0430")
@StaticMetamodel(EmpleadoCauca.class)
public class EmpleadoCauca_ {
	public static volatile SingularAttribute<EmpleadoCauca, String> cedulaEmpleado;
	public static volatile SingularAttribute<EmpleadoCauca, Usuario> usuario;
	public static volatile SingularAttribute<EmpleadoCauca, String> status;
	public static volatile SetAttribute<EmpleadoCauca, Encuesta> encuesta;
	public static volatile SetAttribute<EmpleadoCauca, Empresa> empresa;
	public static volatile SingularAttribute<EmpleadoCauca, String> apellido;
	public static volatile SingularAttribute<EmpleadoCauca, String> celular;
	public static volatile SingularAttribute<EmpleadoCauca, String> correo;
	public static volatile SingularAttribute<EmpleadoCauca, String> direccion;
	public static volatile SingularAttribute<EmpleadoCauca, String> nombre;
}
