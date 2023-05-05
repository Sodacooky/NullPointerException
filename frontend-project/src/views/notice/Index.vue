<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="3">
          <el-menu :default-active="activeTypeRoutePath" :router="true">
            <el-menu-item index="/notice/subscription">
              <el-icon>
                <Bell />
              </el-icon>
              订阅问题
            </el-menu-item>
            <el-menu-item index="/notice/article_reply">
              <el-icon>
                <ChatDotRound />
              </el-icon>
              文章回复
            </el-menu-item>
            <el-menu-item index="/notice/system_notice">
              <el-icon>
                <Warning />
              </el-icon>
              系统消息
            </el-menu-item>
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
    };
  },
  mounted() {
    this.activeTypeRoutePath = this.$route.path;
  },
};
</script>

<style scoped></style>
