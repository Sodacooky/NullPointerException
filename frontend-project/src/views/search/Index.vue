<template>
  <!--  用户页面框架-->
    <el-container>
        <el-main>

            <!--  搜索框-->
            <div class="search-input-area" style="display: flex;justify-content: center;">
                <el-input v-model="searchInputText"
                          placeholder="搜索问题、文章和用户"
                          style="font-size: large;margin-bottom: 16px"
                          @keydown.enter="doSearch">
                    <template #prepend>
                        <el-icon>
                            <Search/>
                        </el-icon>
                    </template>
                    <template #append>
                        <el-button @click="doSearch">搜索</el-button>
                    </template>
                </el-input>
            </div>


            <!--  结果列表-->
            <div v-if="searchText.length>0" class="search-result-area">
                <!--      切换结果类型按钮-->
                <div>
                    <div style="margin-top: 8px">
                        <el-button-group>
                            <el-button :type="searchType==='question'?'primary':''" text
                                       @click="onTypeButtonClick('question')">
                                问题
                            </el-button>
                            <el-button :type="searchType==='article'?'primary':''" text
                                       @click="onTypeButtonClick('article')">
                                文章
                            </el-button>
                            <el-button :type="searchType==='user'?'primary':''" text @click="onTypeButtonClick('user')">
                                用户
                            </el-button>
                        </el-button-group>
                    </div>


                    <!--  问题结果-->
                    <div v-if="searchType==='question'">
                        <!--    问题列表-->
                        <div v-for="item in searchResultData" :key="item.id" class="list">
                            <div class="preview-card">
                                <div class="title">{{ item.title }}</div>
                                <div class="text-short">{{ item.text_short }}</div>
                                <div class="bottom-bar">
                                    <span style="float: left"><el-tag type="info">{{ item.category }}</el-tag></span>
                                    <span style="float: right;color: gray">{{ item.publish_time }}</span>
                                </div>
                                <el-divider/>
                            </div>
                        </div>
                        <!--加载更多按钮-->
                        <div v-if="searchCurrentPage!==-1"
                             style="display: flex;justify-content: center;margin-top: 16px">
                            <el-button type="primary" @click="onLoadMoreButtonClick">加载更多</el-button>
                        </div>
                    </div>

                    <!--  文章结果-->
                    <div v-if="searchType==='article'">
                        <!--    文章列表-->
                        <div v-for="item in searchResultData" :key="item.id" class="list">
                            <div class="preview-card">
                                <div class="title">{{ item.title }}</div>
                                <div class="bottom-bar">
                                    <span style="float: left"><el-tag type="info">{{ item.category }}</el-tag></span>
                                    <span style="float: right;color: gray">{{ item.publish_time }}</span>
                                </div>
                                <el-divider/>
                            </div>
                        </div>
                        <!--加载更多按钮-->
                        <div v-if="searchCurrentPage!==-1"
                             style="display: flex;justify-content: center;margin-top: 16px">
                            <el-button type="primary" @click="onLoadMoreButtonClick">加载更多</el-button>
                        </div>
                    </div>

                    <!--  用户结果-->
                    <div v-if="searchType==='user'">
                        <div class="list">
                            <el-row>
                                <el-col v-for="item in searchResultData" :key="item.id" :span="12" class="preview-card">
                                    <el-row>
                                        <el-col :span="4">
                                            <el-image :src="item.avatar_url" fit="fill"/>
                                        </el-col>
                                        <el-col :span="20">
                                            <div>
                                                <div class="left">
                                                </div>
                                                <div class="right" style="padding-left: 8px">
                                                    <div style="font-weight: bold">{{ item.nickname }}</div>
                                                    <div style="color: gray">{{ item.description }}</div>
                                                    <div style="color: gray">注册时间: {{ item.register_time }}</div>
                                                </div>
                                            </div>
                                        </el-col>
                                    </el-row>
                                    <el-divider/>
                                </el-col>
                            </el-row>
                        </div>

                        <!--加载更多按钮-->
                        <div v-if="searchCurrentPage!==-1"
                             style="display: flex;justify-content: center;margin-top: 16px">
                            <el-button type="primary" @click="onLoadMoreButtonClick">加载更多</el-button>
                        </div>
                    </div>


                </div>
            </div>

            <!--      如果没有搜索-->
            <el-empty v-if="searchText.length<=0" description="未输入搜索内容"/>

            <!--  没有数据都是展示这个-->
            <el-empty v-if="searchResultData.length<=0 && searchText.length>0" description="没有结果..."/>


        </el-main>
    </el-container>


</template>

<script>

export default {
    name: "SearchIndex",
    props: ["text", "type"],
    data() {
        return {
            searchInputText: "", //输入框文本
            searchText: "", //当前真搜索文本，结果对应的文本
            searchType: "",
            searchQueryTime: null,//搜索发起时间，由第一次搜索请求响应中携带(应该改成当前时间
            searchCurrentPage: -1,//当前搜索的页面，为-1不展示加载更多按钮
            searchResultData: [],//结果
        }
    },
    methods: {
        //搜索框搜索按钮被按下
        doSearch() {
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
        //最新问题的获取更多按钮
        onLoadMoreButtonClick() {
            let result = this.searchForUniverse(this.searchText, this.searchType, this.searchCurrentPage + 1);
            if (result.length > 0) {
                this.searchCurrentPage++;
                this.searchResultData = this.searchResultData.concat(result);
            } else {
                this.searchCurrentPage = -1;//no more result
            }
        },
        //问题搜索请求
        searchForQuestion(text, page) {
            return [{
                "id": page,
                "title": "最新问题标题" + page,
                "text_short": "正文的部分概览" + text,
                "category": "分类",
                "publisher_name": "发布者",
                "publish_time": "2022-1-1 00:00:00"
            }];
        },
        //文章搜索请求
        searchForArticle(text, page) {
            return [{
                "id": page,
                "title": "最新文章标题" + page + text,
                "category": "分类",
                "publisher_name": "发布者",
                "publish_time": "2022-1-1 00:00:00"
            }];
        },
        //用户搜索请求
        searchForUser(text, page) {
            return [{
                "id": page,
                "nickname": "用户" + page,
                "description": "正文的部分概览" + text,
                "avatar_url": "https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png",
                "register_time": "2022-1-1 00:00:00"
            }];
        },
        //搜索指定页的内容，返回结果列表
        searchForUniverse(text, type, page) {
            let result = null;
            switch (type) {
                case "question":
                    result = this.searchForQuestion(text, page);
                    break;
                case "article":
                    result = this.searchForArticle(text, page);
                    break;
                case "user":
                    result = this.searchForUser(text, page);
                    break;
            }
            return result;
        },
        //清空容器，然后搜索第一页的内容，并储存
        searchFirstPage(text, type) {
            //清空容器和页码记录
            this.searchResultData = [];
            this.searchCurrentPage = -1;
            //搜索
            let result = this.searchForUniverse(text, type, 1);
            if (result.length > 0) {
                this.searchCurrentPage = 1;
                this.searchResultData = this.searchResultData.concat(result);
            }
        },
    },
    mounted() {
        this.searchText = JSON.parse(JSON.stringify(this.text));
        this.searchInputText = JSON.parse(JSON.stringify(this.searchText));
        this.searchType = JSON.parse(JSON.stringify(this.type));
        this.searchFirstPage(this.searchText, this.searchType);
    },

}
</script>

<style scoped>
.preview-card {
    padding: 8px 8px 1px 8px;
}

.preview-card:hover {
    background-color: rgb(248, 248, 248);
}

.preview-card .title {
    font-weight: bold;
}

.preview-card .text-short {
    font-size: small;
    color: gray;
}

.preview-card .bottom-bar {
    margin-top: 2px;
    height: 10px;
}
</style>