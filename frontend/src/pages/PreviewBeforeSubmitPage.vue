<script setup lang="ts">
import RiddleView from '../components/RiddleView.vue';
import { useRouter, useRoute } from 'vue-router';
import { computed, ref } from 'vue';
import type { Riddle } from '../model/riddle';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';
import LoaderSpinner from '../components/LoaderSpinner.vue';

const route = useRoute();
const router = useRouter();
const loading = ref(false);
const message = ref('');
const riddlesManagement = new RiddlesManagement();

const riddle = computed<Riddle>(() => {
  return {
    id: -1, // Temporary id for preview
    title: String(route.query.title ?? ''),
    description: String(route.query.description ?? ''),
    code: String(route.query.code ?? ''),
    validationCode: String(route.query.validationCode ?? ''),
    testCases: Array.isArray(route.query.testCases)
      ? JSON.parse(route.query.testCases[0] || '[]')
      : JSON.parse((route.query.testCases as string) || '[]'),
  };
});

function handleBack() {
  router.back();
}

async function handleSubmit() {
  loading.value = true;
  message.value = '';
  try {
    const result = await riddlesManagement.submitRiddle({
      title: riddle.value.title,
      description: riddle.value.description,
      code: riddle.value.code,
      validationCode: riddle.value.validationCode,
      testCases: riddle.value.testCases,
    });
    const riddleId = result?.id ?? result?.riddleId ?? result?.data?.id;
    message.value = 'Riddle submitted successfully!';
    if (riddleId) {
      setTimeout(() => router.push({ name: 'Riddle', params: { id: riddleId } }), 1200);
    } else {
      setTimeout(() => router.push({ name: 'Home' }), 1200);
    }
  } catch (e: any) {
    message.value = 'Failed to submit riddle: ' + (e?.message || e);
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="flex flex-col items-center w-full min-h-[calc(100vh-120px)] py-8 bg-gradient-to-br from-blue-50 to-slate-100">
    <div class="w-full max-w-[1600px] flex-1 flex flex-col bg-white rounded-2xl shadow-xl border border-blue-200 p-8">
      <h2 class="text-3xl font-extrabold text-blue-700 mb-6 text-center">Preview Your Riddle</h2>
      <RiddleView :riddle="riddle" />
      <div class="flex justify-end gap-4 mt-8">
        <button @click="handleBack" class="px-6 py-2 bg-gray-200 text-gray-800 rounded font-bold hover:bg-gray-300">Back to Edit</button>
        <button @click="handleSubmit" :disabled="loading" class="px-6 py-2 bg-blue-700 text-white rounded font-bold hover:bg-blue-800 disabled:opacity-60">
          <span v-if="loading"><LoaderSpinner class="inline-block w-5 h-5 align-middle" /></span>
          <span v-else>Submit</span>
        </button>
      </div>
      <div v-if="message" :class="message.includes('success') ? 'text-green-700' : 'text-red-700'" class="mt-4 text-center">{{ message }}</div>
    </div>
  </div>
</template>

<style scoped>
.flex-1 {
  min-height: 0;
}
</style>
