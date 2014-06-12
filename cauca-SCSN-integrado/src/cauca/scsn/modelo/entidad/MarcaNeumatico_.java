package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.562+0100")
@StaticMetamodel(MarcaNeumatico.class)
public class MarcaNeumatico_ {
	public static volatile SingularAttribute<MarcaNeumatico, Integer> idMarcaNeumatico;
	public static volatile SingularAttribute<MarcaNeumatico, Empresa> empresa;
	public static volatile SingularAttribute<MarcaNeumatico, String> nombre;
	public static volatile SingularAttribute<MarcaNeumatico, String> descripcion;
	public static volatile SingularAttribute<MarcaNeumatico, String> status;
	public static volatile SetAttribute<MarcaNeumatico, Diseno> disenos;
}
