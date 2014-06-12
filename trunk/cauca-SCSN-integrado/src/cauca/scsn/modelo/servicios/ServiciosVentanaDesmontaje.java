package cauca.scsn.modelo.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.dnd.Droppable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.sun.xml.bind.v2.model.core.ID;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.NeumaticoVehiculoDAO;
import cauca.scsn.modelo.dao.RutaVehiculoDAO;
import cauca.scsn.modelo.dao.TipoDesgasteDAO;
import cauca.scsn.modelo.dao.VehiculoDAO;
import cauca.scsn.modelo.datamodel.EmpleadoDataModel;
import cauca.scsn.modelo.datamodel.NeumaticoDataModel;
import cauca.scsn.modelo.datamodel.TipoDesgasteDataModel;
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
@SessionScoped
public class ServiciosVentanaDesmontaje {
	
	private Movimiento			movimiento;
	private Vehiculo			vehiculoSeleccionado;
	private int					idVehiculo;
	private boolean				regresarDisabled = true;
	private String				dentroCarretera;
	private List<Neumatico> 	almacenListos = new ArrayList<Neumatico>();
	private List<Neumatico> 	almacenReencauche = new ArrayList<Neumatico>();
	private List<Neumatico> 	almacenReparacion = new ArrayList<Neumatico>();
	private List<Neumatico> 	almacenReclamos = new ArrayList<Neumatico>();
	private List<Neumatico> 	almacenDarBaja = new ArrayList<Neumatico>();
	private List<Movimiento> 	listaMovimiento = new ArrayList<Movimiento>();
	private List<NeumaticoVehiculo> listaNeumaticoVehiculoMontadosPrevio = new ArrayList<NeumaticoVehiculo>();
	
	private Movimiento 			movimientoActual = new Movimiento();
	private MovimientoId		movimientoActualId = new MovimientoId(0,0,"D",new Date());
	
	private Date				fecha = new Date();
	
	private Neumatico				neumaticoSeleccionado = new Neumatico();
	private NeumaticoDataModel 		neumaticoDataModel;
	private NeumaticoVehiculoID 	neumaticoVehiculoId = new NeumaticoVehiculoID();
	private NeumaticoVehiculo		neumaticoVehiculo;
	
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
	private Double			totalMovimiento;
	
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
	
	private boolean			neumatico11Drag;
	private boolean			neumatico12Drag;
	private boolean			neumatico13Drag;
	private boolean			neumatico14Drag;
	private boolean			neumatico21Drag;
	private boolean			neumatico22Drag;
	private boolean			neumatico23Drag;
	private boolean			neumatico24Drag;
	private boolean			neumatico31Drag;
	private boolean			neumatico32Drag;
	private boolean			neumatico33Drag;
	private boolean			neumatico34Drag;
	private boolean			neumatico41Drag;
	private boolean			neumatico42Drag;
	private boolean			neumatico43Drag;
	private boolean			neumatico44Drag;
	private boolean			neumatico51Drag;
	private boolean			neumatico52Drag;
	private boolean			neumatico53Drag;
	private boolean			neumatico54Drag;
	private boolean			neumaticoRepIzqDrag;
	private boolean			neumaticoRepDerDrag;
	
	private boolean			neumatico11Drop;
	private boolean			neumatico12Drop;
	private boolean			neumatico13Drop;
	private boolean			neumatico14Drop;
	private boolean			neumatico21Drop;
	private boolean			neumatico22Drop;
	private boolean			neumatico23Drop;
	private boolean			neumatico24Drop;
	private boolean			neumatico31Drop;
	private boolean			neumatico32Drop;
	private boolean			neumatico33Drop;
	private boolean			neumatico34Drop;
	private boolean			neumatico41Drop;
	private boolean			neumatico42Drop;
	private boolean			neumatico43Drop;
	private boolean			neumatico44Drop;
	private boolean			neumatico51Drop;
	private boolean			neumatico52Drop;
	private boolean			neumatico53Drop;
	private boolean			neumatico54Drop;
	private boolean			neumaticoRepIzqDrop;
	private boolean			neumaticoRepDerDrop;
	
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
	
	private double			presion = 0;
	private Double 			recorridoAcumulado = 0.0;
	private RutaVehiculo 	rutaVehiculo;
	private String			nombreRueda;
	private TipoDesgaste    tipoDesgasteSeleccionado = new TipoDesgaste();
	private String			observacionesMovimiento;
	private String 			almacenActivo;


	private ServiciosVentanaEsquemaEjes	serviciosVentanaEsquemaEjes;
	private ControladorMensajes			mensajes;
	
	public ServiciosVentanaDesmontaje() {
		super();
		serviciosVentanaEsquemaEjes = new ServiciosVentanaEsquemaEjes();
		mensajes = new ControladorMensajes();
		vehiculoSeleccionado = new Vehiculo();
		movimiento = new Movimiento();
		cargarFondosNeumaticos();
		cargarPermisosDrag();
		cargarPermisosDrop();
		cargarListasAlmacen();
	}

	public void cargarFondosNeumaticos() {
		neumatico1 = neumatico2 = neumatico3 = neumatico4 = neumatico5 = neumatico6 = neumatico7 = neumatico8 = neumatico9 =
				neumatico10 = neumatico11 = neumatico12 = neumatico13 = neumatico14 = neumatico15 = neumatico16 = neumatico17 = neumatico18 =
				neumatico19 = neumatico20 = neumatico21 = neumatico22 = "../imagenes/ruedaTransparente.png"; 
	}
	
	public void cargarPermisosDrag() {
		neumatico11Drag = neumatico12Drag = neumatico13Drag = neumatico14Drag = neumatico21Drag = neumatico22Drag = neumatico23Drag = neumatico24Drag = 
				neumatico31Drag = neumatico32Drag = neumatico33Drag = neumatico34Drag = neumatico41Drag = neumatico42Drag = neumatico43Drag = neumatico44Drag =
				neumatico51Drag = neumatico52Drag = neumatico53Drag = neumatico54Drag = neumaticoRepDerDrag = neumaticoRepIzqDrag = true;
	}
	
	public void cargarPermisosDrop() {
		neumatico11Drop = neumatico12Drop = neumatico13Drop = neumatico14Drop = neumatico21Drop = neumatico22Drop = neumatico23Drop = neumatico24Drop = 
				neumatico31Drop = neumatico32Drop = neumatico33Drop = neumatico34Drop = neumatico41Drop = neumatico42Drop = neumatico43Drop = neumatico44Drop =
				neumatico51Drop = neumatico52Drop = neumatico53Drop = neumatico54Drop = neumaticoRepDerDrop = neumaticoRepIzqDrop = true;
	}
	
	public void cargarPrimerNeumatico() {
		primerNeumatico1 = primerNeumatico2 = primerNeumatico3 = primerNeumatico4 = primerNeumatico5 = primerNeumatico6 = primerNeumatico7 = primerNeumatico8 = primerNeumatico9 = 
				primerNeumatico10 = primerNeumatico11 = primerNeumatico12 = primerNeumatico13 = primerNeumatico14 = primerNeumatico15 = primerNeumatico16 = primerNeumatico17 = primerNeumatico18 = 
						primerNeumatico19 = primerNeumatico20 = "";
	}
	
	public void cargarNeumaticosActivo() {
		neumatico1Activo = neumatico2Activo = neumatico3Activo = neumatico4Activo = neumatico5Activo = neumatico6Activo = neumatico7Activo = neumatico8Activo = neumatico9Activo = 
				neumatico10Activo = neumatico11Activo = neumatico12Activo = neumatico13Activo = neumatico14Activo = neumatico15Activo = neumatico16Activo = neumatico17Activo = neumatico18Activo = 
						neumatico19Activo = neumatico20Activo = neumatico21Activo = neumatico22Activo = false;
	}
	
	public void cargarRemanenteInicial() {
		remanenteDerechaA = remanenteDerechaB = remanenteDerechaC = remanenteDerechaD = remanenteInferiorA = remanenteInferiorB = remanenteInferiorC = remanenteInferiorD = remanenteIzquierdoA = remanenteIzquierdoB =
				remanenteIzquierdoC = remanenteIzquierdoD = remanenteSuperiorA = remanenteSuperiorB = remanenteSuperiorC = remanenteSuperiorD = totalSuperior = totalInferior = totalIzquierdo = totalDerecha = totalMovimiento = 0.0;
	}
	
	public void limpiarDatosNeumatico() {
		neumaticoSeleccionado = new Neumatico();
//		neumaticoSeleccionado.setTipoNeumatico("P");
//		neumaticoSeleccionado.setCondicion("P");
		setPresion(0.0);
		setObservacionesMovimiento("");
		//actualizarNeumaticoMontaje();
	}
	
	public void limpiarDatosVehiculo() {
		idVehiculo = 0;
		vehiculoSeleccionado = new Vehiculo();
		cargarPrimerNeumatico();
		serviciosVentanaEsquemaEjes = new ServiciosVentanaEsquemaEjes();
		rutaVehiculo = new RutaVehiculo();
		cargarFondosNeumaticos();
		cargarPermisosDrag();
		cargarPrimerNeumatico();
	}
	
	public void actualizarVehiculoDesmontaje(String cambiar) {
		//if(!existeMontados) {
			cargarFondosNeumaticos();
			cargarPermisosDrag();
			cargarPrimerNeumatico();
			cargarNeumaticosActivo();
			cargarRemanenteInicial();
			limpiarDatosNeumatico();
			rutaVehiculo = new RutaVehiculo();
			listaNeumaticoVehiculoMontadosPrevio.clear();
			mapaRelacionalNeumaticosOperacion.clear();
			if(vehiculoSeleccionado.getIdVehiculo() != 0) {
				vehiculoSeleccionado = (Vehiculo) VehiculoDAO.getInstancia().buscarEntidadPorClave(vehiculoSeleccionado.getIdVehiculo());
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
		/*} else {
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
		
		
		
		
		if(vehiculoSeleccionado.getIdVehiculo() != 0) {
			vehiculoSeleccionado = (Vehiculo) VehiculoDAO.getInstancia().buscarEntidadPorClave(vehiculoSeleccionado.getIdVehiculo());
			serviciosVentanaEsquemaEjes.setEsquemaEjeSeleccionado(vehiculoSeleccionado.getEsquemaEjes());
			serviciosVentanaEsquemaEjes.verificarDatosActivos();
			
			NeumaticoVehiculoDAO.getInstancia().buscarEntidadesPorPropiedad("vehiculo", this.vehiculoSeleccionado);
			
			actualizarFondosNeumaticosPermisosDrag();
			habilitarPrimerNeumatico();
			rutaVehiculo = (RutaVehiculo) RutaVehiculoDAO.getInstancia().buscarEntidadPorPropiedad("vehiculo", this.vehiculoSeleccionado);
		}	*/
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
		        NeumaticoVehiculo neumaticoVehiculoAux = (NeumaticoVehiculo) neumaticoVehiculoMapa.getValue();
				if(listaNeumaticoVehiculoMontadosPrevio.get(j).getPosicion().equals(neumaticoVehiculoAux.getPosicion())) {
					mapaRelacionalNeumaticosOperacion.put((String) neumaticoVehiculoMapa.getKey(), listaNeumaticoVehiculoMontadosPrevio.get(j));
					//actualizarFondosNeumaticos((String) neumaticoVehiculoMapa.getKey());
				}
			}
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
//		movimientoId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
//		movimientoId.setTipoMovimiento("R");
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico4() != null) {
			neumatico4 = "../imagenes/ruedaAgricola.png";
			neumatico14Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico14", neumaticoVehiculo);
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
			neumatico3 = "../imagenes/ruedaAgricola.png";
			neumatico13Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico13", neumaticoVehiculo);
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
			neumatico2 = "../imagenes/ruedaAgricola.png";
			neumatico12Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico12", neumaticoVehiculo);
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
			neumatico1 = "../imagenes/ruedaAgricola.png";
			neumatico11Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico11", neumaticoVehiculo);
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
			neumatico8 = "../imagenes/ruedaAgricola.png";
			neumatico24Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico24", neumaticoVehiculo);
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
			neumatico7 = "../imagenes/ruedaAgricola.png";
			neumatico23Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico23", neumaticoVehiculo);
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
			neumatico6 = "../imagenes/ruedaAgricola.png";
			neumatico22Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico22", neumaticoVehiculo);
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
			neumatico5 = "../imagenes/ruedaAgricola.png";
			neumatico21Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico21", neumaticoVehiculo);
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
			neumatico12 = "../imagenes/ruedaAgricola.png";
			neumatico34Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico34", neumaticoVehiculo);
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
			neumatico11 = "../imagenes/ruedaAgricola.png";
			neumatico33Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico33", neumaticoVehiculo);
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
			neumatico10 = "../imagenes/ruedaAgricola.png";
			neumatico32Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico32", neumaticoVehiculo);
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
			neumatico9 = "../imagenes/ruedaAgricola.png";
			neumatico31Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico31", neumaticoVehiculo);
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
			neumatico16 = "../imagenes/ruedaAgricola.png";
			neumatico44Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico44", neumaticoVehiculo);
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
			neumatico15 = "../imagenes/ruedaAgricola.png";
			neumatico43Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico43", neumaticoVehiculo);
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
			neumatico14 = "../imagenes/ruedaAgricola.png";
			neumatico42Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico42", neumaticoVehiculo);
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
			neumatico13 = "../imagenes/ruedaAgricola.png";
			neumatico41Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico41", neumaticoVehiculo);
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
			neumatico20 = "../imagenes/ruedaAgricola.png";
			neumatico54Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico54", neumaticoVehiculo);
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
			neumatico19 = "../imagenes/ruedaAgricola.png";
			neumatico53Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico53", neumaticoVehiculo);
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
			neumatico18 = "../imagenes/ruedaAgricola.png";
			neumatico52Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico52", neumaticoVehiculo);
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
			neumatico17 = "../imagenes/ruedaAgricola.png";
			neumatico51Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico51", neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico54Disabled, "neumatico54", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
//		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
//		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico21() != null) {
			neumatico21 = "../imagenes/ruedaAgricola.png";
			neumaticoRepIzqDrag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(21);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepIzqDisabled, "neumaticoR21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico22() != null) {
			neumatico22 = "../imagenes/ruedaAgricola.png";
			neumaticoRepDerDrag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", neumaticoVehiculo);
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
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico1() != null) {
			neumatico1 = "../imagenes/ruedaAgricola.png";
			neumatico11Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico11", neumaticoVehiculo);
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
			neumatico2 = "../imagenes/ruedaAgricola.png";
			neumatico12Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico12", neumaticoVehiculo);
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
			neumatico3 = "../imagenes/ruedaAgricola.png";
			neumatico13Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico13", neumaticoVehiculo);
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
			neumatico4 = "../imagenes/ruedaAgricola.png";
			neumatico14Drag= false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico14", neumaticoVehiculo);
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
			neumatico5 = "../imagenes/ruedaAgricola.png";
			neumatico21Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico21", neumaticoVehiculo);
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
			neumatico6 = "../imagenes/ruedaAgricola.png";
			neumatico22Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico22", neumaticoVehiculo);
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
			neumatico7 = "../imagenes/ruedaAgricola.png";
			neumatico23Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico23", neumaticoVehiculo);
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
			neumatico8 = "../imagenes/ruedaAgricola.png";
			neumatico24Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico24", neumaticoVehiculo);
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
			neumatico9 = "../imagenes/ruedaAgricola.png";
			neumatico31Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico31", neumaticoVehiculo);
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
			neumatico10 = "../imagenes/ruedaAgricola.png";
			neumatico32Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico32", neumaticoVehiculo);
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
			neumatico11 = "../imagenes/ruedaAgricola.png";
			neumatico33Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico33", neumaticoVehiculo);
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
			neumatico12 = "../imagenes/ruedaAgricola.png";
			neumatico34Drag= false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico34", neumaticoVehiculo);
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
			neumatico13 = "../imagenes/ruedaAgricola.png";
			neumatico41Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico41", neumaticoVehiculo);
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
			neumatico14 = "../imagenes/ruedaAgricola.png";
			neumatico42Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico42", neumaticoVehiculo);
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
			neumatico15 = "../imagenes/ruedaAgricola.png";
			neumatico43Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico43", neumaticoVehiculo);
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
			neumatico16 = "../imagenes/ruedaAgricola.png";
			neumatico44Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico44", neumaticoVehiculo);
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
			neumatico17 = "../imagenes/ruedaAgricola.png";
			neumatico51Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico51", neumaticoVehiculo);
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
			neumatico18 = "../imagenes/ruedaAgricola.png";
			neumatico52Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico52", neumaticoVehiculo);
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
			neumatico19 = "../imagenes/ruedaAgricola.png";
			neumatico53Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico53", neumaticoVehiculo);
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
			neumatico20 = "../imagenes/ruedaAgricola.png";
			neumatico54Drag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setVehiculo(vehiculoSeleccionado);
			neumaticoVehiculo.setPosicion(Integer.toString(J));
			mapaRelacionalNeumaticosOperacion.put("neumatico54", neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(J);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumatico54Disabled, "neumatico54", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
			J++;
		}
//		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
//		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico21() != null) {
			neumatico21 = "../imagenes/ruedaAgricola.png";
			neumaticoRepIzqDrag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(21));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR21", neumaticoVehiculo);
//			movimiento = new Movimiento();
//			movimiento.setId(movimientoId);
//			movimiento.setPosicionFinal(21);
//			movimiento.setVehiculo(vehiculoSeleccionado);
//			PosicionesVehiculo posicionesVehiculo = new PosicionesVehiculo(neumaticoRepIzqDisabled, "neumaticoR21", movimiento);
//			listaPosicionesActivasVehiculo.add(posicionesVehiculo);
//			listaPosicionesVehiculoPrevio.add(posicionesVehiculo);
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico22() != null) {
			neumatico22 = "../imagenes/ruedaAgricola.png";
			neumaticoRepDerDrag = false;
			neumaticoVehiculo = new NeumaticoVehiculo();
			neumaticoVehiculo.setId(neumaticoVehiculoId);
			neumaticoVehiculo.setPosicion(Integer.toString(22));
			mapaRelacionalNeumaticosOperacion.put("neumaticoR22", neumaticoVehiculo);
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
	
	public void actualizarFondosNeumaticosPermisosDrag() {
		int J = 1;
		
//		neumaticoVehiculoId.setIdVehiculo(this.vehiculoSeleccionado.getIdVehiculo());
//		neumaticoVehiculo.setId(neumaticoVehiculoId);
//		neumaticoVehiculo.setVehiculo(this.vehiculoSeleccionado);
		
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico1() != null) {
			neumatico1 = "../imagenes/ruedaAgricola.png";
			neumatico11Drag = false;
			neumatico11Drop = false;
//			neumaticoVehiculo.setPosicion(J);
//			mapa.put("neumatico11", neumaticoVehiculo);
//			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico2() != null) {
			neumatico2 = "../imagenes/ruedaAgricola.png";
			neumatico12Drag = false;
			neumatico12Drop = false;
//			neumaticoVehiculo.setPosicion(J);
//			mapa.put("neumatico12", neumaticoVehiculo);
//			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico3() != null) {
			neumatico3 = "../imagenes/ruedaAgricola.png";
			neumatico13Drag = false;
			neumatico13Drop = false;
//			neumaticoVehiculo.setPosicion(J);
//			mapa.put("neumatico13", neumaticoVehiculo);
//			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico4() != null) {
			neumatico4 = "../imagenes/ruedaAgricola.png";
			neumatico14Drag = false;
			neumatico14Drop = false;
//			neumaticoVehiculo.setPosicion(J);
//			mapa.put("neumatico14", neumaticoVehiculo);
//			J++;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico5() != null) {
			neumatico5 = "../imagenes/ruedaAgricola.png";
			neumatico21Drag = false;
			neumatico21Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico6() != null) {
			neumatico6 = "../imagenes/ruedaAgricola.png";
			neumatico22Drag = false;
			neumatico22Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico7() != null) {
			neumatico7 = "../imagenes/ruedaAgricola.png";
			neumatico23Drag = false;
			neumatico23Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico8() != null) {
			neumatico8 = "../imagenes/ruedaAgricola.png";
			neumatico24Drag = false;
			neumatico24Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico9() != null) {
			neumatico9 = "../imagenes/ruedaAgricola.png";
			neumatico31Drag = false;
			neumatico31Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico10() != null) {
			neumatico10 = "../imagenes/ruedaAgricola.png";
			neumatico32Drag = false;
			neumatico32Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico11() != null) {
			neumatico11 = "../imagenes/ruedaAgricola.png";
			neumatico33Drag = false;
			neumatico33Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico12() != null) {
			neumatico12 = "../imagenes/ruedaAgricola.png";
			neumatico34Drag = false;
			neumatico34Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico13() != null) {
			neumatico13 = "../imagenes/ruedaAgricola.png";
			neumatico41Drag = false;
			neumatico41Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico14() != null) {
			neumatico14 = "../imagenes/ruedaAgricola.png";
			neumatico42Drag = false;
			neumatico42Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico15() != null) {
			neumatico15 = "../imagenes/ruedaAgricola.png";
			neumatico43Drag = false;
			neumatico43Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico16() != null) {
			neumatico16 = "../imagenes/ruedaAgricola.png";
			neumatico44Drag = false;
			neumatico44Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico17() != null) {
			neumatico17 = "../imagenes/ruedaAgricola.png";
			neumatico51Drag = false;
			neumatico51Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico18() != null) {
			neumatico18 = "../imagenes/ruedaAgricola.png";
			neumatico52Drag = false;
			neumatico52Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico19() != null) {
			neumatico19 = "../imagenes/ruedaAgricola.png";
			neumatico53Drag = false;
			neumatico53Drop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico20() != null) {
			neumatico20 = "../imagenes/ruedaAgricola.png";
			neumatico54Drag = false;
			neumatico54Drop = false;
		}
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico21("S");
		serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().setNeumatico22("S");
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico21() != null) {
			neumatico21 = "../imagenes/ruedaAgricola.png";
			neumaticoRepIzqDrag = false;
			neumaticoRepIzqDrop = false;
		}
		if(serviciosVentanaEsquemaEjes.getEsquemaEjeSeleccionado().getNeumatico22() != null) {
			neumatico22 = "../imagenes/ruedaAgricola.png";
			neumaticoRepDerDrag = false;
			neumaticoRepDerDrop = false;
		}
	}
	
	public void cargarListasAlmacen(){
		almacenListos = NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("estado", "L");
		setNeumaticoDataModel(new NeumaticoDataModel(almacenListos));
		
		almacenReencauche = NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("estado", "L");
		
		almacenReclamos = NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("estado", "L");
		
		almacenReparacion = NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("estado", "L");
		
		almacenDarBaja = NeumaticoDAO.getInstancia().buscarEntidadesPorPropiedad("estado", "L");
	}
	
	public void mensajeNeumaticoSeleccionado(ActionEvent actionEvent){
		if(neumaticoSeleccionado.getIdNeumatico() != null){
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
			System.out.println("si selecciono");
		}
	}
	
	public void habilitarBotonRegresar(SelectEvent filaSeleccionada){
		regresarDisabled = false;
	}
	
	public void inhabilitarBotonRegresar(UnselectEvent filaSeleccionada){
		regresarDisabled = true;
	}
	
	public void botonCancelar(){
		System.out.println("Boton Limpiar");
		cargarRemanenteInicial();
		this.recorridoAcumulado = 0.0;
		this.totalMovimiento = 0.0;
		this.tipoDesgasteSeleccionado = new TipoDesgaste();
		this.presion = 0.0;
		this.observacionesMovimiento = "";
		System.out.println("almacen activo ="+almacenActivo);
		regresarNeumatico();
	}
	
	public void regresarNeumatico(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje","El neumático se ha removido");
		switch (almacenActivo) {
				case "L":
					almacenListos.remove(neumaticoSeleccionado);
					setNeumaticoDataModel(new NeumaticoDataModel(almacenListos));
					context.addMessage(null, mensaje);
					neumaticoSeleccionado = new Neumatico();
			        neumatico2 = "../imagenes/ruedaAgricola.png";
			        neumatico12Drag = false;
					break;
				case "R":
					almacenReencauche.remove(neumaticoSeleccionado);
					context.addMessage(null, mensaje);
					break;
				case "P":
					almacenReparacion.remove(neumaticoSeleccionado);
					context.addMessage(null, mensaje);
					break;
				case "C":
					almacenReclamos.remove(neumaticoSeleccionado);
					context.addMessage(null, mensaje);
					break;
				case "B":
					almacenDarBaja.remove(neumaticoSeleccionado);
					context.addMessage(null, mensaje);
					break;
			}
		
	}
	
	public void abrirVentanaAlmacen(String nombreAlmacen){
		Neumatico neumPrueba = new Neumatico();
		neumPrueba.setCodInterno("PROBANDO EXITO");
		System.out.println("Tamaño del alamacen: "+almacenListos.size());
		almacenListos.add(neumPrueba);
		System.out.println("Tamaño del alamacen: "+almacenListos.size());

		
		switch (nombreAlmacen) {
			case "L":
				almacenActivo = "L";
				setNeumaticoDataModel(new NeumaticoDataModel(almacenListos));
				System.out.println("ENTRAMOS POR LISTO PARA MONTAR");
				break;
			case "R":
				almacenActivo = "R";
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReencauche));
				System.out.println("ENTRAMOS POR REENCAUCHE");
				break;
			case "P":
				almacenActivo = "P";
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReparacion));
				System.out.println("ENTRAMOS POR REPARACION");
				break;
			case "C":
				almacenActivo = "C";
				setNeumaticoDataModel(new NeumaticoDataModel(almacenReclamos));
				System.out.println("ENTRAMOS POR RECLAMO");
				break;
			case "B":
				almacenActivo = "B";
				setNeumaticoDataModel(new NeumaticoDataModel(almacenDarBaja));
				System.out.println("ENTRAMOS POR BAJA");
				break;
		}
	}
	
	public void procederDesmontaje(){
		movimientoActualId.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
	//	movimientoActualId.setIdNeumatico();
		movimientoActual.setId(movimientoActualId);
		
		neumaticoSeleccionado.setCodInterno("21321PRUEBA");
		
		movimientoActual.setNeumatico(neumaticoSeleccionado);
		movimientoActual.setVehiculo(vehiculoSeleccionado);
		movimientoActual.setRecorridoAcumulado(recorridoAcumulado);
		movimientoActual.setRemanenteSuperiorA((double) remanenteSuperiorA);
		movimientoActual.setRemanenteSuperiorB((double) remanenteSuperiorB);
		movimientoActual.setRemanenteSuperiorC((double) remanenteSuperiorC);
		movimientoActual.setRemanenteSuperiorD((double) remanenteSuperiorD);
		movimientoActual.setRemanenteIzquierdoA((double) remanenteIzquierdoA);
		movimientoActual.setRemanenteIzquierdoB((double) remanenteIzquierdoB);
		movimientoActual.setRemanenteIzquierdoC((double) remanenteIzquierdoC);
		movimientoActual.setRemanenteIzquierdoD((double) remanenteIzquierdoD);
		movimientoActual.setRemanenteDiagonalA((double) remanenteInferiorA);
		movimientoActual.setRemanenteDiagonalB((double) remanenteInferiorB);
		movimientoActual.setRemanenteDiagonalC((double) remanenteInferiorC);
		movimientoActual.setRemanenteDiagonalD((double) remanenteInferiorD);
		movimientoActual.setRemanenteDerechoA((double) remanenteDerechaA);
		movimientoActual.setRemanenteDerechoB((double) remanenteDerechaB);
		movimientoActual.setRemanenteDerechoC((double) remanenteDerechaC);
		movimientoActual.setRemanenteDerechoD((double) remanenteDerechaD);
		movimientoActual.setRemanenteMovimiento((double) totalMovimiento);
		//movimientoActual.setPosicionInicial(posicionInicial);
		//movimientoActual.setPosicionFinal(posicionFinal);
		movimientoActual.setKilometraje(movimiento.getKilometraje());
		movimientoActual.setPresion(presion);
		movimientoActual.setTipoDesgaste(tipoDesgasteSeleccionado);
		movimientoActual.setObservaciones(observacionesMovimiento);
	
		listaMovimiento.add(movimientoActual);
		for (int i = 0; i < listaMovimiento.size() ; i++) {
			System.out.println(listaMovimiento.get(i).getNeumatico().getCodInterno());
		}
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
		//if((movimientoActualId.getFecha() != null) && (movimientoActual.getKilometraje() != 0.0)) {
			
	        String draggedComponet = (String) event.getDragId();
	        nombreRueda = obtenerNombreRueda(draggedComponet);
	        
	        neumaticoSeleccionado = ((NeumaticoVehiculo) mapaRelacionalNeumaticosOperacion.get(nombreRueda)).getNeumatico();
	        Droppable componente = (Droppable)event.getComponent();
	        almacenActivo = componente.getId();
	        
	        
	        switch (almacenActivo) {
				case "L":
					almacenListos.add(neumaticoSeleccionado);
			        neumatico2 = "../imagenes/ruedaTransparente.png";
			        neumatico12Drag = true;
					System.out.println("ENTRAMOS POR LISTO PARA MONTAR");
					break;
				case "R":
					almacenReencauche.add(neumaticoSeleccionado);
					System.out.println("ENTRAMOS POR REENCAUCHE");
					break;
				case "P":
					almacenReparacion.add(neumaticoSeleccionado);
					System.out.println("ENTRAMOS POR REPARACION");
					break;
				case "C":
					almacenReclamos.add(neumaticoSeleccionado);
					System.out.println("ENTRAMOS POR RECLAMO");
					break;
				case "B":
					almacenDarBaja.add(neumaticoSeleccionado);
					System.out.println("ENTRAMOS POR BAJA");
					break;
			}
			//RequestContext.getCurrentInstance().addCallbackParam("tarea", "DND");
	//	}  else {
			//mensajes.error("ATENCIÓN", "Los campos Kilometraje Desmonte y Fecha no pueden estar vacíos");
		//}
    }
	
	public String obtenerNombreRueda(String rueda){
		String nombreRueda="";
		for (int i = 0; i < rueda.length() ; i++) {
			if(i>= 15){
				nombreRueda = nombreRueda+rueda.charAt(i);
			}
		}
		return nombreRueda;
	}
	
	public void validarDatosDesmontajes(){
		//String tipoDesgaste = ((TipoDesgaste)TipoDesgasteDAO.getInstancia().buscarEntidadPorClave(tipoDesgasteSeleccionado.getIdTipoDesgaste())).getNombre();
		//System.out.println("tipo desde DAO: "+ tipoDesgaste);
		//System.out.println("presion: "+presion);
		if(remanenteSuperiorA !=0.0 && remanenteSuperiorB !=0.0 && remanenteSuperiorC !=0.0 && remanenteSuperiorD !=0.0 
		   && remanenteIzquierdoA !=0.0 && remanenteIzquierdoB !=0.0 && remanenteIzquierdoC !=0.0 && remanenteIzquierdoD !=0.0
		   && remanenteInferiorA !=0.0 && remanenteInferiorB !=0.0 && remanenteInferiorC !=0.0 && remanenteInferiorD !=0.0
		   && remanenteDerechaA !=0.0 && remanenteDerechaB !=0.0 && remanenteDerechaC !=0.0 && remanenteDerechaD !=0.0
		   && recorridoAcumulado !=0.0 && presion != 0.0 && tipoDesgasteSeleccionado.getIdTipoDesgaste() != null){
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");
			System.out.println("validar desmontajes");
		}else{
			mensajes.error("ATENCIÓN", "Existen elementos sin complentar!!");
			System.out.println("Falta elementos por completar");
		}
	}
	
	public void procesarOperacion(ActionEvent actionEvent){
		
	}
	
	public void calcularTotalSuperior(AjaxBehaviorEvent event){
		totalSuperior = (remanenteSuperiorA+remanenteSuperiorB+remanenteSuperiorC+remanenteSuperiorD)/4;
		calcularTotalMovimiento(event);
	}
	
	public void calcularTotalInferior(AjaxBehaviorEvent event){
		totalInferior = (remanenteInferiorA+remanenteInferiorB+remanenteInferiorC+remanenteInferiorD)/4;
		calcularTotalMovimiento(event);
	}
	
	public void calcularTotalIzquierdo(AjaxBehaviorEvent event){
		totalIzquierdo = (remanenteIzquierdoA+remanenteIzquierdoB+remanenteIzquierdoC+remanenteIzquierdoD)/4;
		calcularTotalMovimiento(event);
	}
	
	public void calcularTotalDerecha(AjaxBehaviorEvent event){
		totalDerecha = (remanenteDerechaA+remanenteDerechaB+remanenteDerechaC+remanenteDerechaD)/4;
		calcularTotalMovimiento(event);
	}
	
	public void calcularTotalMovimiento(AjaxBehaviorEvent event){
		totalMovimiento = (totalSuperior+totalInferior+totalIzquierdo+totalDerecha)/4;
	}
	
	public void calcularRecorridoAcumulado(AjaxBehaviorEvent event){
		recorridoAcumulado = movimientoActual.getKilometraje() - vehiculoSeleccionado.getKilometraje();
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

	public boolean isNeumatico11Drag() {
		return neumatico11Drag;
	}

	public void setNeumatico11Drag(boolean neumatico11Drag) {
		this.neumatico11Drag = neumatico11Drag;
	}

	public boolean isNeumatico12Drag() {
		return neumatico12Drag;
	}

	public void setNeumatico12Drag(boolean neumatico12Drag) {
		this.neumatico12Drag = neumatico12Drag;
	}

	public boolean isNeumatico13Drag() {
		return neumatico13Drag;
	}

	public void setNeumatico13Drag(boolean neumatico13Drag) {
		this.neumatico13Drag = neumatico13Drag;
	}

	public boolean isNeumatico14Drag() {
		return neumatico14Drag;
	}

	public void setNeumatico14Drag(boolean neumatico14Drag) {
		this.neumatico14Drag = neumatico14Drag;
	}

	public boolean isNeumatico21Drag() {
		return neumatico21Drag;
	}

	public void setNeumatico21Drag(boolean neumatico21Drag) {
		this.neumatico21Drag = neumatico21Drag;
	}

	public boolean isNeumatico22Drag() {
		return neumatico22Drag;
	}

	public void setNeumatico22Drag(boolean neumatico22Drag) {
		this.neumatico22Drag = neumatico22Drag;
	}

	public boolean isNeumatico23Drag() {
		return neumatico23Drag;
	}

	public void setNeumatico23Drag(boolean neumatico23Drag) {
		this.neumatico23Drag = neumatico23Drag;
	}

	public boolean isNeumatico24Drag() {
		return neumatico24Drag;
	}

	public void setNeumatico24Drag(boolean neumatico24Drag) {
		this.neumatico24Drag = neumatico24Drag;
	}

	public boolean isNeumatico31Drag() {
		return neumatico31Drag;
	}

	public void setNeumatico31Drag(boolean neumatico31Drag) {
		this.neumatico31Drag = neumatico31Drag;
	}

	public boolean isNeumatico32Drag() {
		return neumatico32Drag;
	}

	public void setNeumatico32Drag(boolean neumatico32Drag) {
		this.neumatico32Drag = neumatico32Drag;
	}

	public boolean isNeumatico33Drag() {
		return neumatico33Drag;
	}

	public void setNeumatico33Drag(boolean neumatico33Drag) {
		this.neumatico33Drag = neumatico33Drag;
	}

	public boolean isNeumatico34Drag() {
		return neumatico34Drag;
	}

	public void setNeumatico34Drag(boolean neumatico34Drag) {
		this.neumatico34Drag = neumatico34Drag;
	}

	public boolean isNeumatico41Drag() {
		return neumatico41Drag;
	}

	public void setNeumatico41Drag(boolean neumatico41Drag) {
		this.neumatico41Drag = neumatico41Drag;
	}

	public boolean isNeumatico42Drag() {
		return neumatico42Drag;
	}

	public void setNeumatico42Drag(boolean neumatico42Drag) {
		this.neumatico42Drag = neumatico42Drag;
	}

	public boolean isNeumatico43Drag() {
		return neumatico43Drag;
	}

	public void setNeumatico43Drag(boolean neumatico43Drag) {
		this.neumatico43Drag = neumatico43Drag;
	}

	public boolean isNeumatico44Drag() {
		return neumatico44Drag;
	}

	public void setNeumatico44Drag(boolean neumatico44Drag) {
		this.neumatico44Drag = neumatico44Drag;
	}

	public boolean isNeumatico51Drag() {
		return neumatico51Drag;
	}

	public void setNeumatico51Drag(boolean neumatico51Drag) {
		this.neumatico51Drag = neumatico51Drag;
	}

	public boolean isNeumatico52Drag() {
		return neumatico52Drag;
	}

	public void setNeumatico52Drag(boolean neumatico52Drag) {
		this.neumatico52Drag = neumatico52Drag;
	}

	public boolean isNeumatico53Drag() {
		return neumatico53Drag;
	}

	public void setNeumatico53Drag(boolean neumatico53Drag) {
		this.neumatico53Drag = neumatico53Drag;
	}

	public boolean isNeumatico54Drag() {
		return neumatico54Drag;
	}

	public void setNeumatico54Drag(boolean neumatico54Drag) {
		this.neumatico54Drag = neumatico54Drag;
	}

	public boolean isNeumaticoRepIzqDrag() {
		return neumaticoRepIzqDrag;
	}

	public void setNeumaticoRepIzqDrag(boolean neumaticoRepIzqDrag) {
		this.neumaticoRepIzqDrag = neumaticoRepIzqDrag;
	}

	public boolean isNeumaticoRepDerDrag() {
		return neumaticoRepDerDrag;
	}

	public void setNeumaticoRepDerDrag(boolean neumaticoRepDerDrag) {
		this.neumaticoRepDerDrag = neumaticoRepDerDrag;
	}

	public boolean isNeumatico11Drop() {
		return neumatico11Drop;
	}

	public void setNeumatico11Drop(boolean neumatico11Drop) {
		this.neumatico11Drop = neumatico11Drop;
	}

	public boolean isNeumatico12Drop() {
		return neumatico12Drop;
	}

	public void setNeumatico12Drop(boolean neumatico12Drop) {
		this.neumatico12Drop = neumatico12Drop;
	}

	public boolean isNeumatico13Drop() {
		return neumatico13Drop;
	}

	public void setNeumatico13Drop(boolean neumatico13Drop) {
		this.neumatico13Drop = neumatico13Drop;
	}

	public boolean isNeumatico14Drop() {
		return neumatico14Drop;
	}

	public void setNeumatico14Drop(boolean neumatico14Drop) {
		this.neumatico14Drop = neumatico14Drop;
	}

	public boolean isNeumatico21Drop() {
		return neumatico21Drop;
	}

	public void setNeumatico21Drop(boolean neumatico21Drop) {
		this.neumatico21Drop = neumatico21Drop;
	}

	public boolean isNeumatico22Drop() {
		return neumatico22Drop;
	}

	public void setNeumatico22Drop(boolean neumatico22Drop) {
		this.neumatico22Drop = neumatico22Drop;
	}

	public boolean isNeumatico23Drop() {
		return neumatico23Drop;
	}

	public void setNeumatico23Drop(boolean neumatico23Drop) {
		this.neumatico23Drop = neumatico23Drop;
	}

	public boolean isNeumatico24Drop() {
		return neumatico24Drop;
	}

	public void setNeumatico24Drop(boolean neumatico24Drop) {
		this.neumatico24Drop = neumatico24Drop;
	}

	public boolean isNeumatico31Drop() {
		return neumatico31Drop;
	}

	public void setNeumatico31Drop(boolean neumatico31Drop) {
		this.neumatico31Drop = neumatico31Drop;
	}

	public boolean isNeumatico32Drop() {
		return neumatico32Drop;
	}

	public void setNeumatico32Drop(boolean neumatico32Drop) {
		this.neumatico32Drop = neumatico32Drop;
	}

	public boolean isNeumatico33Drop() {
		return neumatico33Drop;
	}

	public void setNeumatico33Drop(boolean neumatico33Drop) {
		this.neumatico33Drop = neumatico33Drop;
	}

	public boolean isNeumatico34Drop() {
		return neumatico34Drop;
	}

	public void setNeumatico34Drop(boolean neumatico34Drop) {
		this.neumatico34Drop = neumatico34Drop;
	}

	public boolean isNeumatico41Drop() {
		return neumatico41Drop;
	}

	public void setNeumatico41Drop(boolean neumatico41Drop) {
		this.neumatico41Drop = neumatico41Drop;
	}

	public boolean isNeumatico42Drop() {
		return neumatico42Drop;
	}

	public void setNeumatico42Drop(boolean neumatico42Drop) {
		this.neumatico42Drop = neumatico42Drop;
	}

	public boolean isNeumatico43Drop() {
		return neumatico43Drop;
	}

	public void setNeumatico43Drop(boolean neumatico43Drop) {
		this.neumatico43Drop = neumatico43Drop;
	}

	public boolean isNeumatico44Drop() {
		return neumatico44Drop;
	}

	public void setNeumatico44Drop(boolean neumatico44Drop) {
		this.neumatico44Drop = neumatico44Drop;
	}

	public boolean isNeumatico51Drop() {
		return neumatico51Drop;
	}

	public void setNeumatico51Drop(boolean neumatico51Drop) {
		this.neumatico51Drop = neumatico51Drop;
	}

	public boolean isNeumatico52Drop() {
		return neumatico52Drop;
	}

	public void setNeumatico52Drop(boolean neumatico52Drop) {
		this.neumatico52Drop = neumatico52Drop;
	}

	public boolean isNeumatico53Drop() {
		return neumatico53Drop;
	}

	public void setNeumatico53Drop(boolean neumatico53Drop) {
		this.neumatico53Drop = neumatico53Drop;
	}

	public boolean isNeumatico54Drop() {
		return neumatico54Drop;
	}

	public void setNeumatico54Drop(boolean neumatico54Drop) {
		this.neumatico54Drop = neumatico54Drop;
	}

	public boolean isNeumaticoRepIzqDrop() {
		return neumaticoRepIzqDrop;
	}

	public void setNeumaticoRepIzqDrop(boolean neumaticoRepIzqDrop) {
		this.neumaticoRepIzqDrop = neumaticoRepIzqDrop;
	}

	public boolean isNeumaticoRepDerDrop() {
		return neumaticoRepDerDrop;
	}

	public void setNeumaticoRepDerDrop(boolean neumaticoRepDerDrop) {
		this.neumaticoRepDerDrop = neumaticoRepDerDrop;
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
	
	public double getPresion() {
		return presion;
	}

	public void setPresion(double presion) {
		this.presion = presion;
	}

	public RutaVehiculo getRutaVehiculo() {
		return rutaVehiculo;
	}

	public void setRutaVehiculo(RutaVehiculo rutaVehiculo) {
		this.rutaVehiculo = rutaVehiculo;
	}

	public List<Neumatico> getAlmacenListos() {
		return almacenListos;
	}

	public void setAlmacenListos(List<Neumatico> almacenListos) {
		this.almacenListos = almacenListos;
	}

	public Neumatico getNeumaticoSeleccionado() {
		return neumaticoSeleccionado;
	}

	public void setNeumaticoSeleccionado(Neumatico neumaticoSeleccionado) {
		this.neumaticoSeleccionado = neumaticoSeleccionado;
	}

	public NeumaticoDataModel getNeumaticoDataModel() {
		return neumaticoDataModel;
	}

	public void setNeumaticoDataModel(NeumaticoDataModel neumaticoDataModel) {
		this.neumaticoDataModel = neumaticoDataModel;
	}

	public TipoDesgaste getTipoDesgasteSeleccionado() {
		return tipoDesgasteSeleccionado;
	}

	public void setTipoDesgasteSeleccionado(TipoDesgaste tipoDesgasteSeleccionado) {
		this.tipoDesgasteSeleccionado = tipoDesgasteSeleccionado;
	}		

	public String getObservacionesMovimiento() {
		return observacionesMovimiento;
	}

	public void setObservacionesMovimiento(String observacionesMovimiento) {
		this.observacionesMovimiento = observacionesMovimiento;
	}

	public List<Neumatico> getAlmacenReencauche() {
		return almacenReencauche;
	}

	public void setAlmacenReencauche(List<Neumatico> almacenReencauche) {
		this.almacenReencauche = almacenReencauche;
	}

	public List<Neumatico> getAlmacenReparacion() {
		return almacenReparacion;
	}

	public void setAlmacenReparacion(List<Neumatico> almacenReparacion) {
		this.almacenReparacion = almacenReparacion;
	}

	public List<Neumatico> getAlmacenReclamos() {
		return almacenReclamos;
	}

	public void setAlmacenReclamos(List<Neumatico> almacenReclamos) {
		this.almacenReclamos = almacenReclamos;
	}

	public List<Neumatico> getAlmacenDarBaja() {
		return almacenDarBaja;
	}

	public void setAlmacenDarBaja(List<Neumatico> almacenDarBaja) {
		this.almacenDarBaja = almacenDarBaja;
	}

	public String getAlmacenActivo() {
		return almacenActivo;
	}

	public void setAlmacenActivo(String almacenActivo) {
		this.almacenActivo = almacenActivo;
	}

	public boolean isRegresarDisabled() {
		return regresarDisabled;
	}

	public void setRegresarDisabled(boolean regresarDisabled) {
		this.regresarDisabled = regresarDisabled;
	}

	public List<Movimiento> getListaMovimiento() {
		return listaMovimiento;
	}

	public void setListaMovimiento(List<Movimiento> listaMovimiento) {
		this.listaMovimiento = listaMovimiento;
	}

	public double getRecorridoAcumulado() {
		return recorridoAcumulado;
	}

	public void setRecorridoAcumulado(double recorridoAcumulado) {
		this.recorridoAcumulado = recorridoAcumulado;
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

	public void setTotalSuperior(Double totalSuperior) {
		this.totalSuperior = totalSuperior;
	}

	public Double getTotalSuperior() {
		return totalSuperior;
	}

	public Double getTotalInferior() {
		return totalInferior;
	}

	public Double getTotalIzquierdo() {
		return totalIzquierdo;
	}

	public Double getTotalDerecha() {
		return totalDerecha;
	}

	public Double getTotalMovimiento() {
		return totalMovimiento;
	}

	public void setTotalInferior(Double totalInferior) {
		this.totalInferior = totalInferior;
	}

	public void setTotalIzquierdo(Double totalIzquierdo) {
		this.totalIzquierdo = totalIzquierdo;
	}

	public void setTotalDerecha(Double totalDerecha) {
		this.totalDerecha = totalDerecha;
	}

	public void setTotalMovimiento(Double totalMovimiento) {
		this.totalMovimiento = totalMovimiento;
	}

	public void setRecorridoAcumulado(Double recorridoAcumulado) {
		this.recorridoAcumulado = recorridoAcumulado;
	}

	public Movimiento getMovimientoActual() {
		return movimientoActual;
	}

	public void setMovimientoActual(Movimiento movimientoActual) {
		this.movimientoActual = movimientoActual;
	}

	public MovimientoId getMovimientoActualId() {
		return movimientoActualId;
	}

	public void setMovimientoActualId(MovimientoId movimientoActualId) {
		this.movimientoActualId = movimientoActualId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
}

