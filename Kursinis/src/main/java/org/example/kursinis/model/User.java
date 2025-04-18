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
import java.util.List;

import static org.example.kursinis.model.HashPassword.passwordEncryption;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String name;
    protected String surname;
    protected String login;
    protected String password;
    protected LocalDate dateCreated;
    protected LocalDate dateModified;
    protected LocalDate birthDate;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Comment> comments;
    @Transient
    protected String noNeedToSave;

    public User(String name, String surname, String login, String password, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = passwordEncryption(password);
        this.birthDate = birthDate;
        this.dateCreated = LocalDate.now();
    }


}
