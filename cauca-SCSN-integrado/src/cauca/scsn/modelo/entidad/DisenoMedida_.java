package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.DisenoMedidaId;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-20T17:41:06.515+0100")
@StaticMetamodel(DisenoMedida.class)
public class DisenoMedida_ {
	public static volatile SingularAttribute<DisenoMedida, DisenoMedidaId> id;
	public static volatile SingularAttribute<DisenoMedida, Medida> medida;
	public static volatile SingularAttribute<DisenoMedida, Diseno> diseno;
	public static volatile SingularAttribute<DisenoMedida, String> status;
	public static volatile SetAttribute<DisenoMedida, Neumatico> neumaticos;
}
