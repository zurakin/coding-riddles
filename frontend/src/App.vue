<script setup lang="ts">
import { ref, type Ref } from 'vue';
import RiddlesList from './components/RiddlesList.vue';
import hljs from 'highlight.js';
import CodeEditor from 'simple-code-editor';
import Validation from './components/Validation.vue';

const code: Ref<string> = ref('// type your code here');
const riddleId: Ref<number> = ref(-1);
const codeChanged = () => {
  console.log("Code updated");
};

const onRiddleSelected = (riddleIdentifier: number) => {
  riddleId.value = riddleIdentifier;
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
        :display-language="true" 
        :header="true"
        v-model="code" @update:modelValue="codeChanged"
        :highlight="hljs" :languages="[['javascript', 'JavaScript']]" />
      </div>
      <div class="h-3/10 bg-gray-500 ">
        Terminal
      </div>
    </div>
    <div class="w-1/5 flex">
      <Validation :riddleId="riddleId"/>
    </div>
  </div>
</template>

<style>
</style>