<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';

const router = useRouter();
const route = useRoute();
const riddlesManagement = new RiddlesManagement();

import { ref } from 'vue';

const success = ref(false);
const errorMsg = ref('');

onMounted(async () => {
  const id = Number(route.params.id);
  if (!isNaN(id)) {
    try {
      await riddlesManagement.deleteRiddle(id);
      success.value = true;
    } catch (e) {
      errorMsg.value = 'Failed to delete riddle.';
    }
  } else {
    errorMsg.value = 'Invalid riddle id.';
  }
});
</script>


<template>
  <div class="flex flex-col items-center justify-center h-full">
    <span v-if="!success && !errorMsg">Deleting riddle...</span>
    <span v-if="success" class="text-green-600 font-semibold">Riddle deleted successfully!</span>
    <span v-if="errorMsg" class="text-red-600 font-semibold">{{ errorMsg }}</span>
  </div>
</template>

<style scoped>
</style>
