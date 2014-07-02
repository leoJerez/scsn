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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TipoCarretera entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tipo_carretera", schema = "public")
public class TipoCarretera extends EntidadGenerica {

	// Fields

	private Integer idTipoCarretera;
	private String nombre;
	private String descripcion;
	private Set<Ruta> rutas = new HashSet<Ruta>(0);

	// Constructors

	/** default constructor */
	public TipoCarretera() {
	}

	/** minimal constructor */
	public TipoCarretera(Integer idTipoCarretera, String nombre, String status) {
		this.idTipoCarretera = idTipoCarretera;
		this.nombre = nombre;
		this.status = status;
	}

	/** full constructor */
	public TipoCarretera(Integer idTipoCarretera, String nombre,
			String descripcion, String status, Set<Ruta> rutas) {
		this.idTipoCarretera = idTipoCarretera;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.status = status;
		this.rutas = rutas;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "TipoCarreteraSequence", sequenceName = "tipo_carretera_id_tipo_carretera_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TipoCarreteraSequence")	
	@Column(name = "id_tipo_carretera", unique = true, nullable = false)
	public Integer getIdTipoCarretera() {
		return this.idTipoCarretera;
	}

	public void setIdTipoCarretera(Integer idTipoCarretera) {
		this.idTipoCarretera = idTipoCarretera;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tipoCarretera")
	public Set<Ruta> getRutas() {
		return this.rutas;
	}

	public void setRutas(Set<Ruta> rutas) {
		this.rutas = rutas;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idTipoCarretera;
	}

	@Override
	public String toString() {
		return null;
	}

}