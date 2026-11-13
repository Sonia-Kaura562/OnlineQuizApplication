import React from 'react';

export default function QuestionCard({ question, index, selectedOption, onSelectOption }) {
  const isAnswered = Boolean(selectedOption);

  return (
    <article 
      className={`question-card ${isAnswered ? 'answered' : ''}`}
      id={`question-card-${question.id}`}
    >
      <div className="question-header">
        <span className="question-badge">{index + 1}</span>
        <h2 className="question-text" id={`question-title-${question.id}`}>
          {question.questionText}
        </h2>
      </div>

      <div 
        className="options-group" 
        role="radiogroup" 
        aria-labelledby={`question-title-${question.id}`}
      >
        {Object.entries(question.options).map(([optionKey, optionText]) => {
          const isSelected = selectedOption === optionKey;
          const inputId = `q${question.id}-opt-${optionKey}`;

          return (
            <label
              key={optionKey}
              htmlFor={inputId}
              className={`option-item ${isSelected ? 'selected' : ''}`}
              onClick={() => onSelectOption(question.id, optionKey)}
            >
              <input
                type="radio"
                id={inputId}
                name={`quiz-question-${question.id}`}
                value={optionKey}
                checked={isSelected}
                onChange={() => onSelectOption(question.id, optionKey)}
                className="hidden-radio"
              />
              <span className="custom-radio" aria-hidden="true" />
              <span className="option-key-label">{optionKey}</span>
              <span className="option-content">{optionText}</span>
            </label>
          );
        })}
      </div>
    </article>
  );
}
