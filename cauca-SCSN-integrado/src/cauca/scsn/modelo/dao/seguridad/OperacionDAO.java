package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Operacion;

public class OperacionDAO extends DAOGenerico {
	private static OperacionDAO instancia;

	public static OperacionDAO getInstancia() {
		if (instancia == null) {
			instancia = new OperacionDAO();
		}
		return instancia;
	}

	private OperacionDAO() {
		super(Operacion.class);
	}
	

}