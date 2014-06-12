package cauca.scsn.modelo.entidad.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RutaVehiculoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class RutaVehiculoId implements java.io.Serializable {

	// Fields

	private Integer idRuta;
	private Integer idVehiculo;

	// Constructors

	/** default constructor */
	public RutaVehiculoId() {
	}

	/** full constructor */
	public RutaVehiculoId(Integer idRuta, Integer idVehiculo) {
		this.idRuta = idRuta;
		this.idVehiculo = idVehiculo;
	}

	// Property accessors

	@Column(name = "id_ruta", nullable = false)
	public Integer getIdRuta() {
		return this.idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	@Column(name = "id_vehiculo", nullable = false)
	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RutaVehiculoId))
			return false;
		RutaVehiculoId castOther = (RutaVehiculoId) other;

		return ((this.getIdRuta() == castOther.getIdRuta()) || (this
				.getIdRuta() != null && castOther.getIdRuta() != null && this
				.getIdRuta().equals(castOther.getIdRuta())))
				&& ((this.getIdVehiculo() == castOther.getIdVehiculo()) || (this
						.getIdVehiculo() != null
						&& castOther.getIdVehiculo() != null && this
						.getIdVehiculo().equals(castOther.getIdVehiculo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdRuta() == null ? 0 : this.getIdRuta().hashCode());
		result = 37
				* result
				+ (getIdVehiculo() == null ? 0 : this.getIdVehiculo()
						.hashCode());
		return result;
	}

}