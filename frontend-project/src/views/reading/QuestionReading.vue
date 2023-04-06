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

      <!--回答区        -->
      <el-card
        :body-style="{ padding: '0px 0px 8px 0px' }"
        class="answer-area"
        style="margin-top: 16px"
      >
        <template #header>
          <span style="font-weight: bold">撰写回答</span>
        </template>
        <div class="reply-input">
          <mavon-editor :toolbars="toolbars"></mavon-editor>
        </div>
        <el-button style="margin-top: 8px; margin-left: 16px">
          发布回答
        </el-button>
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
          class="order-select"
          id="order-select"
          style="margin-top: 16px; margin-bottom: 16px"
        >
          <el-select v-model="answerOrder" @change="onOrderChange()">
            <el-option value="time_desc" label="最新发布" />
            <el-option value="time_asc" label="最早发布" />
            <el-option value="app_desc" label="最多点赞" />
            <el-option value="app_asc" label="最少点赞" />
          </el-select>
        </div>
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
          <el-button @click="loadMoreAnswer()"> 加载更多 </el-button>
        </div>
        <div v-else>
          <el-divider>已经看到底了！</el-divider>
        </div>
      </div>
    </div>
  </el-main>
</template>

<script>
import { getUserAvatarUrl, getUserInfo } from "@/api/user";
import { marked } from "marked";
import { ArrowRight } from "@element-plus/icons-vue";
import { ElNotification } from "element-plus";
import {
  getQuestionAnswerAmount,
  getQuestionAnswerByApproval,
  getQuestionAnswerByTime,
  getQuestionInfo,
  getQuestionText,
} from "@/api/reading";
import QuestionAnswerListItem from "@/views/reading/components/QuestionAnswerListItem.vue";

export default {
  name: "QuestionReading",
  computed: {
    renderedMarkdown() {
      return marked(this.text);
    },
  },
  components: { QuestionAnswerListItem, ArrowRight },
  props: ["question_id"],
  data() {
    return {
      //id在props里，以下是文章实体的属性
      title: "标题",
      text: "正文",
      category: "类别",
      publisherId: 1,
      publishTime: "2001-01-28 00:00:00",
      //用户实体属性，用于展示作者
      publisherNickname: "哈哈",
      publisherAvatar: "default",
      //回答内容
      answerListData: [],
      answerCurrentPage: 1,
      answerTotalAmount: 0,
      answerOrder: "time_desc",
      //mavon设置
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: false, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: false, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: false, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: false, // 上一步
        redo: false, // 下一步
        trash: false, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: false, // 导航目录
        /* 2.1.8 */
        alignleft: false, // 左对齐
        aligncenter: false, // 居中
        alignright: false, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
    };
  }, //end of data
  methods: {
    getUserAvatarUrl,
    onOrderChange() {
      this.loadFirstPageAnswer();
    },
    loadMoreAnswer() {
      this.answerCurrentPage++;
      if (this.answerOrder.startsWith("time")) {
        getQuestionAnswerByTime(
          this.question_id,
          this.answerCurrentPage,
          this.answerOrder.endsWith("asc")
        ).then((resp) => {
          if (resp.data.data.length <= 0) this.answerCurrentPage = -1;
          this.answerListData = this.answerListData.concat(resp.data.data);
        });
      } else if (this.answerOrder.startsWith("app")) {
        getQuestionAnswerByApproval(
          this.question_id,
          this.answerCurrentPage,
          this.answerOrder.endsWith("asc")
        ).then((resp) => {
          if (resp.data.data.length <= 0) this.answerCurrentPage = -1;
          this.answerListData = this.answerListData.concat(resp.data.data);
        });
      }
    },
    loadFirstPageAnswer() {
      //加载第一页回答
      //清空
      this.answerCurrentPage = 0;
      this.answerListData = [];
      //加载
      this.loadMoreAnswer();
    },
  }, //end of methods
  mounted() {
    //问题的基本信息
    getQuestionInfo(this.question_id).then((resp) => {
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
      this.category = resp.data.data.category;
      this.publisherId = resp.data.data.publisherId;
      this.publishTime = resp.data.data.publishTime;
      //用户信息
      getUserInfo(this.publisherId).then((resp2) => {
        this.publisherNickname = resp2.data.data.nickname;
        this.publisherAvatar = resp2.data.data.avatar;
      });
    });
    //问题的正文
    getQuestionText(this.question_id).then((resp) => {
      this.text = resp.data.data;
    });
    //回答数量
    getQuestionAnswerAmount(this.question_id).then((resp) => {
      this.answerTotalAmount = resp.data.data;
    });
    //加载第一页回答
    this.loadFirstPageAnswer();
  }, // end of mounted()
};
</script>

<style scoped></style>
