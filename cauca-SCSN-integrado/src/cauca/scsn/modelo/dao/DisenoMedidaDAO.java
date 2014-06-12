package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.DisenoMedida;

public class DisenoMedidaDAO extends DAOGenerico {

	private static DisenoMedidaDAO instancia;

	public static DisenoMedidaDAO getInstancia() {
		if (instancia == null) {
			instancia = new DisenoMedidaDAO();
		}
		return instancia;
	}

	private DisenoMedidaDAO() {
		super(DisenoMedida.class);
	}
}
