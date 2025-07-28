package org.recipe.recipebookmanager.services;

import org.recipe.recipebookmanager.entity.UserEntity;
import org.recipe.recipebookmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.userRepository.findByLogin(login);
    }

    //Verifica se o usuário existe
    public boolean existsUser(String login){
        return this.userRepository.existsByLogin(login);
    }

    //Salva o usuário
    public void saveUser(UserEntity userEntity){
        this.userRepository.save(userEntity);
    }
}
