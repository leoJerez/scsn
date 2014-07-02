package cauca.scsn.modelo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.eclipse.persistence.internal.libraries.asm.tree.JumpInsnNode;
import org.primefaces.context.RequestContext;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.dao.EsquemaEjeDAO;
import cauca.scsn.modelo.datamodel.EsquemaEjeDataModel;
import cauca.scsn.modelo.entidad.EsquemaEje;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

/**
 * Esta clase es utilizada para prestar los servicios requeridos en la ventana que maneja los esquemas de ejes, básicamente
 *es un maestro para los esquemas de ejes
 * @author José Leonardo Jerez Araujo
 * @author jerez.leo13@gmail.com
 *
 */
@ManagedBean
@SessionScoped
public class ServiciosVentanaEsquemaEjes implements ServiciosMaestros {
	
	private EsquemaEjeDAO 			esquemaEjeDAO;											//atributo usado para hacer operaciones sobre el modelo
	private EsquemaEje				esquemaEje;												//atributo utilizado para la creación de un nuevo esquema de ejes
	private EsquemaEje				esquemaEjeSeleccionado;									//atributo utilizado para recibir la selección de un registro en la tabla mostrada al usuario para su posterior realización de cambios
	private Empresa					empresa;												//atributo utilizado para albergar los datos de la empresa y registrarla en la sesión
	private EsquemaEjeDataModel		esquemaEjeDataModel;									//este atributo ayuda a la carga del modelo de datos que usará la tabla		
	private boolean					modificar			 	= false;						//Estos 3 atributos se usan para activar y desactivar las funciones de la 
	private boolean					eliminar			 	= false;							//botonera mostrada en la pantalla y posteriormente enviar los parámetros correspondientes
	private boolean 				consultar			 	= false;
	private ControladorMensajes 	mensajes			 	= new ControladorMensajes();	//crea una instancia de la clase ControladorMensajes con el fin de manipular y mostrar todos los mensajes a través de ella
	private ActionEvent 			eventoCancelar;
	private String					mensajeEliminar;	
	private List<EsquemaEje>		listaEsquemaEjes	 	= new ArrayList<EsquemaEje>();	//esta lista se utiliza para llenar el modelo de datos usado en la tabla 
	private List<String> 			listaTipo 				= new ArrayList<String>();		//esta lista se usa para cargar el comboBox para "Tipo" mostrado en la pantallla
	private String					tipo;													//recibe la selección realizada por el usuario en la pantalla y así poder dedecidir cómo construir el nombre
	private List<String>			listaNeumatico			= new ArrayList<String>();		//estas 2 listas se usan para mantener un registro ordenado de los ejes y 
	private List<String>			listaEje				= new ArrayList<String>();			//neumáticos seleccionados y así poder construir el nombre del esquema más fácilmente
	private int 					contadorEje1 			= 0;							//estos contadores son usados 
	private int 					contadorEje2 			= 0;
	private int 					contadorEje3 			= 0;
	private int 					contadorEje4 			= 0;
	private int 					contadorEje5 			= 0;
	private boolean					neumatico1Activo		= false;						//estos booleans se usan para realizar las operaciones debidas cuando se presenta un cambio de estado en alguno de los botones
	private boolean					neumatico2Activo		= false;							//que representan un neumático o un eje
	private boolean					neumatico3Activo		= false;
	private boolean					neumatico4Activo		= false;
	private boolean					neumatico5Activo		= false;
	private boolean					neumatico6Activo		= false;
	private boolean					neumatico7Activo		= false;
	private boolean					neumatico8Activo		= false;
	private boolean					neumatico9Activo		= false;
	private boolean					neumatico10Activo		= false;
	private boolean					neumatico11Activo		= false;
	private boolean					neumatico12Activo		= false;
	private boolean					neumatico13Activo		= false;
	private boolean					neumatico14Activo		= false;
	private boolean					neumatico15Activo		= false;
	private boolean					neumatico16Activo		= false;
	private boolean					neumatico17Activo		= false;
	private boolean					neumatico18Activo		= false;
	private boolean					neumatico19Activo		= false;
	private boolean					neumatico20Activo		= false;
	private boolean					eje1Activo				= false;
	private boolean					eje2Activo				= false;
	private boolean					eje3Activo				= false;
	private boolean					eje4Activo				= false;
	private boolean					eje5Activo				= false;
	private String					fondoNeumatico1;											//estos atributos se usan para realizar los cambios de imagen de fondo en los botones que representan los neumáticos 
	private String					fondoNeumatico2;												//y ejes, gracias a estos atributos se efectua el cambio de URL para la búsqueda de la imagen de fondo a través de Primefaces en el lado del cliente
	private String					fondoNeumatico3;
	private String					fondoNeumatico4;
	private String					fondoNeumatico5;
	private String					fondoNeumatico6;
	private String					fondoNeumatico7;
	private String					fondoNeumatico8;
	private String					fondoNeumatico9;
	private String					fondoNeumatico10;
	private String					fondoNeumatico11;
	private String					fondoNeumatico12;
	private String					fondoNeumatico13;
	private String					fondoNeumatico14;
	private String					fondoNeumatico15;
	private String					fondoNeumatico16;
	private String					fondoNeumatico17;
	private String					fondoNeumatico18;
	private String					fondoNeumatico19;
	private String					fondoNeumatico20;
	private String					fondoEje1;
	private String					fondoEje2;
	private String					fondoEje3;
	private String					fondoEje4;
	private String					fondoEje5;
	
	public ServiciosVentanaEsquemaEjes() {
		/*
		 * Constructor de la clase, debemos registrar los valores iniciales de los atributos esenciales para este servicio, es decir, debemos darle valor a
		 * los fondos de los neumáticos y ejes, instanciar los esquema de ejes que se usarán, la clase DAO para hacer las operaciones sobre el Modelo
		 * y efectuar los métodos que cargarán los datos en las tablas, combos y también cargar las listas con campos en blanco
		 */
		super();
		fondoNeumatico1=fondoNeumatico2=fondoNeumatico3=fondoNeumatico4=fondoNeumatico5=fondoNeumatico6
				=fondoNeumatico7=fondoNeumatico8=fondoNeumatico9=fondoNeumatico10=fondoNeumatico11
				=fondoNeumatico12=fondoNeumatico13=fondoNeumatico14=fondoNeumatico15=fondoNeumatico16
				=fondoNeumatico17=fondoNeumatico18=fondoNeumatico19=fondoNeumatico20 = "background-color: GREEN;";
		fondoEje1 = fondoEje2 = fondoEje3 = fondoEje4 = fondoEje5 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
		esquemaEje					=	new EsquemaEje();
		esquemaEjeSeleccionado		=	new EsquemaEje();
		esquemaEjeDAO 				= 	esquemaEjeDAO.getInstancia();
		empresaEnLaSesion();
		llenarDataModel();
		llenarTipo();
		llenarListaNeumatico();
		llenarListaEje();
		ServiciosVentanaLogin servicio = new ServiciosVentanaLogin();
		//servicio.setRenderNuevoEsquema(true);
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, pero este tiene la particularidad de que antes de
	 * efectuar la operación de guardar el registro valida que el esquema de eje tenga al menos un neumático registrado en él, además,
	 * verifica si la opción de modificar está activa o no, pues dependiendo de ella puede realizar modificaciones o crear un nuevo registro
	 * una vez que efectua los cambios sobre el Modelo, este método envía un mensaje al cliente que indica el resultado de las operaciones y luego 
	 * se encarga de instanciar los métodos necesarios para volver los elementos
	 * utilizados en su estado original
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @param ActionEvent actionEvent
	 * @return sin ningún retorno
	 * @exception se manejan los errores al momento de efectuar los cambios en la base de datos
	 */
	public void guardarOModificar(ActionEvent actionEvent) {
		
		try {
			if (verificarAntesDeGuardar()){
				if (!modificar) {
					esquemaEjeDAO.insertarOActualizar(this.esquemaEje);
					mensajes.informativo("Operación exitosa", "EsquemaEje: "+ this.esquemaEje.getNombreEsquema() +" ha sido guardado!");
				} else {
					esquemaEjeDAO.actualizar(this.esquemaEjeSeleccionado);
					mensajes.informativo("Operación exitosa", "EsquemaEje: "+ this.esquemaEjeSeleccionado.getNombreEsquema() +" ha sido guardado!");
				}
				llenarDataModel();
			} else {
				mensajes.error("Atención!", "No puede guardar un esquema de ejes sin neumáticos");
			}
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Existen datos que no concuerdan con lo establecido en el modelo de datos");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, y se encarga de la eliminación lógica de los registros en la base de datos,
	 * recarga del modelo de datos para que se muestren los cambios en la tabla mostrada al cliente y una vez que efectua los cambios sobre el Modelo, este método envía un mensaje al cliente que indica el resultado de las operaciones y luego 
	 * se encarga de instanciar los métodos necesarios para volver los elementos
	 * utilizados en su estado original
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @param ActionEvent actionEvent
	 * @return sin ningún retorno
	 * @exception se manejan los errores al momento de efectuar los cambios en la base de datos
	 */
	public void eliminar(ActionEvent actionEvent) {
		try {
			esquemaEjeDAO.eliminarLogicamente(this.esquemaEjeSeleccionado);
			llenarDataModel();
			mensajes.informativo("Operación exitosa", "EsquemaEje: "+ this.esquemaEjeSeleccionado.getNombreEsquema() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}
	
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, y se devolver los elementos o variables utilizadas
	 * en otros métodos a su estado original, de tal forma que posteriormente puedan ser utilizadas sin problemas
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @param ActionEvent actionEvent
	 * @return sin ningún retorno
	 * @exception se manejan los errores al momento de efectuar los cambios en la base de datos
	 */
	public void cancelar(ActionEvent actionEvent) {
		this.contadorEje1 		= 0;
		this.contadorEje2 		= 0;
		this.contadorEje3 		= 0;
		this.contadorEje4 		= 0;
		this.contadorEje5 		= 0;
		neumatico1Activo		= false;
		neumatico2Activo		= false;
		neumatico3Activo		= false;
		neumatico4Activo		= false;
		neumatico5Activo		= false;
		neumatico6Activo		= false;
		neumatico7Activo		= false;
		neumatico8Activo		= false;
		neumatico9Activo		= false;
		neumatico10Activo		= false;
		neumatico11Activo		= false;
		neumatico12Activo		= false;
		neumatico13Activo		= false;
		neumatico14Activo		= false;
		neumatico15Activo		= false;
		neumatico16Activo		= false;
		neumatico17Activo		= false;
		neumatico18Activo		= false;
		neumatico19Activo		= false;
		neumatico20Activo		= false;
		eje1Activo				= false;
		eje2Activo				= false;
		eje3Activo				= false;
		eje4Activo				= false;
		eje5Activo				= false;
		listaNeumatico.clear();
		llenarListaNeumatico();
		listaEje.clear();
		llenarListaEje();
		fondoNeumatico1=fondoNeumatico2=fondoNeumatico3=fondoNeumatico4=fondoNeumatico5=fondoNeumatico6
				=fondoNeumatico7=fondoNeumatico8=fondoNeumatico9=fondoNeumatico10=fondoNeumatico11
				=fondoNeumatico12=fondoNeumatico13=fondoNeumatico14=fondoNeumatico15=fondoNeumatico16
				=fondoNeumatico17=fondoNeumatico18=fondoNeumatico19=fondoNeumatico20 = "background-color: GREEN;";
		fondoEje1 = fondoEje2 = fondoEje3 = fondoEje4 = fondoEje5 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
		tipo					= "";
		this.esquemaEje = null;
		this.esquemaEje = new EsquemaEje();
		this.esquemaEjeSeleccionado = null;
		this.esquemaEjeSeleccionado = new EsquemaEje();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, y se encarga de la activación de la operación "Modificar"
	 * que posteriormente servirá para la selección del tipo de operación a realizar en el método guardarOModificar, además envía los
	 * parámetros correspondientes para que el cliente decida qué ventana va a abrir al presionar un botón de la botonera.
	 * 
	 * También hace un llamado al método verificarDatosActivos para afectar las variables correspondientes a los fondos de los
	 * botones de ejes y neumáticos que se van a mostrar según lo registrado en el esquema de eje que se selecciones de la tabla
	 * 
	 * Es necesario acotar que el cliente debe enviar la selección a través de la variable esquemaEjeSeleccionado, ya que si no se enviará un mensaje
	 * de error indicando lo ocurrido y se limpiará los elementos haciendo uso del método cancelar
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @return sin ningún retorno
	 */
	public void activarModificar() {
		if (esquemaEjeSeleccionado.getIdEsquemaEje() != null){
			modificar = true;
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
			tipo = esquemaEjeSeleccionado.getTipo();
			verificarDatosActivos();
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún esquema de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, y funciona de forma similar al método activarModificar
	 * pero activando, obviamente, la operación de consulta  
	 *  
	 * También hace un llamado al método verificarDatosActivos para afectar las variables correspondientes a los fondos de los
	 * botones de ejes y neumáticos que se van a mostrar según lo registrado en el esquema de eje que se selecciones de la tabla
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @return sin ningún retorno
	 */
	public void activarConsultar() {
		if (esquemaEjeSeleccionado.getIdEsquemaEje() != null){
			setConsultar(true);
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
			verificarDatosActivos();
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún esquemaEje de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas, y funciona de forma similar al método activarModificar y activarConsultar
	 * pero activando, obviamente, la operación de eliminar, pero sin realizar ninguna verificación, ya que no se requiere mostrar los ejes activos en el lado del cliente
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @return sin ningún retorno
	 */
	public void activarEliminar() {
		if (esquemaEjeSeleccionado.getIdEsquemaEje() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ esquemaEjeSeleccionado.getNombreEsquema() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún esquemaEje de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	/**
	 * Este método se encarga de verificar que al guardar se haya seleccionado al menos un neumático en el esquema de ejes, de esta forma se evita
	 * guardar un registro en blanco. Además, limpia los ejes seleccionados que no se les actvió ningún neumático
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @return true o false
	 */
	public boolean verificarAntesDeGuardar() {
		if ((contadorEje1 == 0) && (contadorEje2 == 0) && (contadorEje3 == 0) && (contadorEje4 == 0) && (contadorEje5 == 0)){
			return false;
		} else {
			if (modificar){
				if ((esquemaEjeSeleccionado.getEje1() != null) && (contadorEje1 == 0)){
					esquemaEjeSeleccionado.setEje1(null);
				}
				if ((esquemaEjeSeleccionado.getEje2() != null) && (contadorEje2 == 0)){
					esquemaEjeSeleccionado.setEje2(null);
				}
				if ((esquemaEjeSeleccionado.getEje3() != null) && (contadorEje3 == 0)){
					esquemaEjeSeleccionado.setEje3(null);
				}
				if ((esquemaEjeSeleccionado.getEje4() != null) && (contadorEje4 == 0)){
					esquemaEjeSeleccionado.setEje4(null);
				}
				if ((esquemaEjeSeleccionado.getEje5() != null) && (contadorEje5 == 0)){
					esquemaEjeSeleccionado.setEje5(null);
				}
			} else {
				if ((esquemaEje.getEje1() != null) && (contadorEje1 == 0)){
					esquemaEje.setEje1(null);
				}
				if ((esquemaEje.getEje2() != null) && (contadorEje2 == 0)){
					esquemaEje.setEje2(null);
				}
				if ((esquemaEje.getEje3() != null) && (contadorEje3 == 0)){
					esquemaEje.setEje3(null);
				}
				if ((esquemaEje.getEje4() != null) && (contadorEje4 == 0)){
					esquemaEje.setEje4(null);
				}
				if ((esquemaEje.getEje5() != null) && (contadorEje5 == 0)){
					esquemaEje.setEje5(null);
				}
			}
			return true;	
		}
	}
	
	/**
	 * Este método se encarga de verificar los ejes y neumáticos actvios en un esquema de ejes seleccionado de la base de datos y así poder
	 * modificar la URL de su imagen de fondo para mostrarlo en el lado del cliente 
	 * 
	 * @author José Leonardo Jerez Araujo
	 * @author jerez.leo13@gmail.com 
	 * @return sin ningún retorno
	 */
	public void verificarDatosActivos() {
		if (esquemaEjeSeleccionado.getEje1() != null){
			eje1Activo = true;
			fondoEje1 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
			if (esquemaEjeSeleccionado.getNeumatico1() != null){
				neumatico1Activo = true;
				fondoNeumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje1++;
			}
			if (esquemaEjeSeleccionado.getNeumatico2() != null){
				neumatico2Activo = true;
				fondoNeumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje1++;
			}
			if (esquemaEjeSeleccionado.getNeumatico3() != null){
				neumatico3Activo = true;
				fondoNeumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje1++;
			}
			if (esquemaEjeSeleccionado.getNeumatico4() != null){
				neumatico4Activo = true;
				fondoNeumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje1++;
			}
		}
		if (esquemaEjeSeleccionado.getEje2() != null){
			eje2Activo = true;
			fondoEje2 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
			if (esquemaEjeSeleccionado.getNeumatico5() != null){
				neumatico5Activo = true;
				fondoNeumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje2++;
			}
			if (esquemaEjeSeleccionado.getNeumatico6() != null){
				neumatico6Activo = true;
				fondoNeumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje2++;
			}
			if (esquemaEjeSeleccionado.getNeumatico7() != null){
				neumatico7Activo = true;
				fondoNeumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje2++;
			}
			if (esquemaEjeSeleccionado.getNeumatico8() != null){
				neumatico8Activo = true;
				fondoNeumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje2++;
			}
		}
		if (esquemaEjeSeleccionado.getEje3() != null){
			eje3Activo = true;
			fondoEje3 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
			if (esquemaEjeSeleccionado.getNeumatico9() != null){
				neumatico9Activo = true;
				fondoNeumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje3++;
			}
			if (esquemaEjeSeleccionado.getNeumatico10() != null){
				neumatico10Activo = true;
				fondoNeumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje3++;
			}
			if (esquemaEjeSeleccionado.getNeumatico11() != null){
				neumatico11Activo = true;
				fondoNeumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje3++;
			}
			if (esquemaEjeSeleccionado.getNeumatico12() != null){
				neumatico12Activo = true;
				fondoNeumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje3++;
			}
		}
		if (esquemaEjeSeleccionado.getEje4() != null){
			eje4Activo = true;
			fondoEje4 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
			if (esquemaEjeSeleccionado.getNeumatico13() != null){
				neumatico13Activo = true;
				fondoNeumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje4++;
			}
			if (esquemaEjeSeleccionado.getNeumatico14() != null){
				neumatico14Activo = true;
				fondoNeumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje4++;
			}
			if (esquemaEjeSeleccionado.getNeumatico15() != null){
				neumatico15Activo = true;
				fondoNeumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje4++;
			}
			if (esquemaEjeSeleccionado.getNeumatico16() != null){
				neumatico16Activo = true;
				fondoNeumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje4++;
			}
		}
		if (esquemaEjeSeleccionado.getEje5() != null){
			eje5Activo = true;
			fondoEje5 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
			if (esquemaEjeSeleccionado.getNeumatico17() != null){
				neumatico17Activo = true;
				fondoNeumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje5++;
			}
			if (esquemaEjeSeleccionado.getNeumatico18() != null){
				neumatico18Activo = true;
				fondoNeumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje5++;
			}
			if (esquemaEjeSeleccionado.getNeumatico19() != null){
				neumatico19Activo = true;
				fondoNeumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje5++;
			}
			if (esquemaEjeSeleccionado.getNeumatico20() != null){
				neumatico20Activo = true;
				fondoNeumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
				contadorEje5++;
			}
		}
	}

	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas de maestros, se encarga de llenar el modelo de datos
	 * utilizado para mostrar los registros el la tabla del lado del cliente
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void llenarDataModel() {
		//setListaEsquemaEjes(esquemaEjeDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		setListaEsquemaEjes(esquemaEjeDAO.buscarTodasEntidades());
		setEsquemaEjeDataModel(new EsquemaEjeDataModel(this.listaEsquemaEjes));
	}
	
	/**
	 * Este método es común entre las clases que prestan servicios a las ventanas de maestros, se encarga de extraer la empresa
	 * que está utilizando una sesión de la aplicación y así setear el atributo empresa en el esquema de eje
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.esquemaEje.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}
	
	/**
	 * Este método se encarga de cargar los valores que se mostrarán en el comboBox para "Tipo" en el lado del cliente
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void llenarTipo() {
		listaTipo.add("Chuto");
		listaTipo.add("Remolque");
	}
	
	/**
	 * Este método se encarga de cargar la lista de neumáticos con valores vacios con la intención de apartar un espacio para cada
	 * neumático posible en un esquema de ejes y así poder realizar posibles verificaciones y operaciones sobre ella
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void llenarListaNeumatico() {
		for (int i=0; i < 20; i++) {
			listaNeumatico.add(i, "");
		}
	}
	
	/**
	 * Este método se encarga de cargar la lista de ejes con valores vacios con la intención de apartar un espacio para cada
	 * eje posible en un esquema de ejes y así poder realizar posibles verificaciones y operaciones sobre ella
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void llenarListaEje() {
		for (int i=0; i < 5; i++) {
			listaEje.add(i, "");
		}
	}

	/**
	 * Este método se encarga de activar o desactivar el eje, pero para ello primero verifica si el eje ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del eje en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al eje 1
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarEje1(ActionEvent actionEvent) {		
		if (eje1Activo) {
			if (!neumatico1Activo && !neumatico2Activo && !neumatico3Activo && !neumatico4Activo){
				fondoEje1 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
				listaEje.set(0, "");
				if (modificar){
					esquemaEjeSeleccionado.setEje1(null);
				} else {
					esquemaEje.setEje1(null);
				}	
				eje1Activo = false;
				construirNombre();
			} else{
				mensajes.error("Atención!", "No puede desactivar un eje si tiene neumáticos activos");
			}
		} else {
			if (!tipo.equals("")){
				fondoEje1 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
				eje1Activo = true;
				listaEje.set(0, "S");
				if (modificar){
					esquemaEjeSeleccionado.setEje1("S");
				} else {
					esquemaEje.setEje1("S");
				}	
				construirNombre();
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar el eje, pero para ello primero verifica si el eje ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del eje en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al eje 2
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarEje2(ActionEvent actionEvent) {		
		if (eje2Activo) {
			if (!neumatico5Activo && !neumatico6Activo && !neumatico7Activo && !neumatico8Activo){
				fondoEje2 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
				listaEje.set(1, "");
				if (modificar){
					esquemaEjeSeleccionado.setEje2(null);
				} else {
					esquemaEje.setEje2(null);
				}		
				eje2Activo = false;
				construirNombre();
			} else{
				mensajes.error("Atención!", "No puede desactivar un eje si tiene neumáticos activos");
			}
		} else {
			if (!tipo.equals("")){
				fondoEje2 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
				eje2Activo = true;
				listaEje.set(1, "S");
				if (modificar){
					esquemaEjeSeleccionado.setEje2("S");
				} else {
					esquemaEje.setEje2("S");
				}	
				construirNombre();
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar el eje, pero para ello primero verifica si el eje ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del eje en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al eje 3
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarEje3(ActionEvent actionEvent) {		
		if (eje3Activo) {
			if (!neumatico9Activo && !neumatico10Activo && !neumatico11Activo && !neumatico12Activo){
				fondoEje3 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
				listaEje.set(2, "");
				if (modificar){
					esquemaEjeSeleccionado.setEje3(null);
				} else {
					esquemaEje.setEje3(null);
				}	
				esquemaEje.setEje3(null);	
				eje3Activo = false;
				construirNombre();
			} else{
				mensajes.error("Atención!", "No puede desactivar un eje si tiene neumáticos activos");
			}
		} else {
			if (!tipo.equals("")){
				fondoEje3 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
				eje3Activo = true;
				listaEje.set(2, "S");
				if (modificar){
					esquemaEjeSeleccionado.setEje3("S");
				} else {
					esquemaEje.setEje3("S");
				}	
				construirNombre();
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar el eje, pero para ello primero verifica si el eje ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del eje en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al eje 4
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarEje4(ActionEvent actionEvent) {		
		if (eje4Activo) {
			if (!neumatico13Activo && !neumatico14Activo && !neumatico15Activo && !neumatico16Activo){
				fondoEje4 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
				listaEje.set(3, "");
				if (modificar){
					esquemaEjeSeleccionado.setEje4(null);
				} else {
					esquemaEje.setEje4(null);
				}		
				eje4Activo = false;
				construirNombre();
			} else{
				mensajes.error("Atención!", "No puede desactivar un eje si tiene neumáticos activos");
			}
		} else {
			if (!tipo.equals("")){
				fondoEje4 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
				eje4Activo = true;
				listaEje.set(3, "S");
				if (modificar){
					esquemaEjeSeleccionado.setEje4("S");
				} else {
					esquemaEje.setEje4("S");
				}	
				construirNombre();
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar el eje, pero para ello primero verifica si el eje ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del eje en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al eje 5
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarEje5(ActionEvent actionEvent) {		
		if (eje5Activo) {
			if (!neumatico17Activo && !neumatico18Activo && !neumatico19Activo && !neumatico20Activo){
				fondoEje5 = "background: url(../imagenes/ejeExtraOpaco.png) no-repeat; background-position: center;";
				listaEje.set(4, "");
				if (modificar){
					esquemaEjeSeleccionado.setEje5(null);
				} else {
					esquemaEje.setEje5(null);
				}	
				eje5Activo = false;
				construirNombre();
			} else{
				mensajes.error("Atención!", "No puede desactivar un eje si tiene neumáticos activos");
			}
		} else {
			if (!tipo.equals("")){
				fondoEje5 = "background: url(../imagenes/eje.png) no-repeat; background-position: center;";
				eje5Activo = true;
				listaEje.set(4, "S");
				if (modificar){
					esquemaEjeSeleccionado.setEje5("S");
				} else {
					esquemaEje.setEje5("S");
				}	
				construirNombre();	
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 1
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 *  @param ActionEvent actionEvent, usado para sentir el evento en un botón del lado del cliente
	 */
	public void activarNeumatico1(ActionEvent actionEvent) {
		if (neumatico1Activo) {
			fondoNeumatico1 = "background-color: GREEN";
			listaNeumatico.set(0, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico1(null);
			} else {
				esquemaEje.setNeumatico1(null);
			}	
			neumatico1Activo = false;
			contadorEje1--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje1Activo){
					fondoNeumatico1 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico1Activo = true;
					listaNeumatico.set(0, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico1("S");
					} else {
						esquemaEje.setNeumatico1("S");
					}	
					contadorEje1++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 2
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico2() {
		if (neumatico2Activo) {
			fondoNeumatico2 = "background-color: GREEN";
			listaNeumatico.set(1, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico2(null);
			} else {
				esquemaEje.setNeumatico2(null);
			}		
			neumatico2Activo = false;
			contadorEje1--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje1Activo){
					fondoNeumatico2 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico2Activo = true;
					listaNeumatico.add(1, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico2("S");
					} else {
						esquemaEje.setNeumatico2("S");
					}	
					contadorEje1++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Dede seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 1
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico3() {
		if (neumatico3Activo) {
			fondoNeumatico3 = "background-color: GREEN";
			listaNeumatico.set(2, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico3(null);
			} else {
				esquemaEje.setNeumatico3(null);
			}		
			neumatico3Activo = false;
			contadorEje1--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje1Activo){
					fondoNeumatico3 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico3Activo = true;
					listaNeumatico.add(2, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico3("S");
					} else {
						esquemaEje.setNeumatico3("S");
					}	
					contadorEje1++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Dede seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 4
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico4() {
		if (neumatico4Activo) {
			fondoNeumatico4 = "background-color: GREEN";
			listaNeumatico.set(3, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico4(null);
			} else {
				esquemaEje.setNeumatico4(null);
			}		
			neumatico4Activo = false;
			contadorEje1--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje1Activo){
					fondoNeumatico4 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico4Activo = true;
					listaNeumatico.add(3, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico4("S");
					} else {
						esquemaEje.setNeumatico4("S");
					}	
					contadorEje1++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 5
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico5() {
		if (neumatico5Activo) {
			fondoNeumatico5 = "background-color: GREEN";
			listaNeumatico.set(4, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico5(null);
			} else {
				esquemaEje.setNeumatico5(null);
			}	
			neumatico5Activo = false;
			contadorEje2--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje2Activo){
					fondoNeumatico5 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico5Activo = true;
					listaNeumatico.add(4, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico5("S");
					} else {
						esquemaEje.setNeumatico5("S");
					}	
					contadorEje2++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 6
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico6() {
		if (neumatico6Activo) {
			fondoNeumatico6 = "background-color: GREEN";
			listaNeumatico.set(5, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico6(null);
			} else {
				esquemaEje.setNeumatico6(null);
			}	
			neumatico6Activo = false;
			contadorEje2--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje2Activo){
					fondoNeumatico6 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico6Activo = true;
					listaNeumatico.add(5, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico6("S");
					} else {
						esquemaEje.setNeumatico6("S");
					}	
					contadorEje2++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 7
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico7() {
		if (neumatico7Activo) {
			fondoNeumatico7 = "background-color: GREEN";
			listaNeumatico.set(6, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico7(null);
			} else {
				esquemaEje.setNeumatico7(null);
			}		
			neumatico7Activo = false;
			contadorEje2--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje2Activo){
					fondoNeumatico7 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico7Activo = true;
					listaNeumatico.add(6, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico7("S");
					} else {
						esquemaEje.setNeumatico7("S");
					}	
					contadorEje2++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 8
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico8() {
		if (neumatico8Activo) {
			fondoNeumatico8 = "background-color: GREEN";
			listaNeumatico.set(7, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico8(null);
			} else {
				esquemaEje.setNeumatico8(null);
			}		
			neumatico8Activo = false;
			contadorEje2--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje2Activo){
					fondoNeumatico8 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico8Activo = true;
					listaNeumatico.add(7, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico8("S");
					} else {
						esquemaEje.setNeumatico8("S");
					}	
					contadorEje2++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 9
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico9() {
		if (neumatico9Activo) {
			fondoNeumatico9 = "background-color: GREEN";
			listaNeumatico.set(8, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico9(null);
			} else {
				esquemaEje.setNeumatico9(null);
			}		
			neumatico9Activo = false;
			contadorEje3--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje3Activo){
					fondoNeumatico9 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico9Activo = true;
					listaNeumatico.add(8, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico9("S");
					} else {
						esquemaEje.setNeumatico9("S");
					}	
					contadorEje3++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 10
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico10() {
		if (neumatico10Activo) {
			fondoNeumatico10 = "background-color: GREEN";
			listaNeumatico.set(9, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico10(null);
			} else {
				esquemaEje.setNeumatico10(null);
			}	
			neumatico10Activo = false;
			contadorEje3--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje3Activo){
					fondoNeumatico10 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico10Activo = true;
					listaNeumatico.add(9, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico10("S");
					} else {
						esquemaEje.setNeumatico10("S");
					}	
					contadorEje3++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 11
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico11() {
		if (neumatico11Activo) {
			fondoNeumatico11 = "background-color: GREEN";
			listaNeumatico.set(10, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico11(null);
			} else {
				esquemaEje.setNeumatico11(null);
			}		
			neumatico11Activo = false;
			contadorEje3--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje3Activo){
					fondoNeumatico11 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico11Activo = true;
					listaNeumatico.add(10, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico11("S");
					} else {
						esquemaEje.setNeumatico11("S");
					}	
					contadorEje3++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 12
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico12() {
		if (neumatico12Activo) {
			fondoNeumatico12 = "background-color: GREEN";
			listaNeumatico.set(11, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico12(null);
			} else {
				esquemaEje.setNeumatico12(null);
			}		
			neumatico12Activo = false;
			contadorEje3--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje3Activo){
					fondoNeumatico12 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico12Activo = true;
					listaNeumatico.add(11, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico12("S");
					} else {
						esquemaEje.setNeumatico12("S");
					}	
					contadorEje3++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 13
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico13() {
		if (neumatico13Activo) {
			fondoNeumatico13 = "background-color: GREEN";
			listaNeumatico.set(12, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico13(null);
			} else {
				esquemaEje.setNeumatico13(null);
			}		
			neumatico13Activo = false;
			contadorEje4--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje4Activo){
					fondoNeumatico13 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico13Activo = true;
					listaNeumatico.add(12, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico13("S");
					} else {
						esquemaEje.setNeumatico13("S");
					}	
					contadorEje4++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 14
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico14() {
		if (neumatico14Activo) {
			fondoNeumatico14 = "background-color: GREEN";
			listaNeumatico.set(13, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico14(null);
			} else {
				esquemaEje.setNeumatico14(null);
			}		
			neumatico14Activo = false;
			contadorEje4--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje4Activo){
					fondoNeumatico14 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico14Activo = true;
					listaNeumatico.add(13, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico14("S");
					} else {
						esquemaEje.setNeumatico4("S");
					}	
					contadorEje4++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 15
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico15() {
		if (neumatico15Activo) {
			fondoNeumatico15 = "background-color: GREEN";
			listaNeumatico.set(14, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico15(null);
			} else {
				esquemaEje.setNeumatico15(null);
			}	
			esquemaEje.setNeumatico15(null);	
			neumatico15Activo = false;
			contadorEje4--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje4Activo){
					fondoNeumatico15 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico15Activo = true;
					listaNeumatico.add(14, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico15("S");
					} else {
						esquemaEje.setNeumatico15("S");
					}	
					contadorEje4++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 16
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico16() {
		if (neumatico16Activo) {
			fondoNeumatico16 = "background-color: GREEN";
			listaNeumatico.set(15, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico16(null);
			} else {
				esquemaEje.setNeumatico16(null);
			}		
			neumatico16Activo = false;
			contadorEje4--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje4Activo){
					fondoNeumatico16 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico16Activo = true;
					listaNeumatico.add(15, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico16("S");
					} else {
						esquemaEje.setNeumatico16("S");
					}	
					contadorEje4++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 17
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico17() {
		if (neumatico17Activo) {
			fondoNeumatico17 = "background-color: GREEN";
			listaNeumatico.set(16, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico17(null);
			} else {
				esquemaEje.setNeumatico17(null);
			}		
			neumatico17Activo = false;
			contadorEje5--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje5Activo){
					fondoNeumatico17 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico17Activo = true;
					listaNeumatico.add(16, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico17("S");
					} else {
						esquemaEje.setNeumatico17("S");
					}	
					contadorEje5++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 18
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico18() {
		if (neumatico18Activo) {
			fondoNeumatico18 = "background-color: GREEN";
			listaNeumatico.set(17, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico18(null);
			} else {
				esquemaEje.setNeumatico18(null);
			}		
			neumatico18Activo = false;
			contadorEje5--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje5Activo){
					fondoNeumatico18 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico18Activo = true;
					listaNeumatico.add(17, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico18("S");
					} else {
						esquemaEje.setNeumatico18("S");
					}	
					contadorEje5++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 19
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico19() {
		if (neumatico19Activo) {
			fondoNeumatico19 = "background-color: GREEN";
			listaNeumatico.set(18, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico19(null);
			} else {
				esquemaEje.setNeumatico19(null);
			}		
			neumatico19Activo = false;
			contadorEje5--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje5Activo){
					fondoNeumatico19 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico19Activo = true;
					listaNeumatico.add(18, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico19("S");
					} else {
						esquemaEje.setNeumatico19("S");
					}	
					contadorEje5++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de activar o desactivar un neumático, pero para ello primero verifica si el neumático ya está activo para desactivarlo, también
	 * verifica si se ha seleccionado un Tipo y un eje, luego modifica la URL del fondo para el botón de eje en el lado del servidor, modifica el valor
	 * del neumático en el esquema de eje que se esté afectando y al final hace un llamado al método que construye el nombre del esquema de eje.
	 * 
	 * En este caso se aplica al neumático 20
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void activarNeumatico20() {
		if (neumatico20Activo) {
			fondoNeumatico20 = "background-color: GREEN";
			listaNeumatico.set(19, "");
			if (modificar){
				esquemaEjeSeleccionado.setNeumatico20(null);
			} else {
				esquemaEje.setNeumatico20(null);
			}		
			neumatico20Activo = false;
			contadorEje5--;
			construirNombre();
		} else {
			if (!tipo.equals("")){
				if (eje5Activo){
					fondoNeumatico20 = "background: url(../imagenes/ruedaAgricola.png) no-repeat; background-position: center;";
					neumatico20Activo = true;
					listaNeumatico.add(19, "S");
					if (modificar){
						esquemaEjeSeleccionado.setNeumatico20("S");
					} else {
						esquemaEje.setNeumatico20("S");
					}	
					contadorEje5++;
					construirNombre();
				} else{
					mensajes.error("Atención!", "Primero debe activar el eje para este neumático");
				}
			} else{
				mensajes.error("Atención!", "Debe seleccionar un Tipo");
			}
		}
	}
	
	/**
	 * Este método se encarga de construir el nombre del esquema de eje, para ello debe verificar el Tipo seleccionado para colocar o no
	 * el prefijo (:) y concatenar los valores de los contadores de ejes. Después, se debe recorrer la cadena de caracteres del nombre
	 * para cambiar los ceros (0) por guiones (-).
	 * 
	 * Al finalizar debe afectar el esquema de eje con el que se esté trabajando para que luego se pueda guardar sin problemas
	 * 
	 * 
	 *  @author José Leonardo Jerez Araujo
	 *  @author jerez.leo13@gmail.com
	 *  @return sin ningún retorno
	 */
	public void construirNombre() {
		String nombre;
		String nombre1 = new String();
		
		if (tipo.equals("Chuto")){
			nombre = ""+contadorEje1+contadorEje2+contadorEje3+contadorEje4+contadorEje5;
		} else if (tipo.equals("Remolque")){
			nombre = ":"+contadorEje1+contadorEje2+contadorEje3+contadorEje4+contadorEje5;			
		} else {
			cancelar(eventoCancelar);
			nombre = "";
		}

		for (int i=0; i < nombre.length(); i++){
			if (nombre.charAt(i) == ':'){
				nombre1 = Character.toString(nombre.charAt(i));
			} else if (nombre.charAt(i) != '0'){
				nombre1 = nombre1+Character.toString(nombre.charAt(i));
			} else {
				nombre1 = nombre1+"-";
			}
		}
		
		if (modificar){
			esquemaEjeSeleccionado.setNombreEsquema(nombre1);
			esquemaEjeSeleccionado.setTipo(tipo);
		} else {
			esquemaEje.setNombreEsquema(nombre1);
			esquemaEje.setTipo(tipo);
		}
	}
	
	public EsquemaEjeDataModel getEsquemaEjeDataModel() {
		return esquemaEjeDataModel;
	}

	public void setEsquemaEjeDataModel(EsquemaEjeDataModel esquemaEjeDataModel) {
		this.esquemaEjeDataModel = esquemaEjeDataModel;
	}

	public EsquemaEje getEsquemaEje() {
		return esquemaEje;
	}

	public void setEsquemaEje(EsquemaEje esquemaEje) {
		this.esquemaEje = esquemaEje;
	}

	public List<EsquemaEje> getListaEsquemaEjes() {
		return listaEsquemaEjes;
	}

	public void setListaEsquemaEjes(List<EsquemaEje> listaEsquemaEjes) {
		this.listaEsquemaEjes = listaEsquemaEjes;
	}

	public EsquemaEje getEsquemaEjeSeleccionado() {
		return esquemaEjeSeleccionado;
	}

	public void setEsquemaEjeSeleccionado(EsquemaEje esquemaEjeSeleccionado) {
		this.esquemaEjeSeleccionado = esquemaEjeSeleccionado;
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

	public List<String> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<String> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFondoNeumatico1() {
		return fondoNeumatico1;
	}

	public void setFondoNeumatico1(String fondoNeumatico1) {
		this.fondoNeumatico1 = fondoNeumatico1;
	}

	public String getFondoNeumatico2() {
		return fondoNeumatico2;
	}

	public void setFondoNeumatico2(String fondoNeumatico2) {
		this.fondoNeumatico2 = fondoNeumatico2;
	}

	public String getFondoNeumatico3() {
		return fondoNeumatico3;
	}

	public void setFondoNeumatico3(String fondoNeumatico3) {
		this.fondoNeumatico3 = fondoNeumatico3;
	}

	public String getFondoNeumatico4() {
		return fondoNeumatico4;
	}

	public void setFondoNeumatico4(String fondoNeumatico4) {
		this.fondoNeumatico4 = fondoNeumatico4;
	}

	public String getFondoNeumatico5() {
		return fondoNeumatico5;
	}

	public void setFondoNeumatico5(String fondoNeumatico5) {
		this.fondoNeumatico5 = fondoNeumatico5;
	}

	public String getFondoNeumatico6() {
		return fondoNeumatico6;
	}

	public void setFondoNeumatico6(String fondoNeumatico6) {
		this.fondoNeumatico6 = fondoNeumatico6;
	}

	public String getFondoNeumatico7() {
		return fondoNeumatico7;
	}

	public void setFondoNeumatico7(String fondoNeumatico7) {
		this.fondoNeumatico7 = fondoNeumatico7;
	}

	public String getFondoNeumatico8() {
		return fondoNeumatico8;
	}

	public void setFondoNeumatico8(String fondoNeumatico8) {
		this.fondoNeumatico8 = fondoNeumatico8;
	}

	public String getFondoNeumatico9() {
		return fondoNeumatico9;
	}

	public void setFondoNeumatico9(String fondoNeumatico9) {
		this.fondoNeumatico9 = fondoNeumatico9;
	}

	public String getFondoNeumatico10() {
		return fondoNeumatico10;
	}

	public void setFondoNeumatico10(String fondoNeumatico10) {
		this.fondoNeumatico10 = fondoNeumatico10;
	}

	public String getFondoNeumatico11() {
		return fondoNeumatico11;
	}

	public void setFondoNeumatico11(String fondoNeumatico11) {
		this.fondoNeumatico11 = fondoNeumatico11;
	}

	public String getFondoNeumatico12() {
		return fondoNeumatico12;
	}

	public void setFondoNeumatico12(String fondoNeumatico12) {
		this.fondoNeumatico12 = fondoNeumatico12;
	}

	public String getFondoNeumatico13() {
		return fondoNeumatico13;
	}

	public void setFondoNeumatico13(String fondoNeumatico13) {
		this.fondoNeumatico13 = fondoNeumatico13;
	}

	public String getFondoNeumatico14() {
		return fondoNeumatico14;
	}

	public void setFondoNeumatico14(String fondoNeumatico14) {
		this.fondoNeumatico14 = fondoNeumatico14;
	}

	public String getFondoNeumatico15() {
		return fondoNeumatico15;
	}

	public void setFondoNeumatico15(String fondoNeumatico15) {
		this.fondoNeumatico15 = fondoNeumatico15;
	}

	public String getFondoNeumatico16() {
		return fondoNeumatico16;
	}

	public void setFondoNeumatico16(String fondoNeumatico16) {
		this.fondoNeumatico16 = fondoNeumatico16;
	}

	public String getFondoNeumatico17() {
		return fondoNeumatico17;
	}

	public void setFondoNeumatico17(String fondoNeumatico17) {
		this.fondoNeumatico17 = fondoNeumatico17;
	}

	public String getFondoNeumatico18() {
		return fondoNeumatico18;
	}

	public void setFondoNeumatico18(String fondoNeumatico18) {
		this.fondoNeumatico18 = fondoNeumatico18;
	}

	public String getFondoNeumatico19() {
		return fondoNeumatico19;
	}

	public void setFondoNeumatico19(String fondoNeumatico19) {
		this.fondoNeumatico19 = fondoNeumatico19;
	}

	public String getFondoNeumatico20() {
		return fondoNeumatico20;
	}

	public void setFondoNeumatico20(String fondoNeumatico20) {
		this.fondoNeumatico20 = fondoNeumatico20;
	}

	public String getFondoEje1() {
		return fondoEje1;
	}

	public void setFondoEje1(String fondoEje1) {
		this.fondoEje1 = fondoEje1;
	}

	public String getFondoEje2() {
		return fondoEje2;
	}

	public void setFondoEje2(String fondoEje2) {
		this.fondoEje2 = fondoEje2;
	}

	public String getFondoEje3() {
		return fondoEje3;
	}

	public void setFondoEje3(String fondoEje3) {
		this.fondoEje3 = fondoEje3;
	}

	public String getFondoEje4() {
		return fondoEje4;
	}

	public void setFondoEje4(String fondoEje4) {
		this.fondoEje4 = fondoEje4;
	}

	public String getFondoEje5() {
		return fondoEje5;
	}

	public void setFondoEje5(String fondoEje5) {
		this.fondoEje5 = fondoEje5;
	}

	public boolean isNeumatico1Activo() {
		return neumatico1Activo;
	}

	public void setNeumatico1Activo(boolean neumatico1Activo) {
		this.neumatico1Activo = neumatico1Activo;
	}

	public boolean isNeumatico2Activo() {
		return neumatico2Activo;
	}

	public void setNeumatico2Activo(boolean neumatico2Activo) {
		this.neumatico2Activo = neumatico2Activo;
	}

	public boolean isNeumatico3Activo() {
		return neumatico3Activo;
	}

	public void setNeumatico3Activo(boolean neumatico3Activo) {
		this.neumatico3Activo = neumatico3Activo;
	}

	public boolean isNeumatico4Activo() {
		return neumatico4Activo;
	}

	public void setNeumatico4Activo(boolean neumatico4Activo) {
		this.neumatico4Activo = neumatico4Activo;
	}

	public boolean isNeumatico5Activo() {
		return neumatico5Activo;
	}

	public void setNeumatico5Activo(boolean neumatico5Activo) {
		this.neumatico5Activo = neumatico5Activo;
	}

	public boolean isNeumatico6Activo() {
		return neumatico6Activo;
	}

	public void setNeumatico6Activo(boolean neumatico6Activo) {
		this.neumatico6Activo = neumatico6Activo;
	}

	public boolean isNeumatico7Activo() {
		return neumatico7Activo;
	}

	public void setNeumatico7Activo(boolean neumatico7Activo) {
		this.neumatico7Activo = neumatico7Activo;
	}

	public boolean isNeumatico8Activo() {
		return neumatico8Activo;
	}

	public void setNeumatico8Activo(boolean neumatico8Activo) {
		this.neumatico8Activo = neumatico8Activo;
	}

	public boolean isNeumatico9Activo() {
		return neumatico9Activo;
	}

	public void setNeumatico9Activo(boolean neumatico9Activo) {
		this.neumatico9Activo = neumatico9Activo;
	}

	public boolean isNeumatico10Activo() {
		return neumatico10Activo;
	}

	public void setNeumatico10Activo(boolean neumatico10Activo) {
		this.neumatico10Activo = neumatico10Activo;
	}

	public boolean isNeumatico11Activo() {
		return neumatico11Activo;
	}

	public void setNeumatico11Activo(boolean neumatico11Activo) {
		this.neumatico11Activo = neumatico11Activo;
	}

	public boolean isNeumatico12Activo() {
		return neumatico12Activo;
	}

	public void setNeumatico12Activo(boolean neumatico12Activo) {
		this.neumatico12Activo = neumatico12Activo;
	}

	public boolean isNeumatico13Activo() {
		return neumatico13Activo;
	}

	public void setNeumatico13Activo(boolean neumatico13Activo) {
		this.neumatico13Activo = neumatico13Activo;
	}

	public boolean isNeumatico14Activo() {
		return neumatico14Activo;
	}

	public void setNeumatico14Activo(boolean neumatico14Activo) {
		this.neumatico14Activo = neumatico14Activo;
	}

	public boolean isNeumatico15Activo() {
		return neumatico15Activo;
	}

	public void setNeumatico15Activo(boolean neumatico15Activo) {
		this.neumatico15Activo = neumatico15Activo;
	}

	public boolean isNeumatico16Activo() {
		return neumatico16Activo;
	}

	public void setNeumatico16Activo(boolean neumatico16Activo) {
		this.neumatico16Activo = neumatico16Activo;
	}

	public boolean isNeumatico17Activo() {
		return neumatico17Activo;
	}

	public void setNeumatico17Activo(boolean neumatico17Activo) {
		this.neumatico17Activo = neumatico17Activo;
	}

	public boolean isNeumatico18Activo() {
		return neumatico18Activo;
	}

	public void setNeumatico18Activo(boolean neumatico18Activo) {
		this.neumatico18Activo = neumatico18Activo;
	}

	public boolean isNeumatico19Activo() {
		return neumatico19Activo;
	}

	public void setNeumatico19Activo(boolean neumatico19Activo) {
		this.neumatico19Activo = neumatico19Activo;
	}

	public boolean isNeumatico20Activo() {
		return neumatico20Activo;
	}

	public void setNeumatico20Activo(boolean neumatico20Activo) {
		this.neumatico20Activo = neumatico20Activo;
	}

}
