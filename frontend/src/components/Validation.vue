<script setup lang="ts">

import { ref, type Ref, watch, onMounted, defineProps } from 'vue';
import type { Riddle } from '../model/riddle';
import LoaderSpinner from './LoaderSpinner.vue';

const props = defineProps({
    riddle: {
        type: Object as () => Riddle | undefined,
        required: true
    }
});

const loading: Ref<boolean> = ref<boolean>(false);
const statusPerTestCase: Ref = ref<{ [key: string]: 'default' | 'passed' | 'failed' | 'running' }>({});

const handleTestCaseClick = (testCaseId: any) => {
    statusPerTestCase.value[testCaseId] = 'running';
    setTimeout(() => {
        const result = Math.random() > 0.5 ? 'passed' : 'failed';
        statusPerTestCase.value[testCaseId] = result;
    }, 1000);
};

function resetValidationStatus() {
    if (props.riddle && props.riddle.testCases) {
        props.riddle.testCases.forEach(testCase => {
            statusPerTestCase.value[testCase.id] = 'default';
        });
    }
}

onMounted(resetValidationStatus);
watch(() => props.riddle, resetValidationStatus);

</script>

<template>
    <div class="p-6 w-full mx-auto bg-blue-100 rounded-xl shadow-md space-y-4">
        <h2 class="text-2xl font-bold mb-4 text-center border-b-4 border-blue-500 pb-2">Validation</h2>
        <div v-if="loading" class="text-center">
            <LoaderSpinner/>
        </div>
        <div v-else-if="riddle">
            <h2 class="text-xl font-semibold text-gray-800">{{ riddle.title }}</h2>
            <p class="text-gray-700">{{ riddle.description }}</p>
            <h3 class="text-lg font-medium text-gray-800 mt-4">Test Cases: </h3>
            <div class="space-y-2 p-4">
                <button v-for="(testCase, index) in riddle.testCases" 
                        :key="testCase.id" 
                        :class="{
                            'bg-blue-500 text-white': statusPerTestCase[testCase.id] === 'default',
                            'bg-gray-500 text-white': statusPerTestCase[testCase.id] === 'running',
                            'bg-green-600 hover:bg-green-700 text-white': statusPerTestCase[testCase.id] === 'passed',
                            'bg-red-700 hover:bg-red-900 text-white': statusPerTestCase[testCase.id] === 'failed'
                        }"
                        class="font-bold py-2 px-4 rounded w-full flex items-center justify-center gap-2"
                        @click="handleTestCaseClick(testCase.id)">
                    <span v-if="statusPerTestCase[testCase.id] !== 'running'">Run test {{ index + 1 }}</span>
                    <LoaderSpinner v-if="statusPerTestCase[testCase.id] === 'running'" />
                </button>
            </div>
        </div>
        <div v-else>
            <p class="text-gray-700">Select a riddle to see the details.</p>
        </div>
    </div>
</template>



<style></style>