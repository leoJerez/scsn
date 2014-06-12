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

import cauca.scsn.modelo.entidad.id.RutaVehiculoId;

/**
 * RutaVehiculo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ruta_vehiculo", schema = "public")
public class RutaVehiculo extends EntidadGenerica {

	// Fields

	private RutaVehiculoId id;
	private Vehiculo vehiculo;
	private Ruta ruta;

	// Constructors

	/** default constructor */
	public RutaVehiculo() {
	}

	/** minimal constructor */
	public RutaVehiculo(RutaVehiculoId id, Vehiculo vehiculo, Ruta ruta,
			String status) {
		this.id = id;
		this.vehiculo = vehiculo;
		this.ruta = ruta;
		this.status = status;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idRuta", column = @Column(name = "id_ruta", nullable = false)),
			@AttributeOverride(name = "idVehiculo", column = @Column(name = "id_vehiculo", nullable = false)) })
	public RutaVehiculoId getId() {
		return this.id;
	}

	public void setId(RutaVehiculoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo", nullable = false, insertable = false, updatable = false)
	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ruta", nullable = false, insertable = false, updatable = false)
	public Ruta getRuta() {
		return this.ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
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