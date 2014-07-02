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
import cauca.scsn.modelo.dao.TipoCarreteraDAO;
import cauca.scsn.modelo.datamodel.TipoCarreteraDataModel;
import cauca.scsn.modelo.entidad.TipoCarretera;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaTipoCarretera implements ServiciosMaestros{
	
	private TipoCarreteraDAO 			tipoCarreteraDAO;
	private TipoCarretera				tipoCarretera;
	private TipoCarretera				tipoCarreteraSeleccionado;
	private Empresa						empresa;
	private TipoCarreteraDataModel		tipoCarreteraDataModel;		
	private boolean						modificar			 		= false;
	private boolean						eliminar			 		= false;
	private boolean 					consultar			 		= false;
	private ValidadorBean				validador		   		    = new ValidadorBean();
	private ControladorMensajes 		mensajes			 		= new ControladorMensajes();
	private ActionEvent 				eventoCancelar;
	private String						mensajeEliminar;	
	private List<TipoCarretera>			listaTipoCarreteras  		= new ArrayList<TipoCarretera>();

	public ServiciosVentanaTipoCarretera() {
		super();
		tipoCarretera					=	new TipoCarretera();
		tipoCarreteraSeleccionado		=	new TipoCarretera();
		tipoCarreteraDAO 				= 	TipoCarreteraDAO.getInstancia();
		validador		   		    	= 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}
	
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if(validador.validarLetrasNumerosEspacios(tipoCarretera.getNombre(), "Formato del Tipo de Carretera INCORRECTO ejm: Asfalto, Arena")
					|| validador.validarLetrasNumerosEspacios(tipoCarreteraSeleccionado.getNombre(), "Formato del Tipo de Carretera INCORRECTO ejm: Asfalto, Arena")){
			
				if (!modificar) {
					tipoCarreteraDAO.insertarOActualizar(this.tipoCarretera);	
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				} else {
					tipoCarreteraDAO.actualizar(this.tipoCarreteraSeleccionado);
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				}
			}else{
				throw new Exception();
			}
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "TipoCarretera: "+ this.tipoCarreteraSeleccionado.getNombre() +" ha sido guardado!");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
			mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
			return;
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			tipoCarreteraDAO.eliminarLogicamente(this.tipoCarreteraSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "TipoCarretera: "+ this.tipoCarreteraSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.tipoCarretera = null;
		this.tipoCarretera = new TipoCarretera();
		this.tipoCarreteraSeleccionado = null;
		this.tipoCarreteraSeleccionado = new TipoCarretera();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (tipoCarreteraSeleccionado.getIdTipoCarretera() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
			System.out.println("Aceptar");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoCarretera de la lista");
			cancelar(eventoCancelar);
			System.out.println("cancelar");
		}
	}
	
	public void activarConsultar() {
		if (tipoCarreteraSeleccionado.getIdTipoCarretera() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoCarretera de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (tipoCarreteraSeleccionado.getIdTipoCarretera() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ tipoCarreteraSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoCarretera de la lista");
			cancelar(eventoCancelar);
		}
	}

	public void llenarDataModel() {
		setListaTipoCarreteras(tipoCarreteraDAO.buscarTodasEntidades());
		setTipoCarreteraDataModel(new TipoCarreteraDataModel(this.listaTipoCarreteras));
	}
	
	public void empresaEnLaSesion() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		empresa = (Empresa) session.getAttribute("empresa");
//		this.tipoCarretera.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public TipoCarretera getTipoCarretera() {
		return tipoCarretera;
	}

	public void setTipoCarretera(TipoCarretera tipoCarretera) {
		this.tipoCarretera = tipoCarretera;
	}

	public TipoCarretera getTipoCarreteraSeleccionado() {
		return tipoCarreteraSeleccionado;
	}

	public void setTipoCarreteraSeleccionado(TipoCarretera tipoCarreteraSeleccionado) {
		this.tipoCarreteraSeleccionado = tipoCarreteraSeleccionado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoCarreteraDataModel getTipoCarreteraDataModel() {
		return tipoCarreteraDataModel;
	}

	public void setTipoCarreteraDataModel(
			TipoCarreteraDataModel tipoCarreteraDataModel) {
		this.tipoCarreteraDataModel = tipoCarreteraDataModel;
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

	public List<TipoCarretera> getListaTipoCarreteras() {
		return listaTipoCarreteras;
	}

	public void setListaTipoCarreteras(List<TipoCarretera> listaTipoCarreteras) {
		this.listaTipoCarreteras = listaTipoCarreteras;
	}

}
