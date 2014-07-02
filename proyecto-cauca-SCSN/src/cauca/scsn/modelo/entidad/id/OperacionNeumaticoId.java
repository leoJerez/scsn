package cauca.scsn.modelo.entidad.id;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OperacionNeumaticoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class OperacionNeumaticoId implements java.io.Serializable {

	// Fields

	private Integer idNeumatico;
	private Date fechaOperacion;

	// Constructors

	/** default constructor */
	public OperacionNeumaticoId() {
	}

	/** full constructor */
	public OperacionNeumaticoId(Integer idNeumatico, Date fechaOperacion) {
		this.idNeumatico = idNeumatico;
		this.fechaOperacion = fechaOperacion;
	}

	// Property accessors

	@Column(name = "id_neumatico", nullable = false)
	public Integer getIdNeumatico() {
		return this.idNeumatico;
	}

	public void setIdNeumatico(Integer idNeumatico) {
		this.idNeumatico = idNeumatico;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_operacion", nullable = false, length = 13)
	public Date getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OperacionNeumaticoId))
			return false;
		OperacionNeumaticoId castOther = (OperacionNeumaticoId) other;

		return ((this.getIdNeumatico() == castOther.getIdNeumatico()) || (this
				.getIdNeumatico() != null && castOther.getIdNeumatico() != null && this
				.getIdNeumatico().equals(castOther.getIdNeumatico())))
				&& ((this.getFechaOperacion() == castOther.getFechaOperacion()) || (this
						.getFechaOperacion() != null
						&& castOther.getFechaOperacion() != null && this
						.getFechaOperacion().equals(
								castOther.getFechaOperacion())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdNeumatico() == null ? 0 : this.getIdNeumatico()
						.hashCode());
		result = 37
				* result
				+ (getFechaOperacion() == null ? 0 : this.getFechaOperacion()
						.hashCode());
		return result;
	}

}