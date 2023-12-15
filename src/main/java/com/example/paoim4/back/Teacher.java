package com.example.paoim4.back;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private TeacherStatus status;
    private int yearOfBirth;
    private double salary;
    @ManyToOne
    @JoinColumn(name ="group_id")
    TeacherGroup teacherGroup;
    @Transient
    @CsvIgnore
    static public Teacher selectedTeacher;

    public Teacher() {
    }

    public Teacher(String name, String surname, TeacherStatus status, int yearOfBirth, double salary) {
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.yearOfBirth = yearOfBirth;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public TeacherStatus getStatus() {
        return status;
    }

    public void setStatus(TeacherStatus status) {
        this.status = status;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public TeacherGroup getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(TeacherGroup teacherGroup) {
        this.teacherGroup = teacherGroup;
    }
}
