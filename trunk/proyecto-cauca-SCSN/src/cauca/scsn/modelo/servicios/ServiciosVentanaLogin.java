package cauca.scsn.modelo.servicios;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;

import org.omg.CORBA.portable.InputStream;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import java.util.List;
import java.util.Map.Entry;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.CargoDAO;
import cauca.scsn.modelo.dao.EmpleadoCaucaDAO;
import cauca.scsn.modelo.dao.EmpleadoDAO;
import cauca.scsn.modelo.dao.EmpresaDAO;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.ProveedorDAO;
import cauca.scsn.modelo.dao.seguridad.EncuestaDAO;
import cauca.scsn.modelo.dao.seguridad.InterfazDAO;
import cauca.scsn.modelo.dao.seguridad.LogDAO;
import cauca.scsn.modelo.dao.seguridad.OperacionDAO;
import cauca.scsn.modelo.dao.seguridad.OperacionRolDAO;
import cauca.scsn.modelo.dao.seguridad.RolDAO;
import cauca.scsn.modelo.dao.seguridad.UsuarioDAO;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.EmpleadoCauca;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.NeumaticoVehiculo;
import cauca.scsn.modelo.entidad.Proveedor;
import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Interfaz;
import cauca.scsn.modelo.entidad.seguridad.Log;
import cauca.scsn.modelo.entidad.seguridad.OperacionRol;
import cauca.scsn.modelo.entidad.seguridad.Operacion;
import cauca.scsn.modelo.entidad.seguridad.Rol;
import cauca.scsn.modelo.entidad.seguridad.Usuario;
import cauca.scsn.modelo.entidad.seguridad.Visita;


@ManagedBean
@ViewScoped
public class ServiciosVentanaLogin implements Serializable{

	private static final long 	serialVersionUID 	= -2152389656664659476L;
	private String 				nombre;
	private String 				clave;
	private boolean 			logueado 			= false;
	private boolean				primerIngreso		= false;
	private Empresa 			empresa;
	private Empleado 			empleado;
	private Integer				idCargo;
	private boolean				gestionVehiculoVisible = true;
	private Usuario				usuario = new Usuario();
	
	private List<OperacionRol>  		listaOperacioRol = new ArrayList<OperacionRol>();
	private ValidadorBean				validador = new ValidadorBean();
	private ControladorMensajes 		mensajes = new ControladorMensajes();
	private List<Log> 					listaLog = new ArrayList<Log>();
	private Log 						log = new Log();
	private String 						userEmpleado = "";
	private String 						claveEmpleado = "";
	private String 						claveEmpelado2 = "";	
	private String						cargo = "";

	private ServiciosVentanaEncuesta servicioEncuesta = new ServiciosVentanaEncuesta();
	private HttpSession session;
	
	public ServiciosVentanaLogin() {
		super();
		empresa = new Empresa();
		empleado = new Empleado();
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		System.out.println("MAC -- LOGIN: "+session.getAttribute("mac"));
		cargo = ((Cargo) CargoDAO.getInstancia().buscarEntidadPorClave(4)).getNombre();
		
	}
	
 
	 /**
    * El metodo controlAccesro, es para tomar el control del acceso para cada usuario que ingrese al sistema, a la cual mostrara
      solo lo que va hacer el usurio segun sea el rol y esto llarmara a la base de datos la tabla operacion_rol para hacer las 
      comparaciones del rol en operaciones. 
  **/ 
	public void controlAcceso(Rol rol){
		listaOperacioRol = OperacionRolDAO.getInstancia().buscarTodasEntidades();
		boolean valorObtenido = false; 
		for (int i = 0; i < listaOperacioRol.size(); i++) {
			int operacion = listaOperacioRol.get(i).getOperacion().getIdOperacion(); //Debe venir del objeto obtenido de la BD -> id_operacion
			if(listaOperacioRol.get(i).getRol().getIdRol() == rol.getIdRol()){
				valorObtenido = true;
				session.setAttribute(Integer.toString(operacion), valorObtenido);
			}else{
				valorObtenido = true;
				session.setAttribute(Integer.toString(operacion), valorObtenido);
			}
		}	
	}
	
   /**
    * Este metodo login, va hacer la validacion del usuario y contraseña, tambien hara la validacion si esta registrado o no.
    * Hace validaciones a los conductores a la cual no pueden ingresar al sistema.
    * Tambien mostrara la encuesta a los rolos administradores, gerencia y operador, cuando allan pasado 15 dias.
  **/  
	public void login(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		ControladorMensajes controlMensaje = new ControladorMensajes();
		if (!primerIngreso) {
			//Tomar el nombre = login y el pass para buscar coincidencia con el nombre en la tabla usuario
			usuario = (Usuario)UsuarioDAO.getInstancia().buscarEntidadPorPropiedad("login", nombre);
			if(usuario != null && usuario.getIdUsuario() != null) {
				if (nombre != null && nombre.equals(usuario.getLogin()) && clave != null && clave.equals(usuario.getPassword())) { 
					logueado = true;
					empleado = (Empleado) EmpleadoDAO.getInstancia().buscarEntidadPorPropiedad("usuario", usuario);
					session.setAttribute("empleado", this.empleado);
					if(usuario.getRol().getIdRol() == 4 || usuario.getRol().getIdRol() == 5 || usuario.getRol().getIdRol() == 7){
						listaLog = LogDAO.getInstancia().buscarTodasEntidades();
						
						servicioEncuesta.cargarListaUsuario(empleado.getEmpresa().getIdEmpresa());
						servicioEncuesta.cargarListaEncuesta();
						if(servicioEncuesta.verificarUltimaEncuestaMayor15Dias()){
							RequestContext.getCurrentInstance().addCallbackParam("tarea", "visita");
							RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
						}else{
							context.addCallbackParam("estaLogeado", logueado);
							if (logueado) {
								context.addCallbackParam("view", "contenido/principal.xhtml");
							}
						}
					}else{
						context.addCallbackParam("estaLogeado", logueado);
						if (logueado) {
							context.addCallbackParam("view", "contenido/principal.xhtml");
							RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
						}
					}
          /*
            En este pequeño metodo tomara el registro de cada usuario que alla ingresado al sistema como la fecha y hora.
          */     
					log = new Log();
					log.setFechaUltimoIngreso(new Date());
					log.setUsuario((Usuario) UsuarioDAO.getInstancia().buscarEntidadPorClave(usuario.getIdUsuario()));
					LogDAO.getInstancia().insertarOActualizar(log);
					controlMensaje.informativo("Bienvenid@", nombre);
					registrarEmpresaEnSesion();
					
				} else {
					logueado = false;
					controlMensaje.error("Login Error","Credenciales no válidas");
					
				}
			} else {
				System.out.println("LO SIENTO, NO CONSEGUIMOS ESTE USUARIO");
				controlMensaje.error("Login Error","Lo siento este usuario no esta registrado");
			}
		} else {
			logueado = true;
			controlMensaje.informativo("Bienvenid@", nombre);
			registrarEmpresaEnSesion();
			context.addCallbackParam("estaLogeado", logueado);
			context.addCallbackParam("view", "contenido/principal.xhtml");
			
			
		}
		nombre = "";
		clave = "";
	}
	
	public void logout() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		logueado = false;
		nombre = "";
		clave = "";
	}
	
	public void registrarEmpresaEnSesion() {

		// Esto es para probar la sesion y mandar la empresa como atributo de sesion
		if (primerIngreso) {
			
			this.empleado.setEmpresa(empresa);
			session.setAttribute("empresa", empleado.getEmpresa());
			primerIngreso = false;
		} else {
			Empresa empresa = (Empresa) EmpresaDAO.getInstancia().buscarEntidadPorPropiedad("idEmpresa", empleado.getEmpresa().getIdEmpresa());
			session.setAttribute("empresa", empleado.getEmpresa());
			session.setAttribute("rol", usuario.getRol());
		}
		controlAcceso(usuario.getRol());
		cancelar();
	}
	
   /**
    * En el metodo guardar, lo que hace es guardar, el primer ingreso de la empresa que sera cliente de Renovadora cauca.
    * Se llama el metodo registrarPrimerEncuesta, que es el llevara el control de la encuesta cuando se ingresa al sistema.
    * Tambien se llama el metodo registarProveedorCauca, donde va aguardar cada empresa que se registre por primera ves van
      a tener como principal prooveedor a cauca.
  **/  
	public void guardar(ActionEvent actionEvent) {
		try {
			EmpresaDAO.getInstancia().insertarOActualizar(empresa);
			new ControladorMensajes().informativo("Operación exitosa", "Empresa: "+ this.empresa.getNombre() +" ha sido guardada!");
			registrarAdministrador();
			empleado.setEmpresa(empresa);
			empleado.setCargo((Cargo) CargoDAO.getInstancia().buscarEntidadPorClave(4));
			EmpleadoDAO.getInstancia().insertarOActualizar(this.empleado);
			registrarPrimerEncuesta();
			registrarProveedorCauca();
			new ControladorMensajes().informativo("Operación exitosa", "Empleado: "+ this.empleado.getNombre() +" ha sido guardado!");
			
			login(actionEvent);
		} catch (Exception e) {
			System.out.println("========"+e);
			new ControladorMensajes().error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
			primerIngreso = false;
		}
	}
	 
   /**
    * Este es el metodo que sera seteado cada ves que se registre una empresa por primera ves.
  **/   
	public void registrarProveedorCauca(){
		Empresa empresaCauca = new Empresa();
		empresaCauca = (Empresa) EmpresaDAO.getInstancia().buscarEntidadPorClave(1);
		Proveedor proveedor = new Proveedor();
		proveedor.setEmpresa((Empresa) EmpresaDAO.getInstancia().buscarEntidadPorClave(empresa.getIdEmpresa()));
		proveedor.setNombre(empresaCauca.getNombre());
		proveedor.setDireccion(empresaCauca.getDireccion());
		proveedor.setRif(empresaCauca.getRif());
		proveedor.setTelefono(empresaCauca.getTelefono());
		proveedor.setCorreo(empresaCauca.getCorreo());
		proveedor.setCelular(empresaCauca.getCelular());
		ProveedorDAO.getInstancia().insertarOActualizar(proveedor);
	}
   
	 /**
    * Metodo para registra la encuesta para llevar el control de la visita en cada 15 dias
  **/ 
	public void registrarPrimerEncuesta(){
		Encuesta encuesta = new Encuesta();
		encuesta.setObservaciones("Primer Ingreso");
		encuesta.setFechaEncuesta(new Date());
		encuesta.setUsuario( (Usuario) UsuarioDAO.getInstancia().buscarEntidadPorClave(empleado.getUsuario().getIdUsuario()));
		encuesta.setVisita("S");
		EncuestaDAO.getInstancia().insertarOActualizar(encuesta);
	}
	
   /**
      * Metodo cancelar, lo que hace es limpiar los campos Registar en la vista login.xhtml
  **/  
	public void cancelar(){
		System.out.println("entramos a cancelar");
		this.nombre = "";
		this.clave = "";
		this.empresa = new Empresa();
		this.empleado = new Empleado();
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
	}
	

	public void registrarAdministrador(){
		String loginOClave="";
		for (int i = 0; i < empleado.getCedulaEmpleado().length() ; i++) {
			if(i>= 2){
				loginOClave = loginOClave+empleado.getCedulaEmpleado().charAt(i);
			}
		}
		usuario.setLogin(loginOClave);
		usuario.setPassword(loginOClave);
		usuario.setRol( (Rol) RolDAO.getInstancia().buscarEntidadPorClave(4));
		UsuarioDAO.getInstancia().insertarOActualizar(usuario);
		nombre = empleado.getCedulaEmpleado();
		primerIngreso = true;
		
		empleado.setUsuario(usuario);
					
	}
	
   /**
    * Este metodo, es para el wizard de la vista registrar en login.xhml, lo que hace es un contador para llevar el control de los botones
    siguiente  y atras.
  **/  
	public String flujoPrimerIngreso(FlowEvent event) {
            return event.getNewStep(); 
    }  

	public boolean estaLogeado() {
		return logueado;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getClave() {
		return clave; 
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public boolean isLogeado() {
		return logueado;
	}

	public void setLogeado(boolean logeado) {
		this.logueado = logeado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public boolean isGestionVehiculoVisible() {
		return gestionVehiculoVisible;
	}

	public void setGestionVehiculoVisible(boolean gestionVehiculoVisible) {
		this.gestionVehiculoVisible = gestionVehiculoVisible;
	}
	
	public ServiciosVentanaEncuesta getServicioEncuesta() {
		return servicioEncuesta;
	}

	public void setServicioEncuesta(ServiciosVentanaEncuesta servicioEncuesta) {
		this.servicioEncuesta = servicioEncuesta;
	}

	public String getUserEmpleado() {
		return userEmpleado;
	}

	public void setUserEmpleado(String userEmpleado) {
		this.userEmpleado = userEmpleado;
	}

	public String getClaveEmpleado() {
		return claveEmpleado;
	}

	public void setClaveEmpleado(String claveEmpleado) {
		this.claveEmpleado = claveEmpleado;
	}

	public String getClaveEmpelado2() {
		return claveEmpelado2;
	}

	public void setClaveEmpelado2(String claveEmpelado2) {
		this.claveEmpelado2 = claveEmpelado2;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
}

//
//<filter>
//<filter-name>PrimeFaces FileUpload Filter</filter-name>
//<filter-class>org.primefaces.webapp.filter.FileUploadFilter</filter-class>
//</filter>
//<filter-mapping>
//<filter-name>PrimeFaces FileUpload Filter</filter-name>
//<servlet-name>Faces Servlet</servlet-name>
//</filter-mapping>
	