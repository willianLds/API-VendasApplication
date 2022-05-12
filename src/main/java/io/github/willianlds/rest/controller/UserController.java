package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.User;
import io.github.willianlds.exception.PasswordInvalidException;
import io.github.willianlds.rest.dto.CredentialsDTO;
import io.github.willianlds.rest.dto.TokenDTO;
import io.github.willianlds.security.jwt.JwtService;
import io.github.willianlds.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Valid User user){
        String passwordCrypto = encoder.encode(user.getPassword());
        user.setPassword(passwordCrypto);
        return userService.save(user);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials){
        try {
            User user = User.builder()
                    .username(credentials.getUsername())
                    .password(credentials.getPassword()).build();
            UserDetails userAuthenticate = userService.authenticate(user);
            String token = jwtService.generatedToken(user);

            return new TokenDTO(user.getUsername(), token);
        }catch (UsernameNotFoundException | PasswordInvalidException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
