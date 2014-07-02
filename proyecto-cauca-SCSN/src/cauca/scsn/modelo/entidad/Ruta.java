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
 * Ruta entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ruta", schema = "public")
public class Ruta extends EntidadGenerica {

	// Fields

	private Integer idRuta;
	private Empresa empresa;
	private TipoCarretera tipoCarretera;
	private String nombre;
	private String descripcion;
	private Set<RutaVehiculo> rutaVehiculos = new HashSet<RutaVehiculo>(0);

	// Constructors

	/** default constructor */
	public Ruta() {
	}

	/** minimal constructor */
	public Ruta(Integer idRuta, Empresa empresa, TipoCarretera tipoCarretera,
			String nombre, String status) {
		this.idRuta = idRuta;
		this.empresa = empresa;
		this.tipoCarretera = tipoCarretera;
		this.nombre = nombre;
		this.status = status;
	}

	/** full constructor */
	public Ruta(Integer idRuta, Empresa empresa, TipoCarretera tipoCarretera,
			String nombre, String descripcion, String status,
			Set<RutaVehiculo> rutaVehiculos) {
		this.idRuta = idRuta;
		this.empresa = empresa;
		this.tipoCarretera = tipoCarretera;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.status = status;
		this.rutaVehiculos = rutaVehiculos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "RutaSequence", sequenceName = "ruta_id_ruta_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "RutaSequence")
	@Column(name = "id_ruta", unique = true, nullable = false)
	public Integer getIdRuta() {
		return this.idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
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
	@JoinColumn(name = "id_tipo_carretera", nullable = false)
	public TipoCarretera getTipoCarretera() {
		return this.tipoCarretera;
	}

	public void setTipoCarretera(TipoCarretera tipoCarretera) {
		this.tipoCarretera = tipoCarretera;
	}

	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 150)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ruta")
	public Set<RutaVehiculo> getRutaVehiculos() {
		return this.rutaVehiculos;
	}

	public void setRutaVehiculos(Set<RutaVehiculo> rutaVehiculos) {
		this.rutaVehiculos = rutaVehiculos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idRuta;
	}

	@Override
	public String toString() {
		return null;
	}

}