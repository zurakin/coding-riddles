import type { UserProfile } from '../model/models';

export class UserManagement {
    async getLeaderboard(): Promise<{ username: string; solvedRiddlesCount: number }[]> {
        const token = localStorage.getItem('token');
        const headers: Record<string, string> = {};
        if (token) headers['Authorization'] = `Bearer ${token}`;
        const response = await fetch('/api/user/leaderboard', { headers });
        if (!response.ok) throw new Error('Failed to fetch leaderboard');
        return await response.json();
    }
    isAuthenticated(): boolean {
        return !!localStorage.getItem('token');
    }
    async register(username: string, password: string) {
        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Registration failed');
        }
        return await response.text();
    }

    async login(username: string, password: string): Promise<string> {
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Login failed');
        }
        const data = await response.json();
        return data.token;
    }

    async getProfile(token: string): Promise<UserProfile> {
        const response = await fetch('/api/user', {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!response.ok) {
            let message = 'Failed to fetch user profile';
            try {
                message = await response.text() || message;
            } catch {}
            throw {
                name: 'ApiError',
                message,
                status: response.status,
                response
            };
        }
        return await response.json();
    }
}
