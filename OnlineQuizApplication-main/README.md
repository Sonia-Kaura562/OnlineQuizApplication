# Online Quiz Application 🚀

A full-stack, responsive, and secure Online Quiz Application built with **React (Vite)** on the frontend, **Spring Boot 3 (Java 17)** on the backend, and **MySQL 8** for persistent database storage.

Designed for seamless deployment on **Vercel** (Frontend) and **Render** (Backend).

---

## 🌟 Key Features

1. **Multiple-Choice Questions**: Displays 6 curated technical questions across Java, Spring Boot, React, SQL, HTTP, and Web Security, each with 4 distinct options.
2. **Single Selection**: Accessible custom radio buttons allowing exactly one answer per question with smooth micro-animations.
3. **Backend Score Calculation**: Submissions are scored entirely on the Spring Boot backend with full accuracy calculation and grading feedback.
4. **Answer Concealment & Security**: Correct options and explanations are **never** exposed through the questions API before submission.
5. **Score & Explanations Reveal**: Detailed breakdown displaying final score, percentage, correct answers, and thorough explanations revealed only after submitting.
6. **Automatic Database Seeding**: Pre-seeds questions into MySQL upon startup if the database table is empty.
7. **Retake Quiz Flow**: Instantly reset answers and retake the quiz with a single click.
8. **Live Health Checks**: Public health endpoint at `/api/health` and Spring Boot Actuator at `/actuator/health`.
9. **CORS Configured**: Dynamic CORS origin configuration supporting local dev and Vercel domains.

---

## 🏗️ Architecture & Technology Stack

| Layer | Technology | Details |
|---|---|---|
| **Frontend** | React 18 + Vite | Vanilla CSS design tokens, Lucide icons, Canvas Confetti |
| **Backend** | Java 17 + Spring Boot 3.3.4 | Spring Data JPA, Hibernate, Spring Web, Actuator, Maven |
| **Database** | MySQL 8 | Auto-migration & seed data runner |
| **Frontend Host** | Vercel | Single-Page Application (SPA) routing with `vercel.json` |
| **Backend Host** | Render | Dockerized container runtime with dynamic environment variables |

---

## 📁 Repository Structure

```
OnlineQuizApplication/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/quiz/
│   │   │   │   ├── config/WebConfig.java          # CORS & Web MVC settings
│   │   │   │   ├── controller/
│   │   │   │   │   ├── QuizController.java       # /api/quiz endpoints
│   │   │   │   │   └── HealthController.java     # /api/health endpoint
│   │   │   │   ├── dto/                          # Safe DTOs & submission models
│   │   │   │   ├── entity/Question.java          # JPA entity
│   │   │   │   ├── exception/                    # Global error handler
│   │   │   │   ├── loader/QuizDataLoader.java    # Automatic MySQL data seeder
│   │   │   │   ├── repository/QuestionRepository.java
│   │   │   │   ├── service/QuizService.java      # Score evaluation engine
│   │   │   │   └── QuizApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties        # Env variable overrides
│   │   └── test/                                 # Unit & MockMvc integration tests
│   ├── Dockerfile                                # Multi-stage build for Render
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Header.jsx
│   │   │   ├── ProgressBar.jsx
│   │   │   ├── QuestionCard.jsx
│   │   │   ├── QuizResult.jsx
│   │   │   ├── ErrorMessage.jsx
│   │   │   └── LoadingSkeleton.jsx
│   │   ├── services/api.js                       # API client (fetch)
│   │   ├── App.jsx
│   │   ├── index.css                             # Glassmorphism & design system
│   │   └── main.jsx
│   ├── .env.example
│   ├── .env
│   ├── index.html
│   ├── package.json
│   ├── vercel.json                               # Vercel SPA rewrites
│   └── vite.config.js
└── README.md
```

---

## 🔌 API Endpoints

### 1. `GET /api/quiz/questions`
Fetches all available questions without revealing correct options or explanations.
- **Response `200 OK`**:
```json
[
  {
    "id": 1,
    "questionText": "Which of the following statements is TRUE regarding Java's Garbage Collection mechanism?",
    "options": {
      "A": "The programmer must manually deallocate memory using free() or delete()",
      "B": "Unreachable objects are automatically detected and memory is reclaimed by the JVM",
      "C": "Calling System.gc() guarantees immediate reclamation of all heap memory",
      "D": "Garbage collection completely eliminates all potential memory leaks"
    }
  }
]
```

### 2. `POST /api/quiz/submit`
Calculates score and reveals results, percentages, correct answers, and explanations.
- **Request Body**:
```json
{
  "answers": {
    "1": "B",
    "2": "A",
    "3": "C"
  }
}
```
- **Response `200 OK`**:
```json
{
  "totalQuestions": 6,
  "attemptedQuestions": 3,
  "correctAnswersCount": 3,
  "wrongAnswersCount": 0,
  "scorePercentage": 50.0,
  "performanceSummary": "Good effort! Review the explanations for questions you missed.",
  "results": [
    {
      "questionId": 1,
      "questionText": "Which of the following statements is TRUE regarding Java's Garbage Collection mechanism?",
      "options": { "A": "...", "B": "...", "C": "...", "D": "..." },
      "selectedOption": "B",
      "correctOption": "B",
      "isCorrect": true,
      "explanation": "Java uses automatic garbage collection..."
    }
  ]
}
```

### 3. `GET /actuator/health` or `GET /api/health`
Health check endpoint reporting service and database status.
- **Response `200 OK`**:
```json
{
  "status": "UP",
  "service": "online-quiz-backend"
}
```

---

## 💻 Local Development Setup

### Prerequisites
- **Java**: 17+ (JDK 17 or higher)
- **Maven**: 3.8+
- **Node.js**: 18+ and `npm`
- **MySQL**: 8.0+

### Step 1: Prepare MySQL Database
Open your MySQL terminal or client and run:
```sql
CREATE DATABASE IF NOT EXISTS quizdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Step 2: Configure & Run the Backend
Navigate to the `backend` folder:
```bash
cd backend
```

Set environment variables if your MySQL credentials differ from defaults (`root`/`root`):
```powershell
# Windows PowerShell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="quizdb"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"

# Or Linux / macOS
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=quizdb
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

Run tests to verify:
```bash
mvn clean test
```

Start the Spring Boot backend:
```bash
mvn spring-boot:run
```
Backend will start at: `http://localhost:8080` (questions will be auto-seeded).

### Step 3: Run the Frontend
In a new terminal window, navigate to the `frontend` folder:
```bash
cd frontend
npm install
npm run dev
```
Open `http://localhost:5173/` in your browser.

---

## 🚀 Deployment Guide

### A. Deploy Backend to Render

1. Push your repository to GitHub.
2. Sign in to [Render](https://render.com/) and click **New > Web Service**.
3. Connect your GitHub repository.
4. Set the following settings:
   - **Root Directory**: `backend`
   - **Environment**: `Docker` (Render will automatically detect `backend/Dockerfile`)
5. In **Environment Variables**, configure:
   | Variable | Value |
   |---|---|
   | `SPRING_DATASOURCE_URL` | `jdbc:mysql://<your-remote-mysql-host>:3306/<db_name>?createDatabaseIfNotExist=true&useSSL=true` |
   | `SPRING_DATASOURCE_USERNAME` | `<remote-mysql-username>` |
   | `SPRING_DATASOURCE_PASSWORD` | `<remote-mysql-password>` |
   | `CORS_ALLOWED_ORIGINS` | `https://your-frontend.vercel.app,http://localhost:5173` |
6. Click **Deploy Web Service**.
7. Test the deployed backend health check: `https://your-backend.onrender.com/actuator/health`.

---

### B. Deploy Frontend to Vercel

1. Push your code to GitHub.
2. Sign in to [Vercel](https://vercel.com/) and click **Add New > Project**.
3. Import your GitHub repository.
4. Set the project configuration:
   - **Framework Preset**: `Vite`
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`
5. In **Environment Variables**, add:
   | Variable | Value |
   |---|---|
   | `VITE_API_BASE_URL` | `https://your-backend.onrender.com` |
6. Click **Deploy**.
7. Vercel will build and assign an HTTPS URL (e.g., `https://online-quiz-frontend.vercel.app`).
8. Add this URL to the `CORS_ALLOWED_ORIGINS` variable on your Render backend.

---

## 🧪 Verification & Build Checks

- **Backend tests**:
  ```bash
  cd backend
  mvn test
  ```
  *Result*: 7 tests pass (Service unit tests, MockMvc controller tests, Application context tests).

- **Frontend build**:
  ```bash
  cd frontend
  npm run build
  ```
  *Result*: Builds optimized static assets to `dist/` without warnings.
