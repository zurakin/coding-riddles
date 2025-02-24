<script setup lang="ts">
import { ref, type Ref } from 'vue';
import RiddlesList from './components/RiddlesList.vue';
import hljs from 'highlight.js';
import CodeEditor from 'simple-code-editor';
</script>

<template>
  <div class="flex">
    <div class="w-1/5">
      <RiddlesList />
    </div>
    <div class="w-3/5">
      <CodeEditor line-nums="true" width="100%" 
      class="" display-language="false"
      :header="true"
      value="// type your code here"
      :highlight="hljs" :languages="[['javascript', 'JavaScript']]"></CodeEditor>
    </div>
    <div class="w-1/5">
      <Validator />
    </div>
  </div>
  <RiddlesList />
</template>

<style>
</style>