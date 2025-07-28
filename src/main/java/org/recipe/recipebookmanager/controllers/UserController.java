package org.recipe.recipebookmanager.controllers;

import jakarta.validation.Valid;
import org.recipe.recipebookmanager.dtos.AuthenticationDTO;
import org.recipe.recipebookmanager.dtos.RegisterDTO;
import org.recipe.recipebookmanager.entity.UserEntity;
import org.recipe.recipebookmanager.services.TokenService;
import org.recipe.recipebookmanager.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        if (userService.existsUser(registerDTO.login())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //Encripta a senha do usuário
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        UserEntity newUser = new UserEntity();
        BeanUtils.copyProperties(registerDTO, newUser);
        newUser.setPassword(encryptedPassword);


        userService.saveUser(newUser);


        var usernamePassword = new UsernamePasswordAuthenticationToken(registerDTO.login(), registerDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
