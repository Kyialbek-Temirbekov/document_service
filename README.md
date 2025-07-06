# Document Management System

## Description
A Spring Boot-based document management system that provides functionality for user registration, document storage, and reporting. The system includes features like email confirmation via OTP, file storage using MinIO, and Excel report generation.

Key features:
- User registration and authentication
- Document upload/download with metadata
- Email notification service
- Scheduled tasks for cleanup
- Excel report generation
- Internationalization support

## Usage
The application provides REST APIs for:
- User management (`/customer`)
- Document management (`/document`)
- Time service (`/time`)

API documentation is available via Swagger UI at `/swagger-ui.html` when running the application.

## Technologies
- **Backend**: 
  - Spring Boot 3.3.1
  - Spring Security
  - Spring Data JPA
  - Spring Mail
- **Database**: PostgreSQL
- **Storage**: MinIO
- **File Processing**: Apache POI, Apache Tika
- **API Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven
- **Other**: 
  - Lombok
  - Java 17
  - Excel report generation
  - Internationalization (i18n)
