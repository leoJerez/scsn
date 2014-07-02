package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.ModeloVehiculo;

public class ModeloVehiculoDataModel extends ListDataModel implements SelectableDataModel<ModeloVehiculo> {

	public ModeloVehiculoDataModel() {
	}

	public ModeloVehiculoDataModel(List<ModeloVehiculo> list) {
		super(list);
	}

	@Override
	public Object getRowKey(ModeloVehiculo modeloVehiculo) {
		return modeloVehiculo.getIdModeloVehiculo();
	}

	@Override
	public ModeloVehiculo getRowData(Integer rowKey) {
		List<ModeloVehiculo> modeloVehiculos = (List<ModeloVehiculo>) getWrappedData();
		
		 for(ModeloVehiculo modeloVehiculo : modeloVehiculos) {  
	            if(modeloVehiculo.getIdModeloVehiculo().equals(rowKey))  
	                return modeloVehiculo;
	     }  
		
		return null;
	}

}
