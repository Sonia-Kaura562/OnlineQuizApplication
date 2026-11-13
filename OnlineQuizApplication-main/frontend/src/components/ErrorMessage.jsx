import React from 'react';
import { AlertTriangle, RefreshCw } from 'lucide-react';

export default function ErrorMessage({ message, onRetry }) {
  return (
    <div className="error-banner" role="alert" id="quiz-error-banner">
      <AlertTriangle size={24} style={{ flexShrink: 0, marginTop: '2px' }} />
      <div style={{ flexGrow: 1 }}>
        <h3 className="error-title">Unable to complete request</h3>
        <p style={{ fontSize: '0.95rem' }}>{message}</p>
        {onRetry && (
          <button 
            onClick={onRetry} 
            className="btn-retake" 
            style={{ marginTop: '0.75rem', padding: '0.5rem 1rem', fontSize: '0.9rem' }}
            type="button"
          >
            <RefreshCw size={14} /> Retry Connection
          </button>
        )}
      </div>
    </div>
  );
}
