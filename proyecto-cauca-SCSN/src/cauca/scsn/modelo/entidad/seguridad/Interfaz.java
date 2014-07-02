package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the interfaz database table.
 * 
 */
@Entity
@Table(name="interfaz", schema="public")
public class Interfaz extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	
	private Integer idInterfaz;
	private String nombreInterfaz;
	private Set<Operacion> operacion;

	public Interfaz() {
	}


	@Id
	@SequenceGenerator(name="INTERFAZ_IDINTERFAZ_GENERATOR", sequenceName="interfaz_id_interfaz_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERFAZ_IDINTERFAZ_GENERATOR")
	@Column(name="id_interfaz")
	public Integer getIdInterfaz() {
		return this.idInterfaz;
	}

	public void setIdInterfaz(Integer idInterfaz) {
		this.idInterfaz = idInterfaz;
	}


	@Column(name="nombre_interfaz")
	public String getNombreInterfaz() {
		return this.nombreInterfaz;
	}

	public void setNombreInterfaz(String nombreInterfaz) {
		this.nombreInterfaz = nombreInterfaz;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy="interfaz")
	public Set<Operacion> getOperacion() {
		return operacion;
	}


	public void setOperacion(Set<Operacion> operacion) {
		this.operacion = operacion;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getIdInterfaz();
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}