package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.TipoVehiculo;

public class TipoVehiculoDataModel extends ListDataModel implements SelectableDataModel<TipoVehiculo> {

	public TipoVehiculoDataModel() {
		super();
	}

	public TipoVehiculoDataModel(List<TipoVehiculo> list) {
		super(list);
	}

	@Override
	public Object getRowKey(TipoVehiculo tipoVehiculo) {
		return tipoVehiculo.getIdTipoVehiculo();
	}

	@Override
	public TipoVehiculo getRowData(Integer rowKey) {
		List<TipoVehiculo> tipoVehiculos = (List<TipoVehiculo>) getWrappedData();
		
		 for(TipoVehiculo tipoVehiculo : tipoVehiculos) {  
	            if(tipoVehiculo.getIdTipoVehiculo().equals(rowKey))  
	                return tipoVehiculo;
	     }  
		
		return null;
	}

}
