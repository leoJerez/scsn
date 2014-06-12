package cauca.scsn.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.CausaOperacionDAO;
import cauca.scsn.modelo.datamodel.CausaOperacionDataModel;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.CausaOperacion;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaCausaOperacion implements ServiciosMaestros {

	private CausaOperacionDAO 		causaOperacionDAO;
	private CausaOperacion 			causaOperacion			 	=	new CausaOperacion();
	private CausaOperacion 			causaOperacionSeleccionada 	=	new CausaOperacion();
	private CausaOperacionDataModel causaOperacionDataModel;
	private Empresa 				empresa;
	private boolean					modificar			 		= 	false;
	private boolean					eliminar			 		= 	false;
	private boolean 				consultar			 		= 	false;
	private ValidadorBean			validador		    	    =   new ValidadorBean();
	private ControladorMensajes 	mensajes			 		= 	new ControladorMensajes();
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;
	private List<CausaOperacion>	listaCausa					=	new ArrayList<CausaOperacion>();
	
	public ServiciosVentanaCausaOperacion() {
		super();
		causaOperacion			 	=	new CausaOperacion();
		causaOperacionSeleccionada 	=	new CausaOperacion();
		validador		    	    =   new ValidadorBean();
		causaOperacionDAO 			= 	CausaOperacionDAO.getInstancia();
		empresaEnLaSesion();
		llenarDataModel();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
			if(!causaOperacion.getTipoOperacionNeumatico().equals("")){
				try {
					if(validador.validarLetrasComaPuntoGuionEspacios(causaOperacion.getNombre(), "Formato de la Causa de Operacion INCORRECTO ejm: ?????")
							|| validador.validarLetrasComaPuntoGuionEspacios(causaOperacionSeleccionada.getNombre(), "Formato de la Causa de Operacion INCORRECTO ejm: ?????")){
					
						if (!modificar) {
							causaOperacionDAO.insertarOActualizar(this.causaOperacion);
							mensajes.informativo("Operación exitosa", "CausaOperacion: "+ this.causaOperacion.getNombre() +" ha sido guardada!");
							RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
						} else {
							causaOperacionDAO.actualizar(this.causaOperacionSeleccionada);
							mensajes.informativo("Operación exitosa", "CausaOperacion: "+ this.causaOperacionSeleccionada.getNombre() +" ha sido guardada!");
							RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
						}
					}else{
						throw new Exception();
					}
					llenarDataModel();
				} catch (Exception e) {
					mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				}
				cancelar(eventoCancelar);
				empresaEnLaSesion();	
			}else{
				mensajes.error("Error", "Campo requerido, seleccionar una opción de Tipo Operación");
			}
	
		
		
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			causaOperacionDAO.eliminarLogicamente(this.causaOperacionSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "CausaOperacion: "+ this.causaOperacionSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.causaOperacion = null;
		this.causaOperacion = new CausaOperacion();
		this.causaOperacionSeleccionada = null;
		this.causaOperacionSeleccionada = new CausaOperacion();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}

	@Override
	public void activarModificar() {
		if (causaOperacionSeleccionada.getIdCausaOperacion() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarConsultar() {
		if (causaOperacionSeleccionada.getIdCausaOperacion() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (causaOperacionSeleccionada.getIdCausaOperacion() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ causaOperacionSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void llenarDataModel() {
		setListaCausa(causaOperacionDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setCausaOperacionDataModel(new CausaOperacionDataModel(this.listaCausa));
	}

	@Override
	public void empresaEnLaSesion() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		empresa = (Empresa) session.getAttribute("empresa");
//		this.causaOperacion.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public CausaOperacion getCausaOperacion() {
		return causaOperacion;
	}

	public void setCausaOperacion(CausaOperacion causaOperacion) {
		this.causaOperacion = causaOperacion;
	}

	public CausaOperacion getCausaOperacionSeleccionada() {
		return causaOperacionSeleccionada;
	}

	public void setCausaOperacionSeleccionada(
			CausaOperacion causaOperacionSeleccionada) {
		this.causaOperacionSeleccionada = causaOperacionSeleccionada;
	}

	public CausaOperacionDataModel getCausaOperacionDataModel() {
		return causaOperacionDataModel;
	}

	public void setCausaOperacionDataModel(
			CausaOperacionDataModel causaOperacionDataModel) {
		this.causaOperacionDataModel = causaOperacionDataModel;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public boolean isConsultar() {
		return consultar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}

	public String getMensajeEliminar() {
		return mensajeEliminar;
	}

	public void setMensajeEliminar(String mensajeEliminar) {
		this.mensajeEliminar = mensajeEliminar;
	}

	public List<CausaOperacion> getListaCausa() {
		return listaCausa;
	}

	public void setListaCausa(List<CausaOperacion> listaCausa) {
		this.listaCausa = listaCausa;
	}
}
