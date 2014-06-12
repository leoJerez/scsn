package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.TipoDesgaste;

public class TipoDesgasteDAO extends DAOGenerico {

	private static TipoDesgasteDAO instancia;

	public static TipoDesgasteDAO getInstancia() {
		if (instancia == null) {
			instancia = new TipoDesgasteDAO();
		}
		return instancia;
	}

	private TipoDesgasteDAO() {
		super(TipoDesgaste.class);
	}
}
