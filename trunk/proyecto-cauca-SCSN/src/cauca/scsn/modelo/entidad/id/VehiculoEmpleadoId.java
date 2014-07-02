package cauca.scsn.modelo.entidad.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VehiculoEmpleadoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VehiculoEmpleadoId implements java.io.Serializable {

	// Fields

	private Integer idVehiculo;
	private String cedulaEmpleado;

	// Constructors

	/** default constructor */
	public VehiculoEmpleadoId() {
	}

	/** full constructor */
	public VehiculoEmpleadoId(Integer idVehiculo, String cedulaEmpleado) {
		this.idVehiculo = idVehiculo;
		this.cedulaEmpleado = cedulaEmpleado;
	}

	// Property accessors

	@Column(name = "id_vehiculo", nullable = false)
	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	@Column(name = "cedula_empleado", nullable = false, length = 10)
	public String getCedulaEmpleado() {
		return this.cedulaEmpleado;
	}

	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VehiculoEmpleadoId))
			return false;
		VehiculoEmpleadoId castOther = (VehiculoEmpleadoId) other;

		return ((this.getIdVehiculo() == castOther.getIdVehiculo()) || (this
				.getIdVehiculo() != null && castOther.getIdVehiculo() != null && this
				.getIdVehiculo().equals(castOther.getIdVehiculo())))
				&& ((this.getCedulaEmpleado() == castOther.getCedulaEmpleado()) || (this
						.getCedulaEmpleado() != null
						&& castOther.getCedulaEmpleado() != null && this
						.getCedulaEmpleado().equals(
								castOther.getCedulaEmpleado())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdVehiculo() == null ? 0 : this.getIdVehiculo()
						.hashCode());
		result = 37
				* result
				+ (getCedulaEmpleado() == null ? 0 : this.getCedulaEmpleado()
						.hashCode());
		return result;
	}

}