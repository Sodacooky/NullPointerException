//管理员登录
const loginRoute = {
  path: "/admin/login",
  name: "AdminLogin",
  component: () => import("@/views/adminAuthorization/Login.vue"),
};

//内容管理
const questionManagement = {
  path: "/admin/content/question",
  name: "QuestionManagement",
  component: () =>
    import("@/views/adminContentManagement/QuestionManagement.vue"),
};
const answerManagement = {
  path: "/admin/content/answer",
  name: "AnswerManagement",
  component: () =>
    import("@/views/adminContentManagement/AnswerManagement.vue"),
};
const articleManagement = {
  path: "/admin/content/article",
  name: "ArticleManagement",
  component: () =>
    import("@/views/adminContentManagement/ArticleManagement.vue"),
};
const articleReplyManagement = {
  path: "/admin/content/articleReply",
  name: "ArticleReplyManagement",
  component: () =>
    import("@/views/adminContentManagement/ArticleReplyManagement.vue"),
};
const userManagement = {
  path: "/admin/content/user",
  name: "UserManagement",
  component: () => import("@/views/adminContentManagement/UserManagement.vue"),
};
//end of 内容管理

//举报处理
const questionReport = {
  path: "/admin/report/question",
  name: "QuestionReport",
  component: () => import("@/views/adminReport/QuestionReport.vue"),
};
const articleReport = {
  path: "/admin/report/article",
  name: "ArticleReport",
  component: () => import("@/views/adminReport/ArticleReport.vue"),
};
const answerReport = {
  path: "/admin/report/answer",
  name: "AnswerReport",
  component: () => import("@/views/adminReport/AnswerReport.vue"),
};
const articleReplyReport = {
  path: "/admin/report/articleReply",
  name: "ArticleReplyReport",
  component: () => import("@/views/adminReport/ArticleReplyReport.vue"),
};
const userReport = {
  path: "/admin/report/user",
  name: "UserReport",
  component: () => import("@/views/adminReport/UserReport.vue"),
};
//end of 举报处理

//杂项
const noticeSend = {
  path: "/admin/misc/notice",
  name: "NoticeSend",
  component: () => import("@/views/adminMiscellaneous/NoticeSend.vue"),
};
const advertisementEdit = {
  path: "/admin/misc/ad",
  name: "AdvertisementEdit",
  component: () => import("@/views/adminMiscellaneous/AdvertisementEdit.vue"),
};
const announceEdit = {
  path: "/admin/misc/announce",
  name: "AnnounceEdit",
  component: () => import("@/views/adminMiscellaneous/AnnounceEdit.vue"),
};
//end of 杂项

const adminRoute = {
  //管理员页面的前缀是ADMIN，重定向到功能总览首页
  path: "/admin",
  redirect: "/admin/content/question",
  component: () => import("@/views/AdminFrame.vue"),
  children: [
    //添加上面的各个视图
    loginRoute,
    //内容管理
    questionManagement,
    answerManagement,
    articleManagement,
    articleReplyManagement,
    userManagement,
    //举报处理
    questionReport,
    articleReport,
    answerReport,
    articleReplyReport,
    userReport,
    //杂项
    noticeSend,
    advertisementEdit,
    announceEdit,
  ],
};

export default adminRoute;
