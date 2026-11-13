import React, { useEffect } from 'react';
import confetti from 'canvas-confetti';
import { 
  Trophy, 
  RotateCcw, 
  CheckCircle, 
  XCircle, 
  HelpCircle, 
  Award, 
  AlertCircle 
} from 'lucide-react';

export default function QuizResult({ result, onRetake }) {
  const {
    totalQuestions,
    attemptedQuestions,
    correctAnswersCount,
    wrongAnswersCount,
    scorePercentage,
    performanceSummary,
    results = [],
  } = result;

  useEffect(() => {
    if (scorePercentage >= 70) {
      try {
        confetti({
          particleCount: 100,
          spread: 70,
          origin: { y: 0.6 }
        });
      } catch {
        // Confetti is a visual bonus; safe fallback
      }
    }
  }, [scorePercentage]);

  return (
    <div className="results-container" id="quiz-results-container">
      {/* Primary Score Summary Card */}
      <div className="results-card">
        <div className="score-badge-wrapper">
          <div className="score-badge-circle">
            <span className="score-percentage">{Math.round(scorePercentage)}%</span>
            <span className="score-label">Score</span>
          </div>
        </div>

        <h2 className="result-headline">
          {scorePercentage >= 80 ? (
            <span style={{ display: 'inline-flex', alignItems: 'center', gap: '0.5rem' }}>
              <Trophy size={28} color="#f59e0b" /> Outstanding Performance!
            </span>
          ) : scorePercentage >= 60 ? (
            <span style={{ display: 'inline-flex', alignItems: 'center', gap: '0.5rem' }}>
              <Award size={28} color="#6366f1" /> Great Effort!
            </span>
          ) : (
            <span style={{ display: 'inline-flex', alignItems: 'center', gap: '0.5rem' }}>
              <AlertCircle size={28} color="#ef4444" /> Quiz Completed
            </span>
          )}
        </h2>

        <p className="result-summary-text">{performanceSummary}</p>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-item">
            <div className="stat-val">{totalQuestions}</div>
            <div className="stat-name">Total</div>
          </div>
          <div className="stat-item">
            <div className="stat-val">{attemptedQuestions}</div>
            <div className="stat-name">Attempted</div>
          </div>
          <div className="stat-item">
            <div className="stat-val success">{correctAnswersCount}</div>
            <div className="stat-name">Correct</div>
          </div>
          <div className="stat-item">
            <div className="stat-val danger">{wrongAnswersCount}</div>
            <div className="stat-name">Wrong / Skipped</div>
          </div>
        </div>

        {/* Retake Button */}
        <button 
          id="retake-quiz-btn" 
          onClick={onRetake} 
          className="btn-retake"
          type="button"
        >
          <RotateCcw size={18} /> Retake Quiz
        </button>
      </div>

      {/* Detailed Question Review & Correct Answers */}
      <section className="review-section" aria-label="Detailed Answer Review">
        <h3 className="review-section-title">
          <HelpCircle size={22} color="#818cf8" /> Question Review & Detailed Explanations
        </h3>

        {results.map((qResult, idx) => {
          const isCorrect = qResult.isCorrect;
          const userChoice = qResult.selectedOption;
          const correctChoice = qResult.correctOption;

          return (
            <article 
              key={qResult.questionId} 
              className={`result-card-item ${isCorrect ? 'correct' : 'incorrect'}`}
              id={`review-question-${qResult.questionId}`}
            >
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <span className="question-badge">{idx + 1}</span>
                <span className={`result-status-tag ${isCorrect ? 'correct' : 'incorrect'}`}>
                  {isCorrect ? (
                    <>
                      <CheckCircle size={15} /> Correct (+1)
                    </>
                  ) : userChoice ? (
                    <>
                      <XCircle size={15} /> Incorrect
                    </>
                  ) : (
                    <>
                      <AlertCircle size={15} /> Skipped
                    </>
                  )}
                </span>
              </div>

              <h4 className="question-text" style={{ margin: '1rem 0' }}>
                {qResult.questionText}
              </h4>

              {/* Options Breakdown */}
              <div className="review-options-list">
                {Object.entries(qResult.options).map(([key, text]) => {
                  const isUserSelection = userChoice === key;
                  const isTheCorrectOption = correctChoice === key;

                  let optionClass = 'review-option';
                  if (isTheCorrectOption) {
                    optionClass += ' is-correct-choice';
                  } else if (isUserSelection) {
                    optionClass += ' is-user-choice';
                  }

                  return (
                    <div key={key} className={optionClass}>
                      <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                        <span className="option-key-label">{key}</span>
                        <span className="option-content">{text}</span>
                      </div>
                      <div>
                        {isTheCorrectOption && (
                          <span className="option-tag correct">Correct Answer</span>
                        )}
                        {isUserSelection && !isTheCorrectOption && (
                          <span className="option-tag user">Your Answer</span>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>

              {/* Explanation Reveal */}
              {qResult.explanation && (
                <div className="explanation-box">
                  <div className="explanation-title">
                    <HelpCircle size={16} /> Explanation:
                  </div>
                  <p>{qResult.explanation}</p>
                </div>
              )}
            </article>
          );
        })}

        <div style={{ textAlign: 'center', marginTop: '1.5rem' }}>
          <button 
            id="retake-quiz-btn-bottom" 
            onClick={onRetake} 
            className="btn-primary"
            type="button"
          >
            <RotateCcw size={18} /> Retake Quiz Now
          </button>
        </div>
      </section>
    </div>
  );
}
