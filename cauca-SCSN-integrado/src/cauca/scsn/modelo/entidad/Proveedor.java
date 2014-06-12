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
 * Proveedor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "proveedor", schema = "public")
public class Proveedor extends EntidadGenerica {

	// Fields

	private Integer idProveedor;
	private Empresa empresa;
	private String nombre;
	private String direccion;
	private String rif;
	private String telefono;
	private String celular;
	private String correo;

	// Constructors

	/** default constructor */
	public Proveedor() {
	}

	/** minimal constructor */
	public Proveedor(Integer idProveedor, Empresa empresa, String nombre,
			String direccion, String rif, String celular, String status) {
		this.idProveedor = idProveedor;
		this.empresa = empresa;
		this.nombre = nombre;
		this.direccion = direccion;
		this.rif = rif;
		this.celular = celular;
		this.status = status;
	}

	/** full constructor */
	public Proveedor(Integer idProveedor, Empresa empresa, String nombre,
			String direccion, String rif, String telefono, String celular,
			String correo, String status) {
		this.idProveedor = idProveedor;
		this.empresa = empresa;
		this.nombre = nombre;
		this.direccion = direccion;
		this.rif = rif;
		this.telefono = telefono;
		this.celular = celular;
		this.correo = correo;
		this.status = status;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "ProveedorSequence", sequenceName = "proveedor_id_proveedor_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ProveedorSequence")
	@Column(name = "id_proveedor", unique = true, nullable = false)
	public Integer getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	@Column(name = "rif", nullable = false, length = 15)
	public String getRif() {
		return this.rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
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

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idProveedor;
	}

	@Override
	public String toString() {
		return null;
	}

}