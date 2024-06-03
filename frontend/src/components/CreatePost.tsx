import { useContext, useState } from 'react';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from '@/components/ui/form';
import { Textarea } from '@/components/ui/textarea';
import UserContext from '@/context/UserContext';
import { useApi } from '@/lib/api';
import { Loader2 } from 'lucide-react';
import { PostContext } from '@/context/PostContext';

const FormSchema = z.object({
  post: z.string().min(3, {
    message: 'Post must be at least 3 characters long.',
  }),
});

export default function CreatePost() {
  const { user } = useContext(UserContext);
  const { triggerReload } = useContext(PostContext);
  const api = useApi();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      post: '',
    },
  });

  async function onSubmit({ post }: z.infer<typeof FormSchema>) {
    setIsSubmitting(true);
    try {
      await api.post('/api/posts', {
        content: post,
        user: { username: user?.username },
      });
      form.reset();
      triggerReload();
    } catch (error) {
      form.setError('post', {
        message: 'Failed to create post. Please try again.',
      });
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="w-full space-y-2">
        <FormField
          control={form.control}
          name="post"
          disabled={isSubmitting}
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Textarea
                  placeholder={`What's on your mind, ${user?.name}?`}
                  className="resize-none"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">
          {isSubmitting ? <Loader2 className="animate-spin" /> : 'Post'}
        </Button>
      </form>
    </Form>
  );
}
