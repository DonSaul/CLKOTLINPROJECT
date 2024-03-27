# JobSearch Web Application Documentation

![logo](https://www.dice.com/binaries/medium/content/gallery/dice/insights/2015/06/Screen-Shot-2015-06-22-at-10.41.14-AM.png)

🗂️ **JobSearch** is a web platform for employment and management aimed at facilitating interaction between candidates
and managers. The project is a product of SoftServe's project lab, where we focus on learning and improving our skills.

[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Awesome](https://awesome.re/badge.svg)](https://awesome.re)
[![Material Design](https://img.shields.io/badge/Material_Design-757575?style=flat&logo=material-design&logoColor=white)](https://material.io/)
[![Kotlin](https://img.shields.io/badge/Kotlin-007396?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![React](https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=white)](https://reactjs.org/)
[![Spring](https://img.shields.io/badge/Spring_Framework-6DB33F?style=flat&logo=spring&logoColor=white)](https://spring.io/)
[![Pull request](https://img.shields.io/badge/PRs-welcome-success?style=flat)](https://github.com/DonSaul/CLKOTLINPROJECT/pulls)
[![RESTful API](https://img.shields.io/badge/RESTful_API-005571?style=flat&logo=http&logoColor=white)](https://restfulapi.net/)

## Features

### User Authentication and Authorization:

| Feature                                                               | Description                                                                         |
|-----------------------------------------------------------------------|-------------------------------------------------------------------------------------|
| Users can register an account                                         | 📝 Users provide Last Name, First Name, password, and email to create an account.   |
| Users can log in                                                      | 🔑 Users can log in using their credentials.                                        |
| Different roles (candidate, manager, admin) with specific permissions | 🛡️ Supports roles with specific permissions such as candidate, manager, and admin. |
| Managers can create new user accounts with specific roles             | 🧾 Managers have the ability to create new user accounts with predefined roles.     |

### Candidate Features:

| Feature                                   | Description                                                                                                                            |
|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Candidates can create and manage their CV | 📄 Candidates can add and update information about their years of experience, projects, education, and salary expectation in their CV. |
| Candidates can search for vacancies       | 🔍 Candidates can search for job vacancies based on job family, years of experience, and salary expectation.                           |
| Candidates can apply to vacancies         | 📋 Candidates can apply to vacancies that match their skills and expectations.                                                         |

### Manager Features:

| Feature                                             | Description                                                                                                                                  |
|-----------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| Managers can create and manage vacancies            | 📋 Managers can create and manage job vacancies, specifying job family, company name, description, required years of experience, and salary. |
| Managers can search for candidates                  | 🔍 Managers can search for candidates based on filters such as years of experience, job family, and salary expectation.                      |
| Managers can send vacancy invitations to candidates | 💌 Managers can invite candidates who match the job requirements to apply for vacancies.                                                     |

### Communication Features:

| Feature                                         | Description                                                                                                  |
|-------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| Users can communicate with other users          | 💬 Users can send and receive messages within the app.                                                       |
| Users receive email notifications               | 📧 Users receive email notifications for important actions such as new vacancies, invitations, and messages. |
| Users can respond to messages and notifications | ✉️ Users can respond to messages and notifications directly from their email.                                |

### Integration and Testing:

| Feature                     | Description                                                                         |
|-----------------------------|-------------------------------------------------------------------------------------|
| Seamless integration        | 🔄 All features are seamlessly integrated to ensure they work together effectively. |
| Thorough testing            | 🕵️‍♂️ Thorough testing is conducted to identify and fix any bugs or issues.        |
| Optimization and refinement | 🛠️ The codebase is optimized and refined for better performance and reliability.   |

### Documentation and Finalization:

| Feature                | Description                                                                                            |
|------------------------|--------------------------------------------------------------------------------------------------------|
| Thorough documentation | 📚 The codebase is thoroughly documented, including APIs and usage instructions, for future reference. |
| Finalization           | 🎉 Any remaining tasks or bug fixes are finalized before deployment.                                   |
| Production readiness   | 🚀 The project is prepared for deployment and ensured to be production-ready.                          |

## Links

- [Documentation](#documentation)
- [Demo](https://google.com)

## Discussion

Please visit our [issues discussions](https://github.com/DonSaul/CLKOTLINPROJECT/issues) for general questions. **Issues
are for bug reports and feature requests only.**

## Contributors

[<img src="https://github.com/jamirou.png?size=70" alt="jamirou" style="border-radius: 50%;">](https://github.com/jamirou)
[<img src="https://github.com/s0alken.png?size=70" alt="s0alken" style="border-radius: 50%;">](https://github.com/s0alken)
[<img src="https://github.com/Santisu.png?size=70" alt="Santisu" style="border-radius: 50%;">](https://github.com/Santisu)
[<img src="https://github.com/RafaUribeG.png?size=70" alt="RafaUribeG" style="border-radius: 50%;">](https://github.com/RafaUribeG)
[<img src="https://github.com/ndevia.png?size=70" alt="ndevia" style="border-radius: 50%;">](https://github.com/ndevia)
[<img src="https://github.com/Gabe239.png?size=70" alt="Gabe239" style="border-radius: 50%;">](https://github.com/Gabe239)
[<img src="https://github.com/EdgarAraya.png?size=70" alt="EdgarAraya" style="border-radius: 50%;">](https://github.com/EdgarAraya)

## Acknowledgements

[<img src="https://github.com/DonSaul.png?size=70" alt="DonSaul" style="border-radius: 50%;">](https://github.com/DonSaul)

We would like to express our sincere gratitude to [**@DonSaul**](https://github.com/DonSaul) for his invaluable guidance
and mentorship throughout the development of this project. His expertise, insights, and support have been instrumental
in shaping our journey and helping us overcome various challenges.

Thank you, [**@DonSaul**](https://github.com/DonSaul), for being an exceptional mentor and for believing in our
potential.

_______________________________________________

## *documentation*

## Table of Contents

1. [Getting Started](#getting-started) <br>
   2.2. [Prerequisites](#prerequisites) <br>
   2.3. [Initial Setup and Configuration](#initial-setup-and-configuration)
2. [Basic Usage](#basic-usage) <br>
   2.1. [Basic Operations and Examples as a Candidate](#basic-operations-and-examples-as-a-candidate) <br>
   2.2. [Basic Operations and Examples as a Manager](#basic-operations-and-examples-as-a-manager)
3. [Project Architecture](#project-architecture) <br>
   3.1. [API restful](#api-restful) <br>
4. [Security Features and Best Practices](#security-features-and-best-practices)
5. [Advanced Functionalities](#advanced-functionalities) <br>
   5.1. [Data Query](#data-query) <br>
   5.2. [Communication](#communication-between-frontend-and-backend) <br>
   5.3. [Frontend files](#frontend-helper-files) <br>
   5.4. [Router files](#router-files) <br>
   5.5. [ApplicationController](#application-controller) <br>

--------------------------------------------------------

## Getting Started

### Prerequisites

Ensure you have the following installed:

| Technology          | Installation/Dependency                                       | Version |
|---------------------|---------------------------------------------------------------|---------|
| SpringFramework     | [Spring initializr](https://start.spring.io/)                 | 3.2.2   |
| React               | [React Installation](https://es.react.dev/learn/installation) | 9.6.2   |
| Spring Security     | [Add Dependency SpringSecurity](https://start.spring.io/)     |         |
| Kotlin language     | [Kotlin Installation](https://oregoom.com/kotlin/instalar/)   |         |
| PostgreSQL          | [Add Dependency PostgreSQL Driver](https://start.spring.io/)  |         |
| JWT(Json web token) |                                                               | 0.9.1   |
| JVM                 |                                                               | 1.9.22  |

### Initial Setup and Configuration

1. Clone the repository. `git clone https://github.com/DonSaul/CLKOTLINPROJECT.git`
2. Install dependencies. [follow the Prerequisites step](#prerequisites)
3. Configure application properties.
4. Run the application.

--------------------------------------------------------

## Basic Usage

### Basic Operations and examples as a Candidate

| Operation                      | Description                                                                                                                         | Example                                                                                                 |
|--------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| **Candidate Basic Operations** |                                                                                                                                     |                                                                                                         |
| Candidate Registration         | Register as a candidate in the system.                                                                                              |                                                                                                         |
| Candidate Login                | Log in to the system as a registered candidate.                                                                                     |                                                                                                         |
| Search for Vacancies           | Search for job vacancies based on criteria such as job family, years of experience, and salary expectation.                         | Search for vacancies in 'IT' with '5+ years exp.' and '70k+ salary'.                                    |
| Job Filtering                  | Filter job vacancies based on criteria such as job family, years of experience, and salary expectation.                             | Filter vacancies by 'Engineering' with '3-5 years exp.' and '80k-100k salary'.                          |
| Create and Manage CV           | Create and manage the candidate's CV, including details about experience, projects, education, and salary expectation.              | Create a CV with '3 years exp.', 'completed projects', 'Master's degree', and '80k salary expectation'. |
| Apply to Vacancies             | Apply to job vacancies that match the candidate's skills and expectations.                                                          | Apply to 'Software Engineer' vacancy with '5+ years exp.' and '90k salary expectation'.                 |
| Communication with Manager     | Communicate with the hiring manager regarding job applications or other inquiries.                                                  | Contact manager of 'Engineering Manager' position for further details.                                  |
| **Manager Basic Operations**   |                                                                                                                                     |                                                                                                         |
| Create and Manage Vacancies    | Create and manage job vacancies, specifying details such as job family, company name, description, required experience, and salary. | Create a vacancy for 'Software Developer' at 'XYZ Corp' with '5+ years exp.' and '100k salary'.         |
| Search for Candidates          | Search for candidates based on filters such as years of experience, job family, and salary expectation.                             | Search for candidates with '10+ years exp.' in 'Finance' with '100k+ salary expectation'.               |
| Send Vacancy Invitations       | Send invitations to potential candidates who match the job requirements.                                                            | Send invitation to 'Java Developer' with '5+ years exp.' and '90k salary expectation'.                  |

## Project Architecture

### API restful

```
| Backend            | Frontend                   |
| ------------------ | -------------------------- |
| jobsearch/         | /fronted/job-search/       |
| ├── .gradle        | ├── node_modules           |
| ├── .idea          | ├── public                 |
| ├── build          | ├── src                    |
| ├── gradle         | │   ├── api                |
| ├── out            | │   ├── assets             |
| └── src/           | │   ├── components         |
| ├── main/          | │   ├── helpers            |
| └── kotlin/        | │   ├── hooks              |
| └── com.jobsearch/ | │   ├── pages              |
| ├── config         | │   ├── router             |
| ├── controller     | │   │   ├── paths.js       |
| ├── dto            | │   │   ├── RequireAuth.js |
| ├── entity         | │   │   └── router.js      |
| ├── exception      | │   ├── App.css            |
| ├── interceptor    | │   ├── App.js             |
| ├── jwt            | │   ├── App.test.js        |
| ├── repository     | │   ├── index.css          |
| └── response       | │   ├── index.js           |
| ├── resources      | │   ├── logo.svg           |
| └── test           | │   ├── reportWebVitals.js |
|                    | │   └── setupTests.js      |

```

Organized architecture for separation of concerns, such as:

| Aspect                     | Description                                                                                                                                                                                                                                                                   |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Separation of Concerns** | API Rest for the structure. This separation allows for better organization of code and clear distinction between different aspects of the application logic, making it easier to maintain and update.                                                                         |
| **Scalability**            | The API Rest architecture facilitates scalability by enabling developers to modify or extend one component without affecting the others. This modular approach allows for the addition of new features or changes to existing ones without disrupting the entire application. |
| **Code Reusability**       | The API Rest architecture promotes code reusability through the use of separate components. For example, business logic encapsulated in the Controller can be reused across different views, enhancing development efficiency and reducing duplication of code.               |
| **Testability**            | With REST, each component can be tested independently, leading to more effective testing strategies. Unit tests can be written for the Controller logic, while integration tests can be performed on the interaction between the Model, View, and Controller components.      |

### Security Features and Best Practices

Robust security measures:

| Atribute         | Implementation                                                            |
|------------------|---------------------------------------------------------------------------|
| Authentication   | implementation("io.jsonwebtoken:jjwt:0.9.1")                              |
| Authorization    | implementation ("io.jsonwebtoken:jjwt:0.9.1")                             |
| Encryption       | implementation("org.springframework.boot:spring-boot-starter-security")   |
| Input validation | implementation("org.springframework.boot:spring-boot-starter-validation") |

### Advanced Functionalities

### Search Candidates from Manager entity

![VdeosinttuloHechoconClipchamp-ezgif com-video-to-gif-converter](https://github.com/jamirou/Personal_Schedule/assets/48457084/98542f4e-6334-43ef-9c3e-3e6c4fdd013f)
The **CandidateController** is responsible for handling requests related to candidate search.

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

--------------------------------------------------------

### Authentication and Security

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

--------------------------------------------------------

### Data Query

The project utilizes PostgreSQL as the database management system (DBMS) for efficient data storage and retrieval.

### Database Schema

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

--------------------------------------------------------

### Communication between Frontend and Backend

The frontend and backend of JobSearch communicate via HTTP requests using the RESTful protocol. When a user interacts
with the frontend user interface, requests are sent to the backend to perform operations such as logging in, registering
a user, searching for vacancies, creating CVs, etc.

This communication occurs through files such as paths.js, **RequireAuth.js**, and **router.js**, where endpoints and
authentication mechanisms are defined and utilized. The backend processes these requests and returns corresponding
responses, which the frontend utilizes to update the user interface and display information to the user. The
communication between the frontend and backend is based on a set of RESTful endpoints defined in the backend, which the
frontend utilizes to perform various operations.
--------------------------------------------------------

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
--------------------------------------------------------

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
--------------------------------------------------------

### ApplicationController

This controller handles requests related to applications in the backend of the application.

#### Endpoints

| HTTP Method | URL                        | Description                           | Input Parameters                        | Response Codes                                                                                                        |
|-------------|----------------------------|---------------------------------------|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| POST        | `/api/v1/application`      | Creates a new application.            | `ApplicationDTO` Object                 | `201 Created`: If the application is successfully created.<br>Other possible error codes.                             |
| GET         | `/api/v1/application/{id}` | Retrieves an application by its ID.   | Application ID                          | `200 OK`: If the application is found and returned successfully.<br>`404 Not Found`: If the application is not found. |
| GET         | `/api/v1/application`      | Retrieves all applications.           | -                                       | `200 OK`: If applications are retrieved successfully.<br>Other possible error codes.                                  |
| PUT         | `/api/v1/application/{id}` | Updates the status of an application. | Application ID, `ApplicationDTO` Object | `200 OK`: If the application is updated successfully.<br>Other possible error codes.                                  |
| DELETE      | `/api/v1/application/{id}` | Deletes an application.               | Application ID                          | `204 No Content`: If the application is deleted successfully.<br>Other possible error codes.                          |

- All endpoints are protected with authorization.
- Users with the 'manager' role can access endpoints to update and delete applications.
