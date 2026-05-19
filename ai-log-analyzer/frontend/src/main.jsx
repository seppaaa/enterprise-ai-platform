import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import './style.css';

const API_BASE = 'http://localhost:8080/api/logs';

function App() {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadHistory();
  }, []);

  async function loadHistory() {
    try {
      const response = await fetch(API_BASE);
      const data = await response.json();
      setHistory(data);
    } catch (err) {
      console.error(err);
    }
  }

  async function analyzeLog() {
    if (!file) {
      setError('Please select a .log or .txt file first.');
      return;
    }

    setLoading(true);
    setError('');
    setResult(null);

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await fetch(`${API_BASE}/analyze`, {
        method: 'POST',
        body: formData
      });

      if (!response.ok) {
        throw new Error('Unable to analyze log file.');
      }

      const data = await response.json();
      setResult(data);
      await loadHistory();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page">
      <header className="hero">
        <h1>AI Log Analyzer</h1>
        <p>Upload application logs and get summary, severity, root cause, and suggested fix.</p>
      </header>

      <section className="card upload-card">
        <h2>Upload Log File</h2>
        <input type="file" accept=".log,.txt" onChange={(e) => setFile(e.target.files[0])} />
        <button onClick={analyzeLog} disabled={loading}>
          {loading ? 'Analyzing...' : 'Analyze Log'}
        </button>
        {error && <p className="error">{error}</p>}
      </section>

      {result && <AnalysisResult result={result} />}

      <section className="card">
        <h2>Analysis History</h2>
        {history.length === 0 ? (
          <p>No previous analysis found.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>File</th>
                <th>Severity</th>
                <th>Service</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {history.map((item) => (
                <tr key={item.id} onClick={() => setResult(item)}>
                  <td>{item.id}</td>
                  <td>{item.fileName}</td>
                  <td><span className={`badge ${item.severity?.toLowerCase()}`}>{item.severity}</span></td>
                  <td>{item.impactedService}</td>
                  <td>{item.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </div>
  );
}

function AnalysisResult({ result }) {
  return (
    <section className="card result-card">
      <h2>Analysis Result</h2>
      <div className="grid">
        <Info title="File Name" value={result.fileName} />
        <Info title="Severity" value={result.severity} badge />
        <Info title="Impacted Service" value={result.impactedService} />
        <Info title="Status" value={result.status} />
      </div>
      <Info title="Summary" value={result.summary} />
      <Info title="Root Cause" value={result.rootCause} />
      <Info title="Suggested Fix" value={result.suggestedFix} />
      <Info title="RCA Summary" value={result.rcaSummary} />
    </section>
  );
}

function Info({ title, value, badge }) {
  return (
    <div className="info">
      <h3>{title}</h3>
      {badge ? <span className={`badge ${value?.toLowerCase()}`}>{value}</span> : <p>{value}</p>}
    </div>
  );
}

createRoot(document.getElementById('root')).render(<App />);
