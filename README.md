# University Student Management System - JavaFX Edition v2.0

## 📋 Project Overview

A comprehensive **JavaFX Maven application** integrating a fully-designed OOP backend for university operations management. This application demonstrates professional software architecture with clean separation of UI and business logic layers.

**Version:** 2.0.0  
**Status:** ✅ Production Ready  
**Build Tool:** Maven  
**UI Framework:** JavaFX 21  
**Java Version:** 17+

### 🎯 Key Features

✅ **Complete Student Management** - Registration, editing, searching, GPA tracking  
✅ **Course Management** - Creation, capacity management, semester scheduling  
✅ **Instructor Assignment** - Staff management with specialization tracking  
✅ **Enrollment System** - Course enrollment with duplicate prevention  
✅ **Grade Management** - Automatic GPA calculation and grade assignment  
✅ **Exception Handling** - Comprehensive error handling with custom exceptions  
✅ **Data Persistence** - CSV-based data storage with auto-save  
✅ **Responsive UI** - Modern JavaFX interface with 5 management tabs  

## 🏗️ Architecture

### OOP Principles Implemented
✅ **Encapsulation** - Private fields with public getters/setters  
✅ **Abstraction** - Abstract Person class with polymorphic methods  
✅ **Inheritance** - Student and Instructor extend Person  
✅ **Polymorphism** - Overridden displayInfo() and getRole() methods  

### Design Patterns
✅ **MVC Pattern** - Separation of UI (Controllers) and Business Logic (Manager)  
✅ **Manager Pattern** - Centralized business logic orchestration  
✅ **Exception Pattern** - Custom exception hierarchy for error handling  
✅ **Factory Pattern** - Object creation through manager classes
## 📁 Project Structure

```
src/main/java/com/university/
├── app/
│   └── StudentManagementApp.java          # Main JavaFX application
├── backend/
│   ├── exception/
│   │   ├── StudentManagementException.java     # Base exception
│   │   ├── InvalidEmailException.java
│   │   ├── InvalidAgeException.java
│   │   ├── InvalidCapacityException.java
│   │   ├── InvalidGradeException.java
│   │   ├── CourseFullException.java
│   │   └── DuplicateEnrollmentException.java
│   ├── model/
│   │   ├── Person.java                    # Abstract base class
│   │   ├── Student.java
│   │   ├── Instructor.java
│   │   ├── Course.java
│   │   ├── Enrollment.java
│   │   └── Department.java
│   └── manager/
│       └── StudentManagementManager.java   # Business logic orchestrator
├── ui/
│   ├── controller/
│   │   ├── MainController.java
│   │   ├── DashboardController.java
│   │   ├── StudentController.java
│   │   ├── CourseController.java
│   │   ├── InstructorController.java
│   │   └── EnrollmentController.java
│   └── component/                         # Custom UI components
└── util/
    └── FileIOHandler.java                 # Data persistence

src/main/resources/
├── fxml/
│   ├── MainWindow.fxml
│   ├── DashboardTab.fxml
│   ├── StudentTab.fxml
│   ├── CourseTab.fxml
│   ├── InstructorTab.fxml
│   └── EnrollmentTab.fxml
└── css/
    └── styles.css
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation
```bash
# Clone the repository
git clone https://github.com/irerajochebed/StudentManagementSystem.git
cd StudentManagementSystemFX

# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

## 🎮 Using the Application

### Main Dashboard
- View system statistics (total students, courses, instructors, average GPA)
- Monitor activity log
- Quick access to all management functions

### Student Management Tab
1. **Add Student**: Enter ID, name, email (with @ and .), age (16-100), major, year level
2. **Search**: Find students by name in real-time
3. **View Details**: Click "View Details" for comprehensive student profile
4. **Remove**: Select student and click "Remove Selected"
5. **Refresh**: Update the list

### Course Management Tab
1. **Create Course**: Enter code, name, description, credits, capacity, semester
2. **View Status**: See if course is OPEN or FULL
3. **Manage Enrollments**: Track enrolled students
4. **Remove Course**: Delete course from system

### Instructor Management Tab
1. **Add Instructor**: Register faculty with specialization and office info
2. **Set Salary**: Track compensation information
3. **Assign Courses**: Link instructors to courses
4. **Track Workload**: View courses taught by each instructor

### Enrollment Management Tab
1. **Enroll Students**: Select student and course from dropdowns
2. **Prevent Issues**:
   - Duplicate enrollment automatically prevented
   - Course capacity enforced
3. **Assign Grades**: Select enrollment and enter grade (0.0-4.0)
4. **Automatic Calculation**: GPA calculated automatically

## 💾 Data Management

### Saving Data
```bash
Click "Save Data" button → Saves to CSV files in /data directory
Auto-saves on application exit
```

### File Format
```csv
# students.csv
ID,FirstName,LastName,Email,Age,Major,YearLevel

# courses.csv  
CourseCode,CourseName,Description,Credits,MaxCapacity,Semester

# instructors.csv
ID,FirstName,LastName,Email,Age,Specialization,Office,Salary
```

## 🌿 Git Branches

### Branch Strategy
- **main**: Production-ready code
- **develop**: Integration branch
- **feature/backend**: Backend components
- **feature/ui**: UI and controllers
- **feature/integration**: Integration testing

### Creating Features
```bash
# Start from develop
git checkout develop

# Create feature branch
git checkout -b feature/your-feature

# Make changes and commit
git add .
git commit -m "feat: Add new feature"

# Create Pull Request
git push origin feature/your-feature
```

## ✅ Exception Handling

All operations use custom exceptions for robust error handling:

```java
// Email validation (must contain @ and .)
InvalidEmailException - "Invalid email: 'user'. Email must contain '@' and '.'"

// Age validation (16-100)
InvalidAgeException - "Invalid age: 10. Age must be between 16 and 100."

// Course capacity
InvalidCapacityException - "Invalid capacity: 0. Capacity must be at least 1."
CourseFullException - "Course 'CS101' is full. Maximum capacity: 30"

// Enrollment
DuplicateEnrollmentException - "Student 'John Doe' is already enrolled in 'CS101'."

// Grades
InvalidGradeException - "Invalid grade: 5.0. Grade must be between 0.0 and 4.0."
```

## 🧪 Example: Complete Workflow

```java
// 1. Create manager
StudentManagementManager manager = new StudentManagementManager();

// 2. Create student (validates email and age)
Student student = new Student(
    "STU-001", "John", "Doe",
    "john@university.edu",  // Must have @ and .
    20,                      // Must be 16-100
    "Computer Science",
    1
);
manager.addStudent(student);

// 3. Create course (validates capacity)
Course course = new Course(
    "CS101", "Intro to Programming",
    "Learn programming basics",
    3,    // Credits
    30,   // Capacity (must be >= 1)
    "Fall"
);
manager.addCourse(course);

// 4. Enroll student
try {
    manager.enrollStudent("STU-001", "CS101");
} catch (CourseFullException e) {
    System.out.println("Course is full");
} catch (DuplicateEnrollmentException e) {
    System.out.println("Already enrolled");
}

// 5. Assign grade
try {
    manager.assignGrade("STU-001", "CS101", 3.8);
} catch (InvalidGradeException e) {
    System.out.println("Invalid grade");
}

// 6. Save data
FileIOHandler fileIO = new FileIOHandler();
fileIO.saveManager(manager);
```

## 📊 Features by Tab

### Dashboard
- 4 statistics cards (Students, Courses, Instructors, Avg GPA)
- Activity log
- Refresh button
- Clear log option

### Students (👥)
- Tabular display with all properties
- Add form with validation
- Search by name
- View detailed profile
- Remove functionality
- Status messages

### Courses (📚)
- Course listing with availability
- Add/edit course details
- Capacity management
- Semester selection
- Remove courses
- Status indicators (OPEN/FULL)

### Instructors (👨‍🏫)
- Faculty directory
- Salary tracking
- Specialization info
- Office location
- Course assignments
- Workload tracking

### Enrollments (📋)
- Student-course assignments
- Grade management
- GPA calculation
- Status tracking
- Enrollment date logging
- Letter grade conversion

## 🔐 Validation Rules

| Field | Rule | Example |
|-------|------|---------|
| Email | Must contain @ and . | john@university.edu ✓ |
| Age | 16-100 range | 20 ✓, 10 ✗ |
| Capacity | Minimum 1 | 30 ✓, 0 ✗ |
| Grade | 0.0-4.0 range | 3.8 ✓, 5.0 ✗ |
| ID | Must be unique | STU-001 ✓, STU-001 ✗ |
| Enrollment | No duplicates | Prevented automatically |

## 🎨 User Interface

### Modern Design
- Clean, professional appearance
- Color-coded status indicators
- Intuitive navigation
- Responsive table views
- Dropdown selections for relationships
- Input spinners for numeric values

### CSS Styling
- Professional color scheme
- Hover effects on buttons
- Focus states on inputs
- Readable typography
- Proper spacing and padding

## ⚙️ Technologies

| Component | Technology |
|-----------|-----------|
| UI Framework | JavaFX 21 |
| Build Tool | Maven 3.6+ |
| Language | Java 17+ |
| Layout | FXML |
| Styling | CSS |
| Data Format | CSV |
| Testing | JUnit 4.13 |

## 📈 Performance

- Handles 1000+ records efficiently
- In-memory collections for fast operations
- Lazy evaluation with Streams API
- CSV format for quick load/save
- Platform.runLater() for thread-safe UI updates

## 🔄 Development Workflow

1. **Clone Repository**
   ```bash
   git clone https://github.com/irerajochebed/StudentManagementSystem.git
   ```

2. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature develop
   ```

3. **Make Changes**
   ```bash
   # Edit files
   git add .
   git commit -m "feat: description"
   ```

4. **Push and Create PR**
   ```bash
   git push origin feature/your-feature
   # Create PR on GitHub
   ```

5. **Merge to Develop**
   ```bash
   git checkout develop
   git merge feature/your-feature
   ```

## 📝 Build Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Remove build artifacts |
| `mvn compile` | Compile source code |
| `mvn package` | Create JAR file |
| `mvn javafx:run` | Run application |
| `mvn test` | Run unit tests |
| `mvn clean package` | Build production JAR |

## 🐛 Troubleshooting

**Issue**: Application won't start  
**Solution**: Ensure Java 17+ is installed: `java -version`

**Issue**: Maven build fails  
**Solution**: Clear cache: `mvn clean`, ensure internet connection for dependencies

**Issue**: Data not saving  
**Solution**: Ensure /data directory exists and has write permissions

**Issue**: JavaFX not found  
**Solution**: Maven will download automatically; check internet connection

## 📞 Support & Contact

- **GitHub Issues**: Report bugs and request features
- **Repository**: https://github.com/irerajochebed/StudentManagementSystem
- **Email**: For critical issues

## 📄 License

© 2026 University Student Management System. All rights reserved.

## 🎓 Learning Outcomes

This project teaches:
- ✅ Object-Oriented Programming principles
- ✅ JavaFX GUI development
- ✅ Maven build management
- ✅ Exception handling patterns
- ✅ File I/O operations
- ✅ Git version control
- ✅ Professional code organization
- ✅ MVC architectural pattern
- ✅ Data persistence strategies
- ✅ Responsive UI design

## 📚 Version History

**v2.0.0** (Current - May 3, 2026)
- Complete JavaFX Maven application
- 6 management tabs
- Comprehensive UI with FXML layouts
- Complete exception hierarchy
- CSV data persistence
- Production-ready

**v1.0.0** (Previous)
- Backend OOP implementation
- Console-based interface
- Basic exception handling

---

**Last Updated**: May 3, 2026  
**Maintained By**: GitHub Copilot  
**Status**: ✅ Production Ready
