package cauca.scsn.modelo.servicios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import inexistentes.SelectableDataModel;
import inexistentes.TransferEvent;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.eclipse.persistence.jpa.jpql.parser.AnonymousExpressionVisitor;
import org.eclipse.persistence.jpa.rs.util.metadatasources.JavaLangMetadataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;  
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel; 

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.CargoDAO;
import cauca.scsn.modelo.dao.EjeTraccionDAO;
import cauca.scsn.modelo.dao.EmpleadoDAO;
import cauca.scsn.modelo.dao.EmpresaDAO;
import cauca.scsn.modelo.dao.EsquemaEjeDAO;
import cauca.scsn.modelo.dao.ModeloVehiculoDAO;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.RutaDAO;
import cauca.scsn.modelo.dao.RutaVehiculoDAO;
import cauca.scsn.modelo.dao.VehiculoDAO;
import cauca.scsn.modelo.dao.VehiculoEmpleadoDAO;
import cauca.scsn.modelo.datamodel.EmpleadoDataModel;
import cauca.scsn.modelo.datamodel.VehiculoDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.EjeTraccion;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.EntidadGenerica;
import cauca.scsn.modelo.entidad.EsquemaEje;
import cauca.scsn.modelo.entidad.ModeloVehiculo;
import cauca.scsn.modelo.entidad.Ruta;
import cauca.scsn.modelo.entidad.RutaVehiculo;
import cauca.scsn.modelo.entidad.TipoCarretera;
import cauca.scsn.modelo.entidad.Vehiculo;
import cauca.scsn.modelo.entidad.VehiculoEmpleado;
import cauca.scsn.modelo.entidad.id.RutaVehiculoId;
import cauca.scsn.modelo.entidad.id.VehiculoEmpleadoId;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@SessionScoped
public class ServiciosVentanaVehiculo implements ServiciosMaestros{

	private static final ActionEvent ActionEvent = null;
	private static final AjaxBehaviorEvent AjaxBehaviorEvent = null;
	private VehiculoDAO 				vehiculoDAO;
	private Vehiculo					vehiculo;
	private Vehiculo 					vehiculoSeleccionado;
	private VehiculoDataModel 			vehiculoDataModel;
	private List<Vehiculo>				listaVehiculo			= new ArrayList<Vehiculo>();
	private ControladorMensajes			mensajes;
	private ServiciosVentanaEsquemaEjes serviciosVentanaEsquemaEjes;
	private ServiciosVentanaEmpleado	serviciosVentanaEmpleado;
	private Empresa						empresa;
	private int							idModeloVehiculo		= 0;
	private String 						dentroCarretera;
	private List<ModeloVehiculo>		listaModeloVehiculo;
	private List<EsquemaEje>			listaEsquemaEjes		= new ArrayList<EsquemaEje>();
	private int							indiceListaEsquemaEje;
	private EmpleadoDAO					empleadoDAO;
	private Empleado					empleadoSeleccionado;
	private Empleado					empleadoSeleccionadoDestino;
	private VehiculoEmpleado			vehiculoEmpleado;
	private VehiculoEmpleadoId			vehiculoEmpleadoId;
	private VehiculoEmpleadoDAO			vehiculoEmpleadoDAO;
	private Empleado					conductorPrincipal;
	private List<Empleado>				listaEmpleadosOrigen;		
	private List<Empleado>				listaEmpleadosDestino;
	private EmpleadoDataModel			empleadosDestinoDataModel;
	private EmpleadoDataModel			empleadosOrigenDataModel;
	private EmpleadoDataModel			empleadoTemporalDataModel;
	private List<VehiculoEmpleado> 		listaConductoresEnVehiculoEmpleado = new ArrayList<VehiculoEmpleado>();
	private List<Empleado> 				listaEmpleadosDestinoTemporal	= new ArrayList<Empleado>();
	private List<Empleado> 				listaEmpleadosDestinoTemporalAuxiliar	= new ArrayList<Empleado>();
	private List<EjeTraccion>			listaEjeTraccion = new ArrayList<EjeTraccion>();
	private int							idRuta;
	private Ruta						ruta;
	private List<Ruta>					listaRuta;
	private RutaVehiculo				rutaVehiculo;
	private RutaVehiculoId				rutaVehiculoId;
	private ValidadorBean				validador;
	private boolean						botonAsignarVisible		= false;
	private boolean						botonRegresarVisible	= false;
	private ActionEvent 				eventoCancelar;
	private boolean						conductorTemporal		= false;
	private boolean 					modificar				= false;
	private boolean						consultar 				= false;
	private boolean 					eliminar				= false;
	private boolean 					confirmar				= false;
	private String						mensajeEliminar			= "";
	private int							tabIndex				= 0;
	private boolean						transmision				= false;
	private boolean						traccion1				= false;
	private boolean						traccion2				= false;
	private boolean						traccion3				= false;
	private boolean						traccion4				= false;
	private boolean						traccion5				= false;
	private HashMap<String, Boolean>    mapaEjesTraccion        = new HashMap<String, Boolean>();
	private boolean 					check1 					= false;
	private boolean 					check2 					= false;
	private boolean 					check3 					= false;
	private boolean 					check4 					= false;
	private boolean 					check5 					= false;
	private EjeTraccion   				ejeTraccion 			= new EjeTraccion();
	private boolean						traccionTildado			= false;
	private boolean						renderedCheck			= false;
	private List<Empleado> 				listaEmpleado 			= new ArrayList<Empleado>();
	private int 						rows;
	private String 						tipoEsquemaEjes 		= "";
	private String 						tipoEsquemaEjesMod 		= "";
	private boolean 					renderConductor 		= false;
	
	public ServiciosVentanaVehiculo() {
		super();
		vehiculo					=	new Vehiculo();
		vehiculoSeleccionado		=	new Vehiculo();
		vehiculoDAO 				= 	vehiculoDAO.getInstancia();
		vehiculoEmpleado			= 	new VehiculoEmpleado();
		validador					=	new ValidadorBean();
		indiceListaEsquemaEje		= 	0;
		mensajes					=	new ControladorMensajes();
		serviciosVentanaEsquemaEjes =	new ServiciosVentanaEsquemaEjes();
		serviciosVentanaEmpleado	=	new ServiciosVentanaEmpleado();
		empleadoSeleccionado		=	new Empleado();
		empleadoSeleccionadoDestino	= 	new Empleado();
		conductorPrincipal			=	new Empleado();
		listaEmpleadosOrigen		= 	new ArrayList<Empleado>();
		listaEmpleadosDestino		= 	new ArrayList<Empleado>();
		empresaEnLaSesion();
		cargarListaEmpleados();
		cargarListaRuta();
		llenarDataModelEmpleadosDestino();
		llenarDataModelEmpleadosOrigen();
		modificarVisibilidadBotonesAsignacionConductor();
		llenarDataModel();
		llenarModelos();
		llenarListaEsquema();
		serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(listaEsquemaEjes.get(indiceListaEsquemaEje));
		serviciosVentanaEsquemaEjes.verificarDatosActivos();
		activarCheck();
		validarEsquemasEjes();
	}
	
	public void reiniciarTabIndex() {
		tabIndex = 0;
	}
	
	public void cargarListaRuta() {
		listaRuta =	new ArrayList<Ruta>();
		listaRuta = RutaDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", this.empresa);
	}
	
	public void actualizarTipoCarretera() {
		if(idRuta!=0) {
			ruta = (Ruta) RutaDAO.getInstancia().buscarEntidadPorClave(idRuta);	
		}
	}
	
	
	public boolean asignarRutaVehiculo(Vehiculo vehiculoParametro) {
		rutaVehiculoId = new RutaVehiculoId();
		rutaVehiculo = new RutaVehiculo();
		if(ruta.getNombre() != null) { //(ruta != null) || (!ruta.getNombre().equals("ninguno")) || (!ruta.getNombre().equals(""))
			rutaVehiculoId.setIdRuta(ruta.getIdRuta());
			rutaVehiculoId.setIdVehiculo(vehiculoParametro.getIdVehiculo());
			rutaVehiculo.setId(rutaVehiculoId);
			rutaVehiculo.setVehiculo(vehiculoParametro);
			rutaVehiculo.setRuta(ruta);
			return true;
		} else {
			mensajes.error("Error!", "Debe seleccionar una ruta para el vehículo");
			return false;
		}
	}
	
	public void cargarListaEmpleados() {
		cargarListaConductoresAsignados();
		filtrarEmpleadosEnListaAsignados();
		cagarListaDestinoAPartirDeConductoresEnVehiculoEmpleado(listaConductoresEnVehiculoEmpleado, listaEmpleadosDestino);
	}
	
	public void cargarListaConductoresAsignados() {
		listaConductoresEnVehiculoEmpleado = new ArrayList<VehiculoEmpleado>();
		if((!consultar) && (!conductorTemporal) && (!modificar)) {
			System.out.println("entramos como si fueramos a guardar uno nuevo");
			listaConductoresEnVehiculoEmpleado = VehiculoEmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculo);
		} else {
			System.out.println("entramos por donde debe entrar al modificar, consultar o temporal");
			listaConductoresEnVehiculoEmpleado = VehiculoEmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
		}
	}
	
	public void filtrarEmpleadosEnListaAsignados() {
		empleadoDAO = empleadoDAO.getInstancia();
		listaEmpleadosOrigen.clear();
		listaEmpleado = empleadoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa);
		for (int h = 0; h < listaEmpleado.size(); h++) {
			if(listaEmpleado.get(h).getCargo().getIdCargo() == 6){
				listaEmpleadosOrigen.add(listaEmpleado.get(h));
			}
			
		}
		
		for(int i = 0; i<listaConductoresEnVehiculoEmpleado.size(); i++) {
			for(int j = 0; j<listaEmpleadosOrigen.size(); j++) {
				if(listaEmpleadosOrigen.get(j).getCedulaEmpleado().equals(listaConductoresEnVehiculoEmpleado.get(i).getId().getCedulaEmpleado())) {
					listaEmpleadosOrigen.remove(j);
				}
			}
		}
	}
	
	public void cagarListaDestinoAPartirDeConductoresEnVehiculoEmpleado(List<VehiculoEmpleado> listaAFiltrar, List<Empleado> listaObjetivo) {
		for(int i=0; i<listaAFiltrar.size(); i++) {
			if(listaAFiltrar.get(i).getConductorPrincipal().equals("S")) {
				conductorPrincipal = listaAFiltrar.get(i).getEmpleado();
			}
			listaObjetivo.add(listaAFiltrar.get(i).getEmpleado());
		}
	}
	
	public void llenarDataModelEmpleadosDestino() {
		setEmpleadosDestinoDataModel(new EmpleadoDataModel(listaEmpleadosDestino));
	}
	
	public void llenarDataModelEmpleadoTemporal() {
		setEmpleadoTemporalDataModel(new EmpleadoDataModel(listaEmpleadosDestinoTemporal));
	}
	
	public void llenarDataModelEmpleadosOrigen() {
		setEmpleadosOrigenDataModel(new EmpleadoDataModel(listaEmpleadosOrigen));
	}
	
	public void establecerConductorPrincipal(ActionEvent actionEvent) {
		if(empleadoSeleccionadoDestino != null) {
			conductorPrincipal = empleadoSeleccionadoDestino;	
		} else {
			mensajes.advertencia("Atención!", "Debe seleccionar uno de los conductores asignados");
		}
	}
	
	public void metodoAuxiliarAsignacionConductor(List<Empleado> listaEmpleadosParametro, Empleado empleadoSeleccionadoParametro) {
		for(int j=0; j<listaEmpleadosParametro.size(); j++) {
			if((listaEmpleadosParametro.get(j).getCedulaEmpleado() != null) && (listaEmpleadosParametro.get(j).getCedulaEmpleado().equals(empleadoSeleccionadoParametro.getCedulaEmpleado()))) {
				listaEmpleadosParametro.remove(j);
			}
		}
	}
	
	public void asignarUnConductor(String origenODestino) {
		if((origenODestino.equals("source")) && (empleadoSeleccionado.getCedulaEmpleado() != null)) {
			activarCheck();
			if(conductorTemporal) {
				listaEmpleadosDestinoTemporal.add(empleadoSeleccionado);
			} else {
				listaEmpleadosDestino.add(empleadoSeleccionado);
			}
			metodoAuxiliarAsignacionConductor(listaEmpleadosOrigen, empleadoSeleccionado);
			empleadoSeleccionado = new Empleado();
		} else if((origenODestino.equals("target")) && (empleadoSeleccionadoDestino.getCedulaEmpleado() != null)) {
			if(conductorTemporal) {
				listaEmpleadosOrigen.add(empleadoSeleccionadoDestino);
				metodoAuxiliarAsignacionConductor(listaEmpleadosDestinoTemporal, empleadoSeleccionadoDestino);
			} else {
				listaEmpleadosOrigen.add(empleadoSeleccionadoDestino);
				if(empleadoSeleccionadoDestino.getCedulaEmpleado().equals(conductorPrincipal.getCedulaEmpleado())) {
					conductorPrincipal = new Empleado();
				}
				metodoAuxiliarAsignacionConductor(listaEmpleadosDestino, empleadoSeleccionadoDestino);
			}
			empleadoSeleccionadoDestino = new Empleado();
		}
		llenarDataModelEmpleadosDestino();
		llenarDataModelEmpleadosOrigen();
		llenarDataModelEmpleadoTemporal();
		modificarVisibilidadBotonesAsignacionConductor();
	}
	
	public void asignarTodosConductoresOrigenDestino(ActionEvent actionEvent) {
		for(int i=0; i<listaEmpleadosOrigen.size(); i++) {
			listaEmpleadosDestino.add(listaEmpleadosOrigen.get(i));
		}
		for(int i=0; i<listaEmpleadosOrigen.size(); i++) {
			listaEmpleadosOrigen.remove(i);
		}
		listaEmpleadosOrigen.clear();
		llenarDataModelEmpleadosDestino();
		llenarDataModelEmpleadosOrigen();
		modificarVisibilidadBotonesAsignacionConductor();
	}
	
	public void asignarTodosConductoresDestinoOrigen(ActionEvent actionEvent) {
		for(int i=0; i<listaEmpleadosDestino.size(); i++) {
			listaEmpleadosOrigen.add(listaEmpleadosDestino.get(i));
		}
		for(int i=0; i<listaEmpleadosDestino.size(); i++) {
			listaEmpleadosDestino.remove(i);
		}
		listaEmpleadosDestino.clear();
		llenarDataModelEmpleadosDestino();
		llenarDataModelEmpleadosOrigen();
		modificarVisibilidadBotonesAsignacionConductor();
	}
	
	public void modificarVisibilidadBotonesAsignacionConductor() {
		if(listaEmpleadosOrigen.size() > 0) {
			botonAsignarVisible = false;
		} else {
			botonAsignarVisible = true;
		}
		if(listaEmpleadosDestino.size() > 0) {
			botonRegresarVisible = false;
		} else {
			botonRegresarVisible = true;
		}
		
//		if((conductorTemporal) && (listaEmpleadosDestino.size() == 1)) {
//			botonAsignarVisible = true;
//		} else if((conductorTemporal) && (listaEmpleadosDestino.size() != 1)) {
//			botonAsignarVisible = false;
//		}
		
		if((conductorTemporal) && (listaEmpleadosDestinoTemporal.size() != 0)) {
			botonAsignarVisible = true;
			botonRegresarVisible = false;
		} else if((conductorTemporal) && (listaEmpleadosDestinoTemporal.size() == 0)) {
			botonAsignarVisible = false;
			botonRegresarVisible = true;
		}
		
	}
	
	public boolean asignarConductor(Vehiculo vehiculoParametro, Empleado conductorParametro, List<Empleado> listaParametro) {
		if(listaParametro.size() > 0) {
			for(int i= 0; i<listaParametro.size(); i++) {
				vehiculoEmpleadoId = new VehiculoEmpleadoId();
				vehiculoEmpleadoId.setCedulaEmpleado(listaParametro.get(i).getCedulaEmpleado());
				vehiculoEmpleadoId.setIdVehiculo(vehiculoParametro.getIdVehiculo());
				vehiculoEmpleado.setId(vehiculoEmpleadoId);
				vehiculoEmpleado.setVehiculo(vehiculoParametro);
				vehiculoEmpleado.setEmpleado(listaParametro.get(i));
				//cambiarEstadoConductorPrincipalAsignado(vehiculoEmpleado);
				if(!conductorTemporal) {
					if(conductorParametro.getCedulaEmpleado() != null) {
						if(conductorParametro.getCedulaEmpleado().equals(vehiculoEmpleado.getId().getCedulaEmpleado())) {
							vehiculoEmpleado.setConductorPrincipal("S");
						} else {
							vehiculoEmpleado.setConductorPrincipal("N");
						}
					} 
					vehiculoEmpleado.setMotivo("Asignación de rutina");	
				} else {
					vehiculoEmpleado.setTemporal("S");
				}
				vehiculoEmpleadoDAO.getInstancia().insertarOActualizar(vehiculoEmpleado);
			}
		} else {
			if(tipoEsquemaEjes.equals("chuto")){
				mensajes.advertencia("Atención!", "No ha asignado ningún conductor");
				return false;
			}else{
				if(tipoEsquemaEjes.equals("remolque")){
					if(listaParametro.size() == 0){
						System.out.println("lista empleados asignados no tiene porque remolque no necesita conductores");
					}
				}
			}
			
			
		}
		return true;
	}
	
	public void cambiarEstadoConductorPrincipalAsignado(VehiculoEmpleado vehiculoEmpleadoParametro) {
		//cargarListaConductoresAsignados();
		for(int j=0; j<listaConductoresEnVehiculoEmpleado.size(); j++) {
			if((listaConductoresEnVehiculoEmpleado.get(j).getConductorPrincipal().equals("S")) 
					&& (listaConductoresEnVehiculoEmpleado.get(j).getId().getCedulaEmpleado().equals(vehiculoEmpleadoParametro.getId().getCedulaEmpleado()))) {
				listaConductoresEnVehiculoEmpleado.get(j).setConductorPrincipal("N");
			}
		}
	}
	
	public void activarConductorTemporal() {
		if(vehiculoSeleccionado.getIdVehiculo() != null) {
			conductorTemporal = true;
			cargarListaEmpleados();
			if(verificarConductoresTemporal()) {
				mensajes.advertencia("Atención!", "Este vehículo ya tiene un conductor temporal asignado; "
						+ "si desea cambiarlo debe regresarlo a conductores disponibles para que pueda agregar uno nuevo");
				for(int i=0; i<listaEmpleadosDestinoTemporal.size(); i++) {
					vehiculoEmpleado = (VehiculoEmpleado) VehiculoEmpleadoDAO.getInstancia().buscarEntidadPorPropiedad("empleado", listaEmpleadosDestinoTemporal.get(i));
				}
			}
			llenarDataModelEmpleadosDestino();
			llenarDataModelEmpleadosOrigen();
			llenarDataModelEmpleadoTemporal();
			if(listaEmpleadosOrigen.size() == 0) {
				mensajes.advertencia("Atención!", "Este vehículo tiene asignados todos los conductores actualmente "
						+ "registrados en la empresa. Por favor, registre un nuevo conductor");
			}
			modificarVisibilidadBotonesAsignacionConductor();
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "T");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún Vehículo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public boolean verificarConductoresTemporal() {
		boolean conTemporal = false;
		listaEmpleadosDestinoTemporal = new ArrayList<Empleado>();
		for(int i=0; i<listaConductoresEnVehiculoEmpleado.size(); i++) {
			if((listaConductoresEnVehiculoEmpleado.get(i).getTemporal() != null) && (listaConductoresEnVehiculoEmpleado.get(i).getTemporal().equals("S"))) {
				listaEmpleadosDestinoTemporal.add(listaConductoresEnVehiculoEmpleado.get(i).getEmpleado());
				conTemporal = true;
				if((listaConductoresEnVehiculoEmpleado.get(i).getConductorPrincipal() != null) && (listaConductoresEnVehiculoEmpleado.get(i).getConductorPrincipal().equals("S"))) {
					conductorPrincipal = listaConductoresEnVehiculoEmpleado.get(i).getEmpleado();
				}
			}
		}
		filtrarConductoresDeLista(listaEmpleadosDestino, listaEmpleadosDestinoTemporal);
		for(int j=0; j<listaEmpleadosDestinoTemporal.size(); j++) {
			listaEmpleadosDestinoTemporalAuxiliar.add(listaEmpleadosDestinoTemporal.get(j));
		}
		return conTemporal;
	}
	
	public void filtrarConductoresDeLista(List<Empleado> listaIngreso, List<Empleado> listaObjetivo) {
		if((listaObjetivo.size() != 0) && (listaIngreso.size() != 0)){
			for(int i=0; i<listaObjetivo.size(); i++) {
				for(int j=0; j<listaIngreso.size(); j++) {
					if(listaObjetivo.get(i).getCedulaEmpleado().equals(listaIngreso.get(j).getCedulaEmpleado())) {
						listaIngreso.remove(j);
					}
				}
			}	
		}
	} 
	
	/**
	 * 
	 * @param listaFiltrar
	 * @param listaComparativa
	 * @deprecated
	 */
	public void filtrarConductoresEnVehiculoEmpleado(List<VehiculoEmpleado> listaFiltrar, List<Empleado> listaComparativa) {
		for(int i=0; i<listaComparativa.size(); i++) {
			for(int j=0; j<listaFiltrar.size(); j++) {
				if(listaFiltrar.get(j).getId().getCedulaEmpleado().equals(listaComparativa.get(i).getCedulaEmpleado())) {
					listaFiltrar.remove(j);
				}
			}
		}
	}
	
	public void guardarConductor(ActionEvent actionEvent) {
		serviciosVentanaEmpleado.activarGuardarConductor();
		cargarListaEmpleados();
		llenarDataModelEmpleadosOrigen();
	}
	
	public void quitarConductorTemporal() {
		for(int j=0; j<listaConductoresEnVehiculoEmpleado.size(); j++) {
			for(int i=0; i<listaEmpleadosDestinoTemporalAuxiliar.size(); i++) {
				if(listaConductoresEnVehiculoEmpleado.get(j).getEmpleado().getCedulaEmpleado().equals(listaEmpleadosDestinoTemporalAuxiliar.get(i).getCedulaEmpleado())) {
					VehiculoEmpleadoDAO.getInstancia().eliminarLogicamente(listaConductoresEnVehiculoEmpleado.get(j));
					listaEmpleadosDestinoTemporalAuxiliar.remove(i);
				}
			}	
		}
		listaEmpleadosDestino.clear();
		if(confirmar) {
			vehiculoEmpleado = new VehiculoEmpleado();
			confirmar = false;
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
			RequestContext.getCurrentInstance().addCallbackParam("confirmar", false);
		}
	}
	
	public void activarConfirmar() {
		confirmar = true;
		quitarConductorTemporal();
	}
	
	public void asignarConductorTemporalVehiculo() {
		if(listaEmpleadosDestinoTemporal.size() == 0) {
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
			RequestContext.getCurrentInstance().addCallbackParam("confirmar", true);
		} else if(validador.compararFechaUnoMayorQueFechaDos(vehiculoEmpleado.getFechaInicial(), vehiculoEmpleado.getFechaFinal(), "La Fecha Final es anterior a la Fecha Inicial")) {
			if(vehiculoEmpleado.getMotivo().equals("")) {
				mensajes.error("Error!", "Debe indicar el motivo por el cual está asignando un conductor temporal");
				RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
				return;
			} else if(vehiculoEmpleado.getConductorPrincipal().equals("")) {
				mensajes.error("Error!", "Debe indicar si el conductor será principal o no");
				RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
				return;
			} else {
				quitarConductorTemporal();
				if(asignarConductor(vehiculoSeleccionado, conductorPrincipal, listaEmpleadosDestinoTemporal)) {
					//vehiculoEmpleadoDAO.getInstancia().insertarOActualizar(vehiculoEmpleado);
					mensajes.informativo("Operación Exitosa!", "Conductor Temporal desde el "+validador.formatearFechaEstiloCompleto(vehiculoEmpleado.getFechaInicial())+" hasta el "+validador.formatearFechaEstiloCompleto(vehiculoEmpleado.getFechaFinal()));
					cancelar(eventoCancelar);
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				} else {
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				}
				llenarDataModel();
				cancelar(eventoCancelar);
			}
		} else {
			mensajes.advertencia("Atención!", "Por favor verifique la fechas");
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
			return;
		}
	}
	
	public void sacarConductoresDeLaLista(List<Empleado> listaConductoresOrigen, Vehiculo vehiculoParametro) {
		for(int i=0; i<listaConductoresOrigen.size(); i++) {
			if(listaConductoresOrigen.get(i).getCedulaEmpleado().equals(conductorPrincipal.getCedulaEmpleado())) {
				conductorPrincipal = new Empleado();
			}
			for(int j=0; j<VehiculoEmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("empleado",listaConductoresOrigen.get(i)).size(); j++ ) {
				for(int h=0; h<VehiculoEmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", vehiculoParametro).size(); h++) {
					VehiculoEmpleadoDAO.getInstancia().eliminarFisicamente((VehiculoEmpleado) VehiculoEmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", vehiculoParametro).get(h));
				}
			}
		}
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		boolean repetido = false;
		List<Vehiculo> listaComparativa = vehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", this.empresa);
		try {
			if (!modificar) {
				vehiculo.setPlaca(vehiculo.getPlaca().toUpperCase());
				
				for(int i=0; i<listaComparativa.size(); i++) {
					if(listaComparativa.get(i).getPlaca().toUpperCase().equals(vehiculo.getPlaca())) {
						repetido = true;
					} else {
						repetido = false;
					}
				}
				
				if(repetido) {
					mensajes.error("Error!!", "Este número de placa ya existe ("+vehiculo.getPlaca()+"). Por favor, revise!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				} else if(vehiculo.getModeloVehiculo().getIdModeloVehiculo() == 0) { //idModeloVehiculo == 0
					mensajes.advertencia("Atención!", "Es necesario seleccionar el modelo para el vehículo");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				} else if(conductorPrincipal.getCedulaEmpleado() == null) {
							if(tipoEsquemaEjes.equals("chuto")){
								mensajes.advertencia("Atención!", "No ha seleccionado el conductor principal, asegúrese de asignarlo para poder continuar");
								RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
								return;
							}else{
								if(tipoEsquemaEjes.equals("remolque")){
									if(conductorPrincipal.getCedulaEmpleado() == null){
										System.out.println("Remolque no necesita conductor principal");
									}
								}
							}
					
				} else {
					vehiculoDAO.insertarOActualizar(this.vehiculo);
					vehiculo = (Vehiculo) vehiculoDAO.buscarEntidadPorPropiedad("placa", this.vehiculo.getPlaca());
					if((asignarConductor(vehiculo, conductorPrincipal, listaEmpleadosDestino)) && (asignarRutaVehiculo(vehiculo))) {
						RutaVehiculoDAO.getInstancia().insertarOActualizar(rutaVehiculo); 
					} else {
						if(VehiculoEmpleadoDAO.getInstancia().buscarEntidadPorClave(vehiculoEmpleadoId) != null) {
							VehiculoEmpleadoDAO.getInstancia().eliminarFisicamente(vehiculoEmpleado);
							vehiculoDAO.getInstancia().eliminarFisicamente(vehiculo);
						} else {
							vehiculoDAO.getInstancia().eliminarFisicamente(vehiculo);
						}
						throw new Exception();
					}
					
					Iterator iterador = mapaEjesTraccion.entrySet().iterator();
					Map.Entry entry;
					while(iterador.hasNext()){
						//MAPA
						ejeTraccion = new EjeTraccion();
						ejeTraccion.setVehiculo(vehiculo);
						entry = (Entry) iterador.next();
						ejeTraccion.setNombre((String)entry.getKey());
						EjeTraccionDAO.getInstancia().insertarOActualizar(ejeTraccion);
					}
					mensajes.informativo("Operación exitosa", "Vehículo con placa "+ this.vehiculo.getPlaca() +" ha sido guardado!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				} 
			} else {
				vehiculoSeleccionado.setPlaca(vehiculoSeleccionado.getPlaca().toUpperCase());
				for(int j=0; j<listaComparativa.size(); j++) {
					if((listaComparativa.get(j).getPlaca().toUpperCase().equals(vehiculoSeleccionado.getPlaca())) 
							&& (listaComparativa.get(j).getIdVehiculo() != vehiculoSeleccionado.getIdVehiculo())) {
						repetido = true;
					} else {
						repetido = false;
					}
				}
				if(repetido) {
					mensajes.error("Error!!", "Este número de placa ya existe ("+vehiculoSeleccionado.getPlaca()+"). Por favor, revise!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				} else if(conductorPrincipal.getCedulaEmpleado() == null) {
					mensajes.error("Atención!", "No ha seleccionado el conductor principal, asegúrese de asignarlo para poder continuar");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
					return;
				} else {
					sacarConductoresDeLaLista(listaEmpleadosOrigen, vehiculoSeleccionado);
					if((asignarConductor(vehiculoSeleccionado, conductorPrincipal, listaEmpleadosDestino)) && (asignarRutaVehiculo(vehiculoSeleccionado))) {
						vehiculoDAO.actualizar(this.vehiculoSeleccionado);
//						vehiculoEmpleadoDAO.getInstancia().insertarOActualizar(vehiculoEmpleado);
						RutaVehiculoDAO.getInstancia().insertarOActualizar(rutaVehiculo);
					} else {
						throw new Exception();
					}
					
					eliminarEjeTraccion();
					System.out.println("tamaño del mapa modificar "+mapaEjesTraccion.size());
					Iterator iteradorMod = mapaEjesTraccion.entrySet().iterator();
					Map.Entry entry;
					while(iteradorMod.hasNext()){
						//MAPA
						ejeTraccion = new EjeTraccion();
						ejeTraccion.setVehiculo(vehiculoSeleccionado);
						entry = (Entry) iteradorMod.next();
						ejeTraccion.setNombre((String)entry.getKey());
						EjeTraccionDAO.getInstancia().insertarOActualizar(ejeTraccion);
					}
					mensajes.informativo("Operación exitosa", "Vehículo con placa "+ this.vehiculoSeleccionado.getPlaca() +" ha sido guardado!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				}
			}
			empresaEnLaSesion();
			llenarDataModel();
			cancelar(eventoCancelar);
			
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
			return;
		}
		//cancelar(eventoCancelar);
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			vehiculoDAO.eliminarLogicamente(this.vehiculoSeleccionado);
			mensajes.informativo("Operación exitosa", "Vehículo: "+ this.vehiculoSeleccionado.getPlaca() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		System.out.println("Entramos a cancelar");
		activarCheck();
		reiniciarTabIndex();
		mapaEjesTraccion.clear();
		listaEjeTraccion = new ArrayList<EjeTraccion>();
		idModeloVehiculo = 0;
		indiceListaEsquemaEje = 0;
		dentroCarretera = "";
		this.vehiculo = null;
		this.vehiculo = new Vehiculo();
		this.vehiculoSeleccionado = null;
		this.vehiculoSeleccionado = new Vehiculo();
		empleadoSeleccionado = new Empleado();
		idRuta = 0;
		ruta = new Ruta();
		mensajes = new ControladorMensajes();
		empresaEnLaSesion();
		cargarListaRuta();
		setModificar(false);
		setEliminar(false);
		setConsultar(false);
		serviciosVentanaEsquemaEjes.cancelar(eventoCancelar);
		llenarListaEsquema();
		llenarDataModel();
		conductorPrincipal = new Empleado();
		conductorTemporal = false;
		vehiculoEmpleado = new VehiculoEmpleado();
		cargarListaEmpleados();
		listaEmpleadosDestino = new ArrayList<Empleado>();
		listaEmpleadosDestinoTemporal = new ArrayList<Empleado>();
		listaConductoresEnVehiculoEmpleado = new ArrayList<VehiculoEmpleado>();
		llenarDataModelEmpleadosDestino();
		llenarDataModelEmpleadosOrigen();
		tipoEsquemaEjes = "";
		modificarVisibilidadBotonesAsignacionConductor();
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
	}
	
	public void recorreMapaModificar(){
		Iterator iterador = mapaEjesTraccion.entrySet().iterator();
		Map.Entry entry;
		while(iterador.hasNext()){
			//MAPA
			entry = (Entry) iterador.next();
			compararEjeTraccionMod((String)entry.getKey());
			System.out.println("valor del mapa que recorre: "+(String)entry.getKey());
		}
	}
	
	public void compararEjeTraccionMod(String nombreEjeTraccion){
		
		switch (nombreEjeTraccion) {
			case "eje1":
				serviciosVentanaEsquemaEjes.setFondoEje1("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
				break;
			case "eje2":
				serviciosVentanaEsquemaEjes.setFondoEje2("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
				break;
			case "eje3":
				serviciosVentanaEsquemaEjes.setFondoEje3("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
				break;
			case "eje4":
				serviciosVentanaEsquemaEjes.setFondoEje4("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
				break;
			case "eje5":
				serviciosVentanaEsquemaEjes.setFondoEje5("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
				break;
		}
		
	}
	
	@Override
	public void activarModificar() {
		if (vehiculoSeleccionado.getIdVehiculo() != null){
			reiniciarTabIndex();
			modificar = true;
			if (vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("S")) {
				dentroCarretera = "Si";
			} else if (vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("N")) {
				dentroCarretera = "No";
			}
			serviciosVentanaEsquemaEjes.cancelar(eventoCancelar);
			serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(vehiculoSeleccionado.getEsquemaEjes());
			serviciosVentanaEsquemaEjes.verificarDatosActivos();
			listaEjeTraccion = EjeTraccionDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
			System.out.println("tamaño lista eje traccion: "+listaEjeTraccion.size() );
			for(int i = 0; i < listaEjeTraccion.size() ; i++){// recorra la lista de eje traccion
				mapaEjesTraccion.put(listaEjeTraccion.get(i).getNombre(), true);
			}
			recorreMapaModificar();
			activarCheck();
			//hacer mètodo que recorra el mapa y segùn la clave o key del valor obtenido del mapa evaluarlo en un switch, el cual serà un mètodo aparte
			//que le esntre como paràmetro un String (valor a evaluar en los case), este mètodo lo debemos usar tambièn en el mètodo antes
			//creado llamado validar traccion
			
			cargarListaEmpleados();
			modificarVisibilidadBotonesAsignacionConductor();
			buscarRutaVehiculoSeleccionado();
			llenarDataModelEmpleadosDestino();
			llenarDataModelEmpleadosOrigen();
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún Vehículo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void buscarRutaVehiculoSeleccionado() {
		List<RutaVehiculo> listaRutaVehiculo = new ArrayList<RutaVehiculo>();
		ruta = new Ruta();
		listaRutaVehiculo = RutaVehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
		if(listaRutaVehiculo.size() != 0) {
			this.ruta = listaRutaVehiculo.get(0).getRuta();	//AQUI PUEDE HABER MÁS RUTAS POR CADA VEHICULO, PERO EN LA PRIMERA VERSIÓN DEL PROYECTO SE TOMARÁ SOLO UNA RUTA
		} else {
			this.ruta.setNombre("ninguno");
			this.ruta.setTipoCarretera(new TipoCarretera());
			this.ruta.getTipoCarretera().setNombre("ninguno");
		}
	}

	@Override
	public void activarConsultar() {
		if (vehiculoSeleccionado.getIdVehiculo() != null) {
			consultar = true;
			reiniciarTabIndex();
			if (vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("S")) {
				dentroCarretera = "Si";
			} else if (vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("N")) {
				dentroCarretera = "No";
			}
			serviciosVentanaEsquemaEjes.cancelar(eventoCancelar);
			serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(vehiculoSeleccionado.getEsquemaEjes());
			serviciosVentanaEsquemaEjes.verificarDatosActivos();
			cargarListaEmpleados();
			buscarRutaVehiculoSeleccionado();
			llenarDataModelEmpleadosDestino();
			listaEjeTraccion = EjeTraccionDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
			for(int i = 0; i < listaEjeTraccion.size() ; i++){// recorra la lista de eje traccion
				mapaEjesTraccion.put(listaEjeTraccion.get(i).getNombre(), true);
			}
			recorreMapaModificar();
			activarCheck();
//			llenarDataModelEmpleadosOrigen();
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
			renderConsulta();
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún Vehículo de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (vehiculoSeleccionado.getIdVehiculo() != null){
			eliminar = true;
			mensajeEliminar ="Está seguro de eliminar el vehículo "+ vehiculoSeleccionado.getPlaca() +"?";
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún vehículo de la lista");
			cancelar(eventoCancelar);
		}
	}

	/**
	 * Este metodo llenarDataModel, es un metodo de que se encarga de buscar todos los vehiculos
	 */
	@Override
	public void llenarDataModel() {
		this.listaVehiculo = vehiculoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa);
		setVehiculoDataModel(new VehiculoDataModel(listaVehiculo));
		
		if(listaVehiculo.size() > 0){
			rows = 5;
		}else{
			rows = 0;
		}
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.vehiculo.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
		
	}
	
	public void actualizarMarcaTipoDentroCarretera() {
		String carreteraDentro = "";
		if(!modificar) {
			if(idModeloVehiculo!=0) { 
				vehiculo.setModeloVehiculo((ModeloVehiculo) ModeloVehiculoDAO.getInstancia().buscarEntidadPorClave(idModeloVehiculo));
				carreteraDentro = vehiculo.getModeloVehiculo().getTipoVehiculo().getDentroCarretera();
			} else {
				vehiculo.getModeloVehiculo().getMarcaVehiculo().setNombre("");
				vehiculo.getModeloVehiculo().getTipoVehiculo().setNombre("");
			}
		} else {
			if(idModeloVehiculo!=0) {
				vehiculoSeleccionado.setModeloVehiculo((ModeloVehiculo) ModeloVehiculoDAO.getInstancia().buscarEntidadPorClave(idModeloVehiculo));
				carreteraDentro = vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera();
			}
		}
		
		if(carreteraDentro.equals("S")) {
			dentroCarretera = "Si";
		} else if(carreteraDentro.equals("N")) {
			dentroCarretera = "No";
		} else {
			dentroCarretera = "";
		}
		
		
	}
	
	public void llenarModelos() {
		ModeloVehiculoDAO modeloVehiculoDao = ModeloVehiculoDAO.getInstancia();
		listaModeloVehiculo = new ArrayList<ModeloVehiculo>();
		listaModeloVehiculo = modeloVehiculoDao.buscarTodasEntidades();
	}
	
	public void llenarListaEsquema() {
		listaEsquemaEjes = EsquemaEjeDAO.getInstancia().buscarTodasEntidades();
		introducirEsquemaSeleccionado();
	}
	
	/**
	 * El metodo adelantarEsquema, es un metodo que muestra todos los equema de ejes cuando se pulsa el boton de siguiente en la vista 
	 * para ir cambiando los esquema por otros.
	 * Este metodo es usado para nuevo y modificar.
	 */
	public void adelantarEsquema() {
		int tamanoLista = 0;
		tamanoLista = listaEsquemaEjes.size()-1;
		serviciosVentanaEsquemaEjes.cancelar(eventoCancelar);
		if(indiceListaEsquemaEje==tamanoLista) {
			indiceListaEsquemaEje = 0;
			introducirEsquemaSeleccionado();
			mensajes.informativo("Fin de la Lista", "No existen más esquemas de ejes permitidos");
		} else {
			indiceListaEsquemaEje++;
			introducirEsquemaSeleccionado();
		}
		activarCheck();	
		validarEsquemasEjes();
		mapaEjesTraccion.clear();
	}
	
	/**
	 * El metodo retrocederEsquema, es un metodo que muestra todos los equema de ejes cuando se pulsa el boton de atras en la vista 
	 * para ir cambiando los esquema por otros.
	 * Este metodo es usado para nuevo y modificar.
	 */
	public void retrocederEsquema() {
		int indiceComparativo = 0;
		serviciosVentanaEsquemaEjes.cancelar(eventoCancelar);
		if(indiceListaEsquemaEje==indiceComparativo) {
			introducirEsquemaSeleccionado();
			mensajes.informativo("Fin de la Lista", "No existen más esquemas de ejes permitidos");
		} else {
			indiceListaEsquemaEje--;
			introducirEsquemaSeleccionado();
		}
		activarCheck();
		validarEsquemasEjes();
		mapaEjesTraccion.clear();
	}
	
	public void introducirEsquemaSeleccionado() {
		if(!modificar) {
			vehiculo.setEsquemaEjes(listaEsquemaEjes.get(indiceListaEsquemaEje));
		} else {
			vehiculoSeleccionado.setEsquemaEjes(listaEsquemaEjes.get(indiceListaEsquemaEje));
		}
		serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(listaEsquemaEjes.get(indiceListaEsquemaEje));
		serviciosVentanaEsquemaEjes.verificarDatosActivos();
	}
	
	public void onTransfer(TransferEvent event) {  
        StringBuilder builder = new StringBuilder();  
        for(Object item : event.getItems()) {  
            builder.append(((Empleado) item).getNombre()).append("<br />");  
        }  
        
        mensajes.informativo("Conductor Asignado", builder.toString());
    }  
	
	/**
	 * Este metodo obtenerNombreEje, lo que hace es, obtener el nombre del id que viene desde la vista pero como nombre es largo lo unico que se
	 * quiere el nommbre eje, se hace un for para ir contanto por caracter hasta la posicion que se quiere y obtener el valor esperado. 
	 */
	public String obtenerNombreEje(String nombreEje) {
		String nombreEjeT = "";
		for (int i = 0; i < nombreEje.length(); i++) {
			if(i<=3){
				nombreEjeT = nombreEjeT+nombreEje.charAt(i);
			}
		}
		return nombreEjeT;
	}
	
	/**
	 * El metodo activarCheck, lo que hace es mostrar los check de la vista cuando son diferente de :, que seria remolque, y los check son necesario para lo
	 * que son chutos, que es para tomar la opcion de traccion.
	 * Cuando se muestra los remolque que es igual a los :, con este metodo, no mostrara los check ya que ninguno usa traccion.
	 */
	public void activarCheck(){
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0)!=':'){
			renderedCheck = true;
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje1() != null){
				check1 = false;
				if(mapaEjesTraccion.containsKey("eje1")) {
					traccion1 = true;
				} else {
					traccion1 = false;
				}
			}else{
				check1 = true;
				traccion1 = false;
			}
			
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje2() != null){
				check2 = false;
				if(mapaEjesTraccion.containsKey("eje2")) {
					traccion2 = true;
				} else {
					traccion2 = false;
				}
			}else{
				check2 = true;
				traccion2 = false;
			}
			
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje3() != null){
				check3 = false;
				if(mapaEjesTraccion.containsKey("eje3")) {
					traccion3 = true;
				} else {
					traccion3 = false;
				}
			}else{
				check3 = true;
				traccion3 = false;
			}
			
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje4() != null){
				check4 = false;
				if(mapaEjesTraccion.containsKey("eje4")) {
					traccion4 = true;
				} else {
					traccion4 = false;
				}
			}else{
				check4 = true;
				traccion4 = false;
			}
			
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje5() != null){
				check5 = false;
				if(mapaEjesTraccion.containsKey("eje5")) {
					traccion5 = true;
				} else {
					traccion5 = false;
				}
			}else{
				check5 = true;
				traccion5 = false;
			}
		}else{
			renderedCheck = false;
			traccion1 = false;
			traccion2 = false;
			traccion3 = false;
			traccion4 = false;
			traccion5 = false;
			check1	  = true;
			check2	  = true;
			check3	  = true;
			check4 	  = true;
			check5	  = true;
		}
		
	}
	
	/**
	 * Con el metodo validarTransmision, es un evento que esta compuesto por ajax para hacer la oeracion sin poder recargar la pagina nuevamente.
	 * Este evento lo que hace es tomar el id de la vista atraves de un switch, si el id del eje es seleccionado es igual al case, cambia la imagen
	 * de eje por una de traccion y a la ves se va almacenando en un mapa para ir guardando los valores de los ejes que son seleccionado.
	 * Si se desea destildar la opcion de traccion, se hace otro switch para volver colocar la imagen del eje como estaba antes y se remueve del mapa 
	 * para que el valor no sea guardado. 
	 */
	public void validarTransmision(ValueChangeEvent event){
		/*
		 1.- Obtener ID del eje en cuestion (hacer metodo obtenerNombre)
		 2.- Llenar el mapa con los ejes seleccionados
		 3.- ingresar el valor para el mapa 
		 */
		traccion1 = (boolean)event.getNewValue();
		if(traccion1==true){
			
			switch (obtenerNombreEje(event.getComponent().getId())) {
				case "eje1":
					serviciosVentanaEsquemaEjes.setFondoEje1("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
					break;
				case "eje2":
					serviciosVentanaEsquemaEjes.setFondoEje2("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
					break;
				case "eje3":
					serviciosVentanaEsquemaEjes.setFondoEje3("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
					break;
				case "eje4":
					serviciosVentanaEsquemaEjes.setFondoEje4("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
					break;
				case "eje5":
					serviciosVentanaEsquemaEjes.setFondoEje5("background: url(../imagenes/ejeTraccion.png) no-repeat; background-position: center;");
					break;
			}
			mapaEjesTraccion.put(obtenerNombreEje(event.getComponent().getId()), traccion1);
			System.out.println("ID del checkBox seleccionado eje: "+event.getComponent().getId());
			System.out.println("valor obtenido del eje: "+obtenerNombreEje(event.getComponent().getId()));
			System.out.println("valor del eje: "+traccion1);
		} else {
			switch (obtenerNombreEje(event.getComponent().getId())) {
			case "eje1":
				serviciosVentanaEsquemaEjes.setFondoEje1("background: url(../imagenes/eje.png) no-repeat; background-position: center;");
				break;
			case "eje2":
				serviciosVentanaEsquemaEjes.setFondoEje2("background: url(../imagenes/eje.png) no-repeat; background-position: center;");
				break;
			case "eje3":
				serviciosVentanaEsquemaEjes.setFondoEje3("background: url(../imagenes/eje.png) no-repeat; background-position: center;");
				break;
			case "eje4":
				serviciosVentanaEsquemaEjes.setFondoEje4("background: url(../imagenes/eje.png) no-repeat; background-position: center;");
				break;
			case "eje5":
				serviciosVentanaEsquemaEjes.setFondoEje5("background: url(../imagenes/eje.png) no-repeat; background-position: center;");
				break;
		}
			mapaEjesTraccion.remove(obtenerNombreEje(event.getComponent().getId()));
			System.out.println("Valor del Eje: "+traccion1 +" Eje Removido: "+obtenerNombreEje(event.getComponent().getId()));
		}
	
		Iterator iterador = mapaEjesTraccion.entrySet().iterator();
		Map.Entry entry;
		while(iterador.hasNext()) {
			entry = (Entry) iterador.next();
			System.out.println("clave (eje): "+entry.getKey());
			System.out.println("valor del mapa: "+entry.getValue());
		}
		if(mapaEjesTraccion.size()>0){
			traccionTildado = true;
		} else {
			traccionTildado = false;
		}
	}
	
	/**
	 * El flujoPrimerIngreso, es un evento que va con la funcion del wizard en la vista de nuevo, a la cual sirve para la funcion de los 
	 * botones siguiente y atras, para las operacciones que se van hacer.
	 * Tambien se hacen las validaciones para cuando le de siguiente no falte ningun campo por llenar, que sean obligatorio.
	 * Tambien se hacen validaciones en el esquema de eje,  que seria cuando pasa a la siguiente operacion. Cuando sea chuto tiene validacion
	 * debe selecccionar al menos un eje contraccion con la condicion de mapaEjesTraccion.size que es el que toma el tamaño del mapa y si
	 * tiene un valor asignado o un eje contraccion tildado.
	 */
	public String flujoPrimerIngreso(FlowEvent event) {
		
		if(!event.getNewStep().equals("tab1")){
			validarEsquemasEjes();
			activarCheck();
			if(vehiculo.getPlaca()!=""){
				if(idModeloVehiculo!=0 ){
					if(!tipoEsquemaEjes.equals("0")){
						if(!vehiculo.getAnoFabricacion().equals("") && vehiculo.getAnoFabricacion()!=null){
							if(vehiculo.getKilometraje()!=0.0){
								if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0)!=':'){
									if(mapaEjesTraccion.size()>0){//traccionTildado){
										//traccionTildado = false;
										return event.getNewStep();
									}else{
										mensajes.advertencia("ATENCIÓN!", "El vehículo debe tener al menos un (1) eje con tracción");
										return "esquemaEjes";
									}
								}else{
									return event.getNewStep();
								}
							}else{
								mensajes.error("Error:", "Campo requerido, colocar kilometraje actual del vehículo");
								return "tab1";
							}
						}else{
							mensajes.error("Error:", "Campo requerido, colocar el año de fabricación del vehículo");
							return "tab1";
						}
					}else{
						mensajes.error("Error:", "Campo requerido, Selecciona un esquema de ejes");
						return "tab1";
					}
				}else{
					mensajes.error("Error:", "Campo requerido, Selecciona un modelo");
					return "tab1";
				}
			}else{
				mensajes.error("Error:", "Campo requerido, colocar placa del vehículo");
				return "tab1";
			}
		}else{
			return event.getNewStep();
		}
		
	}  
	
	public void validarConductor(){
		if(tipoEsquemaEjes.equals("chuto")){
			if(listaEmpleadosDestino.size() > 0 ){
				if(idRuta != 0){
					if(conductorPrincipal.getCedulaEmpleado()!=null && !conductorPrincipal.getCedulaEmpleado().equals("")){
						RequestContext.getCurrentInstance().addCallbackParam("validar", true);
					}else{
						mensajes.error("Error:", "Campo requerido, debe asignar un conductor principal");
					}
				}else{
					mensajes.error("Error:", "Campo requerido, selecciona una ruta");
				}
			}else{
				mensajes.error("Error:", "Campo requerido, debe asignar un conductor");
			}
		}else{
			if(tipoEsquemaEjes.equals("remolque")){
				if(idRuta != 0){
					this.conductorPrincipal.setNombre("--");
					this.conductorPrincipal.setCedulaEmpleado("--");
					
					RequestContext.getCurrentInstance().addCallbackParam("validar", true);
				}else{
					mensajes.error("Error:", "Campo requerido, selecciona una ruta");
				}
			}
		}
	}
	
	/**
	 * El flujoPrimerIngresoMod, es un evento que va con la funcion del wizard en la vista de modificar, a la cual sirve para la funcion de los 
	 * botones siguiente y atras, para las operacciones que se van hacer.
	 * Tambien se hacen las validaciones para cuando le de siguiente no falte ningun campo por llenar, que sean obligatorio.
	 */
	public String flujoPrimerIngresoMod(FlowEvent event) {
		if(!event.getNewStep().equals("datosBasicos")){
			validarEsquemaEjesMod();
			if(vehiculoSeleccionado.getPlaca() !=""){
				if(vehiculoSeleccionado.getAnoFabricacion() != ""){
					if(vehiculoSeleccionado.getKilometraje() != 0.0){
						if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0)!=':'){
							if(mapaEjesTraccion.size()>0){//traccionTildado){
								//traccionTildado = false;
								return event.getNewStep();
							}else{
								mensajes.advertencia("ATENCIÓN!", "El vehículo debe tener al menos un (1) eje con tracción");
								return "esquemaEjess";
							}
						}else{
							return event.getNewStep();
						}
					}else{
						mensajes.error("Error:", "Campo requerido, colocar kilometraje del vehículo");
						return "datosBasicos";
					}
				}else{
					mensajes.error("Error:", "Campo requerido, colocar el año de fabricación del vehículo");
					return "datosBasicos";
				}
			}else{
				mensajes.error("Error:", "Campo requerido, colocar placa del vehículo");
				return "datosBasicos";
			}
		}else{
			return event.getNewStep();
		}
		
		
	}
	
	/**
	 * El metodo validarConductorMod, es para hacer validaciones en boton de guarda que es la final de la operacion de la vista,
	 * cuando se halla que modificar, si es chuto muestra los conductores, hacen las validaciones para cada campo, como asignar conductor,
	 * asignar conductor principal y la ruta en donde va ir el vehiculo.
	 * Si es remolque solo se hace la validacion de la ruta en donde se va a ir el remolque, como no muestra ningun conductor no es necesario 
	 * haccer mas validaciones.   
	 */
	public void validarConductorMod(){
		if(!tipoEsquemaEjesMod.equals("0")){
			if(tipoEsquemaEjesMod.equals("chuto")){
				if(listaEmpleadosDestino.size() > 0){
					if(conductorPrincipal.getCedulaEmpleado()!=null && !conductorPrincipal.getCedulaEmpleado().equals("")){
						RequestContext.getCurrentInstance().addCallbackParam("validar", true);
					}else{
						mensajes.error("Error:", "Campo requerido, debe asignar un conductor principal");
					}
				}else{
					mensajes.error("Error:", "Campo requerido, debe asignar un conductor");
				}
			}else{
				if(tipoEsquemaEjesMod.equals("remolque")){
					RequestContext.getCurrentInstance().addCallbackParam("validar", true);
				}
				
			}
		}else{
			System.out.println("esta todo igual no se  modifico");
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Chuto")){
				System.out.println("modificar chuto");
				if(listaEmpleadosDestino.size() > 0){
					if(conductorPrincipal.getCedulaEmpleado()!=null && !conductorPrincipal.getCedulaEmpleado().equals("")){
						RequestContext.getCurrentInstance().addCallbackParam("validar", true);
					}else{
						mensajes.error("Error:", "Campo requerido, debe asignar un conductor principal");
					}
				}else{
					mensajes.error("Error:", "Campo requerido, debe asignar un conductor");
				}
			}else{
				if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Remolque")){
					System.out.println("modificar remolque");
					RequestContext.getCurrentInstance().addCallbackParam("validar", true);
					this.conductorPrincipal.setNombre("--");
					this.conductorPrincipal.setCedulaEmpleado("--");
				}
			}
			
		}
		
		
//		if(listaEmpleadosDestino.size() > 0){
//			if(conductorPrincipal.getCedulaEmpleado()!=null && !conductorPrincipal.getCedulaEmpleado().equals("")){
//				RequestContext.getCurrentInstance().addCallbackParam("validar", true);
//			}else{
//				mensajes.error("Error:", "Campo requerido, debe asignar un conductor principal");
//			}
//		}else{
//			mensajes.error("Error:", "Campo requerido, debe asignar un conductor");
//		}
	}
	
	/**
	 * El metodo validarEsquemaEjes, se usa para validar un nuevo registro del vehiculo y mostrar si es un chuto o remolque atraves de un boolean.
	 * Cuando se selecciona un chuto muestra los conductores y si es remolque no muestra los conductores ya que los remolque no se le asigna conductores.
	 * La condición serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0) != ':', cuando es diferente de : solo muestra el 
	 * esquema que sean chuto.
	 * La condición serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0) == ':', cuando es igual a : muestra los esquema 
	 * de ejes que son remolque.
	 */
	public void validarEsquemasEjes(){
		if(tipoEsquemaEjes.equals("chuto")){
			renderConductor = true;
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0) != ':'){
				System.out.println("==> condision de : "+tipoEsquemaEjes);
			}
		}else{
			if(tipoEsquemaEjes.equals("remolque")){
				renderConductor = false;
				if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema().charAt(0) == ':'){
					System.out.println("==> condision de : "+tipoEsquemaEjes);
					
				}
			}
		}
		
	}
	
	/**
	 * El metodo validarEsquemaEjesMod, es cuando se halla que modificar un valor, esto lo que hace es validar cuando el vehiculo es seleccionado y
	 * tambien cuando selecciona si es chuto o remolque para mostrar los conductores atraves de un boolean.
	**/
	public void validarEsquemaEjesMod(){		
		if(!tipoEsquemaEjesMod.equals("0")){
			
			if(tipoEsquemaEjesMod.equals("chuto")){
				renderConductor = true;
				System.out.println("esquema seleccionado1: "+serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo());
			}else{
				if(tipoEsquemaEjesMod.equals("remolque")){
					renderConductor = false;
					System.out.println("esquema seleccionado2: "+serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo());
				}
			}
		}else{
			System.out.println("no se hace nada");
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Chuto")){
				renderConductor = true;
				System.out.println("esquema seleccionado1: "+serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo());

			}else{
				if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Remolque")){
					renderConductor = false;
					System.out.println("esquema seleccionado1: "+serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo());

				}
			}
		}
	}
	
	/**
	 * El metodo renderdConsular, a la cual esta compuesto por boolean, es la validacion que va tener cuando se quiere hacer la consulta de un vehiculo
	 * dependiendo el tipo de vehiculo si es chuto, muestra los conductores y si es remolque no muestra los conductores porque a los remolque no se 
	 * le asigna conductores. 
	**/
	private boolean renderdConsultar = false;
	public  void renderConsulta(){
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Chuto")){
			renderdConsultar = true;
		}else{
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Remolque")){
				renderdConsultar = false;
			}
		}
		
	}
	
	/**
	 * El metodo eliminarEjeTraccion, es un metodo que elimina los datos fisico de la base de datos eje_traccion que es el que lleva el control de los ejes
	 * decada vehiculo que se seleccione, lo que hace, cuando se quiere modificar cada eje segun la traccion va eliminar los valores guardado para hacer
	 * remplazado por los nuevos valores de los ejes con traccion segun se seleccione. 
	 * **/
	public void eliminarEjeTraccion(){
		List<EjeTraccion> listaEjeTraccionCompleta = (List<EjeTraccion>) EjeTraccionDAO.getInstancia().buscarTodasEntidades();
		if(!tipoEsquemaEjesMod.equals("0")){
			if(tipoEsquemaEjesMod.equals("chuto")){
				System.out.println("se elimina la transmision");
				for (int i = 0; i < listaEjeTraccionCompleta.size(); i++) {
					if(listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo() == vehiculoSeleccionado.getIdVehiculo()){
						System.out.println("lista de ejes traccion: "+listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo());
						EjeTraccionDAO.getInstancia().eliminarFisicamente(listaEjeTraccionCompleta.get(i));
					}
				}
			}else{
				if(tipoEsquemaEjesMod.equals("remolque")){
					System.out.println("Si se elimina cuando selecciona remolque");
					for (int i = 0; i < listaEjeTraccionCompleta.size(); i++) {
						if(listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo() == vehiculoSeleccionado.getIdVehiculo()){
							System.out.println("lista de ejes traccion: "+listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo());
							EjeTraccionDAO.getInstancia().eliminarFisicamente(listaEjeTraccionCompleta.get(i));
						}
					}
				}
			}
			
		}else{
			if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Chuto")){
				System.out.println("se elimina la transmision");
				for (int i = 0; i < listaEjeTraccionCompleta.size(); i++) {
					if(listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo() == vehiculoSeleccionado.getIdVehiculo()){
						System.out.println("lista de ejes traccion: "+listaEjeTraccionCompleta.get(i).getVehiculo().getIdVehiculo());
						EjeTraccionDAO.getInstancia().eliminarFisicamente(listaEjeTraccionCompleta.get(i));
					}
				}
			}else{
				if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getTipo().equals("Remolque")){
					System.out.println("No se elimina nada");
				}
			}
		}
		
		
		
	}
	
	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Vehiculo getVehiculoSeleccionado() {
		return vehiculoSeleccionado;
	}

	public void setVehiculoSeleccionado(Vehiculo vehiculoSeleccionado) {
		this.vehiculoSeleccionado = vehiculoSeleccionado;
	}

	public VehiculoDataModel getVehiculoDataModel() {
		return vehiculoDataModel;
	}

	public void setVehiculoDataModel(VehiculoDataModel vehiculoDataModel) {
		this.vehiculoDataModel = vehiculoDataModel;
	}

	public List<Vehiculo> getListaVehiculo() {
		return listaVehiculo;
	}

	public void setListaVehiculo(List<Vehiculo> listaVehiculo) {
		this.listaVehiculo = listaVehiculo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<ModeloVehiculo> getListaModeloVehiculo() {
		return listaModeloVehiculo;
	}

	public void setListaModeloVehiculo(List<ModeloVehiculo> listaModeloVehiculo) {
		this.listaModeloVehiculo = listaModeloVehiculo;
	}

	public int getIdModeloVehiculo() {
		return idModeloVehiculo;
	}

	public void setIdModeloVehiculo(int idModeloVehiculo) {
		this.idModeloVehiculo = idModeloVehiculo;
	}

	public String getDentroCarretera() {
		return dentroCarretera;
	}

	public void setDentroCarretera(String dentroCarretera) {
		this.dentroCarretera = dentroCarretera;
	}

	public ServiciosVentanaEsquemaEjes getServiciosVentanaEsquemaEjes() {
		return serviciosVentanaEsquemaEjes;
	}

	public void setServiciosVentanaEsquemaEjes(
			ServiciosVentanaEsquemaEjes serviciosVentanaEsquemaEjes) {
		this.serviciosVentanaEsquemaEjes = serviciosVentanaEsquemaEjes;
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

	public Empleado getEmpleadoSeleccionadoDestino() {
		return empleadoSeleccionadoDestino;
	}

	public void setEmpleadoSeleccionadoDestino(Empleado empleadoSeleccionadoDestino) {
		this.empleadoSeleccionadoDestino = empleadoSeleccionadoDestino;
	}

	public EmpleadoDataModel getEmpleadosDestinoDataModel() {
		return empleadosDestinoDataModel;
	}

	public void setEmpleadosDestinoDataModel(
			EmpleadoDataModel empleadosDestinoDataModel) {
		this.empleadosDestinoDataModel = empleadosDestinoDataModel;
	}

	public EmpleadoDataModel getEmpleadosOrigenDataModel() {
		return empleadosOrigenDataModel;
	}

	public void setEmpleadosOrigenDataModel(
			EmpleadoDataModel empleadosOrigenDataModel) {
		this.empleadosOrigenDataModel = empleadosOrigenDataModel;
	}

	public Empleado getConductorPrincipal() {
		return conductorPrincipal;
	}

	public void setConductorPrincipal(Empleado conductorPrincipal) {
		this.conductorPrincipal = conductorPrincipal;
	}

	public boolean isBotonAsignarVisible() {
		return botonAsignarVisible;
	}

	public void setBotonAsignarVisible(boolean botonAsignarVisible) {
		this.botonAsignarVisible = botonAsignarVisible;
	}

	public boolean isBotonRegresarVisible() {
		return botonRegresarVisible;
	}

	public void setBotonRegresarVisible(boolean botonRegresarVisible) {
		this.botonRegresarVisible = botonRegresarVisible;
	}

	public VehiculoEmpleado getVehiculoEmpleado() {
		return vehiculoEmpleado;
	}

	public void setVehiculoEmpleado(VehiculoEmpleado vehiculoEmpleado) {
		this.vehiculoEmpleado = vehiculoEmpleado;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public List<Ruta> getListaRuta() {
		return listaRuta;
	}

	public void setListaRuta(List<Ruta> listaRuta) {
		this.listaRuta = listaRuta;
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public EmpleadoDataModel getEmpleadoTemporalDataModel() {
		return empleadoTemporalDataModel;
	}

	public void setEmpleadoTemporalDataModel(
			EmpleadoDataModel empleadoTemporalDataModel) {
		this.empleadoTemporalDataModel = empleadoTemporalDataModel;
	}

	public List<Empleado> getListaEmpleadosDestinoTemporal() {
		return listaEmpleadosDestinoTemporal;
	}

	public void setListaEmpleadosDestinoTemporal(
			List<Empleado> listaEmpleadosDestinoTemporal) {
		this.listaEmpleadosDestinoTemporal = listaEmpleadosDestinoTemporal;
	}

	public ServiciosVentanaEmpleado getServiciosVentanaEmpleado() {
		return serviciosVentanaEmpleado;
	}

	public void setServiciosVentanaEmpleado(
			ServiciosVentanaEmpleado serviciosVentanaEmpleado) {
		this.serviciosVentanaEmpleado = serviciosVentanaEmpleado;
	}

	public boolean isTransmision() {
		return transmision;
	}

	public void setTransmision(boolean transmision) {
		this.transmision = transmision;
	}

	public boolean isCheck1() {
		return check1;
	}

	public void setCheck1(boolean check1) {
		this.check1 = check1;
	}

	public boolean isCheck2() {
		return check2;
	}

	public void setCheck2(boolean check2) {
		this.check2 = check2;
	}

	public boolean isCheck3() {
		return check3;
	}

	public void setCheck3(boolean check3) {
		this.check3 = check3;
	}

	public boolean isCheck4() {
		return check4;
	}

	public void setCheck4(boolean check4) {
		this.check4 = check4;
	}

	public boolean isCheck5() {
		return check5;
	}

	public void setCheck5(boolean check5) {
		this.check5 = check5;
	}

	public boolean isTraccion1() {
		return traccion1;
	}

	public void setTraccion1(boolean traccion1) {
		this.traccion1 = traccion1;
	}

	public boolean isTraccion2() {
		return traccion2;
	}

	public void setTraccion2(boolean traccion2) {
		this.traccion2 = traccion2;
	}

	public boolean isTraccion3() {
		return traccion3;
	}

	public void setTraccion3(boolean traccion3) {
		this.traccion3 = traccion3;
	}

	public boolean isTraccion4() {
		return traccion4;
	}

	public void setTraccion4(boolean traccion4) {
		this.traccion4 = traccion4;
	}

	public boolean isTraccion5() {
		return traccion5;
	}

	public void setTraccion5(boolean traccion5) {
		this.traccion5 = traccion5;
	}

	public boolean isRenderedCheck() {
		return renderedCheck;
	}

	public void setRenderedCheck(boolean renderedCheck) {
		this.renderedCheck = renderedCheck;
	}

	public List<EjeTraccion> getListaEjeTraccion() {
		return listaEjeTraccion;
	}

	public void setListaEjeTraccion(List<EjeTraccion> listaEjeTraccion) {
		this.listaEjeTraccion = listaEjeTraccion;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getTipoEsquemaEjes() {
		return tipoEsquemaEjes;
	}

	public void setTipoEsquemaEjes(String tipoEsquemaEjes) {
		this.tipoEsquemaEjes = tipoEsquemaEjes;
	}

	public boolean isRenderConductor() {
		return renderConductor;
	}

	public void setRenderConductor(boolean renderConductor) {
		this.renderConductor = renderConductor;
	}

	public String getTipoEsquemaEjesMod() {
		return tipoEsquemaEjesMod;
	}

	public void setTipoEsquemaEjesMod(String tipoEsquemaEjesMod) {
		this.tipoEsquemaEjesMod = tipoEsquemaEjesMod;
	}

	public boolean isRenderdConsultar() {
		return renderdConsultar;
	}

	public void setRenderdConsultar(boolean renderdConsultar) {
		this.renderdConsultar = renderdConsultar;
	}

}