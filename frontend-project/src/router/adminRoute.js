//管理员登录
const loginRoute = {
    path: "/admin/login",
    name: "AdminLogin",
    component: () => import("@/views/adminAuthorization/LoginView.vue"),
};

//内容管理
const contentManagement = {
    path: "/admin/content",
    component: () => import("@/views/adminContentManagement/MenuFrame.vue"),
    children: [],
};

const adminRoute = {
    //管理员页面的前缀是ADMIN，重定向到功能总览首页
    path: "/admin",
    redirect: "/admin/content",
    component: () => import("@/views/AdminFrame.vue"),
    children: [
        //添加上面的各个视图
        loginRoute,
        contentManagement,
    ],
};

export default adminRoute;
