<template>
  <!--  主体部分-->
  <el-main style="min-width: 600px; margin-left: 96px">
    <!--          搜索框-->
    <el-input
      v-model="searchText"
      placeholder="搜索问题、文章和用户"
      style="font-size: large; margin-bottom: 16px"
      @keydown.enter="doSearch()"
    >
      <template #prepend>
        <el-icon>
          <Search />
        </el-icon>
      </template>
      <template #append>
        <el-button @click="doSearch()">搜索</el-button>
      </template>
    </el-input>
    <!--      首页内容切换-->
    <el-menu
      :default-active="activeListRoutePath"
      :router="true"
      mode="horizontal"
    >
      <el-menu-item index="/home/question/latest">最新问题</el-menu-item>
      <el-menu-item index="/home/question/weekly">问题周榜</el-menu-item>
      <el-menu-item index="/home/question/monthly">问题月榜</el-menu-item>
      <el-menu-item index="/home/article/latest">最新文章</el-menu-item>
      <el-menu-item index="/home/article/weekly">文章周榜</el-menu-item>
      <el-menu-item index="/home/article/monthly">文章月榜</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </el-main>

  <!--        侧边栏-->
  <el-aside style="width: 20pc; margin-right: 96px">
    <!--          公告-->
    <el-card :body-style="{ padding: '0 2px 0 8px' }" class="box-card">
      <template #header>
        <div class="card-header">
          <span>公告</span>
        </div>
      </template>
      <el-collapse
        v-if="announcement.length > 0"
        v-model="activeAnnouncementName"
        accordion
      >
        <el-collapse-item
          v-for="item in announcement"
          :key="item.time"
          :name="item.id"
          :title="item.title"
        >
          <div v-html="item.text"></div>
          <div style="font-size: small; color: gray">{{ item.time }}</div>
        </el-collapse-item>
      </el-collapse>
      <div v-else style="padding: 4px">公告栏空空如也...</div>
    </el-card>
    <!--          热门分类-->
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>近期热门分类</span>
        </div>
      </template>
      <div v-if="hotCategories.length > 0">
        <el-badge
          v-for="item in hotCategories"
          :value="item.amount"
          class="hot-category-item"
        >
          <el-button @click="hotCategoriesJump(item.category)">
            {{ item.category }}
          </el-button>
        </el-badge>
      </div>
      <div v-else>近期似乎没有文章</div>
    </el-card>
    <!--          广告-->
    <el-card :body-style="{ padding: '0px' }" class="box-card">
      <template #header>
        <div class="card-header">
          <span>推广</span>
        </div>
      </template>
      <el-carousel height="180px">
        <el-carousel-item v-if="ads.length < 1" style="text-align: center">
          <h3>广告位招租</h3>
        </el-carousel-item>
        <el-carousel-item v-for="item in ads" :key="item.id">
          <a :href="item.url" target="_blank">
            <el-image
              :src="HomeApi.getAdsImageUrl(item.image)"
              fit="fill"
            ></el-image>
          </a>
        </el-carousel-item>
      </el-carousel>
    </el-card>
    <!--          网站数据-->
    <el-card :body-style="{ padding: '16px' }" class="box-card">
      <template #header>
        <div class="card-header">
          <span>网站状况</span>
        </div>
      </template>
      <div>本日新增问题：{{ siteState.todayQuestionAmount }}</div>
      <div>本日新增文章：{{ siteState.todayArticleAmount }}</div>
      <div>总注册用户数：{{ siteState.totalUserAmount }}</div>
    </el-card>
    <!--网站联系方式等信息注脚-->
    <div style="margin-top: 32px; font-size: small">
      <!--联系方式        -->
      <div style="color: gray; text-align: center">
        <p>NullPointerException</p>
        <p>联系邮箱: 523379653@qq.com</p>
      </div>
      <!--进入后台        -->
      <div style="color: gray; justify-content: center; display: flex">
        <router-link to="/admin">点击进入管理后台</router-link>
      </div>
    </div>
  </el-aside>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import { HomeApi } from "@/api/home";

export default {
  name: "HomeIndex",
  computed: {
    HomeApi() {
      return HomeApi;
    },
  },
  components: { Search },
  data() {
    return {
      searchText: "",
      activeListRoutePath: "/home/question/latest",
      siteState: {},
      ads: [],
      hotCategories: [],
      activeAnnouncementName: "-1",
      announcement: [],
    };
  },
  methods: {
    doSearch() {
      if (this.searchText.length <= 0) this.searchText = " ";
      this.$router.push({
        path: "/search",
        query: { keyword: this.searchText },
      });
    },
    hotCategoriesJump(category) {
      this.$router.push({
        path: "/search",
        query: { keyword: category },
      });
    },
  },
  mounted() {
    this.activeListRoutePath = this.$route.path;
    //加载网站数据
    HomeApi.getSiteState().then((resp) => {
      this.siteState = resp.data.data;
    });
    //家在公告
    HomeApi.getAnnouncement().then(
      (resp) => (this.announcement = resp.data.data)
    );
    //加载广告
    HomeApi.getAds().then((resp) => (this.ads = resp.data.data));
    //加载热门分类
    HomeApi.getHotCategories().then(
      (resp) => (this.hotCategories = resp.data.data)
    );
  },
};
</script>

<style scoped>
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
