# 📝 ToDoList API

A RESTful API for task management with user authentication, built with Spring Boot.

---

## 🚀 Technologies

- Java 21
- Spring Boot 4
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Lombok
- Docker

---

## ⚙️ How to Run

### 1. Start the database

```bash
docker run --name mysql-tasks -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tasksdb -p 3306:3306 -d mysql:8
```

### 2. Configure `application.yml`

Create `src/main/resources/application.yml` based on `application.yml.example` and fill in your credentials.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 🔐 Authentication

This API uses JWT. To access protected routes, include the token in the request header:

```
Authorization: Bearer <your_token>
```

---

## 📡 Endpoints

### Auth

| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|---------------|
| POST | `/auth/login` | Login and receive JWT token | ❌ |

### Users

| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|---------------|
| POST | `/users/create` | Register a new user | ❌ |

### Tasks

| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|---------------|
| POST | `/tasks/create` | Create a new task | ✅ |
| GET | `/tasks/list` | List all tasks for the logged-in user | ✅ |
| GET | `/tasks/list?status=PENDING` | Filter tasks by status | ✅ |
| PATCH | `/tasks/{id}` | Update a task | ✅ |
| DELETE | `/tasks/{id}` | Delete a task | ✅ |

---

## 📦 Request Examples

### Register user
```json
POST /users/create
{
  "name": "Mikael",
  "email": "mike@email.com",
  "password": "12345678"
}
```

### Login
```json
POST /auth/login
{
  "email": "mike@email.com",
  "password": "12345678"
}
```

### Create task
```json
POST /tasks/create
{
  "title": "Study Spring Boot",
  "description": "Complete the task management project"
}
```

### Update task
```json
PATCH /tasks/1
{
  "title": "Updated title",
  "status": "COMPLETED"
}
```

---

## 🗂️ Project Structure

```
src/main/java/com/mika/tasks
├── config/         # Security configuration
├── controller/     # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Custom exceptions and global handler
├── filter/         # JWT authentication filter
├── repository/     # Spring Data repositories
└── service/        # Business logic
```