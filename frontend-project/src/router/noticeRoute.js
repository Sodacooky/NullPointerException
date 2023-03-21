const noticeRoute = {
    path: "/notice", redirect: "/notice/subscription", component: () => import("@/views/notice/Index.vue"),
    children: [
        {
            path: "/notice/subscription",
            name: "SubscriptionNotice",
            component: () => import("@/views/notice/SubscriptionView.vue")
        },
        {
            path: "/notice/article_reply",
            name: "ArticleNotice",
            component: () => import("@/views/notice/ArticleReplyView.vue")
        },
        {
            path: "/notice/system_notice",
            name: "SystemNotice",
            component: () => import("@/views/notice/SystemNoticeView.vue")
        },
    ]
}
export default noticeRoute;