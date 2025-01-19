# Employee Record Management

## Project Overview
This project is an Employee Record Management system built with Java and Spring Boot. It allows for the management of employee records, including creating, updating, deleting, and searching for employees.

## Backlog

### User Management
- [x] **Create User**
  - Implement user creation functionality.
  - Log user creation events.
- [x] **Get Current User**
  - Implement functionality to retrieve the current authenticated user.
- [x] **Get User by ID**
  - Implement functionality to retrieve a user by their ID.
  - Handle user not found exceptions.
- [x] **Update User**
  - Implement user update functionality.
  - Log user update events.
- [x] **Delete User**
  - Implement user deletion functionality.
  - Log user deletion events.
- [x] **Get All Users**
  - Implement functionality to retrieve all users with pagination.

### Employee Management
- [x] **Create Employee**
  - Implement employee creation functionality.
  - Ensure unique employee IDs.
  - Log employee creation events.
- [x] **Get Employee by ID**
  - Implement functionality to retrieve an employee by their ID.
  - Handle employee not found exceptions.
- [x] **Update Employee**
  - Implement employee update functionality.
  - Log employee update events.
- [x] **Delete Employee**
  - Implement employee deletion functionality.
  - Log employee deletion events.
- [x] **Get All Employees**
  - Implement functionality to retrieve all employees with pagination.
- [x] **Search Employees**
  - Implement functionality to search employees by keyword.
  - Log search events.
- [x] **Filter Employees**
  - Implement functionality to filter employees by department, employment status, and hire date range.

### Security
- [x] **JWT Authentication**
  - Implement JWT token generation.
  - Secure endpoints with JWT authentication.

### Documentation
- [x] **API Documentation**
  - Implement OpenAPI documentation.
  - Secure API documentation with JWT authentication.

### Miscellaneous
- [x] **Command Line Runner**
  - Implement a command line runner to populate initial data.
- [x] **Logging**
  - Implement logging for all major events and actions.

## Getting Started
### Prerequisites
- Java 17 or higher
- Gradle
- Oracle Database xe

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/riadriadriad/employee-record-management.git