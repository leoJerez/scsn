package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.CausaOperacion;

public class CausaOperacionDataModel extends ListDataModel implements SelectableDataModel<CausaOperacion> {

	public CausaOperacionDataModel() {
		super();
	}

	public CausaOperacionDataModel(List<CausaOperacion> list) {
		super(list);
	}

	@Override
	public Object getRowKey(CausaOperacion causaOperacion) {
		return causaOperacion.getIdCausaOperacion();
	}

	@Override
	public CausaOperacion getRowData(Integer rowKey) {
		
		List<CausaOperacion> causas = (List<CausaOperacion>) getWrappedData();
		
		 for(CausaOperacion causaOperacion : causas) {  
	            if(causaOperacion.getIdCausaOperacion().equals(rowKey))  
	                return causaOperacion;
	     }  
		return null;
	}

}
