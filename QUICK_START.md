# ⚡ Quick Start Guide - 5 Minutes Setup

## 1. Database Setup (2 minutes)

```sql
-- Open MySQL Command Line or MySQL Workbench
CREATE DATABASE skillbarter;
```

**Edit:** `backend/src/main/resources/application.properties`
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

## 2. Start Backend (1 minute)

```bash
cd backend
mvnw.cmd spring-boot:run
```

✅ Wait for: "Started BackendApplication"
✅ Test: http://localhost:8081/api/users (should show `[]`)

## 3. Start Frontend (2 minutes)

**In IntelliJ IDEA:**
1. Open `SkillBarter` folder
2. Run `SkillBarter/src/Main.java`

**Or Command Line:**
```bash
cd SkillBarter
javac -sourcepath src src/Main.java
java -cp src Main
```

✅ UI window should open!

## Done! 🎉

Both are running:
- Backend: http://localhost:8081
- Frontend: Java Swing window

---

**Full details:** See `SETUP_GUIDE.md`

