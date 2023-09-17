package nl.louis.filecapdemo.service;

import lombok.Getter;
import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import nl.louis.filecapdemo.model.FileMetaData;
import nl.louis.filecapdemo.repository.FileCapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Service
public abstract class AbstractFileCapService {

    public static final String API_KEY_HEADER = "APIKey";

    @Value("${filecap.api.base_url}") private String baseUrl;
    @Value("${filecap.api.key}") private String apiKey;
    @Value("${filecap.api.allowed_sender_email}")
    @Getter private String allowedSenderEmail;

    @Autowired
    protected FileCapRepository fileCapRepository;

    public boolean execute(StateContext<States, Events> context) {
        boolean retVal = fileCapRepository.execute(getUrl(), getHttpEntity(context));
        if (retVal) {
            System.out.println(String.format("%s was executed successfully", this.getClass().getSimpleName()));
        }
        return retVal;
    }

    protected abstract String getPath();

    protected abstract MultiValueMap<String, Object> accumulatedBody(StateContext<States, Events> context);

    public String getUrl() {
        return UriComponentsBuilder
                .fromUriString(baseUrl)
                .path(getPath())
                .build().toUriString();
    }

    private MultiValueMap<String, Object> getBody(StateContext<States, Events> context) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(API_KEY_HEADER, apiKey);
        accumulateBody(body, context);
        return body;
    }

    protected HttpEntity getHttpEntity(StateContext<States, Events> context) {
        return new HttpEntity<>(getBody(context), getHeaders());
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private void accumulateBody(MultiValueMap<String, Object> body, StateContext<States, Events> context) {
        body.addAll(accumulatedBody(context));
    }

    MockMultipartFile getFile(){
        MockMultipartFile mockMultipartFile;
        try {
            File file = ResourceUtils.getFile("classpath:files/woord.txt");
            FileInputStream stream = new FileInputStream(file);
            mockMultipartFile = new MockMultipartFile(file.getName(), file.getName(), Files.probeContentType(Paths.get(file.getAbsolutePath())), stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mockMultipartFile;
    }

    FileMetaData getFileMetaData() {
        MockMultipartFile mmpf = getFile();
        return new FileMetaData(mmpf.getOriginalFilename(), mmpf.getSize(), mmpf.getContentType());
    }

    protected String getId(StateContext<States, Events> context) {
        return (String) context.getExtendedState().getVariables().get("id");
    }
}
