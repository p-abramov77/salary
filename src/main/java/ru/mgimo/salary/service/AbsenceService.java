package ru.mgimo.salary.service;

import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.AbsenceEntity;

import java.util.List;

@Service
public interface AbsenceService {
    public void save(AbsenceEntity absenceEntity);
    public List<AbsenceEntity> listAbsence(long employeeId);
    public AbsenceEntity getAbsence(long id);
    public void delete(long id);
}
