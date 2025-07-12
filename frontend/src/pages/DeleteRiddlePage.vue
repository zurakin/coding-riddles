<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { RiddlesManagement } from '../RiddlesManagement/riddles_management';

const router = useRouter();
const route = useRoute();
const riddlesManagement = new RiddlesManagement();

onMounted(async () => {
  const id = Number(route.params.id);
  if (!isNaN(id)) {
    try {
      await riddlesManagement.deleteRiddle(id);
      // Optionally show a success message or redirect
      router.replace({ name: 'Riddles' });
    } catch (e) {
      // Optionally handle error
      alert('Failed to delete riddle.');
      router.replace({ name: 'Riddles' });
    }
  } else {
    router.replace({ name: 'Riddles' });
  }
});
</script>

<template>
  <div class="flex items-center justify-center h-full">
    <span>Deleting riddle...</span>
  </div>
</template>

<style scoped>
</style>
