<template>
  <div
    class="item-card"
    style="padding: 8px 8px 24px 8px"
    @click="$router.push(`/question/${item.questionId}`)"
  >
    <!--                回答数据-->
    <div class="question-info">
      <!--                    标题、分类、发布时间-->
      <div class="title-and-time">
        <span class="title" style="font-weight: bold">
          在 {{ item.questionTitle }} 中回答
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
      <!--            点赞数量 -->
      <span
        class="interact-info"
        style="display: flex; align-items: center; float: right"
      >
        <span style="padding-right: 8px">
          <el-icon><CaretTop /></el-icon>{{ item.approvalAmount }}
        </span>
      </span>
    </div>
  </div>
</template>

<script>
import { CaretTop } from "@element-plus/icons-vue";
import { getUserAvatarUrl } from "@/api/user";

export default {
  name: "AnswerPreviewItem",
  props: ["item"],
  methods: { getUserAvatarUrl },
  components: { CaretTop },
};
</script>

<style scoped></style>
