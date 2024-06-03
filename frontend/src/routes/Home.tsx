import CreatePost from '@/components/CreatePost';
import PostFeed from '@/components/PostFeed';
import { PostProvider } from '@/context/PostContext';

export default function Home() {
  return (
    <PostProvider>
      <div className="flex flex-1 flex-col gap-2 justify-center w-full p-1">
        <CreatePost />
        <PostFeed />
      </div>
    </PostProvider>
  );
}
