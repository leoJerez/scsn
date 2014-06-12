package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Proveedor;

public class ProveedorDataModel extends ListDataModel implements SelectableDataModel<Proveedor> {

	public ProveedorDataModel() {
	}

	public ProveedorDataModel(List<Proveedor> list) {
		super(list);
	}
	
	@Override
	public Object getRowKey(Proveedor proveedor) {
		return proveedor.getIdProveedor();
	}

	@Override
	public Proveedor getRowData(Integer rowKey) {

		List<Proveedor> proveedores = (List<Proveedor>) getWrappedData();
		
		 for(Proveedor proveedor : proveedores) {  
	            if(proveedor.getIdProveedor().equals(rowKey))  
	                return proveedor;
	     }  
		return null;
	}


}
