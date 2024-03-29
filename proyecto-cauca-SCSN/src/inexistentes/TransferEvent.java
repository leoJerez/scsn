package inexistentes;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

public class TransferEvent extends AjaxBehaviorEvent {

    private List<?> items;
    private boolean add;

	public TransferEvent(UIComponent component, Behavior behavior, List<?> items, boolean add) {
		super(component, behavior);
        this.items = items;
        this.add = add;
	}

	@Override
	public boolean isAppropriateListener(FacesListener faceslistener) {
		return (faceslistener instanceof AjaxBehaviorListener);
	}

	@Override
	public void processListener(FacesListener faceslistener) {
        ((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);
	}

    public boolean isAdd() {
        return add;
    }
    
    public boolean isRemove() {
        return !add;
    }

    public List<?> getItems() {
        return items;
    }
}
