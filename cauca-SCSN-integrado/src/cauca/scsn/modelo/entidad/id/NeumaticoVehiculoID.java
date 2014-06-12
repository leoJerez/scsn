package cauca.scsn.modelo.entidad.id;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the neumatico_vehiculo database table.
 * 
 */
@Embeddable
public class NeumaticoVehiculoID implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer idNeumatico;
	private Integer idVehiculo;

	public NeumaticoVehiculoID() {
	}

	@Column(name="id_neumatico", nullable = false)
	public Integer getIdNeumatico() {
		return this.idNeumatico;
	}
	public void setIdNeumatico(Integer idNeumatico) {
		this.idNeumatico = idNeumatico;
	}

	@Column(name="id_vehiculo", nullable = false)
	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}
	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NeumaticoVehiculoID)) {
			return false;
		}
		NeumaticoVehiculoID castOther = (NeumaticoVehiculoID)other;
		return 
			this.idNeumatico.equals(castOther.idNeumatico)
			&& this.idVehiculo.equals(castOther.idVehiculo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idNeumatico.hashCode();
		hash = hash * prime + this.idVehiculo.hashCode();
		
		return hash;
	}
}