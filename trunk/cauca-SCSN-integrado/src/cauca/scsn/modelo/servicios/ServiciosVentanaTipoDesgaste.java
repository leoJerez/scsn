package cauca.scsn.modelo.servicios;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.swing.ImageIcon;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.TipoDesgasteDAO;
import cauca.scsn.modelo.datamodel.TipoDesgasteDataModel;
import cauca.scsn.modelo.entidad.Diseno;
import cauca.scsn.modelo.entidad.TipoDesgaste;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@SessionScoped
public class ServiciosVentanaTipoDesgaste implements ServiciosMaestros {

	private TipoDesgasteDAO 			tipoDesgasteDAO;
	private TipoDesgaste 				tipoDesgaste			 	 =	new TipoDesgaste();
	private TipoDesgaste 				tipoDesgasteSeleccionado 	 =	new TipoDesgaste();
	private TipoDesgasteDataModel 		tipoDesgasteDataModel;
	private Empresa 					empresa;
	private boolean						modificar			 		 = 	false;
	private boolean						eliminar			 		 = 	false;
	private boolean 					consultar			 		 = 	false;
	private ValidadorBean				validador		     		 = 	new ValidadorBean();
	private ControladorMensajes 		mensajes			 		 = 	new ControladorMensajes();
	private ActionEvent 				eventoCancelar;
	private String						mensajeEliminar;
	private DefaultStreamedContent		imagen;
	
	public ServiciosVentanaTipoDesgaste() {
		super();
		tipoDesgaste			 	=	new TipoDesgaste();
		tipoDesgasteSeleccionado 	=	new TipoDesgaste();
		tipoDesgasteDAO 			= 	TipoDesgasteDAO.getInstancia();
		validador		     		= 	new ValidadorBean();
//		empresaEnLaSesion();
		llenarDataModel();
//		colocarImagenDefault();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		//procesarImagen();
		if((tipoDesgaste.getNombre()!="") || tipoDesgasteSeleccionado.getNombre()!=null){
			if((tipoDesgaste.getCausa()!="")  || (tipoDesgasteSeleccionado.getCausa() != null)){
				if(tipoDesgaste.getAccionesCorrectivas() !="" || tipoDesgasteSeleccionado.getAccionesCorrectivas()!=null){
					if((tipoDesgaste.getImagen()!=null) || (tipoDesgasteSeleccionado.getImagen()!=null)){
						try { 
							if(validador.validarLetrasGuionPuntoEspacios(tipoDesgaste.getNombre(), "Formato del Tipo de Desgaste INCORRECTO ejm: Primordial")
									|| validador.validarLetrasGuionPuntoEspacios(tipoDesgasteSeleccionado.getNombre(), "Formato del Tipo de Desgaste INCORRECTO ejm: Primordial")){
							
								if (!modificar) {
									tipoDesgasteDAO.insertarOActualizar(this.tipoDesgaste);
									mensajes.informativo("Operación exitosa", "TipoDesgaste: "+ this.tipoDesgaste.getNombre() +" ha sido guardado!");
									RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
								
								} else {
									tipoDesgasteDAO.actualizar(this.tipoDesgasteSeleccionado);
									mensajes.informativo("Operación exitosa", "TipoDesgaste: "+ this.tipoDesgasteSeleccionado.getNombre() +" ha sido guardado!");
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
		//				empresaEnLaSesion();
					}else{
						mensajes.error("Error", "Campo requerido, cargar imagen del tipo de desgaste");
					}
					
				}else{
					mensajes.error("Error", "Campo requerido: Acciones Coorrectivas");
				}
			}else{
			mensajes.error("Error", "Campo requerido: Causas");
			}
		}else{
			mensajes.error("Error", "Campo requerido: Nombre");
		}	
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			tipoDesgasteDAO.eliminarLogicamente(this.tipoDesgasteSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "TipoDesgaste: "+ this.tipoDesgasteSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
//		empresaEnLaSesion();
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.tipoDesgaste = null;
		this.tipoDesgaste = new TipoDesgaste();
		this.tipoDesgasteSeleccionado = null;
		this.tipoDesgasteSeleccionado = new TipoDesgaste();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
//		empresaEnLaSesion();
//		colocarImagenDefault();
	}

	@Override
	public void activarModificar() {
		if (tipoDesgasteSeleccionado.getIdTipoDesgaste() != null){
			modificar = true;
			extraerImagenBD(tipoDesgasteSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoDesgaste de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarConsultar() {
		if (tipoDesgasteSeleccionado.getIdTipoDesgaste() != null){
			setConsultar(true);
			extraerImagenBD(tipoDesgasteSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
			RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
			extraerImagenBD(tipoDesgasteSeleccionado.getImagen());
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoDesgaste de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (tipoDesgasteSeleccionado.getIdTipoDesgaste() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ tipoDesgasteSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoDesgaste de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void llenarDataModel() {
		setTipoDesgasteDataModel(new TipoDesgasteDataModel(tipoDesgasteDAO.buscarTodasEntidades()));
	}

	@Override
	public void empresaEnLaSesion() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		empresa = (Empresa) session.getAttribute("empresa");
//		this.tipoDesgaste.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public void procesarImagen() {
		File archivo = new File("C:/Documents and Settings/user-pasante/Escritorio/proyecto-cauca/workspace/proyecto-cauca-SCSN/WebContent/imagenes/empleado.png"); //asociamos el archivo fisico
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(archivo); //lo abrimos. Lo importante es que sea un InputStream
			byte[] buffer = new byte[(int) archivo.length()]; //creamos el buffer
			int readers = inputStream.read(buffer);//leemos el archivo al buffer
			this.tipoDesgaste.setImagen(buffer);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			mensajes.error("Error", "No se consigue el archivo");
		} catch (IOException e) {
			e.printStackTrace();
			mensajes.error("Error", "No se puede leer el archivo");
		} 
	}
	
	public void colocarImagenDefault() {
//		Path path = Paths.get("C:/Documents and Settings/user-pasante/Escritorio/proyecto-cauca/workspace/proyecto-cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
//		Path path = Paths.get("C:/Users/cauca/Desktop/SCSN-Coord-Informática/WorkSpace/proyecto-cauca-SCSN-integrar/WebContent/imagenes/mascaraDiseno.jpg");
		Path path = Paths.get("/var/lib/tomcat7/webapps/cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			extraerImagenBD(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void manejarUploadedFile(FileUploadEvent event) {
	    UploadedFile uploadedFile = (UploadedFile)event.getFile();
	    if(!modificar) {
	    	tipoDesgaste.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
		    extraerImagenBD(tipoDesgaste.getImagen()); //publicamos la imagen	
	    } else {
	    	tipoDesgasteSeleccionado.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
		    extraerImagenBD(tipoDesgasteSeleccionado.getImagen()); //publicamos la imagen
	    }
	    mensajes.informativo("Imagen Cargada", "La imagen "+uploadedFile.getFileName()+" ha sido cargada con éxito!");
	}
	
	
	public void extraerImagenBD(byte[] imagenBD) {
		this.imagen = new DefaultStreamedContent(new ByteArrayInputStream(imagenBD));		
	}

	public TipoDesgaste getTipoDesgaste() {
		return tipoDesgaste;
	}

	public void setTipoDesgaste(TipoDesgaste tipoDesgaste) {
		this.tipoDesgaste = tipoDesgaste;
	}

	public TipoDesgaste getTipoDesgasteSeleccionado() {
		return tipoDesgasteSeleccionado;
	}

	public void setTipoDesgasteSeleccionado(TipoDesgaste tipoDesgasteSeleccionado) {
		this.tipoDesgasteSeleccionado = tipoDesgasteSeleccionado;
	}

	public TipoDesgasteDataModel getTipoDesgasteDataModel() {
		return tipoDesgasteDataModel;
	}

	public void setTipoDesgasteDataModel(TipoDesgasteDataModel tipoDesgasteDataModel) {
		this.tipoDesgasteDataModel = tipoDesgasteDataModel;
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

	public DefaultStreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(DefaultStreamedContent imagen) {
		this.imagen = imagen;
	}
}
