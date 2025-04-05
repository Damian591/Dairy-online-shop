package org.example.kursinis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public final class Manager extends User {
    private boolean isAdmin;
    private String uniqueID;
    private String medCert;
    private LocalDate hireDate;
    @ManyToOne
    private Warehouse warehouse;
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> myManagedCarts;

    public Manager(String name, String surname, String login, String password, LocalDate birthDay, boolean isAdmin, String uniqueID, String medCert, LocalDate hireDate) {
        super(name, surname, login, password, birthDay);
        this.isAdmin = isAdmin;
        this.uniqueID = uniqueID;
        this.medCert = medCert;
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "isAdmin=" + isAdmin +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
