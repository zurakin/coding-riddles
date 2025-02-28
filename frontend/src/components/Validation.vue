<script setup lang="ts">
import { ref, watch, type Ref } from 'vue';
import { defineProps } from 'vue';

const props = defineProps({
    riddleId: {
        type: Number,
        required: true
    }
});

interface Riddle {
    id: number;
    title: string;
    description: string;
    code: string;
    testCases: {
        id: number;
        input: string;
        output: string;
    }[];
    validationCode: string;
}

const riddle: Ref<Riddle | null> = ref<Riddle | null>(null);
const loading: Ref<boolean> = ref<boolean>(false);

watch(() => props.riddleId, async (newId) => {
    await fetchRiddle(newId);
}, { immediate: true });

async function fetchRiddle(id: number) {
    loading.value = true;
    try {
        const response = await fetch(`/api/riddles/${id}`);
        const data = await response.json();
        riddle.value = data;
    } catch (error) {
        console.error('Error fetching riddle:', error);
    } finally {
        loading.value = false;
    }
}
</script>

<template>
    <div class="p-6 w-full mx-auto bg-blue-100 rounded-xl shadow-md space-y-4">
        <h2 class="text-2xl font-bold mb-4 text-center border-b-4 border-blue-500 pb-2">Validation</h2>
        <div v-if="loading" class="text-center">
            <svg class="animate-spin h-8 w-8 text-blue-500 mx-auto" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
        </div>
        <div v-else-if="riddle">
            <h2 class="text-xl font-semibold text-gray-800">{{ riddle.title }}</h2>
            <p class="text-gray-700">{{ riddle.description }}</p>
            <h3 class="text-lg font-medium text-gray-800 mt-4">Test Cases</h3>
            <ul class="list-disc list-inside space-y-2">
                <li v-for="testCase in riddle.testCases" :key="testCase.id" class="text-gray-700">
                    <span class="font-mono bg-gray-100 p-1 rounded">{{ testCase.input }}</span> => <span class="font-mono bg-gray-100 p-1 rounded">{{ testCase.output }}</span>
                </li>
            </ul>
        </div>
        <div v-else>
            <p class="text-gray-700">Select a riddle to see the details.</p>
        </div>
    </div>
</template>
<style></style>