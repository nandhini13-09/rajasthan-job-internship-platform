# CareerConnect Rajasthan – Job & Internship Platform

A runnable starter college project using Java 17, Spring Boot, Thymeleaf and MySQL.

## Features
- Student and company registration/login
- Password hashing with BCrypt
- Student profile with college, department, skills and CGPA
- Company job/internship posting
- Job search by keyword and type
- Student job application and application tracking
- Employer applicant list and status updates
- Skill-based recommendation percentage
- Admin dashboard and job approval screen

## Requirements
- JDK 17+
- Maven 3.8+
- MySQL 8+
- Eclipse / IntelliJ IDEA / VS Code

## Database setup
1. Start MySQL.
2. Create the database:
   `CREATE DATABASE rajasthan_jobs;`
3. Open `src/main/resources/application.properties`.
4. Set your MySQL username and password. Default configuration uses username `root` and blank password.

Spring Boot creates/updates tables automatically when the app starts.

## Run
From the project folder:
`mvn spring-boot:run`

Open: http://localhost:8080/jobs

## Demo admin
- Email: `admin@rajasthan.gov.in`
- Password: `Admin@123`

Register student and company accounts using the Register page. A company can post jobs; a student can apply and see matching scores.

## Suggested demo sequence
1. Login as admin and open Dashboard.
2. Register a company account and post a Java internship.
3. Register a student with skills such as `Java, SQL, HTML` and CGPA `8.2`.
4. Open Jobs and apply.
5. Login as company and view applicants; update status.
6. Login as student and view application status and recommendations.

## Notes / limitations for academic prototype
- This is a functional learning prototype, not a production government system.
- Admin approval route is included; company postings are currently published immediately for easy demonstration. To enforce approval, set `j.setApproved(false)` in `JobController.createJob`.
- Resume upload, email/SMS notifications, college coordinator accounts, CAPTCHA, audit logs and production-grade Spring Security are extension tasks.
- Protect admin credentials and change the demo password before any deployment.
- The skill matching score compares comma-separated required skills with student skills. It is explainable rule-based matching, not an AI model.
