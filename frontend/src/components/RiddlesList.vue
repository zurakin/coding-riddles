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
  <div>
    <h2>Riddles List</h2>
    <ul>
      <li v-for="riddle in riddles" :key="riddle.id">
        {{ riddle.title }} - {{ riddle.description }}
      </li>
    </ul>
  </div>
</template>