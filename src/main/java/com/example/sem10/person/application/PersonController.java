package com.example.sem10.person.application;

import com.example.sem10.group.domain.Group;
import com.example.sem10.group.domain.GroupRepository;
import com.example.sem10.person.domain.Person;
import com.example.sem10.person.domain.PersonRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GroupRepository groupRepository;
     @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> personas = personRepository.findAll();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @GetMapping("/{x}")
    public ResponseEntity<Person> getPersonX(@PathVariable Long x) {
        Optional<Person> optionalPerson = personRepository.findById(x);
        if (optionalPerson.isPresent()) {
            return new ResponseEntity<>(optionalPerson.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.status(201).body("Person created");
    }


    @GetMapping("/groups/{id}")
    public ResponseEntity<List<Group>> getGroups(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            List<Group> result = new ArrayList<Group>();
            List<Group> groups = groupRepository.findAll();
            for (Group group : groups) {
                for (Person p : group.getPeople()) {
                    if (p.getId() == person.get().getId()) {
                        result.add(group);
                    }
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
