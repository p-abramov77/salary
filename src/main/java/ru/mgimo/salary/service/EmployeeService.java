package ru.mgimo.salary.service;

import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.EmployeeEntity;

import java.util.List;

@Service
public interface EmployeeService {
    public void save(EmployeeEntity employeeEntity);
    public List<EmployeeEntity> listEmployee(String name);
    public EmployeeEntity getEmployeeById(long id);
}
