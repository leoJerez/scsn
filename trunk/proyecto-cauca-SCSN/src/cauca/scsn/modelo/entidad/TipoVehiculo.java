package cauca.scsn.modelo.entidad;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the tipo_vehiculo database table.
 * 
 */
@Entity
@Table(name="tipo_vehiculo", schema="public")
public class TipoVehiculo extends EntidadGenerica  {

	private Integer idTipoVehiculo;
	private String dentroCarretera;
	private String descripcion;
	private String nombre;
	private Set<ModeloVehiculo> modeloVehiculos;

	public TipoVehiculo() {
	}


	@Id
	@SequenceGenerator(name="TIPO_VEHICULO_IDTIPOVEHICULO_GENERATOR", sequenceName="tipo_vehiculo_id_tipo_vehiculo_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_VEHICULO_IDTIPOVEHICULO_GENERATOR")
	@Column(name="id_tipo_vehiculo", nullable = false)
	public Integer getIdTipoVehiculo() {
		return this.idTipoVehiculo;
	}

	public void setIdTipoVehiculo(Integer idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}


	@Column(name="dentro_carretera", nullable = false, length = 1)
	public String getDentroCarretera() {
		return this.dentroCarretera;
	}

	public void setDentroCarretera(String dentroCarretera) {
		this.dentroCarretera = dentroCarretera;
	}


	@Column(name = "descripcion", length = 150)
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


	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to ModeloVehiculo
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="tipoVehiculo")
	public Set<ModeloVehiculo> getModeloVehiculos() {
		return this.modeloVehiculos;
	}

	public void setModeloVehiculos(Set<ModeloVehiculo> modeloVehiculos) {
		this.modeloVehiculos = modeloVehiculos;
	}

	public ModeloVehiculo addModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		getModeloVehiculos().add(modeloVehiculo);
		modeloVehiculo.setTipoVehiculo(this);

		return modeloVehiculo;
	}

	public ModeloVehiculo removeModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		getModeloVehiculos().remove(modeloVehiculo);
		modeloVehiculo.setTipoVehiculo(null);

		return modeloVehiculo;
	}


	@Override
	public Object getPrimaryKey() {
		return getIdTipoVehiculo();
	}


	@Override
	public String toString() {
		return null;
	}

}