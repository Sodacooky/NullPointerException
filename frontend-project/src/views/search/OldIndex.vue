<template>
  <!--  用户页面框架-->
  <el-container>
    <el-main>
      <!--  搜索框-->
      <div
        class="search-input-area"
        style="display: flex; justify-content: center"
      >
        <el-input
          v-model="searchInputText"
          placeholder="搜索问题、文章和用户"
          style="font-size: large; margin-bottom: 16px"
          @keydown.enter="onSearchRequest()"
        >
          <template #prepend>
            <el-icon>
              <Search />
            </el-icon>
          </template>
          <template #append>
            <el-button @click="onSearchRequest()">搜索</el-button>
          </template>
        </el-input>
      </div>

      <!--  结果列表-->
      <div v-if="searchText.length > 0" class="search-result-area">
        <!--      切换结果类型按钮-->
        <div>
          <div style="margin-top: 8px">
            <el-button-group>
              <el-button
                :type="searchType === 'question' ? 'primary' : ''"
                text
                @click="onTypeButtonClick('question')"
              >
                问题
              </el-button>
              <el-button
                :type="searchType === 'article' ? 'primary' : ''"
                text
                @click="onTypeButtonClick('article')"
              >
                文章
              </el-button>
              <el-button
                :type="searchType === 'user' ? 'primary' : ''"
                text
                @click="onTypeButtonClick('user')"
              >
                用户
              </el-button>
            </el-button-group>
          </div>

          <!--  问题结果-->
          <div v-if="searchType === 'question'">
            <el-select v-model="questionOrder" @change="onOrderChange()">
              <el-option label="最新发表" value="time_desc"></el-option>
              <el-option label="最早发表" value="time_asc"></el-option>
              <el-option label="最多回答" value="answer_desc"></el-option>
              <el-option label="最少回答" value="answer_asc"></el-option>
              <el-option label="最多订阅" value="sub_desc"></el-option>
              <el-option label="最少订阅" value="sub_asc"></el-option>
            </el-select>
            <!--    问题列表-->
            <div v-for="item in searchResultData" :key="item.id" class="list">
              <QuestionListItem :item="item" />
            </div>
            <!--加载更多按钮-->
            <div
              v-if="searchCurrentPage !== -1"
              style="display: flex; justify-content: center; margin-top: 16px"
            >
              <el-button type="primary" @click="onLoadMoreButtonClick()"
                >加载更多
              </el-button>
            </div>
            <div v-else>
              <el-divider>已经看到底了！</el-divider>
            </div>
          </div>

          <!--  文章结果-->
          <div v-if="searchType === 'article'">
            <!--    文章列表-->
            <div v-for="item in searchResultData" :key="item.id" class="list">
              <ArticleListItem :item="item" />
            </div>
            <!--加载更多按钮-->
            <div
              v-if="searchCurrentPage !== -1"
              style="display: flex; justify-content: center; margin-top: 16px"
            >
              <el-button type="primary" @click="onLoadMoreButtonClick()"
                >加载更多
              </el-button>
            </div>
            <div v-else>
              <el-divider>已经看到底了！</el-divider>
            </div>
          </div>

          <!--  用户结果-->
          <div v-if="searchType === 'user'">
            <div class="list">
              <el-row>
                <el-col
                  v-for="item in searchResultData"
                  :key="item.id"
                  :span="12"
                  class="item-card"
                >
                  <el-row>
                    <el-col :span="4">
                      <el-image :src="item.avatar_url" fit="fill" />
                    </el-col>
                    <el-col :span="20">
                      <div>
                        <div class="left"></div>
                        <div class="right" style="padding-left: 8px">
                          <div style="font-weight: bold">
                            {{ item.nickname }}
                          </div>
                          <div style="color: gray">{{ item.description }}</div>
                          <div style="color: gray">
                            注册时间: {{ item.register_time }}
                          </div>
                        </div>
                      </div>
                    </el-col>
                  </el-row>
                  <el-divider />
                </el-col>
              </el-row>
            </div>

            <!--加载更多按钮-->
            <div
              v-if="searchCurrentPage !== -1"
              style="display: flex; justify-content: center; margin-top: 16px"
            >
              <el-button type="primary" @click="onLoadMoreButtonClick"
                >加载更多
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!--      如果没有搜索-->
      <el-empty v-if="searchText.length <= 0" description="未输入搜索内容" />

      <!--  没有数据都是展示这个-->
      <el-empty
        v-if="searchResultData.length <= 0 && searchText.length > 0"
        description="没有结果..."
      />
    </el-main>
  </el-container>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import QuestionListItem from "@/components/QuestionListItem.vue";
import ArticleListItem from "@/components/ArticleListItem.vue";
import moment from "moment/moment";
import { searchQuestionByTime } from "@/api/search";

export default {
  name: "OldSearchIndex",
  components: { ArticleListItem, QuestionListItem, Search },
  props: ["text", "type"],
  data() {
    return {
      searchInputText: "", //输入框文本
      searchText: "", //当前真搜索文本，结果对应的文本
      searchType: "",
      searchQueryTime: null, //搜索发起时间，由第一次搜索请求响应中携带(应该改成当前时间
      searchCurrentPage: -1, //当前搜索的页面，为-1不展示加载更多按钮
      searchResultData: [], //结果
      //搜索的顺序
      articleOrder: "",
      questionOrder: "time_desc",
      userOrder: "",
    };
  },
  methods: {
    //问题搜索
    searchQuestion(keyword, page) {
      switch (this.questionOrder) {
        case "time_desc":
          searchQuestionByTime(keyword, page, false).then((resp) => {
            if (resp.data.data.length <= 0) {
              this.searchCurrentPage = -1;
            } else {
              this.searchResultData = this.searchResultData.concat(
                resp.data.data
              );
              this.searchCurrentPage = page;
            }
          });
          break;
        case "time_asc":
          searchQuestionByTime(keyword, page, true).then((resp) => {
            if (resp.data.data.length <= 0) {
              this.searchCurrentPage = -1;
            } else {
              this.searchResultData = this.searchResultData.concat(
                resp.data.data
              );
              this.searchCurrentPage = page;
            }
          });
          break;
      }
    },
    //搜索指定页的内容，返回结果列表
    search() {
      switch (this.searchType) {
        case "question":
          this.searchQuestion(this.searchText, this.searchCurrentPage);
          break;
        //...
      }
    },
    //清空容器，然后搜索第一页的内容，并储存
    searchFirstPage() {
      //清空容器和页码记录
      this.searchResultData = [];
      this.searchCurrentPage = 1;
      this.searchQueryTime = moment().format("yyyy-MM-DD HH:mm:ss");
      //搜索
      this.search();
    },

    //搜索框搜索按钮被按下
    onSearchRequest() {
      //更改当前搜索文本
      this.searchText = this.searchInputText;
      //访问API加载第一页
      this.searchFirstPage(this.searchText, this.searchType);
    },
    //搜索类型切换按钮被按下
    onTypeButtonClick(type) {
      //更改类型
      this.searchType = type;
      //访问API加载第一页
      this.searchFirstPage(this.searchText, this.searchType);
    },
    //更改了排序的类型
    onOrderChange() {
      //重新搜索
      this.searchFirstPage(this.searchType, this.searchType);
    },
    //最新问题的获取更多按钮
    onLoadMoreButtonClick() {
      this.search(this.searchText, this.searchType, this.searchCurrentPage + 1);
    },
  },
  mounted() {
    this.searchText = JSON.parse(JSON.stringify(this.text));
    this.searchInputText = JSON.parse(JSON.stringify(this.searchText));
    this.searchType = JSON.parse(JSON.stringify(this.type));
    this.searchFirstPage(this.searchText, this.searchType);
  },
};
</script>

<style scoped>
.item-card {
  padding: 8px 8px 1px 8px;
}

.item-card:hover {
  background-color: rgb(248, 248, 248);
}
</style>
