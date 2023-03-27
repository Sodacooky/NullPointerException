<template>
  <!--  主体展示文章内容-->
  <el-container>
    <el-main>

      <el-card class="container-card" :body-style="{padding: '16px'}">
        <!--      标题，作者，分类-->
        <span style="font-size: larger;font-weight: bold;">{{ title }}</span>
        <span style="font-size: large;margin-left: 8px;">
          <el-tag type="info">{{ category }}</el-tag>
        </span>
        <div style="color: gray">发布于：{{ publish_time }}</div>
        <div class="publisher-info">
          <span class="avatar">
            <el-avatar :src="publisherAvatarUrl"/>
          </span>
          <span style="margin-left: 8px">{{ publisherNickname }}</span>
        </div>
        <!--      正文-->
        <div class="text" v-html="text"></div>
      </el-card>

      <!--      回答列表-->
      <div class="answer-list" v-for="item in replyListData" :key="item.id">
        <el-card class="container-card" :body-style="{padding: '16px'}">
          <!--          回答者信息-->
          <template #header>
            <span class="publisher-info">
              <span> <el-avatar :src="item.publisherAvatarUrl" size="small"/></span>
              <span style="margin-left: 4px">{{ item.publisherNickname }}</span>
            </span>

          </template>
          <!--          回答正文内容-->
          <div class="reply-text" style="padding: 2px 4px 2px 4px">
            {{ item.text }}
          </div>
          <!--          回复按钮和发布日期-->
          <div class="operation-and-info" style="color: gray;margin-top: 4px">
            <span><el-button>点赞 114514</el-button></span>
            <span>{{ item.publishTime }}</span>
          </div>
        </el-card>
      </div>


      <!--      评论编写-->
      <el-card class="container-card" :body-style="{padding: '0px 0px 8px 0px'}">
        <template #header>
          <span style="font-weight: bold">撰写回答</span>
        </template>
        <div class="reply-input">
          <mavon-editor></mavon-editor>
        </div>
        <el-button style="margin-top: 16px;margin-left: 16px">发送回答</el-button>
      </el-card>


    </el-main>

    <el-aside width="20%" style="margin-top: 32px;padding: 8px">
      <el-card :body-style="{padding: '4px'}">
        <template #header>
          问题操作
        </template>
        <div>
          <el-button style="width: 100%">订阅</el-button>
        </div>
        <div>
          <el-button style="width: 100%;margin-top: 4px">举报</el-button>
        </div>
      </el-card>
    </el-aside>

  </el-container>
</template>

<script>
export default {
  name: "QuestionReading",
  props: ["article_id"],
  data() {
    return {
      //文章信息
      title: "文章标题",
      category: "分类名称",
      publish_time: "2022-1-1 10:10:11",
      text: "<h1><a id=\"Hello_0\"></a>Hello</h1>\n" +
          "<pre><div class=\"hljs\"><code class=\"lang-shell\">pro a\n" +
          "</code></div></pre>\n" +
          "<ul>\n" +
          "<li>a</li>\n" +
          "<li>b</li>\n" +
          "</ul>",
      //文章发布者信息
      publisherId: "114514",
      publisherNickname: "田所浩二",
      publisherAvatarUrl: "https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png",
      //回复相关
      replyInput: "",
      replyListData: [{
        "id": 1,
        "publisherNickname": "田所浩二2",
        "publisherAvatarUrl": "https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png",
        "text": "好活",
        "publishTime": "2022-1-1 12:00:00",
      }],
    }
  },
}
</script>

<style scoped>

.publisher-info {
  display: flex;
  align-items: center;
  margin-top: 4px;
  padding: 0;
  background-color: transparent;
}

.publisher-info:hover {
  background-image: linear-gradient(to right,
  rgba(225, 225, 225, 1),
  rgba(225, 225, 225, 0.1) 50%,
  rgba(225, 225, 225, 0));
  transition: background-image 0.5s ease;
}

.container-card {
  margin-top: 16px;
}

.operation-and-info {
  display: flex;
  justify-content: space-between;
}


</style>s