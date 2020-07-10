package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "record")
public class Record {

    @Id
    private RecordId primaryKey;

    @Column(name = "grade")
    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private float grade;

    public Record() {
    }

    public RecordId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(RecordId id) {
        this.primaryKey = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
