package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario", schema="public")
public class Usuario extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	
	private Integer idUsuario;
	private Rol rol;
	private String login;
	private String password;
	private Set<Encuesta> encuesta;
	private Set<Log> log;
	private Set<cauca.scsn.modelo.entidad.EmpleadoCauca> EmpleadoCauca;
	
	public Usuario() {
	}


	@Id
	@SequenceGenerator(name="USUARIO_IDUSUARIO_GENERATOR", sequenceName="usuario_id_usuario_seq",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario")
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@ManyToOne
	@JoinColumn(name="id_rol")
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Column(name="login")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name="password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="status", nullable=false ,length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "usuario")
	public Set<Encuesta> getEncuesta() {
		return encuesta;
	}


	public void setEncuesta(Set<Encuesta> encuesta) {
		this.encuesta = encuesta;
	}

	@OneToMany(mappedBy = "usuario")
	public Set<Log> getLog() {
		return log;
	}


	public void setLog(Set<Log> log) {
		this.log = log;
	}

	@OneToMany(mappedBy="usuario")
	public Set<cauca.scsn.modelo.entidad.EmpleadoCauca> getEmpleadoCauca() {
		return EmpleadoCauca;
	}


	public void setEmpleadoCauca(Set<cauca.scsn.modelo.entidad.EmpleadoCauca> empleadoCauca) {
		EmpleadoCauca = empleadoCauca;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.idUsuario;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}