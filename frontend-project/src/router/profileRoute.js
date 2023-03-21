const profileRoute = {
    path: "/profile",
    name: "Profile",
    component: () => import("@/views/profile/Index.vue"),
};

export default profileRoute;