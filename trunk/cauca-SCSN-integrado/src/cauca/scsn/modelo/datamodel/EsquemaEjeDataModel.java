package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.EsquemaEje;
import cauca.scsn.modelo.entidad.EsquemaEje;

public class EsquemaEjeDataModel extends ListDataModel implements
		SelectableDataModel<EsquemaEje> {

	public EsquemaEjeDataModel() {
		super();
	}

	public EsquemaEjeDataModel(List<EsquemaEje> list) {
		super(list);
	}

	@Override
	public Object getRowKey(EsquemaEje esquema) {
		return esquema.getIdEsquemaEje();
	}

	@Override
	public EsquemaEje getRowData(Integer rowKey) {
		List<EsquemaEje> esquemaEjes = (List<EsquemaEje>) getWrappedData();
		
		 for(EsquemaEje esquemaEje : esquemaEjes) {  
	            if(esquemaEje.getIdEsquemaEje().equals(rowKey))  
	                return esquemaEje;
	     }  
		return null;
	}

}
