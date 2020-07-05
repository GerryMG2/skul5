package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "public", name = "department")
public class Department implements Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(message = "EL nombre es de maximo 256 caracteres", max = 256)
    private String name;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Municipality> municipalities = new HashSet<>();

    public Department() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(Set<Municipality> municipalities) {
        this.municipalities = municipalities;
    }
}
