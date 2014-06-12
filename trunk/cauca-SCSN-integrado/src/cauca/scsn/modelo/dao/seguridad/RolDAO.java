package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Rol;

public class RolDAO extends DAOGenerico {
	private static RolDAO instancia;

	public static RolDAO getInstancia() {
		if (instancia == null) {
			instancia = new RolDAO();
		}
		return instancia;
	}

	private RolDAO() {
		super(Rol.class);
	}
	

}

