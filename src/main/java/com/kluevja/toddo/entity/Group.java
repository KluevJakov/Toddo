package com.kluevja.toddo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany
    private Set<User> members;
}
