const homeRoute = {
    //首页重定向到home/question
    path: "/home", redirect: "/home/question", component: () => import("@/views/home/Index.vue"),
    children: [
        //问题列表
        {path: "/home/question", redirect: "/home/question/latest"},
        {
            path: "/home/question/latest",
            name: "HomeLatestQuestion",
            component: () => import("@/views/home/components/LatestQuestionList.vue")
        },
        {
            path: "/home/question/weekly",
            name: "HomeWeeklyQuestion",
            component: () => import("@/views/home/components/WeeklyQuestionList.vue")
        },
        {
            path: "/home/question/monthly",
            name: "HomeMonthlyQuestion",
            component: () => import("@/views/home/components/MonthlyQuestionList.vue")
        },
        //文章列表
        {path: "/home/article", redirect: "/home/article/latest"},
        {
            path: "/home/article/latest",
            name: "HomeLatestArticle",
            component: () => import("@/views/home/components/LatestArticleList.vue")
        },
        {
            path: "/home/article/weekly",
            name: "HomeWeeklyArticle",
            component: () => import("@/views/home/components/WeeklyArticleList.vue")
        },
        {
            path: "/home/article/monthly",
            name: "HomeMonthlyArticle",
            component: () => import("@/views/home/components/MonthlyArticleList.vue")
        },
    ]
}

export default homeRoute