package com.kluevja.toddo.service;

import com.kluevja.toddo.entity.Log;
import com.kluevja.toddo.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> findByDay() {
        return logRepository.findByDay();
    }

    public void log(String message) {
        Log log = new Log();
        log.setDate(new Date());
        log.setMessage(message);
        logRepository.save(log);
    }
}
