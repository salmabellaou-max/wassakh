#!/bin/bash
# MyWelly - Run Script

echo "========================================="
echo "MyWelly - Starting Application"
echo "========================================="
echo ""

# Check if compiled classes exist
if [ ! -d "bin" ]; then
    echo "ERROR: bin directory not found!"
    echo "Please run ./compile.sh first"
    exit 1
fi

# Check database connection
echo "Testing database connection..."
mysql -u root -pLock1982? -e "USE medical_db; SHOW TABLES;" > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "✅ Database connection successful"
    echo ""
else
    echo "⚠️  WARNING: Could not connect to database"
    echo "Please ensure MySQL is running and database is created"
    echo "Run: mysql -u root -p < database_schema.sql"
    echo ""
fi

# Run the application
echo "Starting MyWelly application..."
echo ""
java -cp "bin:lib/*" MyWellyCompleteApp
