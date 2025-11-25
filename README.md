# Patient Management System 🏥
[![Java](https://img.shields.io/badge/Java-21-red?style=flat-square&logo=java&logoColor=white)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green?style=flat-square)]()
[![License](https://img.shields.io/badge/License-Unlicensed-lightgrey.svg?style=flat-square)]()

This repository contains the code for a Patient Management System, a microservices-based application built with Java and Spring Boot. It incorporates features like user authentication, patient data management, billing, and analytics.



## Table of Contents 📚
- [Description](#description)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [License](#license)
- [Important Links](#important-links)
- [Footer](#footer)



## Description 📝
The Patient Management System is a comprehensive solution designed to manage patient data, billing, analytics, and user authentication within a healthcare environment. It utilizes a microservices architecture to ensure scalability and maintainability. The core components include:

- **Patient Service:** Manages patient records, including creation, retrieval, updating, and deletion.
- **Auth Service:** Handles user authentication and authorization using JWT.
- **Billing Service:** Provides billing and account management functionalities.
- **Analytics Service:** Collects and processes patient-related events for generating insights.
- **API Gateway:** Acts as a single entry point for all client requests, routing them to the appropriate microservice.
- **Infrastructure:** AWS CDK project to define and provision infrastructure resources



## Features 🌟
- **User Authentication:** Secure user authentication using JWT tokens provided by the Auth Service.
- **Patient Management:** CRUD operations for patient records (create, read, update, delete) handled by the Patient Service.
- **Billing Account Management:** Integration with the Billing Service to manage patient billing accounts.
- **Real-time Analytics:** Integration with Kafka for real-time processing of patient events via the Analytics Service.
- **API Gateway:** Centralized entry point for accessing all microservices with rate limiting.
- **gRPC Communication:** Utilizes gRPC for communication between Patient Service and Billing Service.
- **Resilience:** Implements circuit breaker pattern using Resilience4j for handling service failures
- **Caching:** Implements Redis caching to improve the response time of Patient Service



## Tech Stack 💻
- **Programming Language:** Java ☕
- **Framework:** Spring Boot 🚀
- **API Gateway:** Spring Cloud Gateway
- **Database:** PostgreSQL 🐘, H2
- **Message Broker:** Kafka ✉️
- **gRPC:** gRPC
- **Build Tool:** Maven ⚙️
- **Containerization:** Docker 🐳
- **Infrastructure as Code:** AWS CDK
- **Testing:** JUnit
- **Other:** Redis



## Installation 🛠️
1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/IsharaDeshan6/patient-management.git
    cd patient-management
    ```

2.  **Build Microservices:**

    Navigate to each microservice directory (`patient-service`, `auth-service`, `billing-service`, `analytics-service`, `api-gateway`) and build the application using Maven:

    ```bash
    mvn clean package
    ```

3.  **Docker Compose (Optional):**

    To run the application using Docker, ensure you have Docker and Docker Compose installed. Navigate to the root directory of the project and use the following command:

    _Note: There is no docker-compose.yml present in the repository, you have to create one yourself based on the Dockerfiles available in the services._

    ```bash
    docker-compose up --build
    ```

4.  **Infrastructure setup**

    The project uses AWS CDK to define and provision infrastructure resources. To deploy the infrastructure, navigate to the `infrastructure` directory and use the following commands:

    ```bash
    cd infrastructure
    mvn clean package
    cdk deploy
    ```



## Usage 🚀
1.  **Running the Applications:**

    After building the microservices, you can run them individually using Spring Boot:

    ```bash
    java -jar <service-name>/target/<service-name>-0.0.1-SNAPSHOT.jar
    ```

    Replace `<service-name>` with the actual name of the service (e.g., `patient-service`).

2.  **Accessing the API Gateway:**

    The API Gateway runs on port `4004`. You can access the various microservices through this gateway.

    -   **Patient Service:** `http://localhost:4004/api/patient-service/v1/patients`

3.  **Authentication:**

    -   Register a new user via POST request to `/auth/register` endpoint.
    -   Login via POST request to `/auth/login` to obtain a JWT token. Include the token in the `Authorization` header (Bearer <token>) for subsequent requests.

4.  **Example API Usage:**

    -   **Get Patients:**

        ```bash
        curl -H "Authorization: Bearer <token>" http://localhost:4004/api/patient-service/v1/patients
        ```

    -   **Create Patient:**

        ```bash
        curl -X POST -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{"name":"John Doe", "email":"john.doe@example.com", "address":"123 Main St", "dateOfBirth":"1990-01-01"}' http://localhost:4004/api/patient-service/v1/patients
        ```



## How to use ⚙️
This project is a patient management system that is built using microservices architecture. Below are a few real world use cases of how to use this project:

- Manage a clinic's patients by creating and updating patient records.
- Use the billing service to keep track of patients' bills and payments.
- Use the analytics service to view statistics and trends about patients.
- Use the authentication service to manage user accounts and passwords.



## Project Structure 📂
```
patient-management/
├── analytics-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── api-requests/
│   └── ...
├── auth-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── billing-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── infrastructure/
│   ├── pom.xml
│   └── src/
├── integration-tests/
│   ├── pom.xml
│   └── src/
├── monitoring/
│   └── prometheus.yml
├── patient-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── ...
```



## API Reference ℹ️



### Patient Service
-   **GET /patient-service/v1/patients**
    -   Retrieves a paginated list of patients.
    -   Parameters:
        -   `page` (default: 1): Page number.
        -   `size` (default: 10): Number of patients per page.
        -   `sortBy` (default: asc): Sorting order (asc or desc).
        -   `sortField` (default: name): Field to sort by.
        -   `searchValue`: Search patients by name.

-   **POST /patient-service/v1/patients**
    -   Creates a new patient.
    -   Request Body:
        ```json
        {
          "name": "string",
          "email": "string",
          "address": "string",
          "dateOfBirth": "yyyy-MM-dd"
        }
        ```

-   **PUT /patient-service/v1/patients/{id}**
    -   Updates an existing patient.
    -   Parameters:
        -   `id`: Patient ID.
    -   Request Body: Same as POST /patient-service/v1/patients

-   **DELETE /patient-service/v1/patients/{id}**
    -   Deletes a patient.
    -   Parameters:
        -   `id`: Patient ID.



### Auth Service
-   **POST /auth/login**
    -   Authenticates a user and returns a JWT token.
    -   Request Body:
        ```json
        {
          "email": "string",
          "password": "string"
        }
        ```

-   **GET /auth/validate**
    -   Validates a JWT token.
    -   Headers:
        -   `Authorization`: Bearer <token>



### Billing Service
-   **gRPC createBillingAccount**

    -   Creates billing account via gRPC call from Patient Service to Billing Service. The billing account information can also be created via Kafka event in case of Billing Service failure.



## Contributing 🤝
Contributions are welcome! Please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes.
4.  Submit a pull request.



## License 📜
This project is unlicensed.



## Important Links 🔗
-   **Repository:** [https://github.com/IsharaDeshan6/patient-management](https://github.com/IsharaDeshan6/patient-management)



## Footer 🦶
-   **Repository:** [patient-management](https://github.com/IsharaDeshan6/patient-management)
-   Author: IsharaDeshan6
-   Contact: N/A

⭐️ Like the project? Give it a star! ✨

🍴 Fork it to make your own improvements!

🐛 Found a bug? Report it! 🐞


---
**<p align="center">Generated by [ReadmeCodeGen](https://www.readmecodegen.com/)</p>**
