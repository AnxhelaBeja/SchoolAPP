package com.example.appSchool.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@Entity
@Table(name="user1")
public class User1 implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
//    @Column(name="firstName")
//@Transient
//private String firstName;
//    @Column(name= "lastName")
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Enumerated(value = EnumType.STRING)
     Rolee role;
    //    @OneToOne
//    @JoinColumn(name = "role_id")
//   private Role role;

    public User1() {
    }

    public User1(Long id, String username, String password, Rolee role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rolee getRole() {
        return role;
    }

    public void setRole(Rolee role) {
        this.role = role;
    }



}
