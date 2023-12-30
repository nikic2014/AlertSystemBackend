package com.AlertSystem.backendSiteDiplom.security;

import com.AlertSystem.backendSiteDiplom.models.People;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PersonDetails implements UserDetails {
    private final People people;

    public PersonDetails(People people) {
        this.people = people;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.people.getPassword();
    }

    @Override
    public String getUsername() {
        return this.people.getLogin();
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

    public People getPeople(){
        return this.people;
    }
}
