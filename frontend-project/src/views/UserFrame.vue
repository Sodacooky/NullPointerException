<template>
  <div class="common-layout">
    <el-container>
      <!--      顶栏-->
      <el-header>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
          <!--    LOGO-->
          <el-tooltip content="返回首页" placement="bottom">
            <router-link to="/">
              <img src="/logo.png" alt="website logo" width="128"/>
            </router-link>
          </el-tooltip>
          <!--    使其他元素靠右的占位器-->
          <div class="flex-grow"/>
          <!--    右侧的菜单-->
          <el-menu-item index="2">发布问题</el-menu-item>
          <el-menu-item index="3">发布文章</el-menu-item>
          <el-menu-item index="4">消息(1)</el-menu-item>
          <el-sub-menu index="5">
            <template #title>我的</template>
            <el-menu-item index="5-1">当前用户：Sodacooky</el-menu-item>
            <el-menu-item index="5-2">设置</el-menu-item>
            <el-menu-item index="5-3">退出登录</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>

      <!--      主体-->
      <el-container>
        <!--          根据需求切换不同的列表显示-->
        <router-view></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "UserFrame",
  data() {
    return {
      activeIndex: '1',
    }
  },
}
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

</style>