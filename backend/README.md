# SkillBarter Backend

## Quick Start

### Option 1: Use MySQL (Recommended for production)
1. Make sure MySQL is installed and running
2. Create database: `CREATE DATABASE skillbarter;`
3. Update credentials in `src/main/resources/application.properties` if needed
4. Run: `mvnw spring-boot:run` or run `BackendApplication.java` in IDE

### Option 2: Use H2 In-Memory Database (Easy for testing)
1. Uncomment H2 dependency in `pom.xml`
2. In `application.properties`, comment out MySQL lines and uncomment H2 lines
3. Run the application

### Current Configuration
- **Port**: 8081
- **Database**: MySQL (localhost:3306/skillbarter)
- **Username**: root
- **Password**: Root@12345

Update these in `application.properties` if your setup is different.

