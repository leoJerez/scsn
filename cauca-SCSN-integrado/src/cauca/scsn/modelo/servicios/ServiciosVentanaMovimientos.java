package cauca.scsn.modelo.servicios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.primefaces.component.dnd.Droppable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.DisenoDAO;
import cauca.scsn.modelo.dao.MovimientoDAO;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.NeumaticoVehiculoDAO;
import cauca.scsn.modelo.dao.RutaVehiculoDAO;
import cauca.scsn.modelo.dao.TipoDesgasteDAO;
import cauca.scsn.modelo.dao.VehiculoDAO;
import cauca.scsn.modelo.datamodel.NeumaticoDataModel;
import cauca.scsn.modelo.entidad.DatosNeumaticoRueda;
import cauca.scsn.modelo.entidad.Diseno;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.Movimiento;
import cauca.scsn.modelo.entidad.Neumatico;
import cauca.scsn.modelo.entidad.NeumaticoVehiculo;
import cauca.scsn.modelo.entidad.PosicionesVehiculo;
import cauca.scsn.modelo.entidad.RutaVehiculo;
import cauca.scsn.modelo.entidad.TipoDesgaste;
import cauca.scsn.modelo.entidad.Vehiculo;
import cauca.scsn.modelo.entidad.id.MovimientoId;
import cauca.scsn.modelo.entidad.id.NeumaticoVehiculoID;
import cauca.scsn.modelo.entidad.id.RutaVehiculoId;

@ManagedBean
@ViewScoped
public class ServiciosVentanaMovimientos {
	
	private ServiciosVentanaEsquemaEjes	serviciosVentanaEsquemaEjes;
	private ServiciosVentanaVehiculo	serviciosVentanaVehiculo;
	private ControladorMensajes			mensajes;
	private ServiciosVentanaNeumatico	serviciosVentanaNeumatico = new ServiciosVentanaNeumatico();
	private Movimiento					movimientoActual;
	private MovimientoId 				movimientoActualId;
	private Movimiento 					movimientoSeleccionado;
	private Movimiento 					movimientoPrevio = new Movimiento();
	private Vehiculo					vehiculoSeleccionado;
	private RutaVehiculo				rutaVehiculo;
	private Empresa						empresa;
	private Neumatico					neumaticoSeleccionado;
	private Neumatico					neumaticoSeleccionadoMontaje;
	private Neumatico					neumaticoSeleccionadoAlmacen = new Neumatico();
	private NeumaticoVehiculo 			neumaticoVehiculo = new NeumaticoVehiculo();
	private NeumaticoVehiculoID 		neumaticoVehiculoId = new NeumaticoVehiculoID();
	private NeumaticoDataModel			neumaticoDataModel;
	private NeumaticoDataModel			neumaticoDataModelListos;
	private ValidadorBean 				validador = new ValidadorBean();
	private DatosNeumaticoRueda 		datosRueda = new DatosNeumaticoRueda();
	private TipoDesgaste				tipoDesgasteSeleccionado = new TipoDesgaste();
	private int							idNeumatico;
	private int							cantidadMaximaNeumaticos = 1;
	private int							idVehiculo;
	private int							posicionActual;
	private StreamedContent 			streamedContentImagen;
	private StreamedContent 			streamedContentImagenConfirmar;
	private List<Neumatico> 			listaNeumaticos;
	private List<Neumatico>				listaAlmacenAux = new ArrayList<Neumatico>();
	private List<NeumaticoVehiculo>		listaNeumaticoVehiculoDeOperacionActual;
	private List<NeumaticoVehiculo>		listaNeumaticoVehiculoMontadosPrevio;
	private List<PosicionesVehiculo> 	listaPosicionesActivasVehiculo;
	private List<PosicionesVehiculo> 	listaPosicionesVehiculoPrevio;
	private List<Movimiento> 			listaMovimiento = new ArrayList<Movimiento>();
	private List<String>				listaPosicionesDisponibles = new ArrayList<String>();
	private List<Neumatico>				almacenListos = new ArrayList<Neumatico>();
	private List<Neumatico>				almacenReparacion = new ArrayList<Neumatico>();
	private List<Neumatico>				almacenReclamos = new ArrayList<Neumatico>();
	private List<Neumatico>				almacenReencauche = new ArrayList<Neumatico>();
	private List<Neumatico>				almacenDarBaja = new ArrayList<Neumatico>();
	private List<Neumatico>				almacenRotacion = new ArrayList<Neumatico>();
	private List<Neumatico>				listaNeumaticosMontados = new ArrayList<Neumatico>();
	private ActionEvent 				eventoCancelar;
	private boolean						desmontaje = false;
	private boolean						existeMontados = false;
	private boolean 					regresarDisabled = true;
	private boolean						disabledBtnMontar = true;
	private boolean						medicionSuperior = false;
	private boolean						medicionInferior = false;
	private boolean						medicionDerecha = false;
	private boolean						medicionIzquierda = false;
	private boolean						existeErrorRemIzq = false;
	private boolean						existeErrorRemDer = false;
	private boolean						existeErrorRemInf = false;
	private boolean						existeErrorRemSup = false;
	private boolean						collapsedSuperior;
	private boolean						collapsedInferior;
	private boolean						collapsedDerecho;
	private boolean						collapsedIzquierdo;
	private boolean						renderedConsejos = false;
	private boolean						dropped = false;
	private boolean 					disabledDatosMontaje = true;
	private boolean 					disabledBtnDevolver = true;
	private Date						fechaActual;
	private Date						fechaMontaje;
	private Date						fechaTerminar;
	private String						condicionConfirmar;
	private	String						tipoNeumaticoConfirmar;
	private String						nombreRuedaOculto;
	private String						observacionesMovimiento;
	private String						tipoNeumatico;
	private String						condicion;
	private String						nombreRuta;
	private String						dentroCarretera;
	private String						posicionSeleccionada = "";
	private String						almacenActivo = "";
	private String						nombreRueda = "";
	private String						mensajeConfirmacion = "";
	private String						operacionActual = "";
	private String 						ultimaRuedaMontada = "";
	private String						posicionInicial = "";
	private Double						kilometrajeMovimiento;
	private Double						presion;
	private Double						recorridoAcumulado = 0.0;
	
	private boolean[] 					ubicacionMedicionesArray = {true, true, true, true};
	private HashMap<String, DatosNeumaticoRueda> mapaRelacionalNeumaticosOperacion = new HashMap<String, DatosNeumaticoRueda>();
	
	private String			remanenteSuperiorA;
	private String			remanenteSuperiorB;
	private String			remanenteSuperiorC;
	private String			remanenteSuperiorD;
	
	private String			remanenteIzquierdoA;
	private String			remanenteIzquierdoB;
	private String			remanenteIzquierdoC;
	private String			remanenteIzquierdoD;

	private String			remanenteInferiorA;
	private String			remanenteInferiorB;
	private String			remanenteInferiorC;
	private String			remanenteInferiorD;

	private String			remanenteDerechaA;
	private String			remanenteDerechaB;
	private String			remanenteDerechaC;
	private String			remanenteDerechaD;
	
	private Double			totalSuperior;
	private Double			totalInferior;
	private Double			totalIzquierdo;
	private Double			totalDerecha;
	private Double			totalOperacion;

	private String			neumatico11Image;
	private String			neumatico12Image;
	private String			neumatico13Image;
	private String			neumatico14Image;
	private String			neumatico21Image;
	private String			neumatico22Image;
	private String			neumatico23Image;
	private String			neumatico24Image;
	private String			neumatico31Image;
	private String			neumatico32Image;
	private String			neumatico33Image;
	private String			neumatico34Image;
	private String			neumatico41Image;
	private String			neumatico42Image;
	private String			neumatico43Image;
	private String			neumatico44Image;
	private String			neumatico51Image;
	private String			neumatico52Image;
	private String			neumatico53Image;
	private String			neumatico54Image;
	private String			neumaticoRepIzqImage;
	private String			neumaticoRepDerImage;
	
	private String			eje1Image;
	private String			eje2Image;
	private String			eje3Image;
	private String			eje4Image;
	private String			eje5Image;
	
	private boolean			neumatico11DragDisabled;
	private boolean			neumatico12DragDisabled;
	private boolean			neumatico13DragDisabled;
	private boolean			neumatico14DragDisabled;
	private boolean			neumatico21DragDisabled;
	private boolean			neumatico22DragDisabled;
	private boolean			neumatico23DragDisabled;
	private boolean			neumatico24DragDisabled;
	private boolean			neumatico31DragDisabled;
	private boolean			neumatico32DragDisabled;
	private boolean			neumatico33DragDisabled;
	private boolean			neumatico34DragDisabled;
	private boolean			neumatico41DragDisabled;
	private boolean			neumatico42DragDisabled;
	private boolean			neumatico43DragDisabled;
	private boolean			neumatico44DragDisabled;
	private boolean			neumatico51DragDisabled;
	private boolean			neumatico52DragDisabled;
	private boolean			neumatico53DragDisabled;
	private boolean			neumatico54DragDisabled;
	private boolean			neumaticoR21DragDisabled;
	private boolean			neumaticoR22DragDisabled;
	
	private boolean			neumatico11Render;
	private boolean			neumatico12Render;
	private boolean			neumatico13Render;
	private boolean			neumatico14Render;
	private boolean			neumatico21Render;
	private boolean			neumatico22Render;
	private boolean			neumatico23Render;
	private boolean			neumatico24Render;
	private boolean			neumatico31Render;
	private boolean			neumatico32Render;
	private boolean			neumatico33Render;
	private boolean			neumatico34Render;
	private boolean			neumatico41Render;
	private boolean			neumatico42Render;
	private boolean			neumatico43Render;
	private boolean			neumatico44Render;
	private boolean			neumatico51Render;
	private boolean			neumatico52Render;
	private boolean			neumatico53Render;
	private boolean			neumatico54Render;
	private boolean			neumaticoRepIzqRender;
	private boolean			neumaticoRepDerRender;
	
	private boolean			neumatico1Activo;
	private boolean			neumatico2Activo;
	private boolean			neumatico3Activo;
	private boolean			neumatico4Activo;
	private boolean			neumatico5Activo;
	private boolean			neumatico6Activo;
	private boolean			neumatico7Activo;
	private boolean			neumatico8Activo;
	private boolean			neumatico9Activo;
	private boolean			neumatico10Activo;
	private boolean			neumatico11Activo;
	private boolean			neumatico12Activo;
	private boolean			neumatico13Activo;
	private boolean			neumatico14Activo;
	private boolean			neumatico15Activo;
	private boolean			neumatico16Activo;
	private boolean			neumatico17Activo;
	private boolean			neumatico18Activo;
	private boolean			neumatico19Activo;
	private boolean			neumatico20Activo;
	private boolean			neumatico21Activo;
	private boolean			neumatico22Activo;
	
	private boolean			neumatico1Previo;
	private boolean			neumatico2Previo;
	private boolean			neumatico3Previo;
	private boolean			neumatico4Previo;
	private boolean			neumatico5Previo;
	private boolean			neumatico6Previo;
	private boolean			neumatico7Previo;
	private boolean			neumatico8Previo;
	private boolean			neumatico9Previo;
	private boolean			neumatico10Previo;
	private boolean			neumatico11Previo;
	private boolean			neumatico12Previo;
	private boolean			neumatico13Previo;
	private boolean			neumatico14Previo;
	private boolean			neumatico15Previo;
	private boolean			neumatico16Previo;
	private boolean			neumatico17Previo;
	private boolean			neumatico18Previo;
	private boolean			neumatico19Previo;
	private boolean			neumatico20Previo;
	private boolean			neumatico21Previo;
	private boolean			neumatico22Previo;
	
	private String			primerNeumatico1 = "";
	private String			primerNeumatico2 = "";
	private String			primerNeumatico3 = "";
	private String			primerNeumatico4 = "";
	private String			primerNeumatico5 = "";
	private String			primerNeumatico6 = "";
	private String			primerNeumatico7 = "";
	private String			primerNeumatico8 = "";
	private String			primerNeumatico9 = "";
	private String			primerNeumatico10 = "";
	private String			primerNeumatico11 = "";
	private String			primerNeumatico12 = "";
	private String			primerNeumatico13 = "";
	private String			primerNeumatico14 = "";
	private String			primerNeumatico15 = "";
	private String			primerNeumatico16 = "";
	private String			primerNeumatico17 = "";
	private String			primerNeumatico18 = "";
	private String			primerNeumatico19 = "";
	private String			primerNeumatico20 = "";
	
	
	@PostConstruct
	public void init() {
//		super();
		movimientoSeleccionado = new Movimiento();
		serviciosVentanaEsquemaEjes = new ServiciosVentanaEsquemaEjes();
		serviciosVentanaVehiculo = new ServiciosVentanaVehiculo();
		mensajes = new ControladorMensajes();
		vehiculoSeleccionado = new Vehiculo();
		neumaticoSeleccionado = new Neumatico();
		neumaticoSeleccionadoMontaje = new Neumatico();
		listaPosicionesActivasVehiculo = new ArrayList<PosicionesVehiculo>();
		rutaVehiculo = new RutaVehiculo();
		streamedContentImagen = new DefaultStreamedContent();
		streamedContentImagenConfirmar = new DefaultStreamedContent();
		listaNeumaticoVehiculoDeOperacionActual = new ArrayList<NeumaticoVehiculo>();
		listaNeumaticoVehiculoMontadosPrevio = new ArrayList<NeumaticoVehiculo>();
		empresaEnLaSesion();
		movimientoActual = new Movimiento();
		movimientoActualId = new MovimientoId();
		cargarDisabled();
		cargarRender();
		cargarFondosNeumaticos();
		cargarFondosEjes();
		cargarNeumaticosActivo();
		cargarPrimerNeumatico();
		colocarImagenDefault();
		cargarRemanenteInicial();
		calcularPosicionesLibres();
		cargarCollapsed();
		filtrarNeumaticoDataModel(0);
		idVehiculo = 0;
		
		
		neumaticoSeleccionado.setCodInterno("PROBANDO AUX");
		listaAlmacenAux.add(neumaticoSeleccionado);
	}
	
	public void cargarCollapsed() {
		collapsedDerecho = collapsedInferior = collapsedIzquierdo = collapsedSuperior = true;
	}
	
	public void cargarRender() {
		neumatico11Render = neumatico12Render = neumatico13Render = neumatico14Render = neumatico21Render = neumatico22Render = neumatico23Render = neumatico24Render = 
				neumatico31Render = neumatico32Render = neumatico33Render = neumatico34Render = neumatico41Render = neumatico42Render = neumatico43Render = neumatico44Render = 
				neumatico51Render = neumatico52Render = neumatico53Render = neumatico54Render = neumaticoRepIzqRender = neumaticoRepDerRender = false;
	}

	public void cargarFondosNeumaticos() {
		neumatico11Image = neumatico12Image = neumatico13Image = neumatico14Image = neumatico21Image = neumatico22Image = neumatico23Image = neumatico24Image =
				neumatico31Image = neumatico32Image = neumatico33Image = neumatico34Image = neumatico41Image = neumatico42Image = neumatico43Image =  neumatico44Image =
				neumatico51Image =  neumatico52Image =  neumatico53Image =  neumatico54Image = neumaticoRepIzqImage = neumaticoRepDerImage = "../imagenes/ruedaTransparente.png"; 
	}
	
	public void cargarFondosEjes() {
		eje1Image = eje2Image = eje3Image = eje4Image = eje5Image = "../imagenes/ejeExtraOpaco.png";
	}
	
	public void cargarDisabled() {
		neumatico11DragDisabled = neumatico12DragDisabled = neumatico13DragDisabled = neumatico14DragDisabled = neumatico21DragDisabled = neumatico22DragDisabled = 
				neumatico23DragDisabled = neumatico24DragDisabled = neumatico31DragDisabled = neumatico32DragDisabled = neumatico33DragDisabled = neumatico34DragDisabled =
				neumatico41DragDisabled = neumatico42DragDisabled = neumatico43DragDisabled = neumatico44DragDisabled = neumatico51DragDisabled = neumatico52DragDisabled = 
				neumatico53DragDisabled = neumatico54DragDisabled = neumaticoR21DragDisabled = neumaticoR22DragDisabled = true;
	}
	
	public void cargarNeumaticosActivo() {
		neumatico1Activo = neumatico2Activo = neumatico3Activo = neumatico4Activo = neumatico5Activo = neumatico6Activo = neumatico7Activo = neumatico8Activo = neumatico9Activo = 
				neumatico10Activo = neumatico11Activo = neumatico12Activo = neumatico13Activo = neumatico14Activo = neumatico15Activo = neumatico16Activo = neumatico17Activo = neumatico18Activo = 
						neumatico19Activo = neumatico20Activo = neumatico21Activo = neumatico22Activo = false;
	}
	
	public void cargarPrimerNeumatico() {
		primerNeumatico1 = primerNeumatico2 = primerNeumatico3 = primerNeumatico4 = primerNeumatico5 = primerNeumatico6 = primerNeumatico7 = primerNeumatico8 = primerNeumatico9 = 
				primerNeumatico10 = primerNeumatico11 = primerNeumatico12 = primerNeumatico13 = primerNeumatico14 = primerNeumatico15 = primerNeumatico16 = primerNeumatico17 = primerNeumatico18 = 
						primerNeumatico19 = primerNeumatico20 = "";
	}
	
	public void cargarRemanenteInicial() {
		remanenteDerechaA = remanenteDerechaB = remanenteDerechaC = remanenteDerechaD = remanenteInferiorA = remanenteInferiorB = remanenteInferiorC = remanenteInferiorD = remanenteIzquierdoA = remanenteIzquierdoB =
				remanenteIzquierdoC = remanenteIzquierdoD = remanenteSuperiorA = remanenteSuperiorB = remanenteSuperiorC = remanenteSuperiorD = "0.0";
		totalDerecha = totalSuperior = totalInferior = totalIzquierdo = totalOperacion = 0.0;
	}
	
	
	public void filtrarNeumaticoDataModel(int idNeumaticoParametro) {
		List<Neumatico> listaNeumaticoAux = new ArrayList<Neumatico>();
		setListaNeumaticos(NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", this.empresa));
		for(int i=0; i<listaNeumaticos.size(); i++) {
			if((listaNeumaticos.get(i).getEstado().equals("L")) && (listaNeumaticos.get(i).getIdNeumatico() != idNeumaticoParametro)) {
				listaNeumaticoAux.add(listaNeumaticos.get(i));
			}
		}
		for(int j=0; j<listaMovimiento.size(); j++) {
			for(int h=0; h<listaNeumaticoAux.size(); h++) {
				if(listaNeumaticoAux.get(h).getIdNeumatico() == listaMovimiento.get(j).getNeumatico().getIdNeumatico()) {//listaNeumaticoVehiculoDeOperacionActual.get(j).getNeumatico().getIdNeumatico()) {
					listaNeumaticoAux.remove(h);
				}
			}
		}
		if(listaNeumaticoAux.size() == 0) {
			mensajes.informativo("ATENCIÓN!", "No existen neumáticos listos para montar");
		}
		setNeumaticoDataModelListos(new NeumaticoDataModel(listaNeumaticoAux));
	}
	
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		
	}
	
	public void limpiarDatosVehiculo() {
		idVehiculo = 0;
		vehiculoSeleccionado = new Vehiculo();
		cargarPrimerNeumatico();
		serviciosVentanaEsquemaEjes = new ServiciosVentanaEsquemaEjes();
		rutaVehiculo = new RutaVehiculo();
		cargarDisabled();
		cargarPrimerNeumatico();
	}
	
	public void limpiarDatosNeumatico() {
		idNeumatico = 0;
		neumaticoSeleccionado = new Neumatico();
//		neumaticoSeleccionado.setTipoNeumatico("P");
//		neumaticoSeleccionado.setCondicion("P");
		tipoNeumatico = "";
		condicion = "";
		setPresion(0.0);
		setObservacionesMovimiento("");
		//actualizarNeumaticoMontaje();
		disabledBtnMontar = true;
	}
	
	public void calcularPosicionesLibres() {
		listaPosicionesDisponibles.clear();
		Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
	    Map.Entry neumaticoVehiculoMapa;
		while (iterador.hasNext()) {
			neumaticoVehiculoMapa = (Map.Entry) iterador.next();
	        NeumaticoVehiculo neumaticoVehiculoAux = ((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo();
			if(!((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).isMontado()) {
				if(!listaPosicionesDisponibles.contains(neumaticoVehiculoAux.getPosicion())) {
					listaPosicionesDisponibles.add(neumaticoVehiculoAux.getPosicion());
				}
			}
		}
		listaPosicionesDisponibles = validador.ordenarListaNumerosStringAscendente(listaPosicionesDisponibles);
	}
	
	public void actualizarVehiculo(String cambiar) {
		if(!existeMontados) {
			cargarDisabled();
			cargarPrimerNeumatico();
			cargarNeumaticosActivo();
			cargarRemanenteInicial();
			limpiarDatosNeumatico();
			cargarFondosEjes();
			cargarFondosNeumaticos();
			rutaVehiculo = new RutaVehiculo();
			listaNeumaticoVehiculoDeOperacionActual.clear();
			listaNeumaticoVehiculoMontadosPrevio.clear();
			mapaRelacionalNeumaticosOperacion.clear();
			listaPosicionesDisponibles.clear();
			if(vehiculoSeleccionado.getIdVehiculo() != 0) {
				vehiculoSeleccionado = (Vehiculo) VehiculoDAO.getInstancia().buscarEntidadPorClave(vehiculoSeleccionado.getIdVehiculo());
				serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(vehiculoSeleccionado.getEsquemaEjes());
				serviciosVentanaEsquemaEjes.verificarDatosActivos();
				actualizarFondoEjes();
				if(!vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("S")){
					actualizarDisabledNeumaticosFC();
				} else {
					actualizarDisabledNeumaticos();
				}
				calcularPrimerNeumatico(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNombreEsquema());
				rutaVehiculo.setId(new RutaVehiculoId());
				rutaVehiculo = (RutaVehiculo) RutaVehiculoDAO.getInstancia().buscarEntidadPorPropiedad("vehiculo", vehiculoSeleccionado);
				listaNeumaticoVehiculoMontadosPrevio = NeumaticoVehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
				if(listaNeumaticoVehiculoMontadosPrevio.size() >= 1) {
					this.mostrarNeumaticoPreviamenteMontados();
				}
				calcularPosicionesLibres();
			} else {
				limpiarDatosVehiculo();
			}
		} else {
			if(cambiar.equals("SI")) {
				existeMontados = false;
				limpiarDatosNeumatico();
				listaNeumaticos.clear();
				listaNeumaticoVehiculoDeOperacionActual.clear();
				filtrarNeumaticoDataModel(0);
				desmontaje = false;
				kilometrajeMovimiento = 0.0;
				this.actualizarVehiculo("");
			} else if(cambiar.equals("NO")) {
				idVehiculo = vehiculoSeleccionado.getIdVehiculo();
			} else {
				RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarCambioVehiculo");
				RequestContext.getCurrentInstance().addCallbackParam("tarea", "V");
			}
		}
	}
	
	/**
	 * Este método se utliza para cargar los neumáticos que ya se han montado en el vehículo seleccionado, de tal forma que en la vista se muestren para
	 * facilidad del usuario
	 */
	public void mostrarNeumaticoPreviamenteMontados() {
		//listaMovimientosPrevio = new ArrayList<Movimiento>();
		
		for(int j=0; j<listaNeumaticoVehiculoMontadosPrevio.size(); j++) {
			Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		    Map.Entry neumaticoVehiculoMapa;
			while (iterador.hasNext()) {
				neumaticoVehiculoMapa = (Map.Entry) iterador.next();
		        NeumaticoVehiculo neumaticoVehiculoAux = ((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo();
				if(listaNeumaticoVehiculoMontadosPrevio.get(j).getPosicion().equals(neumaticoVehiculoAux.getPosicion())) {
					datosNeumaticoRueda = new DatosNeumaticoRueda(true, true, listaNeumaticoVehiculoMontadosPrevio.get(j));
					mapaRelacionalNeumaticosOperacion.put((String) neumaticoVehiculoMapa.getKey(), datosNeumaticoRueda);
					actualizarFondosNeumaticos((String) neumaticoVehiculoMapa.getKey());
				}
			}
		}
	}
	
	
	DatosNeumaticoRueda datosNeumaticoRueda = new DatosNeumaticoRueda(); 
	
	public void abrirVentanaAlmacen(String nombreAlmacen) {
		almacenActivo = nombreAlmacen;
		switch (nombreAlmacen) {
			case "L":
				serviciosVentanaNeumatico.cambiarFormato(almacenListos);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenListos));
				break;
			case "R":
				serviciosVentanaNeumatico.cambiarFormato(almacenReencauche);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReencauche));
				break;
			case "P":
				serviciosVentanaNeumatico.cambiarFormato(almacenReparacion);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReparacion));
				break;
			case "C":
				serviciosVentanaNeumatico.cambiarFormato(almacenReclamos);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReclamos));
				break;
			case "B":
				serviciosVentanaNeumatico.cambiarFormato(almacenDarBaja);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenDarBaja));
				break;
			case "T":
				serviciosVentanaNeumatico.cambiarFormato(almacenRotacion);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenRotacion));

				Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
			    Map.Entry neumaticoVehiculoMapa;
				while (iterador.hasNext()) {
					neumaticoVehiculoMapa = (Map.Entry) iterador.next();
			        NeumaticoVehiculo neumaticoVehiculoAux = ((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo();
					if(neumaticoVehiculoAux.getNeumatico() != null) {
						listaNeumaticosMontados.add(neumaticoVehiculoAux.getNeumatico());
					}
				}
				break;
		}
	}
	
	public void habilitarBotonRegresar(SelectEvent filaSeleccionada){
		regresarDisabled = false;
	}
	
	public void inhabilitarBotonRegresar(UnselectEvent filaSeleccionada){
		regresarDisabled = true;
	}
	
	public void habilitarSeleccionNeumaticoAlmacen(ActionEvent actionEvent){
		if(neumaticoSeleccionadoAlmacen.getIdNeumatico() != null) {
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		}
	}
	
	public void cancelar() {
//		if(mensajeConfirmacion.equals("Está seguro que desea MONTAR el neumático "+neumaticoSeleccionadoMontaje.getCodInterno()+" en la posición "
//				+posicionSeleccionada+" del vehículo con placa "+vehiculoSeleccionado.getPlaca())) {
//			cancelarMontaje = true;
//			actualizarPosicionMontaje();	
//		}
		listaNeumaticosMontados.clear();
		almacenRotacion.clear();
		setNeumaticoDataModel(new NeumaticoDataModel(listaNeumaticosMontados));
		setNeumaticoDataModel(new NeumaticoDataModel(almacenRotacion));
		cargarRemanenteInicial();
		cargarCollapsed();
		this.recorridoAcumulado = 0.0;
		this.tipoDesgasteSeleccionado = new TipoDesgaste();
		this.presion = 0.0;
		this.observacionesMovimiento = "";
		this.posicionSeleccionada = "";
		this.condicion = "";
		this.tipoNeumatico = "";
		posicionInicial = "";
		renderedConsejos = false;
		nombreRueda = "";
		ultimaRuedaMontada = "";
		disabledBtnMontar = true;
		disabledDatosMontaje = true;
		if(dropped) {
			regresarNeumatico(neumaticoSeleccionado);
		}
		neumaticoSeleccionado = new Neumatico();
		neumaticoSeleccionadoAlmacen = new Neumatico();
		neumaticoSeleccionadoMontaje = new Neumatico();
		regresarDisabled = true;
		operacionActual = "";
		mensajeConfirmacion = "";
		dropped = false;
		habilitarBotonMontaje(null);
		mostrarNeumaticoPreviamenteMontados();
	}
	

    DatosNeumaticoRueda datosRuedaInterno = new DatosNeumaticoRueda();
	
	public void cambiarEstadoMontajeNeumatico(Neumatico neumatico, String operacion) {
		Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
	    Map.Entry neumaticoVehiculoMapa;
	    while (iterador.hasNext()) {
			neumaticoVehiculoMapa = (Map.Entry) iterador.next();
			Neumatico neumaticoMapa = ((DatosNeumaticoRueda)neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo().getNeumatico();
			if(neumaticoMapa != null) {
				if(neumaticoMapa.getIdNeumatico() == neumatico.getIdNeumatico()) {
					if(operacion.equals("M")) {
						datosRuedaInterno = new DatosNeumaticoRueda(true, true, ((DatosNeumaticoRueda)neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo());
					} else if(operacion.equals("D")) {
						datosRuedaInterno = new DatosNeumaticoRueda(false, true, ((DatosNeumaticoRueda)neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo());
					}
					mapaRelacionalNeumaticosOperacion.put((String)neumaticoVehiculoMapa.getKey(), datosRuedaInterno);
				}
			}
		}
	}
	
	public void previoRegresarNeumatico() {
		regresarNeumatico(neumaticoSeleccionadoAlmacen);
	}
	
	public void regresarNeumatico(Neumatico neumaticoSacar) {
		switch (almacenActivo) {
				case "L":
					almacenListos.remove(neumaticoSacar);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenListos));
					break;
				case "R":
					almacenReencauche.remove(neumaticoSacar);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenReencauche));
					break;
				case "P":
					almacenReparacion.remove(neumaticoSacar);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenReparacion));
					break;
				case "C":
					almacenReclamos.remove(neumaticoSacar);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenReclamos));
					break;
				case "B":
					almacenDarBaja.remove(neumaticoSacar);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenDarBaja));
					break;
			}
		mensajes.informativo("Éxito", "Neumático removido con éxito");
		cambiarEstadoMontajeNeumatico(neumaticoSacar, "M");
		calcularPosicionesLibres();
		actualizarFondosNeumaticos(obtenerNombreRuedaDelMapa(neumaticoSacar));
		dropped = false;
		cancelar();
	}

	public String obtenerNombreRuedaDelMapa(Neumatico neumatico) {
		Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		Map.Entry objetoMapa;
		while(iterador.hasNext()) {
			objetoMapa = (Map.Entry) iterador.next();
			if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico() != null) {
				if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico().getIdNeumatico() == neumatico.getIdNeumatico()) {
					return (String)objetoMapa.getKey();
				}
			}
		}
		return "";
	}
		

	
	/**
	 * 
	 * @param event, del tipo DragDropEvent, utilizado para capturar el evento generado al soltar un componente
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com
	 * Este método se enacarga de capturar el neumático que ha sido movido a un almacén o una ubicación específica
	 * haciendo uso del evento que se genera del lado del cliente
	 */
	public void onDrop(DragDropEvent event) {
	    String draggedComponet = (String) event.getDragId();
	    Droppable componente = (Droppable)event.getComponent();
	    if(almacenActivo.equals("T")) {
		    nombreRueda = obtenerNombreRueda(draggedComponet, 25);
	    } else {
	    	nombreRueda = obtenerNombreRueda(draggedComponet, 25);
	    	almacenActivo = componente.getId();
	    }
	    neumaticoSeleccionado = ((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(nombreRueda)).getNeumaticoVehiculo().getNeumatico();
	    cambiarEstadoMontajeNeumatico(neumaticoSeleccionado, "D");
	    actualizarFondosNeumaticos(nombreRueda);
		calcularPosicionesLibres();
		dropped = true;
	    switch (almacenActivo) {
			case "L":
				almacenListos.add(neumaticoSeleccionado);
				listaNeumaticos.add(neumaticoSeleccionado);
				operacionActual = "D";
				break;
			case "R":
				almacenReencauche.add(neumaticoSeleccionado);
				operacionActual = "D";
				break;
			case "P":
				almacenReparacion.add(neumaticoSeleccionado);
				operacionActual = "D";
				break;
			case "C":
				almacenReclamos.add(neumaticoSeleccionado);
				operacionActual = "D";
				break;
			case "B":
				almacenDarBaja.add(neumaticoSeleccionado);
				operacionActual = "D";
				break;
			case "T":
				almacenRotacion.add(neumaticoSeleccionado);
				serviciosVentanaNeumatico.cambiarFormato(almacenRotacion);
				setNeumaticoDataModel(new NeumaticoDataModel(almacenRotacion));
				operacionActual = "R";
				break;
		}
    }
	
	public boolean pasarParaRotar(String rueda) {
    	nombreRueda = obtenerNombreRueda(rueda, 3);
	    neumaticoSeleccionado = ((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(nombreRueda)).getNeumaticoVehiculo().getNeumatico();
	    cambiarEstadoMontajeNeumatico(neumaticoSeleccionado, "D");
	    actualizarFondosNeumaticos(nombreRueda);
		calcularPosicionesLibres();

		almacenRotacion.add(neumaticoSeleccionado);
		serviciosVentanaNeumatico.cambiarFormato(almacenRotacion);
		setNeumaticoDataModel(new NeumaticoDataModel(almacenRotacion));
		operacionActual = "R";
		return false;
	}
	
	public void sacarDeListaRotacion() {
		almacenRotacion.remove(neumaticoSeleccionadoAlmacen);
		serviciosVentanaNeumatico.cambiarFormato(almacenRotacion);
		setNeumaticoDataModel(new NeumaticoDataModel(almacenRotacion));
	    cambiarEstadoMontajeNeumatico(neumaticoSeleccionadoAlmacen, "M");
		Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		Map.Entry objetoMapa;
		while(iterador.hasNext()) {
			objetoMapa = (Map.Entry) iterador.next();
			System.out.println("while");
			if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico() != null) {
				System.out.println("no estamos en blanco");
				if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico().getIdNeumatico() == neumaticoSeleccionadoAlmacen.getIdNeumatico()) {
//					posicionInicial = ((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getPosicion();
					System.out.println("entramos a cambiar el fondo porque lo conseguimos");
					System.out.println("clave que encontramos: "+objetoMapa.getKey());
					actualizarFondosNeumaticos((String)objetoMapa.getKey());
				}
			}
		}	
		deseleccionarNeumaticoRotar(null);
	}
	
	private boolean disabledBtnFinalizarRotacion = true;
	
	public boolean isDisabledBtnFinalizarRotacion() {
		return disabledBtnFinalizarRotacion;
	}

	public void setDisabledBtnFinalizarRotacion(boolean disabledBtnFinalizarRotacion) {
		this.disabledBtnFinalizarRotacion = disabledBtnFinalizarRotacion;
	}

	public void rotarPrevio() {
		disabledBtnFinalizarRotacion = false;
//		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidden1");
//		
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("name", event.getNewValue())
		
//		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//		String index = externalContext.getRequestParameterMap().get("listoMontar");
//		System.out.println("ANTES DE HACER LA TRAMPA");
//		FacesContext.getCurrentInstance().getViewRoot().findComponent("formRotacion:btnRotar").setRendered(false);
//		UIComponent.findComponent("listoMontar").setRendered(true);
		
	}
	
	public String obtenerNombreRueda(String rueda, int posicionInicioNombreReal) {
		String nombreRueda="";
		for (int i = 0; i < rueda.length() ; i++) {
			if(i>= posicionInicioNombreReal){
				nombreRueda = nombreRueda+rueda.charAt(i);
			}
		}
		return nombreRueda;
	}
	
	/**
	 * Este método se encarga de buscar las acciones correctivas correspondientes al tipo de desgaste seleccionado en el combo respectivo
	 */
	public void buscarConsejoDesgaste() {
		if(tipoDesgasteSeleccionado.getIdTipoDesgaste() != null && tipoDesgasteSeleccionado.getIdTipoDesgaste() != 0) {
			tipoDesgasteSeleccionado = (TipoDesgaste) TipoDesgasteDAO.getInstancia().buscarEntidadPorClave(tipoDesgasteSeleccionado.getIdTipoDesgaste());
			System.out.println("acciones encontradas: "+tipoDesgasteSeleccionado.getAccionesCorrectivas());
			mensajes.informativo("Acciones Correctivas", tipoDesgasteSeleccionado.getAccionesCorrectivas());
			renderedConsejos = true;
		} else {
			renderedConsejos = false;
		}
	}
	
	public void confirmarDesmontajes() {
		if(!existeErrorRemIzq && !existeErrorRemDer && !existeErrorRemInf && !existeErrorRemSup) {
			if(totalOperacion !=0.0 && presion != 0.0 && tipoDesgasteSeleccionado.getIdTipoDesgaste() != null) {
				if(operacionActual.equals("D")) {
					tipoDesgasteSeleccionado = ((TipoDesgaste)TipoDesgasteDAO.getInstancia().buscarEntidadPorClave(tipoDesgasteSeleccionado.getIdTipoDesgaste()));
					mensajeConfirmacion = "¿Está seguro que desea DESMONTAR el neumático?";
				} else if(operacionActual.equals("R")) {
					mensajeConfirmacion = "¿Está seguro que desea ROTAR los neumáticos seleccionados?";
				}
				RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");	
			} else {
				mensajes.error("ATENCIÓN", "Debe suministrar información completa!");
			}
		} else {
			mensajes.error("ERROR", "No se puede continuar mientras existan errores en los remanentes ingresados");
		}
	}
	
	public void actualizarNeumaticoRotacion(SelectEvent event) {
		if(neumaticoSeleccionadoAlmacen.getIdNeumatico() != null && neumaticoSeleccionadoAlmacen.getIdNeumatico() != 0) {
			Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
			Map.Entry objetoMapa;
			while(iterador.hasNext()) {
				objetoMapa = (Map.Entry) iterador.next();
				if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico() != null) {
					if(((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getNeumatico().getIdNeumatico() == neumaticoSeleccionadoAlmacen.getIdNeumatico()) {
						posicionInicial = ((DatosNeumaticoRueda)objetoMapa.getValue()).getNeumaticoVehiculo().getPosicion();
					}
				}
			}
			disabledDatosMontaje = false;
			disabledBtnDevolver = false;
		}
	}
	
	public void deseleccionarNeumaticoRotar(UnselectEvent evento) {
		posicionInicial = "";
		posicionSeleccionada = "0";
		disabledDatosMontaje = true;
		observacionesMovimiento = "";
		presion = 0.0;
		disabledBtnDevolver = true;
	}
	
	public void calcularTotalSuperior(AjaxBehaviorEvent event) {
		if(validador.validarReal(remanenteSuperiorA, "El remanente 'superior a' debe contener valores numéricos") && 
				validador.validarReal(remanenteSuperiorB, "El remanente 'superior b' debe contener valores numéricos") &&
				validador.validarReal(remanenteSuperiorC, "El remanente 'superior c' debe contener valores numéricos") &&
				validador.validarReal(remanenteSuperiorD, "El remanente 'superior d' debe contener valores numéricos")) {
			totalSuperior = (Double.parseDouble(remanenteSuperiorA)+Double.parseDouble(remanenteSuperiorB)+Double.parseDouble(remanenteSuperiorC)+Double.parseDouble(remanenteSuperiorD))/4;
			calcularTotalMovimiento(event);
			existeErrorRemSup = false;
		} else {
			existeErrorRemSup = true;
			new ValidatorException(new ControladorMensajes().validacion("Remanente superior tiene un error en los datos ingresados"));
		}
	}
	
	public void calcularTotalInferior(AjaxBehaviorEvent event) {
		if(validador.validarReal(remanenteInferiorA, "El remanente 'inferior a' debe contener valores numéricos") && 
				validador.validarReal(remanenteInferiorB, "El remanente 'inferior b' debe contener valores numéricos") &&
				validador.validarReal(remanenteInferiorC, "El remanente 'inferior c' debe contener valores numéricos") &&
				validador.validarReal(remanenteInferiorD, "El remanente 'inferior d' debe contener valores numéricos")) {
			totalInferior = (Double.parseDouble(remanenteInferiorA)+Double.parseDouble(remanenteInferiorB)+Double.parseDouble(remanenteInferiorC)+Double.parseDouble(remanenteInferiorD))/4;
			calcularTotalMovimiento(event);
			existeErrorRemInf = false;
		} else {
			existeErrorRemInf = true;
			new ValidatorException(new ControladorMensajes().validacion("Remanente inferior tiene un error en los datos ingresados"));
		}
	}
	
	public void calcularTotalIzquierdo(AjaxBehaviorEvent event) {
		if(validador.validarReal(remanenteIzquierdoA, "El remanente 'izquierdo a' debe contener valores numéricos") && 
				validador.validarReal(remanenteIzquierdoB, "El remanente 'izquierdo b' debe contener valores numéricos") &&
				validador.validarReal(remanenteIzquierdoC, "El remanente 'izquierdo c' debe contener valores numéricos") &&
				validador.validarReal(remanenteIzquierdoD, "El remanente 'izquierdo d' debe contener valores numéricos")) {
			totalIzquierdo = (Double.parseDouble(remanenteIzquierdoA)+Double.parseDouble(remanenteIzquierdoB)+Double.parseDouble(remanenteIzquierdoC)+Double.parseDouble(remanenteIzquierdoD))/4;
			calcularTotalMovimiento(event);
			existeErrorRemIzq = false;
		} else {
			existeErrorRemIzq = true;
			new ValidatorException(new ControladorMensajes().validacion("Remanente izquierdo tiene un error en los datos ingresados"));
		}
	}
	
	public void calcularTotalDerecha(AjaxBehaviorEvent event) {
		if(validador.validarReal(remanenteDerechaA, "El remanente 'derecho a' debe contener valores numéricos") && 
				validador.validarReal(remanenteDerechaB, "El remanente 'derecho b' debe contener valores numéricos") &&
				validador.validarReal(remanenteDerechaC, "El remanente 'derecho c' debe contener valores numéricos") &&
				validador.validarReal(remanenteDerechaD, "El remanente 'derecho d' debe contener valores numéricos")) {
			totalDerecha = (Double.parseDouble(remanenteDerechaA)+Double.parseDouble(remanenteDerechaB)+Double.parseDouble(remanenteDerechaC)+Double.parseDouble(remanenteDerechaD))/4;
			calcularTotalMovimiento(event);
			existeErrorRemDer = false;
		} else {
			existeErrorRemDer = true;
			new ValidatorException(new ControladorMensajes().validacion("Remanente derecho tiene un error en los datos ingresados"));
		}
	}
	
	public void calcularTotalMovimiento(AjaxBehaviorEvent event){
		totalOperacion = (totalSuperior+totalInferior+totalIzquierdo+totalDerecha)/4;
		habilitarBotonMontaje(null);
	}
	
	public void calcularRecorridoAcumulado(AjaxBehaviorEvent event) {
		recorridoAcumulado = this.kilometrajeMovimiento - vehiculoSeleccionado.getKilometraje();
	}


	

	
	/*

					HashMap<String, Object> propiedades = new HashMap<String, Object>();
					propiedades.put("vehiculo", this.vehiculoSeleccionado);
					propiedades.put("neumatico", this.neumaticoSeleccionado);
					List<Movimiento> listaMovAux = MovimientoDAO.getInstancia().buscarEntidadesPorPropiedades(propiedades);
					
					System.out.println("movimientos cargados en AUX");
					for(int t=0; t<listaMovAux.size(); t++) {
						System.out.println(listaMovAux.get(t).getPosicionFinal());
					}
					
					List<Date> listaFecha = new ArrayList<Date>();
					for(int m=0; m<listaMovAux.size(); m++) {
						if(listaMovAux.get(m).getId().getTipoMovimiento().equals("M")) {
							listaFecha.add(listaMovAux.get(m).getId().getFecha());
						} else {
							listaMovAux.remove(m);
						}
					}
					List<Date> listaFechaOrdenada = validador.ordenarFechasAscendente(listaFecha);
					Movimiento movimientoPrevio = new Movimiento();
					for(int m=0; m<listaMovAux.size(); m++) {
						if(validador.compararFechaUnoConFechaDos(listaMovAux.get(m).getId().getFecha(), listaFechaOrdenada.get(0)) == 0){
							movimientoPrevio = listaMovAux.get(m);
							listaMovimientosPrevio.add(movimientoPrevio);
						}
					}
					System.out.println("movimientos cargados en MOV PREVIO");
					for(int t=0; t<listaMovimientosPrevio.size(); t++) {
						System.out.println(listaMovimientosPrevio.get(t).getPosicionFinal());
					}
//					for(int m=0; m<listaMovAux.size(); m++) {
//						listaMovimientosPrevio.add(obtenerMovimientoPrevio(listaMovAux, listaFechaOrdenada));
//					}
					presionRotacion = movimientoPrevio.getPresion();
					kilometrajeMovimiento = movimientoPrevio.getKilometraje();
					//neumaticoSeleccionado = movimientoPrevio.getNeumatico();
					//listaPosicionesVehiculoPrevio.get(i).setMovimiento(movimientoPrevio);
					//this.actualizarFondosNeumaticos(listaPosicionesActivasVehiculo.get(i).getNombreRueda());
	 */
	
	
	
//	public Movimiento obtenerMovimientoPrevio(List<Movimiento> listaMovAux, List<Date> listaFechaOrdenada) {
//		for(int m=0; m<listaMovAux.size(); m++) {
//			if(validador.compararFechaUnoConFechaDos(listaMovAux.get(m).getId().getFecha(), listaFechaOrdenada.get(0)) == 0){
//				movimientoPrevio = listaMovAux.get(m);
//				return movimientoPrevio;
//			}
//		}
//		return movimientoPrevio;	
//	}
	
	private boolean montado =false;
	
	public boolean isMontado() {
		return montado;
	}

	public void setMontado(boolean montado) {
		this.montado = montado;
	}

	/**
	 * Este metodo se utiliza para actualizar los campos que muestran la informacion del neumatico seleccionado desde el comboBox
	 */
	public void actualizarNeumaticoMontaje() {
		if(neumaticoSeleccionadoMontaje.getIdNeumatico() != 0) {
			neumaticoSeleccionadoMontaje = (Neumatico) NeumaticoDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionadoMontaje.getIdNeumatico());
			setTipoNeumatico(traducirTipoNeumatico(neumaticoSeleccionadoMontaje));
			setCondicion(traducirCondicion(neumaticoSeleccionadoMontaje));
			Diseno disenoActual = (Diseno) DisenoDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionadoMontaje.getIdDisenoActual());
			if(neumaticoSeleccionadoMontaje.getIdNeumatico() == 0 || disenoActual.getImagen() == null) {
				System.out.println("no tenemos nada--- IDneumsaticoSeleccionado: "+neumaticoSeleccionadoMontaje.getIdNeumatico()+" disenoActual: "+disenoActual.getIdDiseno());
				colocarImagenDefault();
			} else {
				System.out.println("ya tenemos la imagen desde la BD");
				setStreamedContentImagen(publicarImagen(((Diseno) DisenoDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionadoMontaje.getIdDisenoActual())).getImagen()));
				System.out.println("IMAGEN: "+getStreamedContentImagen());
			}
			disabledDatosMontaje = false;
			montado = true;
		} else {
			limpiarDatosNeumatico();
		}
		
	}
	
	public void actualizarPosicionMontaje() {
		if(almacenActivo.equals("T")) {
			neumaticoSeleccionadoMontaje = neumaticoSeleccionadoAlmacen;	
		}
		if(neumaticoSeleccionadoMontaje.getIdNeumatico() != null) {
			neumaticoSeleccionadoMontaje.setPresionActual(presion);
			Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		    Map.Entry neumaticoVehiculoMapa;
			while (iterador.hasNext()) {
				neumaticoVehiculoMapa = (Map.Entry) iterador.next();
		        NeumaticoVehiculo neumaticoVehiculoAux = ((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo();
				if(posicionSeleccionada.equals("0") && !ultimaRuedaMontada.equals("")) {
					((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get((Object)ultimaRuedaMontada)).getNeumaticoVehiculo().setNeumatico(null);
					((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get((Object)ultimaRuedaMontada)).setMontado(false);
					actualizarFondosNeumaticos(ultimaRuedaMontada);
					ultimaRuedaMontada = "";
				}
		        if(((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo().getPosicion().equals(posicionSeleccionada)) {
			        if(!ultimaRuedaMontada.equals("")) {
						((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(ultimaRuedaMontada)).getNeumaticoVehiculo().setNeumatico(null);
						((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(ultimaRuedaMontada)).setMontado(false);
						actualizarFondosNeumaticos(ultimaRuedaMontada);
					}
			        if(cancelarMontaje) {
						//((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).setMontado(false);
						//((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo().setNeumatico(neumaticoSeleccionadoMontaje);
						mapaRelacionalNeumaticosOperacion.remove((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue());//put((String)neumaticoVehiculoMapa.getKey(), (DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue());
						cancelarMontaje = false;
			        } else {
						((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).setMontado(true);
						((DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue()).getNeumaticoVehiculo().setNeumatico(neumaticoSeleccionadoMontaje);
						mapaRelacionalNeumaticosOperacion.put((String)neumaticoVehiculoMapa.getKey(), (DatosNeumaticoRueda) neumaticoVehiculoMapa.getValue());
						actualizarFondosNeumaticos(obtenerNombreRuedaDelMapa(neumaticoSeleccionadoMontaje));
			        }
					ultimaRuedaMontada = (String)neumaticoVehiculoMapa.getKey();
					nombreRueda = (String)neumaticoVehiculoMapa.getKey();
				}
			}
			calcularPosicionesLibres();
			habilitarBotonMontaje(null);
		}
	}
	
	
	private boolean cancelarMontaje = false;
	
	public void cancelarMontaje() {
		cancelarMontaje = true;
		actualizarPosicionMontaje();
		cancelar();
	}
	
	public void habilitarBotonMontaje(AjaxBehaviorEvent event) {
		if(presion != 0.0 && presion != null && !posicionSeleccionada.equals("0") && !posicionSeleccionada.equals("")) {
			if(almacenActivo.equals("T")) {
				if(totalOperacion != null && totalOperacion != 0.0) {
					System.out.println("posicionSeleccionada: "+posicionSeleccionada);
					disabledBtnMontar = false;
				}
			} else {
				if(neumaticoSeleccionadoMontaje.getIdNeumatico() != null && neumaticoSeleccionadoMontaje.getIdNeumatico() != 0) {
					disabledBtnMontar = false;
				}
			}
		} else {
			disabledBtnMontar = true;
		}
	}
	
	public void habilitarMontaje() {
		if(neumaticoSeleccionadoMontaje.getIdNeumatico() != null) {
			if(presion != 0.0 && presion != null) {
				if(!posicionSeleccionada.equals("0")) {
					operacionActual = "M";
					mensajeConfirmacion = "Está seguro que desea MONTAR el neumático "+neumaticoSeleccionadoMontaje.getCodInterno()+" en la posición "
										+posicionSeleccionada+" del vehículo con placa "+vehiculoSeleccionado.getPlaca();
					RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");
				} else {
					mensajes.advertencia("ATENCIÓN", "Debe seleccionar la posición en la cual desea montar el neumático seleccionado");
				}
			} else {
				mensajes.advertencia("ATENCIÓN", "Debe ingresar un valor en el campo Presión");
			}
		}
	}
	
	/**
	 * Este metodo se encarga de modificar el valor del atributo que almacena la imagen que se va a mostrar en el diseno del neumatico. El parámetro de entrada es 
	 * un arreglo de bytes que representa la imagen almacena en la BD como imagen del diseno del neumatico
	 * @param byteArrayImagen
	 */
	public StreamedContent publicarImagen(byte[] byteArrayImagen) {
		return new DefaultStreamedContent(new ByteArrayInputStream(byteArrayImagen));
	}
	
	/**
	 * Este metodo se encarga de colocar una imagen por defecto en el formulario que muestra los datos del neumatico
	 */
	public void colocarImagenDefault() {
//		Path path = Paths.get("C:/Documents and Settings/user-pasante/Escritorio/proyecto-cauca/workspace/proyecto-cauca-SCSN/WebContent/imagenes/ruedaTransparente.png");
//		Path path = Paths.get("C:/Users/cauca/Desktop/SCSN-Coord-Informática/WorkSpace/proyecto-cauca-SCSN-integrar/WebContent/imagenes/ruedaTransparente.png");
		Path path = Paths.get("/var/lib/tomcat7/webapps/cauca-SCSN/WebContent/imagenes/ruedaTransparente.png");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			setStreamedContentImagen(publicarImagen(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo se utiliza para mostrar un valor legible para el usuario segun el tipo de neumatico
	 */
	public String traducirTipoNeumatico(Neumatico neumatico) {
		String tipo = "";
		if(neumatico.getTipoNeumatico().equals("D")) {
			tipo = "Direccional";	
		} else if(neumatico.getTipoNeumatico().equals("M")) {
			tipo = "Mixto";	
		} else if(neumatico.getTipoNeumatico().equals("T")) {
			tipo = "Tracción";	
		}
		return tipo;
	}
	
	/**
	 * Este metodo se utiliza para mostrar un valor legible para el usuario segun la condicion del neumatico
	 */
	public String traducirCondicion(Neumatico neumatico) {
		String condicion = "";
		if(neumatico.getCondicion().equals("N")){
			condicion = "Nuevo"; 
		}else if(neumatico.getCondicion().equals("1")){
			condicion = "1er Reencauche";
		}else if(neumatico.getCondicion().equals("2")){
			condicion = "2do Reencauche";
		}else if(neumatico.getCondicion().equals("3")){
			condicion = "3er Reencauche";
		}else if(neumatico.getCondicion().equals("4")){
			condicion = "4to Reencauche";
		}else if(neumatico.getCondicion().equals("5")){
			condicion = "5to Reencauche";
		}
		return condicion;
	}
	
	/**
	 * Este metodo es usado para obtener el numero que le indica al usuario cual es el primer neumático en el esquema de eje del vehiculo, 
	 * ya que este valor cambia de posicion segun la cantidad de neumaticos y si el vehiculo es fuera o dentro de carretera. Utiliza el nombre del esquema para
	 * ir recorriendo cada caracter de esta cadena y asi saber donde colocar el valor
	 * @param nombreEsquema
	 */
	public void calcularPrimerNeumatico(String nombreEsquema){
		boolean primerNeumaticoListo = false;
		int contadorEje = 0;
		
		for(int i=0; i< nombreEsquema.length(); i++){
			if(nombreEsquema.charAt(i) != ':'){
				contadorEje++;
			}
			
			if((nombreEsquema.charAt(i) != '-') && (nombreEsquema.charAt(i) != ':')){
				if(!primerNeumaticoListo){
					if(vehiculoSeleccionado.getModeloVehiculo().getTipoVehiculo().getDentroCarretera().equals("S")){
						if(nombreEsquema.charAt(i)=='4'){
							switch (contadorEje) {
								case 1:
									primerNeumatico1 = "1";
									break;
								case 2:
									primerNeumatico5 = "1";
									break;
								case 3:
									primerNeumatico9 = "1";
									break;
								case 4:
									primerNeumatico13 = "1";
									break;
								case 5:
									primerNeumatico17 = "1";
									break;
							}	
						} else if(nombreEsquema.charAt(i)=='2'){
							switch (contadorEje) {
								case 1:
									primerNeumatico2 = "1";
									break;
								case 2:
									primerNeumatico6 = "1";
									break;
								case 3:
									primerNeumatico10 = "1";
									break;
								case 4:
									primerNeumatico14 = "1";
									break;
								case 5:
									primerNeumatico18 = "1";
									break;
							}
						}
					} else {
						if(nombreEsquema.charAt(i)=='4'){
							switch (contadorEje) {
								case 1:
									primerNeumatico4 = "1";
									break;
								case 2:
									primerNeumatico8 = "1";
									break;
								case 3:
									primerNeumatico12 = "1";
									break;
								case 4:
									primerNeumatico16 = "1";
									break;
								case 5:
									primerNeumatico20 = "1";
									break;
							}	
						} else if(nombreEsquema.charAt(i)=='2'){
							switch (contadorEje) {
								case 1:
									primerNeumatico3 = "1";
									break;
								case 2:
									primerNeumatico7 = "1";
									break;
								case 3:
									primerNeumatico11 = "1";
									break;
								case 4:
									primerNeumatico15 = "1";
									break;
								case 5:
									primerNeumatico19 = "1";
									break;
							}
						}
					}
					primerNeumaticoListo = true;
				}
			}
		}
	}

	public boolean validarDatosParaMontaje() {
		if(this.neumaticoSeleccionado.getIdNeumatico() != null) {
			if((presion != null) && (presion != 0) && (validador.validarReal(Double.toString(presion), "El valor de la presión debe contener solo números o punto (.)"))) {
				if((kilometrajeMovimiento != null) && (kilometrajeMovimiento != 0) && (validador.validarReal(Double.toString(kilometrajeMovimiento), "El valor del kilometraje debe contener solo números o punto (.)"))) {
					return true;
				} else {
					mensajes.error("ATENCIÓN", "El campo KILOMETRAJE ACTUAL del vehículo debe contener valores numéricos");
					return false;
				}
			} else {
				mensajes.error("ATENCIÓN", "El campo PRESIÓN del neumático para montar debe contener valores numéricos");
				return false;
			}
		} else {
			mensajes.advertencia("Atención", "Debe seleccionar un neumático de la lista de la derecha");
			return false;
		}
	}
	
	public void agregarValoresAListas(String neumaticoEntranteParam, boolean desmonte) {
		if(!desmonte) {
			for(int i =0; i<listaPosicionesActivasVehiculo.size(); i++) {
				if(listaPosicionesActivasVehiculo.get(i).getNombreRueda().equals(neumaticoEntranteParam)) {
					movimientoActualId.setIdNeumatico(neumaticoSeleccionado.getIdNeumatico());
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setId(movimientoActualId);
//					listaPosicionesActivasVehiculo.get(i).getMovimiento().setPresion(presionRotacion);
//					listaPosicionesActivasVehiculo.get(i).getMovimiento().setNeumatico(neumaticoSeleccionado);
//					listaPosicionesActivasVehiculo.get(i).getMovimiento().setKilometraje(kilometrajeMovimiento);
//					listaPosicionesActivasVehiculo.get(i).getMovimiento().setObservaciones(observacionesMovimiento);
//					neumaticoVehiculo.getNeumatico().setPresionActual(presionRotacion);
					neumaticoVehiculo.setPosicion(Integer.toString(listaPosicionesActivasVehiculo.get(i).getMovimiento().getPosicionInicial()));
				}
			}
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
			filtrarNeumaticoDataModel(neumaticoSeleccionado.getIdNeumatico());
			existeMontados = true;
		} else {
			for(int i =0; i<listaPosicionesActivasVehiculo.size(); i++) {
				if(listaPosicionesActivasVehiculo.get(i).getNombreRueda().equals(neumaticoEntranteParam)) {
					for(int j = 0;j<listaNeumaticoVehiculoDeOperacionActual.size(); j++) {
						if(listaNeumaticoVehiculoDeOperacionActual.get(j).getPosicion().equals(Integer.toString(listaPosicionesActivasVehiculo.get(i).getMovimiento().getPosicionFinal()))) {
							listaNeumaticoVehiculoDeOperacionActual.remove(j);
						}
					}
					listaPosicionesActivasVehiculo.get(i).getMovimiento().getId().setIdNeumatico(null);;
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setPresion(null);
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setNeumatico(null);
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setKilometraje(null);
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setObservaciones(null);
				}
			}
			filtrarNeumaticoDataModel(0);
			if (listaNeumaticoVehiculoDeOperacionActual.size() <= 0) {
				existeMontados = false;
			}
		}
		limpiarDatosNeumatico();
	}
	
	public void habilitarVentanaConsultaDesmonte(String neumaticoEntranteParam) {
		for(int i = 0; i<listaPosicionesActivasVehiculo.size(); i++){
			if(listaPosicionesActivasVehiculo.get(i).getNombreRueda().equals(neumaticoEntranteParam)){
				movimientoSeleccionado = listaPosicionesActivasVehiculo.get(i).getMovimiento();
				nombreRuedaOculto = listaPosicionesActivasVehiculo.get(i).getNombreRueda();
			}
		}
		setCondicionConfirmar(traducirCondicion(movimientoSeleccionado.getNeumatico()));
		setTipoNeumaticoConfirmar(traducirTipoNeumatico(movimientoSeleccionado.getNeumatico()));
//			this.streamedContentImagenConfirmar = streamedContentImagen;
//			setStreamedContentImagenConfirmar(publicarImagen(movimientoSeleccionado.getNeumatico().getDisenoMedida().getDiseno().getImagen()));
		RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarDesmontajeID");
		RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");
	}
	
	public void obtenerNeumaticoRotar(String neumaticoEntrante) {
	//	this.neumaticoSeleccionado = ((NeumaticoVehiculo) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).getNeumatico();
		
		
//		for(int i = 0; i<listaPosicionesVehiculoPrevio.size(); i++){
//			if(listaPosicionesVehiculoPrevio.get(i).getNombreRueda().equals(neumaticoEntrante)) {
//				neumaticoSeleccionado = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getNeumatico();
//				fechaMontaje = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getId().getFecha();
//				posicionActual = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getPosicionFinal();
//			}
//		}
		setFechaMontaje(fechaMontaje); //AQUI TENEMOS QUE OBTENER LA FECHA DEL MOVIMIENTO QUE SE RELACIONE CON EL NEUMÁTICO OBTENIDO
		setCondicion(traducirCondicion(neumaticoSeleccionado));
		setTipoNeumatico(traducirTipoNeumatico(neumaticoSeleccionado));
		setStreamedContentImagen(publicarImagen(neumaticoSeleccionado.getDisenoMedida().getDiseno().getImagen()));
		disabledBtnMontar = false;
	}
	
	public void actualizarFondosNeumaticos(String neumaticoParametro) {
		String neumaticoEntrante = "";
		neumaticoVehiculo = new NeumaticoVehiculo();
		neumaticoVehiculoId = new NeumaticoVehiculoID();
		
		if(neumaticoParametro.equals("")) {
			neumaticoEntrante = nombreRuedaOculto;
		} else {
			neumaticoEntrante = neumaticoParametro;
		}
		
		if(neumaticoEntrante.equals("neumatico11")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico11Image = "../imagenes/ruedaAgricola.png";
				neumatico11DragDisabled = false;
			} else { 
				neumatico11Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico11DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico12")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico12Image = "../imagenes/ruedaAgricola.png";
				neumatico12DragDisabled = false;
			} else { 
				neumatico12Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico12DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico13")) {
			System.out.println("LO QUE CONSEGUIMOS ESTÁ MONTADO? "+((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado());
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico13Image = "../imagenes/ruedaAgricola.png";
				neumatico13DragDisabled = false;
			} else { 
				neumatico13Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico13DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico14")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico14Image = "../imagenes/ruedaAgricola.png";
				neumatico14DragDisabled = false;
			} else { 
				neumatico14Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico14DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico21")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico21Image = "../imagenes/ruedaAgricola.png";
				neumatico21DragDisabled = false;
			} else { 
				neumatico21Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico21DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico22")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico22Image = "../imagenes/ruedaAgricola.png";
				neumatico22DragDisabled = false;
			} else { 
				neumatico22Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico22DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico23")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico23Image = "../imagenes/ruedaAgricola.png";
				neumatico23DragDisabled = false;
			} else { 
				neumatico23Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico23DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico24")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico24Image = "../imagenes/ruedaAgricola.png";
				neumatico24DragDisabled = false;
			} else { 
				neumatico24Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico24DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico31")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico31Image = "../imagenes/ruedaAgricola.png";
				neumatico31DragDisabled = false;
			} else { 
				neumatico31Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico31DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico32")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico32Image = "../imagenes/ruedaAgricola.png";
				neumatico32DragDisabled = false;
			} else { 
				neumatico32Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico32DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico33")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico33Image = "../imagenes/ruedaAgricola.png";
				neumatico33DragDisabled = false;
			} else { 
				neumatico33Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico33DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico34")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico34Image = "../imagenes/ruedaAgricola.png";
				neumatico34DragDisabled = false;
			} else { 
				neumatico34Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico34DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico41")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico41Image = "../imagenes/ruedaAgricola.png";
				neumatico41DragDisabled = false;
			} else { 
				neumatico41Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico41DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico42")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico42Image = "../imagenes/ruedaAgricola.png";
				neumatico42DragDisabled = false;
			} else { 
				neumatico42Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico42DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico43")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico43Image = "../imagenes/ruedaAgricola.png";
				neumatico43DragDisabled = false;
			} else { 
				neumatico43Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico43DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico44")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico44Image = "../imagenes/ruedaAgricola.png";
				neumatico44DragDisabled = false;
			} else { 
				neumatico44Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico44DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico51")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico51Image = "../imagenes/ruedaAgricola.png";
				neumatico51DragDisabled = false;
			} else { 
				neumatico51Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico51DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico52")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico52Image = "../imagenes/ruedaAgricola.png";
				neumatico52DragDisabled = false;
			} else { 
				neumatico52Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico52DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico53")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico53Image = "../imagenes/ruedaAgricola.png";
				neumatico53DragDisabled = false;
			} else { 
				neumatico53Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico53DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumatico54")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumatico54Image = "../imagenes/ruedaAgricola.png";
				neumatico54DragDisabled = false;
			} else { 
				neumatico54Image = "../imagenes/ruedaAgricolaApagada.png";
				neumatico54DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumaticoR21")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumaticoRepIzqImage = "../imagenes/ruedaAgricola.png";
				neumaticoR21DragDisabled = false;
			} else { 
				neumaticoRepIzqImage = "../imagenes/ruedaAgricolaApagada.png";
				neumaticoR21DragDisabled = true;
			}	
		}
		if(neumaticoEntrante.equals("neumaticoR22")) {
			if(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).isMontado()) {
				neumaticoRepDerImage = "../imagenes/ruedaAgricola.png";
				neumaticoR22DragDisabled = false;
			} else { 
				neumaticoRepDerImage = "../imagenes/ruedaAgricolaApagada.png";
				neumaticoR22DragDisabled = true;
			}	
		}
		/*
		
		System.out.println("::::::::::::::::::::::::::::::::::::: EMPEZAMOS A LISTAR ::::::::::::::::::::::::::::::::");
		for(int i =0; i<listaPosicionesActivasVehiculo.size(); i++){
			System.out.println("nombre de la rueda: "+listaPosicionesActivasVehiculo.get(i).getNombreRueda());
			System.out.println("posicion en lista: "+i);
			System.out.println("posicion que indica: "+listaPosicionesActivasVehiculo.get(i).getMovimiento().getPosicionFinal());
			System.out.println("kilometraje marcado: "+listaPosicionesActivasVehiculo.get(i).getMovimiento().getKilometraje());
			System.out.println("Observaciones: "+listaPosicionesActivasVehiculo.get(i).getMovimiento().getObservaciones());
			System.out.println("fecha: "+listaPosicionesActivasVehiculo.get(i).getMovimiento().getId().getFecha());
			System.out.println("Vehiculo: "+listaPosicionesActivasVehiculo.get(i).getMovimiento().getVehiculo().getPlaca());
		}
		System.out.println("-----------------------------------------------------------------");
		for(int t =0; t<listaNeumaticoVehiculoMontadosPrevio.size(); t++){
			System.out.println("vehiculo donde se monto: "+listaNeumaticoVehiculoMontadosPrevio.get(t).getVehiculo().getIdVehiculo());
			System.out.println("neumatico montado: "+listaNeumaticoVehiculoMontadosPrevio.get(t).getNeumatico().getCodInterno());
			System.out.println("posicion donde se monto: "+listaNeumaticoVehiculoMontadosPrevio.get(t).getPosicion());
		}
		System.out.println("::::::::::::::::::::::::::::::: TERMINAMOS DE LISTAR LOS ELEMENTOS INVOLUCRADOS ::::::::::::::::::::::::::::::::::::::::::::::::.");*/
	}	
	
	/**
	 * Este metodo se encarga de invertir la secuencia de conteo de cada posicion de las ruedas en los vehiculos fuera de carretera, ya que estos vehiculos 
	 * empiezan el conteo de las ruedas de derecha a izquierda
	 */
	public void cambiarNumeracionPosicionFC() {
//		int ultimaPosicion = listaPosicionesActivasVehiculo.size()-2;
//		
//		for(int i=0; i<listaPosicionesActivasVehiculo.size()-2; i++) {
//			listaPosicionesActivasVehiculo.get(i).getMovimiento().setPosicionFinal(ultimaPosicion);
//			ultimaPosicion--;
//		}
		/*for(int i=0; i<listaPosicionesActivasVehiculo.size()-2; i++) {
			if(i == 0 || validador.esPar(i)) {
				listaPosicionesActivasVehiculo.get(i).getMovimiento().setPosicionFinal(listaPosicionesActivasVehiculo.get(i).
						getMovimiento().getPosicionFinal() + 1);
			} else {
				listaPosicionesActivasVehiculo.get(i).getMovimiento().setPosicionFinal(listaPosicionesActivasVehiculo.get(i).
											getMovimiento().getPosicionFinal() - 1);
			}
		}

		listaPosicionesActivasVehiculo.get(listaPosicionesActivasVehiculo.size()-2).getMovimiento().setPosicionFinal(21);
		listaPosicionesActivasVehiculo.get(listaPosicionesActivasVehiculo.size()-1).getMovimiento().setPosicionFinal(22);*/
	    System.out.println("tamaño del mapa: "+mapaRelacionalNeumaticosOperacion.size());	    
	    /*
	    Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
	    Map.Entry neumaticoVehiculoMapa;
	    Map.Entry neumaticoVehiculoMapa2;
	    NeumaticoVehiculo neumaticoVehiculoAux = new NeumaticoVehiculo();
	    NeumaticoVehiculo neumaticoVehiculoAux2 = new NeumaticoVehiculo();
	    String claveAux = "";
	    int i = 0;
	    
		while (iterador.hasNext()) {
			neumaticoVehiculoMapa = (Map.Entry) iterador.next();
			claveAux = (String) neumaticoVehiculoMapa.getKey();
			neum
			aticoVehiculoAux = (NeumaticoVehiculo) neumaticoVehiculoMapa.getValue();
			System.out.println("----------------------");
			System.out.println("neumatico en cuestion: "+neumaticoVehiculoAux.getPosicion());
			System.out.println("clave en cuestion: "+claveAux);
			System.out.println("Posicion a cambiar: "+neumaticoVehiculoAux.getPosicion());
			if(!neumaticoVehiculoAux.getPosicion().equals("21") && !neumaticoVehiculoAux.getPosicion().equals("22")) {
				System.out.println("ENTRAMOS AL IF PARA CAMBIAR LA POSICION");
				if(i == 0 || validador.esPar(i)) {
					neumaticoVehiculoAux.setPosicion( Integer.toString(Integer.parseInt(neumaticoVehiculoAux.getPosicion())+1));
				} else {
					neumaticoVehiculoAux.setPosicion( Integer.toString(Integer.parseInt(neumaticoVehiculoAux.getPosicion())-1));
				}
				System.out.println("Posicion cambiada: "+neumaticoVehiculoAux.getPosicion());
				mapaRelacionalNeumaticosOperacion.put(claveAux, neumaticoVehiculoAux);
			}
			i++;
		}

		System.out.println("posicion donde se monto la rueda: "+(mapaRelacionalNeumaticosOperacion.get("neumaticoR21")).getPosicion());
		Iterator iterador2 = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		while (iterador2.hasNext()) {
			System.out.println("***************************");
			neumaticoVehiculoMapa2 = (Map.Entry) iterador2.next();
			claveAux = (String) neumaticoVehiculoMapa2.getKey();
			neumaticoVehiculoAux2 = (NeumaticoVehiculo) neumaticoVehiculoMapa2.getValue();
			System.out.println("neumatico en rueda "+claveAux+" => "+neumaticoVehiculoAux2.getPosicion());
		}*/
	}
	
	public void actualizarFondoEjes() {
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje1() != null) {
			eje1Image = "../imagenes/eje.png";
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje2() != null) {
			eje2Image = "../imagenes/eje.png";
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje3() != null) {
			eje3Image = "../imagenes/eje.png";
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje4() != null) {
			eje4Image = "../imagenes/eje.png";
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getEje5() != null) {
			eje5Image = "../imagenes/eje.png";
		}
	}
	
	/**
	 * Este metodo se encarga de cambiar el valor de la propiedad disabled para cada boton que representa un neumatico en el esquema de eje del vehiculo seleccionado.
	 * Ademas, crea un objeto Movimiento y PosicionesVehiculo para cada rueda que se habilite en el esquema de eje, para esto debe cargar una lista con cada objeto
	 * PosicionesVehiculo los cuales contendran el movimiento correspondiente para cada rueda. Se aprovecha la secuencia de acciones para actualizar el valor
	 * de la imagen de fondo que sera mostrada en la vista para este esquema de eje, es decir, para que se muestre cada rueda habilitada 
	 */
	public void actualizarDisabledNeumaticosFC() {
		int J = 1;
		NeumaticoVehiculoID neumaticoVehiculoId = new NeumaticoVehiculoID();
		neumaticoVehiculoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
		listaPosicionesActivasVehiculo = new ArrayList<PosicionesVehiculo>();
		listaPosicionesVehiculoPrevio = new ArrayList<PosicionesVehiculo>();
		listaNeumaticoVehiculoDeOperacionActual = new ArrayList<NeumaticoVehiculo>();
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico4() != null) {
//			neumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			//neumatico14RenderBtn = false;
			neumatico14Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico14DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico14", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico11Disabled, "neumatico11", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico3() != null) {
//			neumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			//neumatico13RenderBtn = false;
			neumatico13Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico13DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico13", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico12Disabled, "neumatico12", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico2() != null) {
//			neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			//neumatico12RenderBtn = false;
			neumatico12Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico12DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico12", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico13Disabled, "neumatico13", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico1() != null) {
//			neumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico11RenderBtn = false;
			neumatico11Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico11DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico11", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico14Disabled, "neumatico14", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico8() != null) {
//			neumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico24RenderBtn = false;
			neumatico24Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico24DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico24", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico21Disabled, "neumatico21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico7() != null) {
//			neumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico23RenderBtn = false;
			neumatico23Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico23DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico23", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico22Disabled, "neumatico22", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico6() != null) {
//			neumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico22RenderBtn = false;
			neumatico22Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico22DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico22", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico23Disabled, "neumatico23", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico5() != null) {
//			neumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico21RenderBtn = false;
			neumatico21Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico21DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico21", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico24Disabled, "neumatico24", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico12() != null) {
//			neumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico34RenderBtn = false;
			neumatico34Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico34DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico34", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico31Disabled, "neumatico31", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico11() != null) {
//			neumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico33RenderBtn = false;
			neumatico33Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico33DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico33", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico32Disabled, "neumatico32", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico10() != null) {
//			neumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico32RenderBtn = false;
			neumatico32Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico32DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico32", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico33Disabled, "neumatico33", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico9() != null) {
//			neumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico31RenderBtn = false;
			neumatico31Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico31DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico31", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico34Disabled, "neumatico34", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico16() != null) {
//			neumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico44RenderBtn = false;
			neumatico44Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico44DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico44", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico41Disabled, "neumatico41", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico15() != null) {
//			neumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico43RenderBtn = false;
			neumatico43Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico43DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico43", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico42Disabled, "neumatico42", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico14() != null) {
//			neumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico42RenderBtn = false;
			neumatico42Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico42DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico42", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico43Disabled, "neumatico43", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico13() != null) {
//			neumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico41RenderBtn = false;
			neumatico41Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico41DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico41", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico44Disabled, "neumatico44", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico20() != null) {
//			neumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico54RenderBtn = false;
			neumatico54Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico54DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico54", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico51Disabled, "neumatico51", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico19() != null) {
//			neumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico53RenderBtn = false;
			neumatico53Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico53DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico53", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico52Disabled, "neumatico52", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico18() != null) {
//			neumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico52RenderBtn = false;
			neumatico52Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico52DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico52", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico53Disabled, "neumatico53", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico17() != null) {
//			neumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico51RenderBtn = false;
			neumatico51Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico51DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico51", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico54Disabled, "neumatico54", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico21() != null) {
//			neumatico21 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepIzqDisabled = false;
			neumaticoRepIzqImage = "../imagenes/ruedaAgricolaApagada.png";
//			neumaticoR21DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(21);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepIzqDisabled, "neumaticoR21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico22() != null) {
//			neumatico22 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepDerDisabled = false;
			neumaticoRepDerImage = "../imagenes/ruedaAgricolaApagada.png";
//			neumaticoR22DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(22);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepDerDisabled, "neumaticoR22", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
	}
	
	/**
	 * Este metodo se encarga de cambiar el valor de la propiedad disabled para cada boton que representa un neumatico en el esquema de eje del vehiculo seleccionado.
	 * Ademas, crea un objeto Movimiento y PosicionesVehiculo para cada rueda que se habilite en el esquema de eje, para esto debe cargar una lista con cada objeto
	 * PosicionesVehiculo los cuales contendran el movimiento correspondiente para cada rueda. Se aprovecha la secuencia de acciones para actualizar el valor
	 * de la imagen de fondo que sera mostrada en la vista para este esquema de eje, es decir, para que se muestre cada rueda habilitada 
	 */
	public void actualizarDisabledNeumaticos() {
		int J = 1;
		NeumaticoVehiculoID neumaticoVehiculoId = new NeumaticoVehiculoID();
		neumaticoVehiculoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
//		movimientoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
//		movimientoId.setTipoMovimiento("R");
		listaPosicionesActivasVehiculo = new ArrayList<PosicionesVehiculo>();
		listaPosicionesVehiculoPrevio = new ArrayList<PosicionesVehiculo>();
		listaNeumaticoVehiculoDeOperacionActual = new ArrayList<NeumaticoVehiculo>();
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico1() != null) {
//			neumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico11RenderBtn = false;
			neumatico11Image = "../imagenes/ruedaAgricolaApagada.png";
			//neumatico11DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico11", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico11Disabled, "neumatico11", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico2() != null) {
//			neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico12RenderBtn = false;
			neumatico12Image = "../imagenes/ruedaAgricolaApagada.png";
			//neumatico12DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico12", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico12Disabled, "neumatico12", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico3() != null) {
//			neumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico13RenderBtn = false;
			neumatico13Image = "../imagenes/ruedaAgricolaApagada.png";
			//neumatico13DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico13", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico13Disabled, "neumatico13", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico4() != null) {
//			neumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico14RenderBtn = false;
			neumatico14Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico14DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico14", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico14Disabled, "neumatico14", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico5() != null) {
//			neumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico21RenderBtn = false;
			neumatico21Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico21DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico21", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico21Disabled, "neumatico21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico6() != null) {
//			neumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico22RenderBtn = false;
			neumatico22Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico22DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico22", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico22Disabled, "neumatico22", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico7() != null) {
//			neumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico23RenderBtn = false;
			neumatico23Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico23DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico23", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico23Disabled, "neumatico23", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico8() != null) {
//			neumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico24RenderBtn = false;
			neumatico24Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico24DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico24", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico24Disabled, "neumatico24", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico9() != null) {
//			neumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico31RenderBtn = false;
			neumatico31Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico31DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico31", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico31Disabled, "neumatico31", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico10() != null) {
//			neumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico32RenderBtn = false;
			neumatico32Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico32DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico32", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico32Disabled, "neumatico32", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico11() != null) {
//			neumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico33RenderBtn = false;
			neumatico33Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico33DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico33", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico33Disabled, "neumatico33", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico12() != null) {
//			neumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico34RenderBtn = false;
			neumatico34Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico34DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico34", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico34Disabled, "neumatico34", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico13() != null) {
//			neumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico41RenderBtn = false;
			neumatico41Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico41DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico41", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico41Disabled, "neumatico41", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico14() != null) {
//			neumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico42RenderBtn = false;
			neumatico42Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico42DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico42", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico42Disabled, "neumatico42", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico15() != null) {
//			neumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico43RenderBtn = false;
			neumatico43Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico43DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico43", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico43Disabled, "neumatico43", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico16() != null) {
//			neumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico44RenderBtn = false;
			neumatico44Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico44DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico44", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico44Disabled, "neumatico44", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico17() != null) {
//			neumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico51RenderBtn = false;
			neumatico51Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico51DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico51", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico51Disabled, "neumatico51", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico18() != null) {
//			neumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico52RenderBtn = false;
			neumatico52Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico52DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico52", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico52Disabled, "neumatico52", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico19() != null) {
//			neumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico53RenderBtn = false;
			neumatico53Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico53DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico53", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico53Disabled, "neumatico53", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico20() != null) {
//			neumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//			neumatico54RenderBtn = false;
			neumatico54Image = "../imagenes/ruedaAgricolaApagada.png";
//			neumatico54DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumatico54", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico54Disabled, "neumatico54", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico21() != null) {
//			neumatico21 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepIzqDisabled = false;
			neumaticoRepIzqImage = "../imagenes/ruedaAgricolaApagada.png";
//			neumaticoR21DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(21);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepIzqDisabled, "neumaticoR21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico22() != null) {
//			neumatico22 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepDerDisabled = false;
			neumaticoRepDerImage = "../imagenes/ruedaAgricolaApagada.png";
//			neumaticoR22DragDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			datosRueda = new DatosNeumaticoRueda(false, true, neumaticoVehiculo);
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", datosRueda);
			listaNeumaticoVehiculoDeOperacionActual.add(neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(22);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepDerDisabled, "neumaticoR22", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
	}
	
	public void procesarMovimiento() {
		movimientoActual = new Movimiento();
		Neumatico neumaticoMovimiento = new Neumatico();
		switch (operacionActual) {
			case "M":
				neumaticoMovimiento = neumaticoSeleccionadoMontaje;
				break;
			default:
				System.out.println("entramos por default");
				if(operacionActual.equals("D")) {
					neumaticoMovimiento = neumaticoSeleccionado;	
					movimientoActual.setTipoDesgaste(tipoDesgasteSeleccionado);
				} else if(operacionActual.equals("R")) {
					neumaticoMovimiento = neumaticoSeleccionadoAlmacen;
				}
				movimientoActual.setRemanenteSuperiorA(Double.parseDouble(remanenteSuperiorA));
				movimientoActual.setRemanenteSuperiorB(Double.parseDouble(remanenteSuperiorB));
				movimientoActual.setRemanenteSuperiorC(Double.parseDouble(remanenteSuperiorC));
				movimientoActual.setRemanenteSuperiorD(Double.parseDouble(remanenteSuperiorD));
				movimientoActual.setRemanenteIzquierdoA(Double.parseDouble(remanenteIzquierdoA));
				movimientoActual.setRemanenteIzquierdoB(Double.parseDouble(remanenteIzquierdoB));
				movimientoActual.setRemanenteIzquierdoC(Double.parseDouble(remanenteIzquierdoC));
				movimientoActual.setRemanenteIzquierdoD(Double.parseDouble(remanenteIzquierdoD));
				movimientoActual.setRemanenteDiagonalA(Double.parseDouble(remanenteInferiorA));
				movimientoActual.setRemanenteDiagonalB(Double.parseDouble(remanenteInferiorB));
				movimientoActual.setRemanenteDiagonalC(Double.parseDouble(remanenteInferiorC));
				movimientoActual.setRemanenteDiagonalD(Double.parseDouble(remanenteInferiorD));
				movimientoActual.setRemanenteDerechoA(Double.parseDouble(remanenteDerechaA));
				movimientoActual.setRemanenteDerechoB(Double.parseDouble(remanenteDerechaB));
				movimientoActual.setRemanenteDerechoC(Double.parseDouble(remanenteDerechaC));
				movimientoActual.setRemanenteDerechoD(Double.parseDouble(remanenteDerechaD));
				movimientoActual.setRemanenteMovimiento(totalOperacion);
				break;
		}
		movimientoActualId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
		movimientoActualId.setIdNeumatico(neumaticoMovimiento.getIdNeumatico());
		movimientoActualId.setTipoMovimiento(operacionActual);
//		movimientoActualId.setFecha(fecha);
		movimientoActual.setId(movimientoActualId);
		movimientoActual.setNeumatico(neumaticoMovimiento);
		movimientoActual.setVehiculo(vehiculoSeleccionado);
//		movimientoActual.setRecorridoAcumulado(recorridoAcumulado);
		movimientoActual.setPosicionInicial(Integer.parseInt(((DatosNeumaticoRueda) mapaRelacionalNeumaticosOperacion.get(nombreRueda)).getNeumaticoVehiculo().getPosicion()));
		try{
			movimientoActual.setPosicionFinal(Integer.parseInt(posicionSeleccionada));
		} catch (NumberFormatException ne) {
			movimientoActual.setPosicionFinal(0);
		}
//		movimientoActual.setKilometraje(kilometrajeMovimiento);
		movimientoActual.setPresion(presion);
		movimientoActual.setObservaciones(observacionesMovimiento);
	
		listaMovimiento.add(movimientoActual);
		System.out.println("tamaño de la lista de movimientos actuales: "+listaMovimiento.size());
		for (int i = 0; i < listaMovimiento.size() ; i++) {
			System.out.println("movimiento "+i+": "+listaMovimiento.get(i).getNeumatico().getCodInterno());
		}
		
		System.out.println("--- tamaño de la lista de previos ANTES: "+listaNeumaticoVehiculoMontadosPrevio.size());
		for(int t=0; t<listaNeumaticoVehiculoMontadosPrevio.size(); t++) {
			if(listaNeumaticoVehiculoMontadosPrevio.get(t).getNeumatico().getIdNeumatico() == neumaticoMovimiento.getIdNeumatico()) {
				listaNeumaticoVehiculoMontadosPrevio.remove(t);
			}
		}
		System.out.println("--- tamaño de la lista de previos DESPUES: "+listaNeumaticoVehiculoMontadosPrevio.size());
		
		dropped = false;
		filtrarNeumaticoDataModel(0);
		cancelar();
	}

	
	public void procesarOperacion(String accion) {
		if (listaNeumaticoVehiculoDeOperacionActual.size() == listaNeumaticoVehiculoMontadosPrevio.size()) {
			if(accion.equals("terminarVacio")) {
				limpiarDatosVehiculo();
				movimientoActualId.setFecha(null);
				existeMontados = false;
				limpiarDatosNeumatico();
				listaNeumaticos.clear();
				listaNeumaticoVehiculoDeOperacionActual.clear();
				filtrarNeumaticoDataModel(0);
				desmontaje = false;
				kilometrajeMovimiento = 0.0;
			} else {
				RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarTerminarVacio");
				RequestContext.getCurrentInstance().addCallbackParam("tarea", "CT");
			}
		} else {
			if(validador.validarFecha(validador.formatearFechaEstiloMedio(movimientoActualId.getFecha()))) {
				if(accion.equals("T")) {
						for(int i = 0; i<listaNeumaticoVehiculoDeOperacionActual.size(); i++) {
							NeumaticoVehiculoDAO.getInstancia().insertarOActualizar(listaNeumaticoVehiculoDeOperacionActual.get(i));
							listaNeumaticoVehiculoDeOperacionActual.get(i).getNeumatico().setEstado("M");
							NeumaticoDAO.getInstancia().insertarOActualizar(listaNeumaticoVehiculoDeOperacionActual.get(i).getNeumatico());
						}
						for(int j=0; j<listaPosicionesActivasVehiculo.size(); j++) {
							if(listaPosicionesActivasVehiculo.get(j).getMovimiento().getNeumatico() != null) {
								if(listaPosicionesActivasVehiculo.get(j).getMovimiento().getNeumatico().getRecorridoAcumulado() == null) {
									listaPosicionesActivasVehiculo.get(j).getMovimiento().getNeumatico().setRecorridoAcumulado(0.0);
								}
								listaPosicionesActivasVehiculo.get(j).getMovimiento().setRecorridoAcumulado(listaPosicionesActivasVehiculo.get(j).getMovimiento().getNeumatico().getRecorridoAcumulado());
								MovimientoDAO.getInstancia().insertarOActualizar(listaPosicionesActivasVehiculo.get(j).getMovimiento());
							}
						}
						limpiarDatosVehiculo();
						movimientoActualId.setFecha(null);
						existeMontados = false;
						limpiarDatosNeumatico();
						listaNeumaticos.clear();
						listaNeumaticoVehiculoDeOperacionActual.clear();
						filtrarNeumaticoDataModel(0);
						desmontaje = false;
						kilometrajeMovimiento = 0.0;
				} else if(accion.equals("A")) { 
//					fechaTerminar = validador.formatearFechaEstiloMedio(movimientoActualId.getFecha());
					RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarTerminar");
					RequestContext.getCurrentInstance().addCallbackParam("tarea", "T");
				}
			} else {
				mensajes.error("Fecha Inválida", "Por favor, verifique la fecha ingresada");
			}
		}
	}
	
	public void terminarGuardar() {
		System.out.println("entramos a guardar");
		for(Movimiento movimiento : this.listaMovimiento) {
			movimiento.getId().setFecha(this.fechaTerminar);
			movimiento.setKilometraje(this.kilometrajeMovimiento);
			movimiento.setRecorridoAcumulado(this.recorridoAcumulado);
			movimiento.getNeumatico().setRecorridoAcumulado(recorridoAcumulado);
			movimiento.getNeumatico().setPresionActual(this.presion);
			if(movimiento.getId().getTipoMovimiento().equals("M")) {
				NeumaticoVehiculo neumaticoVehiculo = new NeumaticoVehiculo();
				NeumaticoVehiculoID neumaticoVehiculoId = new NeumaticoVehiculoID();
				neumaticoVehiculoId.setIdNeumatico(movimiento.getNeumatico().getIdNeumatico());
				neumaticoVehiculoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
				neumaticoVehiculo.setId(neumaticoVehiculoId);
				neumaticoVehiculo.setNeumatico(movimiento.getNeumatico());
				neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
				neumaticoVehiculo.setPosicion(Integer.toString(movimiento.getPosicionFinal()));
				NeumaticoVehiculoDAO.getInstancia().insertarOActualizar(neumaticoVehiculo);
				System.out.println("guardamos el neumatico vehiculo");
				cambiarEstadoMontajeNeumatico(movimiento.getNeumatico(), "M");
				movimiento.getNeumatico().setEstado("M");
			} else {
				movimiento.getNeumatico().setRemanenteActual(this.totalOperacion);
			}
			NeumaticoDAO.getInstancia().insertarOActualizar(movimiento.getNeumatico());
			MovimientoDAO.getInstancia().insertarOActualizar(movimiento);
		}
		mensajes.informativo("Exito", "Operaciones guardadas exitosamente");
	}

	public Movimiento getMovimientoActual() {
		return movimientoActual;
	}

	public void setMovimientoActual(Movimiento movimiento) {
		this.movimientoActual = movimiento;
	}

	public Vehiculo getVehiculoSeleccionado() {
		return vehiculoSeleccionado;
	}

	public void setVehiculoSeleccionado(Vehiculo vehiculoSeleccionado) {
		this.vehiculoSeleccionado = vehiculoSeleccionado;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getDentroCarretera() {
		return dentroCarretera;
	}

	public void setDentroCarretera(String dentroCarretera) {
		this.dentroCarretera = dentroCarretera;
	}	

	public ServiciosVentanaEsquemaEjes getServiciosVentanaEsquemaEjes() {
		return serviciosVentanaEsquemaEjes;
	}

	public void setServiciosVentanaEsquemaEjes(
			ServiciosVentanaEsquemaEjes serviciosVentanaEsquemaEjes) {
		this.serviciosVentanaEsquemaEjes = serviciosVentanaEsquemaEjes;
	}
	
	public String getPrimerNeumatico1() {
		return primerNeumatico1;
	}

	public void setPrimerNeumatico1(String primerNeumatico1) {
		this.primerNeumatico1 = primerNeumatico1;
	}

	public String getPrimerNeumatico2() {
		return primerNeumatico2;
	}

	public void setPrimerNeumatico2(String primerNeumatico2) {
		this.primerNeumatico2 = primerNeumatico2;
	}

	public String getPrimerNeumatico3() {
		return primerNeumatico3;
	}

	public void setPrimerNeumatico3(String primerNeumatico3) {
		this.primerNeumatico3 = primerNeumatico3;
	}

	public String getPrimerNeumatico4() {
		return primerNeumatico4;
	}

	public void setPrimerNeumatico4(String primerNeumatico4) {
		this.primerNeumatico4 = primerNeumatico4;
	}
	public String getPrimerNeumatico5() {
		return primerNeumatico5;
	}

	public void setPrimerNeumatico5(String primerNeumatico5) {
		this.primerNeumatico5 = primerNeumatico5;
	}

	public String getPrimerNeumatico6() {
		return primerNeumatico6;
	}

	public void setPrimerNeumatico6(String primerNeumatico6) {
		this.primerNeumatico6 = primerNeumatico6;
	}

	public String getPrimerNeumatico7() {
		return primerNeumatico7;
	}

	public void setPrimerNeumatico7(String primerNeumatico7) {
		this.primerNeumatico7 = primerNeumatico7;
	}

	public String getPrimerNeumatico8() {
		return primerNeumatico8;
	}

	public void setPrimerNeumatico8(String primerNeumatico8) {
		this.primerNeumatico8 = primerNeumatico8;
	}

	public String getPrimerNeumatico9() {
		return primerNeumatico9;
	}

	public void setPrimerNeumatico9(String primerNeumatico9) {
		this.primerNeumatico9 = primerNeumatico9;
	}

	public String getPrimerNeumatico10() {
		return primerNeumatico10;
	}

	public void setPrimerNeumatico10(String primerNeumatico10) {
		this.primerNeumatico10 = primerNeumatico10;
	}

	public String getPrimerNeumatico11() {
		return primerNeumatico11;
	}

	public void setPrimerNeumatico11(String primerNeumatico11) {
		this.primerNeumatico11 = primerNeumatico11;
	}

	public String getPrimerNeumatico12() {
		return primerNeumatico12;
	}

	public void setPrimerNeumatico12(String primerNeumatico12) {
		this.primerNeumatico12 = primerNeumatico12;
	}

	public String getPrimerNeumatico13() {
		return primerNeumatico13;
	}

	public void setPrimerNeumatico13(String primerNeumatico13) {
		this.primerNeumatico13 = primerNeumatico13;
	}

	public String getPrimerNeumatico14() {
		return primerNeumatico14;
	}

	public void setPrimerNeumatico14(String primerNeumatico14) {
		this.primerNeumatico14 = primerNeumatico14;
	}

	public String getPrimerNeumatico15() {
		return primerNeumatico15;
	}

	public void setPrimerNeumatico15(String primerNeumatico15) {
		this.primerNeumatico15 = primerNeumatico15;
	}

	public String getPrimerNeumatico16() {
		return primerNeumatico16;
	}

	public void setPrimerNeumatico16(String primerNeumatico16) {
		this.primerNeumatico16 = primerNeumatico16;
	}

	public String getPrimerNeumatico17() {
		return primerNeumatico17;
	}

	public void setPrimerNeumatico17(String primerNeumatico17) {
		this.primerNeumatico17 = primerNeumatico17;
	}

	public String getPrimerNeumatico18() {
		return primerNeumatico18;
	}

	public void setPrimerNeumatico18(String primerNeumatico18) {
		this.primerNeumatico18 = primerNeumatico18;
	}

	public String getPrimerNeumatico19() {
		return primerNeumatico19;
	}

	public void setPrimerNeumatico19(String primerNeumatico19) {
		this.primerNeumatico19 = primerNeumatico19;
	}

	public String getPrimerNeumatico20() {
		return primerNeumatico20;
	}

	public void setPrimerNeumatico20(String primerNeumatico20) {
		this.primerNeumatico20 = primerNeumatico20;
	}

	public Neumatico getNeumaticoSeleccionado() {
		return neumaticoSeleccionado;
	}

	public void setNeumaticoSeleccionado(Neumatico neumaticoSeleccionado) {
		this.neumaticoSeleccionado = neumaticoSeleccionado;
	}
	
	public String getTipoNeumatico() {
		return tipoNeumatico;
	}

	public void setTipoNeumatico(String tipoNeumatico) {
		this.tipoNeumatico = tipoNeumatico;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public StreamedContent getStreamedContentImagen() {
		return streamedContentImagen;
	}

	public void setStreamedContentImagen(StreamedContent streamedContentImagen) {
		this.streamedContentImagen = streamedContentImagen;
	}

	public String getNombreRuta() {
		return nombreRuta;
	}

	public void setNombreRuta(String nombreRuta) {
		this.nombreRuta = nombreRuta;
	}

	public RutaVehiculo getRutaVehiculo() {
		return rutaVehiculo;
	}

	public void setRutaVehiculo(RutaVehiculo rutaVehiculo) {
		this.rutaVehiculo = rutaVehiculo;
	}

	public NeumaticoDataModel getNeumaticoDataModel() {
		return neumaticoDataModel;
	}

	public void setNeumaticoDataModel(NeumaticoDataModel neumaticoDataModel) {
		this.neumaticoDataModel = neumaticoDataModel;
	}

	public List<Neumatico> getListaNeumaticos() {
		return listaNeumaticos;
	}

	public void setListaNeumaticos(List<Neumatico> listaNeumaticos) {
		this.listaNeumaticos = listaNeumaticos;
	}

	public int getIdNeumatico() {
		return idNeumatico;
	}

	public void setIdNeumatico(int idNeumatico) {
		this.idNeumatico = idNeumatico;
	}

	public Double getKilometrajeMovimiento() {
		return kilometrajeMovimiento;
	}

	public void setKilometrajeMovimiento(Double kilometrajeMovimiento) {
		this.kilometrajeMovimiento = kilometrajeMovimiento;
	}

	public String getObservacionesMovimiento() {
		return observacionesMovimiento;
	}

	public void setObservacionesMovimiento(String observacionesMovimiento) {
		this.observacionesMovimiento = observacionesMovimiento;
	}

	public MovimientoId getMovimientoActualId() {
		return movimientoActualId;
	}

	public void setMovimientoActualId(MovimientoId movimientoId) {
		this.movimientoActualId = movimientoId;
	}


	public Movimiento getMovimientoSeleccionado() {
		return movimientoSeleccionado;
	}


	public void setMovimientoSeleccionado(Movimiento movimientoSeleccionado) {
		this.movimientoSeleccionado = movimientoSeleccionado;
	}


	public String getCondicionConfirmar() {
		return condicionConfirmar;
	}


	public void setCondicionConfirmar(String condicionConfirmar) {
		this.condicionConfirmar = condicionConfirmar;
	}


	public String getTipoNeumaticoConfirmar() {
		return tipoNeumaticoConfirmar;
	}


	public void setTipoNeumaticoConfirmar(String tipoNeumaticoConfirmar) {
		this.tipoNeumaticoConfirmar = tipoNeumaticoConfirmar;
	}


	public StreamedContent getStreamedContentImagenConfirmar() {
		return streamedContentImagenConfirmar;
	}


	public void setStreamedContentImagenConfirmar(
			StreamedContent streamedContentImagenConfirmar) {
		this.streamedContentImagenConfirmar = streamedContentImagenConfirmar;
	}


	public boolean isDesmontaje() {
		return desmontaje;
	}


	public void setDesmontaje(boolean desmontaje) {
		this.desmontaje = desmontaje;
	}


	public String getNombreRuedaOculto() {
		return nombreRuedaOculto;
	}


	public void setNombreRuedaOculto(String nombreRuedaOculto) {
		this.nombreRuedaOculto = nombreRuedaOculto;
	}


	public Date getFechaActual() {
		return new Date();
	}


	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}


	public Date getFechaTerminar() {
		return fechaTerminar;
	}


	public void setFechaTerminar(Date fechaTerminar) {
		this.fechaTerminar = fechaTerminar;
	}


	public List<NeumaticoVehiculo> getListaNeumaticoVehiculoMontados() {
		return listaNeumaticoVehiculoDeOperacionActual;
	}


	public void setListaNeumaticoVehiculoMontados(
			List<NeumaticoVehiculo> listaNeumaticoVehiculoMontados) {
		this.listaNeumaticoVehiculoDeOperacionActual = listaNeumaticoVehiculoMontados;
	}


	public ServiciosVentanaVehiculo getServiciosVentanaVehiculo() {
		return serviciosVentanaVehiculo;
	}


	public void setServiciosVentanaVehiculo(
			ServiciosVentanaVehiculo serviciosVentanaVehiculo) {
		this.serviciosVentanaVehiculo = serviciosVentanaVehiculo;
	}


	public boolean isNeumatico1Previo() {
		return neumatico1Previo;
	}


	public void setNeumatico1Previo(boolean neumatico1Previo) {
		this.neumatico1Previo = neumatico1Previo;
	}


	public boolean isNeumatico2Previo() {
		return neumatico2Previo;
	}


	public void setNeumatico2Previo(boolean neumatico2Previo) {
		this.neumatico2Previo = neumatico2Previo;
	}


	public boolean isNeumatico3Previo() {
		return neumatico3Previo;
	}


	public void setNeumatico3Previo(boolean neumatico3Previo) {
		this.neumatico3Previo = neumatico3Previo;
	}


	public boolean isNeumatico4Previo() {
		return neumatico4Previo;
	}


	public void setNeumatico4Previo(boolean neumatico4Previo) {
		this.neumatico4Previo = neumatico4Previo;
	}


	public boolean isNeumatico5Previo() {
		return neumatico5Previo;
	}


	public void setNeumatico5Previo(boolean neumatico5Previo) {
		this.neumatico5Previo = neumatico5Previo;
	}


	public boolean isNeumatico6Previo() {
		return neumatico6Previo;
	}


	public void setNeumatico6Previo(boolean neumatico6Previo) {
		this.neumatico6Previo = neumatico6Previo;
	}


	public boolean isNeumatico7Previo() {
		return neumatico7Previo;
	}


	public void setNeumatico7Previo(boolean neumatico7Previo) {
		this.neumatico7Previo = neumatico7Previo;
	}


	public boolean isNeumatico8Previo() {
		return neumatico8Previo;
	}


	public void setNeumatico8Previo(boolean neumatico8Previo) {
		this.neumatico8Previo = neumatico8Previo;
	}


	public boolean isNeumatico9Previo() {
		return neumatico9Previo;
	}


	public void setNeumatico9Previo(boolean neumatico9Previo) {
		this.neumatico9Previo = neumatico9Previo;
	}


	public boolean isNeumatico10Previo() {
		return neumatico10Previo;
	}


	public void setNeumatico10Previo(boolean neumatico10Previo) {
		this.neumatico10Previo = neumatico10Previo;
	}


	public boolean isNeumatico11Previo() {
		return neumatico11Previo;
	}


	public void setNeumatico11Previo(boolean neumatico11Previo) {
		this.neumatico11Previo = neumatico11Previo;
	}


	public boolean isNeumatico12Previo() {
		return neumatico12Previo;
	}


	public void setNeumatico12Previo(boolean neumatico12Previo) {
		this.neumatico12Previo = neumatico12Previo;
	}


	public boolean isNeumatico13Previo() {
		return neumatico13Previo;
	}


	public void setNeumatico13Previo(boolean neumatico13Previo) {
		this.neumatico13Previo = neumatico13Previo;
	}


	public boolean isNeumatico14Previo() {
		return neumatico14Previo;
	}


	public void setNeumatico14Previo(boolean neumatico14Previo) {
		this.neumatico14Previo = neumatico14Previo;
	}


	public boolean isNeumatico15Previo() {
		return neumatico15Previo;
	}


	public void setNeumatico15Previo(boolean neumatico15Previo) {
		this.neumatico15Previo = neumatico15Previo;
	}


	public boolean isNeumatico16Previo() {
		return neumatico16Previo;
	}


	public void setNeumatico16Previo(boolean neumatico16Previo) {
		this.neumatico16Previo = neumatico16Previo;
	}


	public boolean isNeumatico17Previo() {
		return neumatico17Previo;
	}


	public void setNeumatico17Previo(boolean neumatico17Previo) {
		this.neumatico17Previo = neumatico17Previo;
	}


	public boolean isNeumatico18Previo() {
		return neumatico18Previo;
	}


	public void setNeumatico18Previo(boolean neumatico18Previo) {
		this.neumatico18Previo = neumatico18Previo;
	}


	public boolean isNeumatico19Previo() {
		return neumatico19Previo;
	}


	public void setNeumatico19Previo(boolean neumatico19Previo) {
		this.neumatico19Previo = neumatico19Previo;
	}


	public boolean isNeumatico20Previo() {
		return neumatico20Previo;
	}


	public void setNeumatico20Previo(boolean neumatico20Previo) {
		this.neumatico20Previo = neumatico20Previo;
	}


	public boolean isNeumatico21Previo() {
		return neumatico21Previo;
	}


	public void setNeumatico21Previo(boolean neumatico21Previo) {
		this.neumatico21Previo = neumatico21Previo;
	}


	public boolean isNeumatico22Previo() {
		return neumatico22Previo;
	}


	public void setNeumatico22Previo(boolean neumatico22Previo) {
		this.neumatico22Previo = neumatico22Previo;
	}


	public int getCantidadMaximaNeumaticos() {
		return cantidadMaximaNeumaticos;
	}


	public void setCantidadMaximaNeumaticos(int cantidadMaximaNeumaticos) {
		this.cantidadMaximaNeumaticos = cantidadMaximaNeumaticos;
	}


	public String getRemanenteSuperiorA() {
		return remanenteSuperiorA;
	}

	public void setRemanenteSuperiorA(String remanenteSuperiorA) {
		this.remanenteSuperiorA = remanenteSuperiorA;
	}

	public String getRemanenteSuperiorB() {
		return remanenteSuperiorB;
	}

	public void setRemanenteSuperiorB(String remanenteSuperiorB) {
		this.remanenteSuperiorB = remanenteSuperiorB;
	}

	public String getRemanenteSuperiorC() {
		return remanenteSuperiorC;
	}

	public void setRemanenteSuperiorC(String remanenteSuperiorC) {
		this.remanenteSuperiorC = remanenteSuperiorC;
	}

	public String getRemanenteSuperiorD() {
		return remanenteSuperiorD;
	}

	public void setRemanenteSuperiorD(String remanenteSuperiorD) {
		this.remanenteSuperiorD = remanenteSuperiorD;
	}

	public String getRemanenteIzquierdoA() {
		return remanenteIzquierdoA;
	}

	public void setRemanenteIzquierdoA(String remanenteIzquierdoA) {
		this.remanenteIzquierdoA = remanenteIzquierdoA;
	}

	public String getRemanenteIzquierdoB() {
		return remanenteIzquierdoB;
	}

	public void setRemanenteIzquierdoB(String remanenteIzquierdoB) {
		this.remanenteIzquierdoB = remanenteIzquierdoB;
	}

	public String getRemanenteIzquierdoC() {
		return remanenteIzquierdoC;
	}

	public void setRemanenteIzquierdoC(String remanenteIzquierdoC) {
		this.remanenteIzquierdoC = remanenteIzquierdoC;
	}

	public String getRemanenteIzquierdoD() {
		return remanenteIzquierdoD;
	}

	public void setRemanenteIzquierdoD(String remanenteIzquierdoD) {
		this.remanenteIzquierdoD = remanenteIzquierdoD;
	}

	public String getRemanenteInferiorA() {
		return remanenteInferiorA;
	}

	public void setRemanenteInferiorA(String remanenteInferiorA) {
		this.remanenteInferiorA = remanenteInferiorA;
	}

	public String getRemanenteInferiorB() {
		return remanenteInferiorB;
	}

	public void setRemanenteInferiorB(String remanenteInferiorB) {
		this.remanenteInferiorB = remanenteInferiorB;
	}

	public String getRemanenteInferiorC() {
		return remanenteInferiorC;
	}

	public void setRemanenteInferiorC(String remanenteInferiorC) {
		this.remanenteInferiorC = remanenteInferiorC;
	}

	public String getRemanenteInferiorD() {
		return remanenteInferiorD;
	}

	public void setRemanenteInferiorD(String remanenteInferiorD) {
		this.remanenteInferiorD = remanenteInferiorD;
	}

	public String getRemanenteDerechaA() {
		return remanenteDerechaA;
	}

	public void setRemanenteDerechaA(String remanenteDerechaA) {
		this.remanenteDerechaA = remanenteDerechaA;
	}

	public String getRemanenteDerechaB() {
		return remanenteDerechaB;
	}

	public void setRemanenteDerechaB(String remanenteDerechaB) {
		this.remanenteDerechaB = remanenteDerechaB;
	}

	public String getRemanenteDerechaC() {
		return remanenteDerechaC;
	}

	public void setRemanenteDerechaC(String remanenteDerechaC) {
		this.remanenteDerechaC = remanenteDerechaC;
	}

	public String getRemanenteDerechaD() {
		return remanenteDerechaD;
	}

	public void setRemanenteDerechaD(String remanenteDerechaD) {
		this.remanenteDerechaD = remanenteDerechaD;
	}

	public Double getTotalSuperior() {
		return totalSuperior;
	}


	public void setTotalSuperior(Double totalSuperior) {
		this.totalSuperior = totalSuperior;
	}


	public Double getTotalInferior() {
		return totalInferior;
	}


	public void setTotalInferior(Double totalInferior) {
		this.totalInferior = totalInferior;
	}


	public Double getTotalIzquierdo() {
		return totalIzquierdo;
	}


	public void setTotalIzquierdo(Double totalIzquierdo) {
		this.totalIzquierdo = totalIzquierdo;
	}


	public Double getTotalDerecha() {
		return totalDerecha;
	}


	public void setTotalDerecha(Double totalDerecha) {
		this.totalDerecha = totalDerecha;
	}


	public Double getTotalOperacion() {
		return totalOperacion;
	}


	public void setTotalOperacion(Double totalOperacion) {
		this.totalOperacion = totalOperacion;
	}


	public Date getFechaMontaje() {
		return fechaMontaje;
	}


	public void setFechaMontaje(Date fechaMontaje) {
		this.fechaMontaje = fechaMontaje;
	}


	public boolean isDisabledBtnMontar() {
		return disabledBtnMontar;
	}


	public void setDisabledBtnMontar(boolean disabledBtnMontar) {
		this.disabledBtnMontar = disabledBtnMontar;
	}


	public Double getPresion() {
		return presion;
	}


	public void setPresion(Double presion) {
		this.presion = presion;
	}


	public int getPosicionActual() {
		return posicionActual;
	}


	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}


	public List<Neumatico> getListaAlmacenAux() {
		return listaAlmacenAux;
	}


	public void setListaAlmacenAux(List<Neumatico> listaAlmacenAux) {
		this.listaAlmacenAux = listaAlmacenAux;
	}

	public String getNeumatico11Image() {
		return neumatico11Image;
	}

	public void setNeumatico11Image(String neumatico11Image) {
		this.neumatico11Image = neumatico11Image;
	}

	public String getNeumatico12Image() {
		return neumatico12Image;
	}

	public void setNeumatico12Image(String neumatico12Image) {
		this.neumatico12Image = neumatico12Image;
	}

	public String getNeumatico13Image() {
		return neumatico13Image;
	}

	public void setNeumatico13Image(String neumatico13Image) {
		this.neumatico13Image = neumatico13Image;
	}

	public String getNeumatico14Image() {
		return neumatico14Image;
	}

	public void setNeumatico14Image(String neumatico14Image) {
		this.neumatico14Image = neumatico14Image;
	}

	public String getNeumatico21Image() {
		return neumatico21Image;
	}

	public void setNeumatico21Image(String neumatico21Image) {
		this.neumatico21Image = neumatico21Image;
	}

	public String getNeumatico22Image() {
		return neumatico22Image;
	}

	public void setNeumatico22Image(String neumatico22Image) {
		this.neumatico22Image = neumatico22Image;
	}

	public String getNeumatico23Image() {
		return neumatico23Image;
	}

	public void setNeumatico23Image(String neumatico23Image) {
		this.neumatico23Image = neumatico23Image;
	}

	public String getNeumatico24Image() {
		return neumatico24Image;
	}

	public void setNeumatico24Image(String neumatico24Image) {
		this.neumatico24Image = neumatico24Image;
	}

	public String getNeumatico31Image() {
		return neumatico31Image;
	}

	public void setNeumatico31Image(String neumatico31Image) {
		this.neumatico31Image = neumatico31Image;
	}

	public String getNeumatico32Image() {
		return neumatico32Image;
	}

	public void setNeumatico32Image(String neumatico32Image) {
		this.neumatico32Image = neumatico32Image;
	}

	public String getNeumatico33Image() {
		return neumatico33Image;
	}

	public void setNeumatico33Image(String neumatico33Image) {
		this.neumatico33Image = neumatico33Image;
	}

	public String getNeumatico34Image() {
		return neumatico34Image;
	}

	public void setNeumatico34Image(String neumatico34Image) {
		this.neumatico34Image = neumatico34Image;
	}

	public String getNeumatico41Image() {
		return neumatico41Image;
	}

	public void setNeumatico41Image(String neumatico41Image) {
		this.neumatico41Image = neumatico41Image;
	}

	public String getNeumatico42Image() {
		return neumatico42Image;
	}

	public void setNeumatico42Image(String neumatico42Image) {
		this.neumatico42Image = neumatico42Image;
	}

	public String getNeumatico43Image() {
		return neumatico43Image;
	}

	public void setNeumatico43Image(String neumatico43Image) {
		this.neumatico43Image = neumatico43Image;
	}

	public String getNeumatico44Image() {
		return neumatico44Image;
	}

	public void setNeumatico44Image(String neumatico44Image) {
		this.neumatico44Image = neumatico44Image;
	}

	public String getNeumatico51Image() {
		return neumatico51Image;
	}

	public void setNeumatico51Image(String neumatico51Image) {
		this.neumatico51Image = neumatico51Image;
	}

	public String getNeumatico52Image() {
		return neumatico52Image;
	}

	public void setNeumatico52Image(String neumatico52Image) {
		this.neumatico52Image = neumatico52Image;
	}

	public String getNeumatico53Image() {
		return neumatico53Image;
	}

	public void setNeumatico53Image(String neumatico53Image) {
		this.neumatico53Image = neumatico53Image;
	}

	public String getNeumatico54Image() {
		return neumatico54Image;
	}

	public void setNeumatico54Image(String neumatico54Image) {
		this.neumatico54Image = neumatico54Image;
	}

	public String getNeumaticoRepIzqImage() {
		return neumaticoRepIzqImage;
	}

	public void setNeumaticoRepIzqImage(String neumaticoRepIzqImage) {
		this.neumaticoRepIzqImage = neumaticoRepIzqImage;
	}

	public String getNeumaticoRepDerImage() {
		return neumaticoRepDerImage;
	}

	public void setNeumaticoRepDerImage(String neumaticoRepDerImage) {
		this.neumaticoRepDerImage = neumaticoRepDerImage;
	}

	public boolean isNeumatico11DragDisabled() {
		return neumatico11DragDisabled;
	}

	public void setNeumatico11DragDisabled(boolean neumatico11DragDisabled) {
		this.neumatico11DragDisabled = neumatico11DragDisabled;
	}

	public boolean isNeumatico12DragDisabled() {
		return neumatico12DragDisabled;
	}

	public void setNeumatico12DragDisabled(boolean neumatico12DragDisabled) {
		this.neumatico12DragDisabled = neumatico12DragDisabled;
	}

	public boolean isNeumatico13DragDisabled() {
		return neumatico13DragDisabled;
	}

	public void setNeumatico13DragDisabled(boolean neumatico13DragDisabled) {
		this.neumatico13DragDisabled = neumatico13DragDisabled;
	}

	public boolean isNeumatico14DragDisabled() {
		return neumatico14DragDisabled;
	}

	public void setNeumatico14DragDisabled(boolean neumatico14DragDisabled) {
		this.neumatico14DragDisabled = neumatico14DragDisabled;
	}

	public boolean isNeumatico21DragDisabled() {
		return neumatico21DragDisabled;
	}

	public void setNeumatico21DragDisabled(boolean neumatico21DragDisabled) {
		this.neumatico21DragDisabled = neumatico21DragDisabled;
	}

	public boolean isNeumatico22DragDisabled() {
		return neumatico22DragDisabled;
	}

	public void setNeumatico22DragDisabled(boolean neumatico22DragDisabled) {
		this.neumatico22DragDisabled = neumatico22DragDisabled;
	}

	public boolean isNeumatico23DragDisabled() {
		return neumatico23DragDisabled;
	}

	public void setNeumatico23DragDisabled(boolean neumatico23DragDisabled) {
		this.neumatico23DragDisabled = neumatico23DragDisabled;
	}

	public boolean isNeumatico24DragDisabled() {
		return neumatico24DragDisabled;
	}

	public void setNeumatico24DragDisabled(boolean neumatico24DragDisabled) {
		this.neumatico24DragDisabled = neumatico24DragDisabled;
	}

	public boolean isNeumatico31DragDisabled() {
		return neumatico31DragDisabled;
	}

	public void setNeumatico31DragDisabled(boolean neumatico31DragDisabled) {
		this.neumatico31DragDisabled = neumatico31DragDisabled;
	}

	public boolean isNeumatico32DragDisabled() {
		return neumatico32DragDisabled;
	}

	public void setNeumatico32DragDisabled(boolean neumatico32DragDisabled) {
		this.neumatico32DragDisabled = neumatico32DragDisabled;
	}

	public boolean isNeumatico33DragDisabled() {
		return neumatico33DragDisabled;
	}

	public void setNeumatico33DragDisabled(boolean neumatico33DragDisabled) {
		this.neumatico33DragDisabled = neumatico33DragDisabled;
	}

	public boolean isNeumatico34DragDisabled() {
		return neumatico34DragDisabled;
	}

	public void setNeumatico34DragDisabled(boolean neumatico34DragDisabled) {
		this.neumatico34DragDisabled = neumatico34DragDisabled;
	}

	public boolean isNeumatico41DragDisabled() {
		return neumatico41DragDisabled;
	}

	public void setNeumatico41DragDisabled(boolean neumatico41DragDisabled) {
		this.neumatico41DragDisabled = neumatico41DragDisabled;
	}

	public boolean isNeumatico42DragDisabled() {
		return neumatico42DragDisabled;
	}

	public void setNeumatico42DragDisabled(boolean neumatico42DragDisabled) {
		this.neumatico42DragDisabled = neumatico42DragDisabled;
	}

	public boolean isNeumatico43DragDisabled() {
		return neumatico43DragDisabled;
	}

	public void setNeumatico43DragDisabled(boolean neumatico43DragDisabled) {
		this.neumatico43DragDisabled = neumatico43DragDisabled;
	}

	public boolean isNeumatico44DragDisabled() {
		return neumatico44DragDisabled;
	}

	public void setNeumatico44DragDisabled(boolean neumatico44DragDisabled) {
		this.neumatico44DragDisabled = neumatico44DragDisabled;
	}

	public boolean isNeumatico51DragDisabled() {
		return neumatico51DragDisabled;
	}

	public void setNeumatico51DragDisabled(boolean neumatico51DragDisabled) {
		this.neumatico51DragDisabled = neumatico51DragDisabled;
	}

	public boolean isNeumatico52DragDisabled() {
		return neumatico52DragDisabled;
	}

	public void setNeumatico52DragDisabled(boolean neumatico52DragDisabled) {
		this.neumatico52DragDisabled = neumatico52DragDisabled;
	}

	public boolean isNeumatico53DragDisabled() {
		return neumatico53DragDisabled;
	}

	public void setNeumatico53DragDisabled(boolean neumatico53DragDisabled) {
		this.neumatico53DragDisabled = neumatico53DragDisabled;
	}

	public boolean isNeumatico54DragDisabled() {
		return neumatico54DragDisabled;
	}

	public void setNeumatico54DragDisabled(boolean neumatico54DragDisabled) {
		this.neumatico54DragDisabled = neumatico54DragDisabled;
	}

	public boolean isNeumaticoR21DragDisabled() {
		return neumaticoR21DragDisabled;
	}

	public void setNeumaticoR21DragDisabled(boolean neumaticoR21DragDisabled) {
		this.neumaticoR21DragDisabled = neumaticoR21DragDisabled;
	}

	public boolean isNeumaticoR22DragDisabled() {
		return neumaticoR22DragDisabled;
	}

	public void setNeumaticoR22DragDisabled(boolean neumaticoR22DragDisabled) {
		this.neumaticoR22DragDisabled = neumaticoR22DragDisabled;
	}

	public boolean isNeumatico11Render() {
		return neumatico11Render;
	}

	public void setNeumatico11Render(boolean neumatico11Render) {
		this.neumatico11Render = neumatico11Render;
	}

	public boolean isNeumatico12Render() {
		return neumatico12Render;
	}

	public void setNeumatico12Render(boolean neumatico12Render) {
		this.neumatico12Render = neumatico12Render;
	}

	public boolean isNeumatico13Render() {
		return neumatico13Render;
	}

	public void setNeumatico13Render(boolean neumatico13Render) {
		this.neumatico13Render = neumatico13Render;
	}

	public boolean isNeumatico14Render() {
		return neumatico14Render;
	}

	public void setNeumatico14Render(boolean neumatico14Render) {
		this.neumatico14Render = neumatico14Render;
	}

	public boolean isNeumatico21Render() {
		return neumatico21Render;
	}

	public void setNeumatico21Render(boolean neumatico21Render) {
		this.neumatico21Render = neumatico21Render;
	}

	public boolean isNeumatico22Render() {
		return neumatico22Render;
	}

	public void setNeumatico22Render(boolean neumatico22Render) {
		this.neumatico22Render = neumatico22Render;
	}

	public boolean isNeumatico23Render() {
		return neumatico23Render;
	}

	public void setNeumatico23Render(boolean neumatico23Render) {
		this.neumatico23Render = neumatico23Render;
	}

	public boolean isNeumatico24Render() {
		return neumatico24Render;
	}

	public void setNeumatico24Render(boolean neumatico24Render) {
		this.neumatico24Render = neumatico24Render;
	}

	public boolean isNeumatico31Render() {
		return neumatico31Render;
	}

	public void setNeumatico31Render(boolean neumatico31Render) {
		this.neumatico31Render = neumatico31Render;
	}

	public boolean isNeumatico32Render() {
		return neumatico32Render;
	}

	public void setNeumatico32Render(boolean neumatico32Render) {
		this.neumatico32Render = neumatico32Render;
	}

	public boolean isNeumatico33Render() {
		return neumatico33Render;
	}

	public void setNeumatico33Render(boolean neumatico33Render) {
		this.neumatico33Render = neumatico33Render;
	}

	public boolean isNeumatico34Render() {
		return neumatico34Render;
	}

	public void setNeumatico34Render(boolean neumatico34Render) {
		this.neumatico34Render = neumatico34Render;
	}

	public boolean isNeumatico41Render() {
		return neumatico41Render;
	}

	public void setNeumatico41Render(boolean neumatico41Render) {
		this.neumatico41Render = neumatico41Render;
	}

	public boolean isNeumatico42Render() {
		return neumatico42Render;
	}

	public void setNeumatico42Render(boolean neumatico42Render) {
		this.neumatico42Render = neumatico42Render;
	}

	public boolean isNeumatico43Render() {
		return neumatico43Render;
	}

	public void setNeumatico43Render(boolean neumatico43Render) {
		this.neumatico43Render = neumatico43Render;
	}

	public boolean isNeumatico44Render() {
		return neumatico44Render;
	}

	public void setNeumatico44Render(boolean neumatico44Render) {
		this.neumatico44Render = neumatico44Render;
	}

	public boolean isNeumatico51Render() {
		return neumatico51Render;
	}

	public void setNeumatico51Render(boolean neumatico51Render) {
		this.neumatico51Render = neumatico51Render;
	}

	public boolean isNeumatico52Render() {
		return neumatico52Render;
	}

	public void setNeumatico52Render(boolean neumatico52Render) {
		this.neumatico52Render = neumatico52Render;
	}

	public boolean isNeumatico53Render() {
		return neumatico53Render;
	}

	public void setNeumatico53Render(boolean neumatico53Render) {
		this.neumatico53Render = neumatico53Render;
	}

	public boolean isNeumatico54Render() {
		return neumatico54Render;
	}

	public void setNeumatico54Render(boolean neumatico54Render) {
		this.neumatico54Render = neumatico54Render;
	}

	public boolean isNeumaticoRepIzqRender() {
		return neumaticoRepIzqRender;
	}

	public void setNeumaticoRepIzqRender(boolean neumaticoRepIzqRender) {
		this.neumaticoRepIzqRender = neumaticoRepIzqRender;
	}

	public boolean isNeumaticoRepDerRender() {
		return neumaticoRepDerRender;
	}

	public void setNeumaticoRepDerRender(boolean neumaticoRepDerRender) {
		this.neumaticoRepDerRender = neumaticoRepDerRender;
	}

	public List<String> getListaPosicionesDisponibles() {
		return listaPosicionesDisponibles;
	}

	public void setListaPosicionesDisponibles(
			List<String> listaPosicionesDisponibles) {
		this.listaPosicionesDisponibles = listaPosicionesDisponibles;
	}

	public String getPosicionSeleccionada() {
		return posicionSeleccionada;
	}

	public void setPosicionSeleccionada(String posicionSeleccionada) {
		this.posicionSeleccionada = posicionSeleccionada;
	}

	public String getEje1Image() {
		return eje1Image;
	}

	public void setEje1Image(String eje1Image) {
		this.eje1Image = eje1Image;
	}

	public String getEje2Image() {
		return eje2Image;
	}

	public void setEje2Image(String eje2Image) {
		this.eje2Image = eje2Image;
	}

	public String getEje3Image() {
		return eje3Image;
	}

	public void setEje3Image(String eje3Image) {
		this.eje3Image = eje3Image;
	}

	public String getEje4Image() {
		return eje4Image;
	}

	public void setEje4Image(String eje4Image) {
		this.eje4Image = eje4Image;
	}

	public String getEje5Image() {
		return eje5Image;
	}

	public void setEje5Image(String eje5Image) {
		this.eje5Image = eje5Image;
	}

	public NeumaticoDataModel getNeumaticoDataModelListos() {
		return neumaticoDataModelListos;
	}

	public void setNeumaticoDataModelListos(
			NeumaticoDataModel neumaticoDataModelListos) {
		this.neumaticoDataModelListos = neumaticoDataModelListos;
	}

	public boolean isRegresarDisabled() {
		return regresarDisabled;
	}

	public void setRegresarDisabled(boolean regresarDisabled) {
		this.regresarDisabled = regresarDisabled;
	}

	public Double getRecorridoAcumulado() {
		return recorridoAcumulado;
	}

	public void setRecorridoAcumulado(Double recorridoAcumulado) {
		this.recorridoAcumulado = recorridoAcumulado;
	}

	public TipoDesgaste getTipoDesgasteSeleccionado() {
		return tipoDesgasteSeleccionado;
	}

	public void setTipoDesgasteSeleccionado(TipoDesgaste tipoDesgasteSeleccionado) {
		this.tipoDesgasteSeleccionado = tipoDesgasteSeleccionado;
	}

	public Neumatico getNeumaticoSeleccionadoAlmacen() {
		return neumaticoSeleccionadoAlmacen;
	}

	public void setNeumaticoSeleccionadoAlmacen(
			Neumatico neumaticoSeleccionadoAlmacen) {
		this.neumaticoSeleccionadoAlmacen = neumaticoSeleccionadoAlmacen;
	}

	public boolean[] getUbicacionMedicionesArray() {
		return ubicacionMedicionesArray;
	}

	public void setUbicacionMedicionesArray(boolean[] ubicacionMedicionesArray) {
		this.ubicacionMedicionesArray = ubicacionMedicionesArray;
	}

	public boolean isMedicionSuperior() {
		return medicionSuperior;
	}

	public void setMedicionSuperior(boolean medicionSuperior) {
		this.medicionSuperior = medicionSuperior;
	}

	public boolean isMedicionInferior() {
		return medicionInferior;
	}

	public void setMedicionInferior(boolean medicionInferior) {
		this.medicionInferior = medicionInferior;
	}

	public boolean isMedicionDerecha() {
		return medicionDerecha;
	}

	public void setMedicionDerecha(boolean medicionDerecha) {
		this.medicionDerecha = medicionDerecha;
	}

	public boolean isMedicionIzquierda() {
		return medicionIzquierda;
	}

	public void setMedicionIzquierda(boolean medicionIzquierda) {
		this.medicionIzquierda = medicionIzquierda;
	}

	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}

	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public String getOperacionActual() {
		return operacionActual;
	}

	public void setOperacionActual(String operacionActual) {
		this.operacionActual = operacionActual;
	}

	public boolean isExisteError() {
		return existeErrorRemIzq;
	}

	public void setExisteError(boolean existeError) {
		this.existeErrorRemIzq = existeError;
	}

	public boolean isCollapsedSuperior() {
		return collapsedSuperior;
	}

	public void setCollapsedSuperior(boolean collapsedSuperior) {
		this.collapsedSuperior = collapsedSuperior;
	}

	public boolean isCollapsedInferior() {
		return collapsedInferior;
	}

	public void setCollapsedInferior(boolean collapsedInferior) {
		this.collapsedInferior = collapsedInferior;
	}

	public boolean isCollapsedDerecho() {
		return collapsedDerecho;
	}

	public void setCollapsedDerecho(boolean collapsedDerecho) {
		this.collapsedDerecho = collapsedDerecho;
	}

	public boolean isCollapsedIzquierdo() {
		return collapsedIzquierdo;
	}

	public void setCollapsedIzquierdo(boolean collapsedIzquierdo) {
		this.collapsedIzquierdo = collapsedIzquierdo;
	}

	public Neumatico getNeumaticoSeleccionadoMontaje() {
		return neumaticoSeleccionadoMontaje;
	}

	public void setNeumaticoSeleccionadoMontaje(
			Neumatico neumaticoSeleccionadoMontaje) {
		this.neumaticoSeleccionadoMontaje = neumaticoSeleccionadoMontaje;
	}
	
	public boolean isDisabledDatosMontaje() {
		return disabledDatosMontaje;
	}

	public void setDisabledDatosMontaje(boolean disabledDatosMontaje) {
		this.disabledDatosMontaje = disabledDatosMontaje;
	}

	public boolean isRenderedConsejos() {
		return renderedConsejos;
	}

	public void setRenderedConsejos(boolean renderedConsejos) {
		this.renderedConsejos = renderedConsejos;
	}

	public String getPosicionInicial() {
		return posicionInicial;
	}

	public void setPosicionInicial(String posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	public List<Neumatico> getListaNeumaticosMontados() {
		return listaNeumaticosMontados;
	}

	public void setListaNeumaticosMontados(List<Neumatico> listaNeumaticosMontados) {
		this.listaNeumaticosMontados = listaNeumaticosMontados;
	}
	
	public boolean isDisabledBtnDevolver() {
		return disabledBtnDevolver;
	}

	public void setDisabledBtnDevolver(boolean disabledBtnDevolver) {
		this.disabledBtnDevolver = disabledBtnDevolver;
	}
}

/*
  <h:panelGrid id="panelDatosOperacion" columns="3">
			    	
			    	<h:outputLabel value="Fecha: " for="fecha" />
			    	<p:calendar id="fecha" value="#{serviciosVentanaMovimientos.movimientoActualId.fecha}" showOn="button" maxdate="#{serviciosVentanaMovimientos.fechaActual}"/>
			   		<h:outputLabel value="" />
			    	
			    	<h:outputLabel value="Último Kilometraje:" for="kilometrajeAnterior"/>
					<h:inputText id="kilometrajeAnterior" label="KilometrajeAnterior" value="#{serviciosVentanaMovimientos.vehiculoSeleccionado.kilometraje}" disabled="true">
			   			<f:validateLength maximum="20"/>
			   		</h:inputText>
			   		<h:outputLabel value="Km" style="font-weight: bold;"/>
			    	
			    	<h:outputLabel value="Kilometraje Actual:" for="kilometrajeMovimientos"/>
					<h:inputText id="kilometrajeMovimientos" label="KilometrajeMontaje" value="#{serviciosVentanaMovimientos.kilometrajeMovimiento}" >
			   			<f:validateLength maximum="20"/>
				   		<f:ajax event="change" listener="#{serviciosVentanaMovimientos.calcularRecorridoAcumulado}" render=":dialogos:recorridoAcumulado"/>
			   		</h:inputText>
			   		<h:outputLabel value="Km" style="font-weight: bold;"/>
			    </h:panelGrid>
			    
			    *
			    *
			    *
			    
						   	
				    		<h:outputLabel value="Recorrido Acumulado:" for="recorridoAcumulado"/>
				    		<h:inputText id="recorridoAcumulado" value="#{serviciosVentanaMovimientos.recorridoAcumulado}" disabled="true"/>
				    		<h:outputLabel value="Km"  style="font-weight: bold;"/>
			    *
			    */
