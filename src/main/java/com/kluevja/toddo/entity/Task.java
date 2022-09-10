package com.kluevja.toddo.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Group assigned;

}
