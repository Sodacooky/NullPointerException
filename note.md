# 端口记录

| 端口   | 说明              |
|:-----|:----------------|
| 8080 | 网关，外部访问的入口      |
| 8081 | Question模块      |
| 8082 | Article模块       |
| 8083 | User模块          | 
| 8084 | Miscellaneous模块 |
| 8085 | Authc模块         |

# 站内消息类型

| type            | 描述          |
|:----------------|:------------|
| question_answer | 问题收到回答      |
| answer_reply    | 回答收到的回复     |
| article_reply   | 文章收到的回复     |
| system          | 来自系统、管理员的通知 |

# API

/public下的表示不需要登录

/auth下的表示需要登录

认证相关接口放在public下

管理员所有接口都在/admin下，包括/public/admin /auth/admin

## service-user

* /public 信息获取 DateFetchController
    * /getUserInfo 获取用户信息
    * /avatar/{filename} 获取用户头像图片
* /public 认证相关 AuthController **部分接口需要token未提供时返回400错误**
    * /login 登入
    * /logout 登出
    * /hasLogin 是否登入
    * /register 注册
    * /registerCodeImage 注册验证码图片
    * /registerNicknameCheck 注册用昵称重复判断
    * /registerEmailCheck 注册用邮箱重复判断
    * /registerVerify 注册激活
* /public/admin 管理员认证相关 AuthController **部分接口需要token未提供时返回400错误**
    * /login 登入
    * /logout 登出
    * /hasLogin 是否登入
* /auth 需要验证身份的内容操作 OperationController
    * /getCurrentUser 获取当前登录的用户的信息
    * /uploadAvatar 上传头像
    * /updateInfo 更改个人信息
    * /updatePassword 更改登录密码
    * /notice 消息通知相关 NoticeController
        * /getQuestionAnswer 获取订阅问题回答通知
        * /getArticleReply 获取文章回复通知
        * /getSystem 获取系统通知
        * /getAmount 获取通知总数量
        * /read 标记通知为已读
* /public/search 检索相关 SearchController
    * /infoByRegisterTime 按注册时间搜索

## service-article

* /public 信息获取 DateFetchController
    * /getArticlePublishedBy 获取某个用户发表的文章
    * /getArticle 获取文章内容
    * /getReplyOf 获取文章回复
    * /getReplyAmountOf 获取文章回复数量
    * /getApprovalAmountOf 获取文章点赞数量
    * /getCategoriesSuggestion 发布用文章类型推荐
* /public/home 首页相关 HomeFetchController
    * /latest 最新
    * /weekly 周榜
    * /monthly 月榜
* /auth 需要验证身份的内容操作 OperationController
    * /approve 点赞
    * /unApprove 取消点赞
    * /publishArticle 发表文章
    * /publishReply 发表文章回复
* /public/search 检索相关 SearchController
    * /byTime 按照发布时间搜索
    * /byApproval 按照点赞数量搜索
    * /byReplyAmount 按照回复数量搜索

## service-question

* /public 信息获取 DateFetchController
    * /getQuestionInfoPublishedBy 获取某个用户发表的问题
    * /getAnswerPublishedBy 获取某个用户发表的回答
    * ~~/getReplyOfAnswer 获取回答的楼中楼回复~~
    * /getQuestionInfo 获取问题基本信息
    * /getQuestionText 获取问题正文
    * /getAnswerAmountOf 获取问题回答数量
    * /getSubscriptionAmountOf 获取问题订阅数量
    * /getAnswerByTimeOf 按时间顺序获取答案
    * /getAnswerByApprovalOf 按点赞顺序获取答案
    * /getCategoriesSuggestion 发布用问题类型推荐
* /public/home 首页相关 HomeFetchController
    * /latest 最新
    * /weekly 周榜
    * /monthly 月榜
* /auth 需要验证身份的内容操作 OperationController
    * /subscription 订阅问题
    * /unSubscription 取消订阅问题
    * /approve 点赞回答
    * /unApprove 取消点赞回答
    * /publishQuestion 发表问题
    * /publishAnswer 发表问题回答
    * ~~/publishReply 发表回答楼中楼回复~~
* /public/search 检索相关 SearchController
    * /infoByTime 按照发布时间搜索问题
    * /infoBySubscriptionAmount 按照订阅数量搜索问题
    * /infoByAnswerAmount 按照回答数量搜索问题
    * /answerByTime 按照发布时间搜索答案
    * /answerByApproval 按照点赞数量搜索答案

# service-miscellaneous

* /public/home 首页相关 HomeMiscController
    * /siteState 网站状态数据
    * /adv 广告数据
    * /ddvImage/{filename} 广告图片
    * /hotCategories 热门分类
    * /announcement 公告
* /auth/report 举报相关 ReportController
    * /isReported 对象是否已经被举报
    * /question 举报问题
    * /answer 举报回答
    * /article 举报文章
    * /articleReply 举报文章回复
    * /user 举报用户
    * ~~/answerReply 举报回答楼中楼回复~~