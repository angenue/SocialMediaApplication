import { ReactNode, createContext, useState } from 'react';

export const PostContext = createContext({
  reload: false,
  triggerReload: () => {},
});

export const PostProvider = ({ children }: { children: ReactNode }) => {
  const [reload, setReload] = useState(false);

  const triggerReload = () => {
    setReload(!reload);
  };

  return (
    <PostContext.Provider value={{ reload, triggerReload }}>
      {children}
    </PostContext.Provider>
  );
};
