package ru.mgimo.salary.model;

import org.springframework.stereotype.Component;

@Component
public class ListDAO {
    String fullName;
    float wage;
    int workingDays;
    int absenceDays;
    int sickDays;
    float awardSum;
    float salary;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public int getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(int absenceDays) {
        this.absenceDays = absenceDays;
    }

    public int getSickDays() {
        return sickDays;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }

    public float getAwardSum() {
        return awardSum;
    }

    public void setAwardSum(float awardSum) {
        this.awardSum = awardSum;
    }
}
