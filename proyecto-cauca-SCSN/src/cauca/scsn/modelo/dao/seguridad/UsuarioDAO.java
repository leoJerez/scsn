package cauca.scsn.modelo.dao.seguridad;

import cauca.scsn.modelo.dao.DAOGenerico;
import cauca.scsn.modelo.entidad.seguridad.Usuario;

public class UsuarioDAO extends DAOGenerico {
	private static UsuarioDAO instancia;

	public static UsuarioDAO getInstancia() {
		if (instancia == null) {
			instancia = new UsuarioDAO();
		}
		return instancia;
	}

	private UsuarioDAO() {
		super(Usuario.class);
	}
	

}
