package cauca.scsn.modelo.entidad;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the vehiculo database table.
 * 
 */
@Entity
@Table(name = "vehiculo", schema = "public")
public class Vehiculo extends EntidadGenerica  {
	
	private Integer idVehiculo;
	private String anoFabricacion;
	private Empresa empresa;
	private EsquemaEje esquemaEjes;
	private double kilometraje;
	private String placa;
	private ModeloVehiculo modeloVehiculo;
	private Set<EjeTraccion> ejeTraccion = new HashSet<EjeTraccion>(0);
	private Set<NeumaticoVehiculo> neumaticoVehiculo = new HashSet<NeumaticoVehiculo>(0);

	public Vehiculo() {
	}


	@Id
	@SequenceGenerator(name="VEHICULO_IDVEHICULO_GENERATOR", sequenceName="vehiculo_id_vehiculo_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VEHICULO_IDVEHICULO_GENERATOR")
	@Column(name="id_vehiculo", nullable = false)
	public Integer getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}


	@Column(name="ano_fabricacion", length = 5)
	public String getAnoFabricacion() {
		return this.anoFabricacion;
	}

	public void setAnoFabricacion(String anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_esquema_ejes", nullable = false)
	public EsquemaEje getEsquemaEjes() {
		return this.esquemaEjes;
	}

	public void setEsquemaEjes(EsquemaEje esquemaEje) {
		this.esquemaEjes = esquemaEje;
	}


	@Column(name = "kilometraje", nullable = false, length = 8)
	public double getKilometraje() {
		return this.kilometraje;
	}

	public void setKilometraje(double kilometraje) {
		this.kilometraje = kilometraje;
	}


	@Column(name = "placa", nullable = false, length = 10)
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}


	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to ModeloVehiculo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_modelo_vehiculo", nullable = false)
	public ModeloVehiculo getModeloVehiculo() {
		return this.modeloVehiculo;
	}

	public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}


	@Override
	public Object getPrimaryKey() {
		return getIdVehiculo();
	}


	@Override
	public String toString() {
		return null;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehiculo")
	public Set<NeumaticoVehiculo> getNeumaticoVehiculo() {
		return neumaticoVehiculo;
	}


	public void setNeumaticoVehiculo(Set<NeumaticoVehiculo> neumaticoVehiculo) {
		this.neumaticoVehiculo = neumaticoVehiculo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehiculo")
	public Set<EjeTraccion> getEjeTraccion() {
		return ejeTraccion;
	}


	public void setEjeTraccion(Set<EjeTraccion> ejeTraccion) {
		this.ejeTraccion = ejeTraccion;
	}

}