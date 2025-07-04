<script setup lang="ts">
import { ref, type Ref, onMounted } from 'vue';
import type { Riddle } from '../model/riddle';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';
import { useRoute, useRouter } from 'vue-router';
import RiddleView from '../components/RiddleView.vue';
import LoaderSpinner from '../components/LoaderSpinner.vue';

const selectedRiddle: Ref<Riddle | undefined> = ref(undefined);
const code: Ref<string> = ref('// Loading riddle code...');
const loadingRiddle = ref(false);

const route = useRoute();
const router = useRouter();

const onRiddleSelected = async (riddleIdentifier: number, skipPush = false) => {
  loadingRiddle.value = true;
  code.value = '// Loading riddle...';
  const riddle = await new RiddlesManagement().getRiddle(riddleIdentifier);
  selectedRiddle.value = riddle;
  if (riddle && typeof riddle.code === 'string') {
    code.value = riddle.code;
  } else {
    code.value = '';
  }
  loadingRiddle.value = false;
  if (!skipPush && (!route.params.id || Number(route.params.id) !== riddleIdentifier)) {
    router.push({ name: 'Riddle', params: { id: riddleIdentifier } });
  }
};

onMounted(async () => {
  if (route.name === 'Riddle' && route.params.id) {
    await onRiddleSelected(Number(route.params.id), true);
  }
});

</script>

<template>
  <main class="flex-1 flex flex-row gap-4 px-2 py-6 w-full">
    <div v-if="loadingRiddle" class="w-full flex items-center justify-center min-h-[400px]">
      <LoaderSpinner class="w-16 h-16 text-blue-700" />
    </div>
    <RiddleView v-else-if="selectedRiddle" :riddle="selectedRiddle" />
  </main>
</template>

<style scoped>
main {
  min-height: 0;
}
</style>
