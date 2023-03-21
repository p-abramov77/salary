package ru.mgimo.salary.entity;

import lombok.Data;
import org.hibernate.persister.entity.Loadable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name="absence")
public class AbsenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeId", nullable = false)
    private EmployeeEntity employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate finishDate;
    boolean paid;
    String comment;

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public String getComment() {
        return comment;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String toString() {
        return "absend "+ Long.toString(id) + " " + startDate + " " + finishDate;
    }
}
