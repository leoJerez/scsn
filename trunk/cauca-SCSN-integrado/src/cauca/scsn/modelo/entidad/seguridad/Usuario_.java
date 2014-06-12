package cauca.scsn.modelo.entidad.seguridad;

import cauca.scsn.modelo.entidad.EmpleadoCauca;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-02T08:43:36.578-0400")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> idUsuario;
	public static volatile SingularAttribute<Usuario, Rol> rol;
	public static volatile SingularAttribute<Usuario, String> login;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, String> status;
	public static volatile SetAttribute<Usuario, Encuesta> encuesta;
	public static volatile SetAttribute<Usuario, Log> log;
	public static volatile SetAttribute<Usuario, EmpleadoCauca> empleadoCauca;
}
