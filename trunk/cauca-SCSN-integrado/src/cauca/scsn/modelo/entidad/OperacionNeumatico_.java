package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.OperacionNeumaticoId;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.609+0100")
@StaticMetamodel(OperacionNeumatico.class)
public class OperacionNeumatico_ {
	public static volatile SingularAttribute<OperacionNeumatico, OperacionNeumaticoId> id;
	public static volatile SingularAttribute<OperacionNeumatico, CausaOperacion> causaOperacion;
	public static volatile SingularAttribute<OperacionNeumatico, Neumatico> neumatico;
	public static volatile SingularAttribute<OperacionNeumatico, String> observacion;
	public static volatile SingularAttribute<OperacionNeumatico, String> status;
}
