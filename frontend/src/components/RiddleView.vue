<script setup lang="ts">
import { ref } from 'vue';
import Validation from '../components/Validation.vue';
import { VueMonacoEditor } from '@guolao/vue-monaco-editor';
import type { Riddle } from '../model/models';


const props = defineProps<{
  riddle: Riddle | null,
}>();

const code = ref(props.riddle?.code ?? '');
const terminal = ref('Run your code to see the output here');
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
};

function testExecuted(output: string, append: boolean = false) {
  if (append) {
    terminal.value += output + "\n";
  } else {
    terminal.value = output;
  }
}

</script>

<template>
  <div class="flex-1 flex flex-row gap-4 w-full">
    <section class="flex-1 flex flex-col gap-4 min-w-0">
      <div class="flex flex-col min-w-0 flex-1 gap-4" style="height: 100%; min-height: 0;">
        <div class="bg-white rounded-2xl shadow-xl border border-blue-200 p-4 flex flex-col min-w-0" style="flex-basis: 70%; flex-grow: 0; flex-shrink: 0; height: 70%; min-height: 0;">
          <div class="flex items-center justify-between mb-2">
            <h2 class="text-xl font-semibold text-blue-700">Code Editor</h2>
          </div>
          <VueMonacoEditor
            v-model:value="code"
            theme="vs"
            language="javascript"
            :options="MONACO_EDITOR_OPTIONS"
            width="100%"
            height="100%"
            class="rounded-lg border border-blue-100 bg-white min-w-0 flex-1"
          />
        </div>
        <div class="bg-blue-50 rounded-2xl shadow border border-blue-200 p-4 flex flex-col min-h-[180px] min-w-0" style="flex-basis: 30%; flex-grow: 0; flex-shrink: 0; height: 30%; min-height: 0;">
          <div class="flex items-center justify-between mb-2">
            <h2 class="text-lg font-semibold text-blue-700">Test Output</h2>
          </div>
          <VueMonacoEditor
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
            height="100%"
            class="rounded-lg border border-blue-100 bg-white min-w-0 flex-1"
          />
        </div>
      </div>
    </section>
    <aside class="w-[32%] max-w-[40%] flex flex-col gap-4 h-full max-h-full">
      <div class="bg-white rounded-2xl shadow-xl border border-blue-200 p-4 flex-1 flex flex-col max-h-full overflow-y-auto">
        <Validation :riddle="riddle" @test-executed="testExecuted" :code="code" class="flex-1" />
      </div>
    </aside>
  </div>
</template>

<style scoped>
main {
  min-height: 0;
}
</style>
