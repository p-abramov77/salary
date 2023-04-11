package ru.mgimo.salary.controller;

import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.service.EmployeeServiceImpl;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/salary")
public class EmployeeController {
    static {
        SpringDocUtils.getConfig().addRestControllers(EmployeeController.class);
    }
    @Autowired
    EmployeeServiceImpl employeeService;

    @GetMapping ("employees")
    public String listEmployees(Model model, @RequestParam(defaultValue = "") String name) {
        List<EmployeeEntity> employees = employeeService.listEmployee(name);
        model.addAttribute("name", name);
        model.addAttribute("employees",employees);
        return "employees";
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
}
