package ru.mgimo.salary.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name="absence")
public class AbsenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeId", nullable = false)
    private EmployeeEntity employee;
    @Schema(description = "Начало периода отсутствия")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Конец периода отсутствия")
    LocalDate finishDate;
    @Schema(description = "Является ли период оплачиваемым")
    boolean paid;
    @Schema(description = "Комментарий")
    String comment;
//
//    public EmployeeEntity getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(EmployeeEntity employee) {
//        this.employee = employee;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public LocalDate getFinishDate() {
//        return finishDate;
//    }
//
//    public boolean isPaid() {
//        return paid;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public void setFinishDate(LocalDate finishDate) {
//        this.finishDate = finishDate;
//    }
//
//    public void setPaid(boolean paid) {
//        this.paid = paid;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
    public String toString() {
        return "absend "+ Long.toString(id) + " " + startDate + " " + finishDate;
    }
}
