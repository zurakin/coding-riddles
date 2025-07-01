import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import RiddlePage from './pages/RiddlePage.vue';
import About from './pages/About.vue';
import RiddlesPage from './pages/RiddlesPage.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: RiddlePage
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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
