# JobSearch Web Application Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started) <br>
   2.1. [Prerequisites](#prerequisites) <br>
   2.2. [Initial Setup and Configuration](#initial-setup-and-configuration)
3. [Basic Usage](#basic-usage) <br>
   3.1. [Basic Operations and Examples as a Candidate](#basic-operations-and-examples-as-a-candidate) <br>
   3.2. [Basic Operations and Examples as a Manager](#basic-operations-and-examples-as-a-manager)
4. [Project Architecture](#project-architecture) <br>
   4.1. [Model-View-Controller (MVC)](#model-view-controller-mvc) <br>
5. [Security Features and Best Practices](#security-features-and-best-practices)
6. [Advanced Functionalities](#advanced-functionalities) <br>
   6.1. [Work Order Filtering](#work-order-filtering) <br>
   6.2. [Search Technician By Name](#search-technician-by-name) <br>
   6.3. [Report Generation](#report-generation) <br>
   6.4. [Testing](#testing) <br>
   6.5. [Login](#login) <br>
   6.6. [Data Query](#data-query) <br>
   6.7. [Frontend](#frontend) <br>
7. [License](#license)

## Introduction

The JobSearch web application is designed for employment and management purposes. It facilitates job openings, candidate
registration, and application submissions.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- SpringFramework -> [Spring initializr](https://start.spring.io/)
- [React](https://es.react.dev/learn/installation).
- Spring Security -> [Add Dependency SpringSecurity](https://start.spring.io/)
- [Kotlin](https://oregoom.com/kotlin/instalar/).
- PostgreSQL -> [Add Dependency PostgreSQL Driver](https://start.spring.io/)

### Initial Setup and Configuration

1. Clone the repository. `git clone https://github.com/DonSaul/CLKOTLINPROJECT.git`
2. Install dependencies. **follow the Prerequisites step**
3. Configure application properties.
4. Run the application.

## Basic Usage

### Basic Operations and Examples as a Candidate

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

### Model-View-Controller (MVC)

Organized architecture for separation of concerns, such as:

1. **Separation of Concerns**: MVC separates the application into three main components - Model, View, and Controller.
   This separation allows for better organization of code and clear distinction between different aspects of the
   application logic, making it easier to maintain and update.
2. **Scalability**: MVC architecture facilitates scalability by enabling developers to modify or extend one component
   without affecting the others. This modular approach allows for the addition of new features or changes to existing
   ones without disrupting the entire application.
3. **Code Reusability**: MVC promotes code reusability through the use of separate components. For example, business
   logic encapsulated in the Controller can be reused across different views, enhancing development efficiency and
   reducing duplication of code.
4. **Testability**: With MVC, each component can be tested independently, leading to more effective testing strategies.
   Unit tests can be written for the Controller logic, while integration tests can be performed on the interaction
   between the Model, View, and Controller components.

## Security Features and Best Practices

Robust security measures:

- Authentication
- Authorization
- Encryption
- Input validation

## Advanced Functionalities

1. ### Work Order Filtering

Advanced filtering options for job seekers.

2. ### Search Technician By Name

Functionality to search for technicians.

3. ### Report Generation

Analytics and decision-making reports.

4. ### Testing

Comprehensive testing suite.

5. ### Login
#### Authentication and Security Documentation

This document outlines the authentication and security mechanisms implemented in the JobSearch web application.

### Overview

The authentication process involves user registration, login, and token generation using JSON Web Tokens (JWT). Security
measures include password encryption, token validation, and authorization for accessing protected resources.

### AuthService 

The `AuthService` class handles user authentication and registration.

Check the class:

```kotlin
@Service
class AuthService(
   private val userRepository: UserRepository,
   val userService: UserService,
) : UserDetailsService {
   @Autowired
   private lateinit var passwordEncoder: PasswordEncoder
   @Autowired
   private lateinit var jwtProvider: JwtProvider
   fun register(userDto: UserDTO) {
      userService.createUser(userDto)
   }
}
```
### JwtProvider
The JwtProvider class generates and validates JWT tokens.
```kotlin
@Component
class JwtProvider {
   @Value("\${jwt.secret}")
   private val jwtSecret: String? = null

   @Value("\${jwt.expiration}")
   private val jwtExpiration: Int? = null

   fun generateJwtToken(userDetails: UserDetails): String {
      val claims = Jwts.claims().setSubject(userDetails.username)
      claims["roles"] = userDetails.authorities
      return Jwts.builder()
         .setClaims(claims)
         .setIssuedAt(Date())
         .setExpiration(Date(System.currentTimeMillis() + jwtExpiration!! * 1000))
         .signWith(SignatureAlgorithm.HS512, jwtSecret)
         .compact()
   }
   //we also implement validateJwtToken for the validation and getUserNameFromJwtToken 
   //get the name based on the token
}
```

### JwtAuthenticationFilter
The JwtAuthenticationFilter intercepts HTTP requests and validates JWT tokens.
```kotlin
@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: UserDetailsService,
    private val userRepository: UserRepository,
) : OncePerRequestFilter() {
    // Method for filtering and validating JWT tokens in HTTP requests
}
```
### AuthInterceptor
The AuthInterceptor class intercepts HTTP requests and extracts JWT tokens.
```kotlin
@Component
class AuthInterceptor : HandlerInterceptor {
    // Method for extracting JWT tokens from HTTP requests
}
```

### AuthController
The AuthController class handles user registration and authentication endpoints.
```kotlin
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    // Methods for registering and authenticating users
}
```

### SecurityConfig
The SecurityConfig class configures security settings and filters for HTTP requests.
```kotlin
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(private val userDetailsService: UserDetailsService) {
    // Configuration for security filters and authentication manager
}
```
### WebConfig
The WebConfig class configures CORS settings for HTTP requests.
```@Configuration
@EnableWebMvc
class WebConfig() : WebMvcConfigurer {
    // Configuration for Cross-Origin Resource Sharing (CORS)
}
```

### Security Features

- **Password Encryption**: User passwords are encrypted using a password encoder to enhance security.
- **Token-Based Authentication**: JWT tokens are used for authentication, providing a stateless and secure authentication mechanism.
- **Token Validation**: JWT tokens are validated to ensure their authenticity and prevent tampering.
- **Authorization**: Access to protected resources is restricted based on user roles and permissions.

### Implementation Details

- **User Registration**: Users can register for an account using the `/api/v1/auth/register` endpoint.
- **User Authentication**: Users can authenticate using their credentials (username and password) via the `/api/v1/auth/login` endpoint.
- **Token Generation**: Upon successful authentication, a JWT token is generated and returned to the client for subsequent requests.
- **Token Validation**: JWT tokens are validated before granting access to protected resources, ensuring their integrity.
- **User Role-Based Access Control**: Access to different endpoints is restricted based on user roles (e.g., candidate, manager, administrator).





6. ### Data Query

Efficient database querying.

7. ### Frontend

User-friendly frontend development.

8. ## License
We need to add this license: [MIT License](https://opensource.org/licenses/MIT).
