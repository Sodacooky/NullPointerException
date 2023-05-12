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
          <div class="publisher-info" @click="jumpUserDetailPage()">
            <a style="font-weight: bold">{{ item.publisherNickname }}</a>
          </div>
          <div style="color: gray">发布于：{{ item.publishTime }}</div>
        </el-col>
        <el-col :span="4">
          <el-tooltip content="点赞问题">
            <el-button
              size="large"
              style="font-size: large; font-weight: bold; float: right"
              @click="onApproveBtn()"
              :type="isApproved ? 'primary' : 'default'"
            >
              <el-icon style="margin-right: 4px">
                <CaretTop />
              </el-icon>
              {{ item.approvalAmount }}
            </el-button>
          </el-tooltip>
        </el-col>
      </el-row>
    </div>

    <!--正文      -->
    <div class="main-text" style="padding: 0 4px 0 4px">
      <div v-highlight class="md" v-html="renderedMarkdown"></div>
    </div>

    <!--举报功能          -->
    <div style="display: flex; justify-content: right; color: gray">
      <a @click="openReportDialog()">举报</a>
    </div>

    <!--举报回答弹窗-->
    <el-dialog v-model="isShowAnswerReport">
      <template #header>
        <span style="font-weight: bold"> 举报该回答 </span>
      </template>
      <el-input
        v-model="reportComment"
        :autosize="{ minRows: 2, maxRows: 4 }"
        placeholder="请输入举报附加信息（必填）"
        type="textarea"
      />
      <template #footer>
        <el-button type="primary" @click="doReportAnswer()">
          确认举报
        </el-button>
        <el-button @click="isShowAnswerReport = false">取消</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import { UserApi } from "@/api/user";
import { CaretTop } from "@element-plus/icons-vue";
import { marked } from "marked";
import { ReportApi } from "@/api/report";
import { AuthApi } from "@/api/auth";
import { ReadingApi } from "@/api/reading";

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
  data() {
    return {
      isShowAnswerReport: false,
      reportComment: "",
      isApproved: false,
      hasLogin: false,
    };
  }, //data
  methods: {
    //打开举报回答对话框，判断用户是否登录以及是否举报过
    openReportDialog() {
      //判断登录，这里使用载入页面时获取的登录状态
      if (this.hasLogin === false) {
        this.$notify.warning({ title: "请先登录" });
        return;
      }
      //判断是否已经举报过
      ReportApi.isReported(this.item.id, "REPORT_ANSWER").then((resp) => {
        if (Boolean(resp.data.data)) {
          this.$notify.warning({ title: "你已经举报过该内容了" });
        } else {
          this.isShowAnswerReport = true;
        }
      });
    },
    //举报回答
    doReportAnswer() {
      if (this.reportComment.length < 1) {
        this.$notify.error({ title: "附加信息不能为空" });
        return;
      }
      ReportApi.reportAnswer(this.item.id, this.reportComment).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success({ title: "举报完成" });
          this.isShowAnswerReport = false;
          this.reportComment = "";
        } else {
          this.$notify.error({
            title: "举报失败",
            message: resp.data.message,
          });
        }
      });
    },
    onApproveBtn() {
      if (this.hasLogin) {
        if (this.isApproved) {
          ReadingApi.doAnswerUnApprove(this.item.id).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("取消点赞回答成功");
              this.item.approvalAmount--;
              this.isApproved = false;
            } else {
              this.$notify.error("取消点赞回答失败");
            }
          });
        } else {
          ReadingApi.doAnswerApprove(this.item.id).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("点赞回答成功");
              this.item.approvalAmount++;
              this.isApproved = true;
            } else {
              this.$notify.error("点赞回答失败");
            }
          });
        }
      } else {
        this.$notify.warning("尚未登录");
      }
    },
    jumpUserDetailPage() {
      let routeData = this.$router.resolve(
        `/profile?userId=${this.item.publisherId}`
      );
      window.open(routeData.href, "_blank");
    },
  }, //methods
  mounted() {
    //判断登录状态
    AuthApi.hasLogin().then((resp) => {
      this.hasLogin = resp.data.data;
      if (this.hasLogin === true) {
        ReadingApi.getAnswerIsApproved(this.item.id).then((resp) => {
          this.isApproved = resp.data.data;
        });
      }
    });
  },
};
</script>

<style scoped></style>
