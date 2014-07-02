package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Interfaz;

public class InterfazDAO extends DAOGenerico {
	private static InterfazDAO instancia;

	public static InterfazDAO getInstancia() {
		if (instancia == null) {
			instancia = new InterfazDAO();
		}
		return instancia;
	}

	private InterfazDAO() {
		super(Interfaz.class);
	}
	

}

