import { createRouter, createWebHashHistory } from 'vue-router'
import SignUp from '@/components/user/SignUp.vue'

const routes = [
    {
        name: 'SignUp',
        component: SignUp,
        path: '/',
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;