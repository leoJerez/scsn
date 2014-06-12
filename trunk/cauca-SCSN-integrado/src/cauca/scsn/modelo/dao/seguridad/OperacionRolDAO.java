package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.OperacionRol;

public class OperacionRolDAO extends DAOGenerico {
	private static OperacionRolDAO instancia;

	public static OperacionRolDAO getInstancia() {
		if (instancia == null) {
			instancia = new OperacionRolDAO();
		}
		return instancia;
	}

	private OperacionRolDAO() {
		super(OperacionRol.class);
	}
	

}
