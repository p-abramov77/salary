package ru.mgimo.salary.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="settings")
public class SettingsEntity {
    @Id
    private Long id = 1L;


    private float taxRate;
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
