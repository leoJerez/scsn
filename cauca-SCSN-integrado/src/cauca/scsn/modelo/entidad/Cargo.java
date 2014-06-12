package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cargo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cargo", schema = "public")
public class Cargo extends EntidadGenerica {

	// Fields

	private Integer idCargo;
	private Empresa empresa;
	private String nombre;
	private String descripcion;
	private Set<Empleado> empleados = new HashSet<Empleado>(0);

	// Constructors

	/** default constructor */
	public Cargo() {
	}

	/** minimal constructor */
	public Cargo(Integer idCargo, Empresa empresa, String nombre, String status) {
		this.idCargo = idCargo;
		this.empresa = empresa;
		this.nombre = nombre;
		this.status = status;
	}

	/** full constructor */
	public Cargo(Integer idCargo, Empresa empresa, String nombre,
			String descripcion, String status, Set<Empleado> empleados) {
		this.idCargo = idCargo;
		this.empresa = empresa;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.status = status;
		this.empleados = empleados;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "CargoSequence", sequenceName = "cargo_id_cargo_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CargoSequence")	
	@Column(name = "id_cargo", unique = true, nullable = false)
	public Integer getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(name = "nombre", nullable = false, length = 64)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cargo")
	public Set<Empleado> getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idCargo;
	}

	@Override
	public String toString() {
		return null;
	}

}