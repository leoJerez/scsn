package cauca.scsn.modelo.servicios;

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
import cauca.scsn.modelo.dao.RutaDAO;
import cauca.scsn.modelo.datamodel.RutaDataModel;
import cauca.scsn.modelo.entidad.TipoCarretera;
import cauca.scsn.modelo.entidad.Ruta;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaRuta implements ServiciosMaestros{
	
	private RutaDAO 			rutaDAO;
	private Ruta 				ruta			 	 =	new Ruta();
	private Ruta 				rutaSeleccionada 	 =	new Ruta();
	private RutaDataModel 		rutaDataModel;
	private Empresa 			empresa;
	private boolean				modificar			 = 	false;
	private boolean				eliminar			 = 	false;
	private boolean 			consultar			 = 	false;
	private ValidadorBean		validador		     =  new ValidadorBean();
	private ControladorMensajes mensajes			 = 	new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;
	private Integer				idTipoCarretera;
	
	public ServiciosVentanaRuta() {
		super();
		ruta			 	=	new Ruta();
		rutaSeleccionada 	=	new Ruta();
		rutaDAO 			= 	RutaDAO.getInstancia();
		validador		    =   new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}

	public void guardarOModificar(ActionEvent actionEvent) {
		TipoCarretera tipoCarretera = (TipoCarretera) TipoCarreteraDAO.getInstancia().buscarEntidadPorClave(this.idTipoCarretera);
		this.ruta.setTipoCarretera(tipoCarretera);
		try {
			if(validador.validarLetrasNumerosPuntoComaParentesisEspacios(ruta.getNombre(), "Formato de la Ruta INCORRECTO ejm: Barquisimeto-Barinas")
					|| validador.validarLetrasNumerosPuntoComaParentesisEspacios(rutaSeleccionada.getNombre(), "Formato de la Ruta INCORRECTO ejm: Barquisimeto-Barinas")){
			
				if (!modificar) {
					rutaDAO.insertarOActualizar(this.ruta);
					mensajes.informativo("Operación exitosa", "Ruta: "+ this.ruta.getNombre() +" ha sido guardada!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				} else{
					rutaDAO.actualizar(this.rutaSeleccionada);
					mensajes.informativo("Operación exitosa", "Ruta: "+ this.rutaSeleccionada.getNombre() +" ha sido modificada!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				}
			}else{
				throw new Exception();
			}
			llenarDataModel();
		} catch (Exception e) {
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
			mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
			return;
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			rutaDAO.eliminarLogicamente(this.rutaSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Ruta: "+ this.rutaSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.ruta = null;
		this.ruta = new Ruta();
		this.rutaSeleccionada = null;
		this.rutaSeleccionada = new Ruta();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (rutaSeleccionada.getIdRuta() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna ruta de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultar() {
		if (rutaSeleccionada.getIdRuta() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna ruta de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (rutaSeleccionada.getIdRuta() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ rutaSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna ruta de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void llenarDataModel() {
		setRutaDataModel(new RutaDataModel(rutaDAO.buscarEntidadesPorPropiedad("empresa", this.empresa)));
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.ruta.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}
	
	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Ruta getRutaSeleccionada() {
		return rutaSeleccionada;
	}

	public void setRutaSeleccionada(Ruta rutaSeleccionada) {
		this.rutaSeleccionada = rutaSeleccionada;
	}

	public RutaDataModel getRutaDataModel() {
		return rutaDataModel;
	}

	public void setRutaDataModel(RutaDataModel rutaDataModel) {
		this.rutaDataModel = rutaDataModel;
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

	public Integer getIdTipoCarretera() {
		return idTipoCarretera;
	}

	public void setIdTipoCarretera(Integer idTipoCarretera) {
		this.idTipoCarretera = idTipoCarretera;
	}
}
