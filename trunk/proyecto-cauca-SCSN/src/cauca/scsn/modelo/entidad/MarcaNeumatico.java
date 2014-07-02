package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * MarcaNeumatico entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "marca_neumatico", schema = "public")
public class MarcaNeumatico extends EntidadGenerica {

	// Fields

	private Integer idMarcaNeumatico;
	private Empresa empresa;
	private String nombre;
	private String descripcion;
	private Set<Diseno> disenos = new HashSet<Diseno>(0);

	// Constructors

	/** default constructor */
	public MarcaNeumatico() {
	}

	/** minimal constructor */
	public MarcaNeumatico(Integer idMarcaNeumatico, Empresa empresa,
			String nombre, String status) {
		this.idMarcaNeumatico = idMarcaNeumatico;
		this.empresa = empresa;
		this.nombre = nombre;
		this.status = status;
	}

	/** full constructor */
	public MarcaNeumatico(Integer idMarcaNeumatico, Empresa empresa,
			String nombre, String descripcion, String status,
			Set<Diseno> disenos) {
		this.idMarcaNeumatico = idMarcaNeumatico;
		this.empresa = empresa;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.status = status;
		this.disenos = disenos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "MarcaNeumaticoSequence", sequenceName = "marca_neumatico_id_marca_neumatico_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MarcaNeumaticoSequence")	
	@Column(name = "id_marca_neumatico", unique = true, nullable = false)
	public Integer getIdMarcaNeumatico() {
		return this.idMarcaNeumatico;
	}

	public void setIdMarcaNeumatico(Integer idMarcaNeumatico) {
		this.idMarcaNeumatico = idMarcaNeumatico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "marcaNeumatico")
	public Set<Diseno> getDisenos() {
		return this.disenos;
	}

	public void setDisenos(Set<Diseno> disenos) {
		this.disenos = disenos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idMarcaNeumatico;
	}

	@Override
	public String toString() {
		return null;
	}

}