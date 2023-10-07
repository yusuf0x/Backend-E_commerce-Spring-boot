package com.ecommerce.app.controllers;
import com.ecommerce.app.config.JwtService;
import com.ecommerce.app.config.UserDetailsImpl;
import com.ecommerce.app.models.Person;
import com.ecommerce.app.models.User;
import com.ecommerce.app.payload.request.LoginRequest;
import com.ecommerce.app.payload.request.SignupRequest;
import com.ecommerce.app.payload.response.JwtResponse;
import com.ecommerce.app.payload.response.MessageResponse;
import com.ecommerce.app.repositories.UserRepository;
import com.ecommerce.app.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonService personService;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        Person person = personService.createPerson(user);
        user.setPerson(person);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        if (!userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email not found !"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtService.generateToken((UserDetailsImpl) authentication.getPrincipal());
        String jwt = jwtService.createToken(new HashMap<>(),request.getEmail());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail()));
    }
}
