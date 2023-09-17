package nl.louis.filecapdemo.service;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.statemachine.StateContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InviteServiceTest {

    @Mock
    private StateContext<States, Events> stateContext;

    private InviteService inviteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        inviteService = new InviteService();
    }

    @Test
    public void testAccumulatedBody() {
        // Arrange
        when(inviteService.getInviteId(stateContext)).thenReturn("iiiiitest");
        when(inviteService.getAllowedSenderEmail()).thenReturn("test@example.com");

        // Act
        MultiValueMap<String, Object> body = inviteService.accumulatedBody(stateContext);

        // Assert
        assertEquals(4, body.size());
        assertEquals("iiiiitest", body.getFirst(InviteService.ID_HEADER));
        assertEquals("test@example.com", body.getFirst(InviteService.SENDER_EMAIL_HEADER));
        assertEquals("tlouistom@gmail.com", body.getFirst(InviteService.RECEIVER_EMAIL_HEADER));
        assertEquals("true", body.getFirst(InviteService.NOTIFY_HEADER));
    }

    @Test
    public void testGetPath() {
        // Act
        String path = inviteService.getPath();

        // Assert
        assertEquals("${filecap.api.invite_path}", path);
    }
}