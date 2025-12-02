Online Reservation System (Java + JDBC + MySQL)

A simple console-based Reservation Management System built using Java, JDBC, and MySQL.
This project allows users to log in, book train tickets, view ticket details, and cancel reservations.

Perfect for Oasis Infobyte Internship tasks, Java mini-projects, and JDBC practice.

📌 Features
🔐 1. User Login

Secure login using database validation

Credentials are validated from the users table

📝 2. Make Reservation

Takes user inputs:

Passenger Name

Train Number & Train Name

Class Type

Date of Journey

From → To Stations

Generates a unique PNR number

Stores data into reservations table

Displays ticket details after successful booking

🎫 3. Print Ticket

Fetches reservation details using PNR

Shows complete ticket information

❌ 4. Cancel Reservation

User enters PNR

System verifies PNR

Displays ticket details

Deletes entry from database after confirmation

🗄️ Database Setup
1. Create Database
CREATE DATABASE reservation_system;
USE reservation_system;

2. Create Users Table
CREATE TABLE users (
    login_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);

3. Create Reservations Table
CREATE TABLE reservations (
    pnr VARCHAR(20) PRIMARY KEY,
    passenger_name VARCHAR(100),
    train_number VARCHAR(20),
    train_name VARCHAR(100),
    class_type VARCHAR(20),
    date_of_journey VARCHAR(20),
    from_station VARCHAR(50),
    to_station VARCHAR(50)
);

4. Insert a Test User
INSERT INTO users VALUES ("admin", "admin123");

🛠️ Technologies Used

Java

JDBC (MySQL Connector)

MySQL Database

OOP Concepts

Exception Handling

▶️ How to Run the Project
Step 1: Install Requirements

Java JDK 8+

MySQL Server

MySQL Connector JAR

Step 2: Add MySQL Connector

Place the mysql-connector-j.jar file into your project library.

Step 3: Update Database Credentials

In the DB class:

DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/reservation_system",
    "root",
    "your_password"
);

Step 4: Run the Program

Compile and run:

javac OnlineReservationSystem.java
java OnlineReservationSystem

📂 Project Structure
OnlineReservationSystem.java
DB.java
README.md

🧪 Sample Login
Login ID  : admin
Password  : admin123

📸 Sample Output
========= ONLINE RESERVATION SYSTEM =========

Enter Login ID: admin
Enter Password: admin123

Login Successful!

1. Make Reservation
2. Cancel Reservation
3. Exit
Select Option:

👨‍💻 Author

Ruchi
Java Developer | OOPS | JDBC | MySQL