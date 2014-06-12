package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-07-26T14:13:24.115+0100")
@StaticMetamodel(Diseno.class)
public class Diseno_ {
	public static volatile SingularAttribute<Diseno, Integer> idDiseno;
	public static volatile SingularAttribute<Diseno, MarcaNeumatico> marcaNeumatico;
	public static volatile SingularAttribute<Diseno, String> nombre;
	public static volatile SingularAttribute<Diseno, String> descripcion;
	public static volatile SingularAttribute<Diseno, byte[]> imagen;
	public static volatile SingularAttribute<Diseno, String> status;
	public static volatile SetAttribute<Diseno, DisenoMedida> disenoMedidas;
}
