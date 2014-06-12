package cauca.scsn.modelo.entidad;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cauca.scsn.modelo.entidad.id.MovimientoId;

/**
 * Movimiento entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "movimiento", schema = "public")
public class Movimiento extends EntidadGenerica {

	// Fields

	private MovimientoId id;
	private TipoDesgaste tipoDesgaste;
	private Neumatico neumatico;
	private Vehiculo vehiculo;
	private Double recorridoAcumulado;
	private Double remanenteSuperiorA;
	private Double remanenteSuperiorB;
	private Double remanenteSuperiorC;
	private Double remanenteIzquierdoA;
	private Double remanenteIzquierdoB;
	private Double remanenteIzquierdoC;
	private Double remanenteIzquierdoD;
	private Double remanenteDerechoA;
	private Double remanenteDerechoB;
	private Double remanenteDerechoC;
	private Double remanenteDerechoD;
	private Double remanenteDiagonalA;
	private Double remanenteDiagonalB;
	private Double remanenteDiagonalC;
	private Double remanenteDiagonalD;
	private Double remanenteSuperiorD;
	private Double remanenteMovimiento;
	private Integer posicionInicial;
	private Integer posicionFinal;
	private Double kilometraje;
	private Double presion;
	private String observaciones;

	// Constructors

	/** default constructor */
	public Movimiento() {
	}

	/** minimal constructor */
	public Movimiento(MovimientoId id, Neumatico neumatico, Vehiculo vehiculo,
			Double recorridoAcumulado, Double remanenteSuperiorA,
			Double remanenteSuperiorB, Double remanenteSuperiorC,
			Double remanenteSuperiorD, Double remanenteMovimiento,
			Double kilometraje, String status) {
		this.id = id;
		this.neumatico = neumatico;
		this.vehiculo = vehiculo;
		this.recorridoAcumulado = recorridoAcumulado;
		this.remanenteSuperiorA = remanenteSuperiorA;
		this.remanenteSuperiorB = remanenteSuperiorB;
		this.remanenteSuperiorC = remanenteSuperiorC;
		this.remanenteSuperiorD = remanenteSuperiorD;
		this.remanenteMovimiento = remanenteMovimiento;
		this.kilometraje = kilometraje;
		this.status = status;
	}

	/** full constructor */
	public Movimiento(MovimientoId id, TipoDesgaste tipoDesgaste,
			Neumatico neumatico, Vehiculo vehiculo, Double recorridoAcumulado,
			Double remanenteSuperiorA, Double remanenteSuperiorB,
			Double remanenteSuperiorC, Double remanenteIzquierdoA,
			Double remanenteIzquierdoB, Double remanenteIzquierdoC,
			Double remanenteIzquierdoD, Double remanenteDerechoA,
			Double remanenteDerechoB, Double remanenteDerechoC,
			Double remanenteDerechoD, Double remanenteDiagonalA,
			Double remanenteDiagonalB, Double remanenteDiagonalC,
			Double remanenteDiagonalD, Double remanenteSuperiorD,
			Double remanenteMovimiento, Integer posicionInicial,
			Integer posicionFinal, Double kilometraje, Double presion,
			String observaciones, String status) {
		this.id = id;
		this.tipoDesgaste = tipoDesgaste;
		this.neumatico = neumatico;
		this.vehiculo = vehiculo;
		this.recorridoAcumulado = recorridoAcumulado;
		this.remanenteSuperiorA = remanenteSuperiorA;
		this.remanenteSuperiorB = remanenteSuperiorB;
		this.remanenteSuperiorC = remanenteSuperiorC;
		this.remanenteIzquierdoA = remanenteIzquierdoA;
		this.remanenteIzquierdoB = remanenteIzquierdoB;
		this.remanenteIzquierdoC = remanenteIzquierdoC;
		this.remanenteIzquierdoD = remanenteIzquierdoD;
		this.remanenteDerechoA = remanenteDerechoA;
		this.remanenteDerechoB = remanenteDerechoB;
		this.remanenteDerechoC = remanenteDerechoC;
		this.remanenteDerechoD = remanenteDerechoD;
		this.remanenteDiagonalA = remanenteDiagonalA;
		this.remanenteDiagonalB = remanenteDiagonalB;
		this.remanenteDiagonalC = remanenteDiagonalC;
		this.remanenteDiagonalD = remanenteDiagonalD;
		this.remanenteSuperiorD = remanenteSuperiorD;
		this.remanenteMovimiento = remanenteMovimiento;
		this.posicionInicial = posicionInicial;
		this.posicionFinal = posicionFinal;
		this.kilometraje = kilometraje;
		this.presion = presion;
		this.observaciones = observaciones;
		this.status = status;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idVehiculo", column = @Column(name = "id_vehiculo", nullable = false)),
			@AttributeOverride(name = "idNeumatico", column = @Column(name = "id_neumatico", nullable = false)),
			@AttributeOverride(name = "tipoMovimiento", column = @Column(name = "tipo_movimiento", nullable = false, length = 1)),
			@AttributeOverride(name = "fecha", column = @Column(name = "fecha", nullable = false, length = 13)) })
	public MovimientoId getId() {
		return this.id;
	}

	public void setId(MovimientoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_desgaste")
	public TipoDesgaste getTipoDesgaste() {
		return this.tipoDesgaste;
	}

	public void setTipoDesgaste(TipoDesgaste tipoDesgaste) {
		this.tipoDesgaste = tipoDesgaste;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_neumatico", nullable = false, insertable = false, updatable = false)
	public Neumatico getNeumatico() {
		return this.neumatico;
	}

	public void setNeumatico(Neumatico neumatico) {
		this.neumatico = neumatico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo", nullable = false, insertable = false, updatable = false)
	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Column(name = "recorrido_acumulado", nullable = false, precision = 17, scale = 17)
	public Double getRecorridoAcumulado() {
		return this.recorridoAcumulado;
	}

	public void setRecorridoAcumulado(Double recorridoAcumulado) {
		this.recorridoAcumulado = recorridoAcumulado;
	}

	@Column(name = "remanente_superior_a", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteSuperiorA() {
		return this.remanenteSuperiorA;
	}

	public void setRemanenteSuperiorA(Double remanenteSuperiorA) {
		this.remanenteSuperiorA = remanenteSuperiorA;
	}

	@Column(name = "remanente_superior_b", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteSuperiorB() {
		return this.remanenteSuperiorB;
	}

	public void setRemanenteSuperiorB(Double remanenteSuperiorB) {
		this.remanenteSuperiorB = remanenteSuperiorB;
	}

	@Column(name = "remanente_superior_c", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteSuperiorC() {
		return this.remanenteSuperiorC;
	}

	public void setRemanenteSuperiorC(Double remanenteSuperiorC) {
		this.remanenteSuperiorC = remanenteSuperiorC;
	}

	@Column(name = "remanente_izquierdo_a", precision = 17, scale = 17)
	public Double getRemanenteIzquierdoA() {
		return this.remanenteIzquierdoA;
	}

	public void setRemanenteIzquierdoA(Double remanenteIzquierdoA) {
		this.remanenteIzquierdoA = remanenteIzquierdoA;
	}

	@Column(name = "remanente_izquierdo_b", precision = 17, scale = 17)
	public Double getRemanenteIzquierdoB() {
		return this.remanenteIzquierdoB;
	}

	public void setRemanenteIzquierdoB(Double remanenteIzquierdoB) {
		this.remanenteIzquierdoB = remanenteIzquierdoB;
	}

	@Column(name = "remanente_izquierdo_c", precision = 17, scale = 17)
	public Double getRemanenteIzquierdoC() {
		return this.remanenteIzquierdoC;
	}

	public void setRemanenteIzquierdoC(Double remanenteIzquierdoC) {
		this.remanenteIzquierdoC = remanenteIzquierdoC;
	}

	@Column(name = "remanente_izquierdo_d", precision = 17, scale = 17)
	public Double getRemanenteIzquierdoD() {
		return this.remanenteIzquierdoD;
	}

	public void setRemanenteIzquierdoD(Double remanenteIzquierdoD) {
		this.remanenteIzquierdoD = remanenteIzquierdoD;
	}

	@Column(name = "remanente_derecho_a", precision = 17, scale = 17)
	public Double getRemanenteDerechoA() {
		return this.remanenteDerechoA;
	}

	public void setRemanenteDerechoA(Double remanenteDerechoA) {
		this.remanenteDerechoA = remanenteDerechoA;
	}

	@Column(name = "remanente_derecho_b", precision = 17, scale = 17)
	public Double getRemanenteDerechoB() {
		return this.remanenteDerechoB;
	}

	public void setRemanenteDerechoB(Double remanenteDerechoB) {
		this.remanenteDerechoB = remanenteDerechoB;
	}

	@Column(name = "remanente_derecho_c", precision = 17, scale = 17)
	public Double getRemanenteDerechoC() {
		return this.remanenteDerechoC;
	}

	public void setRemanenteDerechoC(Double remanenteDerechoC) {
		this.remanenteDerechoC = remanenteDerechoC;
	}

	@Column(name = "remanente_derecho_d", precision = 17, scale = 17)
	public Double getRemanenteDerechoD() {
		return this.remanenteDerechoD;
	}

	public void setRemanenteDerechoD(Double remanenteDerechoD) {
		this.remanenteDerechoD = remanenteDerechoD;
	}

	@Column(name = "remanente_diagonal_a", precision = 17, scale = 17)
	public Double getRemanenteDiagonalA() {
		return this.remanenteDiagonalA;
	}

	public void setRemanenteDiagonalA(Double remanenteDiagonalA) {
		this.remanenteDiagonalA = remanenteDiagonalA;
	}

	@Column(name = "remanente_diagonal_b", precision = 17, scale = 17)
	public Double getRemanenteDiagonalB() {
		return this.remanenteDiagonalB;
	}

	public void setRemanenteDiagonalB(Double remanenteDiagonalB) {
		this.remanenteDiagonalB = remanenteDiagonalB;
	}

	@Column(name = "remanente_diagonal_c", precision = 17, scale = 17)
	public Double getRemanenteDiagonalC() {
		return this.remanenteDiagonalC;
	}

	public void setRemanenteDiagonalC(Double remanenteDiagonalC) {
		this.remanenteDiagonalC = remanenteDiagonalC;
	}

	@Column(name = "remanente_diagonal_d", precision = 17, scale = 17)
	public Double getRemanenteDiagonalD() {
		return this.remanenteDiagonalD;
	}

	public void setRemanenteDiagonalD(Double remanenteDiagonalD) {
		this.remanenteDiagonalD = remanenteDiagonalD;
	}

	@Column(name = "remanente_superior_d", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteSuperiorD() {
		return this.remanenteSuperiorD;
	}

	public void setRemanenteSuperiorD(Double remanenteSuperiorD) {
		this.remanenteSuperiorD = remanenteSuperiorD;
	}

	@Column(name = "remanente_movimiento", nullable = false, precision = 17, scale = 17)
	public Double getRemanenteMovimiento() {
		return this.remanenteMovimiento;
	}

	public void setRemanenteMovimiento(Double remanenteMovimiento) {
		this.remanenteMovimiento = remanenteMovimiento;
	}

	@Column(name = "posicion_inicial")
	public Integer getPosicionInicial() {
		return this.posicionInicial;
	}

	public void setPosicionInicial(Integer posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	@Column(name = "posicion_final")
	public Integer getPosicionFinal() {
		return this.posicionFinal;
	}

	public void setPosicionFinal(Integer posicionFinal) {
		this.posicionFinal = posicionFinal;
	}

	@Column(name = "kilometraje", nullable = false, precision = 17, scale = 17)
	public Double getKilometraje() {
		return this.kilometraje;
	}

	public void setKilometraje(Double kilometraje) {
		this.kilometraje = kilometraje;
	}

	@Column(name = "presion", precision = 17, scale = 17)
	public Double getPresion() {
		return this.presion;
	}

	public void setPresion(Double presion) {
		this.presion = presion;
	}

	@Column(name = "observaciones", length = 150)
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
		return this.id;
	}

	@Override
	public String toString() {
		return null;
	}

}