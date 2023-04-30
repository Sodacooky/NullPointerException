<template>
  <el-main style="display: flex; justify-content: center">
    <div class="page-container" style="min-width: 600px; width: 60pc">
      <!--顶部搜索框，给你再次搜索的机会      -->
      <div
        class="search-input-area"
        style="display: flex; justify-content: center"
      >
        <el-input
          v-model="keyword"
          placeholder="搜索问题、文章或用户"
          style="font-size: larger; margin-bottom: 16px"
          @keydown.enter="onNewSearch()"
        >
          <template #prepend>
            <el-icon>
              <Search />
            </el-icon>
          </template>
          <template #append>
            <el-button @click="onNewSearch()">搜索</el-button>
          </template>
        </el-input>
      </div>

      <!--搜索条件选择栏      -->
      <div
        class="type-selector"
        style="display: flex; justify-content: space-between"
      >
        <!--搜索类型选择器      -->
        <span>
          <el-radio-group size="large" v-model="type" @change="onTypeChange()">
            <el-radio-button label="问题" />
            <el-radio-button label="文章" />
            <el-radio-button label="用户" />
          </el-radio-group>
        </span>
        <!--搜索顺序选择器-->
        <span>
          <el-select size="large" v-model="order" @change="onOrderChange()">
            <el-option
              v-for="item in nowAvailableOrderList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </span>
      </div>

      <!--结果区      -->
      <div class="result-area" style="margin-top: 8px">
        <!--数据列表        -->
        <div class="result-list">
          <!--如果搜索的是问题-->
          <div class="question-list" v-if="type === '问题'">
            <QuestionPreviewItem
              v-for="item in searchResult"
              :key="item.id"
              :item="item"
            />
          </div>
          <div class="article-list" v-if="type === '文章'">
            <ArticlePreviewItem
              v-for="item in searchResult"
              :key="item.id"
              :item="item"
            />
          </div>
          <div class="user-list" v-if="type === '用户'">
            <el-row>
              <UserListItem
                v-for="item in searchResult"
                :key="item.id"
                :item="item"
              />
            </el-row>
          </div>
        </div>

        <!--如果没有数据则显示        -->
        <div v-if="searchResult.length <= 0" class="empty-tips">
          <el-empty description="没有搜索到东西"></el-empty>
        </div>

        <!--底部翻页        -->
        <div
          class="pager-bottom"
          style="padding: 8px 8px 8px 0; display: flex; align-items: center"
        >
          <el-button-group>
            <el-button
              type="default"
              :icon="ArrowLeft"
              @click="onPrevPageBtnClick()"
              :disabled="page <= 1"
            >
              上一页
            </el-button>
            <el-button type="default" @click="onNextPageBtnClick()">
              下一页
              <el-icon class="el-icon--right"><ArrowRight /> </el-icon>
            </el-button>
          </el-button-group>
          <span style="margin-left: 8px; max-width: 160px">
            <el-input v-model="page" type="number">
              <template #prepend>当前页</template>
            </el-input>
          </span>
        </div>
      </div>
    </div>
  </el-main>
</template>

<script>
import { ArrowLeft, ArrowRight, Search } from "@element-plus/icons-vue";
import { SearchApi } from "@/api/search";
import QuestionPreviewItem from "@/components/QuestionPreviewItem.vue";
import { ElNotification } from "element-plus";
import ArticlePreviewItem from "@/components/ArticlePreviewItem.vue";
import UserListItem from "@/views/search/component/UserListItem.vue";

export default {
  name: "Index",
  components: {
    UserListItem,
    ArticlePreviewItem,
    ArrowRight,
    QuestionPreviewItem,
    Search,
  },

  data() {
    return {
      //和query中参数相对应的各个搜索参数
      keyword: "",
      type: "问题",
      order: "time_desc",
      page: 1,
      //搜索结果
      searchResult: [],
      //不同搜索类型的排序方式由不同，用于生成选择框
      questionOrderList: [
        { label: "最新发布", value: "time_desc" },
        { label: "最早发布", value: "time_asc" },
        { label: "最多回答", value: "ans_desc" },
        { label: "最少回答", value: "ans_asc" },
        { label: "最多订阅", value: "sub_desc" },
        { label: "最少订阅", value: "sub_asc" },
      ],
      articleOrderList: [
        { label: "最新发布", value: "time_desc" },
        { label: "最早发布", value: "time_asc" },
        { label: "最多点赞", value: "app_desc" },
        { label: "最少点赞", value: "app_asc" },
        { label: "最多回复", value: "rep_desc" },
        { label: "最少回复", value: "rep_asc" },
      ],
      userOrderList: [
        { label: "最新注册", value: "time_desc" },
        { label: "最早注册", value: "time_asc" },
      ],
    };
  }, //end of data

  computed: {
    ArrowLeft() {
      return ArrowLeft;
    },
    nowAvailableOrderList() {
      switch (this.type) {
        case "问题":
          return this.questionOrderList;
        case "文章":
          return this.articleOrderList;
        case "用户":
          return this.userOrderList;
      }
    },
  }, //end of computed

  mounted() {
    //获取query中的参数
    if (this.$route.query.keyword) this.keyword = this.$route.query.keyword;
    if (this.$route.query.type) this.type = this.$route.query.type;
    if (this.$route.query.order) this.order = this.$route.query.order;
    if (this.$route.query.page) this.page = this.$route.query.page;
    //发送请求获取内容
    this.executeSearchApi();
  }, //end of mounted

  methods: {
    onPrevPageBtnClick() {
      //上一页按钮触发
      this.page--;
      this.onNewSearch();
    },
    onNextPageBtnClick() {
      //下一页按钮触发
      this.page++;
      this.onNewSearch();
    },
    onTypeChange() {
      //更改了搜索类型
      this.page = 1;
      switch (this.type) {
        case "问题":
          this.order = this.questionOrderList[0].value;
          break;
        case "文章":
          this.order = this.articleOrderList[0].value;
          break;
        case "用户":
          this.order = this.userOrderList[0].value;
          break;
      }
      this.onNewSearch();
    },
    onOrderChange() {
      //更改了搜索结果顺序
      this.page = 1;
      this.onNewSearch();
    },
    onNewSearch() {
      //根据目前已有的参数，进行一次新的查询，跳转到新的查询页面
      this.$router.replace({
        path: "/search",
        query: {
          keyword: this.keyword,
          type: this.type,
          order: this.order,
          page: this.page,
        },
      });
    },

    //以下是搜索API相关
    executeSearchApi() {
      //使用this里面的属性值，调用对应的api，并将结果储存到this.searchResult
      switch (this.type) {
        case "问题":
          this.executeQuestionSearchApi();
          break;
        case "文章":
          this.executeArticleSearchApi();
          break;
        case "用户":
          this.executeUserSearchApi();
          break;
      }
    },
    executeQuestionSearchApi() {
      //根据order和page调用api，下面的两个方法同
      let isAsc = this.order.endsWith("asc");
      if (this.order.startsWith("time")) {
        SearchApi.searchQuestionByTime(this.keyword, this.page, isAsc).then(
          (resp) => this.processSearchResult(resp)
        );
      } else if (this.order.startsWith("ans")) {
        SearchApi.searchQuestionByAnsAmount(
          this.keyword,
          this.page,
          isAsc
        ).then((resp) => {
          this.processSearchResult(resp);
        });
      } else if (this.order.startsWith("sub")) {
        SearchApi.searchQuestionBySubAmount(
          this.keyword,
          this.page,
          isAsc
        ).then((resp) => this.processSearchResult(resp));
      }
    },
    executeArticleSearchApi() {
      let isAsc = this.order.endsWith("asc");
      if (this.order.startsWith("time")) {
        SearchApi.searchArticleByTime(this.keyword, this.page, isAsc).then(
          (resp) => this.processSearchResult(resp)
        );
      } else if (this.order.startsWith("app")) {
        SearchApi.searchArticleByApproval(this.keyword, this.page, isAsc).then(
          (resp) => {
            this.processSearchResult(resp);
          }
        );
      } else if (this.order.startsWith("rep")) {
        SearchApi.searchArticleByReplyAmount(
          this.keyword,
          this.page,
          isAsc
        ).then((resp) => this.processSearchResult(resp));
      }
    },
    executeUserSearchApi() {
      SearchApi.searchUserByRegisterTime(
        this.keyword,
        this.page,
        this.order.endsWith("asc")
      ).then((resp) => this.processSearchResult(resp));
    },
    processSearchResult(resp) {
      //处理搜索的结果
      //如果搜索有内容，那么正常装载
      if (resp.data.data.length > 0) this.searchResult = resp.data.data;
      else if (this.page > 1) {
        //如果搜索没有内容，且当前不是第一页，返回第一页
        this.page = 1;
        this.onNewSearch();
        ElNotification({
          titleInput: "提示",
          message: "已无更多页，跳转回第一页",
          type: "warning",
        });
      }
    },
  }, //end of methods
};
</script>

<style scoped></style>
