package com.example.sem10.group.application;

import com.example.sem10.group.domain.Group;
import com.example.sem10.group.domain.GroupRepository;
import com.example.sem10.person.domain.Person;
import com.example.sem10.person.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PersonRepository personRepository;
    @GetMapping
    public ResponseEntity<List<Group>> getAll() {
        List<Group> groups = groupRepository.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
       Optional<Group> group = groupRepository.findById(id);
       if (group.isPresent()) {
           return new ResponseEntity<>(group.get(), HttpStatus.OK);
       }
       else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> createGroup(@RequestBody Group group) {
        Set<Person> persons = group.getPeople();
        for (Person person : persons) {
            Optional<Person> completePerson = personRepository.findById(person.getId());
            person = completePerson.get();
        }
        group.setPeople(persons);
        groupRepository.save(group);
        return ResponseEntity.status(201).body("Group created");
    }
    @GetMapping("/persons/{id}")
    public ResponseEntity<Set<Person>> getPeople(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            return new ResponseEntity<>(group.get().getPeople(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
