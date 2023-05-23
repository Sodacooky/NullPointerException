<template>
  <el-main style="display: flex; justify-content: center">
    <!--整页内容归该元素下，控制大小-->
    <div class="page-container" style="width: 65%; min-width: 600px">
      <!--问题信息和问题正文        -->
      <el-card :body-style="{ padding: '16px' }" class="container-card">
        <!--第一行，标题分类-->
        <div class="title-category">
          <!--标题          -->
          <span style="font-size: larger; font-weight: bold">{{ title }}</span>
          <!--分类-->
          <span style="font-size: large; margin-left: 8px">
            <el-tag type="info">{{ category }}</el-tag>
          </span>
          <!--订阅数量与订阅按钮            -->
          <span style="float: right">
            <el-tooltip content="订阅问题" placement="bottom">
              <el-button
                style="font-size: large; font-weight: bold"
                @click="onSubscribeBtn()"
                :type="isSubscribed ? 'primary' : 'default'"
              >
                <el-icon style="margin-right: 4px"><Star /></el-icon>
                {{ subscriptionAmount }}
              </el-button>
            </el-tooltip>
          </span>
        </div>
        <!--第二行，日期和作者        -->
        <div class="publisher-date">
          <el-row>
            <el-col :span="2">
              <el-avatar :src="UserApi.getUserAvatarUrl(publisherAvatar)" />
            </el-col>
            <el-col :span="22">
              <div class="publisher-info" @click="jumpUserDetailPage()">
                <a style="font-weight: bold">{{ publisherNickname }}</a>
              </div>
              <div style="color: gray">发布于：{{ publishTime }}</div>
            </el-col>
          </el-row>
        </div>
        <!--正文          -->
        <div
          v-highlight
          class="text"
          style="margin-top: 16px"
          v-html="renderedMarkdown"
        ></div>
        <!--举报功能          -->
        <div style="display: flex; justify-content: right; color: gray">
          <a @click="openReportQuestionDialog()">举报</a>
        </div>
      </el-card>

      <!--回答编写-->
      <el-card
        :body-style="{ padding: '0px' }"
        class="reply-input"
        style="margin-top: 16px"
      >
        <template #header>
          <span style="font-weight: bold">撰写回答</span>
        </template>
        <!--登录后才显示功能          -->
        <div v-if="hasLogin" class="reply-area">
          <!--编辑框          -->
          <div class="reply-input">
            <mavon-editor v-model="answerInput" :toolbars="mavonToolbars" />
          </div>
          <!--          确认发布-->
          <div style="padding: 16px">
            <el-button v-if="isAgreeLaw" @click="doPublishAnswer()">
              发布回答
            </el-button>
            <el-button v-else disabled>发布回答(需同意社区规范)</el-button>
            <el-checkbox
              v-model="isAgreeLaw"
              label="我已确认发布的内容符合社区规范"
              style="margin-left: 16px"
            />
          </div>
        </div>
        <div v-else class="reply-area-no-login" style="padding: 16px">
          登录后可用，
          <router-link to="/login">点击登录</router-link>
        </div>
      </el-card>

      <!--如果没有数据则显示        -->
      <div v-if="answerListData.length <= 0" class="empty-tips">
        <el-empty description="还没有人回答。。。要不要考虑成为第一个回答者？">
        </el-empty>
      </div>

      <!--回答列表        -->
      <div v-if="answerListData.length > 0" class="answer-list">
        <!--回答列表顺序选择        -->
        <div
          id="order-select"
          class="order-select"
          style="margin-top: 16px; margin-bottom: 16px"
        >
          <el-select v-model="answerOrder" @change="onOrderChange()">
            <el-option label="最新发布" value="time_desc" />
            <el-option label="最早发布" value="time_asc" />
            <el-option label="最多点赞" value="app_desc" />
            <el-option label="最少点赞" value="app_asc" />
          </el-select>
          <span style="font-size: small; color: gray; margin-left: 32px">
            总共回答数量：{{ answerAmount }}
          </span>
        </div>

        <!--内容          -->
        <QuestionAnswerListItem
          v-for="item in answerListData"
          :key="item.id"
          :item="item"
        />

        <!--加载更多按钮-->
        <div
          v-if="answerCurrentPage !== -1"
          style="display: flex; justify-content: center; margin-top: 16px"
        >
          <el-button @click="loadMoreAnswer()"> 加载更多</el-button>
        </div>
        <div v-else>
          <el-divider>已经看到底了！</el-divider>
        </div>
      </div>

      <!--举报问题弹窗        -->
      <el-dialog v-model="isShowQuestionReport">
        <template #header>
          <span style="font-weight: bold"> 举报当前问题 </span>
        </template>
        <el-input
          v-model="reportComment"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="请输入举报附加信息（必填）"
          type="textarea"
        />
        <template #footer>
          <el-button type="primary" @click="doReportQuestion()">
            确认举报
          </el-button>
          <el-button @click="isShowQuestionReport = false">取消</el-button>
        </template>
      </el-dialog>

      <!-- page-container -->
    </div>
  </el-main>
</template>

<script>
import { UserApi } from "@/api/user";
import { marked } from "marked";
import { ArrowRight } from "@element-plus/icons-vue";
import { ReadingApi } from "@/api/reading";
import QuestionAnswerListItem from "@/views/reading/components/QuestionAnswerListItem.vue";
import { mavonSettings } from "@/mavonSettings";
import { AuthApi } from "@/api/auth";
import { PublishingApi } from "@/api/publishing";
import { ReportApi } from "@/api/report";

export default {
  name: "QuestionReading",
  computed: {
    UserApi() {
      return UserApi;
    },
    mavonToolbars() {
      return mavonSettings;
    },
    renderedMarkdown() {
      return marked(this.text);
    },
  },
  components: { QuestionAnswerListItem, ArrowRight },
  props: ["questionId"],
  data() {
    return {
      //id在props里，以下是文章实体的属性
      title: "标题",
      text: "正文",
      category: "类别",
      publisherId: 1,
      publishTime: "2001-01-28 00:00:00",
      //问题相关数据
      subscriptionAmount: 0,
      isSubscribed: false,
      answerAmount: 0,
      //用户实体属性，用于展示作者
      publisherNickname: "哈哈",
      publisherAvatar: "default",
      //回答内容
      answerListData: [],
      answerCurrentPage: 1,
      answerOrder: "time_desc",
      //用户回答区域
      isAgreeLaw: false,
      answerInput: "",
      hasLogin: false,
      //举报相关
      isShowQuestionReport: false,
      reportComment: "",
    };
  }, //end of data
  methods: {
    //改变了答案的排序顺序
    onOrderChange() {
      this.loadFirstPageAnswer();
    },
    //加载更多答案，根据当前顺序和当前页码加载
    loadMoreAnswer() {
      if (this.answerCurrentPage < 0) return;
      if (this.answerOrder.startsWith("time")) {
        ReadingApi.getQuestionAnswerByTime(
          this.questionId,
          this.answerCurrentPage,
          this.answerOrder.endsWith("asc")
        ).then((resp) => {
          if (resp.data.data.length <= 0) this.answerCurrentPage = -1;
          else {
            this.answerListData = this.answerListData.concat(resp.data.data);
            this.answerCurrentPage++;
          }
        });
      } else if (this.answerOrder.startsWith("app")) {
        ReadingApi.getQuestionAnswerByApproval(
          this.questionId,
          this.answerCurrentPage,
          this.answerOrder.endsWith("asc")
        ).then((resp) => {
          if (resp.data.data.length <= 0) this.answerCurrentPage = -1;
          else {
            this.answerListData = this.answerListData.concat(resp.data.data);
            this.answerCurrentPage++;
          }
        });
      }
    },
    //加载第一页回答
    loadFirstPageAnswer() {
      //清空
      this.answerCurrentPage = 1;
      this.answerListData = [];
      //加载
      this.loadMoreAnswer();
    },
    //发布回答
    doPublishAnswer() {
      //调用发送接口
      PublishingApi.publishQuestionAnswer(
        this.questionId,
        this.answerInput
      ).then((resp) => {
        if (resp.data.code === "0") {
          //success
          // - notice
          this.$notify({ title: "发布回答成功", type: "success" });
          // - reset reply area
          this.answerInput = "";
          this.isAgreeLaw = false;
          // - reload replies
          this.answerListData = [];
          this.answerCurrentPage = 1;
          this.answerOrder = "time_desc";
          this.loadMoreAnswer();
          ReadingApi.getQuestionAnswerAmount(this.questionId).then((resp) => {
            this.answerAmount = resp.data.data;
          });
        } else {
          //fail
          this.$notify({
            title: "发布回答失败",
            message: resp.data.message,
            type: "error",
          });
        }
      });
    },
    //打开举报问题对话框，判断用户是否登录以及是否举报过
    openReportQuestionDialog() {
      //判断登录，这里使用载入页面时获取的登录状态
      if (this.hasLogin === false) {
        this.$notify.warning({ title: "请先登录" });
        return;
      }
      //判断是否已经举报过
      ReportApi.isReported(this.questionId, "REPORT_QUESTION").then((resp) => {
        if (Boolean(resp.data.data)) {
          this.$notify.warning({ title: "你已经举报过该内容了" });
        } else {
          this.isShowQuestionReport = true;
        }
      });
    },
    //举报问题
    doReportQuestion() {
      if (this.reportComment.length < 1) {
        this.$notify.error({ title: "附加信息不能为空" });
        return;
      }
      ReportApi.reportQuestion(this.questionId, this.reportComment).then(
        (resp) => {
          if (resp.data.code === "0") {
            this.$notify.success({ title: "举报完成" });
            this.isShowQuestionReport = false;
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
    onSubscribeBtn() {
      console.log(this.isSubscribed);
      if (this.hasLogin) {
        if (this.isSubscribed) {
          ReadingApi.doQuestionUnSubscribe(this.questionId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("取消订阅问题成功");
              ReadingApi.getQuestionSubscriptionAmount(this.articleId).then(
                (resp) => {
                  this.subscriptionAmount = resp.data.data;
                }
              );
              ReadingApi.getQuestionIsSubscribed(this.questionId).then(
                (resp) => {
                  this.isSubscribed = resp.data.data;
                }
              );
            } else {
              this.$notify.error("取消订阅问题失败");
            }
          });
        } else {
          ReadingApi.doQuestionSubscribe(this.questionId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("订阅问题成功");
              ReadingApi.getQuestionSubscriptionAmount(this.questionId).then(
                (resp) => {
                  this.subscriptionAmount = resp.data.data;
                }
              );
              ReadingApi.getQuestionIsSubscribed(this.questionId).then(
                (resp) => {
                  this.isSubscribed = resp.data.data;
                }
              );
            } else {
              this.$notify.error("订阅问题失败");
            }
          });
        }
      } else {
        this.$notify.warning("尚未登录");
      }
    },
    jumpUserDetailPage() {
      let routeData = this.$router.resolve(
        `/profile?userId=${this.publisherId}`
      );
      window.open(routeData.href, "_blank");
    },
  }, //end of methods
  mounted() {
    //问题的基本信息
    ReadingApi.getQuestionInfo(this.questionId).then((resp) => {
      if (resp.data.code !== "0") {
        this.$notify({
          title: "加载失败",
          message: "无法加载文章，正在跳转回首页",
          type: "error",
        });
        this.$router.replace("/home");
        return;
      }
      this.title = resp.data.data.title;
      this.category = resp.data.data.category;
      this.publisherId = resp.data.data.publisherId;
      this.publishTime = resp.data.data.publishTime;
      //用户信息
      UserApi.getUserInfo(this.publisherId).then((resp2) => {
        this.publisherNickname = resp2.data.data.nickname;
        this.publisherAvatar = resp2.data.data.avatar;
      });
    });
    //问题的正文
    ReadingApi.getQuestionText(this.questionId).then((resp) => {
      this.text = resp.data.data;
    });
    //回答数量
    ReadingApi.getQuestionAnswerAmount(this.questionId).then((resp) => {
      this.answerAmount = resp.data.data;
    });
    //订阅数量
    ReadingApi.getQuestionSubscriptionAmount(this.questionId).then((resp) => {
      this.subscriptionAmount = resp.data.data;
    });
    //加载第一页回答
    this.loadFirstPageAnswer();
    //判断登录状态
    AuthApi.hasLogin().then((resp) => {
      this.hasLogin = resp.data.data;
      if (this.hasLogin === true) {
        ReadingApi.getQuestionIsSubscribed(this.questionId).then((resp) => {
          this.isSubscribed = resp.data.data;
        });
      }
    });
  }, // end of mounted()
};
</script>

<style scoped></style>
