package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.MarcaVehiculo;

public class MarcaVehiculoDAO extends DAOGenerico {

	private static MarcaVehiculoDAO instancia;

	public static MarcaVehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new MarcaVehiculoDAO();
		}
		return instancia;
	}

	private MarcaVehiculoDAO() {
		super(MarcaVehiculo.class);
	}
}
