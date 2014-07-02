package cauca.scsn.modelo.servicios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.DefaultRequestContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.MovimientoDAO;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.NeumaticoVehiculoDAO;
import cauca.scsn.modelo.dao.RutaVehiculoDAO;
import cauca.scsn.modelo.dao.VehiculoDAO;
import cauca.scsn.modelo.datamodel.NeumaticoDataModel;
import cauca.scsn.modelo.datamodel.VehiculoDataModel;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.Movimiento;
import cauca.scsn.modelo.entidad.Neumatico;
import cauca.scsn.modelo.entidad.NeumaticoVehiculo;
import cauca.scsn.modelo.entidad.PosicionesVehiculo;
import cauca.scsn.modelo.entidad.RutaVehiculo;
import cauca.scsn.modelo.entidad.Vehiculo;
import cauca.scsn.modelo.entidad.id.MovimientoId;
import cauca.scsn.modelo.entidad.id.NeumaticoVehiculoID;
import cauca.scsn.modelo.entidad.id.RutaVehiculoId;

@ManagedBean
@SessionScoped
public class ServiciosVentanaRotacion {
	
	private Movimiento					movimiento;
	private MovimientoId 				movimientoId;
	private Movimiento 					movimientoSeleccionado;
	private Movimiento 					movimientoPrevio = new Movimiento();
	private Vehiculo					vehiculoSeleccionado;
	private RutaVehiculo				rutaVehiculo;
	private Empresa						empresa;
	private Neumatico					neumaticoSeleccionado;
	private NeumaticoVehiculo 			neumaticoVehiculo = new NeumaticoVehiculo();
	private NeumaticoVehiculoID 		neumaticoVehiculoId = new NeumaticoVehiculoID();
	private NeumaticoDataModel			neumaticoDataModel;
	private ValidadorBean 				validador = new ValidadorBean();
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
	private List<Movimiento> 			listaMovimientosPrevio;
	private ActionEvent 				eventoCancelar;
	private boolean						desmontaje = false;
	private boolean						existeMontados = false;
	private boolean 					montajePrevio = false;
	private boolean						disabledBtnRotar = true;
	private Date						fechaActual;
	private Date						fechaMontaje;
	private String						fechaTerminar;
	private String						condicionConfirmar;
	private	String						tipoNeumaticoConfirmar;
	private String						nombreRuedaOculto;
	private String						observacionesMovimiento;
	private String						tipoNeumatico;
	private String						condicion;
	private String						nombreRuta;
	private String						dentroCarretera;
	private Double						kilometrajeMovimiento;
	private Double						presionRotacion;
	
	private HashMap<String, NeumaticoVehiculo> mapaRelacionalNeumaticosOperacion = new HashMap<String, NeumaticoVehiculo>();
	
	private Double			remanenteSuperiorA;
	private Double			remanenteSuperiorB;
	private Double			remanenteSuperiorC;
	private Double			remanenteSuperiorD;
	
	private Double			remanenteIzquierdoA;
	private Double			remanenteIzquierdoB;
	private Double			remanenteIzquierdoC;
	private Double			remanenteIzquierdoD;

	private Double			remanenteInferiorA;
	private Double			remanenteInferiorB;
	private Double			remanenteInferiorC;
	private Double			remanenteInferiorD;

	private Double			remanenteDerechaA;
	private Double			remanenteDerechaB;
	private Double			remanenteDerechaC;
	private Double			remanenteDerechaD;
	
	private Double			totalSuperior;
	private Double			totalInferior;
	private Double			totalIzquierdo;
	private Double			totalDerecha;
	private Double			totalOperacion;
	
	private String			neumatico1;
	private String			neumatico2;
	private String			neumatico3;
	private String			neumatico4;
	private String			neumatico5;
	private String			neumatico6;
	private String			neumatico7;
	private String			neumatico8;
	private String			neumatico9;
	private String			neumatico10;
	private String			neumatico11;
	private String			neumatico12;
	private String			neumatico13;
	private String			neumatico14;
	private String			neumatico15;
	private String			neumatico16;
	private String			neumatico17;
	private String			neumatico18;
	private String			neumatico19;
	private String			neumatico20;
	private String			neumatico21;
	private String			neumatico22;
	
	private boolean			neumatico11Disabled;
	private boolean			neumatico12Disabled;
	private boolean			neumatico13Disabled;
	private boolean			neumatico14Disabled;
	private boolean			neumatico21Disabled;
	private boolean			neumatico22Disabled;
	private boolean			neumatico23Disabled;
	private boolean			neumatico24Disabled;
	private boolean			neumatico31Disabled;
	private boolean			neumatico32Disabled;
	private boolean			neumatico33Disabled;
	private boolean			neumatico34Disabled;
	private boolean			neumatico41Disabled;
	private boolean			neumatico42Disabled;
	private boolean			neumatico43Disabled;
	private boolean			neumatico44Disabled;
	private boolean			neumatico51Disabled;
	private boolean			neumatico52Disabled;
	private boolean			neumatico53Disabled;
	private boolean			neumatico54Disabled;
	private boolean			neumaticoRepIzqDisabled;
	private boolean			neumaticoRepDerDisabled;
	
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
	
	private ServiciosVentanaEsquemaEjes	serviciosVentanaEsquemaEjes;
	private ServiciosVentanaVehiculo	serviciosVentanaVehiculo;
	private ControladorMensajes			mensajes;
	
	
	
	public ServiciosVentanaRotacion() {
		super();
		movimientoSeleccionado = new Movimiento();
		serviciosVentanaEsquemaEjes = new ServiciosVentanaEsquemaEjes();
		serviciosVentanaVehiculo = new ServiciosVentanaVehiculo();
		mensajes = new ControladorMensajes();
		vehiculoSeleccionado = new Vehiculo();
		neumaticoSeleccionado = new Neumatico();
		listaPosicionesActivasVehiculo = new ArrayList<PosicionesVehiculo>();
		rutaVehiculo = new RutaVehiculo();
		streamedContentImagen = new DefaultStreamedContent();
		streamedContentImagenConfirmar = new DefaultStreamedContent();
		listaNeumaticoVehiculoDeOperacionActual = new ArrayList<NeumaticoVehiculo>();
		listaNeumaticoVehiculoMontadosPrevio = new ArrayList<NeumaticoVehiculo>();
		empresaEnLaSesion();
		movimiento = new Movimiento();
		movimientoId = new MovimientoId();
		cargarFondosNeumaticos();
		cargarDisabled();
		cargarNeumaticosActivo();
		cargarPrimerNeumatico();
		colocarImagenDefault();
		cargarRemanenteInicial();
		filtrarNeumaticoDataModel(0);
		idVehiculo = 0;
		
		
		neumaticoSeleccionado.setCodInterno("PROBANDO AUX");
		listaAlmacenAux.add(neumaticoSeleccionado);
	}

	
	public void cargarFondosNeumaticos() {
		neumatico1 = neumatico2 = neumatico3 = neumatico4 = neumatico5 = neumatico6 = neumatico7 = neumatico8 = neumatico9 =
				neumatico10 = neumatico11 = neumatico12 = neumatico13 = neumatico14 = neumatico15 = neumatico16 = neumatico17 = neumatico18 =
				neumatico19 = neumatico20 = neumatico21 = neumatico22 = "background: url(../imagenes/ruedaTransparente.png) no-repeat; background-position: center; opacity:0.4;"; 
	}
	
	public void cargarDisabled() {
		neumatico11Disabled = neumatico12Disabled = neumatico13Disabled = neumatico14Disabled = neumatico21Disabled = neumatico22Disabled = neumatico23Disabled = neumatico24Disabled = 
				neumatico31Disabled = neumatico32Disabled = neumatico33Disabled = neumatico34Disabled = neumatico41Disabled = neumatico42Disabled = neumatico43Disabled = neumatico44Disabled =
				neumatico51Disabled = neumatico52Disabled = neumatico53Disabled = neumatico54Disabled = neumaticoRepDerDisabled = neumaticoRepIzqDisabled = true;
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
				remanenteIzquierdoC = remanenteIzquierdoD = remanenteSuperiorA = remanenteSuperiorB = remanenteSuperiorC = remanenteSuperiorD = 0.0;
	}
	
	public void filtrarNeumaticoDataModel(int idNeumaticoParametro) {
		List<Neumatico> listaNeumaticoAux = new ArrayList<Neumatico>();
		setListaNeumaticos(NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("empresa", this.empresa));
		for(int i=0; i<listaNeumaticos.size(); i++) {
			if((listaNeumaticos.get(i).getEstado().equals("L")) && (listaNeumaticos.get(i).getIdNeumatico() != idNeumaticoParametro)) {
				listaNeumaticoAux.add(listaNeumaticos.get(i));
			}
		}
		for(int j=0; j<listaNeumaticoVehiculoDeOperacionActual.size(); j++) {
			for(int h=0; h<listaNeumaticoAux.size(); h++) {
				if(listaNeumaticoAux.get(h).getIdNeumatico() == listaNeumaticoVehiculoDeOperacionActual.get(j).getNeumatico().getIdNeumatico()) {
					listaNeumaticoAux.remove(h);
				}
			}
		}
		if(listaNeumaticoAux.size() == 0) {
			mensajes.informativo("ATENCIÓN!", "No existen neumáticos listos para montar");
		}
		setNeumaticoDataModel(new NeumaticoDataModel(listaNeumaticoAux));
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
		cargarFondosNeumaticos();
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
		setPresionMovimientoNeumatico(0.0);
		setObservacionesMovimiento("");
		//actualizarNeumaticoMontaje();
		disabledBtnRotar = true;
	}
	
	public void actualizarVehiculoRotacion(String cambiar) {
		if(!existeMontados) {
			cargarFondosNeumaticos();
			cargarDisabled();
			cargarPrimerNeumatico();
			cargarNeumaticosActivo();
			cargarRemanenteInicial();
			limpiarDatosNeumatico();
			rutaVehiculo = new RutaVehiculo();
			listaNeumaticoVehiculoDeOperacionActual.clear();
			listaNeumaticoVehiculoMontadosPrevio.clear();
			mapaRelacionalNeumaticosOperacion.clear();
			if(idVehiculo != 0) {
				vehiculoSeleccionado = (Vehiculo) VehiculoDAO.getInstancia().buscarEntidadPorClave(idVehiculo);
				serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(vehiculoSeleccionado.getEsquemaEjes());
				serviciosVentanaEsquemaEjes.verificarDatosActivos();
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
				this.actualizarVehiculoRotacion("");
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
		listaMovimientosPrevio = new ArrayList<Movimiento>();
		
		for(int j=0; j<listaNeumaticoVehiculoMontadosPrevio.size(); j++) {
			Iterator iterador = mapaRelacionalNeumaticosOperacion.entrySet().iterator();
		    Map.Entry neumaticoVehiculoMapa;
			while (iterador.hasNext()) {
				neumaticoVehiculoMapa = (Map.Entry) iterador.next();
		        NeumaticoVehiculo neumaticoVehiculoAux = (NeumaticoVehiculo) neumaticoVehiculoMapa.getValue();
				if(listaNeumaticoVehiculoMontadosPrevio.get(j).getPosicion().equals(neumaticoVehiculoAux.getPosicion())) {
					montajePrevio = true;
					mapaRelacionalNeumaticosOperacion.put((String) neumaticoVehiculoMapa.getKey(), listaNeumaticoVehiculoMontadosPrevio.get(j));
					if(listaNeumaticoVehiculoMontadosPrevio.get(j).getPosicion().equals("21")) {
						neumatico21 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
						neumaticoRepIzqDisabled = false;
					} else if(listaNeumaticoVehiculoMontadosPrevio.get(j).getPosicion().equals("22")) {
						neumatico22 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
						neumaticoRepDerDisabled = false;
					}
					//actualizarFondosNeumaticos((String) neumaticoVehiculoMapa.getKey());
					montajePrevio = false;
				}
			}
		}
	}
	
	/**
	 * Este método se encarga de validar que 
	 */
	public void habilitarRotacion() {
		
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
	
	
	/**
	 * Este metodo se utiliza para actualizar los campos que muestran la informacion del neumatico seleccionado desde el comboBox
	 */
	public void actualizarNeumaticoMontaje() {
		if(idNeumatico != 0) {
			neumaticoSeleccionado = (Neumatico) NeumaticoDAO.getInstancia().buscarEntidadPorClave(idNeumatico);
			setTipoNeumatico(traducirTipoNeumatico(neumaticoSeleccionado));
			setCondicion(traducirCondcion(neumaticoSeleccionado));
			if(idNeumatico == 0) {
				colocarImagenDefault();
			} else {
				setStreamedContentImagen(publicarImagen(neumaticoSeleccionado.getDisenoMedida().getDiseno().getImagen()));
			}
		} else {
			limpiarDatosNeumatico();
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
		Path path = Paths.get("C:/Users/cauca/Desktop/SCSN-Coord-Informática/WorkSpace/proyecto-cauca-SCSN/WebContent/imagenes/ruedaTransparente.png");
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
	public String traducirCondcion(Neumatico neumatico) {
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
			if((presionRotacion != null) && (presionRotacion != 0) && (validador.validarReal(Double.toString(presionRotacion), "El valor de la presión debe contener solo números o punto (.)"))) {
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
					movimientoId.setIdNeumatico(neumaticoSeleccionado.getIdNeumatico());
					listaPosicionesActivasVehiculo.get(i).getMovimiento().setId(movimientoId);
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
		setCondicionConfirmar(traducirCondcion(movimientoSeleccionado.getNeumatico()));
		setTipoNeumaticoConfirmar(traducirTipoNeumatico(movimientoSeleccionado.getNeumatico()));
//			this.streamedContentImagenConfirmar = streamedContentImagen;
//			setStreamedContentImagenConfirmar(publicarImagen(movimientoSeleccionado.getNeumatico().getDisenoMedida().getDiseno().getImagen()));
		RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarDesmontajeID");
		RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");
	}
	
	public void obtenerNeumaticoRotar(String neumaticoEntrante) {
		this.neumaticoSeleccionado = ((NeumaticoVehiculo) mapaRelacionalNeumaticosOperacion.get(neumaticoEntrante)).getNeumatico();
		
		
//		for(int i = 0; i<listaPosicionesVehiculoPrevio.size(); i++){
//			if(listaPosicionesVehiculoPrevio.get(i).getNombreRueda().equals(neumaticoEntrante)) {
//				neumaticoSeleccionado = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getNeumatico();
//				fechaMontaje = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getId().getFecha();
//				posicionActual = listaPosicionesVehiculoPrevio.get(i).getMovimiento().getPosicionFinal();
//			}
//		}
		setFechaMontaje(fechaMontaje); //AQUI TENEMOS QUE OBTENER LA FECHA DEL MOVIMIENTO QUE SE RELACIONE CON EL NEUMÁTICO OBTENIDO
		setCondicion(traducirCondcion(neumaticoSeleccionado));
		setTipoNeumatico(traducirTipoNeumatico(neumaticoSeleccionado));
		setStreamedContentImagen(publicarImagen(neumaticoSeleccionado.getDisenoMedida().getDiseno().getImagen()));
		disabledBtnRotar = false;
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
		
		if(this.neumaticoSeleccionado.getIdNeumatico() != null) {
			neumaticoVehiculoId.setIdNeumatico(neumaticoSeleccionado.getIdNeumatico());
			neumaticoVehiculoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setNeumatico(neumaticoSeleccionado);
		}
		
		if(neumaticoEntrante.equals("neumatico11")) {
			if(!neumatico1Activo) {
				if(validarDatosParaMontaje()) {
					neumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico1Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
				/*	if(montajePrevio) {
						neumatico1Previo = true;
						neumatico1Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico1Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}*/
				} 
			} else { /*
				if(!neumatico1Previo) {
					if(desmontaje) {
						neumatico1 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico1Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				} */
			}	
		}
		if(neumaticoEntrante.equals("neumatico12")) {
			if(!neumatico2Activo) { 
				// PROBANDO
				neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				neumatico2Activo = true;
				/*if(validarDatosParaMontaje()) {
					neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico2Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					/*if(montajePrevio) {
						neumatico2Previo = true;
						neumatico2Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico2Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}*/
				//} 
			} else {/*
				if(!neumatico2Previo) {
					if(desmontaje) {
						neumatico2 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico2Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				} */
			}
		}
		if(neumaticoEntrante.equals("neumatico13")) {
			if(!neumatico3Activo) {
				if(validarDatosParaMontaje()) {
					neumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico3Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					/*if(montajePrevio) {
						neumatico3Previo = true;
						neumatico3Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico3Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} */
				}
			} else {/*
				if(!neumatico3Previo) {
					if(desmontaje) {
						neumatico3 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico3Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				} */
			}
		}
		if(neumaticoEntrante.equals("neumatico14")) {
			if(!neumatico4Activo) {
				if(validarDatosParaMontaje()) {
					neumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico4Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico4Previo = true;
						neumatico4Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico4Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico4Previo) {
					if(desmontaje) {
						neumatico4 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico4Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico21")) {
			if(!neumatico5Activo) {
				if(validarDatosParaMontaje()) {
					neumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico5Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico5Previo = true;
						neumatico5Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico5Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico5Previo) {
					if(desmontaje) {
						neumatico5 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico5Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico22")) {
			if(!neumatico6Activo) {
				if(validarDatosParaMontaje()) {
					neumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico6Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico6Previo = true;
						neumatico6Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico6Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico6Previo) {
					if(desmontaje) {
						neumatico6 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico6Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico23")) {
			if(!neumatico7Activo) {
				if(validarDatosParaMontaje()) {
					neumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico7Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico7Previo = true;
						neumatico7Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico7Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico7Previo) {
					if(desmontaje) {
						neumatico7 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico7Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico24")) {
			if(!neumatico8Activo) {
				if(validarDatosParaMontaje()) {
					neumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico8Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico8Previo = true;
						neumatico8Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico8Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico8Previo) {
					if(desmontaje) {
						neumatico8 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico8Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico31")) {
			if(!neumatico9Activo) {
				if(validarDatosParaMontaje()) {
					neumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico9Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico9Previo = true;
						neumatico9Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico9Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico9Previo) {
					if(desmontaje) {
						neumatico9 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico9Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico32")) {
			if(!neumatico10Activo) {
				if(validarDatosParaMontaje()) {
					neumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico10Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico10Previo = true;
						neumatico10Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico10Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico10Previo) {
					if(desmontaje) {
						neumatico10 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico10Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico33")) {
			if(!neumatico11Activo) {
				if(validarDatosParaMontaje()) {
					neumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico11Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico11Previo = true;
						neumatico11Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico11Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico11Previo) {
					if(desmontaje) {
						neumatico11 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico11Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico34")) {
			if(!neumatico12Activo) {
				if(validarDatosParaMontaje()) {
					neumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico12Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico12Previo = true;
						neumatico12Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico12Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico12Previo) {
					if(desmontaje) {
						neumatico12 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico12Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico41")) {
			if(!neumatico13Activo) {
				if(validarDatosParaMontaje()) {
					neumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico13Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico13Previo = true;
						neumatico13Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico13Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico13Previo) {
					if(desmontaje) {
						neumatico13 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico13Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico42")) {
			if(!neumatico14Activo) {
				if(validarDatosParaMontaje()) {
					neumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico14Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico14Previo = true;
						neumatico14Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico14Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico14Previo) {
					if(desmontaje) {
						neumatico14 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico14Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico43")) {
			if(!neumatico15Activo) {
				if(validarDatosParaMontaje()) {
					neumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico15Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico15Previo = true;
						neumatico15Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico15Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico15Previo) {
					if(desmontaje) {
						neumatico15 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico15Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico44")) {
			if(!neumatico16Activo) {
				if(validarDatosParaMontaje()) {
					neumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico16Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico16Previo = true;
						neumatico16Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico16Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico16Previo) {
					if(desmontaje) {
						neumatico16 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico16Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico51")) {
			if(!neumatico17Activo) {
				if(validarDatosParaMontaje()) {
					neumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico17Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico17Previo = true;
						neumatico17Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico17Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico17Previo) {
					if(desmontaje) {
						neumatico17 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico17Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico52")) {
			if(!neumatico18Activo) {
				if(validarDatosParaMontaje()) {
					neumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico18Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico18Previo = true;
						neumatico18Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico18Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico18Previo) {
					if(desmontaje) {
						neumatico18 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico18Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico53")) {
			if(!neumatico19Activo) {
				if(validarDatosParaMontaje()) {
					neumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico19Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico19Previo = true;
						neumatico19Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico19Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(neumatico19Previo) {
					if(desmontaje) {
						neumatico19 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico19Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		if(neumaticoEntrante.equals("neumatico54")) {
			if(!neumatico20Activo) {
				if(validarDatosParaMontaje()) {
					neumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
//					neumatico20Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					if(montajePrevio) {
						neumatico20Previo = true;
						neumatico20Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico20Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}
				}
			} else {
				if(!neumatico20Previo) {
					if(desmontaje) {
						neumatico20 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico20Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				}
			}
		}
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		if(neumaticoEntrante.equals("neumaticoR21")) {
			if(!neumatico21Activo) {
				if(validarDatosParaMontaje()) {
					neumatico21 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico21Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
				/*	if(montajePrevio) {
						neumatico21Previo = true;
						neumatico21Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico21Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					}*/
				}
			} else {/*
				if(!neumatico21Previo) {
					if(desmontaje) {
						neumatico21 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico21Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				} */
			}
		}
		if(neumaticoEntrante.equals("neumaticoR22")) {
			if(!neumatico22Activo) {
				if(validarDatosParaMontaje()) {
					neumatico22 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico22Activo = true;
//					agregarValoresAListas(neumaticoEntrante, desmontaje);
					/*if(montajePrevio) {
						neumatico22Previo = true;
						neumatico22Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} else {
						neumatico22Activo = true;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
					} */
				}
			} else {/*
				if(!neumatico22Previo) {
					if(desmontaje) {
						neumatico22 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
						neumatico22Activo = false;
						agregarValoresAListas(neumaticoEntrante, desmontaje);
						desmontaje = false;
					} else {
						habilitarVentanaConsultaDesmonte(neumaticoEntrante);
					}
				} else {
					mensajes.advertencia("ATENCIÓN", "Este neumático no puede ser desmontado desde esta interfaz, para realizar dicho desmonte diríjase a la interfaz de Desmontajes");
				} */
			}
		}
		
		
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
		System.out.println("::::::::::::::::::::::::::::::: TERMINAMOS DE LISTAR LOS ELEMENTOS INVOLUCRADOS ::::::::::::::::::::::::::::::::::::::::::::::::.");
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
//		movimientoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
//		movimientoId.setTipoMovimiento("R");
		listaPosicionesActivasVehiculo = new ArrayList<PosicionesVehiculo>();
		listaPosicionesVehiculoPrevio = new ArrayList<PosicionesVehiculo>();
		listaNeumaticoVehiculoDeOperacionActual = new ArrayList<NeumaticoVehiculo>();
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico4() != null) {
			neumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico14Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico14", neumaticoVehiculo);
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
			neumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico13Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico13", neumaticoVehiculo);
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
			neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico12Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico12", neumaticoVehiculo);
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
			neumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico11Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico11", neumaticoVehiculo);
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
			neumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico24Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico24", neumaticoVehiculo);
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
			neumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico23Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico23", neumaticoVehiculo);
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
			neumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico22Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico22", neumaticoVehiculo);
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
			neumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico21Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico21", neumaticoVehiculo);
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
			neumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico34Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico34", neumaticoVehiculo);
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
			neumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico33Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico33", neumaticoVehiculo);
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
			neumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico32Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico32", neumaticoVehiculo);
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
			neumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico31Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico31", neumaticoVehiculo);
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
			neumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico44Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico44", neumaticoVehiculo);
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
			neumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico43Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico43", neumaticoVehiculo);
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
			neumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico42Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico42", neumaticoVehiculo);
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
			neumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico41Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico41", neumaticoVehiculo);
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
			neumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico54Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico54", neumaticoVehiculo);
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
			neumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico53Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico53", neumaticoVehiculo);
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
			neumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico52Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico52", neumaticoVehiculo);
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
			neumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico51Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico51", neumaticoVehiculo);
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
			neumatico21 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepIzqDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", neumaticoVehiculo);
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
			neumatico22 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepDerDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", neumaticoVehiculo);
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
			neumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico11Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico11", neumaticoVehiculo);
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
			neumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico12Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico12", neumaticoVehiculo);
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
			neumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico13Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico13", neumaticoVehiculo);
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
			neumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico14Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico14", neumaticoVehiculo);
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
			neumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico21Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico21", neumaticoVehiculo);
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
			neumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico22Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico22", neumaticoVehiculo);
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
			neumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico23Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico23", neumaticoVehiculo);
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
			neumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico24Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico24", neumaticoVehiculo);
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
			neumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico31Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico31", neumaticoVehiculo);
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
			neumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico32Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico32", neumaticoVehiculo);
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
			neumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico33Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico33", neumaticoVehiculo);
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
			neumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico34Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico34", neumaticoVehiculo);
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
			neumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico41Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico41", neumaticoVehiculo);
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
			neumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico42Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico42", neumaticoVehiculo);
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
			neumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico43Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico43", neumaticoVehiculo);
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
			neumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico44Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico44", neumaticoVehiculo);
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
			neumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico51Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico51", neumaticoVehiculo);
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
			neumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico52Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico52", neumaticoVehiculo);
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
			neumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico53Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico53", neumaticoVehiculo);
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
			neumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
			neumatico54Disabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico54", neumaticoVehiculo);
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
			neumatico21 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepIzqDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", neumaticoVehiculo);
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
			neumatico22 = "background: url(../imagenes/ruedaAgricolaApagada.png) no-repeat; background-position: center;";
//			neumaticoRepDerDisabled = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", neumaticoVehiculo);
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
	
	public void procesarOperacion(String accion) {
		if (listaNeumaticoVehiculoDeOperacionActual.size() == listaNeumaticoVehiculoMontadosPrevio.size()) {
			if(accion.equals("terminarVacio")) {
				limpiarDatosVehiculo();
				movimientoId.setFecha(null);
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
			if(validador.validarFecha(validador.formatearFechaEstiloMedio(movimientoId.getFecha()))) {
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
						movimientoId.setFecha(null);
						existeMontados = false;
						limpiarDatosNeumatico();
						listaNeumaticos.clear();
						listaNeumaticoVehiculoDeOperacionActual.clear();
						filtrarNeumaticoDataModel(0);
						desmontaje = false;
						kilometrajeMovimiento = 0.0;
				} else if(accion.equals("A")) { 
					fechaTerminar = validador.formatearFechaEstiloMedio(movimientoId.getFecha());
					RequestContext.getCurrentInstance().addPartialUpdateTarget("confirmarTerminar");
					RequestContext.getCurrentInstance().addCallbackParam("tarea", "T");
				}
			} else {
				mensajes.error("Fecha Inválida", "Por favor, verifique la fecha ingresada");
			}
		}
	}
	
	public void calcularTotalSuperior(AjaxBehaviorEvent event){
		totalSuperior = (remanenteSuperiorA+remanenteSuperiorB+remanenteSuperiorC+remanenteSuperiorD)/4;
	}
	
	public void calcularTotalInferior(AjaxBehaviorEvent event){
		totalInferior = (remanenteInferiorA+remanenteInferiorB+remanenteInferiorC+remanenteInferiorD)/4;
	}
	
	public void calcularTotalIzquierdo(AjaxBehaviorEvent event){
		totalIzquierdo = (remanenteIzquierdoA+remanenteIzquierdoB+remanenteIzquierdoC+remanenteIzquierdoD)/4;
	}
	
	public void calcularTotalDerecha(AjaxBehaviorEvent event){
		totalDerecha = (remanenteDerechaA+remanenteDerechaB+remanenteDerechaC+remanenteDerechaD)/4;
	}
	
	public void calcularTotalOperacion(AjaxBehaviorEvent event) {
		totalOperacion = (totalSuperior+totalInferior+totalDerecha+totalIzquierdo)/4;	
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
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

	public String getNeumatico1() {
		return neumatico1;
	}

	public void setNeumatico1(String neumatico1) {
		this.neumatico1 = neumatico1;
	}

	public String getNeumatico2() {
		return neumatico2;
	}

	public void setNeumatico2(String neumatico2) {
		this.neumatico2 = neumatico2;
	}

	public String getNeumatico3() {
		return neumatico3;
	}

	public void setNeumatico3(String neumatico3) {
		this.neumatico3 = neumatico3;
	}

	public String getNeumatico4() {
		return neumatico4;
	}

	public void setNeumatico4(String neumatico4) {
		this.neumatico4 = neumatico4;
	}

	public String getNeumatico5() {
		return neumatico5;
	}

	public void setNeumatico5(String neumatico5) {
		this.neumatico5 = neumatico5;
	}

	public String getNeumatico6() {
		return neumatico6;
	}

	public void setNeumatico6(String neumatico6) {
		this.neumatico6 = neumatico6;
	}

	public String getNeumatico7() {
		return neumatico7;
	}

	public void setNeumatico7(String neumatico7) {
		this.neumatico7 = neumatico7;
	}

	public String getNeumatico8() {
		return neumatico8;
	}

	public void setNeumatico8(String neumatico8) {
		this.neumatico8 = neumatico8;
	}

	public String getNeumatico9() {
		return neumatico9;
	}

	public void setNeumatico9(String neumatico9) {
		this.neumatico9 = neumatico9;
	}

	public String getNeumatico10() {
		return neumatico10;
	}

	public void setNeumatico10(String neumatico10) {
		this.neumatico10 = neumatico10;
	}

	public String getNeumatico11() {
		return neumatico11;
	}

	public void setNeumatico11(String neumatico11) {
		this.neumatico11 = neumatico11;
	}

	public String getNeumatico12() {
		return neumatico12;
	}

	public void setNeumatico12(String neumatico12) {
		this.neumatico12 = neumatico12;
	}

	public String getNeumatico13() {
		return neumatico13;
	}

	public void setNeumatico13(String neumatico13) {
		this.neumatico13 = neumatico13;
	}

	public String getNeumatico14() {
		return neumatico14;
	}

	public void setNeumatico14(String neumatico14) {
		this.neumatico14 = neumatico14;
	}

	public String getNeumatico15() {
		return neumatico15;
	}

	public void setNeumatico15(String neumatico15) {
		this.neumatico15 = neumatico15;
	}

	public String getNeumatico16() {
		return neumatico16;
	}

	public void setNeumatico16(String neumatico16) {
		this.neumatico16 = neumatico16;
	}

	public String getNeumatico17() {
		return neumatico17;
	}

	public void setNeumatico17(String neumatico17) {
		this.neumatico17 = neumatico17;
	}

	public String getNeumatico18() {
		return neumatico18;
	}

	public void setNeumatico18(String neumatico18) {
		this.neumatico18 = neumatico18;
	}

	public String getNeumatico19() {
		return neumatico19;
	}

	public void setNeumatico19(String neumatico19) {
		this.neumatico19 = neumatico19;
	}

	public String getNeumatico20() {
		return neumatico20;
	}

	public void setNeumatico20(String neumatico20) {
		this.neumatico20 = neumatico20;
	}

	public String getNeumatico21() {
		return neumatico21;
	}

	public void setNeumatico21(String neumatico21) {
		this.neumatico21 = neumatico21;
	}

	public String getNeumatico22() {
		return neumatico22;
	}

	public void setNeumatico22(String neumatico22) {
		this.neumatico22 = neumatico22;
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

	public boolean isNeumatico11Disabled() {
		return neumatico11Disabled;
	}

	public void setNeumatico11Disabled(boolean neumatico11Disabled) {
		this.neumatico11Disabled = neumatico11Disabled;
	}

	public boolean isNeumatico12Disabled() {
		return neumatico12Disabled;
	}

	public void setNeumatico12Disabled(boolean neumatico12Disabled) {
		this.neumatico12Disabled = neumatico12Disabled;
	}

	public boolean isNeumatico13Disabled() {
		return neumatico13Disabled;
	}

	public void setNeumatico13Disabled(boolean neumatico13Disabled) {
		this.neumatico13Disabled = neumatico13Disabled;
	}

	public boolean isNeumatico14Disabled() {
		return neumatico14Disabled;
	}

	public void setNeumatico14Disabled(boolean neumatico14Disabled) {
		this.neumatico14Disabled = neumatico14Disabled;
	}

	public boolean isNeumatico21Disabled() {
		return neumatico21Disabled;
	}

	public void setNeumatico21Disabled(boolean neumatico21Disabled) {
		this.neumatico21Disabled = neumatico21Disabled;
	}

	public boolean isNeumatico22Disabled() {
		return neumatico22Disabled;
	}

	public void setNeumatico22Disabled(boolean neumatico22Disabled) {
		this.neumatico22Disabled = neumatico22Disabled;
	}

	public boolean isNeumatico23Disabled() {
		return neumatico23Disabled;
	}

	public void setNeumatico23Disabled(boolean neumatico23Disabled) {
		this.neumatico23Disabled = neumatico23Disabled;
	}

	public boolean isNeumatico24Disabled() {
		return neumatico24Disabled;
	}

	public void setNeumatico24Disabled(boolean neumatico24Disabled) {
		this.neumatico24Disabled = neumatico24Disabled;
	}

	public boolean isNeumatico31Disabled() {
		return neumatico31Disabled;
	}

	public void setNeumatico31Disabled(boolean neumatico31Disabled) {
		this.neumatico31Disabled = neumatico31Disabled;
	}

	public boolean isNeumatico32Disabled() {
		return neumatico32Disabled;
	}

	public void setNeumatico32Disabled(boolean neumatico32Disabled) {
		this.neumatico32Disabled = neumatico32Disabled;
	}

	public boolean isNeumatico33Disabled() {
		return neumatico33Disabled;
	}

	public void setNeumatico33Disabled(boolean neumatico33Disabled) {
		this.neumatico33Disabled = neumatico33Disabled;
	}

	public boolean isNeumatico34Disabled() {
		return neumatico34Disabled;
	}

	public void setNeumatico34Disabled(boolean neumatico34Disabled) {
		this.neumatico34Disabled = neumatico34Disabled;
	}

	public boolean isNeumatico41Disabled() {
		return neumatico41Disabled;
	}

	public void setNeumatico41Disabled(boolean neumatico41Disabled) {
		this.neumatico41Disabled = neumatico41Disabled;
	}

	public boolean isNeumatico42Disabled() {
		return neumatico42Disabled;
	}

	public void setNeumatico42Disabled(boolean neumatico42Disabled) {
		this.neumatico42Disabled = neumatico42Disabled;
	}

	public boolean isNeumatico43Disabled() {
		return neumatico43Disabled;
	}

	public void setNeumatico43Disabled(boolean neumatico43Disabled) {
		this.neumatico43Disabled = neumatico43Disabled;
	}

	public boolean isNeumatico44Disabled() {
		return neumatico44Disabled;
	}

	public void setNeumatico44Disabled(boolean neumatico44Disabled) {
		this.neumatico44Disabled = neumatico44Disabled;
	}

	public boolean isNeumatico51Disabled() {
		return neumatico51Disabled;
	}

	public void setNeumatico51Disabled(boolean neumatico51Disabled) {
		this.neumatico51Disabled = neumatico51Disabled;
	}

	public boolean isNeumatico52Disabled() {
		return neumatico52Disabled;
	}

	public void setNeumatico52Disabled(boolean neumatico52Disabled) {
		this.neumatico52Disabled = neumatico52Disabled;
	}

	public boolean isNeumatico53Disabled() {
		return neumatico53Disabled;
	}

	public void setNeumatico53Disabled(boolean neumatico53Disabled) {
		this.neumatico53Disabled = neumatico53Disabled;
	}

	public boolean isNeumatico54Disabled() {
		return neumatico54Disabled;
	}

	public void setNeumatico54Disabled(boolean neumatico54Disabled) {
		this.neumatico54Disabled = neumatico54Disabled;
	}

	public boolean isNeumaticoRepIzqDisabled() {
		return neumaticoRepIzqDisabled;
	}

	public void setNeumaticoRepIzqDisabled(boolean neumaticoRepIzqDisabled) {
		this.neumaticoRepIzqDisabled = neumaticoRepIzqDisabled;
	}

	public boolean isNeumaticoRepDerDisabled() {
		return neumaticoRepDerDisabled;
	}

	public void setNeumaticoRepDerDisabled(boolean neumaticoRepDerDisabled) {
		this.neumaticoRepDerDisabled = neumaticoRepDerDisabled;
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

	public Double getPresionMovimientoNeumatico() {
		return presionRotacion;
	}

	public void setPresionMovimientoNeumatico(Double presionMovimientoNeumatico) {
		this.presionRotacion = presionMovimientoNeumatico;
	}


	public MovimientoId getMovimientoId() {
		return movimientoId;
	}


	public void setMovimientoId(MovimientoId movimientoId) {
		this.movimientoId = movimientoId;
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


	public String getFechaTerminar() {
		return fechaTerminar;
	}


	public void setFechaTerminar(String fechaTerminar) {
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


	public Double getRemanenteSuperiorA() {
		return remanenteSuperiorA;
	}


	public void setRemanenteSuperiorA(Double remanenteSuperiorA) {
		this.remanenteSuperiorA = remanenteSuperiorA;
	}


	public Double getRemanenteSuperiorB() {
		return remanenteSuperiorB;
	}


	public void setRemanenteSuperiorB(Double remanenteSuperiorB) {
		this.remanenteSuperiorB = remanenteSuperiorB;
	}


	public Double getRemanenteSuperiorC() {
		return remanenteSuperiorC;
	}


	public void setRemanenteSuperiorC(Double remanenteSuperiorC) {
		this.remanenteSuperiorC = remanenteSuperiorC;
	}


	public Double getRemanenteSuperiorD() {
		return remanenteSuperiorD;
	}


	public void setRemanenteSuperiorD(Double remanenteSuperiorD) {
		this.remanenteSuperiorD = remanenteSuperiorD;
	}


	public Double getRemanenteIzquierdoA() {
		return remanenteIzquierdoA;
	}


	public void setRemanenteIzquierdoA(Double remanenteIzquierdoA) {
		this.remanenteIzquierdoA = remanenteIzquierdoA;
	}


	public Double getRemanenteIzquierdoB() {
		return remanenteIzquierdoB;
	}


	public void setRemanenteIzquierdoB(Double remanenteIzquierdoB) {
		this.remanenteIzquierdoB = remanenteIzquierdoB;
	}


	public Double getRemanenteIzquierdoC() {
		return remanenteIzquierdoC;
	}


	public void setRemanenteIzquierdoC(Double remanenteIzquierdoC) {
		this.remanenteIzquierdoC = remanenteIzquierdoC;
	}


	public Double getRemanenteIzquierdoD() {
		return remanenteIzquierdoD;
	}


	public void setRemanenteIzquierdoD(Double remanenteIzquierdoD) {
		this.remanenteIzquierdoD = remanenteIzquierdoD;
	}


	public Double getRemanenteInferiorA() {
		return remanenteInferiorA;
	}


	public void setRemanenteInferiorA(Double remanenteInferiorA) {
		this.remanenteInferiorA = remanenteInferiorA;
	}


	public Double getRemanenteInferiorB() {
		return remanenteInferiorB;
	}


	public void setRemanenteInferiorB(Double remanenteInferiorB) {
		this.remanenteInferiorB = remanenteInferiorB;
	}


	public Double getRemanenteInferiorC() {
		return remanenteInferiorC;
	}


	public void setRemanenteInferiorC(Double remanenteInferiorC) {
		this.remanenteInferiorC = remanenteInferiorC;
	}


	public Double getRemanenteInferiorD() {
		return remanenteInferiorD;
	}


	public void setRemanenteInferiorD(Double remanenteInferiorD) {
		this.remanenteInferiorD = remanenteInferiorD;
	}


	public Double getRemanenteDerechaA() {
		return remanenteDerechaA;
	}


	public void setRemanenteDerechaA(Double remanenteDerechaA) {
		this.remanenteDerechaA = remanenteDerechaA;
	}


	public Double getRemanenteDerechaB() {
		return remanenteDerechaB;
	}


	public void setRemanenteDerechaB(Double remanenteDerechaB) {
		this.remanenteDerechaB = remanenteDerechaB;
	}


	public Double getRemanenteDerechaC() {
		return remanenteDerechaC;
	}


	public void setRemanenteDerechaC(Double remanenteDerechaC) {
		this.remanenteDerechaC = remanenteDerechaC;
	}


	public Double getRemanenteDerechaD() {
		return remanenteDerechaD;
	}


	public void setRemanenteDerechaD(Double remanenteDerechaD) {
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


	public boolean isDisabledBtnRotar() {
		return disabledBtnRotar;
	}


	public void setDisabledBtnRotar(boolean disabledBtnRotar) {
		this.disabledBtnRotar = disabledBtnRotar;
	}


	public Double getPresionRotacion() {
		return presionRotacion;
	}


	public void setPresionRotacion(Double presionRotacion) {
		this.presionRotacion = presionRotacion;
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



}

