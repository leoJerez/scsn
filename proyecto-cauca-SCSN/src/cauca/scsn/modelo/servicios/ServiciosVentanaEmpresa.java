package cauca.scsn.modelo.servicios;

import java.util.ArrayList;
import java.util.List;








import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.primefaces.context.RequestContext;








import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.dao.CargoDAO;
import cauca.scsn.modelo.dao.EmpleadoDAO;
import cauca.scsn.modelo.dao.EmpresaDAO;
import cauca.scsn.modelo.dao.seguridad.UsuarioDAO;
import cauca.scsn.modelo.datamodel.EmpleadoDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.seguridad.Usuario;

@ManagedBean
@ViewScoped
public class ServiciosVentanaEmpresa {
	
	private Empresa empresa;
//	private Usuario usuario = new Usuario();
	private Empleado empleado = new Empleado();
	private boolean editarCampo = true;
	private boolean	btonEditar;
	private Empresa actualizarEmpresa = new Empresa();
	private ControladorMensajes 	mensajes = new ControladorMensajes();
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	
	public ServiciosVentanaEmpresa() {
		super();
		empresa = (Empresa) session.getAttribute("empresa");
		System.out.println("id de la empresa: "+empresa.getIdEmpresa());
		
		
	}

	public void buscarEmpresa(){
	
		
	}
	
   /**
    * Metodo para desbloquear el boton editar, campos y guardar, con boolean y en la vista disabled 
  **/  
	public void editarDatosEmpresa(){
		if(editarCampo = true){
			editarCampo = false;
			btonEditar = true;
		}
	}
	
   /**
    * Metodo para modificar los datos de la empresa en la vista empresa.xhtml
  **/  
	public void guardarDatosEmpresa(){
		if(editarCampo == false){
			editarCampo = true;
			btonEditar = false;
			EmpresaDAO.getInstancia().insertarOActualizar(empresa);
			session.setAttribute("empresa", empresa);
			mensajes.informativo("Mensaje", "Los Datos se modificaron correctamente");
		}
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isEditarCampo() {
		return editarCampo;
	}

	public void setEditarCampo(boolean editarCampo) {
		this.editarCampo = editarCampo;
	}

	public boolean isBtonEditar() {
		return btonEditar;
	}

	public void setBtonEditar(boolean btonEditar) {
		this.btonEditar = btonEditar;
	}

}
