import React from 'react';
import { Sparkles, Terminal } from 'lucide-react';

export default function Header() {
  return (
    <header className="app-header">
      <div className="brand-badge">
        <Sparkles size={14} /> Full-Stack Engineering Quiz
      </div>
      <h1 className="app-title">QuizMaster Pro</h1>
      <p className="app-subtitle">
        Test your knowledge of Java, Spring Boot, React, SQL, and Web Security.
        Answers are verified securely by the Spring Boot backend.
      </p>
    </header>
  );
}
