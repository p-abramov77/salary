package ru.mgimo.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.repository.AbsenceRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceServiceImp implements AbsenceService {
    @Autowired
    private AbsenceRepo absenceRepo;
    @Override
    public void save(AbsenceEntity absenceEntity) {
        absenceRepo.save(absenceEntity);
    }

    @Override
    public List<AbsenceEntity> listAbsence(long employeeId) {
        return absenceRepo.findAllByEmployeeIdOrderByStartDate(employeeId);
    }

    @Override
    public AbsenceEntity getAbsence(long id) {
        return absenceRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        absenceRepo.deleteById(id);
    }

}
