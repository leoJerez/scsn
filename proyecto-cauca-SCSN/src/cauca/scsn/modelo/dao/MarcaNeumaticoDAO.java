package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.MarcaNeumatico;

public class MarcaNeumaticoDAO extends DAOGenerico {

	private static MarcaNeumaticoDAO instancia;

	public static MarcaNeumaticoDAO getInstancia() {
		if (instancia == null) {
			instancia = new MarcaNeumaticoDAO();
		}
		return instancia;
	}

	private MarcaNeumaticoDAO() {
		super(MarcaNeumatico.class);
	}
}
