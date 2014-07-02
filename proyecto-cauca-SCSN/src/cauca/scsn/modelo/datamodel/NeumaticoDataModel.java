package cauca.scsn.modelo.datamodel;

import java.util.List;
import inexistentes.SelectableDataModel;
import javax.faces.model.ListDataModel;
import cauca.scsn.modelo.entidad.Neumatico;

public class NeumaticoDataModel extends ListDataModel implements SelectableDataModel<Neumatico> {

	
	public NeumaticoDataModel() {
		super();
	}

	public NeumaticoDataModel(List<Neumatico> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Neumatico neumatico) {
		return null;
	}

	@Override
	public Neumatico getRowData(Integer rowKey) {
		List<Neumatico> neumaticos = (List<Neumatico>) getWrappedData();
		
		 for(Neumatico neumatico : neumaticos) {  
	            if(neumatico.getIdNeumatico().equals(rowKey))  
	                return neumatico;
	     }  
		
		return null;
	}

}
