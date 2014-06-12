package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.TipoCarretera;

public class TipoCarreteraDataModel extends ListDataModel implements SelectableDataModel<TipoCarretera> {

	public TipoCarreteraDataModel() {
		super();
	}

	public TipoCarreteraDataModel(List<TipoCarretera> list) {
		super(list);
	}

	@Override
	public Object getRowKey(TipoCarretera tipoCarretera) {
		return tipoCarretera.getIdTipoCarretera();
	}

	@Override
	public TipoCarretera getRowData(Integer rowKey) {
		List<TipoCarretera> tipoCarreteras = (List<TipoCarretera>) getWrappedData();
		
		 for(TipoCarretera tipoCarretera : tipoCarreteras) {  
	            if(tipoCarretera.getIdTipoCarretera().equals(rowKey))  
	                return tipoCarretera;
	     }  
		
		return null;
	}

}
