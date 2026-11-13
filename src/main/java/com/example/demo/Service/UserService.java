package com.example.demo.Service;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.SignupDto;
import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
    }

    public UserDto signup(SignupDto sd) {
        Optional<User> userOptional = userRepository.findByEmail(sd.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("User already exists"+ sd.getEmail());
        }
         User user=new ModelMapper().map(sd,User.class);
         user.setPassword(passwordEncoder.encode(sd.getPassword()));
         userRepository.save(user);
         return new ModelMapper().map(user, UserDto.class);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }


}
