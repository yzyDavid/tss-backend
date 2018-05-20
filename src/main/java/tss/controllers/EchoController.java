package tss.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;

/**
 * @author yzy
 */
@RestController
@RequestMapping(path = "/echo")
public class EchoController {
    @GetMapping("")
    public String echo() {
        return "Team A TSS is running.";
    }

    @GetMapping("/auth")
    @Authorization
    public String auth(@CurrentUser UserEntity user) {
        return "Login as " + user.getUid();
    }
}
