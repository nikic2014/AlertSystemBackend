package com.AlertSystem.backendSiteDiplom.services;

import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.repositories.PeopleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public PeopleService(PeopleRepository peopleRepository,
                         PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public People getByLogin(String login){
        Optional<People> people = peopleRepository.findByLogin(login);

        if (people.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return people.get();
    }

    public People getById(int id){
        Optional<People> people = peopleRepository.findById(id);

        if (people.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return people.get();
    }

    public void save(People people){
        people.setPassword(passwordEncoder.encode(people.getPassword()));
        this.peopleRepository.save(people);
    }
}
