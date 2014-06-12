package cauca.scsn.modelo.entidad.seguridad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-01T09:57:33.421-0400")
@StaticMetamodel(Log.class)
public class Log_ {
	public static volatile SingularAttribute<Log, Integer> idLog;
	public static volatile SingularAttribute<Log, Date> fechaUltimoIngreso;
	public static volatile SingularAttribute<Log, Usuario> usuario;
	public static volatile SingularAttribute<Log, String> status;
	public static volatile SingularAttribute<Log, String> mac;
}
