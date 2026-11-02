package com.example.demo.Service;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDto ld) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ld.getEmail(), ld.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return token;
    }

}
