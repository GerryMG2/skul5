package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "public", name = "usuario")
public class User implements Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(message = "EL nombre es de maximo 32 caracteres", max = 32)
    private String name;

    @Column(name = "lastname")
    @Size(message = "EL apellido es de maximo 32 caracteres", max = 32)
    private String lastName;

    @Column(name = "role_id")
    @NotNull
    private Integer roleId;

    @Column(name = "sesion")
    private Boolean isSessionOpen = false;

    @Column(name = "activo")
    private Boolean active = false;

    @Column(name = "passwd")
    private String password;

    public User() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getSessionOpen() {
        return isSessionOpen;
    }

    public void setSessionOpen(Boolean sessionOpen) {
        isSessionOpen = sessionOpen;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
