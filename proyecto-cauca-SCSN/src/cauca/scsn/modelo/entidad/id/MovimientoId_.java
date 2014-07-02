package cauca.scsn.modelo.entidad.id;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-03T16:40:24.000-0400")
@StaticMetamodel(MovimientoId.class)
public class MovimientoId_ {
	public static volatile SingularAttribute<MovimientoId, Integer> idVehiculo;
	public static volatile SingularAttribute<MovimientoId, Integer> idNeumatico;
	public static volatile SingularAttribute<MovimientoId, String> tipoMovimiento;
	public static volatile SingularAttribute<MovimientoId, Date> fecha;
}
