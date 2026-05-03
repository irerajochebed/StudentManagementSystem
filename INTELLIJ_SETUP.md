# IntelliJ IDEA Setup Instructions

## ✅ Fix "ClassNotFoundException" in IntelliJ

### Method 1: Invalidate Caches (Recommended)
1. **File → Invalidate Caches...**
2. Check all boxes:
   - ✅ Invalidate and Restart
   - ✅ Clear file system cache and Local History
   - ✅ Clear downloaded shared indexes
3. Click **Invalidate and Restart**
4. Wait for IntelliJ to restart and re-index (2-3 minutes)
5. Click the green **Run** button

---

### Method 2: Rebuild Project
1. **Build → Rebuild Project**
2. Wait for completion
3. **Run → Run 'StudentManagementApp'**

---

### Method 3: Reimport Maven Project
1. Right-click on **pom.xml**
2. Select **Maven → Reload Project**
3. Wait for dependencies to download
4. **Build → Build Project**
5. Click **Run** button

---

### Method 4: Configure Run Configuration Manually
1. **Run → Edit Configurations...**
2. Click **+** (Add New Configuration)
3. Select **Application**
4. Fill in:
   - **Name:** `StudentManagementApp`
   - **Main class:** `com.university.app.StudentManagementApp`
   - **Module:** `student-management-system`
   - **JRE:** Java 17 or higher
5. Click **Apply** then **OK**
6. Click **Run** button

---

### Method 5: Use Maven to Run (Always Works)
```bash
mvn javafx:run
```

This bypasses IntelliJ and runs directly via Maven.

---

## 🔍 Verify Setup

### Check Java Version
```bash
java -version
```
Should show Java 17 or higher.

### Check Maven
```bash
mvn -version
```
Should show Maven 3.6 or higher.

### Check Compilation
```bash
mvn clean compile
```
Should show **BUILD SUCCESS**.

---

## 🎯 Quick Test

1. Open Terminal in IntelliJ (Alt+F12)
2. Run:
   ```bash
   mvn javafx:run
   ```
3. If application starts → IntelliJ cache issue
4. If application fails → Check error message

---

## 🐛 Common Issues

### Issue: "Module not specified"
**Solution:** 
- Run → Edit Configurations
- Set Module to: `student-management-system`

### Issue: "Main class not found"
**Solution:**
- Verify Main class: `com.university.app.StudentManagementApp`
- Check spelling and package name

### Issue: "JavaFX runtime components missing"
**Solution:**
- Maven handles JavaFX automatically
- Use `mvn javafx:run` instead

### Issue: "Java version mismatch"
**Solution:**
- File → Project Structure → Project
- Set SDK to Java 17 or higher
- Set Language Level to 17

---

## ✨ Best Practice

**Always use Maven for JavaFX projects:**
```bash
# Clean build
mvn clean compile

# Run application
mvn javafx:run

# Package JAR
mvn clean package
```

---

## 📝 Run Configuration Created

A run configuration has been created at:
`.idea/runConfigurations/StudentManagementApp.xml`

After invalidating caches, this should appear in your run configurations dropdown.

---

## 🚀 Recommended Workflow

1. **First time setup:**
   - File → Invalidate Caches → Restart
   - Wait for indexing to complete

2. **Daily development:**
   - Use IntelliJ Run button (after cache invalidation)
   - Or use `mvn javafx:run` in terminal

3. **If issues occur:**
   - Maven → Reload Project
   - Build → Rebuild Project
   - Or use `mvn javafx:run`

---

**Status:** Run configuration created ✅  
**Next Step:** Invalidate Caches and Restart IntelliJ
