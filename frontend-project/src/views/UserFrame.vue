<template>
  <div class="common-layout">
    <el-container>
      <!--      顶栏-->
      <el-header>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
          <!--    LOGO-->
          <el-menu-item index="0">
            <el-tooltip content="返回首页" placement="bottom">
              <img src="/logo.png" alt="website logo" width="128"/>
            </el-tooltip>
          </el-menu-item>
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
        <el-container>
          <el-main>
            <!--          搜索框-->
            <el-input v-model="searchContent" placeholder="搜索问题、文章和用户" style="font-size: large">
              <template #prepend>
                <el-icon>
                  <Search/>
                </el-icon>
              </template>
              <template #append>
                <el-butto>搜索</el-butto>
              </template>
            </el-input>
            <!--          根据需求切换不同的列表显示-->
            <router-view></router-view>
          </el-main>
        </el-container>
        <!--        侧边栏-->
        <el-aside width="300px" style="margin-right: 16px">
          <!--          公告-->
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>公告</span>
              </div>
            </template>
            <el-collapse v-model="activeAnnouncementName" accordion>
              <el-collapse-item title="公告1" name="1">
                一行简单的公告
              </el-collapse-item>
              <el-collapse-item title="公告2" name="2">
                <div>第一行</div>
                <div>用DIV分割每一行</div>
              </el-collapse-item>
              <el-collapse-item title="公告3" name="3">
                <span style="color: green">丰富的</span>
                <span style="color: orange">span</span>
              </el-collapse-item>
            </el-collapse>
          </el-card>
          <!--          热门分类-->
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>近期热门分类</span>
              </div>
            </template>
            <el-badge :value="12" class="hot-category-item">
              <el-button>Java</el-button>
            </el-badge>
            <el-badge :value="114514" class="hot-category-item">
              <el-button>运维</el-button>
            </el-badge>
            <el-badge :value="1" class="hot-category-item">
              <el-button>C++</el-button>
            </el-badge>
            <el-badge :value="2" class="hot-category-item">
              <el-button>互联网产业发展</el-button>
            </el-badge>
          </el-card>
          <!--          广告-->
          <el-card class="box-card" :body-style="{ padding: '0px' }">
            <template #header>
              <div class="card-header">
                <span>推广</span>
              </div>
            </template>
            <el-carousel height="150px">
              <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                <h3>广告位招租1</h3>
              </el-carousel-item>
              <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                <h3>广告位招租2</h3>
              </el-carousel-item>
              <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                <h3>广告位招租3</h3>
              </el-carousel-item>
            </el-carousel>
          </el-card>
          <!--          网站数据-->
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>网站状况</span>
              </div>
            </template>
            <p>本日新增问题：114514</p>
            <p>本日新增文章：666</p>
            <p>本日登录用户：1245</p>
            <p>总注册用户数：666666</p>
          </el-card>
        </el-aside>
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
      activeAnnouncementName: '-1',
      searchContent: '',
    }
  },
}
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}

.box-card {
  margin-top: 16px;
}

.hot-category-item {
  margin-right: 16px;
  margin-bottom: 8px;
}

</style>