<template>
  <!--  主体展示文章内容-->
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
        </div>
        <!--第二行，日期和作者        -->
        <div class="publisher-date">
          <el-row>
            <el-col :span="2">
              <el-avatar :src="getUserAvatarUrl(publisherAvatar)" />
            </el-col>
            <el-col :span="22">
              <div class="publisher-info">
                <a style="font-weight: bold">{{ publisherNickname }}</a>
              </div>
              <div style="color: gray">发布于：{{ publishTime }}</div></el-col
            >
          </el-row>
        </div>
        <!--正文          -->
        <div
          class="text"
          style="margin-top: 16px"
          v-highlight
          v-html="renderedMarkdown"
        ></div>
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
        <div class="reply-input">
          <el-input
            :autosize="{ minRows: 3, maxRows: 6 }"
            type="textarea"
          ></el-input>
        </div>
        <el-button style="margin-top: 16px">回复</el-button>
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
    </div>
  </el-main>
</template>

<script>
import { marked } from "marked";
import ArticleReplyListItem from "@/views/reading/components/ArticleReplyListItem.vue";
import { getArticle, getArticleReply } from "@/api/reading";
import { getUserAvatarUrl, getUserInfo } from "@/api/user";
import { ElNotification } from "element-plus";

export default {
  name: "ArticleReading",
  components: { ArticleReplyListItem },
  props: ["article_id"],
  data() {
    return {
      //id在props里，以下是文章实体的属性
      title: "",
      text: "",
      category: "",
      publisherId: 0,
      publishTime: "",
      //用户实体属性，用于展示作者
      publisherNickname: "",
      publisherAvatar: undefined,
      //回复内容
      replyListData: [],
      replyCurrentPage: 1,
    };
  }, //end of data
  methods: {
    getUserAvatarUrl,
    loadArticleContent() {
      //调用接口，加载文章内容，并加载文章对应的用户信息
      getArticle(this.article_id).then((resp) => {
        if (resp.data.code !== 0) {
          ElNotification({
            title: "加载失败",
            message: "无法加载文章，正在跳转回首页",
            type: "error",
          });
          this.$router.replace("/home");
          return;
        }
        this.title = resp.data.data.title;
        this.text = resp.data.data.text;
        this.category = resp.data.data.category;
        this.publisherId = resp.data.data.publisherId;
        this.publishTime = resp.data.data.publishTime;
        getUserInfo(this.publisherId).then((resp2) => {
          this.publisherNickname = resp2.data.data.nickname;
          this.publisherAvatar = resp2.data.data.avatar;
        });
      });
    },
    loadMoreReplies() {
      getArticleReply(this.article_id, this.replyCurrentPage).then((resp) => {
        if (resp.data.data === undefined || resp.data.data.length <= 0) {
          this.replyCurrentPage = -1;
        } else {
          this.replyListData = this.replyListData.concat(resp.data.data);
          this.replyCurrentPage++;
        }
      });
    },
  },
  mounted() {
    this.loadArticleContent();
    this.loadMoreReplies();
  },
  computed: {
    renderedMarkdown() {
      return marked(this.text);
    },
  },
};
</script>

<style scoped></style>
