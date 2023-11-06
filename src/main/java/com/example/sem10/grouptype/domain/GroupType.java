package com.example.sem10.grouptype.domain;

import com.example.sem10.group.domain.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GroupType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "groupType")
    Set<Group> groups;
}
