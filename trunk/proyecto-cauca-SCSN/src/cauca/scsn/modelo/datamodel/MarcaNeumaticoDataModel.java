package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.MarcaNeumatico;

public class MarcaNeumaticoDataModel extends ListDataModel implements SelectableDataModel<MarcaNeumatico> {

	public MarcaNeumaticoDataModel() {
		super();
	}

	public MarcaNeumaticoDataModel(List<MarcaNeumatico> list) {
		super(list);
	}

	@Override
	public Object getRowKey(MarcaNeumatico marcaNeumatico) {
		return marcaNeumatico.getIdMarcaNeumatico();
	}

	@Override
	public MarcaNeumatico getRowData(Integer rowKey) {
		List<MarcaNeumatico> marcaNeumaticos = (List<MarcaNeumatico>) getWrappedData();
		
		 for(MarcaNeumatico marcaNeumatico : marcaNeumaticos) {  
	            if(marcaNeumatico.getIdMarcaNeumatico().equals(rowKey))  
	                return marcaNeumatico;
	     }  
		
		return null;
	}

}
