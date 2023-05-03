<template>
  <el-card :body-style="{ padding: '16px' }">
    <el-row>
      <!--          头像-->
      <el-col :span="3">
        <el-image fit="fill" :src="UserApi.getUserAvatarUrl(userInfo.avatar)" />
      </el-col>
      <!--          详细信息-->
      <el-col :span="20">
        <div class="user-info" style="padding-left: 16px">
          <!--            大大的昵称和小小的ID-->
          <div class="nickname-id">
            <span style="font-weight: bold; font-size: larger">
              {{ userInfo.nickname }}
            </span>
            <span style="color: gray; font-size: small">
              ID: {{ userInfo.id }}
            </span>
            <span style="float: right; color: gray">
              <a @click="openReportDialog()">举报</a>
            </span>
          </div>
          <!--            普通的自我介绍-->
          <div class="description">
            {{ userInfo.description }}
          </div>
          <!--            注册时间-->
          <div class="register-time" style="color: gray">
            <div>注册时间：{{ userInfo.registerTime }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </el-card>

  <!--举报回答弹窗-->
  <el-dialog v-model="isShowReportDialog">
    <template #header>
      <span style="font-weight: bold"> 举报该用户 </span>
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
</template>

<script>
import { UserApi } from "@/api/user";
import { ReportApi } from "@/api/report";

export default {
  name: "UserInformationCard",
  props: ["userInfo"],
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
    //打开举报对话框，判断用户是否登录以及是否举报过
    openReportDialog() {
      //判断登录，这里使用载入页面时获取的登录状态
      if (this.hasLogin === false) {
        this.$notify.warning({ title: "请先登录" });
        return;
      }
      //判断是否已经举报过
      ReportApi.isReported(this.userInfo.id, "REPORT_USER").then((resp) => {
        if (Boolean(resp.data.data)) {
          this.$notify.warning({ title: "你已经举报过该用户了" });
        } else {
          this.isShowReportDialog = true;
        }
      });
    },
    //举报回复
    doReport() {
      if (this.reportComment.length < 1) {
        this.$notify.error({ title: "附加信息不能为空" });
        return;
      }
      ReportApi.reportUser(this.userInfo.id, this.reportComment).then(
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
};
</script>

<style scoped></style>
