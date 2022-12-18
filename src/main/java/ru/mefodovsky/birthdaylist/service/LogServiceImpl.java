package ru.mefodovsky.birthdaylist.service;

import org.springframework.stereotype.Service;
import ru.mefodovsky.birthdaylist.entity.Log;
import ru.mefodovsky.birthdaylist.entity.User;
import ru.mefodovsky.birthdaylist.repository.LogRepository;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void addEntry(User user, String description) {
        Log log = new Log();
        log.setUser(user);
        log.setActionDate(new Date());
        log.setActionDescription(description);
        logRepository.save(log);
    }
}
