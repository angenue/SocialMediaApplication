import React, { ReactNode, useState } from 'react';

// Create the context with default values
const CredentialContext = React.createContext({
  username: '',
  password: '',
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  setCredentials: (username: string, password: string) => {},
});

export const UserProvider = ({ children }: { children: ReactNode }) => {
  // Initialize username and password from local storage
  const [username, setUsername] = useState(
    localStorage.getItem('username') || ''
  );
  const [password, setPassword] = useState(
    localStorage.getItem('password') || ''
  );

  const setCredentials = (username: string, password: string) => {
    setUsername(username);
    setPassword(password);

    // Store username and password in local storage
    localStorage.setItem('username', username);
    localStorage.setItem('password', password);
  };

  return (
    <CredentialContext.Provider value={{ username, password, setCredentials }}>
      {children}
    </CredentialContext.Provider>
  );
};

export default CredentialContext;
