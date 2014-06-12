package cauca.scsn.modelo.interfaces;

import javax.faces.event.ActionEvent;

public interface ServiciosMaestros {

	public void guardarOModificar(ActionEvent actionEvent);
	
	public void eliminar(ActionEvent actionEvent);
	
	public void cancelar(ActionEvent actionEvent);
	
	public void activarModificar();
	
	public void activarConsultar();
	
	public void activarEliminar();
	
	public void llenarDataModel();
	
	public void empresaEnLaSesion();

}
