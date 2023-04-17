# POC_project
POC (Proof of Concept) Project: Register and Login System with Rest CRUD

This is a Spring Boot application with basic user authentication and management functionality. The application allows users to register, login, view and edit their profile, and view a list of all registered users with their associated roles. It includes a simple frontend with HTML forms to interact with the backend services.

## Features

User registration with email validation
Login functionality with email and password
User profile viewing and editing
List of all registered users with roles
User deletion (both self and by admin)
Role-based access control

## Technologies

Java 8
Spring Boot 2.7.10
Spring Security
Thymeleaf
Hibernate
MySQL

## Prerequisites
Make sure you have the following software installed on your system:

Java 8
Maven 3.6 or later
MySQL 8.0 or later

## Usage

Visit /register to create a new account using the registration form.
Visit /login to log in with an existing account using the login form.
Visit /user to view your user profile.
Visit /user/edit to edit your user profile using the profile editing form.
Visit /users to view a list of all registered users with roles (requires admin role).
Visit /users/edit?email=example@example.com to edit a user's profile using the user editing form (requires admin role).
