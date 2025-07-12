<script setup lang="ts">
import { ref } from 'vue';
import { VueMonacoEditor } from '@guolao/vue-monaco-editor';
import LoaderSpinner from '../components/LoaderSpinner.vue';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';
import type { TestCase } from '../model/riddle';

import { useRouter } from 'vue-router';
import { useRiddlePreviewStore } from '../stores/riddlePreviewStore';

import { onMounted } from 'vue';
const title = ref('');
const description = ref('');
const code = ref('// Write your initial code here\nfunction sayHi(input) {\n  return "";\n}');
const validationCode = ref('// Write your validation code here\nreturn sayHi(input);');
const testCases = ref([
  { input: '', output: '' }
]);
const loading = ref(false);
const message = ref('');
const previewing = ref(false);

const router = useRouter();
const riddlesManagement = new RiddlesManagement();

function toTestCaseArray(): TestCase[] {
  return testCases.value.map((tc, idx) => ({ id: idx, input: tc.input, output: tc.output }));
}

function addTestCase() {
  testCases.value.push({ input: '', output: '' });
}
function removeTestCase(index: number) {
  testCases.value.splice(index, 1);
}

async function submitRiddle() {
  loading.value = true;
  message.value = '';
  try {
    await riddlesManagement.submitRiddle({
      title: title.value,
      description: description.value,
      code: code.value,
      validationCode: validationCode.value,
      testCases: toTestCaseArray() as any // Type assertion to bypass id requirement for submission
    });
    message.value = 'Riddle submitted successfully!';
  } catch (e: any) {
    message.value = 'Failed to submit riddle: ' + (e?.message || e);
  } finally {
    loading.value = false;
  }
}

function handlePreview() {
  previewing.value = true;
  const riddlePreviewStore = useRiddlePreviewStore();
  riddlePreviewStore.setRiddle({
    id: -1,
    title: title.value,
    description: description.value,
    code: code.value,
    validationCode: validationCode.value,
    testCases: toTestCaseArray(),
  });
  router.push({
    name: 'PreviewBeforeSubmitPage',
  });
}

// Restore form state from Pinia if available
onMounted(() => {
  const riddlePreviewStore = useRiddlePreviewStore();
  if (riddlePreviewStore.riddle) {
    const r = riddlePreviewStore.riddle;
    title.value = r.title || '';
    description.value = r.description || '';
    code.value = r.code || '';
    validationCode.value = r.validationCode || '';
    testCases.value = Array.isArray(r.testCases) && r.testCases.length > 0
      ? r.testCases.map(tc => ({ input: tc.input, output: tc.output }))
      : [{ input: '', output: '' }];
  }
});
</script>

<template>
  <div v-if="!previewing" class="flex flex-col items-center w-full py-8 bg-gradient-to-br from-blue-50 to-slate-100 overflow-y-auto h-[90%] min-h-0 pb-16">
    <div class="w-full max-w-[1600px] flex-1 flex flex-col bg-white rounded-2xl shadow-xl border border-blue-200 p-8">
      <h2 class="text-3xl font-extrabold text-blue-700 mb-6 text-center">Submit a New Riddle</h2>
      <form @submit.prevent="submitRiddle" class="space-y-6 flex-1 flex flex-col">
        <div class="flex flex-col md:flex-row gap-8 flex-1 min-h-[600px]">
          <div class="flex-1 flex flex-col gap-6 min-w-[320px] max-w-[600px]">
            <div>
              <label class="block font-semibold text-blue-800 mb-1">Title</label>
              <input v-model="title" class="w-full border rounded px-3 py-2" required maxlength="100" />
            </div>
            <div class="flex-1 flex flex-col">
              <label class="block font-semibold text-blue-800 mb-1">Description</label>
              <textarea v-model="description" class="w-full border rounded px-3 py-2 flex-1" rows="4" required maxlength="20000"></textarea>
            </div>
            <div class="flex-1 flex flex-col">
              <label class="block font-semibold text-blue-800 mb-1">Test Cases</label>
              <div class="space-y-2 flex-1">
                <div v-for="(tc, idx) in testCases" :key="idx" class="flex gap-2 items-center">
                  <input v-model="tc.input" class="border rounded px-2 py-1 flex-1" placeholder="Input" />
                  <input v-model="tc.output" class="border rounded px-2 py-1 flex-1" placeholder="Expected Output" />
                  <button type="button" @click="removeTestCase(idx)" class="text-red-600 font-bold px-2">&times;</button>
                </div>
              </div>
              <button type="button" @click="addTestCase" class="mt-2 px-3 py-1 bg-blue-100 text-blue-700 rounded hover:bg-blue-200 font-semibold">Add Test Case</button>
            </div>
          </div>
          <div class="flex-[2] flex flex-col gap-8 min-w-[600px]">
            <div class="flex-1 flex flex-col">
              <label class="block font-semibold text-blue-800 mb-1">Initial Code</label>
              <VueMonacoEditor v-model:value="code" language="javascript" theme="vs" :options="{ automaticLayout: true, fontSize: 18 }" height="400px" class="border rounded flex-1 min-h-[300px] w-full" />
            </div>
            <div class="flex-1 flex flex-col">
              <label class="block font-semibold text-blue-800 mb-1">Validation Code</label>
              <VueMonacoEditor v-model:value="validationCode" language="javascript" theme="vs" :options="{ automaticLayout: true, fontSize: 18 }" height="220px" class="border rounded flex-1 min-h-[120px] w-full" />
            </div>
          </div>
        </div>
        <div v-if="message" :class="message.includes('success') ? 'text-green-700' : 'text-red-700'">{{ message }}</div>
        <div class="flex justify-end">
          <button type="button" @click="handlePreview" :disabled="loading" class="px-6 py-2 bg-blue-500 text-white rounded font-bold hover:bg-blue-600 disabled:opacity-60 mr-2">
            Preview & Submit
          </button>
          <button type="submit" :disabled="loading" class="px-6 py-2 bg-blue-700 text-white rounded font-bold hover:bg-blue-800 disabled:opacity-60">
            <span v-if="loading"><LoaderSpinner class="inline-block w-5 h-5 align-middle" /></span>
            <span v-else>Submit Riddle</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
</style>
