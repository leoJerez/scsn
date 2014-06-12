package cauca.scsn.modelo.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-09-30T17:19:49.606-0430")
@StaticMetamodel(TipoDesgaste.class)
public class TipoDesgaste_ {
	public static volatile SingularAttribute<TipoDesgaste, Integer> idTipoDesgaste;
	public static volatile SingularAttribute<TipoDesgaste, String> nombre;
	public static volatile SingularAttribute<TipoDesgaste, String> descripcion;
	public static volatile SingularAttribute<TipoDesgaste, String> causa;
	public static volatile SingularAttribute<TipoDesgaste, String> accionesCorrectivas;
	public static volatile SingularAttribute<TipoDesgaste, byte[]> imagen;
	public static volatile SingularAttribute<TipoDesgaste, String> status;
	public static volatile SetAttribute<TipoDesgaste, Movimiento> movimientos;
}
