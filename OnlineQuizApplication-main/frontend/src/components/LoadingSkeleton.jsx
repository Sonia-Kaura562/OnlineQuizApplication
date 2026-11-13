import React from 'react';

export default function LoadingSkeleton() {
  return (
    <div className="quiz-questions-list" aria-busy="true" aria-label="Loading quiz questions">
      {[1, 2, 3].map((item) => (
        <div key={item} className="skeleton-card">
          <div className="skeleton-line" style={{ height: '24px', width: '60%' }} />
          <div className="skeleton-line" style={{ height: '16px', width: '90%' }} />
          <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem', marginTop: '1rem' }}>
            <div className="skeleton-line" style={{ height: '48px', width: '100%' }} />
            <div className="skeleton-line" style={{ height: '48px', width: '100%' }} />
            <div className="skeleton-line" style={{ height: '48px', width: '100%' }} />
            <div className="skeleton-line" style={{ height: '48px', width: '100%' }} />
          </div>
        </div>
      ))}
    </div>
  );
}
