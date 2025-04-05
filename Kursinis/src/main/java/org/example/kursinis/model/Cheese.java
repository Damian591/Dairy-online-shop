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
public final class Cheese extends Product {

    private LocalDate productionDate;
    private LocalDate packingDate;
    private LocalDate bestBeforeTill;

    public Cheese(String title, String description, int qty, float weight, LocalDate packingDate, LocalDate productionDate, LocalDate bestBeforeTill) {
        super(title, description, qty, weight, bestBeforeTill, productionDate);
        this.packingDate = packingDate;
        this.productionDate = productionDate;
        this.bestBeforeTill = bestBeforeTill;
    }

    @Override
    public String toString() {
        return title + ":" + description;
    }
}
