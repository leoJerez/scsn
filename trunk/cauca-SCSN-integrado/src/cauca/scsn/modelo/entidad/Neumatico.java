package cauca.scsn.modelo.entidad;

import java.util.Date;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

/**
 * Neumatico entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "neumatico", schema = "public")
public class Neumatico extends EntidadGenerica {

	// Fields

	private Integer idNeumatico;
	private Empresa empresa;
	private DisenoMedida disenoMedida;
	private Proveedor proveedor;
	private String codInterno;
	private String unidireccional;
	private String condicion;
	private String tipoNeumatico;
	@Digits(integer=3,fraction=2)
	private Double remanenteIngreso;
	@Digits(integer=3,fraction=2)
	private Double remanenteActual;
	private Integer idDisenoOriginal;
	private Integer idDisenoActual;
	private Double recorridoAcumulado;
	@Digits(integer=3,fraction=2)
	private Double presionActual;
	private String nroFactura;
	private Date fechaIngreso;
	private Double costo;
	private String estado;
	private Set<OperacionNeumatico> operacionNeumaticos = new HashSet<OperacionNeumatico>(
			0);
	private Set<Movimiento> movimientos = new HashSet<Movimiento>(0);
	private Set<NeumaticoVehiculo> neumaticoVehiculo = new HashSet<NeumaticoVehiculo>(0);

	// Constructors

	/** default constructor */
	public Neumatico() {
	}

	/** minimal constructor */
	public Neumatico(Integer idNeumatico, Empresa empresa,
			DisenoMedida disenoMedida, String codInterno, Proveedor idProveedor,
			String unidireccional, String condicion, String tipoNeumatico,
			Double remanenteIngreso, Integer idDisenoOriginal, Date fechaIngreso,
			Double presionActual, String nroFactura,Double costo, String estado, String status) {
		this.idNeumatico = idNeumatico;
		this.empresa = empresa;
		this.disenoMedida = disenoMedida;
		this.codInterno = codInterno;
		this.unidireccional = unidireccional;
		this.condicion = condicion;
		this.tipoNeumatico = tipoNeumatico;
		this.remanenteIngreso = remanenteIngreso;
		this.idDisenoOriginal = idDisenoOriginal;
		this.fechaIngreso = fechaIngreso;
		this.presionActual = presionActual;
		this.proveedor = idProveedor;
		this.nroFactura = nroFactura;
		this.costo = costo;
		this.estado = estado;
		this.status = status;
	}

	/** full constructor */
	public Neumatico(Integer idNeumatico, Empresa empresa,
			DisenoMedida disenoMedida, String codInterno, Proveedor idProveedor,
			String unidireccional, String condicion, String tipoNeumatico,
			Double remanenteIngreso, Double remanenteActual,
			Integer idDisenoOriginal, Integer idDisenoActual, Date fechaIngreso,
			Double recorridoAcumulado, Double presionActual, String nroFactura, Double costo, String estado,
			String status, Set<OperacionNeumatico> operacionNeumaticos,
			Set<Movimiento> movimientos) {
		this.proveedor = idProveedor;
		this.nroFactura = nroFactura;
		this.costo = costo;
		this.idNeumatico = idNeumatico;
		this.empresa = empresa;
		this.disenoMedida = disenoMedida;
		this.codInterno = codInterno;
		this.unidireccional = unidireccional;
		this.condicion = condicion;
		this.tipoNeumatico = tipoNeumatico;
		this.remanenteIngreso = remanenteIngreso;
		this.remanenteActual = remanenteActual;
		this.idDisenoOriginal = idDisenoOriginal;
		this.idDisenoActual = idDisenoActual;
		this.fechaIngreso = fechaIngreso;
		this.recorridoAcumulado = recorridoAcumulado;
		this.presionActual = presionActual;
		this.estado = estado;
		this.status = status;
		this.operacionNeumaticos = operacionNeumaticos;
		this.movimientos = movimientos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "NeumaticoSequence", sequenceName = "neumatico_id_neumatico_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "NeumaticoSequence")	
	@Column(name = "id_neumatico", unique = true, nullable = false)
	public Integer getIdNeumatico() {
		return this.idNeumatico;
	}

	public void setIdNeumatico(Integer idNeumatico) {
		this.idNeumatico = idNeumatico;
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
	@JoinColumns({
			@JoinColumn(name = "id_medida", referencedColumnName = "id_medida", nullable = false),
			@JoinColumn(name = "id_diseno", referencedColumnName = "id_diseno", nullable = false) })
	public DisenoMedida getDisenoMedida() {
		return this.disenoMedida;
	}

	public void setDisenoMedida(DisenoMedida disenoMedida) {
		this.disenoMedida = disenoMedida;
	}

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", nullable = false)
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", nullable = false)})
			//@JoinColumn(name = "nro_factura", referencedColumnName = "nro_factura", nullable = false) })
	 */
	
	@Column(name = "cod_interno", nullable = false)
	public String getCodInterno() {
		return this.codInterno;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proveedor", nullable = false)
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	@Column(name = "unidireccional", length = 1)
	public String getUnidireccional() {
		return this.unidireccional;
	}

	public void setUnidireccional(String unidireccional) {
		this.unidireccional = unidireccional;
	}

	@Column(name = "condicion", nullable = false, length = 1)
	public String getCondicion() {
		return this.condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	@Column(name = "tipo_neumatico", nullable = false, length = 1)
	public String getTipoNeumatico() {
		return this.tipoNeumatico;
	}

	public void setTipoNeumatico(String tipoNeumatico) {
		this.tipoNeumatico = tipoNeumatico;
	}

	@Column(name = "remanente_ingreso", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteIngreso() {
		return this.remanenteIngreso;
	}

	public void setRemanenteIngreso(Double remanenteIngreso) {
		this.remanenteIngreso = remanenteIngreso;
	}

	@Column(name = "remanente_actual", precision = 17, scale = 17)
	public Double getRemanenteActual() {
		return this.remanenteActual;
	}

	public void setRemanenteActual(Double remanenteActual) {
		this.remanenteActual = remanenteActual;
	}

	@Column(name = "id_diseno_original", nullable = false)
	public Integer getIdDisenoOriginal() {
		return this.idDisenoOriginal;
	}

	public void setIdDisenoOriginal(Integer idDisenoOriginal) {
		this.idDisenoOriginal = idDisenoOriginal;
	}

	@Column(name = "id_diseno_actual")
	public Integer getIdDisenoActual() {
		return this.idDisenoActual;
	}

	public void setIdDisenoActual(Integer idDisenoActual) {
		this.idDisenoActual = idDisenoActual;
	}

	@Column(name = "recorrido_acumulado", precision = 17, scale = 17)
	public Double getRecorridoAcumulado() {
		return this.recorridoAcumulado;
	}

	public void setRecorridoAcumulado(Double recorridoAcumulado) {
		this.recorridoAcumulado = recorridoAcumulado;
	}

	@Column(name = "presion_actual", nullable = false, precision = 17, scale = 17)
	public Double getPresionActual() {
		return this.presionActual;
	}

	public void setPresionActual(Double presionActual) {
		this.presionActual = presionActual;
	}

	@Column(name = "estado", nullable = false, length = 1)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "neumatico")
	public Set<OperacionNeumatico> getOperacionNeumaticos() {
		return this.operacionNeumaticos;
	}

	public void setOperacionNeumaticos(
			Set<OperacionNeumatico> operacionNeumaticos) {
		this.operacionNeumaticos = operacionNeumaticos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "neumatico")
	public Set<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Set<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idNeumatico;
	}

	@Override
	public String toString() {
		return null;
	}

	@Column(name = "nro_factura", nullable = false, length = 60)
	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	@Column(name = "costo", nullable = false, precision = 17, scale = 17)
	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_ingreso", nullable = false, length = 13)
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "neumatico")
	public Set<NeumaticoVehiculo> getNeumaticoVehiculo() {
		return neumaticoVehiculo;
	}

	public void setNeumaticoVehiculo(Set<NeumaticoVehiculo> neumaticoVehiculo) {
		this.neumaticoVehiculo = neumaticoVehiculo;
	}
	
}