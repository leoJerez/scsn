package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.RutaVehiculo;

public class RutaVehiculoDAO extends DAOGenerico {

	private static RutaVehiculoDAO instancia;

	public static RutaVehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new RutaVehiculoDAO();
		}
		return instancia;
	}

	private RutaVehiculoDAO() {
		super(RutaVehiculo.class);
	}
}
