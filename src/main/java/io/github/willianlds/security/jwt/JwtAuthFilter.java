package io.github.willianlds.security.jwt;

import io.github.willianlds.service.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UserServiceImpl usuarioService){
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValid(token);

            if(isValid){
                String loginUser = jwtService.getLoginUser(token);
                UserDetails user = usuarioService.loadUserByUsername(loginUser);
                UsernamePasswordAuthenticationToken userToken = new
                        UsernamePasswordAuthenticationToken(user,null,
                        user.getAuthorities());
                userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
