package cauca.scsn.modelo.entidad.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DisenoMedidaId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class DisenoMedidaId implements java.io.Serializable {

	// Fields

	private Integer idMedida;
	private Integer idDiseno;

	// Constructors

	/** default constructor */
	public DisenoMedidaId() {
	}

	/** full constructor */
	public DisenoMedidaId(Integer idMedida, Integer idDiseno) {
		this.idMedida = idMedida;
		this.idDiseno = idDiseno;
	}

	// Property accessors

	@Column(name = "id_medida", nullable = false)
	public Integer getIdMedida() {
		return this.idMedida;
	}

	public void setIdMedida(Integer idMedida) {
		this.idMedida = idMedida;
	}

	@Column(name = "id_diseno", nullable = false)
	public Integer getIdDiseno() {
		return this.idDiseno;
	}

	public void setIdDiseno(Integer idDiseno) {
		this.idDiseno = idDiseno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DisenoMedidaId))
			return false;
		DisenoMedidaId castOther = (DisenoMedidaId) other;

		return ((this.getIdMedida() == castOther.getIdMedida()) || (this
				.getIdMedida() != null && castOther.getIdMedida() != null && this
				.getIdMedida().equals(castOther.getIdMedida())))
				&& ((this.getIdDiseno() == castOther.getIdDiseno()) || (this
						.getIdDiseno() != null
						&& castOther.getIdDiseno() != null && this
						.getIdDiseno().equals(castOther.getIdDiseno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdMedida() == null ? 0 : this.getIdMedida().hashCode());
		result = 37 * result
				+ (getIdDiseno() == null ? 0 : this.getIdDiseno().hashCode());
		return result;
	}

}