<template>
  <div>
    DEBUG: user_id={{ user_id }}, type={{ type }}
  </div>

  <div class="content-container">

    <div v-if="type==='answer'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <div style="font-weight: bold;font-size: large">{{ item.question_title }}</div>
        <div>{{ item.short_answer }}</div>
        <div style="display: flex;justify-content: space-between">
          <span><el-tag type="info">{{ item.question_category }}</el-tag></span>
          <span style="color: gray">{{ item.publish_time }}</span>
        </div>
      </div>
    </div>

    <div v-if="type==='question'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <div style="font-weight: bold;font-size: large">{{ item.title }}</div>
        <div>{{ item.short }}</div>
        <div style="display: flex;justify-content: space-between">
          <span><el-tag type="info">{{ item.category }}</el-tag></span>
          <span style="color: gray">{{ item.publish_time }}</span>
        </div>
      </div>
    </div>

    <div v-if="type==='article'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <div style="font-weight: bold;font-size: large">{{ item.title }}</div>
        <div style="display: flex;justify-content: space-between">
          <span><el-tag type="info">{{ item.category }}</el-tag></span>
          <span style="color: gray">{{ item.publish_time }}</span>
        </div>
      </div>
    </div>

    <div v-if="type==='reply'">
      <div v-for="item in resultData" :key="item.id" class="preview-item">
        <div style="font-weight: bold;font-size: large">{{ item.article_title }}</div>
        <div>{{ item.short_reply }}</div>
        <div style="display: flex;justify-content: space-between">
          <span><el-tag type="info">{{ item.article_category }}</el-tag></span>
          <span style="color: gray">{{ item.publish_time }}</span>
        </div>
      </div>
    </div>

  </div>

  <div class="bottom-load-more-container" v-if="showLoadMoreButton">
    <el-button ref="loadMoreButton" @click="onLoadMoreButtonClick">加载更多</el-button>
  </div>
</template>

<script>
export default {
  name: "ProfileDetail",
  props: ['user_id', 'type'],
  data() {
    return {
      showLoadMoreButton: true,
      currentPage: 1,
      resultData: [],
    }
  },
  methods: {
    onLoadMoreButtonClick() {
      this.currentPage++;
      this.loadResultData(this.type, this.currentPage);
    },
    loadAnswer(page) {
      return {
        "id": page,
        "question_title": "问题名称",
        "short_answer": "回答内容概要",
        "question_category": "分类",
        "publish_time": "2022-11-11 11:11:11"
      }
    },
    loadQuestion(page) {
      return {
        "id": page,
        "title": "问题名称",
        "short": "内容概要",
        "category": "分类",
        "publish_time": "2022-11-11 11:11:11"
      }
    },
    loadArticle(page) {
      return {
        "id": page,
        "title": "文章名称",
        "category": "分类",
        "publish_time": "2022-11-11 11:11:11"
      }
    },
    loadReply(page) {
      return {
        "id": page,
        "article_title": "文章名称",
        "short_reply": "回复概要",
        "article_category": "分类",
        "publish_time": "2022-11-11 11:11:11"
      }
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
        case "reply":
          this.resultData.push(this.loadReply(page));
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
    }
  }


}
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