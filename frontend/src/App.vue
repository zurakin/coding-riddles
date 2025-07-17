<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { UserManagement } from './UserManagement/user_management';

const username = ref<string | null>(null);
const router = useRouter();

function getToken() {
  return localStorage.getItem('token');
}

async function fetchProfile() {
  const token = getToken();
  if (!token) {
    username.value = null;
    return;
  }
  try {
    const um = new UserManagement();
    const profile = await um.getProfile(token);
    username.value = profile.username;
  } catch {
    username.value = null;
  }
}

function signOut() {
  localStorage.removeItem('token');
  username.value = null;
  router.push('/authentication');
}

onMounted(fetchProfile);
</script>

<template>
  <div class="min-h-screen h-screen w-full bg-gradient-to-br from-blue-50 to-slate-100 flex flex-col overflow-hidden">
    <header class="w-full py-4 px-8 bg-white shadow flex items-center justify-between">
      <h1 class="text-3xl font-extrabold text-blue-700 tracking-wide">Coding Riddles</h1>
      <span class="text-base text-blue-400 font-semibold">Challenge your mind with fun riddles!</span>
    </header>
    <nav class="w-full bg-blue-700 text-white px-8 py-2 flex items-center gap-6 shadow">
      <router-link to="/riddles" class="hover:underline font-semibold">Solve</router-link>
      <router-link to="/submit" class="hover:underline font-semibold">Create</router-link>
      <router-link to="/about" class="hover:underline font-semibold">About</router-link>
      <router-link
        v-if="username"
        to="/profile"
        class="ml-auto hover:underline font-semibold"
      >
        {{ username }}
      </router-link>
      <router-link
        v-else
        to="/authentication"
        class="ml-auto hover:underline font-semibold"
      >
        Login/Signup
      </router-link>
    </nav>
    <div class="flex-1 flex flex-col min-h-0">
      <router-view v-slot="{ Component }">
        <component
          :is="Component"
          :signOut="signOut"
          :username="username"
          @profile-updated="fetchProfile"
          @auth-success="fetchProfile"
        />
      </router-view>
    </div>
  </div>
</template>

<style>
html, body, #app {
  height: 100%;
  overflow: hidden;
}
</style>