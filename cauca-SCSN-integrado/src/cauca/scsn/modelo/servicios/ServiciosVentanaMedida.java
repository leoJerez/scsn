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
import cauca.scsn.modelo.dao.DisenoDAO;
import cauca.scsn.modelo.dao.DisenoMedidaDAO;
import cauca.scsn.modelo.dao.MedidaDAO;
import cauca.scsn.modelo.datamodel.MedidaDataModel;
import cauca.scsn.modelo.entidad.Diseno;
import cauca.scsn.modelo.entidad.DisenoMedida;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.MarcaNeumatico;
import cauca.scsn.modelo.entidad.Medida;
import cauca.scsn.modelo.entidad.id.DisenoMedidaId;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaMedida implements ServiciosMaestros {

	private MedidaDAO 				medidaDAO;
	private Medida 					medida			 	=	new Medida();
	private Medida 					medidaSeleccionada 	=	new Medida();
	private MedidaDataModel 		medidaDataModel;
	private Empresa 				empresa;
	private boolean					modificar			= 	false;
	private boolean					eliminar			= 	false;
	private boolean 				consultar			= 	false;
	private ValidadorBean			validador		    = 	new ValidadorBean();
	private ControladorMensajes 	mensajes			= 	new ControladorMensajes();
	private List<Diseno>			listaDisenos		= 	new ArrayList<Diseno>();
	private DisenoDAO				disenoDAO;
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;
	private Integer					idMarca;
	private Integer					idDiseno;
	private List<Medida>			listaMedida			=	new ArrayList<Medida>();
	
	public ServiciosVentanaMedida() {
		super();
		medida			 	=	new Medida();
		medidaSeleccionada 	=	new Medida();
		medidaDAO 			= 	MedidaDAO.getInstancia();
		validador		    = 	new ValidadorBean();
		empresaEnLaSesion();
		llenarDataModel();
	}

	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		try {
			if((validador.validarLetrasNumerosGuionPuntoBarraDiagonalEspacios(medida.getNombre(), "Formato del Nombre de la Medida del Neumático INCORRECTO ejm: 195/65 R15 91H")
					&& validador.validarReal(medida.getPresionRecomendada().toString(), "Formato de la Presión Recomendada INCORRECTO ejm: 1.2")) 
					|| (validador.validarLetrasNumerosGuionPuntoBarraDiagonalEspacios(medidaSeleccionada.getNombre(), "Formato del Nombre de la Medida del Neumático INCORRECTO ejm: 195/65 R15 91H")
					&& validador.validarReal(medidaSeleccionada.getPresionRecomendada().toString(), "Formato de la Presión Recomendada INCORRECTO ejm: 1.2"))){
			
				if (!modificar) {
					medidaDAO.insertarOActualizar(this.medida);
					this.asignarDisenoMedida();
					mensajes.informativo("Operación exitosa", "Medida: "+ this.medida.getNombre() +" ha sido guardada!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
				
				} else {
					medidaDAO.actualizar(this.medidaSeleccionada);
					mensajes.informativo("Operación exitosa", "Medida: "+ this.medidaSeleccionada.getNombre() +" ha sido guardada!");
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
	
	//Metodo que filtra los diseños por marca
		public void disenosMarca(){
			//Inicializamos una variable del tipo MarcaNeumatico para luego asignarle un id y pasarlo como parametro de busqueda
			MarcaNeumatico marcaBuscar = new MarcaNeumatico();
				
			//Se le asigna el id de la marca
			marcaBuscar.setIdMarcaNeumatico(idMarca);
				
			//Cargamos la lista de diseños, filtrando por la marca previamente cargada
			this.setListaDisenos(disenoDAO.getInstancia().buscarEntidadesPorPropiedad("marcaNeumatico", marcaBuscar));
				
		}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		try {
			medidaDAO.eliminarLogicamente(this.medidaSeleccionada);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "Medida: "+ this.medidaSeleccionada.getNombre() +" ha sido eliminada!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	public void asignarDisenoMedida(){
		DisenoMedida disenoMedida = new DisenoMedida();
		DisenoMedidaId disenoMedidaId = new DisenoMedidaId();
		DisenoMedidaDAO disenoMedidaDAO = null;
		
		disenoMedidaId.setIdDiseno(idDiseno);
		disenoMedidaId.setIdMedida(medida.getIdMedida());
		disenoMedida.setId(disenoMedidaId);
		disenoMedidaDAO.getInstancia().insertarOActualizar(disenoMedida);
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.medida = null;
		this.medida = new Medida();
		this.medidaSeleccionada = null;
		this.medidaSeleccionada = new Medida();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
		empresaEnLaSesion();
	}

	@Override
	public void activarModificar() {
		if (medidaSeleccionada.getIdMedida() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarConsultar() {
		if (medidaSeleccionada.getIdMedida() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (medidaSeleccionada.getIdMedida() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ medidaSeleccionada.getNombre() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar alguna marca de neumático de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void llenarDataModel() {
		setListaMedida(medidaDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setMedidaDataModel(new MedidaDataModel(this.listaMedida));
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.medida.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}

	public Integer getIdDiseno() {
		return idDiseno;
	}

	public void setIdDiseno(Integer idDiseno) {
		this.idDiseno = idDiseno;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public Medida getMedidaSeleccionada() {
		return medidaSeleccionada;
	}

	public void setMedidaSeleccionada(Medida medidaSeleccionada) {
		this.medidaSeleccionada = medidaSeleccionada;
	}

	public MedidaDataModel getMedidaDataModel() {
		return medidaDataModel;
	}

	public void setMedidaDataModel(MedidaDataModel medidaDataModel) {
		this.medidaDataModel = medidaDataModel;
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

	public List<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(List<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public List<Diseno> getListaDisenos() {
		return listaDisenos;
	}

	public void setListaDisenos(List<Diseno> listaDisenos) {
		this.listaDisenos = listaDisenos;
	}

}
