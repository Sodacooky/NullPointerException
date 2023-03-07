import {createRouter, createWebHistory} from "vue-router";

import homeRouter from "@/router/homeRouter";

const routerRules = [homeRouter,];

const router = createRouter({routes: routerRules, history: createWebHistory()});
export default router;