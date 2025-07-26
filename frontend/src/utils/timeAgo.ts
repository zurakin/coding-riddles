// Utility to format a date as "time ago"
export function timeAgo(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    const intervals: [number, string][] = [
        [31536000, 'year'],
        [2592000, 'month'],
        [86400, 'day'],
        [3600, 'hour'],
        [60, 'minute'],
        [1, 'second'],
    ];

    for (const [interval, label] of intervals) {
        const count = Math.floor(seconds / interval);
        if (count >= 1) {
            return `${count} ${label}${count > 1 ? 's' : ''} ago`;
        }
    }
    return 'just now';
}
