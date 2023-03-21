package ru.mgimo.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.repository.EmployeeRepo;
import ru.mgimo.salary.repository.SettingsRepo;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void save(EmployeeEntity employeeEntity) {
        employeeRepo.save(employeeEntity);
    }

    @Override
    public List<EmployeeEntity> listEmployee(String name) {
        return employeeRepo.findAllByFullNameContainingOrderByFullName(name);
    }

    @Override
    public EmployeeEntity getEmployeeById(long id) {
        return employeeRepo.findById(id).orElse(null);
    }
}
