<template>
    <el-container>
        <el-main>
            <el-row>
                <el-col :span="3">
                    <el-menu :default-active="activeTypeRoutePath" :router="true">
                        <el-menu-item index="/notice/subscription">订阅的问题</el-menu-item>
                        <el-menu-item index="/notice/subscription">回答的回复</el-menu-item>
                        <el-menu-item index="/notice/article_reply">文章的回复</el-menu-item>
                        <el-menu-item index="/notice/system_notice">系统通知</el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="21" style="padding: 0 32px 0 32px">
                    <router-view></router-view>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: "NoticeIndex",
    data() {
        return {
            activeTypeRoutePath: "/notice/subscription",
        }
    },
    mounted() {
        this.activeTypeRoutePath = this.$route.path;
    }
}
</script>

<style scoped>

</style>