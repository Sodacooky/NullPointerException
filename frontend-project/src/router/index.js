import {createRouter, createWebHistory} from "vue-router";
import userRoute from "@/router/userRoute";
import adminRoute from "@/router/adminRoute";

const routerRules = [userRoute, adminRoute];

const router = createRouter({routes: routerRules, history: createWebHistory()});
export default router;