package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the operacion database table.
 * 
 */
@Entity
@Table(name="operacion", schema="public")
public class Operacion extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	
	private Integer idOperacion;
	private String nombreOperacion;
	private Interfaz interfaz;
	

	public Operacion() {
	}


	@Id
	@SequenceGenerator(name="OPERACION_IDOPERACION_GENERATOR", sequenceName="operacion_id_operacion_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OPERACION_IDOPERACION_GENERATOR")
	@Column(name="id_operacion")
	public Integer getIdOperacion() {
		return this.idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}


	@Column(name="nombre_operacion")
	public String getNombreOperacion() {
		return this.nombreOperacion;
	}

	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_interfaz")
	public Interfaz getInterfaz() {
		return interfaz;
	}


	public void setInterfaz(Interfaz interfaz) {
		this.interfaz = interfaz;
	}


	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getIdOperacion();
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}