package ru.job4j.urlshortcut.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registrations")
@Data
@NoArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String site;
    private String login;
    private String password;

    public Registration(String site, String login, String password) {
        this.site = site;
        this.login = login;
        this.password = password;
    }
}