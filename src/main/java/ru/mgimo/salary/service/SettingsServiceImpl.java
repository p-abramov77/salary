package ru.mgimo.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.repository.SettingsRepo;

@Service
public class SettingsServiceImpl implements SettingsService{

    @Autowired
    private SettingsRepo settingsRepo;
    @Override
    public SettingsEntity readSettings() {
        return settingsRepo.findById(1L).orElse(null);
    }

    @Override
    public void saveSettings(SettingsEntity settingsEntity) {
        settingsRepo.save(settingsEntity);
    }
}
