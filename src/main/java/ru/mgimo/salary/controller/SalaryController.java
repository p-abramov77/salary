package ru.mgimo.salary.controller;

import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.service.SettingsServiceImpl;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
@RequestMapping ("/salary")
public class SalaryController {
    static {
        SpringDocUtils.getConfig().addRestControllers(SalaryController.class);
    }
    @Autowired
    private SettingsServiceImpl settingsService;

    @PostConstruct
    public void postConstruct() {
        SettingsEntity settingsEntity = settingsService.readSettings();
        if(settingsEntity == null) {
            // if settings dos not exists make default settings
            settingsEntity = new SettingsEntity(1L,13,50);
            settingsService.saveSettings(settingsEntity);
        }
    }
    @GetMapping ("main")
    public String mainPage() {
        return "main";
    }

    @GetMapping ("settings")
    public String settings(Model model) {
        SettingsEntity settingsEntity = settingsService.readSettings();
        model.addAttribute("settings",settingsEntity);
        return "settings";
    }
    @PostMapping("saveSettings")
    public String saveSettings(@ModelAttribute("settings") @ Valid SettingsEntity settingsEntity,
                               BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "settings";
        }
        settingsService.saveSettings(settingsEntity);

        return "redirect:/salary/main";
    }
}
