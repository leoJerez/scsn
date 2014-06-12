package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.MovimientoId;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-25T09:29:58.350-0430")
@StaticMetamodel(Movimiento.class)
public class Movimiento_ {
	public static volatile SingularAttribute<Movimiento, MovimientoId> id;
	public static volatile SingularAttribute<Movimiento, TipoDesgaste> tipoDesgaste;
	public static volatile SingularAttribute<Movimiento, Neumatico> neumatico;
	public static volatile SingularAttribute<Movimiento, Vehiculo> vehiculo;
	public static volatile SingularAttribute<Movimiento, Double> recorridoAcumulado;
	public static volatile SingularAttribute<Movimiento, Double> remanenteSuperiorA;
	public static volatile SingularAttribute<Movimiento, Double> remanenteSuperiorB;
	public static volatile SingularAttribute<Movimiento, Double> remanenteSuperiorC;
	public static volatile SingularAttribute<Movimiento, Double> remanenteIzquierdoA;
	public static volatile SingularAttribute<Movimiento, Double> remanenteIzquierdoB;
	public static volatile SingularAttribute<Movimiento, Double> remanenteIzquierdoC;
	public static volatile SingularAttribute<Movimiento, Double> remanenteIzquierdoD;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDerechoA;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDerechoB;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDerechoC;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDerechoD;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDiagonalA;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDiagonalB;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDiagonalC;
	public static volatile SingularAttribute<Movimiento, Double> remanenteDiagonalD;
	public static volatile SingularAttribute<Movimiento, Double> remanenteSuperiorD;
	public static volatile SingularAttribute<Movimiento, Double> remanenteMovimiento;
	public static volatile SingularAttribute<Movimiento, Integer> posicionInicial;
	public static volatile SingularAttribute<Movimiento, Integer> posicionFinal;
	public static volatile SingularAttribute<Movimiento, Double> kilometraje;
	public static volatile SingularAttribute<Movimiento, Double> presion;
	public static volatile SingularAttribute<Movimiento, String> observaciones;
	public static volatile SingularAttribute<Movimiento, String> status;
}
