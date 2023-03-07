package ru.mgimo.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.EmployeeEntity;

import java.util.List;

@Repository
public interface AbsenceRepo extends JpaRepository<AbsenceEntity, Long> {
    List<AbsenceEntity> findAllByEmployeeIdOrderByStartDate(long employeeId);

}
