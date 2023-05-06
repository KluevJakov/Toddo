package com.kluevja.toddo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long regNumber; //номер события или инцидента, проставляется Руководителем ГРИИБ
    private Boolean isIncident; //событие или инцидент?
    /* Описание события ИБ */
    private String eventDescription; //Описание произошло
    private String eventWhat; //Что произошло
    private String eventHow; //Как произошло
    private String eventWhy; //Почему произошло
    private String eventDamageComponents; //Пораженные компоненты
    private String eventDamageBusiness; //Негативное воздействие на бизнес
    private String eventIndentVulnerability; //Любые идентифицированные уязвимости
    /* Подробности о событии ИБ */
    private Date beginDate; // Дата и время наступления события
    private Date detectionDate; // Дата и время обнаружения события
    private Date reportDate; // Дата и время сообщения о событии
    private Boolean isEventEnd; // закончилось?
    private String eventDuration; //длительность события, если оно уже закончилось
    /* ------------------------ */
    @ManyToOne
    private User reporter; //сообщающий
    @ManyToOne
    private User performer; //текущий исполнитель
    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage; //текущая стадия

}
