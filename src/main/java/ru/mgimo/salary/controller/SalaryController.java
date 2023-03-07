package ru.mgimo.salary.controller;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.service.AbsenceServiceImp;
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

    @Autowired
    private AbsenceServiceImp absenceService;
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
        model.addAttribute("employee", employeeEntity);
        return "employee";
    }
    @GetMapping("resignEmployee/{id}")
    public String resignEmployee(Model model, @PathVariable(value = "id") long id) {
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employeeEntity);
        return "resign";
    }
    @PostMapping("saveResignEmployee")
    public String saveResignEmployee(@ModelAttribute("employee") @Valid EmployeeEntity employeeEntity,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "resign";
        }
        employeeService.save(employeeEntity);

        return "redirect:/salary/employees";
    }
    @GetMapping("absenceDays/{id}")
    public String absenceDays(Model model, @PathVariable(value = "id") long employeeId) {
        List<AbsenceEntity> absenceEntityList = absenceService.listAbsence(employeeId);
        String fullName = employeeService.getEmployeeById(employeeId).getFullName();
        model.addAttribute("absences", absenceEntityList);
        model.addAttribute("name", fullName);
        model.addAttribute("id", employeeId);
        return "absences";
    }
    @GetMapping("newAbsence/{id}")
    public String newAbsence(Model model, @PathVariable(value = "id") long employeeId) {
        AbsenceEntity absenceEntity = new AbsenceEntity();
        absenceEntity.setEmployeeId(employeeId);
        String fullName = employeeService.getEmployeeById(employeeId).getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("absence", absenceEntity);
        return "absence";
    }
    @PostMapping("saveAbsence")
    public String saveAbsence(@ModelAttribute("absence") @Valid AbsenceEntity absenceEntity,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "absence";
        }
        absenceService.save(absenceEntity);
        // Показать пропущенные дни этого работниа
        return "redirect:/salary/absenceDays/" + absenceEntity.getEmployeeId() ;
    }
    @GetMapping("editAbsence/{id}")
    public String editAbsence(Model model, @PathVariable(value = "id") long id) {
        AbsenceEntity absenceEntity = absenceService.getAbsence(id);
        if (absenceEntity == null)
            return "absences";
        String fullName = employeeService.getEmployeeById(absenceEntity.getEmployeeId()).getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("absence", absenceEntity);
        return "absence";
    }

        @GetMapping("deleteAbsence/{id}")
    public String deleteAbsence(Model model, @PathVariable(value = "id") long id) {
        AbsenceEntity absenceEntity = absenceService.getAbsence(id);
        absenceService.delete(id);

        return "redirect:/salary/absenceDays/" + absenceEntity.getEmployeeId();
     }
}
