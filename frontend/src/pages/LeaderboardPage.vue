<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { UserManagement } from '../UserManagement/user_management';

interface LeaderboardUser {
  username: string;
  solvedRiddlesCount: number;
}

const users = ref<LeaderboardUser[]>([]);
const loading = ref(true);
const error = ref('');

onMounted(async () => {
  try {
    const um = new UserManagement();
    users.value = await um.getLeaderboard();
  } catch (e: any) {
    error.value = e?.message || 'Could not load leaderboard';
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <div class="w-full max-w-2xl mx-auto mt-12 p-8 bg-white rounded-2xl shadow-xl border border-blue-200">
    <h2 class="text-3xl font-extrabold text-blue-700 mb-2 text-center">Leaderboard</h2>
    <div class="text-center text-blue-500 mb-4 text-base font-semibold">Top 10 users by solved riddles</div>
    <div v-if="loading" class="flex justify-center items-center py-12">
      <span>Loading...</span>
    </div>
    <div v-else-if="error" class="text-red-600 text-center py-4">{{ error }}</div>
    <table v-else class="w-full border text-lg">
      <thead>
        <tr class="bg-blue-100">
          <th class="p-3 border text-left">Rank</th>
          <th class="p-3 border text-left">User</th>
          <th class="p-3 border text-left">Solved Riddles</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, idx) in users" :key="user.username" class="hover:bg-blue-50">
          <td class="p-3 border font-bold text-blue-700">{{ idx + 1 }}</td>
          <td class="p-3 border font-bold text-blue-900">{{ user.username }}</td>
          <td class="p-3 border text-teal-700">{{ user.solvedRiddlesCount }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
