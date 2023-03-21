package ru.mgimo.salary.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<AbsenceEntity> absenceEntityList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<AwardEntity> awardEntityList;
    @NotEmpty(message = "Не может быть пустым")
    @Size(min = 8, max = 100, message = "Длина строки должна быть от 8 до 100 символов")
    String fullName;
    @NotEmpty(message = "Не может быть пустым")
    @Size(min = 5, max = 50, message = "Длина строки должна быть от 5 до 50 символов")
    String position;
    @Min(value = 1, message = "Не меньше, чем 1")
    float wage;
    @NotEmpty(message = "Не может быть пустым")
    @Size(min = 4, max = 10, message = "Длина строки должна быть от 4 до 10 символов")
    String maritalStatus;
    @Min(value = 0, message = "Больше или равен 0")
    int childAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate resignDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getChildAmount() {
        return childAmount;
    }

    public void setChildAmount(int childAmount) {
        this.childAmount = childAmount;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getResignDate() {
        return resignDate;
    }

    public void setResignDate(String date) {
        this.resignDate = Date.valueOf(date).toLocalDate();
    }

    public void setHireDate(String date) {
        this.hireDate = Date.valueOf(date).toLocalDate();
    }

    public List<AbsenceEntity> getAbsenceEntityList() {
        return absenceEntityList;
    }

    public List<AwardEntity> getAwardEntityList() {
        return awardEntityList;
    }

    public String toString() {
        return Long.toString(id) + " " + fullName + " " + position;
    }
}
