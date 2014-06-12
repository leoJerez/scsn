package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import cauca.scsn.modelo.entidad.EntidadGenerica;

import java.util.Date;


/**
 * The persistent class for the log database table.
 * 
 */
@Entity
@Table(name="log", schema="public")
public class Log extends EntidadGenerica  {
	
	private Integer idLog;
	private Date fechaUltimoIngreso;
	private Usuario usuario;
	private String mac;

	public Log() {
	}


	@Id
	@SequenceGenerator(name="LOG_IDLOG_GENERATOR", sequenceName="log_id_log_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_IDLOG_GENERATOR")
	@Column(name="id_log")
	public Integer getIdLog() {
		return this.idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ultimo_ingreso")
	public Date getFechaUltimoIngreso() {
		return this.fechaUltimoIngreso;
	}

	public void setFechaUltimoIngreso(Date fechaUltimoIngreso) {
		this.fechaUltimoIngreso = fechaUltimoIngreso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}