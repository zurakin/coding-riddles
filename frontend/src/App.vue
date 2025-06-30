<script setup lang="ts">
</script>

<template>
  <div class="min-h-screen h-screen w-full bg-gradient-to-br from-blue-50 to-slate-100 flex flex-col">
    <header class="w-full py-4 px-8 bg-white shadow flex items-center justify-between">
      <h1 class="text-3xl font-extrabold text-blue-700 tracking-wide">Coding Riddles</h1>
      <span class="text-base text-blue-400 font-semibold">Challenge your mind with fun riddles!</span>
    </header>
    <router-view />
  </div>
</template>

<style>
html, body, #app {
  height: 100%;
}
</style>