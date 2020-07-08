package com.example.skul5.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecordId implements Serializable {

    @Column(name = "annio")
    @NotNull
    @Positive
    private Integer year;

    @Column(name = "semester")
    @NotNull
    @Min(value = 1)
    @Max(value = 2)
    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_course")
    private Course course;

    public RecordId() {
    }

    public RecordId(Integer year, Integer semester, Student student, Course course) {
        setYear(year);
        setSemester(semester);
        setStudent(student);
        setCourse(course);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordId recordId = (RecordId) o;
        return Objects.equals(year, recordId.year) &&
                Objects.equals(semester, recordId.semester) &&
                Objects.equals(student, recordId.student) &&
                Objects.equals(course, recordId.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, semester, student, course);
    }
}
