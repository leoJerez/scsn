package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Vehiculo;

public class VehiculoDAO extends DAOGenerico {

	private static VehiculoDAO instancia;

	public static VehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new VehiculoDAO();
		}
		return instancia;
	}

	private VehiculoDAO() {
		super(Vehiculo.class);
	}
}
