<template>
  <!--  最新问题-->
  <div v-if="type === 'latest'">
    <!--    问题列表-->
    <div v-for="item in resultData" :key="item.id" class="list">
      <QuestionPreviewItem :item="item" />
    </div>
    <!--加载更多按钮-->
    <div
      v-if="currentPage !== -1"
      style="display: flex; justify-content: center; margin-top: 16px"
    >
      <el-button @click="this.loadLatestQuestion(this.currentPage + 1)">
        加载更多
      </el-button>
    </div>
    <div v-else>
      <el-divider>已经看到底了！</el-divider>
    </div>
  </div>

  <!--  周热门问题-->
  <div v-if="type === 'weekly'">
    <!--    问题列表-->
    <div v-for="(item, index) in resultData" :key="item.id" class="list">
      <QuestionPreviewItem :is-rank="true" :item="item" :rank-index="index" />
    </div>
  </div>

  <!--  月热门问题-->
  <div v-if="type === 'monthly'">
    <!--    问题列表-->
    <div v-for="(item, index) in resultData" :key="item.id" class="list">
      <QuestionPreviewItem :is-rank="true" :item="item" :rank-index="index" />
    </div>
  </div>

  <!--  无论哪一个，没有数据都是展示这个-->
  <el-empty v-if="resultData.length <= 0" description="暂时没有数据" />
</template>

<script>
import moment from "moment/moment";
import {
  getLatestQuestion,
  getMonthlyQuestion,
  getWeeklyQuestion,
} from "@/api/home";
import QuestionPreviewItem from "@/components/QuestionPreviewItem.vue";

export default {
  name: "QuestionListView",
  components: { QuestionPreviewItem },
  props: ["type"],
  data() {
    return {
      currentPage: -1, //对于latest可以按按钮加载更多，设定一个页数，为-1时隐藏按钮
      resultData: [],
      queryTime: "", //获取第一页数据时的时间
    };
  },
  methods: {
    //获取最新问题
    loadLatestQuestion(page) {
      if (page === this.currentPage) return; //do nothing
      //do api
      getLatestQuestion(page, this.queryTime).then((resp) => {
        if (resp.data.data.length === 0) this.currentPage = -1;
        else {
          this.currentPage = page;
          this.resultData = this.resultData.concat(resp.data.data);
        }
      });
    },
    //获取周榜
    loadWeeklyQuestion() {
      getWeeklyQuestion().then((resp) => (this.resultData = resp.data.data));
    },
    //获取月榜
    loadMonthlyQuestion() {
      getMonthlyQuestion().then((resp) => (this.resultData = resp.data.data));
    },
    //switch结构的，根据type类型进行不同的初始load
    initResultData(type) {
      this.currentPage = -1;
      this.resultData = [];
      switch (type) {
        case "latest":
          this.queryTime = moment().format("yyyy-MM-DD HH:mm:ss");
          this.loadLatestQuestion(1);
          break;
        case "weekly":
          this.loadWeeklyQuestion();
          break;
        case "monthly":
          this.loadMonthlyQuestion();
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
