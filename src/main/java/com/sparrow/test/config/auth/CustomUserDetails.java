package com.sparrow.test.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String emil;
    private String password;
    private GrantedAuthority authorities;




    public CustomUserDetails(Long id,String email, String password, GrantedAuthority authorities) {
        this.id=id;
        this.emil = email;
        this.password = password;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(authorities);
        return auth;

    }

    @Override
    public String getPassword() {
        return password;
    }


    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return emil;
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