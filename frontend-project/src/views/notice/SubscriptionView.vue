<template>
  <!--空状态    -->
  <el-empty v-if="noticeData.length < 1" description="没有消息"></el-empty>

  <!--列表-->
  <div v-for="item in noticeData" :key="item.id" class="notice-list">
    <div
      class="notice-card"
      style="padding: 8px"
      @mouseenter="onHoverNotice(item.id)"
      @click="$router.push(`/question/${item.supplement}`)"
    >
      <!--标题        -->
      <div class="article-name" style="font-size: large">
        <el-tag v-if="item.isRead === 0" type="info">未读</el-tag>
        {{ item.title }}
      </div>
      <div class="reply-short" style="color: gray">
        {{ item.text }}
      </div>
      <div
        class="bottom-info"
        style="font-size: small; font-style: italic; color: gray"
      >
        {{ item.time }}
      </div>
    </div>
  </div>
</template>

<script>
import { NoticeApi } from "@/api/notice";

export default {
  name: "SubscriptionView",
  data() {
    return {
      noticeData: [],
    };
  },
  methods: {
    onHoverNotice(noticeId) {
      //发送标记已读请求
      NoticeApi.read(noticeId).then((resp) => {
        if (resp.data.code == 0) {
          //成功则也将本地的标记为已读
          let found = this.noticeData.find((value) => {
            return value.id === noticeId;
          });
          found.isRead = 1;
        }
      });
    },
  }, //methods
  mounted() {
    NoticeApi.getQuestionAnswer().then((resp) => {
      this.noticeData = resp.data.data;
    });
  },
};
</script>

<style scoped>
.notice-card:hover {
  background-color: rgb(248, 248, 248);
}
</style>
