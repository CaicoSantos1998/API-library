# API-library
A robust API for books management, developed in Java with PostgreSQL persistence and containerization via Docker.

## About Project
This API allowed users to perform CRUD (Create, Read, Update, Delete) operations on books, manage storage, and query works within a library.

## Technologies
* **Language:** Java (Spring Boot suggested)
* **Database:** PostgreSQL
* **Containerization:** Docker & Docker compose
* **Dependency management:** Maven or Gradle.

## Endpoints (Example)
* GET/books - List all books.
* POST/books - Register a new book.
* GET/books/{id} - Get details about a specific book.
* GET/authors - List all authors.
* POST/authors - Register a new author.
* GET/authors/{id} - Get details about a specific author.

## Setup
1 - Clone the repository.

2 - Create a `.env` file based on `.env.example`.

3 - Run `docker-compose up -d`.

## License
See file [LICENSE](LICENSE) to details.
