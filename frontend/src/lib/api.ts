import axios from 'axios';
import { useContext, useEffect } from 'react';
import CredentialContext from '@/context/CredentialContext';

// Create an axios instance without the auth property
const api = axios.create({
  baseURL: 'http://localhost:8080',
});

// Create a custom hook to use the axios instance with auth
export function useApi() {
  const { username, password } = useContext(CredentialContext);

  useEffect(() => {
    // Add a request interceptor to the axios instance
    api.interceptors.request.use((config) => {
      config.auth = {
        username: username || 'missing-username',
        password: password || 'missing-password',
      };
      return config;
    });
  }, [username, password]);

  return api;
}
