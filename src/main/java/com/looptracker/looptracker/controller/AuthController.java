package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.SigninForm;
import com.looptracker.looptracker.dto.response.LoginResponse;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.security.JWT.JwtTokenProvider;
import com.looptracker.looptracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @RequestBody SigninForm login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateJwtToken( authentication);

        User user = userService.findByUsername(login.getUsername());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setFirstName(user.getFirstName());
        loginResponse.setLastName(user.getLastName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());
        loginResponse.setPhoneNumber(user.getPhoneNumber());
        loginResponse.setGender(String.valueOf(user.getGender()));
        loginResponse.setCreateAt(user.getCreateAt());
        loginResponse.setAvatarUrl(user.getAvatarUrl());
        loginResponse.setToken(jwt);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
