package com.izthedark.cursedfate.game.infrastructure.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatusController {
    @GetMapping("/")
    public String health() {
        return "App is running!";
    }
    //To test the status of app.
}
