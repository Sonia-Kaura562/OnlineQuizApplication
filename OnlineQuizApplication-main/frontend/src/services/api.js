// API service for Online Quiz Application

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL || '').replace(/\/$/, '');

/**
 * Fetch quiz questions from backend.
 * Guarantees that no correct answers are returned in this response.
 */
export async function fetchQuizQuestions() {
  const url = `${API_BASE_URL}/api/quiz/questions`;
  const response = await fetch(url, {
    headers: {
      'Accept': 'application/json',
    },
  });

  if (!response.ok) {
    let errorMsg = `Server returned HTTP ${response.status} (${response.statusText})`;
    try {
      const errorJson = await response.json();
      if (errorJson.message) errorMsg = errorJson.message;
    } catch {
      // fallback
    }
    throw new Error(errorMsg);
  }

  return response.json();
}

/**
 * Submit user answers and receive calculated score, percentage, and correct answers.
 * @param {Object} answers - Map of { [questionId]: selectedOptionKey }
 */
export async function submitQuizAnswers(answers) {
  const url = `${API_BASE_URL}/api/quiz/submit`;
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify({ answers }),
  });

  if (!response.ok) {
    let errorMsg = `Submission failed with HTTP ${response.status}`;
    try {
      const errorJson = await response.json();
      if (errorJson.message) errorMsg = errorJson.message;
    } catch {
      // fallback
    }
    throw new Error(errorMsg);
  }

  return response.json();
}

/**
 * Health check to verify backend connectivity.
 */
export async function checkBackendHealth() {
  const url = `${API_BASE_URL}/api/health`;
  try {
    const response = await fetch(url);
    return response.ok;
  } catch {
    return false;
  }
}
