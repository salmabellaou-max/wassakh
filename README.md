# MyWelly - Healthcare Application

MyWelly is a comprehensive healthcare management application built with Java that provides authentication, appointment management, medical records, and doctor search functionality.

## Project Structure

```
wassakh/
├── bin/              # Compiled Java class files (.class)
├── docs/             # Documentation
├── lib/              # External libraries/dependencies
├── README.md         # This file
└── run.sh           # Script to run the application
```

## Features

Based on the compiled classes, MyWelly includes the following features:

### Core Modules
- **Authentication System**: Login, signup, and verification code management
- **User Management**: Support for different user types (Patient, Doctor, Laboratory)
- **Dashboard**: Main application dashboard
- **Profile Service**: User profile management

### Medical Features
- **Appointment Management**: Schedule and manage medical appointments
- **Medical Records**: Store and manage patient medical records (certificates, lab results)
- **Doctor Search**: Search for healthcare providers with filters
- **Review System**: Rate and review healthcare providers
- **Laboratory Integration**: Laboratory services and results

### UI Components
- Custom splash screen with logo
- Modern UI with constants and helper utilities
- Window-based interface (Login, Signup, Dashboard, Search)

## Technical Details

- **Language**: Java
- **Compiled Version**: Java 25 (Class file version 69.0) - *Preview/Early Access*
- **Architecture**: Swing-based GUI application
- **Database**: Uses DatabaseConnection class for data persistence

## Important Notes

### Java Version Compatibility Issue ⚠️

**CRITICAL**: The compiled class files in this repository were built with Java class file version 69.0, which corresponds to a preview/early access version of Java (likely Java 25 EA).

**Current System**: Java 21 (supports class file version up to 65.0)

**This means the application CANNOT run on the current system without:**
1. Installing Java 25 (early access/preview version), OR
2. Obtaining the source `.java` files and recompiling with Java 21

### Missing Source Code

This repository currently contains **only compiled .class files**. For best practices and maintainability, you should:
- Add the original `.java` source files
- Use a build tool (Maven or Gradle)
- Recompile with a stable Java version (Java 17 or 21 LTS recommended)

## How to Run

### Prerequisites

To run this application, you need **Java 25 (Early Access)** or later installed.

Check your Java version:
```bash
java -version
```

### Installation

If you need to install Java 25 EA:
1. Visit [Oracle JDK Early Access Downloads](https://jdk.java.net/)
2. Download Java 25 EA for your platform
3. Install and set JAVA_HOME environment variable

### Running the Application

#### Option 1: Using the run script (when Java 25+ is available)
```bash
chmod +x run.sh
./run.sh
```

#### Option 2: Manual execution (when Java 25+ is available)
```bash
cd bin
java MyWellyCompleteApp
```

## Main Entry Points

The application has the following potential entry points:
- **MyWellyCompleteApp** - Main application entry point (recommended)
- **MyWellyApp** - Alternative entry point
- **LoginWindow** - Direct login window
- **MyWellySplashScreen** - Splash screen only

## Database Configuration

The application uses a DatabaseConnection class. You may need to configure:
- Database URL
- Database credentials
- Database driver

Check the application documentation or configuration files for database setup details.

## Development Recommendations

To properly maintain and develop this application:

1. **Obtain Source Code**: Get the original .java source files
2. **Setup Build System**: Use Maven or Gradle
3. **Target Stable Java**: Recompile for Java 21 LTS
4. **Add Version Control**: Already using Git ✓
5. **Add Tests**: Include unit and integration tests
6. **Configuration Management**: Externalize database and app configuration
7. **Documentation**: Add JavaDoc to source files

## Project Status

- ✅ Repository reorganized with proper structure
- ⚠️ Java version compatibility issue identified
- ❌ Source code not available
- ❌ Cannot run on standard Java installations (requires Java 25 EA)

## Next Steps

1. Locate the original `.java` source files
2. Add source files to a `src/` directory
3. Setup Maven or Gradle build configuration
4. Recompile with Java 21 LTS
5. Add proper configuration management
6. Create comprehensive documentation

## License

Please add license information.

## Contributors

Please add contributor information.
