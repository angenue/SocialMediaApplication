import { Heart, MessageCircle } from 'lucide-react';
import { Avatar, AvatarImage } from './ui/avatar';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { formatDistanceToNow } from '@/lib/utils';

export type IPost = {
  postId: number;
  numLikes: number;
  numComments: number;
  content: string;
  created: string;
  username: string;
  profilePic: string | null;
};

export default function Post({
  numLikes,
  numComments,
  content,
  created,
  username,
  profilePic,
}: IPost) {
  const timePassed = formatDistanceToNow(new Date(created), {
    addSuffix: true,
  });

  return (
    <Card className="flex flex-col p-4 gap-2">
      <div className="flex flex-row gap-4 items-center justify-between">
        <div className="flex flex-row gap-2 items-center">
          <Avatar>
            <AvatarImage
              src={profilePic || 'https://github.com/shadcn.png'}
              alt={username}
            />
          </Avatar>

          <h3 className="font-semibold">{username}</h3>
        </div>
        <p className="text-gray-500 text-xs">{timePassed}</p>
      </div>
      <div className="post-content">
        <p>{content}</p>
      </div>
      <div className="flex flex-row gap-2 items-center">
        <Button variant="ghost" size="icon" className="py-0">
          <Heart className="mr-2 h-4 w-4" /> {numLikes}
        </Button>
        <Button variant="ghost" size="icon" className="py-0">
          <MessageCircle className="mr-2 h-4 w-4" /> {numComments}
        </Button>
      </div>
    </Card>
  );
}
