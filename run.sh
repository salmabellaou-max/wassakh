#!/bin/bash

# MyWelly Application Launcher
# This script runs the MyWelly healthcare application

echo "======================================"
echo "    MyWelly Healthcare Application    "
echo "======================================"
echo ""

# Check Java version
echo "Checking Java version..."
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)

if [ -z "$JAVA_VERSION" ]; then
    echo "❌ ERROR: Java is not installed or not in PATH"
    echo ""
    echo "Please install Java 25 or later to run this application."
    echo "Download from: https://jdk.java.net/"
    exit 1
fi

echo "Java version detected: $JAVA_VERSION"
echo ""

# Check if Java version is sufficient
# Note: Class files are compiled with version 69.0 (Java 25 EA)
JAVA_MAJOR=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}' | awk -F '.' '{print $1}')

if [ "$JAVA_MAJOR" -lt 25 ]; then
    echo "⚠️  WARNING: Java version compatibility issue!"
    echo ""
    echo "Your Java version: $JAVA_MAJOR"
    echo "Required version: 25 or higher"
    echo ""
    echo "The class files were compiled with Java 25 (class file version 69.0)"
    echo "and cannot run on Java $JAVA_MAJOR (max class file version: $((JAVA_MAJOR + 44)).0)"
    echo ""
    echo "To run this application, you need to either:"
    echo "  1. Install Java 25 EA from https://jdk.java.net/"
    echo "  2. Obtain the source code and recompile with your current Java version"
    echo ""
    exit 1
fi

echo "✓ Java version is compatible"
echo ""

# Change to bin directory
cd "$(dirname "$0")/bin" || {
    echo "❌ ERROR: Cannot find bin directory"
    exit 1
}

# Run the application
echo "Launching MyWelly..."
echo "======================================"
echo ""

java MyWellyCompleteApp

# Capture exit code
EXIT_CODE=$?

echo ""
echo "======================================"
if [ $EXIT_CODE -eq 0 ]; then
    echo "Application exited normally"
else
    echo "Application exited with error code: $EXIT_CODE"
fi

exit $EXIT_CODE
