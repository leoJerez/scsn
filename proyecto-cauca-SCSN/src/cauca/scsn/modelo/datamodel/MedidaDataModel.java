package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Medida;
import cauca.scsn.modelo.entidad.Medida;

public class MedidaDataModel extends ListDataModel implements SelectableDataModel<Medida> {

	public MedidaDataModel() {
		super();
	}

	public MedidaDataModel(List<Medida> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Medida medida) {
		return medida.getIdMedida();
	}

	@Override
	public Medida getRowData(Integer rowKey) {
		List<Medida> medidas = (List<Medida>) getWrappedData();
		
		 for(Medida medida : medidas) {  
	            if(medida.getIdMedida().equals(rowKey))  
	                return medida;
	     }  
		
		return null;
	}

}
