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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Diseno entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "diseno", schema = "public")
public class Diseno extends EntidadGenerica {

	// Fields

	private Integer idDiseno;
	private MarcaNeumatico marcaNeumatico;
	private String nombre;
	private String descripcion;
//	private String imagen;
	private Set<DisenoMedida> disenoMedidas = new HashSet<DisenoMedida>(0);
	private byte[] imagen;

	// Constructors

	/** default constructor */
	public Diseno() {
	}

	/** minimal constructor */
	public Diseno(Integer idDiseno, MarcaNeumatico marcaNeumatico,
			String nombre, byte[] imagen, String status) {
		this.idDiseno = idDiseno;
		this.marcaNeumatico = marcaNeumatico;
		this.nombre = nombre;
		this.imagen = imagen;
		this.status = status;
	}

	/** full constructor */
	public Diseno(Integer idDiseno, MarcaNeumatico marcaNeumatico,
			String nombre, String descripcion, byte[] imagen, String status,
			Set<DisenoMedida> disenoMedidas) {
		this.idDiseno = idDiseno;
		this.marcaNeumatico = marcaNeumatico;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.status = status;
		this.disenoMedidas = disenoMedidas;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "DisenoSequence", sequenceName = "diseno_id_diseno_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "DisenoSequence")
	@Column(name = "id_diseno", unique = true, nullable = false)
	public Integer getIdDiseno() {
		return this.idDiseno;
	}

	public void setIdDiseno(Integer idDiseno) {
		this.idDiseno = idDiseno;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_marca_neumatico", nullable = false)
	public MarcaNeumatico getMarcaNeumatico() {
		return this.marcaNeumatico;
	}

	public void setMarcaNeumatico(MarcaNeumatico marcaNeumatico) {
		this.marcaNeumatico = marcaNeumatico;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "diseno")
	public Set<DisenoMedida> getDisenoMedidas() {
		return this.disenoMedidas;
	}

	public void setDisenoMedidas(Set<DisenoMedida> disenoMedidas) {
		this.disenoMedidas = disenoMedidas;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.idDiseno;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}