package ru.mgimo.salary.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name="settings")
public class SettingsEntity {
    @Id
    private Long id = 1L;

    @Min(value = 0, message = "Должен быть больше или равен 0")
    @Max(value = 100, message = "Должен быть меньше или равен 100")
    private float taxRate;
    @Min(value = 0, message = "Должен быть больше или равен 0")
    @Max(value = 100, message = "Должен быть меньше или равен 100")
    private float sickPercent;

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public float getSickPercent() {
        return sickPercent;
    }

    public void setSickPercent(float sickPercent) {
        this.sickPercent = sickPercent;
    }

    public SettingsEntity() {}
    public SettingsEntity(Long id, float taxRate, float sickPercent) {
        this.id = id;
        this.taxRate = taxRate;
        this.sickPercent = sickPercent;
    }
    public String toString() {
        return Long.toString(id) + " " + Float.toString(taxRate) + " " + Float.toString(sickPercent);
    }
}
