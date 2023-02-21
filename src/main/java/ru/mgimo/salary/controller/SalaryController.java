package ru.mgimo.salary.controller;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.service.EmployeeServiceImpl;
import ru.mgimo.salary.service.SettingsServiceImpl;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping ("/salary")
public class SalaryController {
    @Autowired
    private SettingsServiceImpl settingsService;

    @Autowired
    private EmployeeServiceImpl employeeService;

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
    @GetMapping ("list")
    public String payList() {
        return "list";
    }
    @GetMapping ("employees")
    public String listEmployees(Model model, @RequestParam(defaultValue = "") String name) {
        List<EmployeeEntity> employees = employeeService.listEmployee(name);
        model.addAttribute("name", name);
        model.addAttribute("employees",employees);
        return "employees";
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
        System.out.println(settingsEntity);
        settingsService.saveSettings(settingsEntity);

        return "redirect:/salary/main";
    }
    @GetMapping("newEmployee")
    public String newEmployee(Model model) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setHireDate(Date.valueOf(LocalDate.now()));
        model.addAttribute("employee", employeeEntity);
        return "employee";
    }
    @PostMapping("saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") @Valid EmployeeEntity employeeEntity,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "employee";
        }
        employeeService.save(employeeEntity);

        return "redirect:/salary/employees?name="; //+ StringEscapeUtils.escapeJava(employeeEntity.getFullName());

    }
    @GetMapping("editEmployee/{id}")
    public String editEmployee(Model model, @PathVariable(value = "id") long id) {
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(id);
        System.out.println("read : " + employeeEntity);
        model.addAttribute("employee", employeeEntity);
        return "employee";
    }

}
