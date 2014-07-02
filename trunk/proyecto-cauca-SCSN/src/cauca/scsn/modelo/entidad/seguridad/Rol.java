package cauca.scsn.modelo.entidad.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name="rol", schema="public")
public class Rol extends cauca.scsn.modelo.entidad.EntidadGenerica  {
	
	private Integer idRol;
	private String nombre;
	
	private Set<OperacionRol> interfazRols;

	public Rol() {
	}


	@Id
	@SequenceGenerator(name="ROL_IDROL_GENERATOR", sequenceName="rol_id_rol_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_IDROL_GENERATOR")
	@Column(name="id_rol")
	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	@Column(name="nombre")
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to InterfazRol
	@OneToMany(mappedBy="rol")
	public Set<OperacionRol> getInterfazRols() {
		return this.interfazRols;
	}

	public void setInterfazRols(Set<OperacionRol> interfazRols) {
		this.interfazRols = interfazRols;
	}

	public OperacionRol addInterfazRol(OperacionRol interfazRol) {
		getInterfazRols().add(interfazRol);
		interfazRol.setRol(this);

		return interfazRol;
	}

	public OperacionRol removeInterfazRol(OperacionRol interfazRol) {
		getInterfazRols().remove(interfazRol);
		interfazRol.setRol(null);

		return interfazRol;
	}


	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.idRol;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}