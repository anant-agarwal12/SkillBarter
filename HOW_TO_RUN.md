# 🚀 How to Run SkillBarter Project

This project consists of two parts:
1. **Frontend**: Java Swing Desktop Application
2. **Backend**: Spring Boot REST API with MySQL Database

---

## 📋 Prerequisites

1. **Java 21** or higher (required for Spring Boot 3.5.7)
2. **MySQL Database** (for backend)
3. **Maven** (for building the backend, or use Maven Wrapper)
4. **IDE** (IntelliJ IDEA recommended, or any Java IDE)

---

## 🗄️ Database Setup (Backend)

### Step 1: Install and Start MySQL
Make sure MySQL is installed and running on your machine.

### Step 2: Create Database
```sql
CREATE DATABASE skillbarter;
```

### Step 3: Update Database Credentials (if needed)
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## 🔧 Running the Backend (Spring Boot)

### Option 1: Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Navigate to `backend/src/main/java/com/skillbarter/backend/BackendApplication.java`
3. Right-click on the file → **Run 'BackendApplication'**
4. The backend will start on **http://localhost:8081**

### Option 2: Using Maven Command Line
```bash
# Navigate to backend directory
cd backend

# Run using Maven Wrapper (Windows)
mvnw.cmd spring-boot:run

# Or if you have Maven installed
mvn spring-boot:run
```

### Option 3: Build and Run JAR
```bash
cd backend
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

**✅ Backend is running when you see:**
```
Tomcat started on port(s): 8081 (http)
Started BackendApplication in X.XXX seconds
```

---

## 🖥️ Running the Frontend (Swing Application)

### Option 1: Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Navigate to `SkillBarter/src/Main.java`
3. Right-click on the file → **Run 'Main'**
4. The UI window will open!

### Option 2: Using Command Line
```bash
# Navigate to SkillBarter directory
cd SkillBarter

# Compile the project
javac -d out/production/SkillBarter -sourcepath src src/Main.java src/ui/**/*.java src/logic/**/*.java

# Run the application
java -cp out/production/SkillBarter Main
```

### Option 3: Compile and Run with Package Structure
```bash
cd SkillBarter/src

# Compile
javac Main.java ui/**/*.java logic/**/*.java

# Run
java Main
```

**✅ Frontend is running when the SkillBarter window appears!**

---

## 🎯 Quick Start Guide

### Full Setup (First Time)
1. **Setup MySQL Database:**
   ```sql
   CREATE DATABASE skillbarter;
   ```

2. **Update Database Password** in `backend/src/main/resources/application.properties`

3. **Start Backend First:**
   - Run `BackendApplication.java` from IDE, OR
   - Run `cd backend && mvnw.cmd spring-boot:run`

4. **Start Frontend:**
   - Run `Main.java` from IDE, OR
   - Compile and run with `java Main`

---

## 🔍 Verifying Everything Works

### Backend Check:
- Open browser: **http://localhost:8081/api/users**
- Should return `[]` (empty array) if no users yet

### Frontend Check:
- The SkillBarter window should open
- Try navigating between panels (Home, About, Services, etc.)
- Try User Management → Register a user
- Calendar should open when clicking scheduling features

---

## ⚠️ Troubleshooting

### Backend Issues:
- **Port 8081 already in use**: Change port in `application.properties`:
  ```properties
  server.port=8082
  ```
  (Note: Default port is now 8081)
- **Database connection error**: 
  - Check MySQL is running
  - Verify database name is `skillbarter`
  - Check username/password in `application.properties`

### Frontend Issues:
- **ClassNotFoundException**: Make sure you're compiling with the correct source path
- **UI not appearing**: Check console for errors, ensure all UI files are compiled

---

## 📝 Notes

- **Backend must run first** for User Management features to work
- The frontend can run standalone to view the UI, but registration won't work without backend
- MySQL database will auto-create tables on first run (due to `spring.jpa.hibernate.ddl-auto=update`)

---

## 🎨 Enjoy Your Ultra Modern UI! ⚡

The UI features glassmorphism effects, animated backgrounds, and smooth transitions!

