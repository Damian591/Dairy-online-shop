package org.example.kursinis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    protected String description;
    protected int qty;
    protected float weight;
    protected LocalDate productionDate;
    protected LocalDate bestBeforeTill;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Comment> comments;
    private LocalDate dateCreated;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Warehouse warehouse;


    public Product(String title, String description, int qty, float weight, LocalDate bestBeforeTill, LocalDate productionDate) {
        this.title = title;
        this.description = description;
        this.qty = qty;
        this.weight = weight;
        this.bestBeforeTill = bestBeforeTill;
        this.productionDate = productionDate;
        this.comments = new ArrayList<>();
        this.dateCreated = LocalDate.now();
    }
}
