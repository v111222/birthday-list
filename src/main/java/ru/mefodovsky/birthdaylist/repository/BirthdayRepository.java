package ru.mefodovsky.birthdaylist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mefodovsky.birthdaylist.entity.Birthday;

@Repository
public interface BirthdayRepository extends JpaRepository<Birthday, Long> {
}
