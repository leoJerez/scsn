package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.578+0100")
@StaticMetamodel(Medida.class)
public class Medida_ {
	public static volatile SingularAttribute<Medida, Integer> idMedida;
	public static volatile SingularAttribute<Medida, Empresa> empresa;
	public static volatile SingularAttribute<Medida, String> nombre;
	public static volatile SingularAttribute<Medida, String> descripcion;
	public static volatile SingularAttribute<Medida, Double> presionRecomendada;
	public static volatile SingularAttribute<Medida, String> status;
	public static volatile SetAttribute<Medida, DisenoMedida> disenoMedidas;
}
