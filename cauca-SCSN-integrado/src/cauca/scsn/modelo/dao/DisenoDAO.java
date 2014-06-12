package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Diseno;

public class DisenoDAO extends DAOGenerico {

	private static DisenoDAO instancia;

	public static DisenoDAO getInstancia() {
		if (instancia == null) {
			instancia = new DisenoDAO();
		}
		return instancia;
	}

	private DisenoDAO() {
		super(Diseno.class);
	}
}
