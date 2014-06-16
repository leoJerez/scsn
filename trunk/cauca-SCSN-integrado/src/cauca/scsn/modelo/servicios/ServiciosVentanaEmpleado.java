package cauca.scsn.modelo.servicios;

import java.util.Date;
import java.util.HashMap;

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
import cauca.scsn.modelo.dao.EmpleadoDAO;
import cauca.scsn.modelo.dao.seguridad.EncuestaDAO;
import cauca.scsn.modelo.dao.seguridad.RolDAO;
import cauca.scsn.modelo.dao.seguridad.UsuarioDAO;
import cauca.scsn.modelo.datamodel.EmpleadoDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Rol;
import cauca.scsn.modelo.entidad.seguridad.Usuario;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaEmpleado implements ServiciosMaestros{

	private EmpleadoDAO 		empleadoDAO;
	private Empleado 			empleado			 =	new Empleado();
	private Empleado 			empleadoSeleccionado =	new Empleado();
	private EmpleadoDataModel 	empleadoDataModel;
	private Empresa 			empresa;
	private boolean				modificar			= 	false;
	private boolean				eliminar			= 	false;
	private boolean 			consultar			= 	false;
	private ValidadorBean		validador		    =	new ValidadorBean();
	private ControladorMensajes mensajes			= 	new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;
	private Integer				idCargo;
	
	public ServiciosVentanaEmpleado() {
		super();
		empleado			 	=	new Empleado();
		empleadoSeleccionado 	=	new Empleado();
		empleadoDAO 			= 	EmpleadoDAO.getInstancia();
		validador		     	=   new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}
	
	boolean guardarConductor = false;
	
	public void activarGuardarConductor() { //Este metodo y el atributo guardarConductor posiblemente se eliminará cuando se agregue la parte de ROL
		guardarConductor = true;
		guardarOModificar(eventoCancelar);
	}
	
	public void guardarOModificar(ActionEvent actionEvent) {
		if((empleado.getCedulaEmpleado()!="") || (empleadoSeleccionado.getCedulaEmpleado()!=null) ){
			if((empleado.getNombre()!="") || (empleadoSeleccionado.getNombre()!=null)){
				if((empleado.getApellido()!="") || (empleadoSeleccionado.getApellido()!=null)){
					if((empleado.getDireccion()!="") || (empleadoSeleccionado.getDireccion()!=null)){
						if((empleado.getCelular()!="") || (empleadoSeleccionado.getCelular()!=null)){
							if((empleado.getCorreo()!="") || (empleadoSeleccionado.getCorreo()!=null)){
								if((idCargo !=0) || (empleadoSeleccionado.getCargo()!=null)){
									if(!guardarConductor) { //este condicional se va a eliminar cuando se modifique la base de datos, es decir, cuando se agregue la parte de ROL
										Cargo cargo = (Cargo) CargoDAO.getInstancia().buscarEntidadPorClave(this.idCargo);
										if(cargo.getIdCargo() == 6){
											this.empleado.setCargo(cargo);
										}else{
											this.empleado.setCargo(cargo);
											guardarUsuario();
											this.empleado.setUsuario((Usuario) UsuarioDAO.getInstancia().buscarEntidadPorClave(usuario.getIdUsuario()));
										}
									}
									try {
										if(validador.validarEmpleado(empleado) || validador.validarEmpleado(empleadoSeleccionado)){
										
											if (!modificar) {
											
												/*Se usa el metodo de la clase validador, que cambia las letras iniciales de los nombres y apellidos
												  simplemente le asignamos el nombre modificado al empleado*/
												empleado.setNombre(validador.cambiarMayuscula(empleado.getNombre()));
												empleado.setApellido(validador.cambiarMayuscula(empleado.getApellido()));
												
												/*Usamos el objeto empleadoDAO para llamar a su metodo insertarOActualizar que 
												  inserta un nuevo empleado o actualiza los datos de un empleado ingresado previamente*/
												empleadoDAO.insertarOActualizar(this.empleado);
												mensajes.informativo("Operación exitosa", "Empleado: "+ this.empleado.getNombre() +" ha sido guardado!");
												RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
											
											} else {
												empleadoDAO.actualizar(this.empleadoSeleccionado);
												mensajes.informativo("Operación exitosa", "Empleado: "+ this.empleadoSeleccionado.getNombre() +" ha sido guardado!");
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
									mensajes.error("Error", "Campo requerido, seleccionar un Cargo");
								}
							}else{
								mensajes.error("Error", "Campo requerido, colocar el E-mail");
							}
						}else{
							mensajes.error("Error", "Campo requerido, colocar el número de Cedular");
						}
					}else{
						mensajes.error("Error", "Campo requerido, colocar la Dirección");
					}
				}else{
					mensajes.error("Error", "Campo requerido, colocar el Apellido");
				}
			}else{
				mensajes.error("Error", "Campo requerido, colocar el Nombre");
			}
		}else{
			mensajes.error("Error", "Campo requerido, colocar el número de Cedula");
		}
		
	}
	
	private Usuario usuario = new Usuario();
	public void guardarUsuario(){
		usuario.setLogin(empleado.getCedulaEmpleado());
		usuario.setPassword(empleado.getCedulaEmpleado());
		usuario.setRol((Rol) RolDAO.getInstancia().buscarEntidadPorClave(empleado.getCargo().getIdCargo()));
		UsuarioDAO.getInstancia().insertarOActualizar(usuario);
		
		Encuesta encuesta = new Encuesta();
		encuesta.setObservaciones("Primer Ingreso");
		encuesta.setFechaEncuesta(new Date());
		encuesta.setUsuario( (Usuario) UsuarioDAO.getInstancia().buscarEntidadPorClave(usuario.getIdUsuario()));
		encuesta.setVisita("S");
		EncuestaDAO.getInstancia().insertarOActualizar(encuesta);
		mensajes.informativo("Mensaje", "Se a creado el usuario con el login y clave con la cedula del empleado");
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			empleadoDAO.eliminarLogicamente(this.empleadoSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Empleado: "+ this.empleadoSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.empleado = null;
		this.empleado = new Empleado();
		this.empleadoSeleccionado = null;
		this.empleadoSeleccionado = new Empleado();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (empleadoSeleccionado.getCedulaEmpleado() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún empleado de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultar() {
		if (empleadoSeleccionado.getCedulaEmpleado() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún empleado de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (empleadoSeleccionado.getCedulaEmpleado() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ empleadoSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún empleado de la lista");
			cancelar(eventoCancelar);
		}
	}
	private int rows;
	public void llenarDataModel() {
		//setEmpleadoDataModel(new EmpleadoDataModel(empleadoDAO.buscarTodasEntidades()));
		setEmpleadoDataModel(new EmpleadoDataModel(empleadoDAO.buscarEntidadesPorPropiedad("empresa", empresa)));
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.empleado.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
		System.out.println("id empresa en empleado: "+empresa.getIdEmpresa());
	}
	

	public EmpleadoDAO getEmpleadoDAO() {
		return empleadoDAO;
	}

	public void setEmpleadoDAO(EmpleadoDAO empleadoDAO) {
		this.empleadoDAO = empleadoDAO;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	public EmpleadoDataModel getEmpleadoDataModel() {
		return empleadoDataModel;
	}

	public void setEmpleadoDataModel(EmpleadoDataModel empleadoDataModel) {
		this.empleadoDataModel = empleadoDataModel;
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

	public String getMensajeEliminar() {
		return mensajeEliminar;
	}

	public void setMensajeEliminar(String mensajeEliminar) {
		this.mensajeEliminar = mensajeEliminar;
	}

	public boolean isConsultar() {
		return consultar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
