import React, { useState } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState(null);

  const fetchData = () => {
    fetch("http://localhost:8080/test")
      .then((res) => res.text())  // Change this line
      .then((data) => setData(data))
      .catch((error) => console.log(error));
  };

  return (
    <div>
      <button onClick={fetchData}>Fetch Data</button>
      {data && 
      <pre>data : {data}</pre>
      }
    </div>
  );
}

export default App;
