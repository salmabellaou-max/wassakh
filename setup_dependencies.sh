#!/bin/bash
# MyWelly - Dependency Setup Script
# This script downloads all required JAR files for the project

echo "========================================="
echo "MyWelly - Dependency Setup"
echo "========================================="
echo ""

# Create lib directory
echo "Creating lib directory..."
mkdir -p lib

cd lib

echo "Downloading MySQL Connector/J..."
curl -L -o mysql-connector-java.jar \
  "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar"

echo "Downloading FlatLaf (Modern UI)..."
curl -L -o flatlaf.jar \
  "https://repo1.maven.org/maven2/com/formdev/flatlaf/3.2.5/flatlaf-3.2.5.jar"

echo "Downloading BCrypt (Password Hashing)..."
curl -L -o jbcrypt.jar \
  "https://repo1.maven.org/maven2/at/favre/lib/bcrypt/0.10.2/bcrypt-0.10.2.jar"

echo "Downloading JavaMail API..."
curl -L -o javax.mail.jar \
  "https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar"

curl -L -o activation.jar \
  "https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar"

cd ..

echo ""
echo "========================================="
echo "Dependencies downloaded successfully!"
echo "========================================="
echo ""
echo "Downloaded files:"
ls -lh lib/
echo ""
echo "Next steps:"
echo "1. Setup database: mysql -u root -p < database_schema.sql"
echo "2. Compile: ./compile.sh"
echo "3. Run: ./run.sh"
echo ""
