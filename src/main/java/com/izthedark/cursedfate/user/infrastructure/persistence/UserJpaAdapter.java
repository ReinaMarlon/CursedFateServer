package com.izthedark.cursedfate.user.infrastructure.persistence;


import com.izthedark.cursedfate.auth.application.dto.Credentials;
import com.izthedark.cursedfate.auth.application.dto.Register;
import com.izthedark.cursedfate.auth.domain.ports.out.LoadUserPort;
import com.izthedark.cursedfate.auth.domain.ports.out.SaveUserPort;
import com.izthedark.cursedfate.user.domain.model.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements LoadUserPort, SaveUserPort {

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
                        entity.getCoins()
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
                saved.getCoins()
        );
    }

}
