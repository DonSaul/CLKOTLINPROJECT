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
   7.1. [Try Catches](#try-catches) <br>
   7.2. [Input Validation](#input-validation) <br>
7. [License](#license)

## Introduction

The JobSearch web application is designed for employment and management purposes. It facilitates job openings, candidate registration, and application submissions.

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
1. **Separation of Concerns**: MVC separates the application into three main components - Model, View, and Controller. This separation allows for better organization of code and clear distinction between different aspects of the application logic, making it easier to maintain and update.
2. **Scalability**: MVC architecture facilitates scalability by enabling developers to modify or extend one component without affecting the others. This modular approach allows for the addition of new features or changes to existing ones without disrupting the entire application.
3. **Code Reusability**: MVC promotes code reusability through the use of separate components. For example, business logic encapsulated in the Controller can be reused across different views, enhancing development efficiency and reducing duplication of code.
4. **Testability**: With MVC, each component can be tested independently, leading to more effective testing strategies. Unit tests can be written for the Controller logic, while integration tests can be performed on the interaction between the Model, View, and Controller components.

## Security Features and Best Practices
Robust security measures:

- Authentication
- Authorization
- Encryption
- Input validation

## Advanced Functionalities


### Work Order Filtering

Advanced filtering options for job seekers.

### Search Technician By Name

Functionality to search for technicians.

### Report Generation

Analytics and decision-making reports.

### Testing

Comprehensive testing suite.

### Login

Secure login functionality.

### Data Query

Efficient database querying.

### Frontend

User-friendly frontend development.


### Try Catches

Graceful exception handling.

### Input Validation

Preventing injection attacks.

## License

We need to add this license: [MIT License](https://opensource.org/licenses/MIT).
