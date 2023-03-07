package ru.mgimo.salary.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="absence")
public class AbsenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    long employeeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date finishDate;
    boolean paid;
    String comment;

    public long getEmployeeId() {
        return employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public String getComment() {
        return comment;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
