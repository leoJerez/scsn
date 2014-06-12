package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Vehiculo;
import cauca.scsn.modelo.entidad.Vehiculo;

public class VehiculoDataModel extends ListDataModel implements SelectableDataModel<Vehiculo> {

	public VehiculoDataModel() {
	}

	public VehiculoDataModel(List<Vehiculo> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Vehiculo vehiculo) {
		return vehiculo.getIdVehiculo();
	}

	@Override
	public Vehiculo getRowData(Integer rowKey) {
		
		List<Vehiculo> disenos = (List<Vehiculo>) getWrappedData();
		
		 for(Vehiculo vehiculo : disenos) {  
	            if(vehiculo.getIdVehiculo().equals(rowKey))  
	                return vehiculo;
	     }  
		
		return null;
	}

}
