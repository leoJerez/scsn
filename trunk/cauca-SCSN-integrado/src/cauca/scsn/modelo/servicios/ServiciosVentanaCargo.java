package cauca.scsn.modelo.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cauca.scsn.modelo.datamodel.CargoDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaCargo implements ServiciosMaestros {
	
	private CargoDAO 			cargoDAO;
	private Cargo				cargo;
	private Cargo				cargoSeleccionado;
	private Empresa				empresa;
	private CargoDataModel		cargoDataModel;		
	private boolean				modificar			 = false;
	private boolean				eliminar			 = false;
	private boolean 			consultar			 = false;
	private ValidadorBean		validador		     = new ValidadorBean();
	private ControladorMensajes mensajes			 = new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;	
	private List<Cargo>			listaCargos 		 = new ArrayList<Cargo>();
	private String				nombreCargo;

	public ServiciosVentanaCargo() {
		super();
		cargo					=	new Cargo();
		cargoSeleccionado		=	new Cargo();
		cargoDAO 				= 	CargoDAO.getInstancia();
		validador		        =   new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}
	
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if(validador.validarLetrasNumerosGuionPuntoEspacios(cargo.getNombre(), "Formato de Nombre de Cargo INCORRECTO ejm: Administrador, Operador1, Coordinador")
					|| validador.validarLetrasNumerosGuionPuntoEspacios(cargoSeleccionado.getNombre(), "Formato de Nombre de Cargo INCORRECTO ejm: Administrador, Operador1, Coordinador")){
				if (!modificar) {
					cargoDAO.insertarOActualizar(this.cargo);
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				}else{
					cargoDAO.actualizar(this.cargoSeleccionado);
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				}
			}else{
				throw new Exception();
			}
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Cargo: "+ this.cargoSeleccionado.getNombre()+" ha sido guardado!");
			System.out.println("cargo "+cargo.getNombre());
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
			return;
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			cargoDAO.eliminarLogicamente(this.cargoSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Cargo: "+ this.cargoSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.cargo = null;
		this.cargo = new Cargo();
		this.cargoSeleccionado = null;
		this.cargoSeleccionado = new Cargo();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (cargoSeleccionado.getIdCargo() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
			System.out.println("Aceptar "+cargoSeleccionado.getNombre() );
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún cargo de la lista");
			cancelar(eventoCancelar);
			System.out.println("Cancelar");
		}
	}
	
	public void activarConsultar() {
		if (cargoSeleccionado.getIdCargo() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún cargo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (cargoSeleccionado.getIdCargo() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ cargoSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún cargo de la lista");
			cancelar(eventoCancelar);
		}
	}

	public void llenarDataModel() {
//		setListaCargos(cargoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setListaCargos(CargoDAO.getInstancia().buscarTodasEntidades());
		setCargoDataModel(new CargoDataModel(this.listaCargos));
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.cargo.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}
	
	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public CargoDataModel getCargoDataModel() {
		return cargoDataModel;
	}

	public void setCargoDataModel(CargoDataModel cargoDataModel) {
		this.cargoDataModel = cargoDataModel;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public Cargo getCargoSeleccionado() {
		return cargoSeleccionado;
	}

	public void setCargoSeleccionado(Cargo cargoSeleccionado) {
		this.cargoSeleccionado = cargoSeleccionado;
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


}
