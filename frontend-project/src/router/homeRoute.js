const homeRoute = {
    //首页重定向到home/question
    path: "/home", redirect: "/home/question", component: () => import("@/views/home/Index.vue"),
    children: [
        //问题类
        {path: "/home/question", redirect: "/home/question/latest"},
        {
            path: "/home/question/:type",
            name: "HomeQuestionView",
            props: true,
            component: () => import("@/views/home/QuestionListView.vue")
        },
        //文章类
        {path: "/home/article", redirect: "/home/article/monthly"},
        {
            path: "/home/article/:type",
            name: "HomeArticleView",
            props: true,
            component: () => import("@/views/home/ArticleListView.vue")
        }
    ]
}

export default homeRoute