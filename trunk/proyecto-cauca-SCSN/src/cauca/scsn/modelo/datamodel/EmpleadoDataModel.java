package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Empleado;

public class EmpleadoDataModel extends ListDataModel implements SelectableDataModel<Empleado> {
	
	public EmpleadoDataModel() {
	}

	public EmpleadoDataModel(List<Empleado> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Empleado empleado) {
		return empleado.getCedulaEmpleado();
	}

	@Override
	public Empleado getRowData(Integer rowKey) {
		
		List<Empleado> empleados = (List<Empleado>) getWrappedData();
		
		 for(Empleado empleado : empleados) {  
	            if(empleado.getCedulaEmpleado().equals(rowKey))  
	                return empleado;
	     }  
		
		return null;
	}

}
