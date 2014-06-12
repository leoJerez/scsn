package cauca.scsn.modelo.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-14T21:12:57.453-0400")
@StaticMetamodel(Neumatico.class)
public class Neumatico_ {
	public static volatile SingularAttribute<Neumatico, Integer> idNeumatico;
	public static volatile SingularAttribute<Neumatico, Empresa> empresa;
	public static volatile SingularAttribute<Neumatico, DisenoMedida> disenoMedida;
	public static volatile SingularAttribute<Neumatico, String> codInterno;
	public static volatile SingularAttribute<Neumatico, Proveedor> proveedor;
	public static volatile SingularAttribute<Neumatico, String> unidireccional;
	public static volatile SingularAttribute<Neumatico, String> condicion;
	public static volatile SingularAttribute<Neumatico, String> tipoNeumatico;
	public static volatile SingularAttribute<Neumatico, Double> remanenteIngreso;
	public static volatile SingularAttribute<Neumatico, Double> remanenteActual;
	public static volatile SingularAttribute<Neumatico, Integer> idDisenoOriginal;
	public static volatile SingularAttribute<Neumatico, Integer> idDisenoActual;
	public static volatile SingularAttribute<Neumatico, Double> recorridoAcumulado;
	public static volatile SingularAttribute<Neumatico, Double> presionActual;
	public static volatile SingularAttribute<Neumatico, String> estado;
	public static volatile SingularAttribute<Neumatico, String> status;
	public static volatile SetAttribute<Neumatico, OperacionNeumatico> operacionNeumaticos;
	public static volatile SetAttribute<Neumatico, Movimiento> movimientos;
	public static volatile SingularAttribute<Neumatico, String> nroFactura;
	public static volatile SingularAttribute<Neumatico, Double> costo;
	public static volatile SingularAttribute<Neumatico, Date> fechaIngreso;
	public static volatile SetAttribute<Neumatico, NeumaticoVehiculo> neumaticoVehiculo;
}
