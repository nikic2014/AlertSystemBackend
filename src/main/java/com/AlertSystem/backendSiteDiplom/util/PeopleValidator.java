package com.AlertSystem.backendSiteDiplom.util;


import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.services.PeopleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {
    private final PeopleDetailsService peopleDetailsService;

    @Autowired
    public PeopleValidator(PeopleDetailsService peopleDetailsService) {
        this.peopleDetailsService = peopleDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        People person = (People) target;

        try {
            peopleDetailsService.loadUserByUsername(person.getLogin());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue("login", "", "Человек с таким именем существует");
    }

}
