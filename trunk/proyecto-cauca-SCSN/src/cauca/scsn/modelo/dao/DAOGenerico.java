package cauca.scsn.modelo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cauca.scsn.modelo.entidad.EntidadGenerica;


public abstract class DAOGenerico {

	private Class claseDTO;

	public DAOGenerico(Class claseDTO) {
		this.claseDTO = claseDTO;
	}

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	public void insertar(EntidadGenerica entidadGenerica) {
		EntityManagerHelper.log("insertando objeto "+this.claseDTO.getCanonicalName(), Level.INFO, null);
		EntityManagerHelper.beginTransaction();
		try {	    
			getEntityManager().persist(entidadGenerica);
			EntityManagerHelper.commit();
			EntityManagerHelper.log("insercion exitosa ", Level.INFO, null);
		}
		catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("insercion fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	public EntidadGenerica actualizar(EntidadGenerica entidadGenerica) {
		EntityManagerHelper.log("actualizando objeto "+this.claseDTO.getCanonicalName()+" con clave: " + entidadGenerica.getPrimaryKey(), Level.INFO, null);
		EntityManagerHelper.beginTransaction();
		try {
			EntidadGenerica result = getEntityManager().merge(entidadGenerica);
			EntityManagerHelper.commit();
			EntityManagerHelper.log("actualizacion exitosa", Level.INFO, null);
			return result;
		}
		catch(EntityNotFoundException enfe) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("el objeto "+this.claseDTO.getCanonicalName()+" con clave: " + entidadGenerica.getPrimaryKey()+" no existe", Level.SEVERE, enfe);
			throw enfe;
		}
		catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("actualizacion fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	public void insertarOActualizar(EntidadGenerica entidadGenerica) {
		try {
			if (entidadGenerica.getPrimaryKey() == null)
				throw new EntityNotFoundException();
			getEntityManager().getReference(this.claseDTO, entidadGenerica.getPrimaryKey());
			this.actualizar(entidadGenerica);
		}
		catch(EntityNotFoundException enfe) {
			this.insertar(entidadGenerica);
		}
	}

	private void eliminar(EntidadGenerica entidadGenerica) {
		EntityManagerHelper.log("eliminando objeto "+this.claseDTO.getCanonicalName()+" con clave: " + entidadGenerica.getPrimaryKey(), Level.INFO, null);
		EntityManagerHelper.beginTransaction();
		try {
			entidadGenerica = getEntityManager().getReference(this.claseDTO, entidadGenerica.getPrimaryKey());
			getEntityManager().remove(entidadGenerica);
			EntityManagerHelper.commit();
			EntityManagerHelper.log("eliminacion exitosa", Level.INFO, null);
		}
		catch(EntityNotFoundException enfe) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("el objeto "+this.claseDTO.getCanonicalName()+" con clave: " + entidadGenerica.getPrimaryKey()+" no existe", Level.SEVERE, enfe);
			throw enfe;
		}
		catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("eliminacion fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	public void eliminarFisicamente(EntidadGenerica entidadGenerica) {
		this.eliminar(entidadGenerica);
	}

	public void eliminarLogicamente(EntidadGenerica entidadGenerica) {
		entidadGenerica.desactivar();
		this.actualizar(entidadGenerica);
	}

	public EntidadGenerica restaurar(EntidadGenerica entidadGenerica) {
		entidadGenerica.activar();
		return this.actualizar(entidadGenerica);
	}

	public void ejecutarQuery(String queryString) {
		Query query = getEntityManager().createQuery(queryString);
		this.executeUpdate(query);
	}

	public void ejecutarQueryConParametros(String queryString, Map<String, Object> parametros) {
		Query query = getEntityManager().createQuery(queryString);
		for (String nombreParametro : parametros.keySet()) {
			query.setParameter(nombreParametro, parametros.get(nombreParametro));
		}
		this.executeUpdate(query);
	}

	private void executeUpdate(Query query) {
		EntityManagerHelper.log("ejecutando query de la clase "+this.claseDTO.getCanonicalName(), Level.INFO, null);
		EntityManagerHelper.beginTransaction();
		try {
			query.executeUpdate();
			EntityManagerHelper.commit();
			EntityManagerHelper.log("ejecucion exitosa", Level.INFO, null);
		}
		catch (RuntimeException re) {
			EntityManagerHelper.rollback();
			EntityManagerHelper.log("ejecucion fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	public EntidadGenerica buscarEntidadPorClave(Object clave) {
		EntityManagerHelper.log("buscando objeto "+this.claseDTO.getCanonicalName()+" con clave: " + clave, Level.INFO, null);
		try {
			EntidadGenerica entidadGenerica = getEntityManager().find(this.claseDTO, clave);
			return entidadGenerica;
		}
		catch (RuntimeException re) {
			EntityManagerHelper.log("busqueda por clave fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	@SuppressWarnings("unchecked")
	public List buscarTodasEntidades(final int... filaInicioYCantidadFilas) {
		EntityManagerHelper.log("buscando todos los objetos "+this.claseDTO.getCanonicalName(), Level.INFO, null);
		try {
			final String queryString = 
					"SELECT entity FROM "+this.claseDTO.getSimpleName()+" entity WHERE "+
							"entity.status = '"+EntidadGenerica.DATA_ACTIVA+"'";   //en esta linea cambie estado por status 
			return this.buscarEntidadesPorQuery(queryString);
		} 
		catch (RuntimeException re) {
			EntityManagerHelper.log("busqueda total fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	public EntidadGenerica buscarEntidadPorPropiedad(String nombrePropiedad, final Object valorPropiedad, final int... filaInicioYCantidadFilas) {
		return this.buscarPrimeraEntidadEnListado(this.buscarEntidadesPorPropiedad(nombrePropiedad, valorPropiedad, filaInicioYCantidadFilas));
	}

	public List buscarEntidadesPorPropiedad(String nombrePropiedad, final Object valorPropiedad, final int... filaInicioYCantidadFilas) {
		EntityManagerHelper.log("buscando objeto "+this.claseDTO.getCanonicalName()+" con propiedad: "+ nombrePropiedad + " = " + valorPropiedad, Level.INFO, null);
		String queryString =
				"SELECT entity FROM "+this.claseDTO.getSimpleName()+" entity WHERE "+
						"entity."+ nombrePropiedad + "= :valorPropiedad AND " +
						"entity.status = '"+EntidadGenerica.DATA_ACTIVA+"'";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("valorPropiedad", valorPropiedad);
		return this.buscarEntidadesPorQueryConParametros(queryString, parametros, filaInicioYCantidadFilas);
	}

	public EntidadGenerica buscarEntidadPorPropiedades(Map<String, Object> propiedades) {
		return this.buscarPrimeraEntidadEnListado(this.buscarEntidadesPorPropiedades(propiedades));
	}

	public List buscarEntidadesPorPropiedades(Map<String, Object> propiedades) {
		EntityManagerHelper.log("buscando objeto "+this.claseDTO.getCanonicalName()+" con propiedades: ", Level.INFO, null);
		EntityManager entityManager = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(this.claseDTO);	
		Root root = criteriaQuery.from(this.claseDTO);	
		for (String nombrePropiedad : propiedades.keySet()) {
			Predicate predicate = criteriaBuilder.equal(root.get(nombrePropiedad), propiedades.get(nombrePropiedad));
			criteriaQuery.where(criteriaBuilder.and(predicate));
		}
		List lista = entityManager.createQuery(criteriaQuery).getResultList();	
		return new LinkedList(lista);
	}

	public EntidadGenerica buscarEntidadPorQuery(String queryString, final int... filaInicioYCantidadFilas) {
		return this.buscarPrimeraEntidadEnListado(this.buscarEntidadesPorQuery(queryString, filaInicioYCantidadFilas));
	}

	public List buscarEntidadesPorQuery(String queryString, final int... filaInicioYCantidadFilas) {
		//queryString += " AND "+this.claseDTO.getSimpleName().substring(0,1).toLowerCase()+this.claseDTO.getSimpleName().substring(1)+".estado = '"+EntidadGenerica.DATA_ACTIVA+"'";
		Query query = getEntityManager().createQuery(queryString);
		return this.consultarPorQuery(query, filaInicioYCantidadFilas);
	}

	public EntidadGenerica buscarEntidadPorQueryConParametros(String queryString, Map<String, Object> parametros, final int... filaInicioYCantidadFilas) {
		return this.buscarPrimeraEntidadEnListado(buscarEntidadesPorQueryConParametros(queryString, parametros, filaInicioYCantidadFilas));
	}

	public List buscarEntidadesPorQueryConParametros(String queryString, Map<String, Object> parametros, final int... filaInicioYCantidadFilas) {
		//queryString += " AND "+this.claseDTO.getSimpleName().substring(0,1).toLowerCase()+this.claseDTO.getSimpleName().substring(1)+".estado = '"+EntidadGenerica.DATA_ACTIVA+"'";
		Query query = getEntityManager().createQuery(queryString);
		for (String nombreParametro : parametros.keySet()) {
			query.setParameter(nombreParametro, parametros.get(nombreParametro));
		}
		return this.consultarPorQuery(query, filaInicioYCantidadFilas);
	}

	private List consultarPorQuery(Query query, final int... filaInicioYCantidadFilas) {
		try {
			if (filaInicioYCantidadFilas != null && filaInicioYCantidadFilas.length > 0) {
				int rowStartIdx = Math.max(0, filaInicioYCantidadFilas[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (filaInicioYCantidadFilas.length > 1) {
					int rowCount = Math.max(0, filaInicioYCantidadFilas[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
		}
		catch (RuntimeException re) {
			EntityManagerHelper.log("busqueda fallida", Level.SEVERE, re);
			throw re;
		}
		finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	private EntidadGenerica buscarPrimeraEntidadEnListado(List listado) {
		if (listado != null && listado.size() > 0) {
			return (EntidadGenerica)listado.get(0);
		}
		return null;
	}
}
