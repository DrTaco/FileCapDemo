package nl.louis.filecapdemo.config.statemachine;

import nl.louis.filecapdemo.service.FileCheckService;
import nl.louis.filecapdemo.service.FileUploadService;
import nl.louis.filecapdemo.service.InviteService;
import nl.louis.filecapdemo.service.action.CheckAction;
import nl.louis.filecapdemo.service.action.InviteAction;
import nl.louis.filecapdemo.service.action.UploadAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Autowired private FileCheckService fileCheckService;
    @Autowired private FileUploadService fileUploadService;
    @Autowired private InviteService inviteService;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.IDLE)
                .state(States.CHECKING, new CheckAction(fileCheckService))
                .state(States.INVITING, new InviteAction(inviteService))
                .state(States.UPLOADING, new UploadAction(fileUploadService));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.IDLE)
                .target(States.CHECKING)
                .event(Events.CHECK)
                .and()
                .withExternal()
                .source(States.CHECKING)
                .target(States.INVITING)
                .event(Events.INVITE)
                .and()
                .withExternal()
                .source(States.INVITING)
                .target(States.UPLOADING)
                .event(Events.UPLOAD)
                .and()
                .withExternal()
                .source(States.UPLOADING)
                .target(States.IDLE)
                .event(Events.RESET);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}