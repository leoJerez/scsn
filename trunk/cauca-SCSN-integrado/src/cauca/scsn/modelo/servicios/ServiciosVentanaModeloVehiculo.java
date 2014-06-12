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
import cauca.scsn.modelo.dao.MarcaVehiculoDAO;
import cauca.scsn.modelo.dao.ModeloVehiculoDAO;
import cauca.scsn.modelo.dao.TipoVehiculoDAO;
import cauca.scsn.modelo.datamodel.ModeloVehiculoDataModel;
import cauca.scsn.modelo.datamodel.VehiculoDataModel;
import cauca.scsn.modelo.entidad.MarcaVehiculo;
import cauca.scsn.modelo.entidad.ModeloVehiculo;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.Neumatico;
import cauca.scsn.modelo.entidad.TipoVehiculo;

@ManagedBean
@ViewScoped
public class ServiciosVentanaModeloVehiculo {

	private ModeloVehiculoDAO 			modeloVehiculoDAO;
	private ModeloVehiculo 				modeloVehiculo			 	=	new ModeloVehiculo();
	private ModeloVehiculo 				modeloVehiculoSeleccionado 	=	new ModeloVehiculo();
	private ModeloVehiculoDataModel 	modeloVehiculoDataModel;
	//private List<ModeloVehiculoDataModel>	modeloVehiculoDataModel = new ArrayList<ModeloVehiculoDataModel>();
	private Empresa 					empresa;
	private boolean						modificar			 		= 	false;
	private boolean						eliminar			 		= 	false;
	private boolean 					consultar			 		= 	false;
	private ValidadorBean				validador		     		= 	new ValidadorBean();
	private ControladorMensajes 		mensajes			 		= 	new ControladorMensajes();
	private ActionEvent 				eventoCancelar;
	private String						mensajeEliminar;
	private Integer						idMarcaVehiculo;
	private Integer 					idTipoVehiculo;
	private List<MarcaVehiculo> 		listaMarcaVehiculoCompleta 	= new ArrayList<MarcaVehiculo>();
	private List<ModeloVehiculo> 		listaModeloVehiculo 		= new ArrayList<ModeloVehiculo>();
	private List<ModeloVehiculo> 		listaModeloVehiculoCompleta = new ArrayList<ModeloVehiculo>();
	
	public ServiciosVentanaModeloVehiculo() {
		super();
		modeloVehiculo			 	=	new ModeloVehiculo();
		modeloVehiculoSeleccionado 	=	new ModeloVehiculo();
		modeloVehiculoDAO 			= 	ModeloVehiculoDAO.getInstancia();
		validador		     		= 	new ValidadorBean();
		
		empresaEnLaSesion();
		llenarDataModel();
	}

	public void guardarOModificar(ActionEvent actionEvent) {
		MarcaVehiculo marcaVehiculo = (MarcaVehiculo) MarcaVehiculoDAO.getInstancia().buscarEntidadPorClave(this.idMarcaVehiculo);
		this.modeloVehiculo.setMarcaVehiculo(marcaVehiculo);
		TipoVehiculo tipoVehiculo = (TipoVehiculo) TipoVehiculoDAO.getInstancia().buscarEntidadPorClave(this.idTipoVehiculo);
		this.modeloVehiculo.setTipoVehiculo(tipoVehiculo);
		
		if(!modeloVehiculo.getNombre().equals("")){
			if(idMarcaVehiculo !=0){
				if(idTipoVehiculo !=0){
					try {
						if(validador.validarLetrasNumerosGuionPuntoEspacios(modeloVehiculo.getNombre(),"Formato del Nombre del Modelo INCORRECTO ejm: Spark, Corsa")
								|| validador.validarLetrasNumerosGuionPuntoEspacios(modeloVehiculoSeleccionado.getNombre(),"Formato del Nombre del Modelo INCORRECTO ejm: Spark, Corsa")){
						
							if (!modificar) {
								modeloVehiculoDAO.insertarOActualizar(this.modeloVehiculo);
								mensajes.informativo("Operación exitosa", "ModeloVehiculo: "+ this.modeloVehiculo.getNombre() +" ha sido guardado!");
								RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
								
							} else {
								modeloVehiculoDAO.actualizar(this.modeloVehiculoSeleccionado);
								mensajes.informativo("Operación exitosa", "ModeloVehiculo: "+ this.modeloVehiculoSeleccionado.getNombre() +" ha sido guardado!");
								RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
							}
						}else{
							throw new Exception();
						}
						llenarDataModel();
					} catch (Exception e) {
//						RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
//						mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
						return;
					}
					cancelar(eventoCancelar);
					empresaEnLaSesion();
				}else{
					mensajes.error("Error", "Campo requerido seleccionar un tipo de vehículo");
				}
			}else{
				mensajes.error("Error", "Campo requerido seleccionar una marca");
			}
		}else{
			mensajes.error("Error", "Campo requerido Nombre de Vehiculo");
		}
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			modeloVehiculoDAO.eliminarLogicamente(this.modeloVehiculoSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "ModeloVehiculo: "+ this.modeloVehiculoSeleccionado.getNombre() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.modeloVehiculo = null;
		this.modeloVehiculo = new ModeloVehiculo();
		this.modeloVehiculoSeleccionado = null;
		this.modeloVehiculoSeleccionado = new ModeloVehiculo();
		this.idMarcaVehiculo = this.idTipoVehiculo = null;
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (modeloVehiculoSeleccionado.getIdModeloVehiculo() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún modeloVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultar() {
		if (modeloVehiculoSeleccionado.getIdModeloVehiculo() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún modeloVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (modeloVehiculoSeleccionado.getIdModeloVehiculo() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ modeloVehiculoSeleccionado.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún modeloVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void llenarDataModel() {
		listaMarcaVehiculoCompleta = MarcaVehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", empresa);
		listaModeloVehiculoCompleta = modeloVehiculoDAO.getInstancia().buscarTodasEntidades();
		for (int i = 0; i < listaMarcaVehiculoCompleta.size(); i++) {
			for (int j = 0; j < listaModeloVehiculoCompleta.size(); j++) {
				if(listaMarcaVehiculoCompleta.get(i).getIdMarcaVehiculo() == listaModeloVehiculoCompleta.get(j).getMarcaVehiculo().getIdMarcaVehiculo()){
					listaModeloVehiculo.add(listaModeloVehiculoCompleta.get(j));
				}
			}
		}
		
		setModeloVehiculoDataModel(new ModeloVehiculoDataModel(this.listaModeloVehiculo));
		//modeloVehiculoDataModel = ModeloVehiculoDAO.getInstancia().buscarTodasEntidades();
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
//		this.modeloVehiculo.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public ModeloVehiculo getModeloVehiculo() {
		return modeloVehiculo;
	}

	public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}

	public ModeloVehiculo getModeloVehiculoSeleccionado() {
		return modeloVehiculoSeleccionado;
	}

	public void setModeloVehiculoSeleccionado(
			ModeloVehiculo modeloVehiculoSeleccionado) {
		this.modeloVehiculoSeleccionado = modeloVehiculoSeleccionado;
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

	public Integer getIdMarcaVehiculo() {
		return idMarcaVehiculo;
	}

	public void setIdMarcaVehiculo(Integer idMarcaVehiculo) {
		this.idMarcaVehiculo = idMarcaVehiculo;
	}

	public Integer getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(Integer idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public ModeloVehiculoDataModel getModeloVehiculoDataModel() {
		return modeloVehiculoDataModel;
	}

	public void setModeloVehiculoDataModel(
			ModeloVehiculoDataModel modeloVehiculoDataModel) {
		this.modeloVehiculoDataModel = modeloVehiculoDataModel;
	}

}
