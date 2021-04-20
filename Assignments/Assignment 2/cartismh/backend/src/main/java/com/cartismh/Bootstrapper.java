package com.cartismh;

import com.cartismh.security.AuthService;
import com.cartismh.security.dto.SignupRequest;
import com.cartismh.user.RoleRepository;
import com.cartismh.user.UserRepository;
import com.cartismh.user.model.ERole;
import com.cartismh.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            authService.register(SignupRequest.builder()
                    .email("root@root.com")
                    .username("root")
                    .password("root123.")
                    .roles(Set.of("ADMIN"))
                    .build());
        }
    }
}
