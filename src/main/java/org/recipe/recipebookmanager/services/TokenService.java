package org.recipe.recipebookmanager.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.recipe.recipebookmanager.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    //Identifica quem está criando o token
                    .withIssuer("recipe_book")
                    // diz o usuário que está criando
                    .withSubject(user.getLogin())
                    // define um tempo de expiração
                    .withExpiresAt(generateExpirationData())
                    .sign(algorithm); //Assina e gera o token

            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro while generate token", exception);
        }
    }

    private Instant generateExpirationData(){
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean validationToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).build().verify(token);
            return true;
        }catch (JWTVerificationException exception){
            return false;
        }
    }

    public String getUserNameOfToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("recipe_book").build().verify(token).getSubject();
        }catch (JWTVerificationException exception){
            return "Token Invalid or Expired";
        }
    }


}
