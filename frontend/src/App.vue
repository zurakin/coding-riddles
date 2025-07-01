<script setup lang="ts">
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
    </nav>
    <div class="flex-1 flex flex-col min-h-0">
      <router-view />
    </div>
  </div>
</template>

<style>
html, body, #app {
  height: 100%;
  overflow: hidden;
}
</style>