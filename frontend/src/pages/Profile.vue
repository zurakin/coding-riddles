<template>
  <div
    class="w-full flex items-center justify-center bg-gradient-to-br from-blue-50 to-slate-100 overflow-y-auto h-[90%] min-h-0 ">
    <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-6 my-12 mx-4">
      <h2 class="text-2xl font-bold mb-6 text-center text-blue-700">Profile</h2>
      <div v-if="user">
        <div class="mb-6">
          <label class="block text-gray-700 mb-1">Username</label>
          <div class="w-full mt-1 pb-1 border-b border-slate-300 text-lg text-slate-700 font-bold select-text">{{
            user.username }}</div>
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 mb-1">Solved Riddles</label>
          <div class="w-full mt-1 pb-1 border-b border-slate-300 text-lg text-slate-700 font-bold select-text">
            {{ user.solvedRiddlesCount }}
          </div>
        </div>
        <button
          class="w-full mt-2 py-2 px-4 rounded-lg bg-blue-600 text-white font-semibold shadow hover:bg-blue-700 transition-colors"
          @click="signOut">
          Sign Out
        </button>
      </div>
      <div v-else>
        <p class="text-gray-500 text-center">Loading...</p>
      </div>
      <div v-if="error" class="text-red-600 mt-4 text-center">{{ error }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">

import { ref, onMounted, defineProps, defineEmits } from 'vue';
import { useRouter } from 'vue-router';
import { UserManagement } from '../UserManagement/user_management';
import type { UserProfile } from '../model/models';

const props = defineProps<{ signOut?: () => void; username?: string }>();
const emit = defineEmits(['profile-updated']);

const user = ref<UserProfile | null>(null);
const error = ref('');
const router = useRouter();

onMounted(async () => {
  const token = localStorage.getItem('token');
  if (!token) {
    router.replace('/authentication');
    return;
  }
  try {
    const um = new UserManagement();
    user.value = await um.getProfile(token);
  } catch (e: any) {
    console.error('Error fetching profile:', e);
    // If unauthorized, token is probably expired or invalid
    if (e?.response?.status === 401) {
      localStorage.removeItem('token');
      router.replace('/authentication');
      return;
    }
    error.value = 'Failed to load profile.';
  }
});

function signOut() {
  localStorage.removeItem('token');
  emit('profile-updated');
  if (props.signOut) props.signOut();
}
</script>
