# Campus Placement Management System

A comprehensive web-based placement management portal built with Spring Boot, MySQL, and Thymeleaf that connects students, placement officers, and recruiters to streamline campus recruitment activities.

## 🚀 Features

### 🔐 Authentication & Authorization
- Role-based access control (Student, Recruiter, Placement Officer)
- JWT token-based authentication
- Secure password encryption using BCrypt
- Session management

### 👨‍🎓 Student Features
- **Profile Management**: Complete profile with academic details, CGPA, department, batch
- **Resume Upload**: PDF resume upload and management
- **Job Discovery**: Browse eligible jobs based on profile criteria
- **Application Tracking**: Apply for jobs and track application status
- **Interview Management**: View scheduled interviews and results

### 🏢 Recruiter Features
- **Job Posting**: Create, update, and delete job postings
- **Eligibility Criteria**: Define CGPA, department, and batch requirements
- **Application Review**: View and manage student applications
- **Candidate Shortlisting**: Shortlist or reject applications
- **Analytics Dashboard**: View job posting statistics

### 👨‍💼 Placement Officer Features
- **Application Management**: Oversee all applications across jobs
- **Interview Scheduling**: Schedule interview rounds for shortlisted candidates
- **Interview Management**: Update interview results and feedback
- **Placement Tracking**: Record final placement decisions
- **Comprehensive Reports**: Generate placement statistics and analytics
- **Student Management**: View and manage student profiles

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5.1.3, Font Awesome 6.0
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- MySQL Workbench (optional, for database management)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd campus-placement-system
```

### 2. Database Setup
1. Install MySQL and create a database:
```sql
CREATE DATABASE placement_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placement_db?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📊 Database Schema

### Core Entities
- **users**: User information with role-based access
- **jobs**: Job postings with eligibility criteria
- **applications**: Student job applications
- **interviews**: Interview scheduling and results
- **placements**: Final placement records

### Key Relationships
- One Recruiter → Many Jobs
- One Student → Many Applications
- One Job → Many Applications
- One Job → Many Interviews
- One Student → One Placement Record

## 🔗 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User authentication
- `GET /api/users/{id}` - Get user profile

### Job Management
- `POST /api/jobs` - Create job (Recruiter only)
- `GET /api/jobs` - List all active jobs
- `GET /api/jobs/{id}` - Get job details
- `PUT /api/jobs/{id}` - Update job
- `DELETE /api/jobs/{id}` - Delete job

### Application Management
- `POST /api/applications/apply` - Apply for job (Student only)
- `GET /api/applications/student/{id}` - Get student applications
- `GET /api/applications/job/{id}` - Get job applications
- `PUT /api/applications/{id}/status` - Update application status

## 🎨 User Interface

### Student Dashboard
- Overview of eligible jobs, applications, and interviews
- Quick access to profile management and job browsing
- Application status tracking

### Recruiter Dashboard
- Job posting management
- Application review and shortlisting
- Analytics and statistics

### Placement Officer Dashboard
- Comprehensive system overview
- Interview scheduling and management
- Placement reporting and analytics

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/placement_db
spring.datasource.username=root
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# JWT Configuration
jwt.secret=mySecretKey
jwt.expiration=86400000
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/placement/
│   │   ├── config/          # Security and JWT configuration
│   │   ├── controller/      # REST and Web controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic services
│   │   └── PlacementApplication.java
│   └── resources/
│       ├── static/          # CSS, JS, images
│       ├── templates/       # Thymeleaf templates
│       └── application.properties
```

## 🧪 Testing

### Using Postman
1. Import the API collection (if provided)
2. Test authentication endpoints
3. Test CRUD operations for jobs and applications

### Manual Testing
1. Register users with different roles
2. Test job posting and application workflows
3. Verify interview scheduling and placement tracking

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Deployment
1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR:
```bash
java -jar target/campus-placement-system-1.0.0.jar
```

### Docker Deployment (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/campus-placement-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔒 Security Features

- **Password Encryption**: BCrypt hashing
- **JWT Authentication**: Secure token-based auth
- **Role-based Access**: Granular permission control
- **Input Validation**: Server-side validation
- **File Upload Security**: PDF-only resume uploads with size limits

## 📈 Future Enhancements

- Email notifications for application updates
- Real-time chat between recruiters and students
- Advanced analytics and reporting
- Mobile application
- Integration with external job portals
- Automated interview scheduling
- Video interview integration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Bootstrap team for responsive UI components
- Font Awesome for beautiful icons
- MySQL team for reliable database system

---

**Campus Placement Management System** - Streamlining campus recruitment for the digital age! 🎓✨