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
 * Empresa entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "empresa", schema = "public")
public class Empresa extends EntidadGenerica {

	// Fields

	private Integer idEmpresa;
	private String rif;
	private String nombre;
	private String direccion;
	private String telefono;
	private String celular;
	private String fax;
	private String correo;
	private Double remanenteRetiro;
	private Set<Ruta> rutas = new HashSet<Ruta>(0);
	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>(0);
	private Set<MarcaVehiculo> marcaVehiculos = new HashSet<MarcaVehiculo>(0);
	private Set<Empleado> empleados = new HashSet<Empleado>(0);
	private Set<Cargo> cargos = new HashSet<Cargo>(0);
	private Set<Medida> medidas = new HashSet<Medida>(0);
	private Set<Neumatico> neumaticos = new HashSet<Neumatico>(0);
	private Set<MarcaNeumatico> marcaNeumaticos = new HashSet<MarcaNeumatico>(0);
	private Set<Proveedor> proveedors = new HashSet<Proveedor>(0);
	private Set<EsquemaEje> esquemaEjes = new HashSet<EsquemaEje>(0);
	private EmpleadoCauca empleadoCauca;

	// Constructors

	/** default constructor */
	public Empresa() {
	}

	/** minimal constructor */
	public Empresa(Integer idEmpresa, String rif, String nombre,
			String direccion, String telefono, Double remanenteRetiro,
			String status) {
		this.idEmpresa = idEmpresa;
		this.rif = rif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.remanenteRetiro = remanenteRetiro;
		this.status = status;
	}

	/** full constructor */
	public Empresa(Integer idEmpresa, String rif, String nombre,
			String direccion, String telefono, String celular, String fax,
			String correo, Double remanenteRetiro, String status,
			Set<Ruta> rutas, Set<Vehiculo> vehiculos,
			Set<MarcaVehiculo> marcaVehiculos, Set<Empleado> empleados, Set<Cargo> cargos, Set<Medida> medidas,
			Set<Neumatico> neumaticos, Set<MarcaNeumatico> marcaNeumaticos,
			Set<Proveedor> proveedors, Set<EsquemaEje> esquemaEjes) {
		this.idEmpresa = idEmpresa;
		this.rif = rif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.fax = fax;
		this.correo = correo;
		this.remanenteRetiro = remanenteRetiro;
		this.status = status;
		this.rutas = rutas;
		this.vehiculos = vehiculos;
		this.marcaVehiculos = marcaVehiculos;
		this.empleados = empleados;
		this.cargos = cargos;
		this.medidas = medidas;
		this.neumaticos = neumaticos;
		this.marcaNeumaticos = marcaNeumaticos;
		this.proveedors = proveedors;
		this.esquemaEjes = esquemaEjes;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "EmpresaSequence", sequenceName = "empresa_id_empresa_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "EmpresaSequence")
	@Column(name = "id_empresa", unique = true, nullable = false)
	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Column(name = "rif", nullable = false, length = 20)
	public String getRif() {
		return this.rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "direccion", nullable = false)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", nullable = false, length = 15)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "celular", length = 15)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(name = "fax", length = 15)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "correo", length = 30)
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Column(name = "remanente_retiro", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteRetiro() {
		return this.remanenteRetiro;
	}

	public void setRemanenteRetiro(Double remanenteRetiro) {
		this.remanenteRetiro = remanenteRetiro;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Ruta> getRutas() {
		return this.rutas;
	}

	public void setRutas(Set<Ruta> rutas) {
		this.rutas = rutas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public void setVehiculos(Set<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<MarcaVehiculo> getMarcaVehiculos() {
		return this.marcaVehiculos;
	}

	public void setMarcaVehiculos(Set<MarcaVehiculo> marcaVehiculos) {
		this.marcaVehiculos = marcaVehiculos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Empleado> getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Cargo> getCargos() {
		return this.cargos;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Medida> getMedidas() {
		return this.medidas;
	}

	public void setMedidas(Set<Medida> medidas) {
		this.medidas = medidas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Neumatico> getNeumaticos() {
		return this.neumaticos;
	}

	public void setNeumaticos(Set<Neumatico> neumaticos) {
		this.neumaticos = neumaticos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<MarcaNeumatico> getMarcaNeumaticos() {
		return this.marcaNeumaticos;
	}

	public void setMarcaNeumaticos(Set<MarcaNeumatico> marcaNeumaticos) {
		this.marcaNeumaticos = marcaNeumaticos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Proveedor> getProveedors() {
		return this.proveedors;
	}

	public void setProveedors(Set<Proveedor> proveedors) {
		this.proveedors = proveedors;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<EsquemaEje> getEsquemaEjes() {
		return this.esquemaEjes;
	}

	public void setEsquemaEjes(Set<EsquemaEje> esquemaEjes) {
		this.esquemaEjes = esquemaEjes;
	}

	@JoinColumn(name="id_empleado_cauca")
	@ManyToOne(fetch = FetchType.LAZY)
	public EmpleadoCauca getEmpleadoCauca() {
		return empleadoCauca;
	}

	public void setEmpleadoCauca(EmpleadoCauca empleadoCauca) {
		this.empleadoCauca = empleadoCauca;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idEmpresa;
	}

	@Override
	public String toString() {
		return null;
	}

}