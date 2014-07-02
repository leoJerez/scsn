package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-07-16T15:02:26.348+0100")
@StaticMetamodel(Cargo.class)
public class Cargo_ {
	public static volatile SingularAttribute<Cargo, Integer> idCargo;
	public static volatile SingularAttribute<Cargo, Empresa> empresa;
	public static volatile SingularAttribute<Cargo, String> nombre;
	public static volatile SingularAttribute<Cargo, String> descripcion;
	public static volatile SingularAttribute<Cargo, String> status;
	public static volatile SetAttribute<Cargo, Empleado> empleados;
}
