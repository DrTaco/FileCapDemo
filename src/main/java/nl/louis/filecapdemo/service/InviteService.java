package nl.louis.filecapdemo.service;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
public class InviteService extends AbstractFileCapService {

    public static final String ID_HEADER = "id";
    public static final String SENDER_EMAIL_HEADER = "senderEmail";
    public static final String RECEIVER_EMAIL_HEADER = "receiverEmail";
    public static final String NOTIFY_HEADER = "notify";

    @Value("${filecap.api.invite_path}") private String path;

    @Override
    protected String getPath() {
        return this.path;
    }

    @Override
    protected MultiValueMap<String, Object> accumulatedBody(StateContext<States, Events> context) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add(ID_HEADER, getInviteId(context));
        body.add(SENDER_EMAIL_HEADER, getAllowedSenderEmail());
        body.add(RECEIVER_EMAIL_HEADER, "tlouistom@gmail.com");
        body.add(NOTIFY_HEADER, Boolean.TRUE.toString());
        return body;
    }

    String getInviteId(StateContext<States, Events> context){
        return String.format("iiiii%s",getId(context));
    }
}
