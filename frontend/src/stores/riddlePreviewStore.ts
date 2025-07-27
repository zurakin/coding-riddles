import { defineStore } from 'pinia';
import type { Riddle } from '../model/models';

export const useRiddlePreviewStore = defineStore('riddlePreview', {
  state: () => ({
    riddle: null as Riddle | null,
  }),
  actions: {
    setRiddle(riddle: Riddle) {
      this.riddle = riddle;
    },
    clearRiddle() {
      this.riddle = null;
    },
  },
});
