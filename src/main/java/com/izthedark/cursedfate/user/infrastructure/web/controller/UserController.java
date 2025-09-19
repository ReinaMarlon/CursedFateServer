package com.izthedark.cursedfate.user.infrastructure.web.controller;

import com.izthedark.cursedfate.game.application.service.GameService;
import com.izthedark.cursedfate.user.application.dto.UserToApp;
import com.izthedark.cursedfate.user.application.service.UserService;
import com.izthedark.cursedfate.user.domain.model.User;
import com.izthedark.cursedfate.user.infrastructure.persistence.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserData")
    public ResponseEntity<UserToApp> getUserData(@RequestParam String token) {
        return userService.loadUserByToken(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
