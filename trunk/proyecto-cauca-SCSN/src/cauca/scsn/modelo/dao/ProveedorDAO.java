package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Proveedor;

public class ProveedorDAO extends DAOGenerico {

	private static ProveedorDAO instancia;

	public static ProveedorDAO getInstancia() {
		if (instancia == null) {
			instancia = new ProveedorDAO();
		}
		return instancia;
	}

	private ProveedorDAO() {
		super(Proveedor.class);
	}
}
