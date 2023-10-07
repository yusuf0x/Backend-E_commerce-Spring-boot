package com.ecommerce.app.services;

import com.ecommerce.app.models.Person;
import com.ecommerce.app.models.User;
import com.ecommerce.app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(User user){
        Person person = new Person();
//        person.setUser(user);
        return personRepository.save(person);
    }
    public Person save(Person person){
        return personRepository.save(person);
    }
}
