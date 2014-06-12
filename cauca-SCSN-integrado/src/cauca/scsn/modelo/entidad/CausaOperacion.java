package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * CausaOperacion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "causa_operacion", schema = "public")
@ManagedBean
@RequestScoped
public class CausaOperacion extends EntidadGenerica {

	// Fields

	private Integer idCausaOperacion;
	private String tipoOperacionNeumatico;
	private String nombre;
	private String descripcion;
	private Set<OperacionNeumatico> operacionNeumaticos = new HashSet<OperacionNeumatico>(
			0);

	// Constructors

	/** default constructor */
	public CausaOperacion() {
	}

	/** minimal constructor */
	public CausaOperacion(Integer idCausaOperacion,
			String tipoOperacionNeumatico, String nombre, String status) {
		this.idCausaOperacion = idCausaOperacion;
		this.tipoOperacionNeumatico = tipoOperacionNeumatico;
		this.nombre = nombre;
		this.status = status;
	}

	/** full constructor */
	public CausaOperacion(Integer idCausaOperacion,
			String tipoOperacionNeumatico, String nombre, String descripcion,
			String status, Set<OperacionNeumatico> operacionNeumaticos) {
		this.idCausaOperacion = idCausaOperacion;
		this.tipoOperacionNeumatico = tipoOperacionNeumatico;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.status = status;
		this.operacionNeumaticos = operacionNeumaticos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "CausaOperacionSequence", sequenceName = "causa_operacion_id_causa_operacion_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CausaOperacionSequence")
	@Column(name = "id_causa_operacion", unique = true, nullable = false)
	public Integer getIdCausaOperacion() {
		return this.idCausaOperacion;
	}

	public void setIdCausaOperacion(Integer idCausaOperacion) {
		this.idCausaOperacion = idCausaOperacion;
	}

	@Column(name = "tipo_operacion_neumatico", nullable = false, length = 1)
	public String getTipoOperacionNeumatico() {
		return this.tipoOperacionNeumatico;
	}

	public void setTipoOperacionNeumatico(String tipoOperacionNeumatico) {
		this.tipoOperacionNeumatico = tipoOperacionNeumatico;
	}

	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "causaOperacion")
	public Set<OperacionNeumatico> getOperacionNeumaticos() {
		return this.operacionNeumaticos;
	}

	public void setOperacionNeumaticos(
			Set<OperacionNeumatico> operacionNeumaticos) {
		this.operacionNeumaticos = operacionNeumaticos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idCausaOperacion;
	}

	@Override
	public String toString() {
		return null;
	}

}