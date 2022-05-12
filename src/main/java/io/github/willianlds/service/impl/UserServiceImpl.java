package io.github.willianlds.service.impl;

import io.github.willianlds.entity.User;
import io.github.willianlds.exception.PasswordInvalidException;
import io.github.willianlds.repository.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Users userRepository;

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    public UserDetails authenticate(User user){
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        boolean matchesPassword = encoder.matches(user.getPassword(), userDetails.getPassword());

        if(matchesPassword){
            return userDetails;
        }
        throw new PasswordInvalidException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database"));

        String[] roles = user.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[] {"USER"};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
