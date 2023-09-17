package nl.louis.filecapdemo.service;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.statemachine.StateContext;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileUploadServiceTest {

    @Mock
    private StateContext<States, Events> stateContext;

    private FileUploadService fileUploadService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        fileUploadService = new FileUploadService();
    }

    @Test
    public void testAccumulatedBody() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("test.txt", "test.txt", "text/plain", "Test content".getBytes());
        when(fileUploadService.getFile()).thenReturn(file);
        when(fileUploadService.getAllowedSenderEmail()).thenReturn("test@example.com");

        // Act
        MultiValueMap<String, Object> body = fileUploadService.accumulatedBody(stateContext);

        // Assert
        assertEquals(9, body.size());
        assertEquals("test@example.com", body.getFirst(FileUploadService.FROM_HEADER));
        assertEquals("API Exampleasd", body.getFirst(FileUploadService.SUBJECT_HEADER));
        assertEquals("This is an example", body.getFirst(FileUploadService.COMMENT_HEADER));
        assertEquals("true", body.getFirst(FileUploadService.ENCRYPT_MESSAGE_HEADER));
        assertEquals("tlouistom@gmail.com", body.getFirst(FileUploadService.REC_0_HEADER));
        assertEquals(file.getOriginalFilename(), body.getFirst(FileUploadService.FILE_01_HEADER));
    }

    @Test
    public void testGetPath() {
        // Act
        String path = fileUploadService.getPath();

        // Assert
        assertEquals("${filecap.api.send_path}", path);
    }
}