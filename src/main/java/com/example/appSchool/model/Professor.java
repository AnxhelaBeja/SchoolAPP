package com.example.appSchool.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String firstName;
    @Column(unique = true, nullable = false)
    private String lastName;
//    private String email;

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User1 user1;
    @OneToMany(mappedBy = "professor")
    private List<Student> students = new ArrayList<>();



    public void addStudent(Student student) {
        this.students.add(student);
    }

    public Professor(){

    }

    public Professor(Long id, String firstName, String lastName, User1 user1, List<Student> students) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user1 = user1;
        this.students = students;
    }

    public Professor(String firstName, String lastName, String email, String address) {
    }
}
