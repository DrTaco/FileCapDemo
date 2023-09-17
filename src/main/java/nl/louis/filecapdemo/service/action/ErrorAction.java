package nl.louis.filecapdemo.service.action;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.service.AbstractFileCapService;

public class ErrorAction extends AbstractFileCapAction {

    public ErrorAction(AbstractFileCapService service) {
        super(service);
    }

    @Override
    protected Events getNextEvent() {
        return null;
    }
}
