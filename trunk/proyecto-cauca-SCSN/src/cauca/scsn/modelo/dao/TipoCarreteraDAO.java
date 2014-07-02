package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.TipoCarretera;

public class TipoCarreteraDAO extends DAOGenerico {

	private static TipoCarreteraDAO instancia;

	public static TipoCarreteraDAO getInstancia() {
		if (instancia == null) {
			instancia = new TipoCarreteraDAO();
		}
		return instancia;
	}

	private TipoCarreteraDAO() {
		super(TipoCarretera.class);
	}
}
