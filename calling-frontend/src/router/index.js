import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/components/user/SignUp.vue'
import Login from '@/components/user/Login.vue'
import Home from '@/components/home/Home.vue'

const routes = [
    {
        // name: 'SignUp',
        component: SignUp,
        path: '/lu/signup',
    },
    {
        // name: 'Login',
        component: Login,
        path:'/lu/login',
    },
    {component: Home, path:'/lu',}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;