package cauca.scsn.modelo.servicios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.beans.ValidadorBean;
import cauca.scsn.modelo.dao.DisenoDAO;
import cauca.scsn.modelo.dao.DisenoMedidaDAO;
import cauca.scsn.modelo.dao.MarcaNeumaticoDAO;
import cauca.scsn.modelo.dao.MedidaDAO;
import cauca.scsn.modelo.dao.MovimientoDAO;
import cauca.scsn.modelo.dao.NeumaticoDAO;
import cauca.scsn.modelo.dao.ProveedorDAO;
import cauca.scsn.modelo.datamodel.MedidaDataModel;
import cauca.scsn.modelo.datamodel.MovimientoDataModel;
import cauca.scsn.modelo.datamodel.NeumaticoDataModel;
import cauca.scsn.modelo.entidad.Diseno;
import cauca.scsn.modelo.entidad.DisenoMedida;
import cauca.scsn.modelo.entidad.Empresa;
import cauca.scsn.modelo.entidad.MarcaNeumatico;
import cauca.scsn.modelo.entidad.Medida;
import cauca.scsn.modelo.entidad.Movimiento;
import cauca.scsn.modelo.entidad.Neumatico;
import cauca.scsn.modelo.entidad.Proveedor;
import cauca.scsn.modelo.entidad.id.DisenoMedidaId;
import cauca.scsn.modelo.entidad.id.MovimientoId;
import cauca.scsn.modelo.interfaces.ServiciosMaestros;

@ManagedBean
@ViewScoped
public class ServiciosVentanaNeumatico implements ServiciosMaestros{

	private NeumaticoDAO 		neumaticoDAO;
	private Neumatico			neumatico;
	private Neumatico			neumaticoSeleccionado;
	private Empresa				empresa;
	private NeumaticoDataModel	neumaticoDataModel;
	private MovimientoDataModel movimientoDataModel;
	private MedidaDataModel		medidaDataModel;
	private boolean				modificar			 = false;
	private boolean				eliminar			 = false;
	private boolean 			consultar			 = false;
	private ValidadorBean		validador		     = new ValidadorBean();
	private boolean				unidireccional		 = false;
	private ControladorMensajes mensajes			 = new ControladorMensajes();
	private ActionEvent 		eventoCancelar;
	private String				mensajeEliminar;	
	private List<Neumatico>		listaNeumaticos 	 = new ArrayList<Neumatico>();
	private List<Medida>		listaMedida			 = new ArrayList<Medida>();
	private List<DisenoMedida>	listaDisenoMedida	 = new ArrayList<DisenoMedida>();
	private List<Diseno>		listaDisenos		 = new ArrayList<Diseno>();
	private List<Movimiento>	listaMovimientos	 = new ArrayList<Movimiento>();
	private MovimientoDAO		movimientoDAO;
	private DisenoMedidaDAO		disenoMedidaDAO;
	private DisenoDAO			disenoDAO;
	private MedidaDAO			medidaDAO;
	private ProveedorDAO		proveedorDAO;
	private MarcaNeumaticoDAO	marcaDAO;
	private Integer				idDisenoOriginal;
	private Integer				idMedida;
	private String				numeroFactura;
	private String				presionRecomendada;
	private Integer				idProveedor;
	private Integer				idMarca;
	private Date				fechaActual;
	private String				posicionMovimiento;
	private StreamedContent		streamedContentImagen;
	private MarcaNeumatico		marcaSeleccionada	  	= new MarcaNeumatico();
	private Proveedor 			proveedorSeleccionado 	= new Proveedor();
	private Diseno				disenoSeleccionado	  	= new Diseno();
	private Medida				medidaSeleccionada    	= new Medida();
	private Movimiento			movimientoSeleccionado	= new Movimiento();	
	private double 				costoKilometro 		  	= 0.0;
	private double				costoMm				  	= 0.0;
	private double				rendimientoMm			= 0.0;  
	private double				recorridoProyectado		= 0.0;  
	private int					tabIndex				= 0;
	private ServiciosVentanaMedida serviciosVentanaMedida = new ServiciosVentanaMedida();
	private int					rows;
	
	
	public ServiciosVentanaNeumatico() {
		super();
		neumatico 				= 	new Neumatico();
		neumaticoSeleccionado 	= 	new Neumatico();
		neumaticoDAO 			= 	NeumaticoDAO.getInstancia();
		validador 				= 	new ValidadorBean();
		fechaActual				= 	new Date();
		colocarImagenDefault();
		empresaEnLaSesion();
		llenarDataModel();

	}


	@Override
	public void guardarOModificar(ActionEvent actionEvent) {
		System.out.println("neumatico seleccionado: "+neumaticoSeleccionado.getCodInterno());
		
		try {
			System.out.println("entramos al try");
			if(validador.validarNeumatico(neumatico) || validador.validarNeumatico(neumaticoSeleccionado) ){
				System.out.println("neumatico selec "+neumaticoSeleccionado.getCodInterno());
				if (!modificar) {
					this.asignarValores(neumatico);
					System.out.println("Medida: " + idMedida);
					neumaticoDAO.insertarOActualizar(this.neumatico);
					neumaticoSeleccionado = new Neumatico();
					this.limpiarDatos();
					mensajes.informativo("Operación exitosa", "Neumatico: "+ this.neumatico.getCodInterno() +" ha sido guardado!");
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);

				}else{
					this.invertirFormato(neumaticoSeleccionado);
					this.asignarValores(neumaticoSeleccionado);
					neumaticoDAO.actualizar(this.neumaticoSeleccionado);
					this.limpiarDatos();
					RequestContext.getCurrentInstance().addCallbackParam("limpiar", true);
					mensajes.informativo("Operación exitosa", "Neumatico: "+ this.neumaticoSeleccionado.getCodInterno() +" ha sido guardado!");

				}
			}else{

				System.out.println("Entramos al else");
				//RequestContext.getCurrentInstance().addCallbackParam("limpiar", false);
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
	
	public void guardarMedida(ActionEvent actionEvent){
		serviciosVentanaMedida.guardarOModificar(actionEvent);
		medidasDiseno();
	}
	
	public void asignarValores(Neumatico neumaticoVacio){
		DisenoMedida disenoMedida = new DisenoMedida();
		DisenoMedidaId disenoMedidaId = new DisenoMedidaId();
		
		if(!modificar){
			neumaticoVacio.setIdDisenoActual(idDisenoOriginal);
			neumaticoVacio.setRecorridoAcumulado(0.0);
			neumaticoVacio.setRemanenteActual(neumaticoVacio.getRemanenteIngreso());
		}
		neumaticoVacio.setIdDisenoOriginal(idDisenoOriginal);
		disenoMedidaId.setIdDiseno(neumaticoVacio.getIdDisenoActual());
		disenoMedidaId.setIdMedida(idMedida);
		disenoMedida.setId(disenoMedidaId);
		neumaticoVacio.setDisenoMedida(disenoMedida);
		neumaticoVacio.setEstado("L");
		neumaticoVacio.setProveedor(this.buscarProveedor(idProveedor));
		seleccionarUnidireccional();
	}
	
	//Método que cambia el valor del parámetro unidireccional
	public void seleccionarUnidireccional(){
		if(unidireccional){
			neumatico.setUnidireccional("S");
			if(modificar)
				neumaticoSeleccionado.setUnidireccional("S");
		}else{
			neumatico.setUnidireccional("N");
			if(modificar)
				neumaticoSeleccionado.setUnidireccional("N");
		}
	}
	
	public void buscarUnidireccional(){
		if(neumaticoSeleccionado.getUnidireccional().equalsIgnoreCase("S"))
			unidireccional =  true;
		else
			unidireccional =  false;
	}
	
	//Metodo que filtra las medidas por diseño
	public void medidasDiseno(){
		//Inicializamos una variable del tipo Diseño para luego asignarle un id y pasarlo como parametro de busqueda
		Diseno disenoBuscar = new Diseno();
		
		//Se le asigna el id del diseño original
		//disenoBuscar.setIdDiseno(idDisenoOriginal);
		disenoBuscar = (Diseno)DisenoDAO.getInstancia().buscarEntidadPorClave(idDisenoOriginal);
		publicarImagen(disenoBuscar.getImagen());
		//Cargamos la lista de diseños con medidas, filtrando por el diseño previamente cargado
		
		this.setListaDisenoMedida(disenoMedidaDAO.getInstancia().buscarEntidadesPorPropiedad("diseno", disenoBuscar));
		
		//Se limpia la lista en caso de que se halla cargado previamente
		listaMedida.clear();

		System.out.println("Tamaño de la lista de medidas: " + listaDisenoMedida.size());
		//Ciclo que carga todos las medidas en la lista de medidas
		for(int i=0; i < listaDisenoMedida.size(); i++){
			System.out.println("Id de la medida: " + listaDisenoMedida.get(i).getId().getIdMedida());
			listaMedida.add((Medida) medidaDAO.getInstancia().buscarEntidadPorPropiedad("idMedida",listaDisenoMedida.get(i).getId().getIdMedida()));
		}
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
	
	//Metodo que llenara la lista de movimientos
	public void movimientosNeumatico(){
		MovimientoId movimientoId = new MovimientoId();
		Neumatico neumaticoBuscar = new Neumatico();

		//Asignamos el id del Neumatico
		neumaticoBuscar.setIdNeumatico(neumaticoSeleccionado.getIdNeumatico());
		movimientoId.setIdNeumatico(neumaticoSeleccionado.getIdNeumatico());
		
		this.setListaMovimientos(movimientoDAO.getInstancia().buscarEntidadesPorPropiedad("neumatico", neumaticoBuscar));
		this.cambiarFormatoMovimientos(listaMovimientos);
		this.setMovimientoDataModel(new MovimientoDataModel(this.listaMovimientos));
		
		for (int i = 0; i < listaMovimientos.size(); i++) {
			System.out.println("lista movimientos: "+listaMovimientos.get(i).getNeumatico());
		}
	}

	public void limpiarDatos(){
		idProveedor = 0;
		idDisenoOriginal = 0;
		listaDisenoMedida.clear();
		listaMedida.clear();
	}
	
	//Metodo que transforma el tipo de neumático guardado en la base de datos a una descripción explícita
	public String convertirTipo(String tipo){
		String nuevoTipo = "";
		
		if(tipo.equalsIgnoreCase("D")){
			nuevoTipo = "Direccional"; 
		}else if(tipo.equalsIgnoreCase("M")){
			nuevoTipo = "Mixto";
		}else if(tipo.equalsIgnoreCase("T")){
			nuevoTipo = "Tracción";
		}
		return nuevoTipo;
	}
	
	//Metodo que transforma el tipo de movimiento guardado en la base de datos a una descripción explícita
	public String convertirTipoMovimiento(String tipo){
		String nuevoTipo = "";

		if(tipo.equalsIgnoreCase("M")){
			nuevoTipo = "Montaje"; 
		}else if(tipo.equalsIgnoreCase("D")){
			nuevoTipo = "Desmontaje";
		}else if(tipo.equalsIgnoreCase("R")){
			nuevoTipo = "Rotación";
		}else if(tipo.equalsIgnoreCase("I")){
			nuevoTipo = "Inspección";
		}
		return nuevoTipo;
	}
	
	//Metodo que envía carga la variable de posición del movimiento dependiendo que tipo de movimiento sea
	public void mensajePosiciones(){
		String tipo = movimientoSeleccionado.getId().getTipoMovimiento();
		posicionMovimiento = "";
		
		if(tipo.equalsIgnoreCase("Montaje")){
			posicionMovimiento = "Posición final: " + movimientoSeleccionado.getPosicionFinal();
		}else if(tipo.equalsIgnoreCase("Desmontaje")){
			posicionMovimiento = "Posición final: " + movimientoSeleccionado.getPosicionFinal();
		}else if(tipo.equalsIgnoreCase("Rotación")){
			posicionMovimiento = "Posición inicial: " + movimientoSeleccionado.getPosicionInicial() + "Posición final: " + movimientoSeleccionado.getPosicionFinal();
		}else if(tipo.equalsIgnoreCase("Inspección")){
			posicionMovimiento = "Posición final: " + movimientoSeleccionado.getPosicionFinal();
		}else{
			posicionMovimiento = "";
		}
	}

	//Método que transforma el estado del neumático guardado en la base de datos a una descripción explícita
	public String convertirEstado(String estado){
		String nuevoEstado = "";
		
		if(estado.equalsIgnoreCase("L")){
			nuevoEstado = "Listo para Montar"; 
		}else if(estado.equalsIgnoreCase("M")){
			nuevoEstado = "Montado";
		}else if(estado.equalsIgnoreCase("R")){
			nuevoEstado = "En Reencauche";
		}else if(estado.equalsIgnoreCase("P")){
			nuevoEstado = "En Reparación";
		}else if(estado.equalsIgnoreCase("C")){
			nuevoEstado = "En Reclamo";
		}else if(estado.equalsIgnoreCase("B")){
			nuevoEstado = "De Baja";
		}
		return nuevoEstado;
	}
	
	//Método que transforma la condición del neumático guardado en la base de datos a una descripción explícita
	public String convertirCondicion(String condicion){
		String nuevaCondicion = "";
		
		if(condicion.equalsIgnoreCase("N")){
			nuevaCondicion = "Nuevo"; 
		}else if(condicion.equalsIgnoreCase("1")){
			nuevaCondicion = "1er Reencauche";
		}else if(condicion.equalsIgnoreCase("2")){
			nuevaCondicion = "2do Reencauche";
		}else if(condicion.equalsIgnoreCase("3")){
			nuevaCondicion = "3er Reencauche";
		}else if(condicion.equalsIgnoreCase("4")){
			nuevaCondicion = "4to Reencauche";
		}else if(condicion.equalsIgnoreCase("5")){
			nuevaCondicion = "5to Reencauche";
		}
		return nuevaCondicion;
	}
	
	//Método que transforma el estado unidireccional del neumático guardado en la base de datos a una descripción explícita
	public String convertirUnidireccional(String unid){
		String nuevoUnidireccional = "";
		
		if(unid.equalsIgnoreCase("S"))
			nuevoUnidireccional = "Sí";
		else
			nuevoUnidireccional = "No";
		
		return nuevoUnidireccional;
	}
	
	/*
	 * Método que cambia el formato en el que aparecen la condicion, el tipo de neumático, 
	 * el estado del neumático y la característica unidireccional*/
	public void cambiarFormato(List<Neumatico> neumaticos){
		
		for(int i=0; i < neumaticos.size(); i++){
			neumaticos.get(i).setCondicion(this.convertirCondicion(neumaticos.get(i).getCondicion()));
			neumaticos.get(i).setTipoNeumatico(this.convertirTipo((neumaticos.get(i).getTipoNeumatico())));
			neumaticos.get(i).setEstado(this.convertirEstado((neumaticos.get(i).getEstado())));
			neumaticos.get(i).setUnidireccional(this.convertirUnidireccional((neumaticos.get(i).getUnidireccional())));
		}
	}
	
	/*
	 * Método que cambia el formato en el que aparecen el tipo de movimiento realizados a los neumaticos*/
	public void cambiarFormatoMovimientos(List<Movimiento> movimientos){
		
		for(int i=0; i < movimientos.size(); i++){
			movimientos.get(i).getId().setTipoMovimiento(this.convertirTipoMovimiento(movimientos.get(i).getId().getTipoMovimiento()));
		}
	}
	
	//Método que transforma la condición en caso de que no haya sido cambiada por el usuario, a un formato admitido por la base de datos
	public String invertirCondicion(String condicion){
		String nuevaCondicion = "";
		
		switch(condicion){
		case "Nuevo":
			nuevaCondicion = "N"; 
			break;
		case "1er Reencauche":
			nuevaCondicion = "1";
			break;
		case "2do Reencauche":
			nuevaCondicion = "2";
			break;
		case "3er Reencauche":
			nuevaCondicion = "3";
			break;
		case "4to Reencauche":
			nuevaCondicion = "4";
			break;
		case "5to Reencauche":
			nuevaCondicion = "5";
			break;
		default:
			nuevaCondicion = "";
			break;
		}
		
		if(nuevaCondicion.equalsIgnoreCase(""))
			return condicion;
		else
			return nuevaCondicion;
	}
	
	//Método que transforma el tipo de neumatico en caso de que no haya sido cambiado por el usuario, a un formato admitido por la base de datos
	public String invertirTipo(String tipo){
		String nuevoTipo = "";
		
		if(tipo.equalsIgnoreCase("Direccional")){
			nuevoTipo = "D"; 
		}else if(tipo.equalsIgnoreCase("Mixto")){
			nuevoTipo = "M";
		}else if(tipo.equalsIgnoreCase("Tracción")){
			nuevoTipo = "T";
		}
		
		if(nuevoTipo.equalsIgnoreCase(""))
			return tipo;
		else
			return nuevoTipo;
	}
	
	//Método que transforma el estado unidireccional del neumático en caso de que no haya sido cambiado por el usuario, a un formato admitido por la base de datos
	public String invertirUnidireccional(String unid){
		String nuevoUnid = "";

		if(unid.equalsIgnoreCase("Sí")){
			nuevoUnid = "S"; 
		}else if(unid.equalsIgnoreCase("No")){
			nuevoUnid = "M";
		}

		return nuevoUnid;
	}

	//Método que transforma el estado en caso de que no haya sido cambiado por el usuario, a un formato admitido por la base de datos
	public String invertirEstado(String estado){
		String nuevoEstado = "";
		
		if(estado.equalsIgnoreCase("Listo para Montar")){
			nuevoEstado = "L"; 
		}else if(estado.equalsIgnoreCase("Montado")){
			nuevoEstado = "M";
		}else if(estado.equalsIgnoreCase("En Reencauche")){
			nuevoEstado = "R";
		}else if(estado.equalsIgnoreCase("En Reparación")){
			nuevoEstado = "P";
		}else if(estado.equalsIgnoreCase("En Reclamo")){
			nuevoEstado = "C";
		}else if(estado.equalsIgnoreCase("De Baja")){
			nuevoEstado = "B";
		}
		
		if(nuevoEstado.equalsIgnoreCase(""))
			return estado;
		else
			return nuevoEstado;
	}
	
	//Método que llama y ejecuta los métodos que invierten los formatos
	public void invertirFormato(Neumatico neumatico){
		
		neumatico.setCondicion(this.invertirCondicion(neumatico.getCondicion()));
		neumatico.setTipoNeumatico(this.invertirTipo((neumatico.getTipoNeumatico())));
		neumatico.setEstado(this.invertirEstado((neumatico.getEstado())));
		neumatico.setUnidireccional(this.invertirUnidireccional(neumatico.getUnidireccional()));
	}
	
	/////////////////////////////////////REVISAR//////////////// POSIBLE ELIMINACIÓN
	public void recomendarPresion(){
		Medida medidaPresion = new Medida();
		
		medidaPresion = (Medida) medidaDAO.getInstancia().buscarEntidadPorClave(idMedida);
		presionRecomendada = medidaPresion.getPresionRecomendada().toString();
		mensajes.advertencia("Presión Recomendada", "La presión recomendada de la medida: " + medidaPresion.getNombre() + " es: " + presionRecomendada);
	}
	
	//Método que busca la marca del neumatico seleccionado
	public void buscarMarca(){
		Diseno disenoBuscar = new Diseno();
		System.out.println("Diseño actual: "+neumaticoSeleccionado.getIdDisenoActual());
		System.out.println("Diseño Original: "+neumaticoSeleccionado.getIdDisenoOriginal());
		
		disenoBuscar = (Diseno) disenoDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionado.getIdDisenoOriginal());
		marcaSeleccionada = (MarcaNeumatico) marcaDAO.getInstancia().buscarEntidadPorPropiedad("idMarcaNeumatico", disenoBuscar.getMarcaNeumatico().getIdMarcaNeumatico());
	}
	
	//Método que busca al proveedor seleccionado en la tabla para poder manipular sus datos a conveniencia
	public void buscarProveedorSeleccionado(){
		proveedorSeleccionado = (Proveedor) proveedorDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionado.getProveedor().getIdProveedor());
		idProveedor = proveedorSeleccionado.getIdProveedor();
	}
	
	//Método que busca al proveedor seleccionado en la tabla para poder manipular sus datos a conveniencia
	public Proveedor buscarProveedor(Integer id){
		Proveedor proveedorBuscar = (Proveedor) proveedorDAO.getInstancia().buscarEntidadPorClave(id);
		return proveedorBuscar;
	}

	//Método que busca al diseño seleccionado en la tabla para poder manipular sus datos a conveniencia
	public void buscarDiseno(){
		disenoSeleccionado = (Diseno) disenoDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionado.getIdDisenoOriginal());
		idDisenoOriginal = disenoSeleccionado.getIdDiseno();
		System.out.println("Diseno Seleccionado: " + disenoSeleccionado.getNombre());
	}
	
	//Método que busca a la medida seleccionada en la tabla para poder manipular sus datos a conveniencia
	public void buscarMedida(){
		medidaSeleccionada = (Medida) medidaDAO.getInstancia().buscarEntidadPorClave(neumaticoSeleccionado.getDisenoMedida().getId().getIdMedida());
		idMedida = medidaSeleccionada.getIdMedida();
	}
	
	public void actualizarNeumatico(){
		this.publicarImagen(disenoSeleccionado.getImagen());
	}

	@Override
	public void eliminar(ActionEvent actionEvent) {
		this.invertirFormato(neumaticoSeleccionado);
		try {
			neumaticoDAO.eliminarLogicamente(this.neumaticoSeleccionado);
			llenarDataModel();
			
			mensajes.informativo("Operación exitosa", "Neumatico: "+ this.neumaticoSeleccionado.getIdNeumatico() +" ha sido eliminado!");
		} catch (Exception e) {
			mensajes.error("Operación fallida", "Se produjo un error, por favor, verifique");
		}
		cancelar(eventoCancelar);
		empresaEnLaSesion();
	}

	@Override
	public void cancelar(ActionEvent actionEvent) {
		this.neumatico = null;
		this.neumatico = new Neumatico();
		this.neumaticoSeleccionado = null;
		this.neumaticoSeleccionado = new Neumatico();
		colocarImagenDefault();
		setModificar(false);
		setEliminar(false);
		RequestContext.getCurrentInstance().addCallbackParam("ok", false);
		RequestContext.getCurrentInstance().addCallbackParam("tarea", null);
		empresaEnLaSesion();
	}

	@Override
	public void activarModificar() {
		if (neumaticoSeleccionado.getIdNeumatico() != null){
			modificar = true;
			this.buscarProveedorSeleccionado();
			this.buscarMarca();
			this.buscarDiseno();
			this.medidasDiseno();
			this.buscarUnidireccional();
			this.buscarMedida();
			publicarImagen(disenoSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "M");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún neumatico de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarConsultar() {
		if (neumaticoSeleccionado.getIdNeumatico() != null){
			consultar = true;
			this.buscarMarca();
			this.buscarDiseno();
			this.movimientosNeumatico();
			costoKm();
			publicarImagen(disenoSeleccionado.getImagen());
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "C");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún neumatico de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public void activarConsultarMovimientos() {
		if (movimientoSeleccionado != null){
			this.mensajePosiciones();
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "D");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún movimiento de la lista");
			cancelar(eventoCancelar);
		}
	}

	@Override
	public void activarEliminar() {
		if (neumaticoSeleccionado.getIdNeumatico() != null){
			eliminar = true;
			setMensajeEliminar("Está seguro de eliminar a "+ neumaticoSeleccionado.getIdNeumatico() +"?");
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			RequestContext.getCurrentInstance().addCallbackParam("tarea", "E");
		} else {
			mensajes.advertencia("Verifique", "Primero debe seleccionar algún neumatico de la lista");
			cancelar(eventoCancelar);
		}
	}
	
	public String flujoPrimerIngreso(FlowEvent event) {
        return event.getNewStep(); 
	}
	
	/**
	 * @author José Leonardo Jerez Araujo jerez.leo13@gmail.com
	 * @param event
	 * Este método se encarga de manejar la acción de subida de archivos al servidor desde el portal para Diseños de Neumáticos
	 * A través del archivo subido se obtiene el contenido de este (arreglo de bytes), el cual se pasa al método
	 * publicarImagene para mostrarla en pantalla. Además, permite setear el objeto Diseno para luego guardarlo en la BD
	 */
	public void manejarUploadedFile(FileUploadEvent event) {
	    UploadedFile uploadedFile = (UploadedFile)event.getFile();
	    if(!modificar) {
		    
	    	//diseno.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
			//publicarImagen(diseno.getImagen()); //publicamos la imagen	
	    } else {
		    disenoSeleccionado.setImagen(uploadedFile.getContents()); //Seteamos el Byte[] de la imagen del POJO diseno
			publicarImagen(disenoSeleccionado.getImagen()); //publicamos la imagen
	    }
	    mensajes.informativo("Imagen Cargada", "La imagen "+uploadedFile.getFileName()+" ha sido cargada con éxito!");
	}
	
	/**
	 * @author José Leonardo Jerez Araujo jerez.leo13@gmail.com
	 * @param byteArrayImagen
	 * Este método permite setear el atributo que muestra la imagen en pantalla a partir de un arreglo de bytes
	 */
	public void publicarImagen(byte[] byteArrayImagen) {
		streamedContentImagen = new DefaultStreamedContent(new ByteArrayInputStream(byteArrayImagen));
		//System.out.println("Contenido de la imagen: " + streamedContentImagen.getName().toString());
	}
    
	/**
	 * @author José Leonardo Jerez Araujo
	 * Este método permite colocar una imagen por defecto en el formulario para el registro de un nuevo Diseño, de manera que si el usuario no cargó
	 * ninguna imagen esta la supla
	 */
	public void colocarImagenDefault() {
//		Path path = Paths.get("C:/Documents and Settings/user-pasante/Escritorio/proyecto-cauca/workspace/proyecto-cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
//		Path path = Paths.get("C:/Users/cauca/Desktop/SCSN-Coord-Informática/WorkSpace/proyecto-cauca-SCSN-integrar/WebContent/imagenes/mascaraDiseno.jpg");
		Path path = Paths.get("/var/lib/tomcat7/webapps/cauca-SCSN/WebContent/imagenes/mascaraDiseno.jpg");
		byte[] data;
		try {
			data = Files.readAllBytes(path);
			publicarImagen(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void agregarMedida(){
		//Inicializamos una variable del tipo Diseño para luego asignarle un id y pasarlo como parametro de busqueda
		Diseno disenoBuscar = new Diseno();
				
		//Se le asigna el id del diseño original
		disenoBuscar.setIdDiseno(idDisenoOriginal);
				
		//Cargamos la lista de diseños con medidas, filtrando por el diseño previamente cargado
		this.setListaDisenoMedida(disenoMedidaDAO.getInstancia().buscarEntidadesPorPropiedad("diseno", disenoBuscar));
		
		//Inicializamos una variable del tipo Medida para luego asignarle un id y pasarlo como parametro de busqueda
		Medida medidaBuscar = new Medida();
				
		//Se le asigna el id del diseño original
		medidaBuscar.setIdMedida(idMedida);
		 
		//Cargamos la lista de diseños con medidas, filtrando por el diseño previamente cargado
		//setListaDisenoMedida(disenoMedidaDAO.getInstancia().buscarEntidadesPorPropiedad("diseno", disenoBuscar));
		//listaMedida.add((Medida) medidaDAO.getInstancia().buscarEntidadPorPropiedad("idMedida",listaDisenoMedida.get(i).getMedida().getIdMedida()));
	}
	
	public void costoKm(){
		
		Double valorRemanente = neumaticoSeleccionado.getRemanenteIngreso() - neumaticoSeleccionado.getRemanenteActual();
		if(valorRemanente > 0 && neumaticoSeleccionado.getRecorridoAcumulado() > 0){
			costoKilometro = neumaticoSeleccionado.getCosto() / neumaticoSeleccionado.getRecorridoAcumulado();

			costoMm = (valorRemanente * neumaticoSeleccionado.getCosto()) / neumaticoSeleccionado.getRemanenteIngreso();
			
			rendimientoMm = neumaticoSeleccionado.getRecorridoAcumulado() / valorRemanente;
		
			recorridoProyectado = (neumaticoSeleccionado.getRecorridoAcumulado() * neumaticoSeleccionado.getRemanenteActual()) / valorRemanente;
		}else{
			costoKilometro = 0;
			costoMm = 0;
			rendimientoMm = 0;
			recorridoProyectado = 0;
			
		}
		
		
	}
	
	public void reiniciarTabIndex(ActionEvent event) {
		System.out.println("entramos a reiniciar");
		tabIndex = 0;
	}
	
	@Override
	public void llenarDataModel() {
		setListaNeumaticos(neumaticoDAO.buscarEntidadesPorPropiedad("empresa", this.empresa));
		this.cambiarFormato(listaNeumaticos);
		setNeumaticoDataModel(new NeumaticoDataModel(this.listaNeumaticos));
		
		if(listaNeumaticos.size() > 0){
			rows = 5;
		}else{
			rows = 0;
		}
	}

	@Override
	public void empresaEnLaSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresa");
		this.neumatico.setEmpresa(empresa);				//Esto es para meterle valores en el campo Empresa
	}
	
	public List<Diseno> getListaDisenos() {
		return listaDisenos;
	}

	public void setListaDisenos(List<Diseno> listaDisenos) {
		this.listaDisenos = listaDisenos;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Medida getMedidaSeleccionada() {
		return medidaSeleccionada;
	}

	public void setMedidaSeleccionada(Medida medidaSeleccionada) {
		this.medidaSeleccionada = medidaSeleccionada;
	}

	public DisenoDAO getDisenoDAO() {
		return disenoDAO;
	}

	public void setDisenoDAO(DisenoDAO disenoDAO) {
		this.disenoDAO = disenoDAO;
	}

	public ProveedorDAO getProveedorDAO() {
		return proveedorDAO;
	}

	public void setProveedorDAO(ProveedorDAO proveedorDAO) {
		this.proveedorDAO = proveedorDAO;
	}

	public Proveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public Diseno getDisenoSeleccionado() {
		return disenoSeleccionado;
	}

	public void setDisenoSeleccionado(Diseno disenoSeleccionado) {
		this.disenoSeleccionado = disenoSeleccionado;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getPresionRecomendada() {
		return presionRecomendada;
	}

	public void setPresionRecomendada(String presionRecomendada) {
		this.presionRecomendada = presionRecomendada;
	}

	public DisenoMedidaDAO getDisenoMedidaDAO() {
		return disenoMedidaDAO;
	}

	public void setDisenoMedidaDAO(DisenoMedidaDAO disenoMedidaDAO) {
		this.disenoMedidaDAO = disenoMedidaDAO;
	}

	public MedidaDAO getMedidaDAO() {
		return medidaDAO;
	}

	public void setMedidaDAO(MedidaDAO medidaDAO) {
		this.medidaDAO = medidaDAO;
	}

	public List<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(List<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public List<DisenoMedida> getListaDisenoMedida() {
		return listaDisenoMedida;
	}

	public void setListaDisenoMedida(List<DisenoMedida> listaDisenoMedida) {
		this.listaDisenoMedida = listaDisenoMedida;
	}

	public Integer getIdMedida() {
		return idMedida;
	}

	public void setIdMedida(Integer idMedida) {
		this.idMedida = idMedida;
	}

	public Integer getIdDisenoOriginal() {
		return idDisenoOriginal;
	}

	public void setIdDisenoOriginal(Integer idDisenoOriginal) {
		this.idDisenoOriginal = idDisenoOriginal;
	}

	public boolean isUnidireccional() {
		return unidireccional;
	}

	public void setUnidireccional(boolean unidireccional) {
		this.unidireccional = unidireccional;
	}

	public NeumaticoDAO getNeumaticoDAO() {
		return neumaticoDAO;
	}

	public Neumatico getNeumatico() {
		return neumatico;
	}

	public Neumatico getNeumaticoSeleccionado() {
		return neumaticoSeleccionado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public NeumaticoDataModel getNeumaticoDataModel() {
		return neumaticoDataModel;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public boolean isConsultar() {
		return consultar;
	}

	public ValidadorBean getValidador() {
		return validador;
	}

	public ControladorMensajes getMensajes() {
		return mensajes;
	}

	public ActionEvent getEventoCancelar() {
		return eventoCancelar;
	}

	public String getMensajeEliminar() {
		return mensajeEliminar;
	}

	public List<Neumatico> getListaNeumaticos() {
		return listaNeumaticos;
	}

	public void setNeumaticoDAO(NeumaticoDAO neumaticoDAO) {
		this.neumaticoDAO = neumaticoDAO;
	}

	public void setNeumatico(Neumatico neumatico) {
		this.neumatico = neumatico;
	}

	public void setNeumaticoSeleccionado(Neumatico neumaticoSeleccionado) {
		this.neumaticoSeleccionado = neumaticoSeleccionado;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setNeumaticoDataModel(NeumaticoDataModel neumaticoDataModel) {
		this.neumaticoDataModel = neumaticoDataModel;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}

	public void setValidador(ValidadorBean validador) {
		this.validador = validador;
	}

	public void setMensajes(ControladorMensajes mensajes) {
		this.mensajes = mensajes;
	}

	public void setEventoCancelar(ActionEvent eventoCancelar) {
		this.eventoCancelar = eventoCancelar;
	}

	public void setMensajeEliminar(String mensajeEliminar) {
		this.mensajeEliminar = mensajeEliminar;
	}

	public void setListaNeumaticos(List<Neumatico> listaNeumaticos) {
		this.listaNeumaticos = listaNeumaticos;
	}

	public MarcaNeumatico getMarcaSeleccionada() {
		return marcaSeleccionada;
	}

	public void setMarcaSeleccionada(MarcaNeumatico marcaSeleccionada) {
		this.marcaSeleccionada = marcaSeleccionada;
	}

	public MarcaNeumaticoDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaNeumaticoDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	public List<Movimiento> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<Movimiento> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public MovimientoDAO getMovimientoDAO() {
		return movimientoDAO;
	}

	public void setMovimientoDAO(MovimientoDAO movimientoDAO) {
		this.movimientoDAO = movimientoDAO;
	}

	public MovimientoDataModel getMovimientoDataModel() {
		return movimientoDataModel;
	}

	public void setMovimientoDataModel(MovimientoDataModel movimientoDataModel) {
		this.movimientoDataModel = movimientoDataModel;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public MedidaDataModel getMedidaDataModel() {
		return medidaDataModel;
	}

	public void setMedidaDataModel(MedidaDataModel medidaDataModel) {
		this.medidaDataModel = medidaDataModel;
	}

	public Movimiento getMovimientoSeleccionado() {
		return movimientoSeleccionado;
	}

	public void setMovimientoSeleccionado(Movimiento movimientoSeleccionado) {
		this.movimientoSeleccionado = movimientoSeleccionado;
	}

	public String getPosicionMovimiento() {
		return posicionMovimiento;
	}

	public void setPosicionMovimiento(String posicionMovimiento) {
		this.posicionMovimiento = posicionMovimiento;
	}

	public StreamedContent getStreamedContentImagen() {
		return streamedContentImagen;
	}

	public void setStreamedContentImagen(StreamedContent streamedContentImagen) {
		this.streamedContentImagen = streamedContentImagen;
	}


	public double getCostoKilometro() {
		return costoKilometro;
	}


	public void setCostoKilometro(double costoKilometro) {
		this.costoKilometro = costoKilometro;
	}


	public double getCostoMm() {
		return costoMm;
	}


	public void setCostoMm(double costoMm) {
		this.costoMm = costoMm;
	}


	public double getRendimientoMm() {
		return rendimientoMm;
	}


	public void setRendimientoMm(double rendimientoMm) {
		this.rendimientoMm = rendimientoMm;
	}


	public double getRecorridoProyectado() {
		return recorridoProyectado;
	}


	public void setRecorridoProyectado(double recorridoProyectado) {
		this.recorridoProyectado = recorridoProyectado;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}


	public ServiciosVentanaMedida getServiciosVentanaMedida() {
		return serviciosVentanaMedida;
	}


	public void setServiciosVentanaMedida(
			ServiciosVentanaMedida serviciosVentanaMedida) {
		this.serviciosVentanaMedida = serviciosVentanaMedida;
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}

}
