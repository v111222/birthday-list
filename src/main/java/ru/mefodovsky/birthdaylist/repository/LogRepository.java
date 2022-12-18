package ru.mefodovsky.birthdaylist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mefodovsky.birthdaylist.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
