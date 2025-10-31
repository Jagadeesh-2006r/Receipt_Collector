# Campus Placement Management System

A comprehensive web application for managing campus placement activities built with Spring Boot and Thymeleaf.

## Features

### User Management Module
- Role-based access control (Students, Placement Officers, Recruiters)
- User registration and authentication
- Profile management

### Job Management Module
- Job posting by recruiters
- Eligibility criteria filtering
- Application deadline management

### Application Module
- Student job applications
- Resume upload functionality
- Application status tracking

### Interview Management Module
- Interview scheduling
- Multiple interview rounds
- Status tracking and feedback

### Reporting Module
- Placement statistics
- Company-wise reports
- Department-wise analysis

## Technology Stack

- **Backend**: Spring Boot 3.5.7, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 17

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Install MySQL and create a database named `placement_db`
2. Update database credentials in `application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`

## Default Login Credentials

### Placement Officer
- Email: officer@college.edu
- Password: password123

### Student
- Email: student@college.edu
- Password: password123

### Recruiter
- Email: recruiter@company.com
- Password: password123

## System Modules

### 1. Authentication & Authorization
- Login/Logout functionality
- Role-based dashboard routing
- Session management

### 2. Student Module
- Profile management
- Job browsing and application
- Application status tracking

### 3. Recruiter Module
- Job posting and management
- Application review
- Candidate shortlisting

### 4. Placement Officer Module
- Overall system management
- Application processing
- Interview scheduling
- Report generation

### 5. Interview Management
- Schedule interviews for shortlisted candidates
- Track interview rounds and results
- Manage interview feedback

### 6. Reporting & Analytics
- Placement statistics
- Company-wise placement data
- Success rate analysis

## API Endpoints

### Authentication
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Login processing
- `GET /register` - Registration page
- `POST /register` - Registration processing
- `GET /logout` - Logout

### Dashboard
- `GET /dashboard` - Role-based dashboard

### Jobs
- `GET /jobs` - List jobs
- `GET /jobs/create` - Create job form (Recruiters)
- `POST /jobs/create` - Create job
- `GET /jobs/{id}` - Job details

### Applications
- `GET /applications` - List applications
- `POST /applications/apply/{jobId}` - Apply for job
- `POST /applications/{id}/status` - Update application status

### Interviews
- `GET /interviews` - List interviews
- `GET /interviews/schedule/{applicationId}` - Schedule interview form
- `POST /interviews/schedule` - Schedule interview
- `POST /interviews/{id}/status` - Update interview status

### Students
- `GET /students` - List students (Officers)
- `GET /students/profile` - Student profile
- `POST /students/profile` - Update profile

### Reports
- `GET /reports` - Placement reports (Officers)

## Database Schema

### Users Table
- id, email, password, fullName, phone, role
- Student fields: rollNumber, cgpa, resumePath, department_id
- Recruiter fields: companyName, designation

### Jobs Table
- id, title, companyName, description, requirements
- location, salary, jobType, minCgpa, eligibleDepartments
- passingYear, applicationDeadline, recruiter_id

### Applications Table
- id, job_id, student_id, appliedAt, status, coverLetter

### Interviews Table
- id, application_id, round, scheduledAt, venue
- interviewerName, interviewerEmail, status, feedback, score

### Placements Table
- id, student_id, job_id, offerSalary, offerLetter
- placedAt, accepted

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.