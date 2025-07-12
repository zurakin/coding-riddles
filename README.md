# Coding Riddles

Welcome to the Coding Riddles project! This project aims to create a website dedicated to coding riddles, similar to coding games but focused solely on riddles.

## Project Goal

The primary goal of this project is to provide a platform where users can solve coding riddles and receive immediate feedback on their solutions. Riddles are often challenging to explain and validate, making it difficult to know if your solution is correct. This project aims to resolve that issue by offering a system that can accurately validate and provide feedback on riddle solutions.

## Features

- A collection of coding riddles of varying difficulty levels.
- An interface for users to submit their solutions and receive instant validation.
- Detailed explanations and hints for each riddle to aid in understanding and solving them.

---

## Getting Started

### Prerequisites
- Java 23 (or use the provided Dockerfile)
- Node.js (for the frontend)
- Maven (for backend, or use `mvnw`/`mvnw.cmd`)
- Docker (optional, for containerized deployment)

### Backend (Spring Boot)

1. **Build the backend:**
   ```sh
   cd backend
   ./mvnw clean package
   # or on Windows
   mvnw.cmd clean package
   ```
2. **Run locally:**
   ```sh
   java -jar target/coding-riddles.jar
   ```
3. **Run with Docker:**
   ```sh
   docker build -t codingriddles-backend .
   docker run -p 8080:8080 codingriddles-backend
   ```

### Frontend (Vue + Vite)

1. **Install dependencies:**
   ```sh
   cd frontend
   npm install
   ```
2. **Run the dev server:**
   ```sh
   npm run dev
   ```

---

## Deployment

### Using Docker Compose (Recommended)


1. Build the frontend and backend first:
   ```sh
   cd frontend
   npm install
   npm run build
   cd ../backend
   ./mvnw clean package
   # or on Windows
   mvnw.cmd clean package
   cd ..
   ```
2. Copy `.env.example` to `.env` and fill in your database username and password:
   ```sh
   cp .env.example .env
   # Edit .env and set DB_USERNAME and DB_PASSWORD
   ```
3. Start the backend and database together:
   ```sh
   docker compose up --build
   ```
   This will start both the PostgreSQL database and the backend, with credentials loaded from your `.env` file.

### Manual Backend Deployment

- The backend can still be deployed as a standalone JAR or Docker container.
- The frontend can be built with `npm run build`. **You must then manually copy the output in `frontend/dist` to the backend's `src/main/resources/public` directory:**
  ```sh
  cp -r frontend/dist/* backend/src/main/resources/public/
  ```
- Rebuild and run the backend. The Spring Boot server will now serve the frontend at the root URL.

---

## License

This project is licensed under a custom license. You may use, copy, modify, and distribute this software for personal and non-commercial purposes only. Commercial use is strictly prohibited without prior written permission from the author.

See the LICENSE file for full details.
