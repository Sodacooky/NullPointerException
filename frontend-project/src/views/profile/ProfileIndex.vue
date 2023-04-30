<template>
  <el-main style="display: flex; justify-content: center">
    <div class="page-container" style="width: 50pc; min-width: 600px">
      <!--      顶部的信息展示框-->
      <UserInformationCard :user-info="userInfo" />

      <!--类型选择器      -->
      <div class="list-type-selector" style="margin-top: 16px">
        <el-radio-group size="large" v-model="type" @change="onTypeChange()">
          <el-radio-button label="问题" />
          <el-radio-button label="回答" />
          <el-radio-button label="文章" />
        </el-radio-group>
      </div>

      <!--      展示列表-->
      <div class="result-list" style="margin-top: 16px">
        <!--根据类型-->
        <div class="question-list" v-if="type === '问题'">
          <QuestionPreviewItem
            v-for="item in listData"
            :key="item.id"
            :item="item"
          />
        </div>
        <div class="answer-list" v-if="type === '回答'">
          <AnswerPreviewItem
            v-for="item in listData"
            :key="item.id"
            :item="item"
          />
        </div>
        <div class="article-list" v-if="type === '文章'">
          <ArticlePreviewItem
            v-for="item in listData"
            :key="item.id"
            :item="item"
          />
        </div>
        <!--在家更多按钮          -->
        <div
          class="load-more"
          style="margin-top: 16px; display: flex; justify-content: center"
        >
          <el-button v-if="page !== -1" @click="uniFetch()">加载更多</el-button>
          <el-divider v-if="page === -1">已无更多</el-divider>
        </div>
      </div>
    </div>
  </el-main>
</template>

<script>
import UserInformationCard from "@/views/profile/components/UserInformationCard.vue";
import { UserApi } from "@/api/user";
import { ElNotification } from "element-plus";
import QuestionPreviewItem from "@/components/QuestionPreviewItem.vue";
import AnswerPreviewItem from "@/views/profile/components/AnswerPreviewItem.vue";
import ArticlePreviewItem from "@/components/ArticlePreviewItem.vue";

export default {
  name: "ProfileIndex",
  components: {
    ArticlePreviewItem,
    AnswerPreviewItem,
    QuestionPreviewItem,
    UserInformationCard,
  },
  data() {
    return {
      userId: -1,
      userInfo: {},
      type: "问题",
      listData: [],
      page: 0,
    };
  },
  methods: {
    onTypeChange() {
      //列表类型改变，清空数据重新加载
      this.newFetch();
    },
    newFetch() {
      //清空数据
      this.page = 1;
      this.listData = [];
      this.uniFetch();
    },
    uniFetch() {
      switch (this.type) {
        case "问题":
          UserApi.getUserQuestion(this.userId, this.page).then((resp) => {
            if (resp.data.data === null || resp.data.data.length <= 0) {
              this.page = -1;
            } else {
              this.listData = this.listData.concat(resp.data.data);
              this.page++;
            }
          });
          break;
        case "回答":
          UserApi.getUserAnswer(this.userId, this.page).then((resp) => {
            if (resp.data.data === null || resp.data.data.length <= 0) {
              this.page = -1;
            } else {
              this.listData = this.listData.concat(resp.data.data);
              this.page++;
            }
          });
          break;
        case "文章":
          UserApi.getUserArticle(this.userId, this.page).then((resp) => {
            if (resp.data.data === null || resp.data.data.length <= 0) {
              this.page = -1;
            } else {
              this.listData = this.listData.concat(resp.data.data);
              this.page++;
            }
          });
          break;
      }
    },
  }, //end of methods
  mounted() {
    //从query中获取数据
    this.userId = this.$route.query.userId; //userid无论有没有我们都拿，undefined就表示自己
    //填充个人信息
    if (this.userId === undefined) {
      //如果没有指定用户，那么获取自己的信息
      UserApi.getCurrentUser().then((resp) => {
        //如果没有登录，会飞到登录页面
        this.userInfo = resp.data.data;
        this.userId = this.userInfo.id;
        //获取数据
        this.newFetch();
      });
    } else {
      //否则获取指定用户的信息
      UserApi.getUserInfo(this.userId).then((resp) => {
        if (resp.data.code !== "0") {
          ElNotification({
            titleInput: "提示",
            message: "找不到用户，跳转回首页",
            type: "error",
          });
          this.$router.replace("/");
        } else {
          this.userInfo = resp.data.data;
          //获取数据
          this.newFetch();
        }
      });
    }
  }, //end of mounted
};
</script>

<style scoped></style>
