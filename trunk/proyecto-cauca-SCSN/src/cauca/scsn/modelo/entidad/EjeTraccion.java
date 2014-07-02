package cauca.scsn.modelo.entidad;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the eje_traccion database table.
 * 
 */
@Entity
@Table(name="eje_traccion", schema="public")
public class EjeTraccion extends EntidadGenerica  {
	private Integer idEjeTraccion;
	private Vehiculo vehiculo;
	private String nombre;

	public EjeTraccion() {
	}


	@Id
	@SequenceGenerator(name="EJE_TRACCION_IDEJETRACCION_GENERATOR", sequenceName="eje_traccion_id_eje_traccion_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EJE_TRACCION_IDEJETRACCION_GENERATOR")
	@Column(name="id_eje_traccion", unique = true, nullable = false)
	public Integer getIdEjeTraccion() {
		return this.idEjeTraccion;
	}

	public void setIdEjeTraccion(Integer idEjeTraccion) {
		this.idEjeTraccion = idEjeTraccion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo", nullable = false)
	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Column(name="nombre", nullable = false, length = 10)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return idEjeTraccion;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}