package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Movimiento;

public class MovimientoDAO extends DAOGenerico {

	private static MovimientoDAO instancia;

	public static MovimientoDAO getInstancia() {
		if (instancia == null) {
			instancia = new MovimientoDAO();
		}
		return instancia;
	}

	private MovimientoDAO() {
		super(Movimiento.class);
	}
}
