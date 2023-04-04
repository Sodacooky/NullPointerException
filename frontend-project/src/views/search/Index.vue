<template>
  <el-main>
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
    <div class="result-area">
      <!--顶部翻页控件        -->
      <div class="pager-top">
        <Pager
          @prevBtnClick="onPrevPageBtnClick()"
          @nextBtnClick="onNextPageBtnClick()"
          :current-page="page"
          :max-page="maxPage"
        />
      </div>

      <!--数据列表        -->
      <div class="result-list">
        <!--如果搜索的是问题-->
        <div class="question-list" v-for="item in searchResult" :key="item.id">
          <QuestionListItem :item="item" />
        </div>
      </div>

      <!--如果没有数据则显示        -->
      <div v-if="searchResult.length <= 0" class="empty-tips">
        <el-empty description="没有搜索到东西"></el-empty>
      </div>

      <!--底部翻页        -->
      <div class="pager-bottom">
        <Pager
          @prevBtnClick="onPrevPageBtnClick()"
          @nextBtnClick="onNextPageBtnClick()"
          :current-page="page"
          :max-page="maxPage"
        />
      </div>
    </div>
  </el-main>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import Pager from "@/views/search/component/Pager.vue";
import { searchQuestionByTime } from "@/api/search";
import QuestionListItem from "@/components/QuestionListItem.vue";

export default {
  name: "Index",
  components: { QuestionListItem, Pager, Search },
  data() {
    return {
      //和query中参数相对应的各个搜索参数
      keyword: "",
      type: "问题",
      order: "time_desc",
      page: 1,
      //最大页数需要通过api接口调用结果产生
      maxPage: null,
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
  },
  computed: {
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
  },
  mounted() {
    //获取query中的参数
    if (this.$route.query.keyword) this.keyword = this.$route.query.keyword;
    if (this.$route.query.type) this.type = this.$route.query.type;
    if (this.$route.query.order) this.order = this.$route.query.order;
    if (this.$route.query.page) this.page = this.$route.query.page;
    //发送请求获取内容
    this.executeSearchApi();
  },
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
      this.onNewSearch();
    },
    onOrderChange() {
      //更改了搜索结果顺序
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
          break;
        case "用户":
          break;
      }
    },
    executeQuestionSearchApi() {
      //根据order和page调用api，下面的两个方法同
      let isAsc = this.order.endsWith("asc");
      if (this.order.startsWith("time")) {
        searchQuestionByTime(this.keyword, this.page, isAsc).then((resp) =>
          this.processSearchResult(resp)
        );
      } else if (this.order.startsWith("ans")) {
      } else if (this.order.startsWith("sub")) {
      }
    },
    executeArticleSearchApi() {},
    executeUserSearchApi() {},
    processSearchResult(resp) {
      //处理搜索的结果
      //如果搜索有内容，那么正常装载
      if (resp.data.data.length > 0) this.searchResult = resp.data.data;
      else {
        //如果搜索没有内容，且当前不是第一页，返回第一页
        this.page = 1;
        this.onNewSearch();
      }
    },
  },
};
</script>

<style scoped></style>
