package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.EmpleadoCauca;

import java.util.Date;


/**
 * The persistent class for the encuesta database table.
 * 
 */
@Entity
@Table(name="encuesta", schema="public")
public class Encuesta extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEncuesta;
	private Date fechaEncuesta;
	private String fechaVisita;
	private String horaVisita;
	private EmpleadoCauca empleadoCauca;
	private Usuario usuario;
	private String observaciones;
	private String visita;

	public Encuesta() {
	}


	@Id
	@SequenceGenerator(name="ENCUESTA_IDENCUESTA_GENERATOR", sequenceName="encuesta_id_encuesta_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENCUESTA_IDENCUESTA_GENERATOR")
	@Column(name="id_encuesta")
	public Integer getIdEncuesta() {
		return this.idEncuesta;
	}

	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="fecha_encuesta")
	public Date getFechaEncuesta() {
		return this.fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}


	@Column(name="fecha_visita")
	public String getFechaVisita() {
		return this.fechaVisita;
	}

	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}


	@Column(name="hora_visita")
	public String getHoraVisita() {
		return this.horaVisita;
	}

	public void setHoraVisita(String horaVisita) {
		this.horaVisita = horaVisita;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado_cauca", nullable = false)
	public EmpleadoCauca getEmpleadoCauca() {
		return this.empleadoCauca;
	}

	public void setEmpleadoCauca(EmpleadoCauca empleadoCauca) {
		this.empleadoCauca = empleadoCauca;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Column(name="status", length=1, nullable=false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getVisita() {
		return this.visita;
	}

	public void setVisita(String visita) {
		this.visita = visita;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.idEncuesta;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}