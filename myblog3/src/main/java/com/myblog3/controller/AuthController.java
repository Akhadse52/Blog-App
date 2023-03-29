package com.myblog3.controller;

import com.myblog3.entity.Role;
import com.myblog3.entity.User;
import com.myblog3.payload.JWTAuthResponse;
import com.myblog3.payload.LoginDto;
import com.myblog3.payload.SignUpDto;
import com.myblog3.repository.RoleRepository;
import com.myblog3.repository.UserRepository;
import com.myblog3.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;


@RestController
@RequestMapping ("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RoleRepository roleRepo;

    // for sigin Api
    //http://localhost:8080/api/auth/sigin
    @PostMapping("/sigin")
    public ResponseEntity<JWTAuthResponse> authenticationUser(@RequestBody LoginDto loginDto){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authenticate);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }





    //http://localhost:8080/api/auth/sigup
    @PostMapping("/sigup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        if(userRepo.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is Already Present",HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("User Name Already Present",HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_ADMIN").get();

        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);

        return  new ResponseEntity<>("User Register Successfully",HttpStatus.OK);
    }
}
