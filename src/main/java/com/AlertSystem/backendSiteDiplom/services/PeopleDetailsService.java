package com.AlertSystem.backendSiteDiplom.services;

import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.repositories.PeopleRepository;
import com.AlertSystem.backendSiteDiplom.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;


    public PeopleDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<People> people = peopleRepository.findByLogin(login);

        if (people.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(people.get());
    }
}
