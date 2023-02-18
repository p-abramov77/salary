package ru.mgimo.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mgimo.salary.entity.SettingsEntity;

@Repository
public interface SettingsRepo extends JpaRepository<SettingsEntity, Long> {

}
