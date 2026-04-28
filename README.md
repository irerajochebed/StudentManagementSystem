# Student Management System — Phase 3: File I/O

## Overview

This phase extends the Student Management System by adding **data persistence** using Java File I/O.

Previously, all data (students, courses, enrollments) existed only in memory and was lost when the program stopped.  
Now, data is **saved to text files** and can be **loaded back** at any time — even after the program restarts.

---

## What is File I/O?

**File I/O** means reading from and writing to files on disk.

| Term | Meaning |
|---|---|
| **Write (Output)** | Save data from memory → into a file |
| **Read (Input)** | Load data from a file → back into memory |

In this project we use two simple Java classes:

- `FileWriter` — opens or creates a file and writes text into it
- `BufferedReader` + `FileReader` — opens a file and reads it line by line

---

## File Format — CSV (Comma-Separated Values)

Each file stores one record per line, with fields separated by commas.  
This is called **CSV format** — simple, human-readable, and easy to parse.

**Example — students.txt:**
```
studentId,firstName,lastName,email,age,major,yearLevel,gpa
STU-001,Alice,Johnson,alice@uni.edu,20,Computer Science,2,3.50
STU-002,Bob,Williams,bob@uni.edu,21,Computer Science,2,2.65
```

**Example — courses.txt:**
```
courseCode,courseName,description,creditHours,maxCapacity,semester,instructorName
CS101,Data Structures,Arrays; Linked Lists; Trees,3,3,Fall 2025,John Smith
CS301,Introduction to AI,Search; ML; Neural Networks,3,30,Fall 2025,Priya Patel
```

**Example — enrollments.txt:**
```
studentId,studentName,courseCode,courseName,grade,status
STU-001,Alice Johnson,CS101,Data Structures,A,COMPLETED
STU-001,Alice Johnson,CS301,Introduction to AI,A-,COMPLETED
```

---

## Files Created on Disk

| File | Contents |
|---|---|
| `students.txt` | All student records (ID, name, email, age, major, year, GPA) |
| `courses.txt` | All course records (code, name, description, credits, capacity, semester, instructor) |
| `enrollments.txt` | All enrollment records (student, course, grade, status) |

---

## New Class: DataManager.java

`DataManager` is the dedicated class that handles all file reading and writing.  
All methods are `static` — no need to create an object, just call them directly.

```
DataManager.saveStudents(allStudents);
DataManager.loadStudents();
```

### Methods

| Method | Direction | Description |
|---|---|---|
| `saveStudents(List<Student>)` | Write | Saves all students to `students.txt` |
| `loadStudents()` | Read | Reads `students.txt`, returns `List<Student>` |
| `saveCourses(List<Course>)` | Write | Saves all courses to `courses.txt` |
| `loadCourses()` | Read | Reads `courses.txt`, returns `List<Course>` |
| `saveEnrollments(List<Student>)` | Write | Saves all enrollments to `enrollments.txt` |
| `loadAndDisplayEnrollments()` | Read | Reads `enrollments.txt` and prints a table |

---

## How It Works — Step by Step

### Writing to a File
```java
// 1. Open (or create) the file
FileWriter writer = new FileWriter("students.txt");

// 2. Write a header line
writer.write("studentId,firstName,lastName,...\n");

// 3. Write one line per student
writer.write("STU-001,Alice,Johnson,...\n");

// 4. File is automatically closed (try-with-resources)
```

### Reading from a File
```java
// 1. Open the file
BufferedReader reader = new BufferedReader(new FileReader("students.txt"));

// 2. Read line by line
String line = reader.readLine(); // reads one line

// 3. Split by comma to get individual fields
String[] parts = line.split(",");

// 4. Rebuild the object
Student s = new Student(parts[0], parts[1], parts[2], ...);
```

---

## Project Structure

```
StudentManagementSystem/
├── src/
│   ├── Person.java              # Abstract base class
│   ├── Student.java             # Extends Person — List + Set collections
│   ├── Instructor.java          # Extends Person — List + Map collections
│   ├── Course.java              # List + Set + Map collections
│   ├── Enrollment.java          # Map for grade history
│   ├── Department.java          # List + Map + Set collections
│   ├── DataManager.java         # NEW — handles all File I/O
│   ├── Main.java                # Runs all demos
│   └── [Exception classes]
├── students.txt                 # NEW — saved student data
├── courses.txt                  # NEW — saved course data
└── enrollments.txt              # NEW — saved enrollment data
```

---

## How to Run

```bash
# Compile all files
javac -d out src/*.java

# Run the program
java -cp out Main
```

After running, check the project root folder for:
- `students.txt`
- `courses.txt`
- `enrollments.txt`

---

## Previous Phases

| Phase | Focus |
|---|---|
| Phase 1 | OOP — Encapsulation, Abstraction, Inheritance, Polymorphism |
| Phase 2 | Collections — List, Set, Map relationships |
| **Phase 3** | **File I/O — Save and load data using text files** |
