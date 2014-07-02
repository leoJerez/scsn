package cauca.scsn.modelo.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cauca.scsn.controlador.ControladorMensajes;
import cauca.scsn.modelo.entidad.Empleado;
import cauca.scsn.modelo.entidad.Neumatico;
import cauca.scsn.modelo.entidad.Proveedor;

@ManagedBean
@SessionScoped
public class ValidadorBean{

	private ControladorMensajes mensajes = new ControladorMensajes();
	
	public ValidadorBean() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Metodo que valida la sintaxix de cualquier valor del tipo real que sea ingresado, 
	 * de modo que no contenga ningun caracter invalido*/
	public boolean validarReal(String strValue, String msj){
		if(strValue != null){
			//Este metodo permite usar numeros, un punto y nuevamente numeros
			if (!strValue.matches("^[\\d]*[.]?[\\d]*$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*
	 * Metodo que valida la sintaxix las cedulas ingresadas, de modo que no contenga 
	 * ningun caracter invalido*/
	public boolean validarCedula(String strValue, String msj){
		if(strValue != null){
			//Este método permite usar una serie de 7 u 8 numeros dependiendo del numero de cedula
			if (!strValue.matches("^([0-9][0-9]|[0-9])[0-9][0-9][0-9][0-9][0-9][0-9]$")) {
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*
	 * Metodo que valida la sintaxix los strings o cadenas de caracteres ingresadas, 
	 * de modo que no contenga ningun caracter invalido*/
	public boolean validarString(String strValue, String msj){
		if(strValue != null){
			//Este método permite usar solo letras y espacios en blanco
			if (!strValue.matches("^([A-ZÑ]|[a-zñ])+(\\s+)?(([A-Z]|[a-z])+(\\s+)?)?$")) {
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}


	/*
	 * Metodo que valida la sintaxix los emails ingresados, de modo que no contenga 
	 * ningun caracter invalido*/
	public boolean validarMail(String strValue, String msj){
		if(strValue != null){
			/*Este método permite usar cualquier caracter antes del @ (excepto espacios en blanco), 
			 * luego se debe colocar un @ de separación, luego para escribir el servidor de correo, 
			 * puedes usar letras o numeros, a continuación viene un punto y luego letras o numeros dependiendo del dominio*/
			if (!strValue.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	/*
	 * Metodo usado para validar: Cargos, Marcas de Vehiculo, Marcas de Neumatico, Modelos de Vehiculo*/
	public boolean validarLetrasNumerosGuionPuntoEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-ZÑa-zñ0-9\\-\\.\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	
	/*
	 * Metodo usado para validar: Ruta*/
	public boolean validarLetrasNumerosPuntoComaParentesisEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[\\w\\.\\,\\(\\)\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	
	/*
	 * Metodo usado para validar: Tipo de Desgaste de Neumatico*/
	public boolean validarLetrasGuionPuntoEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-Za-z\\-\\.\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	/*
	 * Metodo usado para validar: Diseño de Neumático*/
	public boolean validarLetrasGuionEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-Za-z\\-\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*
	 * Metodo usado para validar: Causa de Operación*/
	public boolean validarLetrasComaPuntoGuionEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-Za-z\\.\\,\\-\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/*
	 * Metodo que valida la sintaxis del rif del proveedor, 
	 * de modo que no contenga ningun caracter invalido*/
	public boolean validarRif(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[VEJG][-]\\d{7,8}[-][0-9]$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}

	}
	
	/*
	 * Metodo usado para validar: Medida de Neumatico*/
	public boolean validarLetrasNumerosGuionPuntoBarraDiagonalEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-Za-z0-9\\-\\.\\/\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public boolean validarNeumatico(Neumatico neumatico){
		if(neumatico.getCodInterno()!=null){
			String remantente = neumatico.getRemanenteIngreso().toString();
			String presion = neumatico.getPresionActual().toString();
			String costo = neumatico.getCosto().toString();
			//Si todos los campos validados son correctos devuelve "true"
			if(validarReal(remantente, "Formato de Remanente INCORRECTO ejm: 1.2") 
					&& validarReal(presion, "Formato de Presión INCORRECTO ejm: 0.7")
					&& validarReal(costo, "Formato de Costo INCORRECTO ejm: 1500.0")){
				return true;
			}else {
				return false;
			}
		} else{
			return false;
		}
	}
	
	/*
	 * Metodo usado para validar: Tipo de Vehículo*/
	public boolean validarLetrasNumerosPuntoAsteriscoGuionEspacios(String strValue, String msj){
		
		if(!strValue.matches("^[\\w\\.\\*\\-\\s]+$")){
			mensajes.advertencia("Verifique", msj);
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * Metodo usado para validar: Tipos de Carretera*/
	public boolean validarLetrasNumerosEspacios(String strValue, String msj){
		if(strValue != null){
			if(!strValue.matches("^[A-ZÑa-zñ0-9\\s]+$")){
				mensajes.advertencia("Verifique", msj);
				return false;
			}else{
				return true;
			}
		} else {
			return false;
		}
	}

	
	/*
	 * Metodo que permite validar la cedula, nombre, apellido e email de los Empleados*/
	public boolean validarEmpleado(Empleado emp){

		//Si todos los campos validados son correctos devuelve "true"
		if(validarCedula(emp.getCedulaEmpleado(), "Formato de Cédula INCORRECTO ejm: 12345678 o 08231451") && validarString(emp.getNombre(), "Formato de Nombre INCORRECTO ejm: Francisco, Cecilia") 
				&& validarString(emp.getApellido(), "Formato de Apellido INCORRECTO ejm: Sanchez")){	//&& validarMail(emp.getCorreo(), "Formato de Mail INCORRECTO ejm: ejemplo@mail.com")
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Metodo que permite validar el rif y el email de los Proveedores*/
	public boolean validarProveedor(Proveedor pro){

		//Si todos los campos validados son correctos devuelve "true"
		if(validarRif(pro.getRif(), "Formato del RIF INCORRECTO ejm: V-12345678-0") && validarMail(pro.getCorreo(), "Formato de Mail INCORRECTO ejm: ejemplo@mail.com")){
			return true;
		}else{
			return false;
		}
	}
	
	/* 
	 * Metodo que cambia las primeras letras de cada palabra en un nombre o apellido*/
	public String cambiarMayuscula(String value){
		
		/*
		 * Se declara un array de string, en el que se van a guardar las palabras que conforman el nombre o apellido, 
		 * este se divide con el metodo split*/
		String arrayMayuscula[] = value.split(" ");
		
		//Se limpia la variable value para volver a utilizarla
		value="";
		
		/*
		 * Se declara un for para recorrer el array que contiene los nombres, 
		 * para cambiar la primera letra de cada palabra*/
		for(int i=0; i < arrayMayuscula.length; i++){
			
			/*
			 * Aqui value acumula los resultados de la operación, la cual consiste en tomar la primera letra de cada palabra
			 * a través del método charAt(0), que devuelve la primera letra a la que se le hace un toUpperCase, que cambia su tamaño
			 * luego se le concatena el resto de la palabra, y se repite el procedimiento para el resto del nombre o apellido*/
			value += Character.toUpperCase(arrayMayuscula[i].charAt(0)) + arrayMayuscula[i].substring(1) + " ";
			
		}
		
		return value;
	}


	/**
	 * 
	 * @param fecha1 es la fecha inicial de un periodo de tiempo
	 * @param fecha2 es la fecha final de un periodo de tiempo
	 * @param msj es el mensaje que se va a enviar desde el controlador
	 * @return true si la fecha final es menor que la incial y false en caso contrario
	 */
	public boolean compararFechaUnoMayorQueFechaDos(Date fecha1, Date fecha2, String msj) {
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if((fecha1 != null) && (fecha2 != null)) {
			if(fecha2.before(fecha1)) {
				//mensajes.error("Error!", msj);
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param fechaObjetivo es la fecha que queremos transformar a un formato facil de leer
	 * @return String que contiene la fecha ya formateada en formato (FULL) ejemplo: "lunes 6 de agosto de 2012"
	 */
	public String formatearFechaEstiloCompleto(Date fechaObjetivo) {
		DateFormat formatoFechaFull = DateFormat.getDateInstance(DateFormat.FULL);
		return formatoFechaFull.format(fechaObjetivo);
	}
	
	/**
	 * 
	 * @param fechaObjetivo es la fecha que queremos transformar a un formato facil de leer
	 * @return String que contiene la fecha ya formateada en formato (SHORT) ejemplo: "06/08/12"
	 */
	public String formatearFechaEstiloCorto(Date fechaObjetivo) {
		DateFormat formatoFechaShort = DateFormat.getDateInstance(DateFormat.SHORT);
		return formatoFechaShort.format(fechaObjetivo);
	}
	
	/**
	 * 
	 * @param fechaObjetivo es la fecha que queremos transformar a un formato facil de leer
	 * @return String que contiene la fecha ya formateada en formato (MEDIUM) ejemplo: "06/08/2012"
	 */
	public String formatearFechaEstiloMedio(Date fechaObjetivo) {
		DateFormat formatoFechaMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return formatoFechaMedium.format(fechaObjetivo);
	}
	
	/**
	 * 
	 * @param fechaObjetivo es la fecha que queremos transformar a un formato facil de leer
	 * @return String que contiene la fecha ya formateada en formato (LONG) ejemplo: "6 de agosto de 2012"
	 */
	public String formatearFechaEstiloLargo(Date fechaObjetivo) {
		DateFormat formatoFechaLong = DateFormat.getDateInstance(DateFormat.LONG);
		return formatoFechaLong.format(fechaObjetivo);
	}
	
	/**
	 * Método creado con el fin de validar que la fecha ingresada sea menor o igual a la fecha actual
	 * @param fechaIngresada
	 * @param msj
	 * @return verdadero o falso, dependiendo si la fecha aprueba la validación o no
	 */
	public boolean validarFechaIngresadaMenorHoy(Date fechaIngresada, String msj) {
		Calendar fechaHoy = GregorianCalendar.getInstance();
		if(fechaIngresada != null) {
			if(fechaIngresada.compareTo(fechaHoy.getTime()) < 1) {
				return true;
			} else {
				//mensajes.error("Error!", msj);
				return false;
			}
		} else {
			mensajes.error("Error!", "Debe ingresar un valor tipo fecha");
		}
		return false;
	}
	
	/**
	 * Compara dos fechas ingresadas por parámetros, con la finalidad de decir si la fecha es mayor (resultado = 1), menor (resultado = -1) o igual (resultado = 0),
	 * en caso de que no se pueda comparar las fechas retornará -5
	 * @param fechaUno
	 * @param fechaDos
	 * @return resultado (int)
	 */
	public int compararFechaUnoConFechaDos(Date fechaUno, Date fechaDos) {
		int resultado = -5;
		if((fechaUno != null) && (fechaDos != null)) {
			resultado = fechaUno.compareTo(fechaDos);
			switch (resultado) {
				case 1:
					//mensajes.informativo("RESULTADO", "fechaUno es mayor que fechaDos");
					break;
				case 0:
					//mensajes.informativo("RESULTADO", "fechaUno es igual que fechaDos");
					break;
				case -1:
					//mensajes.informativo("RESULTADO", "fechaUno es menor que fechaDos");
					break;
			}
		} else {
			mensajes.error("Error!", "Debe ingresar valores tipo fecha");
		}
		return resultado;
	}
	
	/**
	 * Método usado para validar que la fecha ingresada sea válida, a través del formateo de la cadena ingrresada,
	 * es decir, si la logra transformar es una fecha válida, de lo contrario no lo es
	 * @param fecha
	 * @return true si no ocurren problemas con la conversión
	 */
	public boolean validarFecha(String fecha) {
	    try {
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());    
	        //desabilita el modo permisivo osea que si se introduce una fecha invalida la ignorara
	        // y no la convertira a una fecha equivalente valida
	        formatoFecha.setLenient(false);
	        //Convierte la cadena introducida en una fecha
	        formatoFecha.parse(fecha);
	    } catch (ParseException e) {
	        // si se produce un error en la conversion devuelve false= fecha no valida
            return false;
        }    
	    return true;
	}
	
	public List<Date> ordenarFechasAscendente(List<Date> listaFechas) {
		Date aux = new Date();
		for(int i = 0; i < listaFechas.size(); i++) {
			for(int j = i + 1; j < listaFechas.size(); j++) {
				if(compararFechaUnoConFechaDos(listaFechas.get(j), listaFechas.get(i)) == 1) { 
					aux = listaFechas.get(i);
					listaFechas.set(i, listaFechas.get(j));
					listaFechas.set(j, aux);
				}
			}           
		}
		return listaFechas;
	}
	
	/**
	 * 
	 * @param numero
	 * @return true si es par
	 */
	public boolean esPar(int numero) {
        //Si el resto de numero entre 2 es 0, el numero es par
        if(numero % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
	
	public boolean verificarEntero(Object entrante) {
		try {
			Integer.parseInt((String)entrante);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	public List<String> ordenarListaNumerosStringAscendente(List<String> listaObjetivo) {
		String aux = "";
		for(int i=0; i<listaObjetivo.size(); i++) {
			for(int j=i+1; j<listaObjetivo.size(); j++) {
				if(verificarEntero(listaObjetivo.get(i)) && verificarEntero(listaObjetivo.get(j))) {
					if(Integer.parseInt(listaObjetivo.get(i)) > Integer.parseInt(listaObjetivo.get(j))) {
						aux = listaObjetivo.get(i);
						listaObjetivo.set(i, listaObjetivo.get(j));
						listaObjetivo.set(j, aux);
					}
				}
			}
		}
		return listaObjetivo;
	}

}
