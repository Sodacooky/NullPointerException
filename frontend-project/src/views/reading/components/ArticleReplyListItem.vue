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
      <a @click="openReportDialog()">举报</a>
    </div>
    <!--举报回答弹窗-->
    <el-dialog v-model="isShowReportDialog">
      <template #header>
        <span style="font-weight: bold"> 举报该回复 </span>
      </template>
      <el-input
        type="textarea"
        v-model="reportComment"
        :autosize="{ minRows: 2, maxRows: 4 }"
        placeholder="请输入举报附加信息（必填）"
      />
      <template #footer>
        <el-button type="primary" @click="doReport()"> 确认举报 </el-button>
        <el-button @click="isShowReportDialog = false">取消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { baseUrl } from "@/api/requests";
import { UserApi } from "@/api/user";
import { ReportApi } from "@/api/report";

export default {
  name: "ArticleReplyListItem",
  computed: {
    UserApi() {
      return UserApi;
    },
  },
  data() {
    return {
      isShowReportDialog: false,
      reportComment: "",
    };
  },
  methods: {
    baseUrl() {
      return baseUrl;
    },
    //打开举报对话框，判断用户是否登录以及是否举报过
    openReportDialog() {
      //判断登录，这里使用载入页面时获取的登录状态
      if (this.hasLogin === false) {
        this.$notify.warning({ title: "请先登录" });
        return;
      }
      //判断是否已经举报过
      ReportApi.isReported(this.item.id, "REPORT_ARTICLE_REPLY").then(
        (resp) => {
          if (Boolean(resp.data.data)) {
            this.$notify.warning({ title: "你已经举报过该内容了" });
          } else {
            this.isShowReportDialog = true;
          }
        }
      );
    },
    //举报回复
    doReport() {
      if (this.reportComment.length < 1) {
        this.$notify.error({ title: "附加信息不能为空" });
        return;
      }
      ReportApi.reportArticleReply(this.item.id, this.reportComment).then(
        (resp) => {
          if (resp.data.code === "0") {
            this.$notify.success({ title: "举报完成" });
            this.isShowReportDialog = false;
            this.reportComment = "";
          } else {
            this.$notify.error({
              title: "举报失败",
              message: resp.data.message,
            });
          }
        }
      );
    },
  },
  props: ["item"],
};
</script>

<style scoped></style>
