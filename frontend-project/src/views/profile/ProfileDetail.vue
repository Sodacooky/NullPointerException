<template>
  <div class="content-container">
    <div v-if="type === 'answer'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <AnswerPreviewItem :item="item" />
      </div>
    </div>

    <div v-if="type === 'question'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <QuestionPreviewItem :item="item" />
      </div>
    </div>

    <div v-if="type === 'article'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <ArticlePreviewItem :item="item" />
      </div>
    </div>
  </div>

  <div v-if="showLoadMoreButton" class="bottom-load-more-container">
    <el-button ref="loadMoreButton" @click="onLoadMoreButtonClick()"
      >加载更多
    </el-button>
  </div>
</template>

<script>
import QuestionPreviewItem from "@/components/QuestionPreviewItem.vue";
import AnswerPreviewItem from "@/views/profile/components/AnswerPreviewItem.vue";
import ArticlePreviewItem from "@/components/ArticlePreviewItem.vue";

export default {
  name: "ProfileDetail",
  components: { ArticlePreviewItem, AnswerPreviewItem, QuestionPreviewItem },
  props: ["user_id", "type"],
  data() {
    return {
      showLoadMoreButton: true,
      currentPage: 1,
      resultData: [],
    };
  },
  methods: {
    onLoadMoreButtonClick() {
      this.currentPage++;
      this.loadResultData(this.type, this.currentPage);
    },
    loadAnswer(page) {
      return {
        id: page,
        question_title: "问题名称",
        short_answer: "回答内容概要",
        question_category: "分类",
        publish_time: "2022-11-11 11:11:11",
      };
    },
    loadQuestion(page) {
      return {
        id: page,
        title: "问题名称",
        short: "内容概要",
        category: "分类",
        publish_time: "2022-11-11 11:11:11",
      };
    },
    loadArticle(page) {
      return {
        id: page,
        title: "文章名称",
        category: "分类",
        publish_time: "2022-11-11 11:11:11",
      };
    },
    // 根据type和page加载数据
    loadResultData(type, page) {
      switch (type) {
        case "answer":
          this.resultData.push(this.loadAnswer(page));
          break;
        case "question":
          this.resultData.push(this.loadQuestion(page));
          break;
        case "article":
          this.resultData.push(this.loadArticle(page));
          break;
      }
    },
  },
  mounted() {
    //被加载，加载当前分类的第一页数据
    this.loadResultData(this.type, this.currentPage);
  },
  watch: {
    //类型发生变化
    type(newVal, oldVal) {
      //清空容器
      this.resultData = [];
      this.currentPage = 1;
      //重新获取当前分类的第一页数据
      this.loadResultData(newVal, this.currentPage);
    },
  },
};
</script>

<style scoped>
.bottom-load-more-container {
  margin-top: 8px;
  display: flex;
  justify-content: center;
}

.preview-item {
  padding: 2px 8px 2px 8px;
}

.preview-item:hover {
  background-color: rgb(248, 248, 248);
}
</style>
