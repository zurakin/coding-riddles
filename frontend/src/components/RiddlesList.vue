<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import type { Riddle } from '../model/riddle';
import { defineEmits } from 'vue';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';

const riddles = ref<Riddle[]>([]);
const selectedRiddleId = ref<number | null>(null);

const props = defineProps<{ activeRiddleId?: number }>();

onMounted(async () => {
  riddles.value = await new RiddlesManagement().listRiddles();
  if (props.activeRiddleId) {
    selectedRiddleId.value = props.activeRiddleId;
  }
});

watch(
  () => props.activeRiddleId,
  (newId) => {
    if (newId) selectedRiddleId.value = newId;
  }
);

const emit = defineEmits(['riddle-selected']);

function onRiddleSelected(riddleId: number) {
  selectedRiddleId.value = riddleId;
  emit('riddle-selected', riddleId);
}

</script>

<template>
  <div class="bg-blue-50 p-4 rounded-xl shadow w-full">
    <h2 class="text-xl font-bold mb-4 text-center border-b-2 border-blue-400 pb-2 text-blue-800">Riddles</h2>
    <ul class="space-y-2">
      <li 
        v-for="riddle in riddles" 
        @click="onRiddleSelected(riddle.id)" 
        :key="riddle.id" 
        :class="[
          'cursor-pointer p-2 rounded-md transition-colors',
          riddle.id === selectedRiddleId ? 'bg-blue-200 text-blue-900 font-semibold' : 'bg-white hover:bg-blue-100',
        ]"
      >
        <h3 class="text-base font-bold">{{ riddle.title }}</h3>
        <p class="text-gray-600 text-sm">{{ riddle.description }}</p>
      </li>
    </ul>
  </div>
</template>

<style scoped>
</style>