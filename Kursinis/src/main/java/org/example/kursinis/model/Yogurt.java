package org.example.kursinis.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Yogurt extends Product {
    private String manufacturer;
    private String flavour;

    public Yogurt(String title, String description, int qty, float weight, String manufacturer, String flavour, LocalDate bestBeforeTill, LocalDate productionDate ) {
        super(title, description, qty, weight, bestBeforeTill, productionDate);
        this.manufacturer = manufacturer;
        this.flavour = flavour;
    }
    @Override
    public String toString() {
        return title + ":" + description;
    }
}
