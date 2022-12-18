package ru.mefodovsky.birthdaylist.service;

import ru.mefodovsky.birthdaylist.entity.User;

public interface LogService {
    void addEntry(User user, String description);
}
