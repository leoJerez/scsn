package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.OperacionNeumatico;

public class OperacionNeumaticoDAO extends DAOGenerico {

	private static OperacionNeumaticoDAO instancia;

	public static OperacionNeumaticoDAO getInstancia() {
		if (instancia == null) {
			instancia = new OperacionNeumaticoDAO();
		}
		return instancia;
	}

	private OperacionNeumaticoDAO() {
		super(OperacionNeumatico.class);
	}
}
