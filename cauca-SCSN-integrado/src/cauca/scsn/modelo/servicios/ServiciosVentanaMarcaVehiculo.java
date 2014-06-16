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
import cauca.scsn.modelo.dao.CargoDAO;
import cauca.scsn.modelo.dao.MarcaVehiculoDAO;
import cauca.scsn.modelo.datamodel.MarcaVehiculoDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.MarcaVehiculo;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaMarcaVehiculo implements ServiciosMaestros{

	private MarcaVehiculoDAO 		marcaVehiculoDAO;
	private MarcaVehiculo 			marcaVehiculo			 	=	new MarcaVehiculo();
	private MarcaVehiculo 			marcaVehiculoSeleccionada 	=	new MarcaVehiculo();
	private MarcaVehiculoDataModel 	marcaVehiculoDataModel;
	private Empresa 				empresa;
	private boolean					modificar			 		= 	false;
	private boolean					eliminar			 		= 	false;
	private boolean 				consultar			 		= 	false;
	private ValidadorBean			validador		     		= 	new ValidadorBean();
	private ControladorMensajes 	mensajes			 		= 	new ControladorMensajes();
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;
	private List<MarcaVehiculo>		listaMarca					=	new ArrayList<MarcaVehiculo>();
	private int cantidad;
	
	public ServiciosVentanaMarcaVehiculo() {
		super();
		marcaVehiculo			 	=	new MarcaVehiculo();
		marcaVehiculoSeleccionada 	=	new MarcaVehiculo();
		marcaVehiculoDAO 			= 	MarcaVehiculoDAO.getInstancia();
		validador		     		= 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if(validador.validarLetrasNumerosGuionPuntoEspacios(marcaVehiculo.getNombre(), "Formato de Nombre de Marca de Vehiculo INCORRECTO ejm: Ford, Jeep, Chevrolet")
					|| validador.validarLetrasNumerosGuionPuntoEspacios(marcaVehiculoSeleccionada.getNombre(), "Formato de Nombre de Marca de Vehiculo INCORRECTO ejm: Ford, Jeep, Chevrolet")){
			
				if (!modificar) {
					marcaVehiculoDAO.insertarOActualizar(this.marcaVehiculo);
					mensajes.informativo("Operación exitosa", "MarcaVehiculo: "+ this.marcaVehiculo.getNombre() +" ha sido guardada!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
					
				} else {
					marcaVehiculoDAO.actualizar(this.marcaVehiculoSeleccionada);
					mensajes.informativo("Operación exitosa", "MarcaVehiculo: "+ this.marcaVehiculoSeleccionada.getNombre() +" ha sido guardada!");
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
			marcaVehiculoDAO.eliminarLogicamente(this.marcaVehiculoSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "MarcaVehiculo: "+ this.marcaVehiculoSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
		
	}

	@Override
	public void llenarDataModel() {
		setListaMarca(marcaVehiculoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setMarcaVehiculoDataModel(new MarcaVehiculoDataModel(this.listaMarca));
	
		if(listaMarca.size() > 0){
			cantidad = 5;
		}else{
			cantidad = 0;
		}
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.marcaVehiculo.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
		
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.marcaVehiculo = null;
		this.marcaVehiculo = new MarcaVehiculo();
		this.marcaVehiculoSeleccionada = null;
		this.marcaVehiculoSeleccionada = new MarcaVehiculo();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (marcaVehiculoSeleccionada.getIdMarcaVehiculo() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún marcaVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultar() {
		if (marcaVehiculoSeleccionada.getIdMarcaVehiculo() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún marcaVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (marcaVehiculoSeleccionada.getIdMarcaVehiculo() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ marcaVehiculoSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún marcaVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}

	public MarcaVehiculo getMarcaVehiculo() {
		return marcaVehiculo;
	}

	public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
		this.marcaVehiculo = marcaVehiculo;
	}

	public MarcaVehiculo getMarcaVehiculoSeleccionada() {
		return marcaVehiculoSeleccionada;
	}

	public void setMarcaVehiculoSeleccionada(MarcaVehiculo marcaVehiculoSeleccionada) {
		this.marcaVehiculoSeleccionada = marcaVehiculoSeleccionada;
	}

	public MarcaVehiculoDataModel getMarcaVehiculoDataModel() {
		return marcaVehiculoDataModel;
	}

	public void setMarcaVehiculoDataModel(
			MarcaVehiculoDataModel marcaVehiculoDataModel) {
		this.marcaVehiculoDataModel = marcaVehiculoDataModel;
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

	public List<MarcaVehiculo> getListaMarca() {
		return listaMarca;
	}

	public void setListaMarca(List<MarcaVehiculo> listaMarca) {
		this.listaMarca = listaMarca;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
