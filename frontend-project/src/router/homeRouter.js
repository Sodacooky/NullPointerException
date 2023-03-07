const homeRouter = {
    //首页重定向到home/question
    path: "/", redirect: "/home/question",
    children: [
        {
            //home也要重定向到question
            path: "/home", redirect: "/home/question", component: () => import("@/views/UserFrame.vue"),
            children: [
                {path: "/home/question", name: "HomeQuestion", compoent: () => import("@/views/home/Question.vue")}
            ]
        }
    ]
}

export default homeRouter