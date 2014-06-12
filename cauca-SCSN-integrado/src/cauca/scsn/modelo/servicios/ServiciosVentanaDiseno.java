package cauca.scsn.modelo.servicios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.MarcaNeumaticoDAO;
import cauca.scsn.modelo.entidad.MarcaNeumatico;
import cauca.scsn.modelo.datamodel.DisenoDataModel;
import cauca.scsn.modelo.entidad.Diseno;
import cauca.scsn.modelo.dao.DisenoDAO;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@SessionScoped
public class ServiciosVentanaDiseno implements ServiciosMaestros {

	private DisenoDAO 			disenoDAO;
	private Diseno 				diseno			 	 =	new Diseno();
	private Diseno 				disenoSeleccionado 	 =	new Diseno();
	private DisenoDataModel 	disenoDataModel;
	private Empresa 			empresa 			 =  new Empresa();
	private boolean				modificar			 = 	false;
	private boolean				eliminar			 = 	false;
	private boolean 			consultar			 = 	false;
	private ValidadorBean		validador		     =  new ValidadorBean();
	private ControladorMensajes mensajes			 = 	new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;
	private Integer				idMarca;
	private StreamedContent		streamedContentImagen;
	//private StreamedContent		imagenTabla;
	private List<Diseno>		listaDisenosCompleta 		 =  new ArrayList<Diseno>();
	
	public ServiciosVentanaDiseno() {
		super();
		diseno			 	=	new Diseno();
		disenoSeleccionado 	=	new Diseno();
		disenoDAO 			= 	DisenoDAO.getInstancia();
		validador		    =   new ValidadorBean();
		colocarImagenDefault();
		empresaEnLaSesion();
		llenarDataModel();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		MarcaNeumatico marca = (MarcaNeumatico) MarcaNeumaticoDAO.getInstancia().buscarEntidadPorClave(this.idMarca);
		if((diseno.getNombre()!="") || (disenoSeleccionado.getNombre()!=null)){
			if((idMarca !=0) || (disenoSeleccionado.getMarcaNeumatico()!=null)){
				if((diseno.getImagen() !=null) || (disenoSeleccionado.getImagen()!=null)){
					try {
//						if(validador.validarLetrasGuionEspacios(diseno.getNombre(), "Formato del Nombre del Diseño de Neumatico INCORRECTO ejm: Taco, Direccional")
//								|| validador.validarLetrasGuionEspacios(disenoSeleccionado.getNombre(), "Formato del Nombre del Diseño de Neumatico INCORRECTO ejm: Taco, Direccional")){
//						
//							if (!modificar) {
//								disenoDAO.insertarOActualizar(this.diseno);
//								mensajes.informativo("Operación exitosa", "Diseno: "+ this.diseno.getNombre() +" ha sido guardado!");
//							
//							} else {				
//								disenoDAO.actualizar(this.disenoSeleccionado);
//								mensajes.informativo("Operación exitosa", "Diseno: "+ this.disenoSeleccionado.getNombre() +" ha sido guardado!");
//							}
//						}else{
//							throw new Exception();
//						}
						
						//-------
						if (!modificar) {
							this.diseno.setMarcaNeumatico(marca);
							disenoDAO.insertarOActualizar(this.diseno);
							mensajes.informativo("Operación exitosa", "Diseno: "+ this.diseno.getNombre() +" ha sido guardado!");
						} else {	
							this.disenoSeleccionado.setMarcaNeumatico(marca);			
							disenoDAO.actualizar(this.disenoSeleccionado);
							mensajes.informativo("Operación exitosa", "Diseno: "+ this.disenoSeleccionado.getNombre() +" ha sido guardado!");
						}
						//-------
						
						llenarDataModel();
					} catch (Exception e) {
						mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
					}
					cancelar(eventoCancelar);
//					empresaEnLaSesion();
				}else{
					mensajes.error("Error", "Campo requerido, cargar imagen del diseño del neumático");
				}
				
			}else{
				mensajes.error("Error", "Campo requerido, seleccionar una marca");
			}
			
		}else{
			mensajes.error("Error", "Campo requerido, nombre del diseño");
		}
		
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			disenoDAO.eliminarLogicamente(this.disenoSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Diseno: "+ this.disenoSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
//		empresaEnLaSesion();
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.diseno = null;
		this.diseno = new Diseno();
		this.disenoSeleccionado = null;
		this.disenoSeleccionado = new Diseno();
		setModificar(false);
		setEliminar(false);
		colocarImagenDefault();
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
//		empresaEnLaSesion();
	}

	@Override
	public void activarModificar() {
		if (disenoSeleccionado.getIdDiseno() != null){
			modificar = true;
			publicarImagen(disenoSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún diseno de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarConsultar() {
		if (disenoSeleccionado.getIdDiseno() != null){
			setConsultar(true);
			publicarImagen(disenoSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún diseno de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (disenoSeleccionado.getIdDiseno() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ disenoSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún diseno de la lista");
			cancelar(eventoCancelar);
		}
	}

	private List<MarcaNeumatico> listaMarcaNeumatico = new ArrayList<MarcaNeumatico>();
	private List<Diseno> listaDiseno = new ArrayList<Diseno>();
	@Override
	public void llenarDataModel() {
		listaDiseno.clear();
		listaDisenosCompleta = new ArrayList<Diseno>();
		listaMarcaNeumatico = MarcaNeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", empresa);
		listaDisenosCompleta = disenoDAO.getInstancia().buscarTodasEntidades();
		for (int i = 0; i < listaDisenosCompleta.size(); i++) {
			for (int j = 0; j < listaMarcaNeumatico.size(); j++) {
				if(listaMarcaNeumatico.get(j).getIdMarcaNeumatico() == listaDisenosCompleta.get(i).getMarcaNeumatico().getIdMarcaNeumatico()){
					listaDiseno.add(listaDisenosCompleta.get(i));
				}
			}
		}
//		setListaDisenos(disenoDAO.buscarTodasEntidades());
		setDisenoDataModel(new DisenoDataModel(this.listaDiseno));
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa"); 
//		this.diseno.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	
	/**
	 * @author José Leonardo Jerez Araujo jerez.leo13@gmail.com
	 * @param event
	 * Este método se encarga de manejar la acción de subida de archivos al servidor desde el portal para Diseños de Neumáticos
	 * A través del archivo subido se obtiene el contenido de este (arreglo de bytes), el cual se pasa al método
	 * publicarImagene para mostrarla en pantalla. Además, permite setear el objeto Diseno para luego guardarlo en la BD
	 */
	public void manejarUploadedFile(FileUploadEvent event) {
	    UploadedFile uploadedFile = (UploadedFile)event.getFile();
	    if(!modificar) {
		    diseno.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
			publicarImagen(diseno.getImagen()); //publicamos la imagen	
	    } else {
		    disenoSeleccionado.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
			publicarImagen(disenoSeleccionado.getImagen()); //publicamos la imagen
	    }
	    mensajes.informativo("Imagen Cargada", "La imagen "+uploadedFile.getFileName()+" ha sido cargada con éxito!");
	}
	
	/**
	 * @author José Leonardo Jerez Araujo jerez.leo13@gmail.com
	 * @param byteArrayImagen
	 * Este método permite setear el atributo que muestra la imagen en pantalla a partir de un arreglo de bytes
	 */
	public void publicarImagen(byte[] byteArrayImagen) {
		streamedContentImagen = new DefaultStreamedContent(new ByteArrayInputStream(byteArrayImagen));
	}
    
	/**
	 * @author José Leonardo Jerez Araujo
	 * Este método permite colocar una imagen por defecto en el formulario para el registro de un nuevo Diseño, de manera que si el usuario no cargó
	 * ninguna imagen esta la supla
	 */
	public void colocarImagenDefault() {
//		Path path = Paths.get("C:/Documents and Settings/user-pasante/Escritorio/proyecto-cauca/workspace/proyecto-cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
//		Path path = Paths.get("C:/Users/cauca/Desktop/SCSN-Coord-Informática/WorkSpace/proyecto-cauca-SCSN-integrar/WebContent/imagenes/mascaraDiseno.jpg");
		Path path = Paths.get("/var/lib/tomcat7/webapps/cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			publicarImagen(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
    /*
	public void manejarErroresDeSubidaDeImagenes(UploadedFile uploadedFile, FileUploadEvent event) {
		uploadedFile = event.getFile();
        if (JsfFileUploadUtils.isCorrectFileContentType(uploadedFile)) {
            if (JsfFileUploadUtils.isCorrectFileSize(uploadedFile)) {
                logoFile = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
                uploadedFileExtension = uploadedFile.getFileName().substring(uploadedFile.getFileName().indexOf("."));
            } else {
                JsfMessagesUtils.addFacesErrorMessage("app.error.fileUpload.badFileSize");
            }
        } else {
            JsfMessagesUtils.addFacesErrorMessage("app.error.fileUpload.badFileContentType");
        }
	}
	
	
	/*
	 * PARA MANEJAR LOS ERRORES!!!
	 * 


		uploadedFile = event.getFile();
        if (JsfFileUploadUtils.isCorrectFileContentType(uploadedFile)) {
            if (JsfFileUploadUtils.isCorrectFileSize(uploadedFile)) {
                logoFile = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
                uploadedFileExtension = uploadedFile.getFileName().substring(uploadedFile.getFileName().indexOf("."));
            } else {
                JsfMessagesUtils.addFacesErrorMessage("app.error.fileUpload.badFileSize");
            }
        } else {
            JsfMessagesUtils.addFacesErrorMessage("app.error.fileUpload.badFileContentType");
        }



	 * 
	 * 
	 * 
	 * 
	 */
	
	public Diseno getDiseno() {
		return diseno;
	}

	public void setDiseno(Diseno diseno) {
		this.diseno = diseno;
	}

	public List<Diseno> getListaDisenos() {
		return listaDisenosCompleta;
	}

	public void setListaDisenos(List<Diseno> listaDisenos) {
		this.listaDisenosCompleta = listaDisenos;
	}

	public Diseno getDisenoSeleccionado() {
		return disenoSeleccionado;
	}

	public void setDisenoSeleccionado(Diseno disenoSeleccionado) {
		this.disenoSeleccionado = disenoSeleccionado;
	}

	public DisenoDataModel getDisenoDataModel() {
		return disenoDataModel;
	}

	public void setDisenoDataModel(DisenoDataModel disenoDataModel) {
		this.disenoDataModel = disenoDataModel;
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

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public StreamedContent getStreamedContentImagen() {
		return streamedContentImagen;
	}

	public void setStreamedContentImagen(StreamedContent streamedContentImagen) {
		this.streamedContentImagen = streamedContentImagen;
	}

	/**
	 * @author José Leonardo Jerez Araujo jerez.leo13@gmail.com
	 * @return
	 * Este método nos permite mostrar en la tabla la imagen registrada del diseño
	 */
/*	public StreamedContent getImagenTabla() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else {
            String idDiseno = context.getExternalContext().getRequestParameterMap().get("idDiseno");
            Diseno disenoImagen = (Diseno) DisenoDAO.getInstancia().buscarEntidadPorClave(idDiseno);
            return new DefaultStreamedContent(new ByteArrayInputStream(disenoImagen.getImagen()));
        }
    }
	
	public void setImagenTabla(StreamedContent imagenTab) {
		imagenTabla = imagenTab;
	}
	
	
	/*
	 <p:column>
					        <p:graphicImage value="#{serviciosVentanaDiseno.imagenTabla}">
            					<f:param name="idDiseno" value="#{diseno.idDiseno}" />
					        </p:graphicImage>
					    </p:column>
	 */
	
	
	
	
	
	
}
