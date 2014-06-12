package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TipoDesgaste entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tipo_desgaste", schema = "public")
@ManagedBean
@RequestScoped
public class TipoDesgaste extends EntidadGenerica {

	// Fields

	private Integer idTipoDesgaste;
	private String nombre;
	private String descripcion;
	private String causa;
	private String accionesCorrectivas;
	private byte[] imagen;
	private Set<Movimiento> movimientos = new HashSet<Movimiento>(0);

	// Constructors

	/** default constructor */
	public TipoDesgaste() {
	}

	/** minimal constructor */
	public TipoDesgaste(Integer idTipoDesgaste, String nombre, String causa,
			String status) {
		this.idTipoDesgaste = idTipoDesgaste;
		this.nombre = nombre;
		this.causa = causa;
		this.status = status;
	}

	/** full constructor */
	public TipoDesgaste(Integer idTipoDesgaste, String nombre,
			String descripcion, String causa, String accionesCorrectivas,
			byte[] imagen, String status, Set<Movimiento> movimientos) {
		this.idTipoDesgaste = idTipoDesgaste;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.causa = causa;
		this.accionesCorrectivas = accionesCorrectivas;
		this.imagen = imagen;
		this.status = status;
		this.movimientos = movimientos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "TipoDesgasteSequence", sequenceName = "tipo_desgaste_id_tipo_desgaste_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "TipoDesgasteSequence")
	@Column(name = "id_tipo_desgaste", unique = true, nullable = false)
	public Integer getIdTipoDesgaste() {
		return this.idTipoDesgaste;
	}

	public void setIdTipoDesgaste(Integer idTipoDesgaste) {
		this.idTipoDesgaste = idTipoDesgaste;
	}

	@Column(name = "nombre", nullable = false, length = 60)
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

	@Column(name = "causa", nullable = false)
	public String getCausa() {
		return this.causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	@Column(name = "acciones_correctivas")
	public String getAccionesCorrectivas() {
		return this.accionesCorrectivas;
	}

	public void setAccionesCorrectivas(String accionesCorrectivas) {
		this.accionesCorrectivas = accionesCorrectivas;
	}

	@Lob
	@Column(name = "imagen", nullable = false)
	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tipoDesgaste")
	public Set<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Set<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idTipoDesgaste;
	}

	@Override
	public String toString() {
		return null;
	}

}