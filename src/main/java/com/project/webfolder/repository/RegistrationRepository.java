package com.project.webfolder.repository;

import com.project.webfolder.model.User;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
