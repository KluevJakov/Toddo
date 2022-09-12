package com.kluevja.toddo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sheets")
@Getter
@Setter
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private boolean isGroup;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Group assigned;
    @ManyToMany
    private Set<Task> tasks;
    @ManyToMany
    private Set<Stage> stages;

    @Override
    public String toString() {
        return "Sheet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isGroup=" + isGroup +
                ", creator=" + creator +
                ", assigned=" + assigned +
                ", tasks=" + tasks +
                ", stages=" + stages +
                '}';
    }
}
