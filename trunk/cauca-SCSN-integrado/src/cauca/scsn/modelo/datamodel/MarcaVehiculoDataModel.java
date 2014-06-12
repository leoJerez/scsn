package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.MarcaVehiculo;

public class MarcaVehiculoDataModel extends ListDataModel implements SelectableDataModel<MarcaVehiculo> {

	public MarcaVehiculoDataModel() {
		super();
	}

	public MarcaVehiculoDataModel(List<MarcaVehiculo> list) {
		super(list);
	}

	@Override
	public Object getRowKey(MarcaVehiculo marcaVehiculo) {
		return marcaVehiculo.getIdMarcaVehiculo();
	}

	@Override
	public MarcaVehiculo getRowData(Integer rowKey) {
		List<MarcaVehiculo> marcaVehiculos = (List<MarcaVehiculo>) getWrappedData();
		
		 for(MarcaVehiculo marcaVehiculo : marcaVehiculos) {  
	            if(marcaVehiculo.getIdMarcaVehiculo().equals(rowKey))  
	                return marcaVehiculo;
	     }  
		
		return null;
	}

}
