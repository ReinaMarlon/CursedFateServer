package com.izthedark.cursedfate.auth.infrastructure.web.controller;

import com.izthedark.cursedfate.auth.application.dto.Credentials;
import com.izthedark.cursedfate.auth.application.dto.Register;
import com.izthedark.cursedfate.auth.application.services.AuthService;
import com.izthedark.cursedfate.auth.domain.model.AuthToken;
import com.izthedark.cursedfate.auth.domain.model.Origin;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody Credentials credentials) {
        System.out.println("Email recibido: '" + credentials.getEmail() + "'");
        System.out.println("Password recibido: '" + credentials.getPassword() + "'");
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        AuthToken token = authService.register(register);

        if (register.getOrigin() == Origin.WEB) {
            return ResponseEntity.ok("Usuario registrado correctamente desde la web.");
        }

        return ResponseEntity.ok(token);
    }
}
