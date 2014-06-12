package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Log;

public class LogDAO extends DAOGenerico {
	private static LogDAO instancia;

	public static LogDAO getInstancia() {
		if (instancia == null) {
			instancia = new LogDAO();
		}
		return instancia;
	}

	private LogDAO() {
		super(Log.class);
	}
	

}
