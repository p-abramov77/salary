package ru.mgimo.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mgimo.salary.entity.EmployeeEntity;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByFullNameContaining(String name);
}
