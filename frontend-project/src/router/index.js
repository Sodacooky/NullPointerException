import {createRouter, createWebHistory} from "vue-router";
import homeRoute from "@/router/homeRoute";
import searchRoute from "@/router/searchRoute";

const routerRules = [
    //根目录重定向到首页，首页为最新问题列表
    {
        path: "/", redirect: "/home", component: () => import("@/views/UserFrame.vue"),
        children: [
            homeRoute,    //首页部分
            searchRoute,  //搜索部分
        ]
    },

];

const router = createRouter({routes: routerRules, history: createWebHistory()});
export default router;