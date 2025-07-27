<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Riddle } from '../model/models';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';
import { useRouter } from 'vue-router';
import LoaderSpinner from '../components/LoaderSpinner.vue';

const riddles = ref<Riddle[]>([]);
const router = useRouter();
const loading = ref(true);

onMounted(async () => {
  riddles.value = await new RiddlesManagement().listRiddles();
  loading.value = false;
});

function goToRiddle(riddleId: number) {
  router.push({ name: 'Riddle', params: { id: riddleId } });
}
</script>

<template>
  <div class="w-[80%] max-w-3xl mx-auto mt-12 p-8 bg-white rounded-2xl shadow-xl border border-blue-200 overflow-y-auto h-[90%] min-h-0 pb-16">
    <h2 class="text-3xl font-extrabold text-blue-700 mb-6 text-center">Riddles</h2>
    <div v-if="loading" class="flex justify-center items-center py-12">
      <LoaderSpinner />
    </div>
    <ul v-else class="space-y-4">
      <li v-for="riddle in riddles" :key="riddle.id"
          :class="[
            'rounded-lg p-4 border cursor-pointer transition',
            riddle.completedByCurrentUser ? 'bg-teal-100 border-teal-300 hover:bg-teal-200' : 'bg-blue-50 border-blue-100 hover:bg-blue-100'
          ]"
          @click="goToRiddle(riddle.id)">
        <h3 class="text-lg font-bold text-blue-900 mb-1">{{ riddle.title }}</h3>
        <p class="text-gray-700 text-base">
          {{ riddle.description.length > 180 ? riddle.description.slice(0, 180) + '...' : riddle.description }}
        </p>
      </li>
    </ul>
  </div>
</template>

<style scoped>
</style>
