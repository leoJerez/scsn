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
import cauca.scsn.modelo.dao.MarcaNeumaticoDAO;
import cauca.scsn.modelo.datamodel.MarcaNeumaticoDataModel;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.MarcaNeumatico;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaMarcaNeumatico implements ServiciosMaestros {

	private MarcaNeumaticoDAO 		marcaNeumaticoDAO;
	private MarcaNeumatico 			marcaNeumatico			 	=	new MarcaNeumatico();
	private MarcaNeumatico 			marcaNeumaticoSeleccionada 	=	new MarcaNeumatico();
	private MarcaNeumaticoDataModel marcaNeumaticoDataModel;
	private Empresa 				empresa;
	private boolean					modificar			 		= 	false;
	private boolean					eliminar			 		= 	false;
	private boolean 				consultar			 		= 	false;
	private ValidadorBean			validador		     		= 	new ValidadorBean();
	private ControladorMensajes 	mensajes			 		= 	new ControladorMensajes();
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;
	private List<MarcaNeumatico>	listaMarca					=	new ArrayList<MarcaNeumatico>();
	private int						rows;
	
	public ServiciosVentanaMarcaNeumatico() {
		super();
		marcaNeumatico			 	=	new MarcaNeumatico();
		marcaNeumaticoSeleccionada 	=	new MarcaNeumatico();
		marcaNeumaticoDAO 			= 	MarcaNeumaticoDAO.getInstancia();
		validador		     		= 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}
	
	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if(validador.validarLetrasNumerosGuionPuntoEspacios(marcaNeumatico.getNombre(), "Formato de Nombre de Marca de Neumatico INCORRECTO ejm: Goodyear, Firestone")
					|| validador.validarLetrasNumerosGuionPuntoEspacios(marcaNeumaticoSeleccionada.getNombre(), "Formato de Nombre de Marca de Neumatico INCORRECTO ejm: Goodyear, Firestone")){
			
				if (!modificar) {
					marcaNeumaticoDAO.insertarOActualizar(this.marcaNeumatico);
					mensajes.informativo("Operación exitosa", "MarcaNeumatico: "+ this.marcaNeumatico.getNombre() +" ha sido guardada!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
					
				} else {
					marcaNeumaticoDAO.actualizar(this.marcaNeumaticoSeleccionada);
					mensajes.informativo("Operación exitosa", "MarcaNeumatico: "+ this.marcaNeumaticoSeleccionada.getNombre() +" ha sido guardada!");
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

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			marcaNeumaticoDAO.eliminarLogicamente(this.marcaNeumaticoSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "MarcaNeumatico: "+ this.marcaNeumaticoSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.marcaNeumatico = null;
		this.marcaNeumatico = new MarcaNeumatico();
		this.marcaNeumaticoSeleccionada = null;
		this.marcaNeumaticoSeleccionada = new MarcaNeumatico();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}

	@Override
	public void activarModificar() {
		if (marcaNeumaticoSeleccionada.getIdMarcaNeumatico() != null){
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
		if (marcaNeumaticoSeleccionada.getIdMarcaNeumatico() != null){
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
		if (marcaNeumaticoSeleccionada.getIdMarcaNeumatico() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ marcaNeumaticoSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void llenarDataModel() {
		setListaMarca(marcaNeumaticoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setMarcaNeumaticoDataModel(new MarcaNeumaticoDataModel(this.listaMarca));
		
		if(listaMarca.size() > 0){
			rows = 5;
		}else{
			rows = 0;
		}
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.marcaNeumatico.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public MarcaNeumatico getMarcaNeumatico() {
		return marcaNeumatico;
	}

	public void setMarcaNeumatico(MarcaNeumatico marcaNeumatico) {
		this.marcaNeumatico = marcaNeumatico;
	}

	public MarcaNeumatico getMarcaNeumaticoSeleccionada() {
		return marcaNeumaticoSeleccionada;
	}

	public void setMarcaNeumaticoSeleccionada(
			MarcaNeumatico marcaNeumaticoSeleccionada) {
		this.marcaNeumaticoSeleccionada = marcaNeumaticoSeleccionada;
	}

	public MarcaNeumaticoDataModel getMarcaNeumaticoDataModel() {
		return marcaNeumaticoDataModel;
	}

	public void setMarcaNeumaticoDataModel(
			MarcaNeumaticoDataModel marcaNeumaticoDataModel) {
		this.marcaNeumaticoDataModel = marcaNeumaticoDataModel;
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

	public List<MarcaNeumatico> getListaMarca() {
		return listaMarca;
	}

	public void setListaMarca(List<MarcaNeumatico> listaMarca) {
		this.listaMarca = listaMarca;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
