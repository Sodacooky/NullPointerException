const searchRoute = {
    path: "/search", redirect: "/search/question", component: () => import("@/views/search/Index.vue"),
    children: [
        {
            path: "/search/question",
            name: "QuestionSearch",
            component: () => import("@/views/search/components/QuestionResultList.vue")
        },
        {
            path: "/search/article",
            name: "ArticleSearch",
            component: () => import("@/views/search/components/ArticleResultList.vue")
        },
        {
            path: "/search/user",
            name: "UserSearch",
            component: () => import("@/views/search/components/UserResultList.vue")
        },
    ]
}

export default searchRoute;