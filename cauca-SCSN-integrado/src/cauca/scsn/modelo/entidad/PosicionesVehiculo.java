package cauca.scsn.modelo.entidad;

public class PosicionesVehiculo {
	
	private boolean disabled;
	private String nombreRueda;
	private Movimiento movimiento;
	
	
	public PosicionesVehiculo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PosicionesVehiculo(boolean disabled, String nombreRueda,
			Movimiento movimiento) {
		super();
		this.disabled = disabled;
		this.nombreRueda = nombreRueda;
		this.movimiento = movimiento;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getNombreRueda() {
		return nombreRueda;
	}

	public void setNombreRueda(String nombreRueda) {
		this.nombreRueda = nombreRueda;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
}
