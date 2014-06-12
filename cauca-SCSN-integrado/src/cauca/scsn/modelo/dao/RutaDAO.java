package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Ruta;

public class RutaDAO extends DAOGenerico {

	private static RutaDAO instancia;

	public static RutaDAO getInstancia() {
		if (instancia == null) {
			instancia = new RutaDAO();
		}
		return instancia;
	}

	private RutaDAO() {
		super(Ruta.class);
	}
}
