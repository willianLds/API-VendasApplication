package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.Usuario;
import io.github.willianlds.exception.PasswordInvalidException;
import io.github.willianlds.rest.dto.CredentialsDTO;
import io.github.willianlds.rest.dto.TokenDTO;
import io.github.willianlds.security.jwt.JwtService;
import io.github.willianlds.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/saveuser")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String passwordCrypto = encoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCrypto);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials){
        try {
            Usuario user = Usuario.builder()
                    .username(credentials.getUsername())
                    .password(credentials.getPassword()).build();
            UserDetails userAuthenticate = usuarioService.authenticate(user);
            String token = jwtService.generatedToken(user);

            return new TokenDTO(user.getUsername(), token);
        }catch (UsernameNotFoundException | PasswordInvalidException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
