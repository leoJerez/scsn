package cauca.scsn.modelo.entidad;

import java.io.Serializable;

import javax.persistence.*;

import cauca.scsn.modelo.entidad.id.NeumaticoVehiculoID;


/**
 * The persistent class for the neumatico_vehiculo database table.
 * 
 */
@Entity
@Table(name="neumatico_vehiculo", schema = "public")
public class NeumaticoVehiculo extends EntidadGenerica {
	
	private NeumaticoVehiculoID id;
	private Neumatico neumatico;
	private Vehiculo vehiculo;
	private String posicion;

	public NeumaticoVehiculo() {
	}


	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "idNeumatico", column = @Column(name = "id_neumatico", nullable = false)),
		@AttributeOverride(name = "idVehiculo", column = @Column(name = "id_vehiculo", nullable = false)) })
	public NeumaticoVehiculoID getId() {
		return this.id;
	}

	public void setId(NeumaticoVehiculoID id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_neumatico", nullable = false, insertable = false, updatable = false)
	public Neumatico getNeumatico() {
		return neumatico;
	}


	public void setNeumatico(Neumatico neumatico) {
		this.neumatico = neumatico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo", nullable = false, insertable = false, updatable = false)
	public Vehiculo getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}


	@Column(name="posicion", nullable= false, length = 1)
	public String getPosicion() {
		return this.posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}


	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}


	@Override
	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public Object getPrimaryKey() {
		return this.id;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}