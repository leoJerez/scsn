package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.TipoVehiculo;

public class TipoVehiculoDAO extends DAOGenerico {

	private static TipoVehiculoDAO instancia;

	public static TipoVehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new TipoVehiculoDAO();
		}
		return instancia;
	}

	private TipoVehiculoDAO() {
		super(TipoVehiculo.class);
	}
}
