package com.example.skul5.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "public", name = "student")
public class Student implements Model {

    public Student() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(message = "El nombre debe tener maximo 80 caracteres", max = 80)
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "El apellido es obligatorio")
    @Size(message = "El apellido debe tener maximo 80 caracteres", max = 80)
    private String lastName;

    @Column(name = "license")
    @NotEmpty(message = "El carnet es obligatorio")
    @Size(message = "El carnet debe tener exactamente 9 caracteres", min = 9, max = 9)
    private String license;

    @Column(name = "address")
    @NotEmpty(message = "La direccion es obligatoria")
    @Size(message = "La direccion maximo 256 caracteres", max = 256)
    private String address;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthDate;

    @Column(name = "telephone")
    @Size(message = "El telefono debe tener minimo 8 caracteres y maximo 32", min = 8, max = 32)
    private String telephone;

    @Column(name = "cellphone")
    @Size(message = "El celular debe tener minimo 8 caracteres y maximo 32", min = 8, max = 32)
    private String Cellphone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_school")
    private School school;

    @Column(name = "father_name")
    @Size(message = "El nombre del padre debe tener como maximo 32 caracteres", max = 32)
    private String fatherName;

    @Column(name = "mother_name")
    @Size(message = "El nombre de la madre debe tener como maximo 32 caracteres", max = 32)
    private String motherName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Set<Record> records = new HashSet<>();

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer code) {
        this.id = code;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return Cellphone;
    }

    public void setCellphone(String cellphone) {
        Cellphone = cellphone;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}