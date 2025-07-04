import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import RiddlePage from './pages/RiddlePage.vue';
import About from './pages/About.vue';
import RiddlesPage from './pages/RiddlesPage.vue';
import SubmitRiddlePage from './pages/SubmitRiddlePage.vue';
import PreviewBeforeSubmitPage from './pages/PreviewBeforeSubmitPage.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: RiddlesPage
  },
  {
    path: '/riddle/:id',
    name: 'Riddle',
    component: RiddlePage
  },
  {
    path: '/riddles',
    name: 'Riddles',
    component: RiddlesPage
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/submit',
    name: 'SubmitRiddle',
    component: SubmitRiddlePage
  },
  {
    path: '/preview-submit',
    name: 'PreviewBeforeSubmitPage',
    component: () => PreviewBeforeSubmitPage
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
