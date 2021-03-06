package com.project.webfolder.service;

import com.project.webfolder.entity.Role;
import com.project.webfolder.entity.User;
import com.project.webfolder.repository.RoleRepository;
import com.project.webfolder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw  new UsernameNotFoundException("User not found");
        }

        return user;
    }



    public User findUserById(Long userId){
        Optional<User> userFromBd = userRepository.findById(userId);
        return userFromBd.orElse(new User());
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public boolean saveUser(User user){
        User userFromDb = userRepository.findUserByUsername(user.getUsername());
        if(userFromDb != null){
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "USER")));
        user.setNewUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId){
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
