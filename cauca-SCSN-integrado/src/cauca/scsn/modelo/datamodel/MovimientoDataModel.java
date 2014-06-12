package cauca.scsn.modelo.datamodel;

import java.util.List;

import inexistentes.SelectableDataModel;

import javax.faces.model.ListDataModel;

import cauca.scsn.modelo.entidad.Movimiento;

public class MovimientoDataModel extends ListDataModel implements SelectableDataModel<Movimiento> {

	public MovimientoDataModel() {
		super();
	}

	public MovimientoDataModel(List<Movimiento> list) {
		super(list);
	}

	@Override
	public Object getRowKey(Movimiento movimiento) {
		return null;
	}

	@Override
	public Movimiento getRowData(Integer rowKey) {
		List<Movimiento> movimientos = (List<Movimiento>) getWrappedData();
		
		 for(Movimiento movimiento : movimientos) {  
	            if(movimiento.getId().getIdNeumatico().equals(rowKey))  
	                return movimiento;
	     }  
		
		return null;
	}

}
