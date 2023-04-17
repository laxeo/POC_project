# POC_project
POC (Proof of Concept) Project: Register and Login System with Rest CRUD

This is a Spring Boot application with basic user authentication and management functionality. The application allows users to register, login, view and edit their profile, and view a list of all registered users with their associated roles. It includes a simple frontend with HTML forms to interact with the backend services.

## Features

### Registration

Users can register a new account by providing their first name, last name, email, and password.

Passwords are encrypted using BCrypt.

Validation is performed to ensure that all fields are non-empty and that the email is a valid email address.

If registration is successful, the user is redirected to the login page.

If registration fails, an error message is displayed indicating the reason for failure.

### Login

Users can log in using their email and password.

Validation is performed to ensure that the email and password are non-empty and that the email is a valid email address.

If login is successful, the user is redirected to their profile page.

If login fails, an error message is displayed indicating the reason for failure.

### Profile

Users can view their first name, last name, email, and role (user or admin) on their profile page.

Users can edit their first name, last name, and password on their profile page.

Users can delete their account on their profile page.

Admin users can view a list of all registered users and their details on the Users page.

Admin users can edit a user's first name, last name, and password on the Users page.

Admin users can delete a user's account on the Users page.

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

Import this project as a Maven Project in STS3 and start as a Spring Boot Application after modifying Application.porperties according to your own MySQL database.

Open a web browser and go to http://localhost:5001/register to register a new user. Enter the required information and click the "Register" button. You should see a message indicating that the registration was successful.

Go to http://localhost:5001/login to log in. Enter the email and password you used to register and click the "Login" button. You should see a message indicating that you are logged in.

Go to http://localhost:5001/user to see your user information. You should see your first name, last name, email, and role.

Click the "Edit Info" button to update your user information. Enter the new information and click the "Update" button. You should see a message indicating that the update was successful.

Click the "Logout" button to log out.

Go to http://localhost:5001/users to see a list of registered users. You should see the first name, last name, email, and role of each user.

Click the "Edit" button next to a user to update their information. Enter the new information and click the "Update" button. You should see a message indicating that the update was successful.

Click the "Delete" button next to a user to delete them. You should see a message indicating that the user was deleted.



