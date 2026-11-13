import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import ProgressBar from './components/ProgressBar';
import QuestionCard from './components/QuestionCard';
import QuizResult from './components/QuizResult';
import ErrorMessage from './components/ErrorMessage';
import LoadingSkeleton from './components/LoadingSkeleton';
import { fetchQuizQuestions, submitQuizAnswers, checkBackendHealth } from './services/api';
import { Send, AlertCircle, Server } from 'lucide-react';

export default function App() {
  const [questions, setQuestions] = useState([]);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const [submitWarning, setSubmitWarning] = useState(null);
  const [backendOnline, setBackendOnline] = useState(true);

  // Load questions on initial mount
  const loadQuestions = async () => {
    setIsLoading(true);
    setError(null);
    setSubmitWarning(null);
    try {
      const data = await fetchQuizQuestions();
      setQuestions(data);
      setBackendOnline(true);
    } catch (err) {
      console.error('Failed to load questions:', err);
      setError(err.message || 'Unable to connect to the quiz backend. Please make sure the Spring Boot service is running.');
      setBackendOnline(false);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadQuestions();
    // Periodically verify health
    checkBackendHealth().then((isUp) => setBackendOnline(isUp));
  }, []);

  // Handle radio option selection
  const handleSelectOption = (questionId, optionKey) => {
    setSelectedAnswers((prev) => ({
      ...prev,
      [questionId]: optionKey,
    }));
    setSubmitWarning(null);
  };

  // Handle quiz submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSubmitWarning(null);

    const answeredCount = Object.keys(selectedAnswers).length;
    if (answeredCount === 0) {
      setSubmitWarning('Please answer at least one question before submitting!');
      return;
    }

    if (answeredCount < questions.length && !submitWarning) {
      const unanswered = questions.length - answeredCount;
      setSubmitWarning(`You have ${unanswered} unanswered question${unanswered > 1 ? 's' : ''}. Click Submit again if you wish to proceed without answering them.`);
      return;
    }

    setIsSubmitting(true);
    try {
      const submissionResponse = await submitQuizAnswers(selectedAnswers);
      setResult(submissionResponse);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
      console.error('Submission failed:', err);
      setError(err.message || 'Failed to submit quiz answers to backend.');
    } finally {
      setIsSubmitting(false);
    }
  };

  // Handle Retake Quiz
  const handleRetake = () => {
    setSelectedAnswers({});
    setResult(null);
    setError(null);
    setSubmitWarning(null);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const answeredCount = Object.keys(selectedAnswers).length;
  const totalCount = questions.length;

  return (
    <div className="app-container">
      <Header />

      <main>
        {error && <ErrorMessage message={error} onRetry={loadQuestions} />}

        {isLoading ? (
          <LoadingSkeleton />
        ) : result ? (
          <QuizResult result={result} onRetake={handleRetake} />
        ) : (
          <>
            {totalCount > 0 && (
              <ProgressBar total={totalCount} answered={answeredCount} />
            )}

            <form onSubmit={handleSubmit} style={{ marginTop: '1.5rem' }}>
              <div className="quiz-questions-list">
                {questions.map((question, index) => (
                  <QuestionCard
                    key={question.id}
                    question={question}
                    index={index}
                    selectedOption={selectedAnswers[question.id]}
                    onSelectOption={handleSelectOption}
                  />
                ))}
              </div>

              {submitWarning && (
                <div 
                  className="error-banner" 
                  style={{ marginTop: '1.5rem', background: 'var(--warning-bg)', borderColor: 'rgba(245, 158, 11, 0.35)', color: '#fde68a' }}
                >
                  <AlertCircle size={20} style={{ flexShrink: 0, color: '#f59e0b' }} />
                  <div>
                    <span style={{ fontWeight: 600 }}>Attention: </span>
                    {submitWarning}
                  </div>
                </div>
              )}

              <div className="submit-section">
                <button
                  type="submit"
                  id="submit-quiz-btn"
                  className="btn-primary"
                  disabled={isSubmitting || questions.length === 0}
                >
                  {isSubmitting ? (
                    'Evaluating Answers...'
                  ) : (
                    <>
                      <Send size={18} /> Submit Quiz
                    </>
                  )}
                </button>
                <p className="submit-helper">
                  {answeredCount === totalCount && totalCount > 0
                    ? 'All questions answered! Click submit to calculate your score.'
                    : `Answered ${answeredCount} of ${totalCount} questions.`}
                </p>
              </div>
            </form>
          </>
        )}
      </main>

      <footer className="app-footer">
        <p>Online Quiz Application &bull; Full-Stack React + Spring Boot + MySQL 8</p>
        <div className="api-badge">
          <Server size={12} />
          <span>API Backend:</span>
          <span 
            className="status-dot" 
            style={{ background: backendOnline ? 'var(--success)' : 'var(--danger)', boxShadow: backendOnline ? '0 0 6px var(--success)' : '0 0 6px var(--danger)' }} 
          />
          <span>{backendOnline ? 'Online' : 'Offline / Reconnecting'}</span>
        </div>
      </footer>
    </div>
  );
}
