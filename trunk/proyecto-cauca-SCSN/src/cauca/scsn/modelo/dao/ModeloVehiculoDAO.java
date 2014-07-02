package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.ModeloVehiculo;

public class ModeloVehiculoDAO extends DAOGenerico {

	private static ModeloVehiculoDAO instancia;

	public static ModeloVehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new ModeloVehiculoDAO();
		}
		return instancia;
	}

	private ModeloVehiculoDAO() {
		super(ModeloVehiculo.class);
	}
}
