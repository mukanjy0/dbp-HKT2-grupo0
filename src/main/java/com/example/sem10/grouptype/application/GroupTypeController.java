package com.example.sem10.grouptype.application;

import com.example.sem10.grouptype.domain.GroupType;
import com.example.sem10.grouptype.domain.GroupTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grouptype")
public class GroupTypeController {
    @Autowired
    private GroupTypeRepository repository;

    @GetMapping
    public ResponseEntity<List<GroupType>> read () {
        List<GroupType> groupTypes = repository.findAll();
        return new ResponseEntity<>(groupTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupType> read (@PathVariable Long id) {
        Optional<GroupType> groupType = repository.findById(id);
        return new ResponseEntity<>(groupType.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create (@RequestBody GroupType groupType) {
        repository.save(groupType);
        return ResponseEntity.status(201).body("Group type created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(200).body("Group type deleted.");
    }

}
