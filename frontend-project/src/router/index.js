import {createRouter, createWebHistory} from "vue-router";
import homeRoute from "@/router/homeRoute";
import searchRoute from "@/router/searchRoute";
import profileRoute from "@/router/profileRoute";
import noticeRoute from "@/router/noticeRoute";

const routerRules = [
    //根目录重定向到首页，首页为最新问题列表
    {
        path: "/", redirect: "/home", component: () => import("@/views/UserFrame.vue"),
        children: [
            homeRoute,    //首页部分
            searchRoute,  //搜索部分
            profileRoute, //用户页面部分
            noticeRoute,  //消息部分
            //两个发布页面
            {
                path: "/publisher/question",
                name: "QuestionPublisher",
                component: () => import("@/views/publisher/QuestionEditor.vue")
            },
            {
                path: "/publisher/article",
                name: "ArticlePublisher",
                component: () => import("@/views/publisher/ArticleEditor.vue")
            },
            {path: "/publisher", redirect: "/"},
            //个人设置页面
            {path: "/settings", name: "ProfileSettings", component: () => import("@/views/profileSettings/Index.vue")},
            //登录页面
            {
                path: "/login",
                name: "UserLogin",
                component: () => import("@/views/authorization/LoginView.vue")
            },
            //注册页面
            {
                path: "/register",
                name: "UserRegister",
                component: () => import("@/views/authorization/RegisterView.vue")
            },

        ]
    },

];

const router = createRouter({routes: routerRules, history: createWebHistory()});
export default router;