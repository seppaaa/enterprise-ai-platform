# AI-Powered Log Analyzer for Microservices

This is a full-stack starter project built for resume/interview use.

It allows a user to upload application logs and returns:

- Issue summary
- Severity
- Impacted service
- Probable root cause
- Suggested fix
- RCA summary

The backend currently uses mock AI logic, so you can run the project without an OpenAI API key. Later, you can replace `AiAnalysisService` with Spring AI/OpenAI integration.

---

## Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 database for local testing
- PostgreSQL dependency included for future use

### Frontend
- React
- Vite
- CSS

---

## Project Flow

1. User uploads a `.log` or `.txt` file from React UI.
2. Spring Boot receives the file using multipart upload.
3. `LogParserService` extracts important lines containing keywords like `ERROR`, `Exception`, `Kafka`, `Timeout`, `Redis`, `database`, and `500`.
4. `AiAnalysisService` analyzes the important lines and creates a structured response.
5. The result is saved in the database.
6. React displays the summary, severity, root cause, and suggested fix.

---

## How to Run Backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:ailoganalyzerdb
```

Username:

```text
sa
```

Password is empty.

---

## How to Run Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

## Test with Sample Log

Use this file:

```text
sample-logs/payment-kafka-timeout.log
```

Upload it from the UI and click **Analyze Log**.

---

## API Endpoints

### Analyze log file

```http
POST /api/logs/analyze
Content-Type: multipart/form-data
```

Form field:

```text
file
```

### Get all analyses

```http
GET /api/logs
```

### Get one analysis

```http
GET /api/logs/{id}
```

---

## Interview Explanation

I built an AI-powered log analysis platform using Java, Spring Boot, React, and PostgreSQL/H2. The purpose of this project is to reduce manual debugging time during production issues. A developer can upload application logs, and the backend extracts important error patterns such as exceptions, timeouts, Kafka failures, Redis connectivity issues, database errors, and HTTP 500 failures.

After extracting important log lines, the analysis service generates a structured response with summary, severity, impacted service, probable root cause, suggested fix, and RCA summary. I designed the application using controller, service, repository, DTO, and exception handling layers, similar to an enterprise microservices application.

In a real production version, I would integrate Spring AI/OpenAI for LLM-based analysis and Kafka for asynchronous processing of large log files.

---

## Resume Bullet

Developed an AI-powered Log Analyzer using Java, Spring Boot, React, PostgreSQL, and OpenAI/Spring AI concepts to summarize production logs, identify exception patterns, classify severity, and generate RCA recommendations for distributed microservices environments.

---

## Future Enhancements

- Replace mock AI with Spring AI/OpenAI ChatClient
- Add JWT login
- Add PostgreSQL profile
- Add Docker Compose
- Add Kafka async processing
- Add PDF RCA report export
- Add Splunk/Datadog API integration
