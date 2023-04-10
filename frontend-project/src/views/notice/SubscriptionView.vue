<template>
  <div v-for="item in noticeData" :key="item.id" class="notice-list">
    <div class="notice-card" style="padding: 2px 2px 0 2px">
      <div class="article-name" style="font-size: large">
        回复：{{ item.question_title }}
      </div>
      <div class="reply-short" style="color: gray">
        {{ item.answer_publisher_nickname }}: {{ item.text_short }}
      </div>
      <div class="bottom-info" style="text-align: right">
        {{ item.publish_time }}
      </div>
      <el-divider />
    </div>
  </div>
  <div style="display: flex; justify-content: center">
    <el-button>加载更多</el-button>
  </div>
</template>

<script>
export default {
  name: "SubscriptionView",
  data() {
    return {
      noticeData: [],
    };
  },
  mounted() {
    this.noticeData.push({
      id: "1",
      question_title: "问题标题",
      answer_publisher_nickname: "回复发布者",
      text_short: "回复正文概要",
      publish_time: "2022-2-22 22:22:22",
    });
    this.noticeData.push({
      id: "1",
      question_title: "问题标题",
      answer_publisher_nickname: "回复发布者2",
      text_short: "回复正文概要22222222222222..........",
      publish_time: "2022-2-22 22:22:22",
    });
  },
};
</script>

<style scoped>
.notice-card:hover {
  background-color: rgb(248, 248, 248);
}
</style>
