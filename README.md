

## Project:Secure Vault Inc

A web-based secure vault system built with Spring Boot, Thymeleaf, and MySQL that allows users to safely store, manage, and protect sensitive information using encryption.

## Project Overview

Secure Vault Inc is designed to help users:

Store private secrets securely
Encrypt sensitive data before saving
Retrieve and decrypt secrets safely
Manage secrets (Create, Read, Update, Delete)

## Features
 User Authentication (Login & Register)
 Add New Secrets
 View Stored Secrets
 Edit Secrets
 Delete Secrets
 Encryption & Decryption of data
 Session-based access control
 Clean UI using Thymeleaf
 
 
## Technologies Used
Backend: Spring Boot (Java)
Frontend: Thymeleaf + HTML + CSS
Database: MySQL
ORM: JPA (Hibernate)
Security: Custom session-based authentication
Encryption: Custom Encryption Utility

## Core Business Rules
A user must be logged in to access secrets
Each secret belongs to one user
Secret content must be at least 4 characters
All secrets are encrypted before storage
Only the owner can view/edit/delete their secrets
Decryption happens only when displaying data

## Database Design
User Table
| Column   | Type    |
| -------- | ------- |
| id       | BIGINT  |
| username | VARCHAR |
| password | VARCHAR |

Secret Table

| Column     | Type             |
| ---------- | ---------------- |
| id         | BIGINT           |
| title      | VARCHAR          |
| content    | TEXT (encrypted) |
| user_id    | BIGINT (FK)      |
| created_at | DATETIME         |
| updated_at | DATETIME         |

# Group Members

Barinda Systems Sylvere reg:24rp08563
Uwayehova Priscilla     reg:24rp11728

Class: IT C Year 2


