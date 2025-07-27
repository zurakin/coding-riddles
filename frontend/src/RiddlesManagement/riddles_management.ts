import type { Riddle } from '../model/models';

export class RiddlesManagement {
    async getRiddle(id: number) {
        try {
            const headers = this.getAuthHeaders();
            const response = await fetch(`/api/riddles/${id}`, { headers });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error fetching riddle:', error);
        }
    }

    async deleteRiddle(id: number) {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('User must be authenticated to delete a riddle');
        }
        try {
            await fetch(`/api/riddles/${id}`, {
                method: 'DELETE',
                headers: this.getAuthHeaders(),
            });
        } catch (error) {
            console.error('Error deleting riddle:', error);
        }
    }

    async listRiddles() {
        try {
            const headers = this.getAuthHeaders();
            const response = await fetch('/api/riddles', { headers });
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error listing riddles:', error);
        }
    }

    async submitRiddle(riddle: Omit<Riddle, 'id'>) {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('User must be authenticated to submit a riddle');
        }
        try {
            const headers = this.getAuthHeaders({ 'Content-Type': 'application/json' });
            const response = await fetch('/api/riddles', {
                method: 'POST',
                headers,
                body: JSON.stringify(riddle)
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to submit riddle');
            }
            return await response.json();
        } catch (error) {
            throw error;
        }
    }
    
    private getAuthHeaders(extraHeaders: Record<string, string> = {}): Record<string, string> {
        const token = localStorage.getItem('token');
        const headers: Record<string, string> = { ...extraHeaders };
        if (token) headers['Authorization'] = `Bearer ${token}`;
        return headers;
    }
}
