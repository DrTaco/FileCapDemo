package nl.louis.filecapdemo.controller;

import static org.junit.jupiter.api.Assertions.*;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import nl.louis.filecapdemo.service.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

public class ApplicationControllerTest {

    @Mock
    private StateMachine<States, Events> stateMachine;

    @Mock
    private FileUploadService fileUploadService;

    private ApplicationController applicationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        applicationController = new ApplicationController();
    }

    @Test
    public void testUploadFile() {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);

        // Act
        applicationController.uploadFile(file);

        // Assert
        verify(stateMachine).getExtendedState().getVariables().put(eq("id"), anyString());
        verify(stateMachine).sendEvent(Events.CHECK);
    }
}