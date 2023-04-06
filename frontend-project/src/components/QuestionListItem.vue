<template>
  <div
    class="item-card"
    style="padding: 8px 8px 24px 8px"
    @click="$router.push(`/question/${item.id}`)"
  >
    <!--                问题数据-->
    <div class="question-info">
      <!--                    标题、分类、发布时间-->
      <div class="title-and-time">
        <span class="title" style="font-weight: bold">
          <span
            v-if="isRank"
            :style="{ color: rankIndex <= 2 ? 'red' : 'black' }"
          >
            #{{ rankIndex + 1 }}
          </span>
          {{ item.title }}
        </span>
        <span class="category-tag" style="margin-left: 4px">
          <el-tag type="info">{{ item.category }}</el-tag>
        </span>
        <span class="publish-time" style="color: gray; float: right">
          {{ item.publishTime }}
        </span>
      </div>
      <!--                    摘要-->
      <div class="short-text" style="font-size: small; color: gray">
        {{ item.shortText }}
      </div>
    </div>
    <!--                底部信息-->
    <div class="bottom-bar" style="margin-top: 8px; padding-bottom: 8px">
      <!--            用户信息-->
      <span class="user-info">
        <span style="display: flex; align-items: center; float: left">
          <el-avatar
            :src="getUserAvatarUrl(item.publisherAvatar)"
            size="small"
            style="margin-right: 8px"
          />
          {{ item.publisherNickname }}
        </span>
      </span>
      <!--            回复数量与订阅数量 -->
      <span
        class="interact-info"
        style="display: flex; align-items: center; float: right"
      >
        <span style="padding-right: 8px">
          <el-icon><Comment /></el-icon>{{ item.answerAmount }}
        </span>
        <span style="padding-right: 8px">
          <el-icon><StarFilled /></el-icon>{{ item.subscriptionAmount }}
        </span>
      </span>
    </div>
  </div>
</template>

<script>
import {
  Comment,
  StarFilled,
  VideoCameraFilled,
} from "@element-plus/icons-vue";
import { baseUrl } from "@/api/requests";
import { getUserAvatarUrl } from "@/api/user";

export default {
  name: "QuestionListItem",
  methods: {
    getUserAvatarUrl,
    baseUrl() {
      return baseUrl;
    },
  },
  components: { VideoCameraFilled, Comment, StarFilled },
  props: ["item", "isRank", "rankIndex"],
};
</script>

<style scoped>
.item-card:hover {
  background-color: rgb(248, 248, 248);
}
</style>
