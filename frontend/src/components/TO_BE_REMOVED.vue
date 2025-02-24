<script setup lang="ts">
import { nextTick, ref } from 'vue';
const code = ref<string>('// Write your JavaScript code here\n');

const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'Tab') {
        event.preventDefault();
        const target = event.target as HTMLTextAreaElement;
        const start = target.selectionStart;
        const end = target.selectionEnd;
        code.value = code.value.substring(0, start) + "\t" + code.value.substring(end);
        nextTick(() => {
            target.selectionStart = target.selectionEnd = start + 1;
        });
    }
};
</script>

<template>
    <div class="p-4">
        <textarea v-model="code"
            class="w-full h-96 p-4 bg-gray-900 text-green-400 font-mono text-sm rounded-lg focus:outline-none"
            spellcheck="false"
            @keydown="handleKeyDown"></textarea>
    </div>
</template>

<style scoped></style>
