import { useState, useEffect, useContext } from 'react';
import Post from './Post';
import { PostContext } from '@/context/PostContext';
import { useApi } from '@/lib/api';

import type { IPost } from './Post';

export default function PostFeed() {
  const { reload } = useContext(PostContext);
  const api = useApi();
  const [posts, setPosts] = useState<IPost[] | []>([]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await api.get('/api/posts/user/1');
        setPosts(response.data);
      } catch (error) {
        console.error('Failed to fetch posts:', error);
      }
    };
    fetchPosts();
  }, [reload]);

  const sortedPosts = [...posts].sort(
    (a, b) => new Date(b.created).getTime() - new Date(a.created).getTime()
  );

  return (
    <div className="flex flex-col gap-2 py-4">
      {sortedPosts.map((post) => (
        <Post key={post.postId} {...post} />
      ))}
    </div>
  );
}
