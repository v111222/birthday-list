package ru.mefodovsky.birthdaylist.service;

import ru.mefodovsky.birthdaylist.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

}
