package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Encuesta;

public class EncuestaDAO extends DAOGenerico {
	private static EncuestaDAO instancia;

	public static EncuestaDAO getInstancia() {
		if (instancia == null) {
			instancia = new EncuestaDAO();
		}
		return instancia;
	}

	private EncuestaDAO() {
		super(Encuesta.class);
	}
	

}
