package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Cargo;

public class CargoDAO extends DAOGenerico {

	private static CargoDAO instancia;

	public static CargoDAO getInstancia() {
		if (instancia == null) {
			instancia = new CargoDAO();
		}
		return instancia;
	}

	private CargoDAO() {
		super(Cargo.class);
	}
}
