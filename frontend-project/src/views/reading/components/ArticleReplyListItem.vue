<template>
  <div class="reply">
    <!--信息-->
    <div
      class="publisher-info"
      style="display: flex; justify-content: space-between; align-items: center"
    >
      <!--用户头像和昵称        -->
      <span class="user-info">
        <span
          class="nickname-avatar"
          style="display: flex; align-items: center; font-weight: bold"
        >
          <el-avatar
            :src="UserApi.getUserAvatarUrl(item.publisherAvatar)"
            style="margin-right: 8px"
          />
          {{ item.publisherNickname }}
        </span>
      </span>
      <!--发布时间        -->
      <span class="date" style="color: gray">
        {{ item.publishTime }}
      </span>
    </div>
    <!--正文      -->
    <div class="reply-text" style="padding-left: 48px">
      {{ item.text }}
    </div>
    <!--举报功能          -->
    <div style="display: flex; justify-content: right; color: gray">
      <a>举报</a>
    </div>
  </div>
</template>

<script>
import { baseUrl } from "@/api/requests";
import { UserApi } from "@/api/user";

export default {
  name: "ArticleReplyListItem",
  computed: {
    UserApi() {
      return UserApi;
    },
  },
  methods: {
    baseUrl() {
      return baseUrl;
    },
  },
  props: ["item"],
};
</script>

<style scoped></style>
