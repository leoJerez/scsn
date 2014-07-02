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
 * Medida entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "medida", schema = "public")
public class Medida extends EntidadGenerica {

	// Fields

	private Integer idMedida;
	private Empresa empresa;
	private String nombre;
	private String descripcion;
	private Double presionRecomendada;
	private Set<DisenoMedida> disenoMedidas = new HashSet<DisenoMedida>(0);

	// Constructors

	/** default constructor */
	public Medida() {
	}

	/** minimal constructor */
	public Medida(Integer idMedida, Empresa empresa, String nombre,
			Double presionRecomendada, String status) {
		this.idMedida = idMedida;
		this.empresa = empresa;
		this.nombre = nombre;
		this.presionRecomendada = presionRecomendada;
		this.status = status;
	}

	/** full constructor */
	public Medida(Integer idMedida, Empresa empresa, String nombre,
			String descripcion, Double presionRecomendada, String status,
			Set<DisenoMedida> disenoMedidas) {
		this.idMedida = idMedida;
		this.empresa = empresa;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.presionRecomendada = presionRecomendada;
		this.status = status;
		this.disenoMedidas = disenoMedidas;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "MedidaSequence", sequenceName = "medida_id_medida_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MedidaSequence")
	@Column(name = "id_medida", unique = true, nullable = false)
	public Integer getIdMedida() {
		return this.idMedida;
	}

	public void setIdMedida(Integer idMedida) {
		this.idMedida = idMedida;
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

	@Column(name = "presion_recomendada", nullable = false, precision = 17, scale = 17)
	public Double getPresionRecomendada() {
		return this.presionRecomendada;
	}

	public void setPresionRecomendada(Double presionRecomendada) {
		this.presionRecomendada = presionRecomendada;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medida")
	public Set<DisenoMedida> getDisenoMedidas() {
		return this.disenoMedidas;
	}

	public void setDisenoMedidas(Set<DisenoMedida> disenoMedidas) {
		this.disenoMedidas = disenoMedidas;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idMedida;
	}

	@Override
	public String toString() {
		return null;
	}

}