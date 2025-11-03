# 🚀 Complete Setup Guide - SkillBarter

This guide will walk you through setting up and running the entire SkillBarter project from scratch.

---

## 📋 Prerequisites

1. **Java 21** (Required for Spring Boot 3.5.7)
   - Download from: https://www.oracle.com/java/technologies/downloads/#java21
   - Verify: `java -version` (should show version 21)

2. **MySQL Database**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Install and start MySQL service

3. **Maven** (or use Maven Wrapper included)
   - Download from: https://maven.apache.org/download.cgi
   - Or use `mvnw` wrapper included in backend folder

4. **IDE** (Optional but recommended)
   - IntelliJ IDEA: https://www.jetbrains.com/idea/
   - Or Eclipse, VS Code with Java extensions

---

## 🗄️ Step 1: Database Setup

### 1.1 Install and Start MySQL

**Windows:**
- Install MySQL from the official website
- MySQL will run as a Windows service automatically
- Or start manually: Open Services → find MySQL → Start

**Mac:**
```bash
brew install mysql
brew services start mysql
```

**Linux:**
```bash
sudo apt-get install mysql-server
sudo systemctl start mysql
```

### 1.2 Create Database

1. Open MySQL Command Line Client or MySQL Workbench
2. Login with root credentials
3. Run these commands:

```sql
CREATE DATABASE skillbarter;
USE skillbarter;
```

### 1.3 Configure Database Connection

1. Open: `backend/src/main/resources/application.properties`
2. Update the database credentials:

```properties
# Update these values to match your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/skillbarter?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**⚠️ Important:** Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password.

---

## 🔧 Step 2: Backend Setup

### 2.1 Navigate to Backend Directory

```bash
cd backend
```

### 2.2 Build the Backend (First Time)

**Using Maven Wrapper (Windows):**
```bash
mvnw.cmd clean install
```

**Using Maven Wrapper (Mac/Linux):**
```bash
./mvnw clean install
```

**Or if you have Maven installed:**
```bash
mvn clean install
```

### 2.3 Run the Backend

**Option A: Using Maven Wrapper**
```bash
# Windows
mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run
```

**Option B: Using IntelliJ IDEA**
1. Open IntelliJ IDEA
2. File → Open → Select `backend` folder
3. Wait for Maven to download dependencies
4. Open `backend/src/main/java/com/skillbarter/backend/BackendApplication.java`
5. Right-click → Run 'BackendApplication'
6. Wait for "Started BackendApplication" message

**Option C: Run JAR file (after building)**
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### 2.4 Verify Backend is Running

1. Open browser: http://localhost:8081/api/users
2. You should see: `[]` (empty array - this is normal)
3. If you see JSON, the backend is working! ✅

**Backend will automatically:**
- Create database tables on first run
- Run on port 8081
- Enable CORS for frontend requests

---

## 🖥️ Step 3: Frontend Setup

### 3.1 Navigate to Frontend Directory

Open a new terminal/command prompt:
```bash
cd SkillBarter
```

### 3.2 Compile the Frontend

**Option A: Using IntelliJ IDEA**
1. File → Open → Select `SkillBarter` folder
2. Wait for project to index
3. Open `SkillBarter/src/Main.java`
4. Right-click → Run 'Main'
5. The UI window should open! ✅

**Option B: Using Command Line**

```bash
# Windows
cd SkillBarter\src
javac Main.java ui\**\*.java ui\components\*.java ui\panels\*.java ui\core\*.java ui\models\*.java logic\*.java
java Main

# Mac/Linux
cd SkillBarter/src
javac Main.java ui/**/*.java ui/components/*.java ui/panels/*.java ui/core/*.java ui/models/*.java logic/*.java
java Main
```

**If you get "package does not exist" errors:**

Navigate to the SkillBarter directory first:
```bash
cd SkillBarter
javac -d out/production/SkillBarter -sourcepath src src/Main.java
java -cp out/production/SkillBarter Main
```

### 3.3 Verify Frontend Works

1. The SkillBarter window should open
2. You should see the Dashboard panel
3. Navigation buttons should work
4. All panels should load without errors

---

## 🔗 Step 4: Connect Frontend to Backend

Currently, the frontend has sample data. To connect it to the backend:

### 4.1 Test Backend Connection

1. Make sure backend is running (check http://localhost:8081/api/users)
2. Backend should respond with JSON

### 4.2 Update Frontend API Calls

The frontend panels are ready but need to connect to real API endpoints. Here's how:

**Example: User Registration (already implemented in UserManagementPanel)**

The `UserManagementPanel` already has API integration:
- It sends POST requests to `http://localhost:8081/api/users`
- Make sure backend is running
- Try registering a user through the UI

**To add more API integrations**, you would need to:
1. Create API client classes in the frontend
2. Make HTTP requests to backend endpoints
3. Parse JSON responses

---

## ✅ Step 5: Test Everything

### 5.1 Test Backend API

**Using Browser or Postman:**

1. **Get All Users:**
   - GET: http://localhost:8081/api/users
   - Should return: `[]`

2. **Create User:**
   - POST: http://localhost:8081/api/users
   - Body (JSON):
   ```json
   {
     "username": "john",
     "email": "john@example.com",
     "password": "password123",
     "location": "San Francisco, CA",
     "points": 100,
     "skillsTaught": ["JavaScript", "React"],
     "skillsToLearn": ["UI/UX Design"]
   }
   ```

3. **Get Skills:**
   - GET: http://localhost:8081/api/skills
   - Should return: `[]`

### 5.2 Test Frontend

1. Open the SkillBarter application
2. Navigate through all panels:
   - Dashboard
   - Marketplace
   - Matchmaking
   - Messages
   - Sessions
   - Wallet
   - Profile
   - Settings

3. Try User Management:
   - Go to Profile panel
   - Register a new user (if backend is running)

---

## 🐛 Troubleshooting

### Backend Issues

**Port 8081 already in use:**
```properties
# Change port in application.properties
server.port=8082
```
(Note: Default port is now 8081)

**Database connection error:**
- Check MySQL is running: `mysql -u root -p`
- Verify database exists: `SHOW DATABASES;`
- Check username/password in `application.properties`
- Make sure database name is `skillbarter`

**ClassNotFoundException:**
- Make sure you've run `mvn clean install` first
- Rebuild the project

### Frontend Issues

**ClassNotFoundException:**
- Make sure you're compiling from the correct directory
- Check package structure matches folder structure
- Use: `javac -cp . Main.java` from src directory

**UI not appearing:**
- Check console for errors
- Make sure all UI files are compiled
- Try running from IDE instead

**Cannot connect to backend:**
- Verify backend is running: http://localhost:8081/api/users
- Check CORS is enabled (it is by default)
- Verify port matches (default: 8081)

---

## 📝 Quick Start Checklist

- [ ] MySQL installed and running
- [ ] Database `skillbarter` created
- [ ] `application.properties` configured with correct credentials
- [ ] Backend compiles (`mvn clean install`)
- [ ] Backend runs successfully (shows "Started BackendApplication")
- [ ] Backend responds at http://localhost:8081/api/users
- [ ] Frontend compiles without errors
- [ ] Frontend window opens
- [ ] All UI panels load correctly

---

## 🎯 Next Steps

Once everything is working:

1. **Create Test Data:**
   - Register users through the UI
   - Create skills via API or add API integration
   - Test the matchmaking system

2. **Complete API Integration:**
   - Connect frontend panels to backend endpoints
   - Add error handling
   - Add loading states

3. **Enhance Features:**
   - Add authentication/authorization
   - Implement real-time messaging
   - Add file uploads for profile pictures

---

## 🆘 Still Having Issues?

1. **Check Java Version:**
   ```bash
   java -version  # Should show 21
   ```

2. **Check MySQL Version:**
   ```bash
   mysql --version
   ```

3. **Check Backend Logs:**
   - Look for errors in the console
   - Check Spring Boot startup logs

4. **Check Frontend Console:**
   - If using IDE, check the Run/Debug console
   - Look for stack traces

---

## 📞 Summary

**Backend:**
```bash
cd backend
mvnw.cmd spring-boot:run
# Verify: http://localhost:8081/api/users
```

**Frontend:**
```bash
cd SkillBarter
# Open in IntelliJ and run Main.java
# Or compile with javac and run with java
```

**Both should run simultaneously!** ✅

---

Good luck! 🚀

