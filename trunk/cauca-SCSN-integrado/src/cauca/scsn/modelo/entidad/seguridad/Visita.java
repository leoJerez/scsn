package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the visita database table.
 * 
 */
@Entity
@Table(name="visita", schema="public")
public class Visita extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	private Integer idVisita;
	private String fechaVisita;
	private Usuario usuario;
	private String mac;
	private String observaciones;

	public Visita() {
	}


	@Id
	@SequenceGenerator(name="VISITA_IDVISITA_GENERATOR", sequenceName="visita_id_visita_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VISITA_IDVISITA_GENERATOR")
	@Column(name="id_visita")
	public Integer getIdVisita() {
		return this.idVisita;
	}

	public void setIdVisita(Integer idVisita) {
		this.idVisita = idVisita;
	}


	@Column(name="fecha_visita")
	public String getFechaVisita() {
		return this.fechaVisita;
	}

	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario Usuario) {
		this.usuario = Usuario;
	}


	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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