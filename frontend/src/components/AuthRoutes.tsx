import React, { ReactNode, useContext } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import CredentialContext from '@/context/CredentialContext';
import Navbar from './Navbar';
import { UserProvider } from '@/context/UserContext';

export default function AuthRoute({ children }: { children: ReactNode }) {
  const { username, password } = useContext(CredentialContext);
  const location = useLocation();

  if (!username || !password) {
    return <Navigate to="/login" state={{ from: location }} />;
  }

  return (
    <UserProvider>
      <Navbar />
      <div className="flex flex-1 flex-row mx-auto max-w-3xl w-full gap-2">
        <div className="flex flex-col w-full">{children}</div>
      </div>
    </UserProvider>
  );
}
