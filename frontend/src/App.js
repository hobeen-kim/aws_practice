import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState(null);
  const [authData, setAuthData] = useState(null); 
  const [csrfToken, setCsrfToken] = useState(null); // CSRF 토큰을 위한 state
  const [username, setUsername] = useState(''); // For form login
  const [password, setPassword] = useState(''); // For form login
  const URI = "http://ec2-3-34-138-44.ap-northeast-2.compute.amazonaws.com";

  const fetchData = () => {
    fetch(URI + "/userInfo", {
      credentials: 'include' // Include this line
    })
      .then((res) => res.text())  // Change this line
      .then((data) => setData(data))
      .catch((error) => console.log(error));
  };

  const fetchAuthData = () => {
    fetch(URI + "/auth", {
      credentials: 'include',
      // headers: {
      //   "X-CSRF-TOKEN": csrfToken  // 헤더에 CSRF 토큰 추가
      // },
    })
      .then((res) => res.text())  // Change this line
      .then((data) => setAuthData(data))
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    // CSRF 토큰 받아오기
    fetch(URI + "/csrf", {
      credentials: 'include'
    })
      .then((res) => {
        setCsrfToken(res.headers.get('X-CSRF-TOKEN'))
      })
  }, []);

  const handleLogin = (e) => {
    e.preventDefault();

    fetch(URI + "/form/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        "X-CSRF-TOKEN": csrfToken  // 헤더에 CSRF 토큰 추가
      },
      credentials: 'include',
      body: new URLSearchParams({
        username: username,
        password: password
      }).toString(),
    })
      .then((res) => {
        if (res.ok) {
          // Successfully logged in
          // Do something here...
        } else {
          // Handle login error
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
    <div>
      <button onClick={fetchData}>인증된 사용자 정보</button>
      {data && 
      <pre>data : {data}</pre>
      }
      <button onClick={fetchAuthData}>인증 사용자만 보이는 데이터</button>
      {authData && 
      <pre>data : {authData}</pre>
      }
      <div>
        <form onSubmit={handleLogin}>
          <input 
            type="text" 
            placeholder="Username" 
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input 
            type="password" 
            placeholder="Password" 
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit">로그인</button>
        </form>
      </div>
      <div>
      <a href={`${URI}/oauth2/authorization/google`}>구글 로그인</a>
      </div>
      <div>
      <a href={`${URI}/oauth2/authorization/kakao`}>카카오 로그인</a>
      </div>
      <div>
      <a href={`${URI}/oauth2/authorization/github`}>깃허브 로그인</a>
      </div>
      

    </div>
  </>
  );
}

export default App;
