package nl.louis.filecapdemo.service.action;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import nl.louis.filecapdemo.service.AbstractFileCapService;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class AbstractFileCapAction implements Action<States, Events> {

    private AbstractFileCapService service;

    public AbstractFileCapAction(AbstractFileCapService service) {
        super();
        this.service = service;
    }
    @Override
    public void execute(StateContext<States, Events> stateContext) {
         if(service.execute(stateContext)){
            stateContext.getStateMachine().sendEvent(getNextEvent());
         } else {
             stateContext.getStateMachine().sendEvent(Events.RESET);
         }
    }
    protected abstract Events getNextEvent();
}
