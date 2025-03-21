<template>
  <el-main style="display: flex; justify-content: center">
    <!--整页内容归该元素下，控制大小-->
    <div class="page-container" style="width: 65%; min-width: 600px">
      <el-card :body-style="{ padding: '16px' }" class="container-card">
        <!--第一行，标题分类-->
        <div class="title-category">
          <!--标题          -->
          <span style="font-size: larger; font-weight: bold">{{ title }}</span>
          <!--分类-->
          <span style="font-size: large; margin-left: 8px">
            <el-tag type="info">{{ category }}</el-tag>
          </span>
          <!--点赞数量与点赞按钮            -->
          <span style="float: right">
            <el-tooltip content="点赞文章" placement="bottom">
              <el-button
                style="font-size: large; font-weight: bold"
                :type="isApproved ? 'primary' : 'default'"
                @click="onApproveBtn()"
              >
                <el-icon style="margin-right: 4px"><CaretTop /></el-icon>
                {{ approvalAmount }}
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
          <a @click="openReportDialog()">举报</a>
        </div>
      </el-card>

      <!--      评论编写-->
      <el-card
        :body-style="{ padding: '16px' }"
        class="reply-input"
        style="margin-top: 16px"
      >
        <template #header>
          <span style="font-weight: bold">撰写回复</span>
        </template>
        <!--登录后才显示的评论功能          -->
        <div v-if="hasLogin" class="reply-area">
          <!--评论编辑框          -->
          <div class="reply-input">
            <el-input
              v-model="replyInput"
              :autosize="{ minRows: 3, maxRows: 6 }"
              type="textarea"
            ></el-input>
          </div>
          <!--          确认发布-->
          <div style="margin-top: 16px">
            <el-button v-if="isAgreeLaw" @click="doPublishReply()"
              >回复
            </el-button>
            <el-button v-else disabled>回复(需同意社区规范)</el-button>
            <el-checkbox
              v-model="isAgreeLaw"
              label="我已确认发布的内容符合社区规范"
              style="margin-left: 16px"
            />
          </div>
        </div>
        <div v-else class="reply-area-no-login">
          登录后可用，
          <router-link to="/login">点击登录</router-link>
        </div>
      </el-card>

      <!--评论内容区-->
      <el-card
        :body-style="{ padding: '16px' }"
        class="reply-list"
        style="margin-top: 16px"
      >
        <template #header>
          <span style="font-weight: bold">文章回复</span>
        </template>
        <div
          v-for="item in replyListData"
          :key="item.id"
          class="reply-list"
          style="margin-bottom: 16px"
        >
          <ArticleReplyListItem :item="item" />
        </div>

        <div class="load-more" style="justify-content: center; display: flex">
          <el-button v-if="replyCurrentPage !== -1" @click="loadMoreReplies()">
            加载更多
          </el-button>
          <el-divider v-if="replyCurrentPage === -1">已无更多</el-divider>
        </div>

        <el-empty v-if="replyListData.length < 1" description="还没有回复" />
      </el-card>

      <!--举报弹窗        -->
      <el-dialog v-model="isShowReportDialog">
        <template #header>
          <span style="font-weight: bold"> 举报当前文章 </span>
        </template>
        <el-input
          v-model="reportComment"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="请输入举报附加信息（必填）"
          type="textarea"
        />
        <template #footer>
          <el-button type="primary" @click="doReport()"> 确认举报</el-button>
          <el-button @click="isShowReportDialog = false">取消</el-button>
        </template>
      </el-dialog>
    </div>
  </el-main>
</template>

<script>
import { marked } from "marked";
import ArticleReplyListItem from "@/views/reading/components/ArticleReplyListItem.vue";
import { ReadingApi } from "@/api/reading";
import { UserApi } from "@/api/user";
import { PublishingApi } from "@/api/publishing";
import { AuthApi } from "@/api/auth";
import { ReportApi } from "@/api/report";

export default {
  name: "ArticleReading",
  components: { ArticleReplyListItem },
  props: ["articleId"],
  data() {
    return {
      //id在props里，以下是文章实体的属性
      title: "",
      text: "",
      category: "",
      publisherId: 0,
      publishTime: "",
      //文章相关数据
      approvalAmount: 0,
      isApproved: false,
      //用户实体属性，用于展示作者
      publisherNickname: "",
      publisherAvatar: undefined,
      //回复内容
      replyListData: [],
      replyCurrentPage: 1,
      //用户回复区域
      isAgreeLaw: false,
      replyInput: "",
      hasLogin: false,
      //举报相关
      isShowReportDialog: false,
      reportComment: "",
    };
  }, //end of data
  methods: {
    //调用接口，加载文章内容，并加载文章对应的用户信息
    loadArticleContent() {
      ReadingApi.getArticle(this.articleId).then((resp) => {
        if (resp.data.code !== "0") {
          this.$notify({
            title: "加载失败",
            message: "无法加载文章，正在跳转回首页",
            type: "error",
          });
          this.$router.replace("/");
          return;
        }
        this.title = resp.data.data.title;
        this.text = resp.data.data.text;
        this.category = resp.data.data.category;
        this.publisherId = resp.data.data.publisherId;
        this.publishTime = resp.data.data.publishTime;
        UserApi.getUserInfo(this.publisherId).then((resp2) => {
          this.publisherNickname = resp2.data.data.nickname;
          this.publisherAvatar = resp2.data.data.avatar;
        });
      });
    },
    //文章回复加载，根据当前this.replyCurrentPage加载，并自动修改
    loadMoreReplies() {
      ReadingApi.getArticleReply(this.articleId, this.replyCurrentPage).then(
        (resp) => {
          if (resp.data.data === undefined || resp.data.data.length <= 0) {
            this.replyCurrentPage = -1;
          } else {
            this.replyListData = this.replyListData.concat(resp.data.data);
            this.replyCurrentPage++;
          }
        }
      );
    },
    //文章回复发布
    doPublishReply() {
      //调用发送接口
      PublishingApi.publishArticleReply(this.articleId, this.replyInput).then(
        (resp) => {
          if (resp.data.code === "0") {
            //success
            // - notice
            this.$notify({ title: "回复成功", type: "success" });
            // - reset reply area
            this.replyInput = "";
            this.isAgreeLaw = false;
            // - reload replies
            this.replyListData = [];
            this.replyCurrentPage = 1;
            this.loadMoreReplies();
          } else {
            //fail
            this.$notify({
              title: "回复失败",
              message: resp.data.message,
              type: "error",
            });
          }
        }
      );
    },
    //打开举报问题对话框，判断用户是否登录以及是否举报过
    openReportDialog() {
      //判断登录，这里使用载入页面时获取的登录状态
      if (this.hasLogin === false) {
        this.$notify.warning({ title: "请先登录" });
        return;
      }
      //判断是否已经举报过
      ReportApi.isReported(this.articleId, "REPORT_ARTICLE").then((resp) => {
        if (Boolean(resp.data.data)) {
          this.$notify.warning({ title: "你已经举报过该内容了" });
        } else {
          this.isShowReportDialog = true;
        }
      });
    },
    //举报问题
    doReport() {
      if (this.reportComment.length < 1) {
        this.$notify.error({ title: "附加信息不能为空" });
        return;
      }
      ReportApi.reportArticle(this.articleId, this.reportComment).then(
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
    //
    onApproveBtn() {
      if (this.hasLogin) {
        if (this.isApproved) {
          ReadingApi.doArticleUnApprove(this.articleId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("取消点赞文章成功");
              //加载点赞数量
              ReadingApi.getArticleApprovalAmount(this.articleId).then(
                (resp) => {
                  this.approvalAmount = resp.data.data;
                }
              );
              ReadingApi.getArticleIsApproved(this.articleId).then((resp) => {
                this.isApproved = resp.data.data;
              });
            } else {
              this.$notify.error("取消点赞文章失败");
            }
          });
        } else {
          ReadingApi.doArticleApprove(this.articleId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("点赞文章成功");
              //加载点赞数量
              ReadingApi.getArticleApprovalAmount(this.articleId).then(
                (resp) => {
                  this.approvalAmount = resp.data.data;
                }
              );
              ReadingApi.getArticleIsApproved(this.articleId).then((resp) => {
                this.isApproved = resp.data.data;
              });
            } else {
              this.$notify.error("点赞文章失败");
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
  }, // methods
  mounted() {
    //加载内容
    this.loadArticleContent();
    //加载回复
    this.loadMoreReplies();
    //加载点赞数量
    ReadingApi.getArticleApprovalAmount(this.articleId).then((resp) => {
      this.approvalAmount = resp.data.data;
    });
    //判断登录状态
    AuthApi.hasLogin().then((resp) => {
      this.hasLogin = resp.data.data;
      if (this.hasLogin === true) {
        ReadingApi.getArticleIsApproved(this.articleId).then((resp) => {
          this.isApproved = resp.data.data;
        });
      }
    });
  }, //mounted
  computed: {
    UserApi() {
      return UserApi;
    },
    renderedMarkdown() {
      return marked(this.text);
    },
  },
};
</script>

<style scoped></style>
