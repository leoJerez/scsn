package cauca.scsn.modelo.entidad.seguridad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-05-27T10:41:05.375-0400")
@StaticMetamodel(Rol.class)
public class Rol_ {
	public static volatile SingularAttribute<Rol, Integer> idRol;
	public static volatile SingularAttribute<Rol, String> nombre;
	public static volatile SingularAttribute<Rol, String> status;
	public static volatile SetAttribute<Rol, OperacionRol> interfazRols;
}
