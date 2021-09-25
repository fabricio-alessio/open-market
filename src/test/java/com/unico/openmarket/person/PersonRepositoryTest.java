package com.unico.openmarket.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.unico.openmarket.OpenMarketApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpenMarketApplication.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void givenPersonRepository_whenSaveAndRetrievePerson_thenOK() {
        Person person = personRepository
                .save(Person.builder().firstName("Joao").lastName("Santana").build());
        final var foundPerson = personRepository
                .findById(person.getId());

        assertTrue(foundPerson.isPresent());
        assertEquals(person.getFirstName(), foundPerson.get().getFirstName());
        assertEquals(person.getLastName(), foundPerson.get().getLastName());
    }
}