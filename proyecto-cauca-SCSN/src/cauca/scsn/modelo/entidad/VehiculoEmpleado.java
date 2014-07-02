package cauca.scsn.modelo.entidad;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cauca.scsn.modelo.entidad.id.VehiculoEmpleadoId;

/**
 * VehiculoEmpleado entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vehiculo_empleado", schema = "public")
public class VehiculoEmpleado extends EntidadGenerica {

	// Fields

	private VehiculoEmpleadoId id;
	private Vehiculo vehiculo;
	private Empleado empleado;
	private String conductorPrincipal;
	private String temporal;
	private Date fechaInicial;
	private Date fechaFinal;
	private String motivo;

	// Constructors

	/** default constructor */
	public VehiculoEmpleado() {
	}

	/** minimal constructor */
	public VehiculoEmpleado(VehiculoEmpleadoId id, Vehiculo vehiculo,
			Empleado empleado, String conductorPrincipal, String status) {
		this.id = id;
		this.vehiculo = vehiculo;
		this.empleado = empleado;
		this.conductorPrincipal = conductorPrincipal;
		this.status = status;
	}


	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idVehiculo", column = @Column(name = "id_vehiculo", nullable = false)),
			@AttributeOverride(name = "cedulaEmpleado", column = @Column(name = "cedula_empleado", nullable = false, length = 10)) })
	public VehiculoEmpleadoId getId() {
		return this.id;
	}

	public void setId(VehiculoEmpleadoId id) {
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
	@JoinColumn(name = "cedula_empleado", nullable = false, insertable = false, updatable = false)
	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Column(name = "conductor_principal", nullable = false, length = 1)
	public String getConductorPrincipal() {
		return this.conductorPrincipal;
	}

	public void setConductorPrincipal(String conductorPrincipal) {
		this.conductorPrincipal = conductorPrincipal;
	}

	@Column(name = "temporal", nullable = true, length = 1)
	public String getTemporal() {
		return temporal;
	}

	public void setTemporal(String temporal) {
		this.temporal = temporal;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicial", nullable = false, length = 13)
	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_final", length = 13)
	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	@Column(name = "motivo", nullable = false)
	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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