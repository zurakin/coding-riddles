<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Riddle } from '../types/riddle';
import api from '../api';

const riddles = ref<Riddle[]>([]);

onMounted(async () => {
  try {
    const response = await api.get<Riddle[]>('/riddles');
    riddles.value = response.data;
  } catch (error) {
    console.error('Error fetching riddles:', error);
  }
});
</script>

<template>
  <div class="container mx-auto p-4 bg-blue-50 min-h-screen">
    <h2 class="text-2xl font-bold mb-4 text-center">Riddles List</h2>
    <ul class="space-y-4">
      <li v-for="riddle in riddles" :key="riddle.id" class="bg-white p-4 rounded-lg shadow-md hover:bg-gray-200">
        <h3 class="text-xl font-semibold pb-6">{{ riddle.title }}</h3>
        <p class="text-gray-600">{{ riddle.description }}</p>
      </li>
    </ul>
  </div>
</template>