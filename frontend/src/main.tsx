import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './index.css';

// Routes
import Root from './routes/Root';
import ErrorElement from './components/ErrorElement';
import Login from './routes/Login';
import Home from './routes/Home';
import AuthRoute from './components/AuthRoutes';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <ErrorElement />,
    children: [
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/home',
        element: (
          <AuthRoute>
            <Home />
          </AuthRoute>
        ),
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
