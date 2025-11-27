#!/bin/bash
# MyWelly - Compilation Script

echo "========================================="
echo "MyWelly - Compiling Source Files"
echo "========================================="
echo ""

# Check if lib directory exists
if [ ! -d "lib" ]; then
    echo "ERROR: lib directory not found!"
    echo "Please run ./setup_dependencies.sh first"
    exit 1
fi

# Check if source directory exists
if [ ! -d "src" ]; then
    echo "ERROR: src directory not found!"
    exit 1
fi

# Create bin directory for compiled classes
echo "Creating bin directory..."
mkdir -p bin

# Count source files
SOURCE_COUNT=$(find src -name "*.java" | wc -l)
echo "Found $SOURCE_COUNT Java source files"
echo ""

# Compile all Java files
echo "Compiling..."
javac -d bin -cp "lib/*" src/*.java

# Check compilation result
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✅ Compilation successful!"
    echo "========================================="
    echo ""
    echo "Compiled classes:"
    ls -1 bin/*.class | wc -l
    echo ""
    echo "To run the application:"
    echo "  ./run.sh"
    echo ""
else
    echo ""
    echo "========================================="
    echo "❌ Compilation failed!"
    echo "========================================="
    echo ""
    echo "Please check the error messages above"
    echo ""
    exit 1
fi
