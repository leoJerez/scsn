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
 * EsquemaEje entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "esquema_eje", schema = "public")
public class EsquemaEje extends EntidadGenerica {

	// Fields

	private Integer idEsquemaEje;
	private Empresa empresa;
	private String nombreEsquema;
	private String descripcion;
	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>(0);
	private String neumatico1;
	private String neumatico2;
	private String neumatico3;
	private String neumatico4;
	private String neumatico5;
	private String neumatico6;
	private String neumatico7;
	private String neumatico8;
	private String neumatico9;
	private String neumatico10;
	private String neumatico11;
	private String neumatico12;
	private String neumatico13;
	private String neumatico14;
	private String neumatico15;
	private String neumatico16;
	private String neumatico17;
	private String neumatico18;
	private String neumatico19;
	private String neumatico20;
	private String neumatico21;
	private String neumatico22;
	private String eje1;
	private String eje2;
	private String eje3;
	private String eje4;
	private String eje5;
	private String tipo;

	// Constructors

	/** default constructor */
	public EsquemaEje() {
	}
	
	public EsquemaEje(Integer idEsquemaEje, Empresa empresa,
			String nombreEsquema, String descripcion, Set<Vehiculo> vehiculos,
			String neumatico1, String neumatico2, String neumatico3,
			String neumatico4, String neumatico5, String neumatico6,
			String neumatico7, String neumatico8, String neumatico9,
			String neumatico10, String neumatico11, String neumatico12,
			String neumatico13, String neumatico14, String neumatico15,
			String neumatico16, String neumatico17, String neumatico18,
			String neumatico19, String neumatico20, String neumatico21, String neumatico22, String eje1, String eje2,
			String eje3, String eje4, String eje5, String tipo) {
		super();
		this.idEsquemaEje = idEsquemaEje;
		this.empresa = empresa;
		this.nombreEsquema = nombreEsquema;
		this.descripcion = descripcion;
		this.vehiculos = vehiculos;
		this.neumatico1 = neumatico1;
		this.neumatico2 = neumatico2;
		this.neumatico3 = neumatico3;
		this.neumatico4 = neumatico4;
		this.neumatico5 = neumatico5;
		this.neumatico6 = neumatico6;
		this.neumatico7 = neumatico7;
		this.neumatico8 = neumatico8;
		this.neumatico9 = neumatico9;
		this.neumatico10 = neumatico10;
		this.neumatico11 = neumatico11;
		this.neumatico12 = neumatico12;
		this.neumatico13 = neumatico13;
		this.neumatico14 = neumatico14;
		this.neumatico15 = neumatico15;
		this.neumatico16 = neumatico16;
		this.neumatico17 = neumatico17;
		this.neumatico18 = neumatico18;
		this.neumatico19 = neumatico19;
		this.neumatico20 = neumatico20;
		this.neumatico21 = neumatico21;
		this.neumatico22 = neumatico22;
		this.eje1 = eje1;
		this.eje2 = eje2;
		this.eje3 = eje3;
		this.eje4 = eje4;
		this.eje5 = eje5;
		this.tipo = tipo;
	}


	/** minimal constructor */
	public EsquemaEje(Integer idEsquemaEje, Empresa empresa,
			String nombreEsquema, String status) {
		this.idEsquemaEje = idEsquemaEje;
		this.empresa = empresa;
		this.nombreEsquema = nombreEsquema;
		this.status = status;
	}

	/** full constructor */
	public EsquemaEje(Integer idEsquemaEje, Empresa empresa,
			String nombreEsquema, String descripcion, String status,
			Set<Vehiculo> vehiculos) {
		this.idEsquemaEje = idEsquemaEje;
		this.empresa = empresa;
		this.nombreEsquema = nombreEsquema;
		this.descripcion = descripcion;
		this.status = status;
		this.vehiculos = vehiculos;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "EsquemaEjeSequence", sequenceName = "esquema_eje_id_esquema_eje_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "EsquemaEjeSequence")	
	@Column(name = "id_esquema_eje", unique = true, nullable = false)
	public Integer getIdEsquemaEje() {
		return this.idEsquemaEje;
	}

	public void setIdEsquemaEje(Integer idEsquemaEje) {
		this.idEsquemaEje = idEsquemaEje;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(name = "nombre_esquema", nullable = false, length = 60)
	public String getNombreEsquema() {
		return this.nombreEsquema;
	}

	public void setNombreEsquema(String nombreEsquema) {
		this.nombreEsquema = nombreEsquema;
	}

	@Column(name = "descripcion")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "esquemaEjes")
	public Set<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public void setVehiculos(Set<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	@Column(name = "neumatico1", nullable = true)
	public String getNeumatico1() {
		return neumatico1;
	}

	public void setNeumatico1(String neumatico1) {
		this.neumatico1 = neumatico1;
	}

	@Column(name = "neumatico2", nullable = true)
	public String getNeumatico2() {
		return neumatico2;
	}

	public void setNeumatico2(String neumatico2) {
		this.neumatico2 = neumatico2;
	}

	@Column(name = "neumatico3", nullable = true)
	public String getNeumatico3() {
		return neumatico3;
	}

	public void setNeumatico3(String neumatico3) {
		this.neumatico3 = neumatico3;
	}

	@Column(name = "neumatico4", nullable = true)
	public String getNeumatico4() {
		return neumatico4;
	}

	public void setNeumatico4(String neumatico4) {
		this.neumatico4 = neumatico4;
	}

	@Column(name = "neumatico5", nullable = true)
	public String getNeumatico5() {
		return neumatico5;
	}

	public void setNeumatico5(String neumatico5) {
		this.neumatico5 = neumatico5;
	}

	@Column(name = "neumatico6", nullable = true)
	public String getNeumatico6() {
		return neumatico6;
	}

	public void setNeumatico6(String neumatico6) {
		this.neumatico6 = neumatico6;
	}

	@Column(name = "neumatico7", nullable = true)
	public String getNeumatico7() {
		return neumatico7;
	}

	public void setNeumatico7(String neumatico7) {
		this.neumatico7 = neumatico7;
	}

	@Column(name = "neumatico8", nullable = true)
	public String getNeumatico8() {
		return neumatico8;
	}

	public void setNeumatico8(String neumatico8) {
		this.neumatico8 = neumatico8;
	}

	@Column(name = "neumatico9", nullable = true)
	public String getNeumatico9() {
		return neumatico9;
	}

	public void setNeumatico9(String neumatico9) {
		this.neumatico9 = neumatico9;
	}

	@Column(name = "neumatico10", nullable = true)
	public String getNeumatico10() {
		return neumatico10;
	}

	public void setNeumatico10(String neumatico10) {
		this.neumatico10 = neumatico10;
	}

	@Column(name = "neumatico11", nullable = true)
	public String getNeumatico11() {
		return neumatico11;
	}

	public void setNeumatico11(String neumatico11) {
		this.neumatico11 = neumatico11;
	}

	@Column(name = "neumatico12", nullable = true)
	public String getNeumatico12() {
		return neumatico12;
	}

	public void setNeumatico12(String neumatico12) {
		this.neumatico12 = neumatico12;
	}

	@Column(name = "neumatico13", nullable = true)
	public String getNeumatico13() {
		return neumatico13;
	}

	public void setNeumatico13(String neumatico13) {
		this.neumatico13 = neumatico13;
	}

	@Column(name = "neumatico14", nullable = true)
	public String getNeumatico14() {
		return neumatico14;
	}

	public void setNeumatico14(String neumatico14) {
		this.neumatico14 = neumatico14;
	}

	@Column(name = "neumatico15", nullable = true)
	public String getNeumatico15() {
		return neumatico15;
	}

	public void setNeumatico15(String neumatico15) {
		this.neumatico15 = neumatico15;
	}

	@Column(name = "neumatico16", nullable = true)
	public String getNeumatico16() {
		return neumatico16;
	}

	public void setNeumatico16(String neumatico16) {
		this.neumatico16 = neumatico16;
	}

	@Column(name = "neumatico17", nullable = true)
	public String getNeumatico17() {
		return neumatico17;
	}

	public void setNeumatico17(String neumatico17) {
		this.neumatico17 = neumatico17;
	}

	@Column(name = "neumatico18", nullable = true)
	public String getNeumatico18() {
		return neumatico18;
	}

	public void setNeumatico18(String neumatico18) {
		this.neumatico18 = neumatico18;
	}

	@Column(name = "neumatico19", nullable = true)
	public String getNeumatico19() {
		return neumatico19;
	}

	public void setNeumatico19(String neumatico19) {
		this.neumatico19 = neumatico19;
	}

	@Column(name = "neumatico20", nullable = true)
	public String getNeumatico20() {
		return neumatico20;
	}

	public void setNeumatico20(String neumatico20) {
		this.neumatico20 = neumatico20;
	}
	
	@Column(name = "neumatico21", nullable = true)
	public String getNeumatico21() {
		return neumatico21;
	}

	public void setNeumatico21(String neumatico21) {
		this.neumatico21 = neumatico21;
	}
	
	@Column(name = "neumatico22", nullable = true)
	public String getNeumatico22() {
		return neumatico22;
	}

	public void setNeumatico22(String neumatico22) {
		this.neumatico22 = neumatico22;
	}

	@Column(name = "eje1", nullable = true)
	public String getEje1() {
		return eje1;
	}

	public void setEje1(String eje1) {
		this.eje1 = eje1;
	}

	@Column(name = "eje2", nullable = true)
	public String getEje2() {
		return eje2;
	}

	public void setEje2(String eje2) {
		this.eje2 = eje2;
	}

	@Column(name = "eje3", nullable = true)
	public String getEje3() {
		return eje3;
	}

	public void setEje3(String eje3) {
		this.eje3 = eje3;
	}

	@Column(name = "eje4", nullable = true)
	public String getEje4() {
		return eje4;
	}

	public void setEje4(String eje4) {
		this.eje4 = eje4;
	}

	@Column(name = "eje5", nullable = true)
	public String getEje5() {
		return eje5;
	}

	public void setEje5(String eje5) {
		this.eje5 = eje5;
	}

	@Column(name = "tipo", nullable = false, length = 60)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object getPrimaryKey() {
		return this.idEsquemaEje;
	}

	@Override
	public String toString() {
		return null;
	}

}