# Install Maven - Quick Guide

## Step 1: Download Maven
1. Go to: https://maven.apache.org/download.cgi
2. Download: **apache-maven-3.9.6-bin.zip** (or latest 3.9.x)
3. Extract to: `C:\Program Files\Apache\maven` (or any location)

## Step 2: Add to PATH
1. Win + X → **System** → **Advanced System Settings**
2. Click **Environment Variables**
3. Under **System Variables**, find and select **Path**, click **Edit**
4. Click **New** and add: `C:\Program Files\Apache\maven\bin`
5. Click **OK** on all dialogs

## Step 3: Verify
Open new Command Prompt:
```bash
mvn -version
```
Should show Maven version info.

## Step 4: Use Maven
Now you can use `mvn` instead of `mvnw.cmd`:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

