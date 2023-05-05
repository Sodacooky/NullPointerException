<template>
  <!--  最新文章-->
  <div v-if="type === 'latest'">
    <!--    文章列表-->
    <div v-for="item in resultData" :key="item.id" class="list">
      <ArticlePreviewItem :item="item"></ArticlePreviewItem>
    </div>
    <!--加载更多按钮-->
    <div
      v-if="currentPage !== -1"
      style="display: flex; justify-content: center; margin-top: 16px"
    >
      <el-button @click="this.loadLatestArticle(this.currentPage + 1)">
        加载更多
      </el-button>
    </div>
    <div v-else>
      <el-divider>已经看到底了！</el-divider>
    </div>
  </div>

  <!--  周热门文章-->
  <div v-if="type === 'weekly'">
    <!--    文章列表-->
    <div v-for="(item, index) in resultData" :key="item.id" class="list">
      <ArticlePreviewItem :is-rank="true" :item="item" :rank-index="index" />
    </div>
  </div>

  <!--  月热门文章-->
  <div v-if="type === 'monthly'">
    <!--    文章列表-->
    <div v-for="(item, index) in resultData" :key="item.id" class="list">
      <ArticlePreviewItem :is-rank="true" :item="item" :rank-index="index" />
    </div>
  </div>

  <!--  无论哪一个，没有数据都是展示这个-->
  <el-empty v-if="resultData.length <= 0" description="暂时没有数据" />
</template>

<script>
import ArticlePreviewItem from "@/components/ArticlePreviewItem.vue";
import moment from "moment";
import { HomeApi } from "@/api/home";

export default {
  name: "ArticleListView",
  components: { ArticlePreviewItem },
  props: ["type"],
  data() {
    return {
      currentPage: -1, //对于latest可以按按钮加载更多，设定一个页数，为-1时隐藏按钮
      resultData: [],
      queryTime: "",
    };
  },
  methods: {
    //获取最新
    loadLatestArticle(page) {
      if (page === this.currentPage) return; //do nothing
      //do api
      HomeApi.getLatestArticle(page, this.queryTime).then((resp) => {
        if (resp.data.data.length === 0) this.currentPage = -1;
        else {
          this.currentPage = page;
          this.resultData = this.resultData.concat(resp.data.data);
        }
      });
    },
    //获取周榜
    loadWeeklyArticle() {
      HomeApi.getWeeklyArticle().then(
        (resp) => (this.resultData = resp.data.data)
      );
    },
    //获取月榜
    loadMonthlyArticle() {
      HomeApi.getMonthlyArticle().then(
        (resp) => (this.resultData = resp.data.data)
      );
    },
    //switch结构的，根据type类型进行不同的初始load
    initResultData(type) {
      this.currentPage = -1;
      this.resultData = [];
      switch (type) {
        case "latest":
          this.queryTime = moment().format("yyyy-MM-DD HH:mm:ss");
          this.loadLatestArticle(1);
          if (this.resultData.length > 0) this.currentPage = 1;
          break;
        case "weekly":
          this.loadWeeklyArticle();
          break;
        case "monthly":
          this.loadMonthlyArticle();
          break;
      }
    },
  },
  mounted() {
    this.initResultData(this.type);
  },
  watch: {
    //类型转变
    type(newVal, oldVal) {
      if (newVal === oldVal) return;
      this.initResultData(newVal);
    },
  },
};
</script>

<style scoped></style>