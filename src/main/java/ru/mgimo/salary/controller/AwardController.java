package ru.mgimo.salary.controller;

import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mgimo.salary.entity.AwardEntity;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.service.AwardServiceImp;
import ru.mgimo.salary.service.EmployeeServiceImpl;

import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/salary")
public class AwardController {
    static {
        SpringDocUtils.getConfig().addRestControllers(AwardController.class);
    }
    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    AwardServiceImp awardService;

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
