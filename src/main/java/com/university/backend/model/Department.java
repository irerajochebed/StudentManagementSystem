package com.university.backend.model;

import java.util.ArrayList;
import java.util.List;


public class Department {

    private String           departmentId;
    private String           departmentName;
    private String           building;
    private List<Instructor> instructors;
    private List<Course>     courses;

    public Department(String departmentId, String departmentName, String building) {
        this.departmentId   = departmentId;
        this.departmentName = departmentName;
        this.building       = building;
        this.instructors    = new ArrayList<>();
        this.courses        = new ArrayList<>();
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        System.out.println(instructor.getFullName()
                + " added to " + departmentName + " department.");
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course '" + course.getCourseName()
                + "' added to " + departmentName + " department.");
    }

    public void displayDepartmentSummary() {
        System.out.println("========================================");
        System.out.println("  DEPARTMENT : " + departmentName);
        System.out.println("  Building   : " + building);
        System.out.println("  Instructors: " + instructors.size());
        System.out.println("  Courses    : " + courses.size());
        System.out.println("----------------------------------------");

        System.out.println("  [Instructors]");
        for (int i = 0; i < instructors.size(); i++) {
            Instructor ins = instructors.get(i);
            System.out.println("    " + (i + 1) + ". "
                    + ins.getFullName()
                    + " (" + ins.getSpecialization() + ")");
        }

        System.out.println("  [Courses]");
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.println("    " + (i + 1) + ". "
                    + c.getCourseCode()
                    + " | " + c.getCourseName()
                    + " | Enrolled: " + c.getEnrolledCount()
                    + "/" + c.getMaxCapacity());
        }

        System.out.println("========================================");
    }

    public String getDepartmentId() {
        return departmentId;   }
    public String getDepartmentName() {
        return departmentName; }
    public String getBuilding() {
        return building;       }
    public List<Instructor> getInstructors(){
        return instructors;    }
    public List<Course> getCourses() {
        return courses;        }

    public void setBuilding(String building)    {
        this.building = building; }
}
