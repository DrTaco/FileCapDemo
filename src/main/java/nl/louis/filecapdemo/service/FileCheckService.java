package nl.louis.filecapdemo.service;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class FileCheckService extends AbstractFileCapService {

    public static final String FILENAME_FORM_HEADER = "filename";
    public static final String SENDER_FORM_HEADER = "sender";
    public static final String MIME_FORM_HEADER = "mime";

    @Value("${filecap.api.check_path}") private String path;

    @Override
    protected MultiValueMap<String, Object> accumulatedBody(StateContext<States, Events> context) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        MockMultipartFile file = getFile();
        body.add(FILENAME_FORM_HEADER, file.getOriginalFilename());
        body.add(SENDER_FORM_HEADER, getAllowedSenderEmail());
        body.add(MIME_FORM_HEADER, file.getContentType());
        return body;
    }

    @Override
    protected String getPath() {
        return this.path;
    }
}
