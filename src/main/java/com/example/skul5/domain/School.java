package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "public", name = "school")
public class School implements Model{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(message = "El nombre debe tener como maximo 256 caracteres", max = 256)
    private String name;

    public School() { }

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
}
