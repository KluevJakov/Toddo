package com.kluevja.toddo.service;

import com.kluevja.toddo.config.VianoConstants;
import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.entity.dto.MessageDto;
import com.kluevja.toddo.repository.StageRepository;
import com.kluevja.toddo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private StageRepository stageRepository;

    public ResponseEntity<?> createEvent(Task task) {
        if (task.getIsEventEnd() == null || !task.getIsEventEnd()) {
            task.setIsEventEnd(false);
            task.setEventDuration(null);
        }
        List<String> validateList = validateTaskEventFields(task);
        if (validateList.size() != 0) {
            return ResponseEntity.badRequest().body("Проверьте заполненность полей: " + validateList);
        }

        task.setStage(stageRepository.getById(VianoConstants.STAGE_1_ID));
        taskRepository.save(task);
        return ResponseEntity.ok().body(new MessageDto("Событие отправлено на проверку!"));
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(taskRepository.findAll()); //TODO: КРОМЕ АРХИВА
    }

    public ResponseEntity<?> countNotify() {
        return ResponseEntity.ok().body(new MessageDto(taskRepository.countNotify().toString()));
    }

    private List<String> validateTaskEventFields(Task task) {
        List<String> resultList = new ArrayList<>();
        if (task.getEventDescription() == null) resultList.add("Описание события");
        if (task.getEventWhat() == null) resultList.add("Что произошло");
        if (task.getEventHow() == null) resultList.add("Как произошло");
        if (task.getEventWhy() == null) resultList.add("Почему произошло");
        if (task.getEventDamageComponents() == null) resultList.add("Пораженные компоненты");
        if (task.getEventDamageBusiness() == null) resultList.add("Негативное воздействие на бизнес");
        if (task.getEventIndentVulnerability() == null) resultList.add("Любые идентифицированные уязвимости");
        if (task.getBeginDate() == null) resultList.add("Дата и время наступления события");
        if (task.getDetectionDate() == null) resultList.add("Дата и время обнаружения события");
        if (task.getReportDate() == null) resultList.add("Дата и время сообщения о событии");
        if (task.getIsEventEnd() == null) resultList.add("Закончилось ли событие?");
        if (task.getIsEventEnd() != null && task.getIsEventEnd()) {
            if (task.getEventDuration() == null) resultList.add("Длительность события");
        }
        return resultList;
    }
}
