create table user_info
(
    id            varchar(64)                    not null comment '用户的ID，UUID，没有横杠'
        primary key,
    nickname      varchar(64)                    not null comment '昵称，要求系统中唯一',
    description   varchar(256) default '无'      not null comment '简短的自我介绍',
    avatar        varchar(256) default 'default' not null comment '头像的文件名称',
    register_date datetime                       not null comment '注册日期',
    is_banned     tinyint(1)   default 0         not null comment '是否已经被封禁',
    constraint user_info_pk2
        unique (nickname)
)
    comment '用户基本信息表';

create table article
(
    id           varchar(64)  not null comment '文章ID'
        primary key,
    title        varchar(256) not null comment '文章标题',
    text         longtext     not null comment '文章正文',
    category     varchar(64)  not null comment '文章的分类',
    publisher_id varchar(64)  not null comment '发布者用户ID',
    publish_time datetime     not null comment '发布时间',
    constraint article_user_info_id_fk
        foreign key (publisher_id) references user_info (id)
)
    comment '文章';

create table approval_article
(
    id         varchar(64) not null comment '点赞记录ID'
        primary key,
    user_id    varchar(64) not null comment '用户ID',
    article_id varchar(64) not null comment '文章ID',
    time       datetime    not null comment '点赞时间',
    constraint approval_article_article_id_fk
        foreign key (article_id) references article (id),
    constraint approval_article_user_info_id_fk
        foreign key (user_id) references user_info (id)
)
    comment '用户对文章赞的记录';

create table article_reply
(
    id              varchar(64) not null comment '回复ID'
        primary key,
    goal_article_id varchar(64) null comment '回复的目标文章ID，如果回复的是其他回复请保持NULL',
    goal_reply_id   varchar(64) null comment '回复的目标回复的ID，如果回复的是文章请保持NULL',
    text            mediumtext  not null comment '回复的内容正文',
    publisher_id    varchar(64) not null comment '回复者ID',
    publish_time    datetime    not null comment '发布时间',
    constraint article_reply_article_id_fk
        foreign key (goal_article_id) references article (id),
    constraint article_reply_article_reply_id_fk
        foreign key (goal_reply_id) references article_reply (id),
    constraint article_reply_user_info_id_fk
        foreign key (publisher_id) references user_info (id)
)
    comment '文章回复';

create table question_info
(
    id           varchar(64)  not null comment '问题的ID'
        primary key,
    title        varchar(256) not null comment '问题的标题、概述',
    publisher_id varchar(64)  not null comment '发布者用户的ID',
    publish_time datetime     not null comment '发布的时间',
    category     varchar(64)  not null comment '问题的分类',
    constraint question_header_user_info_id_fk
        foreign key (publisher_id) references user_info (id)
)
    comment '问题的信息';

create table question_answer
(
    id           varchar(64) not null comment '回答的ID'
        primary key,
    question_id  varchar(64) not null comment '问题的ID',
    text         mediumtext  not null comment '回答的正文',
    order_number int         not null comment '在一个问题中的序号，楼层号',
    publisher_id varchar(64) not null comment '发布回答用户的ID',
    publish_time datetime    not null comment '发布时间',
    constraint question_answer_question_info_id_fk
        foreign key (question_id) references question_info (id),
    constraint question_answer_user_info_id_fk
        foreign key (publisher_id) references user_info (id)
)
    comment '对问题的回答，包括一楼的问题详细内容';

create table approval_answer
(
    id        varchar(64) not null comment '点赞记录ID'
        primary key,
    user_id   varchar(64) not null comment '用户ID',
    answer_id varchar(64) not null comment '回答的ID',
    time      datetime    not null comment '点赞时间',
    constraint approval_answer_question_answer_id_fk
        foreign key (answer_id) references question_answer (id),
    constraint approval_answer_user_info_id_fk
        foreign key (user_id) references user_info (id)
)
    comment '用户对回答赞的记录';

create table question_reply
(
    id             varchar(64) not null comment '回复的ID'
        primary key,
    goal_answer_id varchar(64) null comment '目标问题ID，如果回复的是其他回复请保持为NULL',
    goal_reply_id  varchar(64) null comment '目标回复的ID，如果回复的是一个回答请保持为NULL',
    text           mediumtext  not null comment '回复内容正文',
    publisher_id   varchar(64) not null comment '回复发布者ID',
    publish_time   datetime    not null comment '回复发布时间',
    constraint question_reply_question_answer_id_fk
        foreign key (goal_answer_id) references question_answer (id),
    constraint question_reply_question_reply_id_fk
        foreign key (goal_reply_id) references question_reply (id),
    constraint question_reply_user_info_id_fk
        foreign key (publisher_id) references user_info (id)
)
    comment '对问题中的回答或其他回复的回复';

create table report
(
    id                      varchar(64)          not null comment '举报记录ID'
        primary key,
    goal_user_id            varchar(64)          null comment '被举报的用户的ID',
    goal_article_id         varchar(64)          null comment '被举报的文章ID',
    goal_article_reply_id   varchar(64)          null comment '被举报的文章回复ID',
    goal_question_id        varchar(64)          null comment '被举报的问题ID',
    goal_question_reply_id  varchar(64)          null comment '被举报的问答回复ID',
    goal_question_answer_id varchar(64)          null comment '被举报的回答ID',
    time                    datetime             not null comment '举报时间',
    comment                 text                 not null comment '举报附加信息',
    is_processed            tinyint(1) default 0 not null comment '是否已经处理',
    reporter_id             varchar(64)          not null comment '举报者的用户ID',
    constraint report_article_id_fk
        foreign key (goal_article_id) references article (id),
    constraint report_article_reply_id_fk
        foreign key (goal_article_reply_id) references article_reply (id),
    constraint report_question_answer_id_fk
        foreign key (goal_question_answer_id) references question_answer (id),
    constraint report_question_info_id_fk
        foreign key (goal_question_id) references question_info (id),
    constraint report_question_reply_id_fk
        foreign key (goal_question_reply_id) references question_reply (id),
    constraint report_user_info_id_fk
        foreign key (reporter_id) references user_info (id),
    constraint report_user_info_id_fk2
        foreign key (goal_user_id) references user_info (id)
)
    comment '用户举报';

create table user_authentication
(
    id       varchar(64) not null comment '用户id'
        primary key,
    email    varchar(64) not null comment '用户注册时的邮箱',
    password varchar(64) not null comment '用户登录密码',
    constraint user_authentication_user_info_id_fk
        foreign key (id) references user_info (id)
)
    comment '用户登录关键信息';

create table user_notice
(
    id           varchar(64) not null comment '消息id'
        primary key,
    goal_user_id varchar(64) not null comment '接收消息的用户的ID',
    title        varchar(64) not null comment '消息标题',
    text         mediumtext  not null comment '正文',
    time         datetime    not null comment '通知时间',
    is_read      tinyint(1)  not null comment '是否已读消息',
    constraint user_notice_user_info_id_fk
        foreign key (goal_user_id) references user_info (id)
)
    comment '用户消息';

create table user_question_subscription
(
    id          varchar(64) not null comment '订阅ID'
        primary key,
    user_id     varchar(64) not null comment '订阅人ID',
    question_id varchar(64) not null comment '所订阅的问题ID',
    time        datetime    not null comment '订阅时间',
    constraint user_question_subscription_question_info_id_fk
        foreign key (question_id) references question_info (id),
    constraint user_question_subscription_user_info_id_fk
        foreign key (user_id) references user_info (id)
)
    comment '用户关注的问题';


