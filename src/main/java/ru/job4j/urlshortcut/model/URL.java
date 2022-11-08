package ru.job4j.urlshortcut.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "url")
@Data
@NoArgsConstructor
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String url;
    private String key;
    @Column(name = "calls_number",
            nullable = false,
            columnDefinition = "int default 0")
    private int callsNumber;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Registration owner;
    public URL(String url, String key, Registration owner) {
        this.url = url;
        this.key = key;
        this.owner = owner;
    }
}