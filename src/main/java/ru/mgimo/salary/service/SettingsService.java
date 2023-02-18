package ru.mgimo.salary.service;

import ru.mgimo.salary.entity.SettingsEntity;

public interface SettingsService {
    SettingsEntity readSettings();
    void saveSettings(SettingsEntity settingsEntity);
}
