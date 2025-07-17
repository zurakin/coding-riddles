<template>
    <div class="w-[90%] max-w-xl mx-auto mt-10 bg-white rounded shadow p-8 overflow-y-auto h-[90%] min-h-0 ">
        <div class="flex flex-col gap-8">
          <!-- Login Form -->
          <form @submit.prevent="onSubmit" class="flex-1 bg-slate-50 rounded-lg shadow p-6">
            <h3 class="text-xl font-bold mb-4 text-blue-700 text-center">Login</h3>
            <div class="mb-4">
              <label class="block text-gray-700">Username:</label>
              <input v-model="username" class="input" required autocomplete="username" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700">Password:</label>
              <input v-model="password" class="input" type="password" required autocomplete="current-password" />
            </div>
            <button type="submit" class="btn btn-primary w-full" :disabled="loading">Login</button>
            <div v-if="error && !signupError" class="text-red-600 mt-2 text-center">{{ error }}</div>
          </form>
          <h3 class="text-xl font-bold mb-4 text-blue-700 text-center">Or</h3>
          <!-- Signup Form -->
          <form @submit.prevent="onSignup" class="flex-1 bg-slate-50 rounded-lg shadow p-6">
            <h3 class="text-xl font-bold mb-4 text-blue-700 text-center">Sign Up</h3>
            <div class="mb-4">
              <label class="block text-gray-700">Username:</label>
              <input v-model="signupUsername" class="input" required autocomplete="username" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700">Password:</label>
              <input v-model="signupPassword" class="input" type="password" required autocomplete="new-password" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700">Repeat Password:</label>
              <input v-model="signupPasswordRepeat" class="input" type="password" required autocomplete="new-password" />
            </div>
            <button type="submit" class="btn btn-secondary w-full" :disabled="loading">Sign Up</button>
            <div v-if="signupError" class="text-red-600 mt-2 text-center">{{ signupError }}</div>
          </form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from 'vue';
import { useRouter } from 'vue-router';
import { UserManagement } from '../UserManagement/user_management';


const emit = defineEmits(['auth-success']);
const username = ref('');
const password = ref('');
const error = ref('');
const loading = ref(false);
const signupUsername = ref('');
const signupPassword = ref('');
const signupPasswordRepeat = ref('');
const signupError = ref('');
const router = useRouter();
const um = new UserManagement();

// Redirect to profile if already logged in
if (localStorage.getItem('token')) {
  router.replace('/profile');
}

async function onSubmit() {
  error.value = '';
  loading.value = true;
  try {
    const token = await um.login(username.value, password.value);
    localStorage.setItem('token', token);
    emit('auth-success');
    router.push('/profile');
  } catch (e: any) {
    error.value = e.message || 'Login failed';
  } finally {
    loading.value = false;
  }
}

async function onSignup() {
  signupError.value = '';
  loading.value = true;
  if (signupPassword.value !== signupPasswordRepeat.value) {
    signupError.value = 'Passwords do not match.';
    loading.value = false;
    return;
  }
  try {
    await um.register(signupUsername.value, signupPassword.value);
    // After signup, log in automatically
    username.value = signupUsername.value;
    password.value = signupPassword.value;
    await onSubmit();
  } catch (e: any) {
    signupError.value = e.message || 'Signup failed';
    loading.value = false;
  }
}

</script>

<style scoped>
.input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem;
    margin-top: 0.25rem;
}

.btn {
    padding: 0.5rem 1.5rem;
    border-radius: 0.375rem;
    font-weight: 600;
    border: none;
    cursor: pointer;
}

.btn-primary {
    background: #2563eb;
    color: white;
}

.btn-primary:hover {
    background: #1d4ed8;
}

.btn-secondary {
    background: #e5e7eb;
    color: #1e293b;
}

.btn-secondary:hover {
    background: #cbd5e1;
}
</style>
