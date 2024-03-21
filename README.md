# JobSearch Web Application Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started) <br>
   2.1. [Communication](#communication-between-frontend-and-backend) <br>
   2.2. [Prerequisites](#prerequisites) <br>
   2.3. [Initial Setup and Configuration](#initial-setup-and-configuration)
3. [Basic Usage](#basic-usage) <br>
   3.1. [Basic Operations and Examples as a Candidate](#basic-operations-and-examples-as-a-candidate) <br>
   3.2. [Basic Operations and Examples as a Manager](#basic-operations-and-examples-as-a-manager)
4. [Project Architecture](#project-architecture) <br>
   4.1. [Model-View-Presenter (MVP)](#model-view-presenter-mvp) <br>
5. [Security Features and Best Practices](#security-features-and-best-practices)
6. [Advanced Functionalities](#advanced-functionalities) <br>
   6.1. [Data Query](#data-query) <br>
   6.2. [Frontend files](#frontend-helper-files) <br>
    

## Introduction

The JobSearch web application is designed for employment and management purposes. It facilitates job openings, candidate
registration, and application submissions.

| Role      | Basic Operations                                                                                                                            |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------|
| Candidate | - Register an account with your details.                                                                                                    |
|           | - Log in to your account securely.                                                                                                          |
|           | - Create and manage your CV, including details like years of experience, projects, education, and salary expectation.                       |
|           | - Search for job vacancies based on job family, years of experience, and salary expectation.                                                |
|           | - Apply to vacancies matching your skills and expectations.                                                                                 |
|           | - Communicate with managers regarding job opportunities.                                                                                    |
| Manager   | - Create and manage job vacancies, specifying details like job family, company name, description, required years of experience, and salary. |
|           | - Search for candidates based on filters such as years of experience, job family, and salary expectation.                                   |
|           | - Send vacancy invitations to candidates who match the job requirements.                                                                    |

## Getting Started

### Communication between Frontend and Backend

The frontend and backend of JobSearch communicate via HTTP requests using the RESTful protocol. When a user interacts
with the frontend user interface, requests are sent to the backend to perform operations such as logging in, registering
a user, searching for vacancies, creating CVs, etc.

This communication occurs through files such as paths.js, **RequireAuth.js**, and **router.js**, where endpoints and
authentication mechanisms are defined and utilized. The backend processes these requests and returns corresponding
responses, which the frontend utilizes to update the user interface and display information to the user. The
communication between the frontend and backend is based on a set of RESTful endpoints defined in the backend, which the
frontend utilizes to perform various operations.

### Prerequisites

Ensure you have the following installed:

SpringFramework.  [Spring initializr](https://start.spring.io/) ```implementation("org.springframework.boot:spring-boot-starter-web") version 3.2.2```

- [React](https://es.react.dev/learn/installation). ```npm install version 9.6.2```
- Spring
  Security.  [Add Dependency SpringSecurity](https://start.spring.io/) ```implementation("org.springframework.boot:spring-boot-starter-security")```
- Kotlin language. [Click HERE](https://oregoom.com/kotlin/instalar/).
-

PostgreSQL. [Add Dependency PostgreSQL Driver](https://start.spring.io/) ```runtimeOnly("org.postgresql:postgresql")```

- JWT(Json web token). ```implementation ("io.jsonwebtoken:jjwt:0.9.1")```
- JVM  ```kotlin("jvm") version "1.9.22"```

### Initial Setup and Configuration

1. Clone the repository. `git clone https://github.com/DonSaul/CLKOTLINPROJECT.git`
2. Install dependencies. **follow the Prerequisites step**
3. Configure application properties.
4. Run the application.

## Basic Usage

### Basic Operations and examples as a Candidate

- Candidate registration.
- Candidate login.
- Search for vacancies based on job family, years of experience, and salary expectation.
- Job Filtering.
- Create and manage my CV, including information about years of experience, projects, education, and salary expectation.
- Apply to vacancies that match my skills and expectations.
- Communication between Candidate and Manager.

### Basic Operations and Examples as a Manager

- Create and manage vacancies including job family, company name, description, required years of experience, and salary.
- Search for candidates based on filters such as years of experience, job family, and salary expectation.
- Send vacancy invitations to candidates who match the job requirements.

## Project Architecture

### Model-View-Presenter (MVP)

```
#backenend: jobsearch/
├── .gradle
├── .idea
├── build
├── gradle
├── out
└── src/
├── main/
└── kotlin/
└── com.jobsearch/
├── config
├── controller
├── dto
├── entity
├── exception
├── interceptor
├── jwt
├── repository
└── response
└── resources  
└── test

/fronted/job-search
├── node_modules
├── public
├── src
│   ├── api
│   ├── assets
│   ├── components
│   ├── helpers
│   ├── hooks
│   ├── pages
│   ├── router
│   │   ├── paths.js
│   │   ├── RequireAuth.js
│   │   └── router.js
│   ├── App.css
│   ├── App.js
│   ├── App.test.js
│   ├── index.css
│   ├── index.js
│   ├── logo.svg
│   ├── reportWebVitals.js
│   └── setupTests.js

```

Organized architecture for separation of concerns, such as:

1. **Separation of Concerns**: MVP separates the application into three main components - Model, View, and Controller.
   This separation allows for better organization of code and clear distinction between different aspects of the
   application logic, making it easier to maintain and update.
2. **Scalability**: MVP architecture facilitates scalability by enabling developers to modify or extend one component
   without affecting the others. This modular approach allows for the addition of new features or changes to existing
   ones without disrupting the entire application.
3. **Code Reusability**: MVP promotes code reusability through the use of separate components. For example, business
   logic encapsulated in the Controller can be reused across different views, enhancing development efficiency and
   reducing duplication of code.
4. **Testability**: With MVP, each component can be tested independently, leading to more effective testing strategies.
   Unit tests can be written for the Controller logic, while integration tests can be performed on the interaction
   between the Model, View, and Controller components.

## Security Features and Best Practices

Robust security measures:

| Atribute         | Implementation                                                            |
|------------------|---------------------------------------------------------------------------|
| Authentication   | implementation("io.jsonwebtoken:jjwt:0.9.1")                              |
| Authorization    | implementation ("io.jsonwebtoken:jjwt:0.9.1")                             |
| Encryption       | implementation("org.springframework.boot:spring-boot-starter-security")   |
| Input validation | implementation("org.springframework.boot:spring-boot-starter-validation") |

**We recommend initializing the project at https://start.spring.io/ to acquire the dependencies in an updated and more
efficient way.**

## Advanced Functionalities

### Search Candidates from Manager entity

The `CandidateController` is responsible for handling requests related to candidate search.

#### Endpoints

| Endpoint                    | Description                                        | HTTP Method | Query Parameters                                                                                                                                                                  | Response                                                                                                                                                                                                   |
|-----------------------------|----------------------------------------------------|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/api/v1/candidates/search` | Searches for candidates based on provided filters. | GET         | `salary` (optional): Candidate's expected salary. <br> `jobFamilyId` (optional): Candidate's job family ID. <br> `yearsOfExperience` (optional): Candidate's years of experience. | - HTTP Status Code: <br>   - 200 (OK): Candidates matching the criteria found. <br>   - 204 (No Content): No candidates matching the criteria found. <br> - Response Body: List of `CandidateDTO` objects. |

#### Related Classes

- `CandidateDTO`: DTO representing candidate details.
- `UserService`: Service providing logic for candidate search.

The `UserService` handles operations related to users and candidates.

#### Main Methods

| Method                  | Description                                         | Parameters                                                                                                                                                                        | Return                                                       |
|-------------------------|-----------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| `searchCandidates`      | Searches for candidates based on provided criteria. | `salary` (optional): Candidate's expected salary. <br> `jobFamilyId` (optional): Candidate's job family ID. <br> `yearsOfExperience` (optional): Candidate's years of experience. | List of `CandidateDTO` objects matching the search criteria. |
| `mapToUserCandidateDTO` | Maps a `Cv` object to a `CandidateDTO` object.      | `cvEntity`: Cv entity to be mapped. <br> `jobFamilies`: List of job families associated with the candidate.                                                                       | `CandidateDTO` object with candidate information.            |

#### Related Classes

- `Cv`: Entity representing user's curriculum.
- `JobFamily`: Entity representing a job family.
- `CvRepository`: Repository for accessing curriculum data.

The `CandidateDTO` represents the details of a candidate.

#### Attributes

- `id`: Candidate's identifier.
- `firstName`: Candidate's first name.
- `lastName`: Candidate's last name.
- `email`: Candidate's email address.
- `yearsOfExperience`: Candidate's years of experience.
- `salaryExpectation`: Candidate's expected salary.
- `jobFamilies`: List of job families associated with the candidate.

## Authentication and Security

The authentication process involves user registration, login, and token generation using JSON Web Tokens (JWT). Security
measures include password encryption, token validation, and authorization for accessing protected resources.

#### Security Features

- **Password Encryption**: Encrypts user passwords using a password encoder for enhanced security.
- **Token-Based Authentication**: Utilizes JWT tokens for authentication, ensuring a stateless and secure authentication
  mechanism.
- **Token Validation**: Validates JWT tokens to maintain authenticity and prevent tampering.
- **Authorization**: Restricts access to protected resources based on user roles and permissions.

#### Implementation Details

- **User Registration**: Users can register for an account using the `/api/v1/auth/register` endpoint.
- **User Authentication**: Users can authenticate using their credentials (username and password) via
  the `/api/v1/auth/login` endpoint.
- **Token Generation**: Upon successful authentication, a JWT token is generated and returned to the client for
  subsequent requests.
- **Token Validation**: Validates JWT tokens before granting access to protected resources, ensuring integrity.
- **User Role-Based Access Control**: Access to different endpoints is restricted based on user roles (e.g., candidate,
  manager, administrator).

### Summary Table

| Component               | Description                                                 |
|-------------------------|-------------------------------------------------------------|
| AuthService             | Handles user authentication and registration.               |
| JwtProvider             | Generates and validates JWT tokens.                         |
| JwtAuthenticationFilter | Intercepts HTTP requests and validates JWT tokens.          |
| AuthInterceptor         | Intercepts HTTP requests and extracts JWT tokens.           |
| AuthController          | Manages user registration and authentication endpoints.     |
| SecurityConfig          | Configures security settings and filters for HTTP requests. |
| WebConfig               | Configures CORS settings for HTTP requests.                 |

## Data Query

The project utilizes PostgreSQL as the database management system (DBMS) for efficient data storage and retrieval.

#### Database Schema

The following tables are part of the database schema:

| Table Name         | Description                                                        |
|--------------------|--------------------------------------------------------------------|
| application_status | Stores various application statuses such as "Not Applied"          |
|                    | and "Accepted".                                                    |
| applications       | Represents job applications submitted by candidates.               |
| chat_messages      | Stores chat messages exchanged between users.                      |
| conversations      | Manages conversations between users, containing multiple messages. |
| cvs                | Contains CV (curriculum vitae) information of users.               |
| interests          | Tracks user interests in specific job families.                    |
| job_family         | Represents different job categories or families.                   |
| notification       | Stores notifications sent to users regarding vacancies, messages,  |
|                    | invitations, etc.                                                  |
| notification_types | Defines types of notifications such as "vacancies" and "messages". |
| person             | Contains basic personal information of users.                      |
| project            | Represents projects listed in users' CVs.                          |
| roles              | Stores user roles such as "candidate", "manager", and "admin".     |
| skill              | Stores various skills that users possess.                          |
| users              | Stores user account information including email, password, etc.    |
| vacancy            | Represents job vacancies posted by companies.                      |

#### Database Models

The project includes various entity classes representing database tables:

- `Application`: Represents job applications submitted by users.
- `ChatMessage`: Stores chat messages exchanged between users.
- `Conversation`: Manages conversations between users.
- `Cv`: Contains information about users' curriculum vitae.
- `Interest`: Tracks user interests in specific job families.
- `JobFamily`: Represents different job categories or families.
- `Notification`: Stores notifications sent to users.
- `NotificationType`: Defines types of notifications.
- `Person`: Contains basic personal information of users.
- `Project`: Represents projects listed in users' CVs.
- `Role`: Stores user roles.
- `Skill`: Represents various skills that users possess.
- `Status`: Stores application statuses.
- `User`: Stores user account information.

### Frontend Helper Files

The frontend helper files in the `/helpers` directory provide essential functionalities and constants used throughout
the application. Here's an overview:

| File           | Purpose                                                                                               |
|----------------|-------------------------------------------------------------------------------------------------------|
| constants.js   | Defines constants such as the authentication token name (`AUTH_TOKEN_NAME`) and user roles (`ROLES`). |
| endpoints.js   | Contains endpoint URLs for various API requests used in the application.                              |
| queryClient.js | Creates a React Query Client instance used for data fetching and caching.                             |
| tokenHelper.js | Provides helper functions to decode and extract information from authentication tokens.               |
| userContext.js | Defines the authentication context and provides hooks to access user authentication information.      |

These helper files play a crucial role in managing authentication, API communication, and user context within the
frontend application.

### Router Files

The router files in the `/router` directory define the application's navigation and route authentication logic. Here's a
breakdown:

| File           | Purpose                                                                                                 |
|----------------|---------------------------------------------------------------------------------------------------------|
| paths.js       | Defines path constants for different routes within the application.                                     |
| RequireAuth.js | Implements a component to ensure route access is restricted to authenticated users with specific roles. |
| router.js      | Configures the application routes using React Router, including route guarding and role-based access.   |

These router files ensure proper navigation and access control within the frontend application, enhancing overall user
experience and security.

