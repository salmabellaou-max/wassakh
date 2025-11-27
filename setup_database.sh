#!/bin/bash
# MyWelly - Database Setup Script

echo "========================================="
echo "MyWelly - Database Setup"
echo "========================================="
echo ""

# Check if SQL schema file exists
if [ ! -f "database_schema.sql" ]; then
    echo "ERROR: database_schema.sql not found!"
    exit 1
fi

echo "This will create the medical_db database and all tables."
echo "If the database already exists, all data will be lost!"
echo ""
read -p "Continue? (y/n): " -n 1 -r
echo ""

if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Aborted."
    exit 0
fi

echo ""
echo "Setting up database..."
mysql -u root -pLock1982? < database_schema.sql

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✅ Database setup successful!"
    echo "========================================="
    echo ""
    echo "Database: medical_db"
    echo ""
    echo "Tables created:"
    mysql -u root -pLock1982? -e "USE medical_db; SHOW TABLES;"
    echo ""
    echo "Sample data has been inserted for testing."
    echo ""
    echo "Next steps:"
    echo "1. Compile: ./compile.sh"
    echo "2. Run: ./run.sh"
    echo ""
else
    echo ""
    echo "========================================="
    echo "❌ Database setup failed!"
    echo "========================================="
    echo ""
    echo "Please check MySQL connection and credentials"
    echo ""
    exit 1
fi
