package cauca.scsn.modelo.entidad.id;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MovimientoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MovimientoId implements java.io.Serializable {

	// Fields

	private Integer idVehiculo;
	private Integer idNeumatico;
	private String tipoMovimiento;
	private Date fecha;

	// Constructors

	/** default constructor */
	public MovimientoId() {
	}

	/** full constructor */
	public MovimientoId(Integer idVehiculo, Integer idNeumatico,
			String tipoMovimiento, Date fecha) {
		this.idVehiculo = idVehiculo;
		this.idNeumatico = idNeumatico;
		this.tipoMovimiento = tipoMovimiento;
		this.fecha = fecha;
	}

	// Property accessors

	@Column(name = "id_vehiculo", nullable = false)
	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	@Column(name = "id_neumatico", nullable = false)
	public Integer getIdNeumatico() {
		return this.idNeumatico;
	}

	public void setIdNeumatico(Integer idNeumatico) {
		this.idNeumatico = idNeumatico;
	}

	@Column(name = "tipo_movimiento", nullable = false, length = 1)
	public String getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false, length = 13)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MovimientoId))
			return false;
		MovimientoId castOther = (MovimientoId) other;

		return ((this.getIdVehiculo() == castOther.getIdVehiculo()) || (this
				.getIdVehiculo() != null && castOther.getIdVehiculo() != null && this
				.getIdVehiculo().equals(castOther.getIdVehiculo())))
				&& ((this.getIdNeumatico() == castOther.getIdNeumatico()) || (this
						.getIdNeumatico() != null
						&& castOther.getIdNeumatico() != null && this
						.getIdNeumatico().equals(castOther.getIdNeumatico())))
				&& ((this.getTipoMovimiento() == castOther.getTipoMovimiento()) || (this
						.getTipoMovimiento() != null
						&& castOther.getTipoMovimiento() != null && this
						.getTipoMovimiento().equals(
								castOther.getTipoMovimiento())))
				&& ((this.getFecha() == castOther.getFecha()) || (this
						.getFecha() != null && castOther.getFecha() != null && this
						.getFecha().equals(castOther.getFecha())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdVehiculo() == null ? 0 : this.getIdVehiculo()
						.hashCode());
		result = 37
				* result
				+ (getIdNeumatico() == null ? 0 : this.getIdNeumatico()
						.hashCode());
		result = 37
				* result
				+ (getTipoMovimiento() == null ? 0 : this.getTipoMovimiento()
						.hashCode());
		result = 37 * result
				+ (getFecha() == null ? 0 : this.getFecha().hashCode());
		return result;
	}

}