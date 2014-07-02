package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Ruta;

public class RutaDataModel extends ListDataModel implements SelectableDataModel<Ruta> {

	public RutaDataModel() {
		super();
	}

	public RutaDataModel(List<Ruta> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Ruta ruta) {
		return ruta.getIdRuta();
	}

	@Override
	public Ruta getRowData(Integer rowKey) {
		
		List<Ruta> rutas = (List<Ruta>) getWrappedData();
		
		 for(Ruta ruta : rutas) {  
	            if(ruta.getIdRuta().equals(rowKey))  
	                return ruta;
	     }  
		
		return null;
	}

}
