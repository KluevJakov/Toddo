package com.kluevja.toddo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sheets")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Boolean isGroup;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Group assigned;
    @ManyToMany
    private Set<Task> tasks;
    @ManyToMany
    private Set<Stage> stages;
}
