-- MyWelly Medical Application Database Schema
-- Database: medical_db
-- This schema creates all necessary tables for the MyWelly medical appointment system

CREATE DATABASE IF NOT EXISTS medical_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medical_db;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS medical_records;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS verification_codes;
DROP TABLE IF EXISTS certificates;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS laboratories;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS users;

-- Users Table (Base table for all user types)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    user_type ENUM('PATIENT', 'DOCTOR', 'LABORATORY') NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    account_locked BOOLEAN DEFAULT FALSE,
    failed_login_attempts INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_email (email),
    INDEX idx_user_type (user_type),
    INDEX idx_is_verified (is_verified)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Verification Codes Table (for email verification and password reset)
CREATE TABLE verification_codes (
    code_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    code VARCHAR(10) NOT NULL,
    purpose ENUM('SIGNUP', 'PASSWORD_RESET', 'EMAIL_CHANGE') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_code (code),
    INDEX idx_user_purpose (user_id, purpose),
    INDEX idx_expires (expires_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Patients Table
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    id_number VARCHAR(50) UNIQUE,
    phone_number VARCHAR(20),
    personal_email VARCHAR(100),
    no_show_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_full_name (full_name),
    INDEX idx_date_of_birth (date_of_birth)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Doctors Table
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    clinic_name VARCHAR(150),
    address TEXT,
    city VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    consultation_fees DECIMAL(10, 2) NOT NULL,
    years_of_experience INT DEFAULT 0,
    bio TEXT,
    rating DECIMAL(3, 2) DEFAULT 0.00,
    review_count INT DEFAULT 0,
    is_accepting_patients BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_specialty (specialty),
    INDEX idx_city (city),
    INDEX idx_rating (rating),
    INDEX idx_consultation_fees (consultation_fees),
    INDEX idx_accepting (is_accepting_patients)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Certificates Table (for doctors)
CREATE TABLE certificates (
    cert_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    name VARCHAR(200) NOT NULL,
    issuing_organization VARCHAR(200),
    issue_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
    INDEX idx_doctor (doctor_id),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Laboratories Table
CREATE TABLE laboratories (
    laboratory_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    laboratory_name VARCHAR(150) NOT NULL,
    location TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    services_offered TEXT,
    rating DECIMAL(3, 2) DEFAULT 0.00,
    review_count INT DEFAULT 0,
    accepts_walk_ins BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_city (city),
    INDEX idx_rating (rating),
    INDEX idx_laboratory_name (laboratory_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Appointments Table
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    provider_id INT NOT NULL,
    provider_type ENUM('DOCTOR', 'LABORATORY') NOT NULL,
    date_time DATETIME NOT NULL,
    reason TEXT,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED_BY_PATIENT', 'CANCELLED_BY_DOCTOR', 'NO_SHOW') DEFAULT 'SCHEDULED',
    cancellation_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    INDEX idx_patient (patient_id),
    INDEX idx_provider (provider_id, provider_type),
    INDEX idx_date_time (date_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Medical Records Table
CREATE TABLE medical_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    appointment_id INT,
    record_type ENUM('CONSULTATION_REPORT', 'LAB_RESULT', 'PRESCRIPTION', 'DIAGNOSIS', 'RADIOLOGY_REPORT') NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    file_url VARCHAR(500),
    added_by_type ENUM('DOCTOR', 'LABORATORY', 'PATIENT') NOT NULL,
    added_by_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE SET NULL,
    INDEX idx_patient (patient_id),
    INDEX idx_appointment (appointment_id),
    INDEX idx_record_type (record_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Reviews Table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    provider_id INT NOT NULL,
    provider_type ENUM('DOCTOR', 'LABORATORY') NOT NULL,
    appointment_id INT,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    feedback TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE SET NULL,
    INDEX idx_provider (provider_id, provider_type),
    INDEX idx_patient (patient_id),
    INDEX idx_rating (rating),
    INDEX idx_created_at (created_at),
    UNIQUE KEY unique_appointment_review (appointment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Triggers to update doctor/laboratory ratings when reviews are added
DELIMITER //

CREATE TRIGGER update_doctor_rating_after_insert
AFTER INSERT ON reviews
FOR EACH ROW
BEGIN
    IF NEW.provider_type = 'DOCTOR' THEN
        UPDATE doctors
        SET rating = (
            SELECT AVG(rating)
            FROM reviews
            WHERE provider_id = NEW.provider_id AND provider_type = 'DOCTOR'
        ),
        review_count = (
            SELECT COUNT(*)
            FROM reviews
            WHERE provider_id = NEW.provider_id AND provider_type = 'DOCTOR'
        )
        WHERE doctor_id = NEW.provider_id;
    END IF;
END//

CREATE TRIGGER update_laboratory_rating_after_insert
AFTER INSERT ON reviews
FOR EACH ROW
BEGIN
    IF NEW.provider_type = 'LABORATORY' THEN
        UPDATE laboratories
        SET rating = (
            SELECT AVG(rating)
            FROM reviews
            WHERE provider_id = NEW.provider_id AND provider_type = 'LABORATORY'
        ),
        review_count = (
            SELECT COUNT(*)
            FROM reviews
            WHERE provider_id = NEW.provider_id AND provider_type = 'LABORATORY'
        )
        WHERE laboratory_id = NEW.provider_id;
    END IF;
END//

DELIMITER ;

-- Insert Moroccan cities for the filter
CREATE TABLE moroccan_cities (
    city_id INT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL UNIQUE,
    region VARCHAR(100),
    INDEX idx_city_name (city_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert major Moroccan cities
INSERT INTO moroccan_cities (city_name, region) VALUES
('Casablanca', 'Casablanca-Settat'),
('Rabat', 'Rabat-Salé-Kénitra'),
('Fès', 'Fès-Meknès'),
('Marrakech', 'Marrakech-Safi'),
('Agadir', 'Souss-Massa'),
('Tangier', 'Tanger-Tétouan-Al Hoceïma'),
('Meknès', 'Fès-Meknès'),
('Oujda', 'Oriental'),
('Kenitra', 'Rabat-Salé-Kénitra'),
('Tétouan', 'Tanger-Tétouan-Al Hoceïma'),
('Safi', 'Marrakech-Safi'),
('Salé', 'Rabat-Salé-Kénitra'),
('Mohammedia', 'Casablanca-Settat'),
('Khouribga', 'Béni Mellal-Khénifra'),
('El Jadida', 'Casablanca-Settat'),
('Béni Mellal', 'Béni Mellal-Khénifra'),
('Nador', 'Oriental'),
('Taza', 'Fès-Meknès'),
('Settat', 'Casablanca-Settat'),
('Ksar El Kebir', 'Tanger-Tétouan-Al Hoceïma'),
('Larache', 'Tanger-Tétouan-Al Hoceïma'),
('Khemisset', 'Rabat-Salé-Kénitra'),
('Guelmim', 'Guelmim-Oued Noun'),
('Berrechid', 'Casablanca-Settat'),
('Oued Zem', 'Béni Mellal-Khénifra'),
('Sidi Slimane', 'Rabat-Salé-Kénitra'),
('Errachidia', 'Drâa-Tafilalet'),
('Sidi Kacem', 'Rabat-Salé-Kénitra'),
('Taourirt', 'Oriental'),
('Youssoufia', 'Marrakech-Safi'),
('Taroudant', 'Souss-Massa'),
('Essaouira', 'Marrakech-Safi'),
('Oulad Teima', 'Souss-Massa'),
('Tiznit', 'Souss-Massa'),
('Tan-Tan', 'Guelmim-Oued Noun'),
('Laâyoune', 'Laâyoune-Sakia El Hamra'),
('Dakhla', 'Dakhla-Oued Ed-Dahab');

-- Sample data for testing (optional)
-- Create a test doctor account
INSERT INTO users (user_name, email, password_hash, phone_number, user_type, is_verified, is_active)
VALUES ('dr.smith', 'dr.smith@mywelly.com', '$2a$10$XCzZyqQIGPYb0sJKvJiKFeZQZhGSP.xYW8M0YB9QlGJRXCm3KxZOy', '+212600000001', 'DOCTOR', TRUE, TRUE);

INSERT INTO doctors (user_id, full_name, specialty, clinic_name, address, city, phone_number, consultation_fees, years_of_experience, bio, is_accepting_patients)
VALUES (
    LAST_INSERT_ID(),
    'Dr. Ahmed Smith',
    'Cardiology',
    'Heart Care Clinic',
    '123 Health Street',
    'Casablanca',
    '+212600000001',
    500.00,
    10,
    'Experienced cardiologist specializing in preventive cardiology and heart disease treatment.',
    TRUE
);

-- Create a test patient account
INSERT INTO users (user_name, email, password_hash, phone_number, user_type, is_verified, is_active)
VALUES ('patient1', 'patient1@example.com', '$2a$10$XCzZyqQIGPYb0sJKvJiKFeZQZhGSP.xYW8M0YB9QlGJRXCm3KxZOy', '+212600000002', 'PATIENT', TRUE, TRUE);

INSERT INTO patients (user_id, full_name, date_of_birth, gender, id_number, phone_number, personal_email)
VALUES (
    LAST_INSERT_ID(),
    'Mohammed Test Patient',
    '1990-05-15',
    'Male',
    'AB123456',
    '+212600000002',
    'patient1@example.com'
);

-- Grant privileges (adjust username as needed)
-- GRANT ALL PRIVILEGES ON medical_db.* TO 'root'@'localhost';
-- FLUSH PRIVILEGES;
