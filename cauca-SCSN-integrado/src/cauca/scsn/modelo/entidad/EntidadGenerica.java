package cauca.scsn.modelo.entidad;

import java.io.Serializable;

import javax.persistence.Column;


public abstract class EntidadGenerica implements Serializable {

	public final static String DATA_ACTIVA = "A";
	public final static String DATA_INACTIVA = "I";

	protected String status = DATA_ACTIVA;

	public EntidadGenerica() {
		super();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other.getClass().equals(this.getClass())))
			return false;
		EntidadGenerica castOther = (EntidadGenerica) other;
		return ((this.getPrimaryKey() == castOther.getPrimaryKey()) || 
				(this.getPrimaryKey() != null && 
				castOther.getPrimaryKey() != null && 
				this.getPrimaryKey().equals(castOther.getPrimaryKey())));	
	}

	public void activar() {
		this.status = DATA_ACTIVA;
	}

	public void desactivar() {
		this.status = DATA_INACTIVA;
	}

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract Object getPrimaryKey();

	public abstract String toString();

}
