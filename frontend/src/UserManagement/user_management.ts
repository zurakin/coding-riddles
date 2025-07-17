export class UserManagement {
    async register(username: string, password: string) {
        console.log("Attempting to register with:", JSON.stringify({ username, password }));
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

    async getProfile(token: string): Promise<{ username: string }> {
        const response = await fetch('/api/user', {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!response.ok) {
            throw new Error('Failed to fetch user profile');
        }
        return await response.json();
    }
}
