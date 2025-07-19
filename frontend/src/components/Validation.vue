<script setup lang="ts">

import { ref, type Ref, watch, onMounted, defineProps } from 'vue';
import type { Riddle } from '../model/riddle';
import LoaderSpinner from './LoaderSpinner.vue';
import { LocalJavaScriptValidator } from '../Validator/LocalJavaScriptValidator';

const props = defineProps({
    riddle: {
        type: Object as () => Riddle | null | undefined,
        required: false
    }, 
    code: 
    {
        type: String,
        required: false
    }
});
const emit = defineEmits(["test-executed"]);
const loading: Ref<boolean> = ref<boolean>(false);
const statusPerTestCase: Ref = ref<{ [key: string]: 'default' | 'passed' | 'failed' | 'running' }>({});
const validator = new LocalJavaScriptValidator();

const handleTestCaseClick = (testCaseId: number) => {
    statusPerTestCase.value[testCaseId] = 'running';

    if (props.riddle && props.code) {
        const result = validator.validateTestCase(props.code, props.riddle, testCaseId);
        statusPerTestCase.value[testCaseId] = result.status ? 'passed' : 'failed';
        emit('test-executed', result.message, false);
        return;
    }
    statusPerTestCase.value[testCaseId] = 'failed';
    emit('test-executed', `Test Case ${testCaseId}: [failed] - Test case, validation code, or user code missing`, false);
};

function resetValidationStatus() {
    if (props.riddle && props.riddle.testCases) {
        props.riddle.testCases.forEach((_, idx) => {
            statusPerTestCase.value[idx] = 'default';
        });
    }
}

onMounted(resetValidationStatus);
watch(() => props.riddle, resetValidationStatus);

</script>

<template>
    <div class="p-8 w-full max-w-2xl mx-auto bg-gradient-to-br from-blue-100 to-blue-200 rounded-2xl shadow-xl space-y-6 border border-blue-300 overflow-y-auto h-90% min-h-0 ">
        <h2 class="text-3xl font-extrabold mb-6 text-center border-b-4 border-blue-500 pb-3 tracking-wide text-blue-800 drop-shadow">Validation</h2>
        <div v-if="loading" class="text-center py-8">
            <LoaderSpinner/>
        </div>
        <div v-else-if="riddle">
            <h2 class="text-2xl font-bold text-blue-900 mb-2 text-center">{{ riddle.title }}</h2>
            <div v-if="riddle.author" class="text-base text-blue-700 mb-2 text-center">
                <span class="font-semibold">Author:</span> {{ riddle.author.username }}
            </div>
            <div class="mb-4 text-center max-h-[40vh] overflow-y-auto bg-white/80 rounded-lg p-4 relative scrollable-section">
                <p class="text-gray-700 italic mt-2 whitespace-pre-line">{{ riddle.description }}</p>
            </div>
            <div class="scroll-fade-bottom"></div>
            <h3 class="text-2xl font-semibold text-blue-700 mt-6 mb-2 text-center">Test Cases</h3>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 p-2">
                <button v-for="(_, index) in riddle.testCases" 
                        :key="index" 
                        :class="{
                            'bg-blue-500 text-white': statusPerTestCase[index] === 'default',
                            'bg-gray-500 text-white': statusPerTestCase[index] === 'running',
                            'bg-green-600 hover:bg-green-700 text-white': statusPerTestCase[index] === 'passed',
                            'bg-red-700 hover:bg-red-900 text-white': statusPerTestCase[index] === 'failed'
                        }"
                        class="font-bold py-2 px-4 rounded w-full flex items-center justify-center gap-2"
                        @click="handleTestCaseClick(index)">
                    <span v-if="statusPerTestCase[index] !== 'running'">Test {{ index + 1 }}</span>
                    <LoaderSpinner v-if="statusPerTestCase[index] === 'running'" />
                </button>
            </div>
        </div>
        <div v-else class="text-center py-8">
            <p class="text-gray-600 text-lg">Select a riddle to see the details.</p>
        </div>
    </div>
</template>

<style scoped>
/* Add a subtle fade-in animation for the main card */
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.p-8 {
  animation: fadeIn 0.7s cubic-bezier(0.4, 0, 0.2, 1);
}

button[disabled] {
  opacity: 0.6;
  cursor: not-allowed;
}

.scrollable-section {
  box-shadow: 0 2px 8px 0 rgba(30, 64, 175, 0.07);
  transition: box-shadow 0.2s;
  position: relative;
}
.scrollable-section:focus,
.scrollable-section:hover {
  box-shadow: 0 4px 16px 0 rgba(30, 64, 175, 0.13);
}
.scrollable-section::-webkit-scrollbar {
  width: 8px;
  background: #e0e7ff;
  border-radius: 8px;
}
.scrollable-section::-webkit-scrollbar-thumb {
  background: #a5b4fc;
  border-radius: 8px;
}

.scroll-fade-bottom {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  height: 18px;
  pointer-events: none;
  opacity: 0.7;
  background: linear-gradient(to bottom, rgba(255,255,255,0), rgba(164,180,252,0.5));
  z-index: 2;
  border-radius: 0 0 16px 16px;
}
</style>