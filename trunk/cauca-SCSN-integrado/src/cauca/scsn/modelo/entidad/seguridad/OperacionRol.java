package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the interfaz_rol database table.
 * 
 */
@Entity
@Table(name="operacion_rol", schema="public")
public class OperacionRol extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	private Integer idOperacionRol;
	private Operacion operacion;
	private Rol rol;
//	private Set<InterfazRolOperacion> interfazRolOperacion = new HashSet<InterfazRolOperacion>(); 
	
	public OperacionRol() {
	}


	@Id
	@SequenceGenerator(name="INTERFAZ_ROL_IDINTERFAZROL_GENERATOR", sequenceName="interfaz_rol_id_interfaz_rol_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERFAZ_ROL_IDINTERFAZROL_GENERATOR")
	@Column(name="id_operacion_rol")
	public Integer getIdOperacionRol() {
		return this.idOperacionRol;
	}

	public void setIdOperacionRol(Integer idOperacionRol) {
		this.idOperacionRol = idOperacionRol;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_operacion", nullable = false)
	public Operacion getOperacion() {
		return this.operacion;
	}

	public void setOperacion(Operacion idOperacion) {
		this.operacion = idOperacion;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rol", nullable = false)
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol idRol) {
		this.rol = idRol;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
//	@OneToMany(mappedBy="interfazRol")
//	public Set<InterfazRolOperacion> getInterfazRolOperacion() {
//		return interfazRolOperacion;
//	}
//
//
//	public void setInterfazRolOperacion(
//			Set<InterfazRolOperacion> interfazRolOperacions) {
//		this.interfazRolOperacion = interfazRolOperacions;
//	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getIdOperacionRol();
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}