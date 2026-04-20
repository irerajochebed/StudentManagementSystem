## Student Management System (Java OOP Project)
### Project Overview

This project is a Student Management System developed in Java to demonstrate the core principles of Object-Oriented Programming (OOP).

The system models a real-world university environment where:

    Students enroll in courses
    Instructors teach courses
    Departments manage instructors and courses
    Grades are assigned and GPA is calculated

The focus of this project is clean class design, real-world modeling, and proper implementation of OOP principles.
### Objectives

    Apply OOP principles in a real-world scenario
    Design logical relationships between classes
    Demonstrate Encapsulation, Abstraction, Inheritance, and Polymorphism
    Use standard for loops (no for-each loops)

### Project Structure

The system includes the following classes:
Class	Description
Person	Abstract base class for all people in the system
Student	Represents a student (inherits from Person)
Instructor	Represents an instructor (inherits from Person)
Course	Represents a university course
Enrollment	Links Student and Course (many-to-many relationship)
Department	Manages instructors and courses
Main	Runs and demonstrates the system

## OOP Concepts Implemented
  1. Encapsulation

    All fields are declared private
    Access provided through getters and setters
    Input validation inside setter methods

  2. Abstraction

    Person is an abstract class
    Contains abstract methods:
        getRole()
        displayInfo()
    Forces subclasses to provide their own implementation

   3. Inheritance

    Student extends Person
    Instructor extends Person
    Reuses common properties (name, email, age, ID)

  4. Polymorphism

    displayInfo() behaves differently for:
        Student
        Instructor
    Demonstrated using a Person[] array in Main

  5. Association (Real-World Relationship)

    Enrollment connects:
        One Student
        One Course
    Stores grade and status
    Represents a many-to-many relationship

  Features

    Create departments
    Add instructors and courses
    Enroll students in courses
    Prevent duplicate enrollment
    Prevent enrollment when course is full
    Assign grades
    Automatically calculate GPA
    Display:
        Student reports
        Course rosters
        Instructor dashboards
        Department summaries
