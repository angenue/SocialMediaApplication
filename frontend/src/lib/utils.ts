import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function formatDistanceToNow(
  date: Date,
  options: { addSuffix: boolean }
): string {
  const now = new Date();
  const distanceInSeconds = Math.abs(now.getTime() - date.getTime()) / 1000;

  const timeUnits = [
    { name: 'year', seconds: 60 * 60 * 24 * 365 },
    { name: 'month', seconds: 60 * 60 * 24 * 30 },
    { name: 'day', seconds: 60 * 60 * 24 },
    { name: 'hour', seconds: 60 * 60 },
    { name: 'minute', seconds: 60 },
    { name: 'second', seconds: 1 },
  ];

  for (const unit of timeUnits) {
    const value = Math.floor(distanceInSeconds / unit.seconds);
    if (value >= 1) {
      return `${value} ${unit.name}${value > 1 ? 's' : ''} ${
        options.addSuffix ? 'ago' : ''
      }`;
    }
  }

  return 'now';
}
