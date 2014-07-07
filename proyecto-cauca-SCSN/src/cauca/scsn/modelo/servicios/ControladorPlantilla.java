package cauca.scsn.modelo.servicios;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
  * El ControladorPlantilla, es un controlador que esta modificado para los tipos de roles que tenga los usuario, especificando su tipo, podra
  * mostrar solo lo necesario que hara el usuario que alla ingresado al sitemas, esto sera manejado atraves de un boolean para identificar si 
  * es falso o no, si se comparara atraves de la base de datos operacion, a la cual ya esta identificada para cada rol. 
 **/

@ManagedBean
@SessionScoped
public class ControladorPlantilla {
	
	private HttpSession session;
	
	private boolean			renderEmpleado;
	private boolean			renderProveedor;
	private boolean			renderRutas;
	private boolean			renderGestionarVehiculos;
	private boolean			renderConfiguracionesMarcas;
	private boolean			renderConfiguracionesModelos;
	private boolean			renderConfiguracionesTiposVehiculos;
	private boolean			renderConfiguracionesEsquemaEjes;
	private boolean			renderGestionarNeumaticos;
	private boolean			renderConfiguracionesNeumaticosMarcas;
	private boolean			renderConfiguracionesNeumaticosMedidas;
	private boolean			renderConfiguracionesNeumaticosDiseno;
	private boolean			renderMovimientosCausasOperaciones;
	private boolean			renderMovimientosTiposDesgaste;
	
	private boolean			renderNuevoEmpleado;
	public boolean			renderNuevoCargoEmpleado;
	public boolean			renderModificarEmpleado;
	public boolean			renderModificarCargoEmpleado;
	public boolean			renderEliminarEmpleado;
	public boolean			renderConsultarEmpleado;
	public boolean			renderNuevoProveedor;
	public boolean			renderModificarProveedor;
	public boolean			renderEliminarProveedor;
	public boolean			renderConsultarProveedor;
	public boolean			renderNuevoRuta;
	public boolean			renderNuevoTipoCarreteraRuta;
	public boolean			renderModificarRuta;
	public boolean			renderModificarTipoCarreteraRuta;
	public boolean			renderEliminarRuta;
	public boolean			renderConsutalarRuta;
	public boolean			renderNuevoVehiculo;
	public boolean			renderNuevoModeloVehiculo;
	public boolean			renderNuevoModeloMarcaVehiculo = false;
	public boolean			renderNuevoModeloTipoVehiculo = false;
	public boolean			renderNuevoRutaVehiculo = false;
	public boolean			renderNuevoRutaTipoCarreteraVehiculo = false;
	public boolean			renderModificarVehiculo = false;
	public boolean			renderModificarModeloVehiculo = false;
	public boolean			renderModificarModeloMarcaVehiculo = false;
	public boolean			renderModificarModeloTipoVehiculo = false;
	public boolean			renderModificarRutaVehiculo = false;
	public boolean			renderModificarTipoCarreteraVehiculo = false;
	public boolean			renderElimnarVehiculo = false;
	public boolean			renderConsultarVehiculo = false;
	public boolean			renderAsignarConductorTemporalVehiculo = false;
	public boolean			renderNuevoVehiculoMarca = false;
	public boolean			renderModificarVehiculoMarca = false;
	public boolean			renderEliminarVehiculoMarca = false;
	public boolean			renderConsultarVehiculoMarca = false;
	public boolean			renderNuevoVehiculoModelo = false;
	public boolean			renderNuevoVehiculoMarcaModelo = false;
	public boolean			renderNuevoTipoVehiculoModelo = false;
	public boolean			renderModificarVehiculoModelo = false;
	public boolean			renderModificarVehiculoMarcaModelo = false;
	public boolean			renderModificarTipoVehiculoModelo = false;
	public boolean			renderEliminarVehiculoModelo = false;
	public boolean			renderConsultarVehiculoModelo = false;
	public boolean			renderNuevoVehiculoTipo = false;
	public boolean			renderModificarVehiculoTipo = false;
	public boolean			renderEliminarVehiculoTipo = false;
	public boolean			renderConsultarVehiculoTipo = false;
	private boolean			renderNuevoEsquema;
	public boolean			renderModificarEsquema = false;
	public boolean			renderEliminarEsquema;
	public boolean			renderConsultarEsquema;
	public boolean			renderNuevoNeumatico = false;
	public boolean			renderNuevoMarcaNeumatico = false;
	public boolean			renderNuevoDisenoNeumatico = false;
	public boolean			renderNuevoMedidaNeumatico = false;
	public boolean			renderNuevoProveedorNeumatico = false;
	public boolean			renderModificarNeumatico = false;
	public boolean			renderModificarMarcaNeumatico = false;
	public boolean			renderModificarDisenoNeumatico = false;
	public boolean			renderModificarProveedorNeumatico = false;
	public boolean			renderEliminarNeumatico = false;
	public boolean			renderConsultarNeumatico = false;
	public boolean			renderNuevoNeumaticoMarca = false;
	public boolean			renderModificarNeumaticoMarca = false;
	public boolean			renderEliminarNeumaticoMarca = false;
	public boolean			renderConsultarNeumaticoMarca = false;
	public boolean			renderNuevoNeumaticoMedida = false;
	public boolean			renderModificarNeumaticoMedida = false;
	public boolean			renderEliminarNeumaticoMedida = false;
	public boolean			renderConsultarNeumaticoMedida = false;
	public boolean			renderNuevoNeumaticoDiseno = false;
	public boolean			renderNuevoNeumaticoMarcaDiseno = false;
	public boolean			renderModificarNeumaticoDiseno = false;
	public boolean			renderModificarNeumaticoMarcaDiseno = false;
	public boolean			renderEliminarNeumaticoDiseno = false;
	public boolean			renderConsultarNeumaticoDiseno = false;
	public boolean			renderNuecoCausaOperacion = false;
	public boolean			renderModificarCausaOperacion = false;
	public boolean			renderEliminarCausaOperacion = false;
	public boolean			renderConsultarCausaOperacion = false;
	public boolean			renderNuevoTipoDesgaste = false;
	public boolean			renderModificarTipoDesgaste = false;
	public boolean			renderEliminarTipoDesgaste = false;
	public boolean			renderConsultarTipoDesgaste = false;

	public ControladorPlantilla() {
		super();
		this.cargarRender();
	}
	
	public void cargarRender() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
	}

	public boolean isRenderEmpleado() {
		this.renderEmpleado = (boolean) session.getAttribute("85");
		return renderEmpleado;
	}

	public void setRenderEmpleado(boolean renderEmpleado) {
		this.renderEmpleado = renderEmpleado;
	}

	public boolean isRenderProveedor() {
		this.renderProveedor = (boolean) session.getAttribute("86");
		return renderProveedor;
	}

	public void setRenderProveedor(boolean renderProveedor) {
		this.renderProveedor = renderProveedor;
	}

	public boolean isRenderRutas() {
		this.renderRutas = (boolean) session.getAttribute("87");
		return renderRutas;
	}

	public void setRenderRutas(boolean renderRutas) {
		this.renderRutas = renderRutas;
	}

	public boolean isRenderGestionarVehiculos() {
		this.renderGestionarVehiculos = (boolean) session.getAttribute("88");
		return renderGestionarVehiculos;
	}

	public void setRenderGestionarVehiculos(boolean renderGestionarVehiculos) {
		this.renderGestionarVehiculos = renderGestionarVehiculos;
	}

	public boolean isRenderConfiguracionesMarcas() {
		this.renderConfiguracionesMarcas = (boolean) session.getAttribute("89");
		return renderConfiguracionesMarcas;
	}

	public void setRenderConfiguracionesMarcas(boolean renderConfiguracionesMarcas) {
		this.renderConfiguracionesMarcas = renderConfiguracionesMarcas;
	}

	public boolean isRenderConfiguracionesModelos() {
		this.renderConfiguracionesModelos = (boolean) session.getAttribute("90");
		return renderConfiguracionesModelos;
	}

	public void setRenderConfiguracionesModelos(boolean renderConfiguracionesModelos) {
		this.renderConfiguracionesModelos = renderConfiguracionesModelos;
	}

	public boolean isRenderConfiguracionesTiposVehiculos() {
		this.renderConfiguracionesTiposVehiculos= (boolean) session.getAttribute("91");
		return renderConfiguracionesTiposVehiculos;
	}

	public void setRenderConfiguracionesTiposVehiculos(boolean renderConfiguracionesTiposVehiculos) {
		this.renderConfiguracionesTiposVehiculos = renderConfiguracionesTiposVehiculos;
	}

	public boolean isRenderConfiguracionesEsquemaEjes() {
		this.renderConfiguracionesEsquemaEjes = (boolean) session.getAttribute("92");
		return renderConfiguracionesEsquemaEjes;
	}

	public void setRenderConfiguracionesEsquemaEjes(boolean renderConfiguracionesEsquemaEjes) {
		this.renderConfiguracionesEsquemaEjes = renderConfiguracionesEsquemaEjes;
	}

	public boolean isRenderGestionarNeumaticos() {
		this.renderGestionarNeumaticos = (boolean) session.getAttribute("93");
		return renderGestionarNeumaticos;
	}

	public void setRenderGestionarNeumaticos(boolean renderGestionarNeumaticos) {
		this.renderGestionarNeumaticos = renderGestionarNeumaticos;
	}

	public boolean isRenderConfiguracionesNeumaticosMarcas() {
		this.renderConfiguracionesNeumaticosMarcas = (boolean) session.getAttribute("94");
		return renderConfiguracionesNeumaticosMarcas;
	}

	public void setRenderConfiguracionesNeumaticosMarcas(boolean renderConfiguracionesNeumaticosMarcas) {
		this.renderConfiguracionesNeumaticosMarcas = renderConfiguracionesNeumaticosMarcas;
	}

	public boolean isRenderConfiguracionesNeumaticosMedidas() {
		this.renderConfiguracionesNeumaticosMedidas = (boolean) session.getAttribute("95");
		return renderConfiguracionesNeumaticosMedidas;
	}

	public void setRenderConfiguracionesNeumaticosMedidas(boolean renderConfiguracionesNeumaticosMedidas) {
		this.renderConfiguracionesNeumaticosMedidas = renderConfiguracionesNeumaticosMedidas;
	}

	public boolean isRenderConfiguracionesNeumaticosDiseno() {
		this.renderConfiguracionesNeumaticosDiseno = (boolean) session.getAttribute("96");
		return renderConfiguracionesNeumaticosDiseno;
	}

	public void setRenderConfiguracionesNeumaticosDiseno(boolean renderConfiguracionesNeumaticosDiseno) {
		this.renderConfiguracionesNeumaticosDiseno = renderConfiguracionesNeumaticosDiseno;
	}

	public boolean isRenderMovimientosCausasOperaciones() {
		this.renderMovimientosCausasOperaciones = (boolean) session.getAttribute("97");
		return renderMovimientosCausasOperaciones;
	}

	public void setRenderMovimientosCausasOperaciones(boolean renderMovimientosCausasOperaciones) {
		this.renderMovimientosCausasOperaciones = renderMovimientosCausasOperaciones;
	}

	public boolean isRenderMovimientosTiposDesgaste() {
		this.renderMovimientosTiposDesgaste = (boolean) session.getAttribute("98");
		return renderMovimientosTiposDesgaste;
	}

	public void setRenderMovimientosTiposDesgaste(boolean renderMovimientosTiposDesgaste) {
		this.renderMovimientosTiposDesgaste = renderMovimientosTiposDesgaste;
	}

	public boolean isRenderNuevoEmpleado() {
		this.renderNuevoEmpleado = (boolean) session.getAttribute("1");
		return renderNuevoEmpleado;
	}

	public void setRenderNuevoEmpleado(boolean renderNuevoEmpleado) {
		this.renderNuevoEmpleado = renderNuevoEmpleado;
	}

	
	
}
