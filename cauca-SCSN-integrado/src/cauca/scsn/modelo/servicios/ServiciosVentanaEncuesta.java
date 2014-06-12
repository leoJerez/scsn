package cauca.scsn.modelo.servicios;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.javax.persistence.osgi.OSGiProviderResolver;
import org.primefaces.context.RequestContext;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.EmpleadoCaucaDAO;
import cauca.scsn.modelo.dao.EmpleadoDAO;
import cauca.scsn.modelo.dao.EmpresaDAO;
import cauca.scsn.modelo.dao.seguridad.EncuestaDAO;
import cauca.scsn.modelo.dao.seguridad.RolDAO;
import cauca.scsn.modelo.dao.seguridad.UsuarioDAO;
import cauca.scsn.modelo.dao.seguridad.VisitaDAO;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.EmpleadoCauca;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.seguridad.Encuesta;
import cauca.scsn.modelo.entidad.seguridad.Log;
import cauca.scsn.modelo.entidad.seguridad.Rol;
import cauca.scsn.modelo.entidad.seguridad.Usuario;
import cauca.scsn.modelo.entidad.seguridad.Visita;

@ManagedBean
@ViewScoped
public class ServiciosVentanaEncuesta  implements Serializable{
	
	private List<Empleado> 			listaEmpleados = new ArrayList<Empleado>();
	private List<Encuesta> 			listaEncuesta = new ArrayList<Encuesta>(); 
	private ValidadorBean			validador = new ValidadorBean();
	private String 					fechaAntes;
	private String 					fechaActual;
	private List<EmpleadoCauca>		listaEmpleado = new ArrayList<EmpleadoCauca>();
	private ControladorMensajes 	mensajes = new ControladorMensajes();
	private Encuesta 				encuesta = new Encuesta();

	private Date			fechaAproximada;
	private int				horaAproximada;
	private boolean 		condicionSINO = true;
	private	int				minutoAproximado;
	private String 			horaAMPM;
	private String			observaciones;
	private String			cedulaEmpleado;
	private boolean 		logueado = false;
	private String			visita = "SI";
	private Date			fechaEncuesta = new Date();
	private String 			fechaVisita;
	private String			observacionesVisita;
	private String			loginOperador;
	private String			claveOperador;
	private	Process 		p; 
	private	ProcessBuilder 	pb;
	private String			guardarArchivo = "";
	private Usuario 		usuario = new Usuario(); 
	
	public ServiciosVentanaEncuesta() {
		fechaVisita = validador.formatearFechaEstiloCompleto(fechaEncuesta);
	}

	public void cargarListaUsuario(int idEmpresa){
		List<Empleado> listaEmpleadoCompleta = EmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", (Empresa) EmpresaDAO.getInstancia().buscarEntidadPorClave(idEmpresa));
		for (int i = 0; i < listaEmpleadoCompleta.size(); i++) {
			listaEmpleados.add(listaEmpleadoCompleta.get(i));
		}
//		List<Usuario> listaUsuarioCompleta = UsuarioDAO.getInstancia().buscarTodasEntidades();
//		for (int i = 0; i <listaUsuarioCompleta.size(); i++) {
//			//empleado = (Empleado) EmpleadoDAO.getInstancia().buscarEntidadPorPropiedad("usuario", listaUsuarioCompleta.get(i));
//			if(((Empleado) EmpleadoDAO.getInstancia().buscarEntidadPorPropiedad("usuario", listaUsuarioCompleta.get(i))).getEmpresa().getIdEmpresa() == idEmpresa){
//				listaUsuario.add(listaUsuarioCompleta.get(i));
//			}
//		}
	}
	 	
	 public void cargarListaEncuesta(){
		 List<Encuesta> listaEncuestaCompleta = EncuestaDAO.getInstancia().buscarTodasEntidades();	
		 for (int i = 0; i < listaEncuestaCompleta.size(); i++) {
			for (int j = 0; j < listaEmpleados.size(); j++) {
				if(listaEncuestaCompleta.get(i).getUsuario().getIdUsuario() == listaEmpleados.get(j).getUsuario().getIdUsuario()){
					listaEncuesta.add(listaEncuestaCompleta.get(i));
					
				}
			}
		}
		 
		Encuesta aux = new Encuesta();
		for (int k = 0; k < listaEncuesta.size(); k++) {
			for (int j = k+1; j < listaEncuesta.size(); j++) {
				if(validador.compararFechaUnoConFechaDos(listaEncuesta.get(j).getFechaEncuesta(), listaEncuesta.get(k).getFechaEncuesta()) == 1){
					aux = listaEncuesta.get(k);
					listaEncuesta.set(k, listaEncuesta.get(j));
					listaEncuesta.set(j, aux);
				}
			}
		}
	 }
	 
	 public boolean verificarUltimaEncuestaMayor15Dias(){
		    Calendar calendario = new GregorianCalendar();
		    calendario.setTime(listaEncuesta.get(0).getFechaEncuesta());
		    calendario.add(Calendar.DATE, 15);
		    SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy");
		    Date date = calendario.getTime();
			if(validador.validarFechaIngresadaMenorHoy(date, "")) {
				System.out.println("pasaron 15 dias");
				fechaAntes = time.format(listaEncuesta.get(0).getFechaEncuesta());
				fechaActual = validador.formatearFechaEstiloMedio(fechaEncuesta);
				listaEmpleado = EmpleadoCaucaDAO.getInstancia().buscarTodasEntidades();
						//EmpleadoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", (Empresa) EmpresaDAO.getInstancia().buscarEntidadPorClave(1));
				return true;
			}else{
				System.out.println("no han pasado 15 dias");
				return false;
			}
	 }
	 
	 public void botonRegistrarEncuesta(){
		RequestContext context = RequestContext.getCurrentInstance();
		logueado = true;
		if(condicionSINO == true){
			if(observaciones != ""){
				encuesta = new Encuesta();
				encuesta.setObservaciones(observaciones);
				encuesta.setFechaEncuesta(fechaEncuesta);
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				encuesta.setUsuario((Usuario)UsuarioDAO.getInstancia().buscarEntidadPorClave(((Empleado) session.getAttribute("empleado")).getUsuario().getIdUsuario()));
				encuesta.setVisita("N");
				EncuestaDAO.getInstancia().insertarOActualizar(encuesta);
				context.addCallbackParam("estaLogeado", logueado);
				if (logueado) {
					context.addCallbackParam("view", "contenido/principal.xhtml");
				}
			}else{
				mensajes.error("Error: Campo requerido", "Colocar las observaciones al vendedor");
			}
		}else{
			if(!cedulaEmpleado.equals(null) && !cedulaEmpleado.equals("")){
				if(fechaAproximada != null){
					if(horaAproximada != 0){
						if(minutoAproximado != 0){
							if(horaAMPM != ""){
								if(observaciones != ""){
									String horaA = Integer.toString(horaAproximada);
									String minutoA = Integer.toString(minutoAproximado);
									encuesta = new Encuesta();
									encuesta.setEmpleadoCauca((EmpleadoCauca) EmpleadoCaucaDAO.getInstancia().buscarEntidadPorClave(cedulaEmpleado));
									//encuesta.setEmpleado((Empleado) EmpleadoDAO.getInstancia().buscarEntidadPorClave(cedulaEmpleado));
									encuesta.setFechaVisita(validador.formatearFechaEstiloCompleto(fechaAproximada));
									encuesta.setHoraVisita(horaA+":"+minutoA+" "+horaAMPM);
									encuesta.setObservaciones(observaciones);
									encuesta.setFechaEncuesta(fechaEncuesta);
									HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
									encuesta.setUsuario((Usuario)UsuarioDAO.getInstancia().buscarEntidadPorClave(((Empleado) session.getAttribute("empleado")).getUsuario().getIdUsuario()));
									encuesta.setVisita("S");
									EncuestaDAO.getInstancia().insertarOActualizar(encuesta);
									
									context.addCallbackParam("estaLogeado", logueado);
									if (logueado) {
										context.addCallbackParam("view", "contenido/principal.xhtml");
									}
								}else{
									mensajes.error("Error: Campo requerido", "Colocar las observaciones al vendedor");
								}
							}else{
								mensajes.error("Error: Campo requerido", "Seleccione si es am/pm");
							}
						}else{
							mensajes.error("Error: Campo requerido", "Colocar minutos apriximado");
						}
					}else{
						mensajes.error("Error: Campo requerido", "colocar una hora aproximada");
					}
				}else{
					mensajes.error("Error: Campo requerido", " Colocar una fecha aproximada");
				}
			}else{
				mensajes.error("Error: Campo requerido", "Seleccione un vendedor");
			}
		}
	}
	
	
	public void loginOperadorCauca(ActionEvent event){
		usuario = (Usuario) UsuarioDAO.getInstancia().buscarEntidadPorPropiedad("login", loginOperador);
		if(usuario !=null && loginOperador != null && loginOperador.equals(usuario.getLogin()) && claveOperador != null && claveOperador.equals(usuario.getPassword())){
			if(usuario.getRol().getIdRol() == 1){
				System.out.println("id usuario: "+usuario.getIdUsuario());
				mensajes.informativo("Bienvenido", loginOperador);
				RequestContext.getCurrentInstance().addCallbackParam("tarea", "ventanaMantenimiento");
			}else{
				mensajes.error("Login Error","Credenciales no válidas");
			}	
			
		}else{
			mensajes.error("Login Error","Credenciales no válidas");
		}
	}
	
	public void guardarVisita(){
		if(observacionesVisita != ""){
			Visita visita = new Visita();
			visita.setFechaVisita(fechaVisita);
			visita.setObservaciones(observacionesVisita);
			visita.setUsuario((Usuario) UsuarioDAO.getInstancia().buscarEntidadPorClave(usuario.getIdUsuario()));
			VisitaDAO.getInstancia().insertarOActualizar(visita);
			mensajes.informativo("Mensaje", "Se guardo correctamente la observación de la visita");
		}else{
			mensajes.error("Error: Campo requerido", "Colocar la observación de la visita");
		}
	} 
	
	public void cancelar(ActionEvent actionEvent){
		this.loginOperador = "";
		this.claveOperador = "";
		this.observacionesVisita = "";
		this.guardarArchivo = "";
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
	}
	
	public void crearBackup(String host, String puerto, String usuario, String clave, String bDatos, String format, String path) {
		  try { /*
		          C:\Archivos de programa\PostgreSQL\9.2\bin\pg_dump.exe 
		          --host localhost --port 5432 --username "postgres" --role "postgres"  
		          --format custom --blobs --oids --inserts --column-inserts --no-privileges --no-tablespaces 
		          --use-set-session-authorization --disable-dollar-quoting --verbose --quote-all-identifiers 
		          --no-unlogged-table-data --file "path" "postgres" */
		   if(!format.equalsIgnoreCase("")) {
		    pb = new ProcessBuilder("C:/Archivos de programa/PostgreSQL/9.2/bin\\pg_dump.exe", "--verbose", "--format", format, "-f", path);
		   } else {
		    pb = new ProcessBuilder("C:/Archivos de programa/PostgreSQL/9.2/bin\\pg_dump.exe", "--verbose", "--inserts", "--column-inserts", "-f", path);
		   }
		         pb.environment().put("PGHOST", host);
		         pb.environment().put("PGPORT", puerto);
		         pb.environment().put("PGUSER", usuario);
		         pb.environment().put("PGPASSWORD", clave);
		         pb.environment().put("PGDATABASE", bDatos);
		         pb.redirectErrorStream(true);
		         p = pb.start();
		    
		         escribirProcess(p);
		         System.out.print("terminado backup "+path+"\n");
		         mensajes.informativo("Mensaje", "La data es guardada de manera correcta");
		     } catch (Exception e) {
		      System.out.print("backup \n"+e.getMessage()+"\n");
		     }
		 }
	
	 public void guardarRespaldo(){
		 if(guardarArchivo != ""){
			 System.out.println("nombre del archivo: "+guardarArchivo+".backup");
			 String path = "webapps/proyecto/respaldos/"+guardarArchivo+".backup";
			 crearBackup("localhost", "5432", "postgres", "postgres", "cauca", "Tar", path);
		 }else{
			 mensajes.error("Error: Campo requerido", "Colocar nombre a la bases de datos");
		 }
	 }
	 
	 static void escribirProcess(Process process) throws Exception{
	 	BufferedInputStream bufferIs = new BufferedInputStream(process.getInputStream());
      InputStreamReader isReader = new InputStreamReader( bufferIs );
      BufferedReader reader = new BufferedReader(isReader);
      String line = ""; 
      System.out.println("process: ---> "+line);
      while (true){
			line = reader.readLine();
	        if (line == null) break;
	        System.out.println(" ---->> "+line);
	    }  
	 }
	
	public String getFechaAntes() {
		return fechaAntes;
	}

	public void setFechaAntes(String fechaAntes) {
		this.fechaAntes = fechaAntes;
	}

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<EmpleadoCauca> getListaEmpleado() {
		return listaEmpleado;
	}

	public void setListaEmpleado(List<EmpleadoCauca> listaEmpleado) {
		this.listaEmpleado = listaEmpleado;
	}

	public Date getFechaAproximada() {
		return fechaAproximada;
	}

	public void setFechaAproximada(Date fechaAproximada) {
		this.fechaAproximada = fechaAproximada;
	}

	public int getHoraAproximada() {
		return horaAproximada;
	}

	public void setHoraAproximada(int horaAproximada) {
		this.horaAproximada = horaAproximada;
	}

	public boolean isCondicionSINO() {
		return condicionSINO;
	}

	public void setCondicionSINO(boolean condicionSINO) {
		this.condicionSINO = condicionSINO;
	}

	public int getMinutoAproximado() {
		return minutoAproximado;
	}

	public void setMinutoAproximado(int minutoAproximado) {
		this.minutoAproximado = minutoAproximado;
	}

	public String getHoraAMPM() {
		return horaAMPM;
	}

	public void setHoraAMPM(String horaAMPM) {
		this.horaAMPM = horaAMPM;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

//	public int getSeleccionarEmpleado() {
//		return cedulaEmpleado;
//	}
//
//	public void setSeleccionarEmpleado(int seleccionarEmpleado) {
//		this.cedulaEmpleado = seleccionarEmpleado;
//	}

	public String getVisita() {
		return visita;
	}

	public void setVisita(String visita) {
		this.visita = visita;
	}

	public String getCedulaEmpleado() {
		return cedulaEmpleado;
	}

	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
	}
	
	public String getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getObservacionesVisita() {
		return observacionesVisita;
	}

	public void setObservacionesVisita(String observacionesVisita) {
		this.observacionesVisita = observacionesVisita;
	}
	
	public String getLoginOperador() {
		return loginOperador;
	}

	public void setLoginOperador(String loginOperador) {
		this.loginOperador = loginOperador;
	}

	public String getClaveOperador() {
		return claveOperador;
	}

	public void setClaveOperador(String claveOperador) {
		this.claveOperador = claveOperador;
	}
	
	public String getGuardarArchivo() {
		return guardarArchivo;
	}

	public void setGuardarArchivo(String guardarArchivo) {
		this.guardarArchivo = guardarArchivo;
	}
}
