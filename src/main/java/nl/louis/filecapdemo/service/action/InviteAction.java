package nl.louis.filecapdemo.service.action;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.service.AbstractFileCapService;

public class InviteAction extends AbstractFileCapAction {
    public InviteAction(AbstractFileCapService service) {
        super(service);
    }

    @Override
    protected Events getNextEvent() {
        return Events.UPLOAD;
    }

}
