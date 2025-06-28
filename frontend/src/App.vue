<script setup lang="ts">
import { ref, type Ref } from 'vue';
import RiddlesList from './components/RiddlesList.vue';
import hljs from 'highlight.js';
import CodeEditor from 'simple-code-editor';
import Validation from './components/Validation.vue';
import type { Riddle } from './model/riddle';
import { RiddlesManagement } from './RiddlesManagement/riddles_management';

const selectedRiddle: Ref<Riddle | undefined> = ref(undefined);
const code: Ref<string> = ref('// Select a riddle to start coding');
const terminal: Ref<string> = ref('Run your code to see the output here');
const loadingRiddle = ref(false);

const codeChanged = () => {
  // console.log("Code updated");
};

const testExecuted = (output: string, append: boolean = false) => {
  if (append) {
    terminal.value += output + "\n";
  } else {
    terminal.value = output;
  }
};

const onRiddleSelected = async (riddleIdentifier: number) => {
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
};

import { shallowRef } from 'vue'
import { VueMonacoEditor } from '@guolao/vue-monaco-editor'
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
}

const editorRef = shallowRef();
const handleMount = (editor: any) => (editorRef.value = editor);

</script>

<template>
  <div class="min-h-screen h-screen w-full bg-gradient-to-br from-blue-50 to-slate-100 flex flex-col">
    <header class="w-full py-4 px-8 bg-white shadow flex items-center justify-between">
      <h1 class="text-3xl font-extrabold text-blue-700 tracking-wide">Coding Riddles</h1>
      <span class="text-base text-blue-400 font-semibold">Challenge your mind with fun riddles!</span>
    </header>
    <main class="flex-1 flex flex-row gap-4 px-2 py-6 w-full">
      <!-- Left: Riddles List -->
      <aside class="w-[260px] max-w-xs flex flex-col gap-4">
        <div class="bg-white rounded-2xl shadow-xl border border-blue-200 p-4 flex-1 flex flex-col">
          <RiddlesList @riddle-selected="onRiddleSelected" class="flex-1" />
        </div>
      </aside>
      <!-- Center: Code Editor & Test Output -->
      <section class="flex-1 flex flex-col gap-4 min-w-0">
        <div class="flex-1 bg-white rounded-2xl shadow-xl border border-blue-200 p-4 flex flex-col min-w-0">
          <div class="flex items-center justify-between mb-2">
            <h2 class="text-xl font-bold text-blue-700">Code Editor</h2>
          </div>
          <vue-monaco-editor
            v-model:value="code"
            theme="vs"
            language="javascript"
            :options="MONACO_EDITOR_OPTIONS"
            @mount="handleMount"
            width="100%"
            height="100%"
            class="rounded-lg border border-blue-100 bg-white min-w-0 flex-1"
          />
        </div>
        <div class="bg-blue-50 rounded-2xl shadow border border-blue-200 p-4 flex flex-col min-h-[180px] min-w-0">
          <div class="flex items-center justify-between mb-2">
            <h2 class="text-lg font-semibold text-blue-700">Test Output</h2>
          </div>
          <vue-monaco-editor
            v-model:value="terminal"
            theme="vs-light"
            language="plaintext"
            :options="{
              readOnly: true,
              minimap: { enabled: false },
              lineNumbers: 'off',
              fontSize: 14,
              fontFamily: 'Fira Mono, Menlo, Monaco, Consolas, monospace',
              wordWrap: 'on',
              scrollbar: { vertical: 'auto', horizontal: 'auto' },
              automaticLayout: true
            }"
            width="100%"
            height="120px"
            class="rounded-lg border border-blue-100 bg-white min-w-0"
          />
        </div>
      </section>
      <!-- Right: Validation -->
      <aside class="w-[340px] max-w-sm flex flex-col gap-4">
        <div class="bg-white rounded-2xl shadow-xl border border-blue-200 p-4 flex-1 flex flex-col">
          <Validation :riddle="selectedRiddle" @test-executed="testExecuted" :code="code" class="flex-1" />
        </div>
      </aside>
    </main>
  </div>
</template>

<style>
html, body, #app {
  height: 100%;
}

main {
  min-height: 0;
}
</style>