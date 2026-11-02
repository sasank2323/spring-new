package com.example.demo.Controller;

import com.example.demo.Advice.ApiResponse;
import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.SignupDto;
import com.example.demo.Dto.UserDto;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.Cookie; // ✅
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService us;
    private final AuthService as;
    @PostMapping
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto sd)
    {
        UserDto user=us.signup(sd);
       return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto ld, HttpServletRequest request, HttpServletResponse response)
    {
       String token=as.login(ld);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
       return ResponseEntity.ok(new ApiResponse<>(token));
    }
}
