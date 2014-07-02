package cauca.scsn.modelo.entidad;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cauca.scsn.modelo.entidad.id.OperacionNeumaticoId;

/**
 * OperacionNeumatico entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operacion_neumatico", schema = "public")
public class OperacionNeumatico extends EntidadGenerica {

	// Fields

	private OperacionNeumaticoId id;
	private CausaOperacion causaOperacion;
	private Neumatico neumatico;
	private String observacion;

	// Constructors

	/** default constructor */
	public OperacionNeumatico() {
	}

	/** minimal constructor */
	public OperacionNeumatico(OperacionNeumaticoId id, Neumatico neumatico,
			String status) {
		this.id = id;
		this.neumatico = neumatico;
		this.status = status;
	}

	/** full constructor */
	public OperacionNeumatico(OperacionNeumaticoId id,
			CausaOperacion causaOperacion, Neumatico neumatico,
			String observacion, String status) {
		this.id = id;
		this.causaOperacion = causaOperacion;
		this.neumatico = neumatico;
		this.observacion = observacion;
		this.status = status;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idNeumatico", column = @Column(name = "id_neumatico", nullable = false)),
			@AttributeOverride(name = "fechaOperacion", column = @Column(name = "fecha_operacion", nullable = false, length = 13)) })
	public OperacionNeumaticoId getId() {
		return this.id;
	}

	public void setId(OperacionNeumaticoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_causa_operacion")
	public CausaOperacion getCausaOperacion() {
		return this.causaOperacion;
	}

	public void setCausaOperacion(CausaOperacion causaOperacion) {
		this.causaOperacion = causaOperacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_neumatico", nullable = false, insertable = false, updatable = false)
	public Neumatico getNeumatico() {
		return this.neumatico;
	}

	public void setNeumatico(Neumatico neumatico) {
		this.neumatico = neumatico;
	}

	@Column(name = "observacion")
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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