package ru.mgimo.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.repository.SettingsRepo;

import java.util.Optional;

@Service
public class SettingsServiceImpl implements SettingsService{

    @Autowired
    private SettingsRepo settingsRepo;
    @Override
    public SettingsEntity readSettings() {
        Optional< SettingsEntity > optional = settingsRepo.findById(1L);
        return optional.orElse(null);
    }

    @Override
    public void saveSettings(SettingsEntity settingsEntity) {
        settingsRepo.save(settingsEntity);
    }
}
