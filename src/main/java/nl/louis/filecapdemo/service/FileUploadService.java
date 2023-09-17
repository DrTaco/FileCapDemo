package nl.louis.filecapdemo.service;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@Service
public class FileUploadService extends AbstractFileCapService {

    public static final String IDS_HEADER = "ids";
    public static final String FROM_HEADER = "from";
    public static final String SUBJECT_HEADER = "subject";
    public static final String COMMENT_HEADER = "comment";
    public static final String REC_0_HEADER = "rec0";
    public static final String FILE_01_HEADER = "file01";
    public static final String FILE_METADATA_HEADER = "fileMetadata";
    public static final String ENCRYPT_MESSAGE_HEADER = "encryptMessage";

    @Value("${filecap.api.send_path}") private String path;

    @Override
    protected MultiValueMap<String, Object> accumulatedBody(StateContext<States, Events> context) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add(IDS_HEADER, getId(context));
        body.add(FROM_HEADER, getAllowedSenderEmail());
        body.add(SUBJECT_HEADER, "API Exampleasd");
        body.add(COMMENT_HEADER, "This is an example");
        body.add(ENCRYPT_MESSAGE_HEADER, "true");
        body.add(REC_0_HEADER, "XXX");
        body.add(FILE_METADATA_HEADER, getFileMetaData());
        body.add(FILE_01_HEADER, getFileByteArray(getFile()));
        return body;
    }

    @Override
    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);
        return headers;
    }

    @Override
    protected String getPath() {
        return this.path;
    }

    private ByteArrayResource getFileByteArray(MockMultipartFile file){
        ByteArrayResource byteArrayResource;
        try {
            byteArrayResource = new ByteArrayResource(file.getBytes());
        } catch (IOException e) {
            System.out.println("Chosen file could not be converted to ByteArrayResource, Resetting state machine");
            throw new RuntimeException(e);
        }
        return byteArrayResource;
    }

}
