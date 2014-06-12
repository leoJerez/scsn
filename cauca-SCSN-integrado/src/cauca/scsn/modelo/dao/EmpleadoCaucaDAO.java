package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.EmpleadoCauca;

public class EmpleadoCaucaDAO extends DAOGenerico{
	
	private static EmpleadoCaucaDAO instancia;
	
	public static EmpleadoCaucaDAO getInstancia() {
		if (instancia == null) {
			instancia = new EmpleadoCaucaDAO();
		}
		return instancia;
	}

	private EmpleadoCaucaDAO() {
		super(EmpleadoCauca.class);
	}

}