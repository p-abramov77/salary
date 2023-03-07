package ru.mgimo.salary.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Entity
@Table(name="employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "Не может быть пустым")
    @Size(min = 3, max = 10, message = "Длина строки должна быть от 3 до 10 символов")
    String tableNumber;
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
    Date hireDate;
    Date resignDate;

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

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

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    public void setHireDate(String date) {
        this.hireDate = Date.valueOf(date);
    }

    public String toString() {
        return Long.toString(id) + " " + fullName + " " + position;
    }
}
