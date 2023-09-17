package nl.louis.filecapdemo.service.action;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.service.AbstractFileCapService;

public class CheckAction extends AbstractFileCapAction {
    public CheckAction(AbstractFileCapService service) {
        super(service);
    }

    @Override
    protected Events getNextEvent() {
        return Events.INVITE;
    }
}
