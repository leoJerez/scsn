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
import cauca.scsn.modelo.datamodel.TipoVehiculoDataModel;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.MarcaVehiculo;
import cauca.scsn.modelo.entidad.ModeloVehiculo;
import cauca.scsn.modelo.entidad.TipoVehiculo;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaTipoVehiculo implements ServiciosMaestros{

	private TipoVehiculoDAO 		tipoVehiculoDAO;
	private TipoVehiculo 			tipoVehiculo			 	=	new TipoVehiculo();
	private TipoVehiculo 			tipoVehiculoSeleccionada 	=	new TipoVehiculo();
	private TipoVehiculoDataModel 	tipoVehiculoDataModel;
	private Empresa 				empresa;
	private boolean					modificar			 		= 	false;
	private boolean					eliminar			 		= 	false;
	private boolean 				consultar			 		= 	false;
	private ValidadorBean			validador		     		= 	new ValidadorBean();
	private ControladorMensajes 	mensajes			 		= 	new ControladorMensajes();
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;
	private List<TipoVehiculo>		listaTipoVehiculo			=	new ArrayList<TipoVehiculo>();
	private int						rows;
	
	public ServiciosVentanaTipoVehiculo() {
		super();
		tipoVehiculo			 	=	new TipoVehiculo();
		tipoVehiculoSeleccionada 	=	new TipoVehiculo();
		tipoVehiculoDAO 			= 	TipoVehiculoDAO.getInstancia();
		validador		     		= 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		if(!tipoVehiculo.getNombre().equals("")){
			if(tipoVehiculo.getDentroCarretera() != ""){
				try {
					if(validador.validarLetrasNumerosPuntoAsteriscoGuionEspacios(tipoVehiculo.getNombre(), "Formato del Nombre del Tipo de Vehiculo INCORRECTO ejm: Deportivo, Furgoneta, Todo terreno")
							|| validador.validarLetrasNumerosPuntoAsteriscoGuionEspacios(tipoVehiculoSeleccionada.getNombre(), "Formato del Nombre del Tipo de Vehiculo INCORRECTO ejm: Deportivo, Furgoneta, Todo terreno")){
					
						if (!modificar) {
							tipoVehiculoDAO.insertarOActualizar(this.tipoVehiculo);
							mensajes.informativo("Operación exitosa", "TipoVehiculo: "+ this.tipoVehiculo.getNombre() +" ha sido guardada!");
							RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
						
						} else {
							tipoVehiculoDAO.actualizar(this.tipoVehiculoSeleccionada);
							mensajes.informativo("Operación exitosa", "TipoVehiculo: "+ this.tipoVehiculoSeleccionada.getNombre() +" ha sido guardada!");
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
			}else{
				mensajes.error("Error", "Campo requerido, seleccionar una opción dentro de carretera");
			}
		}else{
			mensajes.error("Error", "Campo requerido, Nombre del tipo de vehículo");
		}
		
		
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			tipoVehiculoDAO.eliminarLogicamente(this.tipoVehiculoSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "TipoVehiculo: "+ this.tipoVehiculoSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
		
	}

	@Override
	public void llenarDataModel() {
//		List<MarcaVehiculo> listaMarcaVehiculo = MarcaVehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", empresa);
//		List<ModeloVehiculo> listaModeloVehiculo = ModeloVehiculoDAO.getInstancia().buscarTodasEntidades();
//		List<ModeloVehiculo> listaModelo = new ArrayList<ModeloVehiculo>();
//		listaTipoVehiculo.clear();
//		List<TipoVehiculo> listaTipoVehiculoCompleta = TipoVehiculoDAO.getInstancia().buscarTodasEntidades();
//		for (int i = 0; i < listaModeloVehiculo.size(); i++) {
//			for (int j = 0; j < listaMarcaVehiculo.size(); j++) {
//				if(listaMarcaVehiculo.get(j).getIdMarcaVehiculo() == listaModeloVehiculo.get(i).getMarcaVehiculo().getIdMarcaVehiculo()){
//					listaModelo.add(listaModeloVehiculo.get(i));
//				}
//			}
//		}
//		System.out.println("lista modelo internar: "+listaModelo.size());
//		for (int h = 0; h < listaModelo.size(); h++) {
//			listaTipoVehiculo.add( (TipoVehiculo) TipoVehiculoDAO.getInstancia().buscarEntidadPorClave(listaModelo.get(h).getTipoVehiculo().getIdTipoVehiculo()));
////			for (int l = 0; l < listaTipoVehiculoCompleta.size(); l++) {
////				if(listaModelo.get(h).getTipoVehiculo().getIdTipoVehiculo() == listaTipoVehiculoCompleta.get(l).getIdTipoVehiculo()){
////					listaTipoVehiculo.add(listaTipoVehiculoCompleta.get(l));
////				}
////			}
//		}
		setListaTipoVehiculo(tipoVehiculoDAO.buscarTodasEntidades());
		setTipoVehiculoDataModel(new TipoVehiculoDataModel(this.listaTipoVehiculo));
		
		if(listaTipoVehiculo.size() > 0){
			rows = 5;
		}else{
			rows = 0;
		}
		
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
//		this.tipoVehiculo.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
		
	}
	
	public void cancelar(ActionEvent actionEvent) {
		this.tipoVehiculo = null;
		this.tipoVehiculo = new TipoVehiculo();
		this.tipoVehiculoSeleccionada = null;
		this.tipoVehiculoSeleccionada = new TipoVehiculo();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}
	
	public void activarModificar() {
		if (tipoVehiculoSeleccionada.getIdTipoVehiculo() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultar() {
		if (tipoVehiculoSeleccionada.getIdTipoVehiculo() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarEliminar() {
		if (tipoVehiculoSeleccionada.getIdTipoVehiculo() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ tipoVehiculoSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún tipoVehiculo de la lista");
			cancelar(eventoCancelar);
		}
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public TipoVehiculo getTipoVehiculoSeleccionada() {
		return tipoVehiculoSeleccionada;
	}

	public void setTipoVehiculoSeleccionada(TipoVehiculo tipoVehiculoSeleccionada) {
		this.tipoVehiculoSeleccionada = tipoVehiculoSeleccionada;
	}

	public TipoVehiculoDataModel getTipoVehiculoDataModel() {
		return tipoVehiculoDataModel;
	}

	public void setTipoVehiculoDataModel(TipoVehiculoDataModel tipoVehiculoDataModel) {
		this.tipoVehiculoDataModel = tipoVehiculoDataModel;
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

	public List<TipoVehiculo> getListaTipoVehiculo() {
		return listaTipoVehiculo;
	}

	public void setListaTipoVehiculo(List<TipoVehiculo> listaTipoVehiculo) {
		this.listaTipoVehiculo = listaTipoVehiculo;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
