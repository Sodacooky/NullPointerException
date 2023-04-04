//用户界面相关的路由

//首页
const homeRoute = {
  //首页重定向到home/question
  path: "/home",
  redirect: "/home/question",
  component: () => import("@/views/home/Index.vue"),
  children: [
    //问题类
    { path: "/home/question", redirect: "/home/question/latest" },
    {
      path: "/home/question/:type",
      name: "HomeQuestionView",
      props: true,
      component: () => import("@/views/home/QuestionListView.vue"),
    },
    //文章类
    { path: "/home/article", redirect: "/home/article/monthly" },
    {
      path: "/home/article/:type",
      name: "HomeArticleView",
      props: true,
      component: () => import("@/views/home/ArticleListView.vue"),
    },
  ],
};

//用户消息
const noticeRoute = {
  path: "/notice",
  redirect: "/notice/subscription",
  component: () => import("@/views/notice/Index.vue"),
  children: [
    {
      path: "/notice/subscription",
      name: "SubscriptionNotice",
      component: () => import("@/views/notice/SubscriptionView.vue"),
    },
    {
      path: "/notice/article_reply",
      name: "ArticleNotice",
      component: () => import("@/views/notice/ArticleReplyView.vue"),
    },
    {
      path: "/notice/system_notice",
      name: "SystemNotice",
      component: () => import("@/views/notice/SystemNoticeView.vue"),
    },
  ],
};

//搜索
const searchRoute = {
  path: "/search",
  name: "Search",
  component: () => import("@/views/search/Index.vue"),
};

//用户个人页面
const profileRoute = {
  path: "/profile",
  name: "Profile",
  component: () => import("@/views/profile/ProfileIndex.vue"),
};

//问题发布页
const questionPublishRoute = {
  path: "/publisher/question",
  name: "QuestionPublisher",
  component: () => import("@/views/publisher/QuestionEditor.vue"),
};

//文章发布页
const articlePublishRoute = {
  path: "/publisher/article",
  name: "ArticlePublisher",
  component: () => import("@/views/publisher/ArticleEditor.vue"),
};

//个人设置页面
const profileSettingsRoute = {
  path: "/settings",
  name: "ProfileSettings",
  component: () => import("@/views/profile/ProfileSettingsIndex.vue"),
};

//登录页面
const loginRoute = {
  path: "/login",
  name: "UserLogin",
  component: () => import("@/views/authorization/LoginView.vue"),
};

//注册页面
const registerRoute = {
  path: "/register",
  name: "UserRegister",
  component: () => import("@/views/authorization/RegisterView.vue"),
};

// 文章详情页面
const articleReadingRoute = {
  path: "/article/:article_id",
  name: "ArticleReading",
  props: true,
  component: () => import("@/views/reading/ArticleReading.vue"),
};

//问题详情页
const questionReadingRoute = {
  path: "/question/:question_id",
  name: "QuestionReading",
  props: true,
  component: () => import("@/views/reading/QuestionReading.vue"),
};

const userRoute = {
  //访问网站根目录，重定向到home（home又会重定向到home/question
  path: "/",
  redirect: "/home",
  component: () => import("@/views/UserFrame.vue"),
  children: [
    //publisher什么都没有，仅做前缀作用
    { path: "/publisher", redirect: "/" },
    //添加上面的各个视图
    homeRoute,
    noticeRoute,
    searchRoute,
    profileRoute,
    questionPublishRoute,
    articlePublishRoute,
    profileSettingsRoute,
    loginRoute,
    registerRoute,
    articleReadingRoute,
    questionReadingRoute,
  ],
};

export default userRoute;
