package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.500+0100")
@StaticMetamodel(CausaOperacion.class)
public class CausaOperacion_ {
	public static volatile SingularAttribute<CausaOperacion, Integer> idCausaOperacion;
	public static volatile SingularAttribute<CausaOperacion, String> tipoOperacionNeumatico;
	public static volatile SingularAttribute<CausaOperacion, String> nombre;
	public static volatile SingularAttribute<CausaOperacion, String> descripcion;
	public static volatile SingularAttribute<CausaOperacion, String> status;
	public static volatile SetAttribute<CausaOperacion, OperacionNeumatico> operacionNeumaticos;
}
