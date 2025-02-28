<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Riddle } from '../model/riddle';
import api from '../api';
import { defineEmits } from 'vue';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';

const riddles = ref<Riddle[]>([]);
const selectedRiddleId = ref<number | null>(null);

onMounted(async () => {
  riddles.value = await new RiddlesManagement().listRiddles();
});

const emit = defineEmits(['riddle-selected']);

function onRiddleSelected(riddleId: number) {
  selectedRiddleId.value = riddleId;
  emit('riddle-selected', riddleId);
}

</script>

<template>
  <div class="container mx-auto p-4 bg-blue-50 min-h-screen">
    <h2 class="text-2xl font-bold mb-4 text-center border-b-4 border-blue-500 pb-2">Riddles</h2>
    <ul class="space-y-4">
      <li 
        v-for="riddle in riddles" 
        @click="onRiddleSelected(riddle.id)" 
        :key="riddle.id" 
        :class="{'bg-gray-300': riddle.id === selectedRiddleId, 'bg-white': riddle.id !== selectedRiddleId, 'hover:bg-gray-200': riddle.id !== selectedRiddleId}" 
        class="p-4 rounded-lg shadow-md"
      >
        <h3 class="text-xl font-semibold pb-6">{{ riddle.title }}</h3>
        <p class="text-gray-600">{{ riddle.description }}</p>
      </li>
    </ul>
  </div>
</template>