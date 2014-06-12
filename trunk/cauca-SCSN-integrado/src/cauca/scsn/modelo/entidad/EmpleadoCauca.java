package cauca.scsn.modelo.entidad;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Usuario;


/**
 * The persistent class for the empleado_cauca database table.
 * 
 */
@Entity
@Table(name="empleado_cauca", schema="public")
public class EmpleadoCauca extends EntidadGenerica  {
	private static final long serialVersionUID = 1L;
	private String cedulaEmpleado;
	private String apellido;
	private String celular;
	private String correo;
	private String direccion;
	private Usuario usuario;
	private String nombre;
	private Set<Encuesta> encuesta;
	private Set<Empresa> empresa;

	public EmpleadoCauca() {
	}


	@Id
	@SequenceGenerator(name="EMPLEADO_CAUCA_CEDULAEMPLEADO_GENERATOR", sequenceName="EMPLEADO_CAUCA_ID_$TABLE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLEADO_CAUCA_CEDULAEMPLEADO_GENERATOR")
	@Column(name="cedula_empleado")
	public String getCedulaEmpleado() {
		return this.cedulaEmpleado;
	}

	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
	}


	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}


	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	@JoinColumn(name="id_usuario")
	@ManyToOne(fetch = FetchType.LAZY)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy="empleadoCauca")
	public Set<Encuesta> getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Set<Encuesta> encuesta) {
		this.encuesta = encuesta;
	}

	@OneToMany(mappedBy="empleadoCauca")
	public Set<Empresa> getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Set<Empresa> empresa) {
		this.empresa = empresa;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.cedulaEmpleado;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}