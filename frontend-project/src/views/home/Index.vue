<template>

  <!--  主体部分-->
    <el-container>
        <el-main>
            <!--          搜索框-->
            <el-input v-model="searchText"
                      placeholder="搜索问题、文章和用户"
                      style="font-size: large;margin-bottom: 16px"
                      @keydown.enter="doSearch">
                <template #prepend>
                    <el-icon>
                        <Search/>
                    </el-icon>
                </template>
                <template #append>
                    <el-button @click="doSearch">搜索
                    </el-button>
                </template>
            </el-input>
            <!--      首页内容切换-->
            <el-menu :default-active="activeListRoutePath" :router="true" mode="horizontal">
                <el-menu-item index="/home/question/latest">最新问题</el-menu-item>
                <el-menu-item index="/home/question/weekly">问题周榜</el-menu-item>
                <el-menu-item index="/home/question/monthly">问题月榜</el-menu-item>
                <el-menu-item index="/home/article/latest">最新文章</el-menu-item>
                <el-menu-item index="/home/article/weekly">文章周榜</el-menu-item>
                <el-menu-item index="/home/article/monthly">文章月榜</el-menu-item>
            </el-menu>
            <router-view></router-view>
        </el-main>
    </el-container>

  <!--        侧边栏-->
    <el-aside style="margin-right: 16px" width="20pc">
        <!--          公告-->
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>公告</span>
                </div>
            </template>
            <el-collapse v-model="activeAnnouncementName" accordion>
                <el-collapse-item name="1" title="公告1">
                    一行简单的公告
                </el-collapse-item>
                <el-collapse-item name="2" title="公告2">
                    <div>第一行</div>
                    <div>用DIV分割每一行</div>
                </el-collapse-item>
                <el-collapse-item name="3" title="公告3">
                    <span style="color: green">丰富的</span>
                    <span style="color: orange">span</span>
                </el-collapse-item>
            </el-collapse>
        </el-card>
        <!--          热门分类-->
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>近期热门分类</span>
                </div>
            </template>
            <el-badge :value="12" class="hot-category-item">
                <el-button>Java</el-button>
            </el-badge>
            <el-badge :value="114514" class="hot-category-item">
                <el-button>运维</el-button>
            </el-badge>
            <el-badge :value="1" class="hot-category-item">
                <el-button>C++</el-button>
            </el-badge>
            <el-badge :value="2" class="hot-category-item">
                <el-button>互联网产业发展</el-button>
            </el-badge>
        </el-card>
        <!--          广告-->
        <el-card :body-style="{ padding: '0px' }" class="box-card">
            <template #header>
                <div class="card-header">
                    <span>推广</span>
                </div>
            </template>
            <el-carousel height="150px">
                <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                    <h3>广告位招租1</h3>
                </el-carousel-item>
                <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                    <h3>广告位招租2</h3>
                </el-carousel-item>
                <el-carousel-item style="background-color: gray;text-align: center;vertical-align: center">
                    <h3>广告位招租3</h3>
                </el-carousel-item>
            </el-carousel>
        </el-card>
        <!--          网站数据-->
        <el-card :body-style="{ padding: '16px' }" class="box-card">
            <template #header>
                <div class="card-header">
                    <span>网站状况</span>
                </div>
            </template>
            <div>本日新增问题：114514</div>
            <div>本日新增文章：666</div>
            <div>本日登录用户：1245</div>
            <div>总注册用户数：666666</div>
        </el-card>
    </el-aside>
</template>

<script>
export default {
    name: "HomeIndex",
    data() {
        return {
            activeAnnouncementName: '-1',
            searchText: '',
            activeListRoutePath: '/home/question/latest',
        }
    },
    methods: {
        doSearch() {
            if (this.searchText.length <= 0) this.searchText = " ";
            this.$router.push(`/search/question/${this.searchText}`);
        },
    },
    mounted() {
        this.activeListRoutePath = this.$route.path;
    }
}
</script>

<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: bold;
}

.box-card {
    margin-top: 16px;
}

.hot-category-item {
    margin-right: 16px;
    margin-bottom: 8px;
}
</style>