package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Cargo;

public class CargoDataModel extends ListDataModel implements SelectableDataModel<Cargo> {

	public CargoDataModel() {
		super();
	}
	
	public CargoDataModel(List<Cargo> list) {
		super(list);
	}
	
	@Override
	public Object getRowKey(Cargo cargo) {
		return cargo.getIdCargo();
	}

	@Override
	public Cargo getRowData(Integer rowKey) {
		List<Cargo> cargos = (List<Cargo>) getWrappedData();
		
		 for(Cargo cargo : cargos) {  
	            if(cargo.getIdCargo().equals(rowKey))  
	                return cargo;
	     }  
		
		return null;
	}


}
