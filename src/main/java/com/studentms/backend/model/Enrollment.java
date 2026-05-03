package com.studentms.backend.model;

import java.time.LocalDate;

public class Enrollment {

    private Student student;
    private Course  course;
    private double  gradePoints;
    private boolean isGraded;
    private LocalDate enrollmentDate;
    private String  status;

    public Enrollment(Student student, Course course) {
        this.student        = student;
        this.course         = course;
        this.gradePoints    = 0.0;
        this.isGraded       = false;
        this.enrollmentDate = LocalDate.now();
        this.status         = "ACTIVE";
    }

    public void assignGrade(double gradePoints) {
        if (gradePoints < 0.0 || gradePoints > 4.0)
            throw new RuntimeException("Grade must be between 0.0 and 4.0.");
        this.gradePoints = gradePoints;
        this.isGraded    = true;
        this.status      = "COMPLETED";
        student.recalculateGPA();
    }

    public String getLetterGrade() {
        if (!isGraded) return "N/A";
        if (gradePoints >= 3.7) return "A";
        if (gradePoints >= 3.3) return "A-";
        if (gradePoints >= 3.0) return "B+";
        if (gradePoints >= 2.7) return "B";
        if (gradePoints >= 2.3) return "B-";
        if (gradePoints >= 2.0) return "C+";
        if (gradePoints >= 1.7) return "C";
        if (gradePoints >= 1.3) return "C-";
        if (gradePoints >= 1.0) return "D";
        return "F";
    }

    public String getSummary() {
        return course.getCourseCode() + " | " + course.getCourseName()
             + " | Grade: " + getLetterGrade() + " | Status: " + status;
    }

    public Student  getStudent()     { return student; }
    public Course   getCourse()      { return course; }
    public double   getGradePoints() { return gradePoints; }
    public boolean  isGraded()       { return isGraded; }
    public String   getStatus()      { return status; }
}
