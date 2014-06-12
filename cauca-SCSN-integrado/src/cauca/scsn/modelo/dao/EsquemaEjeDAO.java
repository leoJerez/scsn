package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.EsquemaEje;

public class EsquemaEjeDAO extends DAOGenerico {

	private static EsquemaEjeDAO instancia;

	public static EsquemaEjeDAO getInstancia() {
		if (instancia == null) {
			instancia = new EsquemaEjeDAO();
		}
		return instancia;
	}

	private EsquemaEjeDAO() {
		super(EsquemaEje.class);
	}
}
