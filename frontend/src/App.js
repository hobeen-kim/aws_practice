import React, { useState } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState(null);
  const [DBdata, setDBData] = useState(null);

  const fetchData = () => {
    fetch("http://ec2-3-34-138-44.ap-northeast-2.compute.amazonaws.com/test")
      .then((res) => res.text())  // Change this line
      .then((data) => setData(data))
      .catch((error) => console.log(error));
  };

  const fetchDBData = () => {
    fetch("http://ec2-3-34-138-44.ap-northeast-2.compute.amazonaws.com/test/name1")
      .then((res) => res.text())  // Change this line
      .then((data) => setDBData(data))
      .catch((error) => console.log(error));
  };

  return (
    <>
    <div>
      <button onClick={fetchData}>Fetch Data</button>
      {data && 
      <pre>data : {data}</pre>
      }
    </div>
    <div>
    <button onClick={fetchDBData}>Fetch DBdata</button>
    {data && 
    <pre>DBdata : {DBdata}</pre>
    }
  </div>
  </>
  );
}

export default App;
