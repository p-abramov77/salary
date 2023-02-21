package ru.mgimo.salary.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Entity
@Table(name="employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size()
    String tableNumber;
    String fullName;
    String position;
    float wage;
    String maritalStatus;
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
