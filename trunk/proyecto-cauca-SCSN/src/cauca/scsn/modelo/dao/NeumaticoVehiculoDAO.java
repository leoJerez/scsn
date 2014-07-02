package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.NeumaticoVehiculo;

public class NeumaticoVehiculoDAO extends DAOGenerico {
	
	private static NeumaticoVehiculoDAO instancia;
	
	public static NeumaticoVehiculoDAO getInstancia() {
		if (instancia == null) {
			instancia = new NeumaticoVehiculoDAO();
		}
		return instancia;
	}
	
	private NeumaticoVehiculoDAO() {
		super(NeumaticoVehiculo.class);
	}
}
