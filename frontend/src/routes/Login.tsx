import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import CredentialContext from '@/context/CredentialContext';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const { setCredentials } = useContext(CredentialContext);
  const [isLogin, setIsLogin] = useState(true);
  const [usernameInput, setUsernameInput] = useState('');
  const [passwordInput, setPasswordInput] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
          username: usernameInput,
          password: passwordInput,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to Login. Please try again.');
      }

      setCredentials(usernameInput, passwordInput);

      navigate('/home');
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      setError(error.message);
    }
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    if (passwordInput !== confirmPassword) {
      setError('Passwords do not match.');
      return;
    }
    setError('');

    try {
      const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: usernameInput,
          newPassword: passwordInput,
          newPasswordConfirmation: confirmPassword,
          email: email,
          name: name,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to register. Please try again.');
      }

      // I know this is insecure but it's just for the sake of the demo
      setCredentials(usernameInput, confirmPassword);
      navigate('/home');
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      setError(error?.message);
    }
  };

  return (
    <div className="flex flex-1 flex-col justify-center items-center gap-8">
      <h1 className="text-3xl font-bold text-center">✨Spark✨</h1>

      <Card className="max-w-sm w-full">
        <form
          onSubmit={isLogin ? handleLogin : handleRegister}
          className="w-full bg-white rounded-xl px-6 py-8 flex flex-col gap-4"
        >
          {!isLogin && (
            <>
              <div className="grid gap-1.5">
                <Label htmlFor="name">Name</Label>
                <Input
                  type="text"
                  id="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                />
              </div>
              <div className="grid gap-1.5">
                <Label htmlFor="email">Email</Label>
                <Input
                  type="email"
                  id="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
            </>
          )}
          <div className="grid gap-1.5">
            <Label htmlFor="username">Username</Label>
            <Input
              type="text"
              id="username"
              value={usernameInput}
              onChange={(e) => setUsernameInput(e.target.value)}
              required
            />
          </div>
          <div className="grid gap-1.5">
            <Label htmlFor="password">Password</Label>
            <Input
              type="password"
              id="password"
              value={passwordInput}
              onChange={(e) => setPasswordInput(e.target.value)}
              required
            />
          </div>
          {!isLogin && (
            <div className="grid gap-1.5">
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <Input
                type="password"
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>
          )}
          <div className="h-2 w-full text-center">
            {error && (
              <p className="text-red-500 text-xs italic mb-1">{error}</p>
            )}
          </div>

          <div className="flex flex-col items-center gap-4">
            <Button className="w-full" type="submit">
              {isLogin ? 'Sign In' : 'Register'}
            </Button>
            <a
              href="#"
              onClick={() => {
                setIsLogin(!isLogin);
                setError('');
              }}
              className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800 cursor-pointer"
            >
              {isLogin ? 'Need an account? Register' : 'Have an account? Login'}
            </a>
          </div>
        </form>
      </Card>
    </div>
  );
};

export default Login;
