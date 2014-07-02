package cauca.scsn.modelo.dao;

import cauca.scsn.modelo.entidad.Empresa;

public class EmpresaDAO extends DAOGenerico {

	private static EmpresaDAO instancia;

	public static EmpresaDAO getInstancia() {
		if (instancia == null) {
			instancia = new EmpresaDAO();
		}
		return instancia;
	}

	private EmpresaDAO() {
		super(Empresa.class);
	}
}
