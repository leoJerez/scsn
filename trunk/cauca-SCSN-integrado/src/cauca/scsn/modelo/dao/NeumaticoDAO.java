package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Neumatico;

public class NeumaticoDAO extends DAOGenerico {

	private static NeumaticoDAO instancia;

	public static NeumaticoDAO getInstancia() {
		if (instancia == null) {
			instancia = new NeumaticoDAO();
		}
		return instancia;
	}

	private NeumaticoDAO() {
		super(Neumatico.class);
	}
}
