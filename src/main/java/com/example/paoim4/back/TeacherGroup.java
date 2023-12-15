package com.example.paoim4.back;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class TeacherGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;
    private String groupName;
    private int size;
    @CsvIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "teacherGroup",fetch = FetchType.EAGER)
    private List<Teacher> teacherList = new ArrayList<>();
    @CsvIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "teacherGroup",fetch = FetchType.EAGER)
    private List<Rate> rateList = new ArrayList<>();
    @Transient
    @CsvIgnore
    static public TeacherGroup selectedGroup;

    public TeacherGroup() {
    }

    public TeacherGroup(String groupName, int size) {
        this.groupName = groupName;
        this.size = size;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Rate> getRateList() {
        return rateList;
    }

    public void setRateList(List<Rate> rateList) {
        this.rateList = rateList;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
