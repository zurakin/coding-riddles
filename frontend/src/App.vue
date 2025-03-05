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
  selectedRiddle.value = await new RiddlesManagement().getRiddle(riddleIdentifier);
  if (selectedRiddle.value) {
    code.value = selectedRiddle.value.code;
  }
};

import { shallowRef } from 'vue'
import { VueMonacoEditor } from '@guolao/vue-monaco-editor'
const MONACO_EDITOR_OPTIONS = {
  automaticLayout: true,
  formatOnType: true,
  formatOnPaste: true,
}

const editorRef = shallowRef();
const handleMount = (editor:any) => (editorRef.value = editor);

</script>

<template>
  <div class="flex">
    <div class="w-1/5">
      <RiddlesList @riddle-selected="onRiddleSelected"/>
    </div>
    <div class="w-3/5">
      <div class="h-7/10">
        <vue-monaco-editor
          v-model:value="code"
          theme="vs-dark" language="javascript"
          :options="MONACO_EDITOR_OPTIONS"
          @mount="handleMount"
        />
      </div>
      <div class="h-3/10 bg-gray-500 ">
        <CodeEditor :read-only="true" theme="github" :display-language="false" width="100%" height="100%" :header="false" v-model="terminal"/>
      </div>
    </div>
    <div class="w-1/5 flex">
      <Validation :riddle="selectedRiddle"  @test-executed="testExecuted" :code="code" />
    </div>
  </div>
</template>

<style>
</style>