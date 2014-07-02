package cauca.scsn.modelo.entidad;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the modelo_vehiculo database table.
 * 
 */
@Entity
@Table(name="modelo_vehiculo", schema="public")
public class ModeloVehiculo extends EntidadGenerica  {
	
	private Integer idModeloVehiculo;
	private String descripcion;
	private String nombre;
	private MarcaVehiculo marcaVehiculo;
	private TipoVehiculo tipoVehiculo;
	private Set<Vehiculo> vehiculos;

	public ModeloVehiculo() {
	}


	@Id
	@SequenceGenerator(name="MODELO_VEHICULO_IDMODELOVEHICULO_GENERATOR", sequenceName="modelo_vehiculo_id_modelo_vehiculo_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MODELO_VEHICULO_IDMODELOVEHICULO_GENERATOR")
	@Column(name="id_modelo_vehiculo", nullable = false)
	public Integer getIdModeloVehiculo() {
		return this.idModeloVehiculo;
	}

	public void setIdModeloVehiculo(Integer idModeloVehiculo) {
		this.idModeloVehiculo = idModeloVehiculo;
	}


	@Column(name = "descripcion", length = 255)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Column(name = "status", length = 1, nullable = false)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to MarcaVehiculo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca_vehiculo", nullable=false)
	public MarcaVehiculo getMarcaVehiculo() {
		return this.marcaVehiculo;
	}

	public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
		this.marcaVehiculo = marcaVehiculo;
	}


	//bi-directional many-to-one association to TipoVehiculo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_vehiculo", nullable = false)
	public TipoVehiculo getTipoVehiculo() {
		return this.tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}


	//bi-directional many-to-one association to Vehiculo
	@OneToMany(mappedBy="modeloVehiculo")
	public Set<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public void setVehiculos(Set<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public Vehiculo addVehiculo(Vehiculo vehiculo) {
		getVehiculos().add(vehiculo);
		vehiculo.setModeloVehiculo(this);

		return vehiculo;
	}

	public Vehiculo removeVehiculo(Vehiculo vehiculo) {
		getVehiculos().remove(vehiculo);
		vehiculo.setModeloVehiculo(null);

		return vehiculo;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}