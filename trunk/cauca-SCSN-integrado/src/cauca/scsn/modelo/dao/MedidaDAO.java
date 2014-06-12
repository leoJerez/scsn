package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Medida;

public class MedidaDAO extends DAOGenerico {
	
	private static MedidaDAO instancia;

	public static MedidaDAO getInstancia() {
		if (instancia == null) {
			instancia = new MedidaDAO();
		}
		return instancia;
	}

	private MedidaDAO() {
		super(Medida.class);
	}

}
