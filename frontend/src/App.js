// src/App.js
import React, { useState } from 'react';
import './App.css';

function App() {
  const [swaraName, setSwaraName] = useState('');
  const [octaveOffset, setOctaveOffset] = useState(0);
  const [message, setMessage] = useState('');

  const playSwara = async () => {
    try {
      const response = await fetch(`http://localhost:8080/play/${swaraName}/${octaveOffset}`);
      const data = await response.text();
      setMessage(data);
    } catch (error) {
      setMessage('Error playing swara');
    }
  };

  return (
    <div className="App">
      <h1>Play Swara</h1>
      <input
        type="text"
        placeholder="Swara Name"
        value={swaraName}
        onChange={(e) => setSwaraName(e.target.value)}
      />
      <input
        type="number"
        placeholder="Octave Offset"
        value={octaveOffset}
        onChange={(e) => setOctaveOffset(Number(e.target.value))}
      />
      <button onClick={playSwara}>Play</button>
      <p>{message}</p>
    </div>
  );
}

export default App;