package com.example.skul5.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    private String password;

    @Column(name = "name")
    @NotEmpty
    @Size(message = "EL nombre es de maximo 32 caracteres", max = 32)
    private String name;

    @Column(name = "last_name")
    @NotEmpty
    @Size(message = "EL apellido es de maximo 32 caracteres", max = 32)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @NotNull(message = "Es necesario asignar un rol")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipality")
    private Municipality municipality;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthDate;

    @Column(name = "address")
    @NotEmpty
    @Size(max = 256, message = "La direccion adminte hasta 256 caracteres")
    private String address;

    @Column(name = "sesion")
    private Boolean isSessionOpen = false;

    @Column(name = "active")
    private Boolean active = false;

    public User() {
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

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
