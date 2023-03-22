<template>

  <!--  最新文章-->
  <div v-if="type==='latest'">
    <!--    文章列表-->
    <div class="list" v-for="item in resultData" :key="item.id">
      <div class="preview-card">
        <div class="title">{{ item.title }}</div>
        <div class="bottom-bar">
          <span style="float: left"><el-tag type="info">{{ item.category }}</el-tag></span>
          <span style="float: right;color: gray">{{ item.publish_time }}</span>
        </div>
        <el-divider/>
      </div>
    </div>
    <!--加载更多按钮-->
    <div style="display: flex;justify-content: center;margin-top: 16px" v-if="currentPage!==-1">
      <el-button type="primary" @click="onLoadMoreButtonClick">加载更多</el-button>
    </div>
  </div>

  <!--  周热门文章-->
  <div v-if="type==='weekly'">
    <!--    文章列表-->
    <div class="list" v-for="(item,index) in resultData" :key="item.id">
      <div class="preview-card">
        <div class="title">
          <span :style="{color: index<3?'red':'black'}">#{{ index + 1 }}</span>
          {{ item.title }}
        </div>
        <div class="bottom-bar">
          <span style="float: left"><el-tag type="info">{{ item.category }}</el-tag></span>
          <span style="float: right;color: gray">{{ item.publish_time }}</span>
        </div>
        <el-divider/>
      </div>
    </div>
  </div>

  <!--  月热门文章-->
  <div v-if="type==='monthly'">
    <!--    文章列表-->
    <div class="list" v-for="(item,index) in resultData" :key="item.id">
      <div class="preview-card">
        <div class="title">
          <span :style="{color: index<5?'red':'black'}">#{{ index + 1 }}</span>
          {{ item.title }}
        </div>
        <div class="bottom-bar">
          <span style="float: left"><el-tag type="info">{{ item.category }}</el-tag></span>
          <span style="float: right;color: gray">{{ item.publish_time }}</span>
        </div>
        <el-divider/>
      </div>
    </div>
  </div>

  <!--  无论哪一个，没有数据都是展示这个-->
  <el-empty v-if="resultData.length<=0" description="暂时没有数据"/>

</template>

<script>
export default {
  name: "ArticleListView",
  props: ['type'],
  data() {
    return {
      currentPage: -1, //对于latest可以按按钮加载更多，设定一个页数，为-1时隐藏按钮
      resultData: [],
    }
  },
  methods: {
    //最新问题的获取更多按钮
    onLoadMoreButtonClick() {
      this.loadLatestArticle(this.currentPage + 1);
    },
    //获取最新
    loadLatestArticle(page) {
      if (page === this.currentPage) return;//do nothing
      //do api
      this.resultData.push({
        "id": page,
        "title": "最新标题" + page,
        "category": "分类",
        "publisher_name": "发布者",
        "publish_time": "2022-1-1 00:00:00"
      });
      //this.currentPage = page;
      //还得判断有没有新数据，没有新数据的话设置为-1
      this.currentPage = -1;
    },
    //获取周榜
    loadWeeklyArticle() {
      this.resultData.push({
        "id": 1,
        "title": "周榜标题" + 1,
        "category": "分类",
        "publisher_name": "发布者",
        "publish_time": "2022-1-1 00:00:00"
      });
      this.resultData.push({
        "id": 2,
        "title": "周榜标题" + 2,
        "category": "分类",
        "publisher_name": "发布者",
        "publish_time": "2022-1-1 00:00:00"
      });
    },
    //获取月榜
    loadMonthlyArticle() {
      this.resultData.push({
        "id": 1,
        "title": "月榜标题" + 1,
        "category": "分类",
        "publisher_name": "发布者",
        "publish_time": "2022-1-1 00:00:00"
      });
      this.resultData.push({
        "id": 2,
        "title": "月榜标题" + 2,
        "category": "分类",
        "publisher_name": "发布者",
        "publish_time": "2022-1-1 00:00:00"
      });
    },
    //switch结构的，根据type类型进行不同的初始load
    initResultData(type) {
      this.currentPage = -1;
      this.resultData = [];
      switch (type) {
        case "latest":
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
    }
  }
}
</script>

<style scoped>
.preview-card {
  padding: 8px 8px 2px 8px;
}

.preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.preview-card .title {
  font-weight: bold;
}

.preview-card .text-short {
  font-size: small;
  color: gray;
}

.preview-card .bottom-bar {
  margin-top: 2px;
  height: 10px;
}
</style>