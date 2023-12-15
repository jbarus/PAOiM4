package com.example.paoim4.back;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private long id;
    private int starNumber;
    private Date reviewDate;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private TeacherGroup teacherGroup;

    public Rate() {
    }

    public Rate(int starNumber, Date reviewDate, String comment) {
        this.starNumber = starNumber;
        this.reviewDate = reviewDate;
        this.comment = comment;
    }

    public int getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(int starNumber) {
        this.starNumber = starNumber;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TeacherGroup getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(TeacherGroup teacherGroup) {
        this.teacherGroup = teacherGroup;
    }
}
