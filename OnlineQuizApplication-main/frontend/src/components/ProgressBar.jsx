import React from 'react';
import { CheckCircle2 } from 'lucide-react';

export default function ProgressBar({ total, answered }) {
  const percentage = total > 0 ? Math.round((answered / total) * 100) : 0;

  return (
    <div className="sticky-progress" role="region" aria-label="Quiz progress">
      <div className="progress-header">
        <span className="progress-label">
          <CheckCircle2 size={16} /> Progress
        </span>
        <span className="progress-counter">
          <strong>{answered}</strong> of <strong>{total}</strong> questions answered ({percentage}%)
        </span>
      </div>
      <div className="progress-bar-track">
        <div 
          className="progress-bar-fill" 
          style={{ width: `${percentage}%` }}
          aria-valuenow={percentage}
          aria-valuemin="0"
          aria-valuemax="100"
          role="progressbar"
        />
      </div>
    </div>
  );
}
