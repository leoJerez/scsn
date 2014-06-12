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
import cauca.scsn.modelo.dao.ProveedorDAO;
import cauca.scsn.modelo.datamodel.DisenoDataModel;
import cauca.scsn.modelo.datamodel.ProveedorDataModel;
import cauca.scsn.modelo.entidad.Cargo;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.Proveedor;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaProveedor implements ServiciosMaestros{

	private Proveedor			proveedor				=	new Proveedor();
	private Proveedor			proveedorSeleccionado	=	new Proveedor();
	private ProveedorDAO		proveedorDAO;
	private ProveedorDataModel	proveedorDataModel;
	private Empresa 			empresa;
	private boolean				modificar			 	= 	false;
	private boolean				eliminar			 	= 	false;
	private boolean 			consultar			 	= 	false;
	private ValidadorBean		validador		     	= 	new ValidadorBean();
	private ControladorMensajes mensajes			 	= 	new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;
	private List<Proveedor>		listaProveedores	 	=  	new ArrayList<Proveedor>();
	
	public ServiciosVentanaProveedor() {
		super();
		proveedor				=	new Proveedor();
		proveedorSeleccionado	=	new Proveedor();
		proveedorDAO			=	ProveedorDAO.getInstancia();
		validador		        = 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}
	
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if(validador.validarProveedor(proveedor)
					|| validador.validarProveedor(proveedorSeleccionado)){
			
				if (!modificar) {
					proveedorDAO.insertarOActualizar(this.proveedor);
					mensajes.informativo("Operación exitosa", "Proveedor: "+ this.proveedor.getNombre() +" ha sido guardado!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				} else {
					proveedorDAO.actualizar(this.proveedorSeleccionado);
					mensajes.informativo("Operación exitosa", "Proveedor: "+ this.proveedorSeleccionado.getNombre() +" ha sido guardado!");
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

	public void eliminar(ActionEvent actionEvent) {
		try {
			proveedorDAO.eliminarLogicamente(this.proveedorSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Proveedor: "+ this.proveedorSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.proveedor = null;
		this.proveedor = new Proveedor();
		this.proveedorSeleccionado = null;
		this.proveedorSeleccionado = new Proveedor();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (proveedorSeleccionado.getIdProveedor() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
			System.out.println("Aceptar");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún proveedor de la lista");
			cancelar(eventoCancelar);
			System.out.println("Cancelar");
		}
	}
	
	public void activarConsultar() {
		if (proveedorSeleccionado.getIdProveedor() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún proveedor de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (proveedorSeleccionado.getIdProveedor() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ proveedorSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún proveedor de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void llenarDataModel() {
		setListaProveedores(proveedorDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setProveedorDataModel(new ProveedorDataModel(listaProveedores));
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.proveedor.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
		System.out.println("id empresa en proveedor: "+empresa.getIdEmpresa());
	}
	
	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Proveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public ProveedorDataModel getProveedorDataModel() {
		return proveedorDataModel;
	}

	public void setProveedorDataModel(ProveedorDataModel proveedorDataModel) {
		this.proveedorDataModel = proveedorDataModel;
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
}
