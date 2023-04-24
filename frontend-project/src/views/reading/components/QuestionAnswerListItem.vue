<template>
  <el-card
    :body-style="{ padding: '8px 8px 0 8px' }"
    shadow="hover"
    style="margin-bottom: 4px"
  >
    <div class="publisher-info">
      <el-row>
        <el-col :span="2">
          <el-avatar :src="UserApi.getUserAvatarUrl(item.publisherAvatar)" />
        </el-col>
        <el-col :span="18">
          <div class="publisher-info">
            <a style="font-weight: bold">{{ item.publisherNickname }}</a>
          </div>
          <div style="color: gray">发布于：{{ item.publishTime }}</div>
        </el-col>
        <el-col :span="4">
          <el-button
            size="large"
            style="font-size: large; font-weight: bold; float: right"
          >
            <el-icon style="margin-right: 4px">
              <CaretTop />
            </el-icon>
            {{ item.approvalAmount }}
          </el-button>
        </el-col>
      </el-row>
    </div>

    <!--正文      -->
    <div class="main-text" style="padding: 0 4px 0 4px">
      <div class="md" v-highlight v-html="renderedMarkdown"></div>
    </div>

    <!--举报功能          -->
    <div style="display: flex; justify-content: right; color: gray">
      <a>举报</a>
    </div>
  </el-card>
</template>

<script>
import { UserApi } from "@/api/user";
import { CaretTop } from "@element-plus/icons-vue";
import { marked } from "marked";

export default {
  name: "QuestionAnswerListItem",
  components: { CaretTop },

  props: ["item"],
  computed: {
    UserApi() {
      return UserApi;
    },
    renderedMarkdown() {
      return marked(this.item.text);
    },
  },
};
</script>

<style scoped></style>
