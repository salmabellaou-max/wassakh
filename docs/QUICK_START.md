# Quick Start Guide - MyWelly Healthcare Application

## System Requirements

- **Java Runtime**: Java 25 or higher (Early Access version required)
- **Operating System**: Linux, macOS, or Windows
- **Memory**: Minimum 512MB RAM recommended

## Current Status

⚠️ **IMPORTANT**: This application cannot run on standard Java installations.

The compiled class files require Java 25 (class file version 69.0), which is currently an early access release. Most systems have Java 17 or 21 installed, which are incompatible with these class files.

## Installation Steps

### Step 1: Check Your Java Version

```bash
java -version
```

You should see output like:
```
openjdk version "25-ea" ...
```

### Step 2: Install Java 25 EA (if needed)

If you don't have Java 25:

1. Visit https://jdk.java.net/
2. Download Java 25 Early Access Build
3. Extract to a directory (e.g., `/opt/jdk-25`)
4. Set JAVA_HOME:
   ```bash
   export JAVA_HOME=/opt/jdk-25
   export PATH=$JAVA_HOME/bin:$PATH
   ```

### Step 3: Run the Application

```bash
cd /home/user/wassakh
./run.sh
```

Or manually:
```bash
cd /home/user/wassakh/bin
java MyWellyCompleteApp
```

## Alternative: Recompile with Source Code

If you have the original `.java` source files:

1. Create a `src/` directory
2. Place all `.java` files in `src/`
3. Compile with your current Java version:
   ```bash
   javac -d bin src/*.java
   ```
4. Run the application:
   ```bash
   cd bin
   java MyWellyCompleteApp
   ```

## Troubleshooting

### Error: UnsupportedClassVersionError

```
java.lang.UnsupportedClassVersionError: MyWellyCompleteApp has been compiled
by a more recent version of the Java Runtime (class file version 69.0)
```

**Solution**: Install Java 25 EA or recompile the source code with your current Java version.

### Error: ClassNotFoundException

**Solution**: Make sure you're running the command from the `bin/` directory or using the `run.sh` script.

### Error: NoClassDefFoundError

**Solution**: Missing dependencies. Check if external JAR files are needed in the `lib/` directory.

## Application Features

Once running, you should see:

1. **Splash Screen**: MyWelly logo and loading animation
2. **Login Window**: Enter credentials or sign up
3. **Dashboard**: Main application interface with:
   - Appointment management
   - Medical records
   - Doctor search
   - Profile settings

## Database Setup

The application may require database configuration. Check for:
- Configuration files (`.properties`, `.xml`, `.yml`)
- Database connection settings
- Required database schema

## Getting Help

If you encounter issues:

1. Check the main [README.md](../README.md)
2. Verify Java version compatibility
3. Ensure all class files are in the `bin/` directory
4. Check for missing dependencies in `lib/`

## Known Limitations

- No source code available in repository
- Requires Java 25 EA (not widely available)
- Database configuration not documented
- No automated build system (Maven/Gradle)

## Recommended Next Steps

1. **Obtain Source Code**: Get the original `.java` files
2. **Use Stable Java**: Recompile for Java 17 or 21 LTS
3. **Add Build Tool**: Setup Maven or Gradle
4. **Configuration**: Externalize database settings
5. **Testing**: Add unit tests and integration tests
