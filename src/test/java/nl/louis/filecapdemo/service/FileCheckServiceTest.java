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

public class FileCheckServiceTest {

    @Mock
    private StateContext<States, Events> stateContext;

    private FileCheckService fileCheckService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        fileCheckService = new FileCheckService();
    }

    @Test
    public void testAccumulatedBody() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("test.txt", "test.txt", "text/plain", "Test content".getBytes());
        when(fileCheckService.getFile()).thenReturn(file);
        when(fileCheckService.getAllowedSenderEmail()).thenReturn("test@example.com");

        // Act
        MultiValueMap<String, Object> body = fileCheckService.accumulatedBody(stateContext);

        // Assert
        assertEquals(3, body.size());
        assertEquals(file.getOriginalFilename(), body.getFirst(FileCheckService.FILENAME_FORM_HEADER));
        assertEquals(file.getContentType(), body.getFirst(FileCheckService.MIME_FORM_HEADER));
        assertEquals(fileCheckService.getAllowedSenderEmail(), body.getFirst(FileCheckService.SENDER_FORM_HEADER));
    }

    @Test
    public void testGetPath() {
        // Act
        String path = fileCheckService.getPath();

        // Assert
        assertEquals("${filecap.api.check_path}", path);
    }
}