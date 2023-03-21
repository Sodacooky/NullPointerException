<template>
  <el-container>
    <el-main>
      <!--      顶部的信息展示框-->
      <el-card :body-style="{ padding: '8px' }">
        <el-row>
          <!--          头像-->
          <el-col :span="3">
            <el-image fit="fill" src="https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png"/>
          </el-col>
          <el-col :span="1"></el-col>
          <!--          详细信息-->
          <el-col :span="20">
            <!--            大大的昵称-->
            <div style="font-weight: bold;font-size: larger">Sodacooky</div>
            <!--            普通的自我介绍-->
            <div>
              这是一段自我介绍，他介绍了我自己，所以是一段自我介绍，正因为他是一段自我介绍，所以写的应该是关于我的东西。
            </div>
            <!--            灰色的注册时间和用户ID-->
            <div style="color: rgb(96,96,96)">
              <div>注册时间：2022.1.1 00:00:15</div>
              <div>用户ID：{{ user_id }}</div>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <!--      展示回答的问题、发布的问题、发布的文章、发表的文章回复-->
      <div>
        <div style="margin-top: 8px">
          <el-button-group>
            <el-button
                text
                @click="onTypeSwitchButtonClick('answer')"
                :type="nowActiveTypeName==='answer'?'primary':''">
              回答
            </el-button>
            <el-button
                text
                @click="onTypeSwitchButtonClick('question')"
                :type="nowActiveTypeName==='question'?'primary':''">
              提问
            </el-button>
            <el-button
                text
                @click="onTypeSwitchButtonClick('article')"
                :type="nowActiveTypeName==='article'?'primary':''">
              文章
            </el-button>
            <el-button
                text
                @click="onTypeSwitchButtonClick('reply')"
                :type="nowActiveTypeName==='reply'?'primary':''">
              文章回复
            </el-button>
          </el-button-group>
        </div>
        <div style="margin-top: 8px">
          <ProfileDetail :user_id="user_id" :type="nowActiveTypeName"/>
        </div>
      </div>
    </el-main>
  </el-container>

</template>

<script>
import ProfileDetail from "@/views/profile/components/ProfileDetail.vue";

export default {
  name: "ProfileIndex",
  components: {ProfileDetail},
  data() {
    return {
      user_id: "self",
      nowActiveTypeName: "answer",
    }
  },
  methods: {
    onTypeSwitchButtonClick(toSwitchTypeName) {
      //判断是否当前页
      if (toSwitchTypeName !== this.nowActiveTypeName) {
        //更新传给列表的参数
        this.nowActiveTypeName = toSwitchTypeName;
      }
    }
  },
  mounted() {
    if (this.$route.query.user_id !== undefined) {
      this.user_id = this.$route.query.user_id; //否则使用默认值self
    }
    if (this.$route.query.type !== undefined) {
      this.nowActiveTypeName = this.$route.query.type; //否则使用默认值answer
    }
  }
}
</script>

<style scoped>

</style>