package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.609+0100")
@StaticMetamodel(Ruta.class)
public class Ruta_ {
	public static volatile SingularAttribute<Ruta, Integer> idRuta;
	public static volatile SingularAttribute<Ruta, Empresa> empresa;
	public static volatile SingularAttribute<Ruta, TipoCarretera> tipoCarretera;
	public static volatile SingularAttribute<Ruta, String> nombre;
	public static volatile SingularAttribute<Ruta, String> descripcion;
	public static volatile SingularAttribute<Ruta, String> status;
	public static volatile SetAttribute<Ruta, RutaVehiculo> rutaVehiculos;
}
