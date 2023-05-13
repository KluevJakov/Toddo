package com.kluevja.toddo.service;

import com.kluevja.toddo.config.VianoConstants;
import com.kluevja.toddo.config.jwt.JwtTokenUtil;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.entity.auth.JwtRequest;
import com.kluevja.toddo.entity.auth.JwtResponse;
import com.kluevja.toddo.entity.dto.MessageDto;
import com.kluevja.toddo.repository.DepartmentRepository;
import com.kluevja.toddo.repository.RoleRepository;
import com.kluevja.toddo.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenUtil jwtUtils;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository depsRepository;
    @Autowired
    private LogService logService;

    public ResponseEntity<?> login(JwtRequest user) {
        Optional<User> userAttempt = userRepository.findByEmail(user.getEmail());
        if (userAttempt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Ошибка! Пользователь не найден");
        } else if (!encoder.matches(user.getPassword(), userAttempt.get().getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ошибка! Неверный пароль");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        logService.log("Пользователь " + user.getEmail() + " вошел в систему");
        return ResponseEntity.ok(new JwtResponse(jwt, userAttempt.get().getEmail(), userAttempt.get().getDepartment(), userAttempt.get().getAuthorities(), userAttempt.get().getId()));
    }

    public ResponseEntity<?> createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email уже зарегистрирован. Пользователь не создан.");
        }

        String generatedPassword = RandomString.make(VianoConstants.USER_PASSWORD_GENERATION_LENGTH);
        //отправка пароля на почту пользователю
        user.setPassword(encoder.encode(generatedPassword));
        user.setActive(true);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(roleRepository.findById(VianoConstants.ROLE_USER_ID).get());
        user.setDepartment(depsRepository.findById(VianoConstants.DEPARTMENT_ORGANIZATION_ID).get());
        user.setRegDate(new Date());
        userRepository.save(user);
        logService.log("Создан аккаунт: " + user.getEmail());
        return ResponseEntity.ok(new MessageDto("Аккаунт для пользователя '" + user.getFio() + "' создан!\nПароль направлен на указанный email."));
    }

    public ResponseEntity<?> updateUser(User user) {
        if (user.getDepartment().getId().equals(VianoConstants.DEPARTMENT_HEAD_ID) &&
                isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_HEAD_ID).isPresent()) {
            User actualAdmin = isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_HEAD_ID).get();
            if (!user.getId().equals(actualAdmin.getId())) {
                return ResponseEntity.badRequest().body("Невозможно назначить руководителя 'Руководитель организации', " +
                        "сначала измените должность пользователя '" + actualAdmin.getFio() + "'");
            }
        } else if (user.getDepartment().getId().equals(VianoConstants.DEPARTMENT_ADM_ID) &&
                isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_ADM_ID).isPresent()) {
            User actualAdmin = isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_ADM_ID).get();
            if (!user.getId().equals(actualAdmin.getId())) {
                return ResponseEntity.badRequest().body("Невозможно назначить руководителя 'Администратора ИБ', " +
                        "сначала измените должность пользователя '" + actualAdmin.getFio() + "'");
            }
        } else if (user.getDepartment().getId().equals(VianoConstants.DEPARTMENT_MNGR_ID) &&
                isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_MNGR_ID).isPresent()) {
            User actualAdmin = isAdminDepartmentAssigned(VianoConstants.DEPARTMENT_MNGR_ID).get();
            if (!user.getId().equals(actualAdmin.getId())) {
                return ResponseEntity.badRequest().body("Невозможно назначить руководителя 'Менеджера ИБ', " +
                        "сначала измените должность пользователя '" + actualAdmin.getFio() + "'");
            }
        }
        //отправка изменений на почту пользователю
        userRepository.save(user);
        logService.log("Аккаунт пользователя " + user.getEmail() + " обновлен");
        return ResponseEntity.ok(new MessageDto("Аккаунт для пользователя '" + user.getFio() + "' обновлён!\nИзменения направлены на указанный email."));
    }

    public Optional<User> isAdminDepartmentAssigned(Long depId) {
        return userRepository.adminDepartmentAssigned(depId);
    }

    public ResponseEntity<?> importUsers(List<List<String>> userRecords) {
        int success = 0;
        int skip = 0;
        for(List<String> userRecord : userRecords) {
            if (userRecord.size() != VianoConstants.USER_RECORD_REQUIRED_FIELDS_COUNT) {
                skip++;
                continue;
            }
            User userFromRecord = new User();
            userFromRecord.setEmail(userRecord.get(0).trim());
            userFromRecord.setSurname(userRecord.get(1).trim());
            userFromRecord.setName(userRecord.get(2).trim());
            userFromRecord.setPatronymic(userRecord.get(3).trim());
            userFromRecord.setAddress(userRecord.get(4).trim());
            userFromRecord.setJobPosition(userRecord.get(5).trim());
            userFromRecord.setPhone(userRecord.get(6).trim());
            ResponseEntity<?> result = createUser(userFromRecord);

            if (result.getStatusCode().is2xxSuccessful()) {
                success++;
            } else {
                skip++;
            }
        }

        return ResponseEntity.ok(new MessageDto("Пакетная регистрация пользователей завершена! Загружено: "+success+"; Пропущено: "+skip+"."));
    }

    public List<User> getAll(Long depId, Long currentUserId) {
        return userRepository.getAll(depId, currentUserId);
    }
}
