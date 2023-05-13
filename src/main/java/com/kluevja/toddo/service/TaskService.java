package com.kluevja.toddo.service;

import com.kluevja.toddo.config.VianoConstants;
import com.kluevja.toddo.entity.Stage;
import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.entity.dto.MessageDto;
import com.kluevja.toddo.repository.StageRepository;
import com.kluevja.toddo.repository.TaskRepository;
import com.kluevja.toddo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;

    public ResponseEntity<?> createEvent(Task task) {
        if (task.getIsEventEnd() == null || !task.getIsEventEnd()) {
            task.setIsEventEnd(false);
            task.setEventDuration(null);
        }
        List<String> validateList = validateTaskEventFields(task);
        if (validateList.size() != 0) {
            return ResponseEntity.badRequest().body("Проверьте заполненность полей: " + validateList);
        }
        task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_OPER_ISIRT_ID));
        task.setReportDate(new Date());
        task.setStage(stageRepository.getById(VianoConstants.STAGE_1_ID));
        taskRepository.save(task);
        logService.log("Получено сообщение о событии ИБ (Репортер: " + task.getReporter().getEmail() + "; Событие: " + beautifyEventName(task) + ")");
        return ResponseEntity.ok().body(new MessageDto("Событие отправлено на проверку!"));
    }

    public ResponseEntity<?> processIncident(Task task) {
        switch (task.getStage().getId().intValue()) {
            case 2:
                task.setIsIncident(true);
                task.setStage(stageRepository.getById(VianoConstants.STAGE_2_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_MNGR_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Событие: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Событие " + beautifyEventName(task) + " помечено как 'Инцидент' и переведено на руководителя ГРИИБ."));
            case 3:
                List<Long> regNumbers = taskRepository.findRegNumbers();
                if (regNumbers.contains(task.getRegNumber())) {
                    return ResponseEntity.badRequest().body("Инцидент с номером (" + task.getRegNumber() + ") уже зарегистрирован в системе. Введите другой номер.");
                }
                task.setStage(stageRepository.getById(VianoConstants.STAGE_3_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_OPER_AUDIT_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Инциденту " + beautifyEventName(task) + " назначен номер " + task.getRegNumber()));
            case 4:
                task.setStage(stageRepository.getById(VianoConstants.STAGE_4_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_OPER_ISIRT_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Правовая оценка инцидента " + beautifyEventName(task) + " проведена. Возврат ГРИИБ для ознакомления."));
            case 5:
                task.setStage(stageRepository.getById(VianoConstants.STAGE_5_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_OPER_ISID_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Направление инцидента " + beautifyEventName(task) + " на анализ улучшений."));
            case 6:
                task.setStage(stageRepository.getById(VianoConstants.STAGE_6_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_OPER_ISIRT_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Анализ улучшений инцидента " + beautifyEventName(task) + " проведен. Возврат ГРИИБ для ознакомления."));
            case 7:
                task.setStage(stageRepository.getById(VianoConstants.STAGE_7_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_MNGR_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Направление инцидента " + beautifyEventName(task) + " менеджеру ИБ для ознакомления."));
            case 8:
                task.setStage(stageRepository.getById(VianoConstants.STAGE_8_ID));
                task.setPerformer(userRepository.findEmployerWithDep(VianoConstants.DEPARTMENT_ADM_ID));
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Инцидент: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto("Направление инцидента " + beautifyEventName(task) + " администратору ИБ для ознакомления."));
            case 9:
                String msg = "";
                if (task.getIsIncident() == null || !task.getIsIncident()) {
                    task.setIsIncident(false);
                    msg = "Событие " + beautifyEventName(task) + " помечено как 'Ложная тревога' и перемещено в архив.";
                } else {
                    msg = "Инцидент " + beautifyEventName(task) + " обработан и перемещен в архив.";
                }
                task.setStage(stageRepository.getById(VianoConstants.STAGE_9_ID));
                task.setPerformer(null);
                taskRepository.save(task);
                logService.log("Процессинг инцидента (Стадия: " + task.getStage().getName() + "; Объект: " + beautifyEventName(task) + ")");
                return ResponseEntity.ok().body(new MessageDto(msg));
            default:
                return ResponseEntity.badRequest().body("Ошибка процессинга инцидента, обратитесь к администратору");
        }
    }

    private String beautifyEventName(Task task) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String regNumberIf = "";
        if (task.getRegNumber() == null) {
            regNumberIf = "Событие №" + task.getId();
        } else {
            regNumberIf = "Инцидент №" + task.getRegNumber();
        }
        return regNumberIf + " " + formatter.format(task.getReportDate());
    }

    public ResponseEntity<?> findArchive() {
        return ResponseEntity.ok().body(taskRepository.findArchive());
    }

    public ResponseEntity<?> findEvents() {
        return ResponseEntity.ok().body(taskRepository.findEvents());
    }

    public ResponseEntity<?> findStageI() {
        return ResponseEntity.ok().body(taskRepository.findStageI());
    }

    public ResponseEntity<?> findStageII() {
        return ResponseEntity.ok().body(taskRepository.findStageII());
    }

    public ResponseEntity<?> findStageIII() {
        return ResponseEntity.ok().body(taskRepository.findStageIII());
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
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
        if (task.getIsEventEnd() == null) resultList.add("Закончилось ли событие?");
        if (task.getIsEventEnd() != null && task.getIsEventEnd()) {
            if (task.getEventDuration() == null) resultList.add("Длительность события");
        }
        return resultList;
    }
}
