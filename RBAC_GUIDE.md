# Role-Based Access Control (RBAC) - Updated

## 🔐 User Roles & Permissions

### 👑 ADMIN (Full Access)
**Can do everything:**
- ✅ Add/Remove/Edit Students
- ✅ Add/Remove/Edit Courses
- ✅ Add/Remove/Edit Instructors
- ✅ Enroll Students in Courses
- ✅ Assign Grades to Students
- ✅ View All Data
- ✅ Save/Load/Clear All Data

**Tabs Visible:**
- 📊 Dashboard
- 👥 Students
- 📚 Courses
- 👨🏫 Instructors
- 📋 Enrollments

---

### 👨🏫 INSTRUCTOR (Limited Access)
**Can only:**
- ✅ View Courses (Read-Only)
- ✅ Assign Grades to Students
- ✅ View All Enrollments
- ❌ Cannot Add/Remove Students
- ❌ Cannot Add/Remove Courses
- ❌ Cannot Enroll Students

**Tabs Visible:**
- 📊 Dashboard
- 📚 Courses (View Only)
- 📋 Enrollments (Can Assign Grades Only)

**Use Case:**
- Instructor teaches courses assigned by admin
- Can grade students in their courses
- Cannot modify course structure or enrollment

---

### 👨🎓 STUDENT (Minimal Access)
**Can only:**
- ✅ View Their Own Enrollments
- ✅ View Their Own Grades
- ✅ View Available Courses (Read-Only)
- ❌ Cannot Enroll Themselves
- ❌ Cannot Modify Grades
- ❌ Cannot See Other Students' Data

**Tabs Visible:**
- 📊 Dashboard (Their Stats Only)
- 📚 Courses (View Only)
- 📋 Enrollments (Their Enrollments Only - Read-Only)

**Use Case:**
- Student logs in to check their grades
- Can see which courses they're enrolled in
- Can view course catalog
- Cannot modify anything

---

## 🎯 Workflow Examples

### Example 1: Admin Workflow
1. Login as `admin/admin123`
2. Go to **Students** tab → Add new student (STU-001)
3. Go to **Instructors** tab → Add new instructor (INS-001)
4. Go to **Courses** tab → Add new course (CS101)
5. Go to **Enrollments** tab → Enroll STU-001 in CS101
6. Assign grade to STU-001 for CS101
7. Save data

### Example 2: Instructor Workflow
1. Admin creates instructor (INS-001) and assigns courses
2. Instructor registers account:
   - Username: `prof_smith`
   - Password: `pass123`
   - Role: INSTRUCTOR
   - Linked ID: INS-001
3. Instructor logs in
4. Goes to **Enrollments** tab
5. Selects student enrollment
6. Assigns grade (e.g., 3.8)
7. Student's GPA updates automatically

### Example 3: Student Workflow
1. Admin creates student (STU-001)
2. Admin enrolls student in courses
3. Student registers account:
   - Username: `john_doe`
   - Password: `pass123`
   - Role: STUDENT
   - Linked ID: STU-001
4. Student logs in
5. Goes to **Enrollments** tab
6. Sees only their own courses and grades
7. Goes to **Courses** tab to browse available courses
8. Cannot enroll or modify anything

---

## 🔒 Security Features

### Field-Level Security
- **ADMIN**: All fields enabled
- **INSTRUCTOR**: Enrollment fields disabled, grade fields enabled
- **STUDENT**: All fields disabled (read-only)

### Data Filtering
- **ADMIN**: Sees all data
- **INSTRUCTOR**: Sees all data but limited actions
- **STUDENT**: Sees only their own data (filtered by Linked ID)

### Button Controls
- Add/Remove buttons disabled for non-admin users
- Grade assignment enabled only for ADMIN and INSTRUCTOR
- Enrollment button enabled only for ADMIN

---

## 📝 Testing the System

### Test 1: Admin Full Access
```
Login: admin / admin123
Expected: All tabs visible, all buttons enabled
```

### Test 2: Instructor Limited Access
```
1. As admin: Create instructor INS-001
2. Register: instructor1 / pass123 / INSTRUCTOR / INS-001
3. Login as instructor1
Expected: 
  - Only Dashboard, Courses, Enrollments tabs
  - Cannot enroll students
  - Can assign grades
```

### Test 3: Student Read-Only Access
```
1. As admin: Create student STU-001
2. As admin: Enroll STU-001 in CS101
3. Register: student1 / pass123 / STUDENT / STU-001
4. Login as student1
Expected:
  - Only Dashboard, Courses, Enrollments tabs
  - Enrollments shows only STU-001's courses
  - All fields disabled
  - Cannot modify anything
```

---

## 🎓 Default Accounts

### Admin Account (Pre-created)
```
Username: admin
Password: admin123
Role: ADMIN
Linked ID: (none)
```

### Create Test Accounts

**Instructor:**
1. Login as admin
2. Add instructor: INS-001, John Smith, john@university.edu
3. Logout
4. Register: instructor1 / pass123 / INSTRUCTOR / INS-001

**Student:**
1. Login as admin
2. Add student: STU-001, Jane Doe, jane@university.edu
3. Enroll STU-001 in some courses
4. Logout
5. Register: student1 / pass123 / STUDENT / STU-001

---

## 🔄 Permission Matrix

| Action | ADMIN | INSTRUCTOR | STUDENT |
|--------|-------|------------|---------|
| Add Student | ✅ | ❌ | ❌ |
| Remove Student | ✅ | ❌ | ❌ |
| View All Students | ✅ | ❌ | ❌ |
| Add Course | ✅ | ❌ | ❌ |
| Remove Course | ✅ | ❌ | ❌ |
| View All Courses | ✅ | ✅ | ✅ |
| Add Instructor | ✅ | ❌ | ❌ |
| Remove Instructor | ✅ | ❌ | ❌ |
| Enroll Student | ✅ | ❌ | ❌ |
| Assign Grade | ✅ | ✅ | ❌ |
| View All Enrollments | ✅ | ✅ | ❌ |
| View Own Enrollments | ✅ | ✅ | ✅ |
| Save/Load Data | ✅ | ✅ | ✅ |
| Clear All Data | ✅ | ❌ | ❌ |

---

## 💡 Key Points

1. **ADMIN** = Full system control
2. **INSTRUCTOR** = Can grade students only
3. **STUDENT** = Can view their own data only
4. **Linked ID** connects user accounts to Student/Instructor records
5. All permissions enforced at UI level (buttons disabled/hidden)
6. Data filtering ensures students see only their own records

---

**Last Updated:** May 3, 2026  
**Version:** 2.1.0 with RBAC
