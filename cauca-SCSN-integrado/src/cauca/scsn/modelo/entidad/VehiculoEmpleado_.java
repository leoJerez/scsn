package cauca.scsn.modelo.entidad;

import cauca.scsn.modelo.entidad.id.VehiculoEmpleadoId;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-12-06T11:18:30.349-0430")
@StaticMetamodel(VehiculoEmpleado.class)
public class VehiculoEmpleado_ {
	public static volatile SingularAttribute<VehiculoEmpleado, VehiculoEmpleadoId> id;
	public static volatile SingularAttribute<VehiculoEmpleado, Vehiculo> vehiculo;
	public static volatile SingularAttribute<VehiculoEmpleado, Empleado> empleado;
	public static volatile SingularAttribute<VehiculoEmpleado, String> conductorPrincipal;
	public static volatile SingularAttribute<VehiculoEmpleado, String> temporal;
	public static volatile SingularAttribute<VehiculoEmpleado, Date> fechaInicial;
	public static volatile SingularAttribute<VehiculoEmpleado, Date> fechaFinal;
	public static volatile SingularAttribute<VehiculoEmpleado, String> motivo;
	public static volatile SingularAttribute<VehiculoEmpleado, String> status;
}
