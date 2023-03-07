const homeRouter = {
    //首页重定向到home/question
    path: "/", redirect: "/home/question", component: () => import("@/views/UserFrame.vue"),
    children: [
        {
            //home也要重定向到question
            path: "/home", redirect: "/home/question", component: () => import("@/views/home/HomeBase.vue"),
            children: [
                {
                    path: "/home/question",
                    name: "HomeQuestion",
                    component: () => import("@/views/home/HomeQuestionList.vue")
                },
                {
                    path: "/home/article",
                    name: "HomeArticle",
                    component: () => import("@/views/home/HomeArticleList.vue")
                },

            ]
        }
    ]
}

export default homeRouter