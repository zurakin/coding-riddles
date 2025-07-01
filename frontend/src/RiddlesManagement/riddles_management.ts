import type { Riddle } from '../model/riddle';

export class RiddlesManagement {
    async getRiddle(id: number) {
        try {
            const response = await fetch(`/api/riddles/${id}`);
            const data = await response.json();
            return data;;
        } catch (error) {
            console.error('Error fetching riddle:', error);
        }
    }

    async deleteRiddle(id: number) {
        try {
            await fetch(`/api/riddles/${id}`, { method: 'DELETE' });
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
        try {
            const response = await fetch('/api/riddles', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
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
