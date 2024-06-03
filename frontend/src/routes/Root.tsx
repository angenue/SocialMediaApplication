import { UserProvider } from '@/context/CredentialContext';
import { Outlet } from 'react-router-dom';

export default function Root() {
  return (
    <UserProvider>
      <div className="flex flex-col w-full min-h-screen">
        <main className="flex flex-col h-full">
          <Outlet />
        </main>
      </div>
    </UserProvider>
  );
}
