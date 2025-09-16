package com.izthedark.cursedfate.auth.application.services;

import com.izthedark.cursedfate.auth.domain.model.AuthToken;
import com.izthedark.cursedfate.auth.application.dto.Credentials;
import com.izthedark.cursedfate.auth.domain.ports.in.UserLoginUseCase;
import com.izthedark.cursedfate.auth.domain.ports.in.UserRegisterUseCase;
import com.izthedark.cursedfate.auth.domain.ports.out.LoadUserPort;
import com.izthedark.cursedfate.auth.domain.ports.out.SaveUserPort;
import com.izthedark.cursedfate.auth.domain.ports.out.TokenProviderPort;
import com.izthedark.cursedfate.config.exceptions.InvalidCredentialsException;
import com.izthedark.cursedfate.user.infrastructure.persistence.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserLoginUseCase, UserRegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final TokenProviderPort tokenProviderPort;

    public AuthService(LoadUserPort loadUserPort, SaveUserPort saveUserPort, TokenProviderPort tokenProviderPort) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public AuthToken login(Credentials credentials) {

        var user = loadUserPort.loadUserByEmail(credentials.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        System.out.println("Password en BD: '" + user.getPassword() + "'");
        System.out.println("Password recibido: '" + credentials.getPassword() + "'");

        if (!user.getPassword().equals(credentials.getPassword())) throw new InvalidCredentialsException("Password missmatch!");


        return tokenProviderPort.generateToken(user);
    }

    @Override
    public AuthToken register(Credentials credentials) {
        UserEntity entity = new UserEntity();
        var newUser = saveUserPort.save(credentials);
        return tokenProviderPort.generateToken(newUser);
    }
}
