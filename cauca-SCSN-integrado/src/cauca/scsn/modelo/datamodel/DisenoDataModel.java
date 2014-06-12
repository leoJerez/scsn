package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Diseno;

public class DisenoDataModel extends ListDataModel implements SelectableDataModel<Diseno> {

	public DisenoDataModel() {
	}

	public DisenoDataModel(List<Diseno> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Diseno disenoNeumatico) {
		return disenoNeumatico.getIdDiseno();
	}

	@Override
	public Diseno getRowData(Integer rowKey) {
		
		List<Diseno> disenos = (List<Diseno>) getWrappedData();
		
		 for(Diseno disenoNeumatico : disenos) {  
	            if(disenoNeumatico.getIdDiseno().equals(rowKey))  
	                return disenoNeumatico;
	     }  
		
		return null;
	}

}
