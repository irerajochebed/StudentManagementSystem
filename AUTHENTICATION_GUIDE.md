# Authentication & Authorization Setup

## ✅ What Was Added

### 1. **User Model** (`User.java`)
- Username, password, role (ADMIN, INSTRUCTOR, STUDENT)
- Linked ID to connect users to Student/Instructor records

### 2. **Authentication Manager** (`AuthenticationManager.java`)
- User registration and login
- Session management
- Default admin account: `username: admin, password: admin123`

### 3. **Login & Register Pages**
- `Login.fxml` - Login screen
- `Register.fxml` - User registration
- `LoginController.java` - Login logic
- `RegisterController.java` - Registration logic

### 4. **Role-Based Access Control**
- **ADMIN**: Full access to all tabs (Dashboard, Students, Courses, Instructors, Enrollments)
- **INSTRUCTOR**: Access to Courses and Enrollments only
- **STUDENT**: Access to Courses and Enrollments only

### 5. **Data Persistence**
- Users saved to `data/users.csv`
- Auto-load on startup
- Auto-save on exit

## 🚀 How to Run

### Step 1: Clean and Rebuild
```bash
mvn clean compile
```

### Step 2: Run the Application
```bash
mvn javafx:run
```

Or in IntelliJ:
1. **File → Invalidate Caches → Invalidate and Restart**
2. Wait for indexing to complete
3. Click the green Run button

## 🔐 Default Login Credentials

**Admin Account:**
- Username: `admin`
- Password: `admin123`

## 📝 How to Use

### First Time Login
1. Application starts with Login screen
2. Use default admin credentials
3. You'll see the full system with all tabs

### Register New Users
1. Click "Register" on login screen
2. Fill in:
   - Username (unique)
   - Password (min 4 characters)
   - Confirm Password
   - Role (ADMIN/INSTRUCTOR/STUDENT)
   - Linked ID (optional - Student ID or Instructor ID)
3. Click "Register"
4. Redirected to login automatically

### Role-Based Views

**ADMIN sees:**
- 📊 Dashboard
- 👥 Students
- 📚 Courses
- 👨🏫 Instructors
- 📋 Enrollments

**INSTRUCTOR sees:**
- 📊 Dashboard
- 📚 Courses
- 📋 Enrollments

**STUDENT sees:**
- 📊 Dashboard
- 📚 Courses
- 📋 Enrollments

### Logout
- Click "Logout" button in top-right corner
- Returns to login screen

## 📁 New Files Created

```
src/main/java/com/university/
├── backend/
│   ├── manager/
│   │   └── AuthenticationManager.java          ✨ NEW
│   └── model/
│       └── User.java                            ✨ NEW
└── ui/
    └── controller/
        ├── LoginController.java                 ✨ NEW
        └── RegisterController.java              ✨ NEW

src/main/resources/
├── fxml/
│   ├── Login.fxml                               ✨ NEW
│   └── Register.fxml                            ✨ NEW
└── css/
    └── styles.css                               ✨ UPDATED

data/
└── users.csv                                    ✨ AUTO-CREATED
```

## 🔧 Modified Files

1. **StudentManagementApp.java**
   - Added AuthenticationManager
   - Shows login screen first
   - Saves/loads user data

2. **MainController.java**
   - Role-based tab visibility
   - User info display
   - Logout functionality

3. **MainWindow.fxml**
   - Added user info label
   - Added logout button

4. **FileIOHandler.java**
   - Save/load users to CSV
   - Overloaded methods for backward compatibility

## 🎯 Testing Scenarios

### Test 1: Admin Login
1. Login as admin
2. Verify all 5 tabs are visible
3. Add students, courses, instructors
4. Logout and login again
5. Verify data persists

### Test 2: Create Instructor Account
1. Login as admin
2. Add an instructor (e.g., ID: INS-001)
3. Logout
4. Register new user:
   - Username: instructor1
   - Password: pass123
   - Role: INSTRUCTOR
   - Linked ID: INS-001
5. Login as instructor1
6. Verify only Dashboard, Courses, Enrollments tabs visible

### Test 3: Create Student Account
1. Login as admin
2. Add a student (e.g., ID: STU-001)
3. Logout
4. Register new user:
   - Username: student1
   - Password: pass123
   - Role: STUDENT
   - Linked ID: STU-001
5. Login as student1
6. Verify only Dashboard, Courses, Enrollments tabs visible

## 🐛 Troubleshooting

### Issue: "ClassNotFoundException"
**Solution:** 
```bash
# In IntelliJ
File → Invalidate Caches → Invalidate and Restart

# Or rebuild
mvn clean compile
```

### Issue: Login screen doesn't appear
**Solution:** Check that Login.fxml exists in `src/main/resources/fxml/`

### Issue: Users not persisting
**Solution:** 
- Check `data/` directory exists
- Verify write permissions
- Check console for error messages

## 📊 Data Files

### users.csv Format
```csv
Username,Password,Role,LinkedId
admin,admin123,ADMIN,
instructor1,pass123,INSTRUCTOR,INS-001
student1,pass123,STUDENT,STU-001
```

## 🔒 Security Notes

⚠️ **For Educational Purposes Only**
- Passwords stored in plain text (not production-ready)
- No password hashing/encryption
- No session timeout
- No password strength validation beyond length

**For Production:**
- Use BCrypt or similar for password hashing
- Implement session management
- Add HTTPS/TLS
- Add password complexity requirements
- Add account lockout after failed attempts
- Add password reset functionality

## ✨ Features Summary

✅ Login/Register UI
✅ Role-based access control
✅ Session management
✅ User data persistence
✅ Logout functionality
✅ Default admin account
✅ User info display
✅ Responsive design
✅ Error handling
✅ Input validation

---

**Ready to use!** Login with `admin/admin123` and start managing your university! 🎓
