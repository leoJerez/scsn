package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.TipoDesgaste;

public class TipoDesgasteDataModel extends ListDataModel implements SelectableDataModel<TipoDesgaste> {

	public TipoDesgasteDataModel() {
		super();
	}

	public TipoDesgasteDataModel(List<TipoDesgaste> list) {
		super(list);
	}

	@Override
	public Object getRowKey(TipoDesgaste tipoDesgaste) {
		return tipoDesgaste.getIdTipoDesgaste();
	}

	@Override
	public TipoDesgaste getRowData(Integer rowKey) {
		
		List<TipoDesgaste> tipos = (List<TipoDesgaste>) getWrappedData();
		
		 for(TipoDesgaste tipoDesgaste : tipos) {  
	            if(tipoDesgaste.getIdTipoDesgaste().equals(rowKey))  
	                return tipoDesgaste;
	     }  
		return null;
	}

}
