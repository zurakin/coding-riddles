<template>
  <div class="max-w-3xl mx-auto mt-12 p-8 bg-white rounded-2xl shadow-xl border border-blue-200 overflow-y-auto h-[90%] min-h-0 ">
    <h2 class="text-3xl font-extrabold text-blue-700 mb-6 text-center">About Coding Riddles</h2>
    <p class="text-lg text-gray-700 mb-4">
      <strong>Coding Riddles</strong> is a platform dedicated to one simple but challenging idea:<br>
      <span class="italic text-blue-800">making classic riddles expressible—and verifiable—through code.</span>
    </p>
    <p class="text-lg text-gray-700 mb-4">
      Most riddles are fun to think about, but notoriously hard to formalize in a way that a program can understand and validate.<br>
      <span class="block mt-2">Our goal is <span class="font-semibold text-blue-700">not</span> to teach programming or offer coding challenges like traditional platforms do.</span>
      Instead, we focus on a different kind of challenge: <span class="font-semibold">finding elegant ways to represent classic puzzles so they can be solved and automatically checked by a program.</span>
    </p>
    <div class="bg-blue-50 border-l-4 border-blue-400 p-4 rounded mb-4">
      <p class="text-blue-900 font-semibold mb-1">Example: The <a href="https://en.wikipedia.org/wiki/River_crossing_puzzle" target="_blank" class="underline hover:text-blue-700">River Crossing Puzzle</a></p>
      <p class="text-gray-700">
        A timeless riddle involving a farmer, a wolf, a goat, and a cabbage. It’s intuitive to understand, but hard to turn into a verifiable programming task.<br>
        On Coding Riddles, such a problem can be modeled as a set of functions that simulate possible actions. The system then validates the solution by running the simulation and checking whether any failure conditions are triggered.
      </p>
    </div>
    <p class="text-lg text-gray-700 mb-4">
      In short, <strong>Coding Riddles</strong> is a playground for encoding logic and cleverness, not just code.
    </p>
    <div class="mt-8">
      <p class="text-base text-gray-700 mb-2">
        <strong>Want to contribute?</strong> Coding Riddles is open source and welcomes creative minds! Visit the GitHub repository to share ideas, report issues, or submit pull requests:
      </p>
      <a href="https://github.com/zurakin/coding-riddles/" target="_blank" class="text-blue-700 font-semibold underline hover:text-blue-900">
        https://github.com/zurakin/coding-riddles/
      </a>
    </div>
  </div>
</template>

<script setup lang="ts">
// No logic needed for static about page
</script>

<style scoped>
</style>
