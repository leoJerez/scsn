package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.VehiculoEmpleado;

public class VehiculoEmpleadoDAO extends DAOGenerico {

	private static VehiculoEmpleadoDAO instancia;

	public static VehiculoEmpleadoDAO getInstancia() {
		if (instancia == null) {
			instancia = new VehiculoEmpleadoDAO();
		}
		return instancia;
	}

	private VehiculoEmpleadoDAO() {
		super(VehiculoEmpleado.class);
	}
}
