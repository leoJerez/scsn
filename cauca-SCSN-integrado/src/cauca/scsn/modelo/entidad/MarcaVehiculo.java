package cauca.scsn.modelo.entidad;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the marca_vehiculo database table.
 * 
 */
@Entity
@Table(name="marca_vehiculo" , schema="public")
public class MarcaVehiculo extends EntidadGenerica  {
	
	private Integer idMarcaVehiculo;
	private String descripcion;
	private Empresa empresa;
	private String nombre;
	private Set<ModeloVehiculo> modeloVehiculos;

	public MarcaVehiculo() {
	}


	@Id
	@SequenceGenerator(name="MARCA_VEHICULO_IDMARCAVEHICULO_GENERATOR", sequenceName="marca_vehiculo_id_marca_vehiculo_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MARCA_VEHICULO_IDMARCAVEHICULO_GENERATOR")
	@Column(name="id_marca_vehiculo", nullable = false)
	public Integer getIdMarcaVehiculo() {
		return this.idMarcaVehiculo;
	}

	public void setIdMarcaVehiculo(Integer idMarcaVehiculo) {
		this.idMarcaVehiculo = idMarcaVehiculo;
	}


	@Column(name = "descripcion", length = 255)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	@Column(name = "nombre", nullable = false, length=60)
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="marcaVehiculo")
	public Set<ModeloVehiculo> getModeloVehiculos() {
		return this.modeloVehiculos;
	}

	public void setModeloVehiculos(Set<ModeloVehiculo> modeloVehiculos) {
		this.modeloVehiculos = modeloVehiculos;
	}

	public ModeloVehiculo addModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		getModeloVehiculos().add(modeloVehiculo);
		modeloVehiculo.setMarcaVehiculo(this);

		return modeloVehiculo;
	}

	public ModeloVehiculo removeModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		getModeloVehiculos().remove(modeloVehiculo);
		modeloVehiculo.setMarcaVehiculo(null);

		return modeloVehiculo;
	}


	@Override
	public Object getPrimaryKey() {
		return getIdMarcaVehiculo();
	}


	@Override
	public String toString() {
		return null;
	}

}