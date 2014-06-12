package cauca.scsn.modelo.entidad;

public class DatosNeumaticoRueda {
	
	private boolean				montado;
	private boolean				activo;
	private NeumaticoVehiculo	neumaticoVehiculo;
	
	public DatosNeumaticoRueda() {
		super();
	}

	public DatosNeumaticoRueda(boolean montado, boolean activo,
			NeumaticoVehiculo neumaticoVehiculo) {
		super();
		this.montado = montado;
		this.activo = activo;
		this.neumaticoVehiculo = neumaticoVehiculo;
	}

	public boolean isMontado() {
		return montado;
	}

	public void setMontado(boolean montado) {
		this.montado = montado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public NeumaticoVehiculo getNeumaticoVehiculo() {
		return neumaticoVehiculo;
	}

	public void setNeumaticoVehiculo(NeumaticoVehiculo neumaticoVehiculo) {
		this.neumaticoVehiculo = neumaticoVehiculo;
	}
}
