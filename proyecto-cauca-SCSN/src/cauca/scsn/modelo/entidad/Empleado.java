package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Operacion;
import cauca.scsn.modelo.entidad.seguridad.Usuario;

/**
 * Empleado entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "empleado", schema = "public")
public class Empleado extends EntidadGenerica {

	// Fields

	private String cedulaEmpleado;
	private Empresa empresa;
	private Cargo cargo;
	private Usuario usuario;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private String celular;
	private String correo;
	private Set<VehiculoEmpleado> vehiculoEmpleados = new HashSet<VehiculoEmpleado>(0);
//	private Set<Encuesta> encuesta;

	// Constructors

	/** default constructor */
	public Empleado() {
	}

	/** minimal constructor */
	public Empleado(String cedulaEmpleado, Empresa empresa, Cargo cargo,
			String nombre, String apellido, String direccion, String celular,
			String status) {
		this.cedulaEmpleado = cedulaEmpleado;
		this.empresa = empresa;
		this.cargo = cargo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.celular = celular;
		this.status = status;
	}

	/** full constructor */
	public Empleado(String cedulaEmpleado, Empresa empresa, Cargo cargo,
			String nombre, String apellido, String direccion, String telefono,
			String celular, String correo, String status,
			Set<VehiculoEmpleado> vehiculoEmpleados) {
		this.cedulaEmpleado = cedulaEmpleado;
		this.empresa = empresa;
		this.cargo = cargo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.correo = correo;
		this.status = status;
		this.vehiculoEmpleados = vehiculoEmpleados;
	}

	// Property accessors
	@Id
	@Column(name = "cedula_empleado", unique = true, nullable = false, length = 10)
	public String getCedulaEmpleado() {
		return this.cedulaEmpleado;
	}

	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cargo", nullable = true)
	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido", nullable = false, length = 60)
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Column(name = "direccion", nullable = false, length = 150)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", length = 15)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "celular", nullable = false, length = 15)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(name = "correo", length = 30)
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empleado")
	public Set<VehiculoEmpleado> getVehiculoEmpleados() {
		return this.vehiculoEmpleados;
	}

	public void setVehiculoEmpleados(Set<VehiculoEmpleado> vehiculoEmpleados) {
		this.vehiculoEmpleados = vehiculoEmpleados;
	}

	@Override
	public Object getPrimaryKey() {
		return this.cedulaEmpleado;
	}

	@Override
	public String toString() {
		return null;
	}

//	@OneToMany(mappedBy="empleado")
//	public Set<Encuesta> getEncuesta() {
//		return encuesta;
//	}
//
//	public void setEncuesta(Set<Encuesta> encuesta) {
//		this.encuesta = encuesta;
//	}

}