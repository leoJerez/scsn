package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cauca.scsn.modelo.entidad.id.DisenoMedidaId;

/**
 * DisenoMedida entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "diseno_medida", schema = "public")
public class DisenoMedida extends EntidadGenerica {

	// Fields

	private DisenoMedidaId id;
	private Medida medida;
	private Diseno diseno;
	private Set<Neumatico> neumaticos = new HashSet<Neumatico>(0);

	// Constructors

	/** default constructor */
	public DisenoMedida() {
	}

	/** minimal constructor */
	public DisenoMedida(DisenoMedidaId id, Medida medida, Diseno diseno,
			String status) {
		this.id = id;
		this.medida = medida;
		this.diseno = diseno;
		this.status = status;
	}

	/** full constructor */
	public DisenoMedida(DisenoMedidaId id, Medida medida, Diseno diseno,
			String status, Set<Neumatico> neumaticos) {
		this.id = id;
		this.medida = medida;
		this.diseno = diseno;
		this.status = status;
		this.neumaticos = neumaticos;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idMedida", column = @Column(name = "id_medida", nullable = false)),
			@AttributeOverride(name = "idDiseno", column = @Column(name = "id_diseno", nullable = false)) })
	public DisenoMedidaId getId() {
		return this.id;
	}

	public void setId(DisenoMedidaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medida", nullable = false, insertable = false, updatable = false)
	public Medida getMedida() {
		return this.medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_diseno", nullable = false, insertable = false, updatable = false)
	public Diseno getDiseno() {
		return this.diseno;
	}

	public void setDiseno(Diseno diseno) {
		this.diseno = diseno;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "disenoMedida")
	public Set<Neumatico> getNeumaticos() {
		return this.neumaticos;
	}

	public void setNeumaticos(Set<Neumatico> neumaticos) {
		this.neumaticos = neumaticos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.id;
	}

	@Override
	public String toString() {
		return null;
	}

}