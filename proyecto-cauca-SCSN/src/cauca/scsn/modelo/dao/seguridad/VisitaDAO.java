package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Visita;

public class VisitaDAO extends DAOGenerico {
	private static VisitaDAO instancia;

	public static VisitaDAO getInstancia() {
		if (instancia == null) {
			instancia = new VisitaDAO();
		}
		return instancia;
	}

	private VisitaDAO() {
		super(Visita.class);
	}
	

}
