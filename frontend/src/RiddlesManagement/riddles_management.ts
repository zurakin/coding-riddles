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
}
