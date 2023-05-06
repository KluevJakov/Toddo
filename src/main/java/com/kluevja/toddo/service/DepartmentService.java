package com.kluevja.toddo.service;

import com.kluevja.toddo.config.VianoConstants;
import com.kluevja.toddo.entity.Department;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.repository.DepartmentRepository;
import com.kluevja.toddo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Department> findAllExcludeHead() {
        return departmentRepository.findAllExcludeHead();
    }

    public List<String> getDepInfo() {
        List<String> depInfo = new ArrayList<>();
        AtomicInteger regulator = new AtomicInteger();
        AtomicInteger isirt = new AtomicInteger();
        AtomicInteger isid = new AtomicInteger();
        AtomicInteger audit = new AtomicInteger();
        AtomicInteger org = new AtomicInteger();
        AtomicReference<User> head = new AtomicReference<>();
        AtomicReference<User> adm = new AtomicReference<>();
        AtomicReference<User> mngr = new AtomicReference<>();
        userRepository.findAll()
                .forEach(e -> {
                    if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_HEAD_ID)) {
                        head.set(e);
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_ADM_ID)) {
                        adm.set(e);
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_MNGR_ID)) {
                        mngr.set(e);
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_OPER_REGULATOR_ID)) {
                        regulator.getAndIncrement();
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_OPER_ISIRT_ID)) {
                        isirt.getAndIncrement();
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_OPER_ISID_ID)) {
                        isid.getAndIncrement();
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_OPER_AUDIT_ID)) {
                        audit.getAndIncrement();
                    } else if (e.getDepartment().getId().equals(VianoConstants.DEPARTMENT_ORGANIZATION_ID)) {
                        org.getAndIncrement();
                    }
                });

        if (head.get() == null) {
            depInfo.add("Не назначен");
        } else {
            depInfo.add(head.get().getSurname() + " " + head.get().getName() + " " + head.get().getPatronymic());
        }
        if (adm.get() == null) {
            depInfo.add("Не назначен");
        } else {
            depInfo.add(adm.get().getSurname() + " " + adm.get().getName() + " " + adm.get().getPatronymic());
        }
        if (mngr.get() == null) {
            depInfo.add("Не назначен");
        } else {
            depInfo.add(mngr.get().getSurname() + " " + mngr.get().getName() + " " + mngr.get().getPatronymic());
        }
        depInfo.add(String.valueOf(regulator));
        depInfo.add(String.valueOf(isirt));
        depInfo.add(String.valueOf(audit));
        depInfo.add(String.valueOf(isid));
        depInfo.add(String.valueOf(org));
        return depInfo;
    }
}
