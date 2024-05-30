package com.example.appSchool.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String firstName;
    @Column(unique = true, nullable = false)
    private String lastName;
    private String email;
    private String address;
    private double average;
    private boolean state;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JoinColumn(name = "userId")
    private User1 user1;
    @OneToMany
            (cascade=CascadeType.ALL,mappedBy="student")
   private Set<StudentAssignment> studentAssignments;
    @ManyToOne
//    @JoinColumn(name = "professor_id", nullable = false, updatable = false)

    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Student(Long id, String firstName, String lastName, String email, String address, double average, User1 user1, Set<StudentAssignment> studentAssignments, Professor professor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.average = average;
        this.user1 = user1;
        this.studentAssignments = studentAssignments;
        this.professor = professor;
    }

    public Student(Long id, String firstName, String lastName, String email, String address, double average,
                   boolean state, User1 user1, Set<StudentAssignment> studentAssignments, Professor professor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.average = average;
        this.state = state;
        this.user1 = user1;
        this.studentAssignments = studentAssignments;
        this.professor = professor;
    }

    public Student() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStudentId() {
        return id;
    }

    public void setStudentId(Long studentId) {
        this.id = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<StudentAssignment> getStudentAssignments() {
        return studentAssignments;
    }

    public void setStudentAssignments(Set<StudentAssignment> studentAssignments) {
        this.studentAssignments = studentAssignments;
    }

    public String getSection() {

        return "";
    }

    public String getPassword() {
        return user1.getPassword();
    }

    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public User1 getUser1() {
        return user1;
    }

    public void setUser1(User1 user1) {
        this.user1 = user1;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}


