package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "public", name = "usuario")
public class User implements Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    @NotEmpty
    @Size(message = "EL nombre de usuario es de maximo 32 caracteres", max = 32)
    private String userName;

    @Column(name = "passwd")
    @NotEmpty
    @Size(message = "EL nombre de usuario es de maximo 256 caracteres y minimo 8", max = 256, min = 8)
    private String password;

    @Column(name = "name")
    @Size(message = "EL nombre es de maximo 32 caracteres", max = 32)
    private String name;

    @Column(name = "last_name")
    @Size(message = "EL apellido es de maximo 32 caracteres", max = 32)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @NotNull(message = "Es necesario asignar un rol")
    private Role role;

    @Column(name = "sesion")
    private Boolean isSessionOpen = false;

    @Column(name = "active")
    private Boolean active = false;

    public User() {
    }

    public User(Role role) {
        setRole(role);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

}
