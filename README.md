# Notes App Server
The Notes App Server is a backend server for the Notes App Android client that is responsible for accepting requests from the client and saving data to a MySQL database. It is written in Java using Spring framework and is designed using best practices and design patterns.

## Prerequisites
### Before running the server code locally, ensure that you have the following installed:

- Java 11 or later
- MySQL command line client
- IntelliJ IDEA

## Getting Started
1. Clone the repository onto your local machine:
git clone https://github.com/miaSara22/NotesAppBackend.git

2. Customize the MySQL username and password in the application.properties file if needed.

3. Open the project in IntelliJ IDEA and run the server by running the NotesAppServerApplication class.

4. The server will run on localhost:9000.

## Features and Functionalities
- Accepts requests from the Notes App Android client via Retrofit and saves data to a MySQL database.
- Secure server using Spring Security and JWT.

## Input and Output Format
The server-side code expects requests to be sent in JSON format and returns responses in JSON format.

## Dependencies
The required dependencies for the project can be found in the build.gradle file.

## Contributing
Contributions to the project are welcome. To contribute, please fork the repository, make changes, and submit a pull request.
