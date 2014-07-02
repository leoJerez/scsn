package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.CausaOperacion;

public class CausaOperacionDAO extends DAOGenerico {

	private static CausaOperacionDAO instancia;

	public static CausaOperacionDAO getInstancia() {
		if (instancia == null) {
			instancia = new CausaOperacionDAO();
		}
		return instancia;
	}

	private CausaOperacionDAO() {
		super(CausaOperacion.class);
	}
}
