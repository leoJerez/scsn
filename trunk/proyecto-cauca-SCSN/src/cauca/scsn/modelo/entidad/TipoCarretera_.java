package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.609+0100")
@StaticMetamodel(TipoCarretera.class)
public class TipoCarretera_ {
	public static volatile SingularAttribute<TipoCarretera, Integer> idTipoCarretera;
	public static volatile SingularAttribute<TipoCarretera, String> nombre;
	public static volatile SingularAttribute<TipoCarretera, String> descripcion;
	public static volatile SingularAttribute<TipoCarretera, String> status;
	public static volatile SetAttribute<TipoCarretera, Ruta> rutas;
}
