package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Empleado;

public class EmpleadoDAO extends DAOGenerico {

	private static EmpleadoDAO instancia;

	public static EmpleadoDAO getInstancia() {
		if (instancia == null) {
			instancia = new EmpleadoDAO();
		}
		return instancia;
	}

	private EmpleadoDAO() {
		super(Empleado.class);
	}
}
