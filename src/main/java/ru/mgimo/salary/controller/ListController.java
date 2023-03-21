package ru.mgimo.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mgimo.salary.entity.AbsenceEntity;
import ru.mgimo.salary.entity.AwardEntity;
import ru.mgimo.salary.entity.EmployeeEntity;
import ru.mgimo.salary.entity.SettingsEntity;
import ru.mgimo.salary.model.ListDAO;
import ru.mgimo.salary.service.AbsenceServiceImp;
import ru.mgimo.salary.service.AwardServiceImp;
import ru.mgimo.salary.service.EmployeeServiceImpl;
import ru.mgimo.salary.service.SettingsServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.objects.NativeMath.max;
import static jdk.nashorn.internal.objects.NativeMath.pow;

@Controller
@RequestMapping("/salary")
public class ListController {

    @Autowired
    private SettingsServiceImpl settingsService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private AbsenceServiceImp absenceService;
    @Autowired
    private AwardServiceImp awardService;

    @GetMapping("list")
    public String payList(Model model, @RequestParam(defaultValue = "") String date) {
        if (date.isEmpty()){
            date = LocalDate.now().toString();
        }
        model.addAttribute("date", date);

        SettingsEntity settings = settingsService.readSettings();

        LocalDate localDate = tryToConvert(date);
        String monthYear = localDate.getMonth().toString() + " " + localDate.getYear();
        model.addAttribute("monthYear", monthYear);

        List<ListDAO> list = new ArrayList<>();
        model.addAttribute("list", list);

        LocalDate start = localDate.minusDays(localDate.getDayOfMonth() - 1); // first day of the current month
        LocalDate finish = start.plusDays(start.getMonth().length(localDate.isLeapYear())-1);
        System.out.println("start = " + start + " finish = " + finish);

        int countOfWorkingDaysInMonth = countWorkingDays(start, finish);
        System.out.println("countOfWorkingDaysInMonth = " + countOfWorkingDaysInMonth);

        List<EmployeeEntity> employeeEntityList = employeeService.listEmployee("")
                .stream()
                .filter(x -> x.getHireDate().isBefore(finish.plusDays(1L)) && (x.getResignDate() == null || x.getResignDate().isAfter(start.minusDays(1L))) )
                .collect(Collectors.toList());
        System.out.println("-------------------------------- found = " + employeeEntityList.size());

        for ( EmployeeEntity employee : employeeEntityList ) {
            System.out.println("===================================================");
            System.out.println(employee);

            ListDAO row = new ListDAO();
            row.setFullName(employee.getFullName());
            row.setSalary(0f);
            list.add(row);

            final LocalDate empStart;
            if(start.isBefore(employee.getHireDate())) empStart = employee.getHireDate();
                    else empStart = start;
            final LocalDate empFinish;
            if(employee.getResignDate() == null) empFinish = finish;
            else
                if(finish.isAfter(employee.getResignDate())) empFinish = employee.getResignDate();
                    else empFinish =finish;
            System.out.println("empStart = " + empStart + " empFinish = " + empFinish);

            Map<LocalDate, Float> days = mapWorkingDays(empStart, empFinish);
            System.out.println("countOfWorkingDays = " + days.size());

            List<AbsenceEntity> absenceEntityList = employee.getAbsenceEntityList()
                    .stream()
                    .filter(x -> !(x.getStartDate().isAfter(empFinish) && x.getFinishDate().isBefore(empStart)) )
                    .collect(Collectors.toList());
            for(AbsenceEntity absence : absenceEntityList){
                System.out.println(absence);
                for (int add = 0; absence.getStartDate().plusDays(add).isBefore(absence.getFinishDate().plusDays(1L)); add++ ) {
                    if(days.containsKey(absence.getStartDate().plusDays(add))) {
                        if(absence.isPaid())
                            days.put(absence.getStartDate().plusDays(add), settings.getSickPercent());
                        else
                            days.put(absence.getStartDate().plusDays(add), 0.0f);
                    }
                }
            }
            int workingDays = 0;
            int absenceDays = 0;
            int sickDays = 0;
            float sumOfPercents = 0.0f;
            for(LocalDate d : days.keySet()) {
                float percent = days.get(d);
                System.out.println(d + " " + percent);
                if(percent < 100.0f) {
                    absenceDays++;
                    if (percent > 0.0f) {
                        sickDays++;
                    }
                } else {
                    workingDays++;
                }
                sumOfPercents += percent;
            }

            row.setWage(employee.getWage());
            row.setWorkingDays(workingDays);
            row.setAbsenceDays(absenceDays);
            row.setSickDays(sickDays);

            List<AwardEntity> awardEntityList = employee.getAwardEntityList()
                    .stream()
                    .filter(x -> x.getDate().isAfter(empStart.minusDays(1L)) || x.getDate().isBefore(empFinish.plusDays(1L)))
                    .collect(Collectors.toList());

            float awardSum = 0.0f;
            for(AwardEntity award : awardEntityList){
                System.out.println(award);
                awardSum += award.getAmount();
            }
            row.setAwardSum(awardSum);
            System.out.println( "ordinary = " + employee.getWage() * sumOfPercents / (countOfWorkingDaysInMonth * 100.0f));
            row.setSalary((employee.getWage() * sumOfPercents / (countOfWorkingDaysInMonth * 100.0f) + awardSum) * (100.0f - settings.getTaxRate()) / 100.0f);
        }
        return "list";
    }
    private LocalDate tryToConvert(String dateString) {
        LocalDate date;
        if (!dateString.equals("")) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale(Locale.US);
                date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                System.out.println(dateString + " exeption: " + e.getMessage());
                date = LocalDateTime.now().toLocalDate();
            }
        } else {
            date = LocalDateTime.now().toLocalDate();
        }
        return date;
    }
    private boolean isWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return !(dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY));
    }

    private Map<LocalDate, Float> mapWorkingDays(LocalDate start, LocalDate finish) {
        Map<LocalDate, Float> map = new HashMap<>();
        for (int add = 0; start.plusDays(add).isBefore(finish.plusDays(1L)); add++ ) {
            if (isWorkingDay(start.plusDays(add))) map.put(start.plusDays(add),100.0f);
        }
        return map;
    }
    private int countWorkingDays(LocalDate start, LocalDate finish) {
        int count = 0;
        for (int add = 0; start.plusDays(add).isBefore(finish.plusDays(1L)); add++ ) {
            if (isWorkingDay(start.plusDays(add))) count++;
        }
        return count;
    }
}
