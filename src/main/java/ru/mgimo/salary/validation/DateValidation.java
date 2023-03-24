package ru.mgimo.salary.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.service.AbsenceServiceImp;

import java.util.List;

@Component
public class DateValidation {
    @Autowired
    AbsenceServiceImp absenceService;
    public boolean isPeriodCrossedWithEachOther(AbsenceEntity absenceNew) {
        List<AbsenceEntity> absenceEntityList = absenceService.listAbsence(absenceNew.getEmployee().getId());
        for(AbsenceEntity absence : absenceEntityList) {
            if(absenceNew.getId() != null
                    &&
               absenceNew.getId().equals(absence.getId())) continue;
            if( ! ( absenceNew.getStartDate().isAfter(absence.getFinishDate())
                    ||
                    absenceNew.getFinishDate().isBefore(absence.getStartDate())))
                return true;
        }
        return false;
    }
    public boolean isPeriod(AbsenceEntity absenceNew) {
        return absenceNew.getStartDate().isBefore(absenceNew.getFinishDate().plusDays(1L));
    }

}
