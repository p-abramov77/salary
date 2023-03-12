package ru.mgimo.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.AwardEntity;

import java.util.List;

@Repository
public interface AwardRepo extends JpaRepository<AwardEntity, Long> {
    List<AwardEntity> findByEmployeeIdOrderByDate(long employeeId);

}
