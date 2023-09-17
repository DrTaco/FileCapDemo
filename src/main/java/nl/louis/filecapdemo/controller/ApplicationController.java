package nl.louis.filecapdemo.controller;

import nl.louis.filecapdemo.config.statemachine.Events;
import nl.louis.filecapdemo.config.statemachine.States;
import nl.louis.filecapdemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;

@Controller
public class ApplicationController {

    @Autowired private StateMachine<States, Events> machine;
    @Autowired FileUploadService fileUploadService;

    @GetMapping("/")
    public String index() {
        return "upload_form";
    }

    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        machine.getExtendedState().getVariables().put("id", generateId());
        machine.sendEvent(Events.CHECK);
        return "upload_form";
    }

    private String generateId() {
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder(25);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 25; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
