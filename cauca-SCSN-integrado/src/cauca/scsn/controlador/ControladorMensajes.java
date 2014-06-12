package cauca.scsn.controlador;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

@ManagedBean
@ApplicationScoped
public class ControladorMensajes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacesContext context;
	
	public void informativo(String titulo, String detalles) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalles));  
	}
	 
	public void error(String titulo, String detalles) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalles));  
	}
	
	public void advertencia(String titulo, String detalles) {
		context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, detalles));
	}
	
	public void fatal(String titulo, String detalles) {
		context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, detalles));
	}
	
	public FacesMessage validacion(String detalles) {
		FacesMessage mensaje = new FacesMessage(detalles);
		return mensaje;
	}
}


//
//<p:commandButton value="Limpiar" ajax="true" process="@this data" actionListener="#{serviciosVentanaEmpleado.refresh}" 
//	 style="margin-left:5px" update=":formNuevo:data" image="ui-icon-refresh"/>

