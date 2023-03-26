package ru.mgimo.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.AwardEntity;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.service.AbsenceServiceImp;
import ru.mgimo.salary.service.AwardServiceImp;
import ru.mgimo.salary.service.EmployeeServiceImpl;
import ru.mgimo.salary.service.SettingsServiceImpl;
import ru.mgimo.salary.validation.DateValidation;

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
    @Autowired
    private AwardServiceImp awardService;
    @Autowired
    DateValidation dateValidation;
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
        employeeEntity.setHireDate(Date.valueOf(LocalDate.now()).toLocalDate());
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
        model.addAttribute("name", employeeEntity.getFullName());
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
        absenceEntity.setEmployee(employeeService.getEmployeeById(employeeId));
        String fullName = absenceEntity.getEmployee().getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("absence", absenceEntity);
        return "absence";
    }
    @PostMapping("saveAbsence")
    public String saveAbsence(Model model, @ModelAttribute("absence") @Valid AbsenceEntity absenceEntity,
                                     BindingResult bindingResult) {
        if (! dateValidation.isPeriodCrossedWithEachOther(absenceEntity).isEmpty()) {
            model.addAttribute("errorMessage", dateValidation.isPeriodCrossedWithEachOther(absenceEntity));
            model.addAttribute("name", absenceEntity.getEmployee().getFullName());
            return "absence";
        }
        if (!dateValidation.isPeriod(absenceEntity)) {
            model.addAttribute("errorMessage", "Начало периода превышает конец периода");
            model.addAttribute("name", absenceEntity.getEmployee().getFullName());
            return "absence";
        }

        if(bindingResult.hasErrors()) {
            return "absence";
        }
        absenceService.save(absenceEntity);
        // Показать пропущенные дни этого работниа
        return "redirect:/salary/absenceDays/" + absenceEntity.getEmployee().getId() ;
    }
    @GetMapping("editAbsence/{id}")
    public String editAbsence(Model model, @PathVariable(value = "id") long id) {
        AbsenceEntity absenceEntity = absenceService.getAbsence(id);
        if (absenceEntity == null)
            return "absences";
        String fullName = employeeService.getEmployeeById(absenceEntity.getEmployee().getId()).getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("absence", absenceEntity);
        return "absence";
    }

    @GetMapping("deleteAbsence/{id}")
    public String deleteAbsence(Model model, @PathVariable(value = "id") long id) {
        AbsenceEntity absenceEntity = absenceService.getAbsence(id);
        absenceService.delete(id);

        return "redirect:/salary/absenceDays/" + absenceEntity.getEmployee().getId();
     }

    @GetMapping("awards/{id}")
    public String awards(Model model, @PathVariable(value = "id") long employeeId) {
        List<AwardEntity> awardEntityList = awardService.listAwards(employeeId);
        String fullName = employeeService.getEmployeeById(employeeId).getFullName();
        model.addAttribute("awards", awardEntityList);
        model.addAttribute("name", fullName);
        model.addAttribute("id", employeeId);
        return "awards";
    }
    @GetMapping("newAward/{id}")
    public String newAward(Model model, @PathVariable(value = "id") long employeeId) {
        AwardEntity awardEntity = new AwardEntity();
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(employeeId);
        awardEntity.setEmployeeEntity(employeeEntity);
        String fullName = awardEntity.getEmployee().getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("award", awardEntity);
        return "award";
    }
    @PostMapping("saveAward")
    public String saveAward(@ModelAttribute("award") @Valid AwardEntity awardEntity,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "award";
        }
        awardService.save(awardEntity);
        return "redirect:/salary/awards/" + awardEntity.getEmployeeEntity().getId() ;
    }
    @GetMapping("editAward/{id}")
    public String editAward(Model model, @PathVariable(value = "id") long id) {
        AwardEntity awardEntity = awardService.getAward(id);
        if (awardEntity == null)
            return "awards";
        String fullName = awardEntity.getEmployeeEntity().getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("award", awardEntity);
        return "award";
    }

    @GetMapping("deleteAward/{id}")
    public String deleteAward(Model model, @PathVariable(value = "id") long id) {
        AwardEntity awardEntity = awardService.getAward(id);
        awardService.delete(id);

        return "redirect:/salary/awards/" + awardEntity.getEmployeeEntity().getId();
    }


}
