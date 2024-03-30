# Fleet Master Application

This project is a car fleet management application designed to simplify the process of car rental and user management. It facilitates travel organization and efficient fleet management.

## How to Run
1. Clone this repository to your local machine.
2. Navigate to the cloned repository directory.
3. Run the application using Maven with the command:
   ```bash
   ./mvnw spring-boot:run
4. Ensure you have JDK 21 installed on your system.
5. Before running, make sure to set up your GitHub token in the `application.properties` file. Find the `application.properties` file in the project directory and insert your GitHub token in the designated place.
6. Ensure you have Java 21 installed on your computer.

### To run the application using Docker, follow these steps:
1. Clone this repository to your local machine.
2. Navigate to the cloned repository directory.
3. Run the application using Maven with the command:
   ```bash
   ./mvnw package
4. Build the Docker image using the following command:
   ```bash
   docker build -t car-fleet-management .
5. Run the Docker container using the following command:
   ```bash
   docker run -p 8080:80 car-fleet-management


## Dependencies

- **Spring Security:** Implemented for secure user authentication and authorization.
- **Spring Data JPA:** Utilized for easy integration with the PostgreSQL database, enabling seamless data access and manipulation.
- **Spring Web:** Employed to develop RESTful APIs for communication between the frontend and backend.
- **RestClient:** Utilized for making HTTP requests to external services or APIs.
- **Unit Testing:** Implemented to ensure the functionality of individual components or units of code.
- **Integration Testing:** Conducted to test the interactions between different parts of the system.
- **Docker:** Used for containerization, ensuring consistency across different environments.
- **GitHub Actions CI/CD Pipelines:** Implemented for automated testing, building, and deployment processes, ensuring continuous integration and delivery.
- **Java 21:** Project developed using Java 21 features and enhancements.
- **Lombok:** Used to reduce boilerplate code and simplify Java development.

This application leverages these dependencies, including PostgreSQL, to create a robust and efficient solution for managing car fleets and facilitating travel arrangements.

## Testing

Throughout the development process, the focus was primarily on writing comprehensive tests, including unit tests and integration tests. The project maintains a code coverage of 90%, ensuring thorough testing of the codebase.

For integration tests, the H2 in-memory database was utilized, providing a lightweight and efficient solution for database testing.
