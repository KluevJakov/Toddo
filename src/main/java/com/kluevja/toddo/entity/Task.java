package com.kluevja.toddo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private int priority;
    @ManyToOne
    private User creator;
    @ManyToOne
    private User performer;
    @ManyToOne
    private Stage stage;

}
