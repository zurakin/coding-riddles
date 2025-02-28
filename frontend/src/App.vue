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

const codeChanged = () => {
  // console.log("Code updated");
};

const onRiddleSelected = async (riddleIdentifier: number) => {
  selectedRiddle.value = await new RiddlesManagement().getRiddle(riddleIdentifier);
  if (selectedRiddle.value) {
    code.value = selectedRiddle.value.code;
  }
};

</script>

<template>
  <div class="flex">
    <div class="w-1/5">
      <RiddlesList @riddle-selected="onRiddleSelected"/>
    </div>
    <div class="w-3/5">
      <div class="h-7/10">
        <CodeEditor :line-nums="true" width="100%" height="100%"
        :display-language="true" :header="true"
        v-model="code" @update:modelValue="codeChanged"
        :highlight="hljs" :languages="[['javascript', 'JavaScript']]" />
      </div>
      <div class="h-3/10 bg-gray-500 ">
        Terminal
      </div>
    </div>
    <div class="w-1/5 flex">
      <Validation :riddle="selectedRiddle"/>
    </div>
  </div>
</template>

<style>
</style>