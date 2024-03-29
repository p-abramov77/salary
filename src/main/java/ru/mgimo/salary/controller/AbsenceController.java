package ru.mgimo.salary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.service.AbsenceServiceImp;
import ru.mgimo.salary.service.EmployeeServiceImpl;
import ru.mgimo.salary.validation.DateValidation;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "Контроллер для работы с отсутствием сотрудников")
@Controller
@RequestMapping("/salary")
public class AbsenceController {
    static {
        SpringDocUtils.getConfig().addRestControllers(AbsenceController.class);
    }
    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    AbsenceServiceImp absenceService;
    @Autowired
    DateValidation dateValidation;
    @Operation(summary = "Список периодов отсутствия сотрудника", description = "Выдается список периодов отсутствия сотрудника с номером id")
    @GetMapping("absenceDays/{id}")
    public String absenceDays(Model model, @PathVariable(value = "id") long employeeId) {
        List<AbsenceEntity> absenceEntityList = absenceService.listAbsence(employeeId);
        String fullName = employeeService.getEmployeeById(employeeId).getFullName();
        model.addAttribute("absences", absenceEntityList);
        model.addAttribute("name", fullName);
        model.addAttribute("id", employeeId);
        return "absences";
    }
    @Operation(summary = "Подготовка данных для формы об отсутствии сотрудника", description = "Подготовка данных для формы об отсутствии сотрудника")
    @GetMapping("newAbsence/{id}")
    public String newAbsence(Model model, @PathVariable(value = "id") long employeeId) {
        AbsenceEntity absenceEntity = new AbsenceEntity();
        absenceEntity.setEmployee(employeeService.getEmployeeById(employeeId));
        String fullName = absenceEntity.getEmployee().getFullName();
        model.addAttribute("name", fullName);
        model.addAttribute("absence", absenceEntity);
        return "absence";
    }
    @Operation(summary = "Обработка запроса на сохранение данных об отсутствии сотрудника", description = "Обработка запроса на сохранение данных об отсутствии сотрудник")
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
    @Operation(summary = "Обработка запроса на изменение данных об отсутствии сотрудника", description = "Обработка запроса на изменение данных об отсутствии сотрудник")
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

    @Operation(summary = "Обработка запроса на удаление данных об отсутствии сотрудника", description = "Обработка запроса на удаление данных об отсутствии сотрудник")
    @GetMapping("deleteAbsence/{id}")
    public String deleteAbsence(Model model, @PathVariable(value = "id") long id) {
        AbsenceEntity absenceEntity = absenceService.getAbsence(id);
        absenceService.delete(id);

        return "redirect:/salary/absenceDays/" + absenceEntity.getEmployee().getId();
    }

}
