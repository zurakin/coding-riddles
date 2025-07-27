export class SolutionsManagement {
    async getSolvedRiddles() {
        try {
            const response = await fetch('/api/riddle-solutions');
            if (!response.ok) {
                throw new Error('Failed to fetch solved riddles');
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching solved riddles:', error);
            throw error;
        }
    }

    async postSolvedRiddle(riddleId: number, answerCode: string) {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('User must be authenticated to submit a solution');
        }
        try {
            const response = await fetch('/api/riddle-solutions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ riddleId, answerCode })
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to submit solution');
            }
            return await response.json();
        } catch (error) {
            console.error('Error submitting solution:', error);
            throw error;
        }
    }
}
