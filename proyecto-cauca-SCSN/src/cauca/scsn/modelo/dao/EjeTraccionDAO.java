package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.EjeTraccion;

public class EjeTraccionDAO extends DAOGenerico {

		private static EjeTraccionDAO instancia;

		public static EjeTraccionDAO getInstancia() {
			if (instancia == null) {
				instancia = new EjeTraccionDAO();
			}
			return instancia;
		}

		private EjeTraccionDAO() {
			super(EjeTraccion.class);
		}

}
