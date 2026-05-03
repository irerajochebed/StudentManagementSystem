package com.studentms.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String specialization;
    private String officeLocation;
    private double salary;
    private List<Course> assignedCourses;

    public Instructor(String instructorId, String firstName, String lastName,
                      String email, int age, String specialization,
                      String officeLocation, double salary) {
        super(instructorId, firstName, lastName, email, age);
        this.specialization  = specialization;
        this.officeLocation  = officeLocation;
        this.salary          = salary;
        this.assignedCourses = new ArrayList<>();
    }

    @Override public String getRole() { return "Instructor"; }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getPersonId() + " | " + getFullName()
                + " | Spec: " + specialization + " | Office: " + officeLocation);
    }

    public void assignCourse(Course course) { assignedCourses.add(course); }

    public String getSpecialization()        { return specialization; }
    public String getOfficeLocation()        { return officeLocation; }
    public double getSalary()                { return salary; }
    public List<Course> getAssignedCourses() { return assignedCourses; }
}
