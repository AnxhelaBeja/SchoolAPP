package com.example.appSchool.service;

import com.example.appSchool.model.AuthenticationResponse;
import com.example.appSchool.model.Professor;
import com.example.appSchool.model.Rolee;
import com.example.appSchool.model.User1;
import com.example.appSchool.model.dto.RegisterProfessorRequest;
import com.example.appSchool.repository.ProfessorRepository;
import com.example.appSchool.repository.User1Repository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationService {
    private final User1Repository user1Repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfessorRepository professorRepository;

    public AuthenticationService(User1Repository user1Repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager, ProfessorRepository professorRepository) {
        this.user1Repository = user1Repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.professorRepository = professorRepository;
    }
    public AuthenticationResponse register(RegisterProfessorRequest request) {
        User1 user = new User1();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Rolee.PROFESSOR);
        user = user1Repository.save(user);

        Professor professor = new Professor();
        professor.setFirstName(request.getFirstName());
        professor.setLastName(request.getLastName());
        professor.setUser1(user);
        professor = professorRepository.save(professor);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

//    public AuthenticationResponse register(User1   request) {
//       User1 user = new User1();
////       user.setFirstName(request.getFirstName());
////       user.setLastName(request.getLastName());
//       user.setUsername(request.getUsername());
//       user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//       user.setRole(Rolee.PROFESSOR);
//
//       user = user1Repository.save(user);
//
//        Professor professor = new Professor();
//        professor.setFirstName(request.getFirstName());
//        professor.setLastName(request.getLastName());
//        professor.setUser1(user);
//
//        professor = professorRepository.save(professor);
//
//       String token = jwtService.generateToken(user);
//       return new AuthenticationResponse(token);
//    }

    public AuthenticationResponse authenticate(User1 request) {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           request.getUsername(),
                           request.getPassword()));

           User1 user = user1Repository.findByUsername(request.getUsername()).orElseThrow();
           String token = jwtService.generateToken(user);
           return new AuthenticationResponse(token);
    }
}
