package ru.mgimo.salary.service;

import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.AwardEntity;

import java.util.List;

@Service
public interface AwardService {
    public void save(AwardEntity awardEntity);
    public List<AwardEntity> listAwards(long employeeId);
    public AwardEntity getAward(long id);
    public void delete(long id);
}
