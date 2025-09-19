package com.izthedark.cursedfate.user.infrastructure.persistence;


import com.izthedark.cursedfate.auth.application.dto.Register;
import com.izthedark.cursedfate.auth.domain.ports.out.LoadUserPort;
import com.izthedark.cursedfate.auth.domain.ports.out.SaveUserPort;
import com.izthedark.cursedfate.user.application.dto.UserToApp;
import com.izthedark.cursedfate.user.domain.model.User;
import com.izthedark.cursedfate.user.domain.ports.out.UserGetDataPort;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements LoadUserPort, SaveUserPort, UserGetDataPort {

    private final UserJpaRepository repository;

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPassword(),
                        entity.getLevel(),
                        entity.getCoins(),
                        entity.getToken()
                ));
    }

    @Override
    public User save(Register register) {
        UserEntity entity = new UserEntity();
        entity.setUsername(register.getUsername());
        entity.setEmail(register.getEmail());
        entity.setPassword(register.getPassword());
        entity.setLevel(1L);
        entity.setCoins(0L);

        UserEntity saved = repository.save(entity);

        return new User(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getLevel(),
                saved.getCoins(),
                saved.getToken()
        );
    }

    @Override
    public void updateToken(Long userId, String token) {
        repository.findById(userId).ifPresent(entity -> {
            entity.setToken(token);
            repository.save(entity);
        });
    }

    @Override
    public Optional<UserToApp> loadUserByToken(String token) {
        return repository.findByToken(token)
                .map(entity -> new UserToApp(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getLevel(),
                        entity.getCoins(),
                        entity.getCharacters()
                ));
    }

}
