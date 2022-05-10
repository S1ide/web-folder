package com.project.webfolder.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table(name="Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @Transient
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<File> files;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate joiningDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate leavingDate;
    private boolean active;

    public void setNewUser(){
        this.joiningDate = LocalDate.now();
        this.active = true;
        this.files = new HashSet<>();
        this.comments = new HashSet<>();
    }


    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
