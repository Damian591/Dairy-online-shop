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
public class Milk extends Product {
    private String type;
    private String manufakturer;

    public Milk(String title, String description, int qty, float weight, String type, String manufakturer, LocalDate bestBeforeTill, LocalDate productionDate) {
        super(title, description, qty, weight, bestBeforeTill, productionDate);
        this.type = type;
        this.manufakturer = manufakturer;
    }
    @Override
    public String toString() {
        return title + ":" + description;
    }
}
