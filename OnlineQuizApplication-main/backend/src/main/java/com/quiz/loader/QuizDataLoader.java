package com.quiz.loader;

import com.quiz.entity.Question;
import com.quiz.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(QuizDataLoader.class);

    private final QuestionRepository questionRepository;

    public QuizDataLoader(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        if (questionRepository.count() == 0) {
            logger.info("Initializing quiz database with default multiple-choice questions...");

            List<Question> initialQuestions = List.of(
                    new Question(
                            "Which of the following statements is TRUE regarding Java's Garbage Collection mechanism?",
                            "The programmer must manually deallocate memory using free() or delete()",
                            "Unreachable objects are automatically detected and memory is reclaimed by the JVM",
                            "Calling System.gc() guarantees immediate reclamation of all heap memory",
                            "Garbage collection completely eliminates all potential memory leaks",
                            "B",
                            "Java uses automatic garbage collection where the JVM reclaims memory of unreachable objects. System.gc() only suggests execution."
                    ),
                    new Question(
                            "In Spring Boot, which annotation is a meta-annotation that encapsulates @Configuration, @EnableAutoConfiguration, and @ComponentScan?",
                            "@SpringBootApplication",
                            "@EnableAutoConfiguration",
                            "@SpringBootConfiguration",
                            "@ComponentApplication",
                            "A",
                            "@SpringBootApplication is a convenience annotation that bundles @Configuration, @EnableAutoConfiguration, and @ComponentScan."
                    ),
                    new Question(
                            "In React, what is the primary purpose of the useEffect hook?",
                            "To mutate global Redux state directly during rendering",
                            "To define static stylesheet themes for child components",
                            "To synchronize components with external systems and execute side effects like data fetching",
                            "To prevent functional components from ever re-rendering",
                            "C",
                            "useEffect lets functional components manage side effects such as data fetching, subscriptions, and DOM event listeners."
                    ),
                    new Question(
                            "Which SQL constraint uniquely identifies each record in a database table and disallows NULL values?",
                            "FOREIGN KEY",
                            "PRIMARY KEY",
                            "UNIQUE",
                            "CHECK",
                            "B",
                            "A PRIMARY KEY uniquely identifies each row in a database table, enforces uniqueness, and implicitly forbids NULL values."
                    ),
                    new Question(
                            "Which HTTP response status code is standard when the client provides an invalid or malformed request payload?",
                            "400 Bad Request",
                            "401 Unauthorized",
                            "403 Forbidden",
                            "404 Not Found",
                            "A",
                            "HTTP 400 Bad Request is sent when the server cannot process the request due to client errors like invalid syntax or validation failures."
                    ),
                    new Question(
                            "What is the most effective primary defense against Cross-Site Scripting (XSS) in modern web applications?",
                            "Using HTTP instead of HTTPS",
                            "Context-aware output encoding and strict Content Security Policy (CSP)",
                            "Using GET requests for database updates",
                            "Storing sensitive credentials in localStorage",
                            "B",
                            "Proper contextual output encoding/escaping combined with a robust Content Security Policy stops injected scripts from executing."
                    )
            );

            questionRepository.saveAll(initialQuestions);
            logger.info("Successfully seeded {} questions into the database.", initialQuestions.size());
        } else {
            logger.info("Database already contains {} questions. Skipping data seeding.", questionRepository.count());
        }
    }
}
