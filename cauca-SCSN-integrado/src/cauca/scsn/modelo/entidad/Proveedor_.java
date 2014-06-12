package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-26T16:49:42.156-0430")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ {
	public static volatile SingularAttribute<Proveedor, Integer> idProveedor;
	public static volatile SingularAttribute<Proveedor, Empresa> empresa;
	public static volatile SingularAttribute<Proveedor, String> nombre;
	public static volatile SingularAttribute<Proveedor, String> direccion;
	public static volatile SingularAttribute<Proveedor, String> rif;
	public static volatile SingularAttribute<Proveedor, String> telefono;
	public static volatile SingularAttribute<Proveedor, String> celular;
	public static volatile SingularAttribute<Proveedor, String> correo;
	public static volatile SingularAttribute<Proveedor, String> status;
}
