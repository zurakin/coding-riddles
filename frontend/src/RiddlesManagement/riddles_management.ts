import type { Riddle } from '../model/riddle';

export class RiddlesManagement {
    async getRiddle(id: number) {
        try {
            const response = await fetch(`/api/riddles/${id}`);
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
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error deleting riddle:', error);
        }
    }

    async listRiddles() {
        try {
            const response = await fetch('/api/riddles');
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
            const response = await fetch('/api/riddles', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
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
}
