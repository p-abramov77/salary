package ru.mgimo.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.AwardEntity;
import ru.mgimo.salary.repository.AwardRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AwardServiceImp implements AwardService {
    @Autowired
    private AwardRepo awardRepo;
    @Override
    public void save(AwardEntity awardEntity) {
        awardRepo.save(awardEntity);
    }

    @Override
    public List<AwardEntity> listAwards(long employeeId) {
        return awardRepo.findAllByEmployeeIdOrderByDate(employeeId);
    }

    @Override
    public AwardEntity getAward(long id) {
        Optional<AwardEntity> optional = awardRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        awardRepo.deleteById(id);
    }
}
