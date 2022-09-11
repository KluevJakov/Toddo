package com.kluevja.toddo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sheets")
@Getter
@Setter
@ToString
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Group assigned;
    @ManyToMany
    private Set<Task> tasks;
}
