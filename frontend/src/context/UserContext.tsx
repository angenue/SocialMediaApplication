import React, { createContext, useState, useEffect, ReactNode } from 'react';
import { useApi } from '@/lib/api';

type User = {
  id: number;
  username: string;
  email: string;
  bio: string;
  profilePicture: string;
  name: string;
};

// Create the context with default values
const UserContext = createContext({
  user: null as User | null,
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  setUser: (user: User) => {},
});

export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const api = useApi();

  useEffect(() => {
    // Fetch the user data when the component is mounted
    api
      .get('/api/user/me')
      .then((response) => {
        setUser(response.data);
      })
      .catch((error) => {
        console.error('Failed to fetch user data:', error);
      });
  }, []);

  return (
    <UserContext.Provider value={{ user, setUser }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;
