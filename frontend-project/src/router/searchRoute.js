const searchRoute = {
    path: "/search/:type/:text",
    name: "Search",
    props: true,
    component: () => import("@/views/search/Index.vue")
}

export default searchRoute;