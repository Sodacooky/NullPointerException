-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: npe
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advertisement` (
  `id` bigint NOT NULL COMMENT 'ID',
  `url` varchar(512) NOT NULL COMMENT '点击广告跳转的地址',
  `image` varchar(128) NOT NULL COMMENT '广告图片的文件名',
  `time` datetime NOT NULL COMMENT '广告设置的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='推广、广告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
INSERT INTO `advertisement` VALUES (1654814633004941313,'https://www.baidu.com/','1','2023-05-06 19:45:28'),(1654815704624447490,'https://www.google.com/','2','2023-05-06 19:49:44');
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement` (
  `id` bigint NOT NULL COMMENT '公告的ID',
  `title` varchar(256) NOT NULL COMMENT '公告的标题',
  `text` text NOT NULL COMMENT '公告的正文',
  `time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (114514,'运营辣','NullPointerException网站现在开始试运行啦！','2023-04-17 16:27:57'),(6666666666,'社区整治公告','任何用户发布的内容都需要遵循互联网基本规则，如发现不良内容可以点击举报。','2023-04-17 16:39:56');
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approval_answer`
--

DROP TABLE IF EXISTS `approval_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approval_answer` (
  `id` bigint NOT NULL COMMENT '点赞记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `answer_id` bigint NOT NULL COMMENT '回答的ID',
  `time` datetime NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  KEY `approval_answer_question_answer_id_fk` (`answer_id`),
  KEY `approval_answer_user_info_id_fk` (`user_id`),
  CONSTRAINT `approval_answer_question_answer_id_fk` FOREIGN KEY (`answer_id`) REFERENCES `question_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `approval_answer_user_info_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户对回答赞的记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_answer`
--

LOCK TABLES `approval_answer` WRITE;
/*!40000 ALTER TABLE `approval_answer` DISABLE KEYS */;
INSERT INTO `approval_answer` VALUES (1655523964281229313,1650889078710849537,1655252194873626625,'2023-05-08 18:44:06');
/*!40000 ALTER TABLE `approval_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approval_article`
--

DROP TABLE IF EXISTS `approval_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approval_article` (
  `id` bigint NOT NULL COMMENT '点赞记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `time` datetime NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  KEY `approval_article_user_info_id_fk` (`user_id`),
  KEY `approval_article_article_id_fk` (`article_id`),
  CONSTRAINT `approval_article_article_id_fk` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `approval_article_user_info_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户对文章赞的记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_article`
--

LOCK TABLES `approval_article` WRITE;
/*!40000 ALTER TABLE `approval_article` DISABLE KEYS */;
INSERT INTO `approval_article` VALUES (1655524072729100290,1650889078710849537,1,'2023-05-08 18:44:32');
/*!40000 ALTER TABLE `approval_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` bigint NOT NULL COMMENT '文章ID',
  `title` varchar(256) NOT NULL COMMENT '文章标题',
  `text` longtext NOT NULL COMMENT '文章正文',
  `category` varchar(64) NOT NULL COMMENT '文章的分类',
  `publisher_id` bigint NOT NULL COMMENT '发布者用户ID',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `article_user_info_id_fk` (`publisher_id`),
  CONSTRAINT `article_user_info_id_fk` FOREIGN KEY (`publisher_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'试试发文章功能','# 标题\n\n* 列表项目1\n* 列表项目2\n\n我的代码如下：\n```java\nif (articlePublishVO == null) {\n    return RestResponse.fail(1, \"缺少参数\");\n}\n```\n\n> 你看懂了吗','其他',3,'2023-04-07 21:21:12'),(1652699489386921985,'标题','写点什么??','测试',1650889078710849537,'2023-04-30 23:40:39'),(1655235581214507009,'测试文章','文本文本文本','测试',1650889078710849537,'2023-05-07 23:38:10');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_reply`
--

DROP TABLE IF EXISTS `article_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_reply` (
  `id` bigint NOT NULL COMMENT '回复ID',
  `goal_article_id` bigint DEFAULT NULL COMMENT '回复的目标文章ID，如果回复的是其他回复请保持NULL',
  `text` mediumtext NOT NULL COMMENT '回复的内容正文',
  `publisher_id` bigint NOT NULL COMMENT '回复者ID',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `article_reply_article_id_fk` (`goal_article_id`),
  KEY `article_reply_user_info_id_fk` (`publisher_id`),
  CONSTRAINT `article_reply_article_id_fk` FOREIGN KEY (`goal_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_reply_user_info_id_fk` FOREIGN KEY (`publisher_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章回复';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_reply`
--

LOCK TABLES `article_reply` WRITE;
/*!40000 ALTER TABLE `article_reply` DISABLE KEYS */;
INSERT INTO `article_reply` VALUES (2,1,'我也回复！',2,'2023-04-08 22:36:34');
/*!40000 ALTER TABLE `article_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `global_data`
--

DROP TABLE IF EXISTS `global_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_data` (
  `name` varchar(256) NOT NULL COMMENT '值名称',
  `content` varchar(1024) NOT NULL COMMENT '值的内容',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='各种数值，相当于全局变量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_data`
--

LOCK TABLES `global_data` WRITE;
/*!40000 ALTER TABLE `global_data` DISABLE KEYS */;
INSERT INTO `global_data` VALUES ('adminPassword','114514'),('backendHost','http://localhost:8080'),('host','http://localhost:5371');
/*!40000 ALTER TABLE `global_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_answer`
--

DROP TABLE IF EXISTS `question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_answer` (
  `id` bigint NOT NULL COMMENT '回答的ID',
  `question_id` bigint NOT NULL COMMENT '问题的ID',
  `text` mediumtext NOT NULL COMMENT '回答的正文',
  `order_number` int NOT NULL COMMENT '在一个问题中的序号，楼层号',
  `publisher_id` bigint NOT NULL COMMENT '发布回答用户的ID',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `question_answer_question_info_id_fk` (`question_id`),
  KEY `question_answer_user_info_id_fk` (`publisher_id`),
  CONSTRAINT `question_answer_question_info_id_fk` FOREIGN KEY (`question_id`) REFERENCES `question_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_answer_user_info_id_fk` FOREIGN KEY (`publisher_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='对问题的回答，包括一楼的问题详细内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_answer`
--

LOCK TABLES `question_answer` WRITE;
/*!40000 ALTER TABLE `question_answer` DISABLE KEYS */;
INSERT INTO `question_answer` VALUES (1655240863650992129,1655240863550328833,'就是说有啥用？',0,1650889078710849537,'2023-05-07 23:59:09'),(1655241020564099074,1655241020522156033,'本社爆破',0,1,'2023-05-07 23:59:47'),(1655251790655967234,1655251790580469761,'# 标题\n\n* 第一点\n* 第二点 \n\n```java\nString words = \"代码\"+\"块\";\n```',0,1,'2023-05-08 00:42:35'),(1655252194873626625,1655251790580469761,'最佳回答：**我不知道**',1,1650889078710849537,'2023-05-08 00:44:11');
/*!40000 ALTER TABLE `question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_info`
--

DROP TABLE IF EXISTS `question_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_info` (
  `id` bigint NOT NULL COMMENT '问题的ID',
  `title` varchar(256) NOT NULL COMMENT '问题的标题、概述',
  `publisher_id` bigint NOT NULL COMMENT '发布者用户的ID',
  `publish_time` datetime NOT NULL COMMENT '发布的时间',
  `category` varchar(64) NOT NULL COMMENT '问题的分类',
  PRIMARY KEY (`id`),
  KEY `question_header_user_info_id_fk` (`publisher_id`),
  CONSTRAINT `question_header_user_info_id_fk` FOREIGN KEY (`publisher_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='问题的信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_info`
--

LOCK TABLES `question_info` WRITE;
/*!40000 ALTER TABLE `question_info` DISABLE KEYS */;
INSERT INTO `question_info` VALUES (1655240863550328833,'这个网站有什么用？',1650889078710849537,'2023-05-07 23:59:09','社区'),(1655241020522156033,'略略略，这个网站有啥用',1,'2023-05-07 23:59:47','测试'),(1655251790580469761,'Java中有什么好用的单元测试框架？',1,'2023-05-08 00:42:35','测');
/*!40000 ALTER TABLE `question_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL COMMENT '举报记录ID',
  `goal_user_id` bigint DEFAULT NULL COMMENT '被举报的用户的ID',
  `goal_article_id` bigint DEFAULT NULL COMMENT '被举报的文章ID',
  `goal_article_reply_id` bigint DEFAULT NULL COMMENT '被举报的文章回复ID',
  `goal_question_id` bigint DEFAULT NULL COMMENT '被举报的问题ID',
  `goal_question_answer_id` bigint DEFAULT NULL COMMENT '被举报的回答ID',
  `time` datetime NOT NULL COMMENT '举报时间',
  `comment` text NOT NULL COMMENT '举报附加信息',
  `is_processed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已经处理',
  `reporter_id` bigint NOT NULL COMMENT '举报者的用户ID',
  PRIMARY KEY (`id`),
  KEY `report_article_id_fk` (`goal_article_id`),
  KEY `report_article_reply_id_fk` (`goal_article_reply_id`),
  KEY `report_question_answer_id_fk` (`goal_question_answer_id`),
  KEY `report_question_info_id_fk` (`goal_question_id`),
  KEY `report_user_info_id_fk` (`reporter_id`),
  KEY `report_user_info_id_fk2` (`goal_user_id`),
  CONSTRAINT `report_article_id_fk` FOREIGN KEY (`goal_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_article_reply_id_fk` FOREIGN KEY (`goal_article_reply_id`) REFERENCES `article_reply` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_question_answer_id_fk` FOREIGN KEY (`goal_question_answer_id`) REFERENCES `question_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_question_info_id_fk` FOREIGN KEY (`goal_question_id`) REFERENCES `question_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_user_info_id_fk` FOREIGN KEY (`reporter_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_user_info_id_fk2` FOREIGN KEY (`goal_user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户举报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1653836413749264385,3,NULL,NULL,NULL,NULL,'2023-05-04 02:58:22','11',1,1650889078710849537),(1655141665811308546,NULL,1652699489386921985,NULL,NULL,NULL,'2023-05-07 17:24:59','灌水啦啦啦啦啦啦这个',0,1650889078710849537),(1655266498171678721,NULL,NULL,NULL,1655241020522156033,NULL,'2023-05-08 01:41:01','111',0,1650889078710849537);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authentication`
--

DROP TABLE IF EXISTS `user_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authentication` (
  `id` bigint NOT NULL COMMENT '用户id',
  `email` varchar(64) NOT NULL COMMENT '用户注册时的邮箱',
  `password` varchar(64) NOT NULL COMMENT '用户登录密码',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_authentication_user_info_id_fk` FOREIGN KEY (`id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录关键信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authentication`
--

LOCK TABLES `user_authentication` WRITE;
/*!40000 ALTER TABLE `user_authentication` DISABLE KEYS */;
INSERT INTO `user_authentication` VALUES (1,'test@test.cn','1145141919'),(2,'test2@test.cn','1145141919'),(3,'test3@test.cn','1145141919'),(1650889078710849537,'523379653@qq.com','1145141919');
/*!40000 ALTER TABLE `user_authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL COMMENT '用户的ID',
  `nickname` varchar(64) NOT NULL COMMENT '昵称，要求系统中唯一',
  `description` varchar(256) NOT NULL DEFAULT '无' COMMENT '简短的自我介绍',
  `avatar` varchar(256) NOT NULL DEFAULT 'default' COMMENT '头像的文件名称',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `is_banned` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已经被封禁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_info_pk2` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'TestUser1','测试用户1','default','2023-03-29 13:27:13',0),(2,'测试用户2','哈哈','default','2023-03-29 13:27:26',0),(3,'小明','我就是小明','default','2023-03-29 13:27:33',1),(1650889078710849537,'Sodacooky','哥只是一个传说','default','2023-04-25 23:46:43',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notice`
--

DROP TABLE IF EXISTS `user_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notice` (
  `id` bigint NOT NULL COMMENT '消息id',
  `goal_user_id` bigint NOT NULL COMMENT '接收消息的用户的ID',
  `type` varchar(64) DEFAULT NULL COMMENT '消息的类型',
  `title` varchar(64) NOT NULL COMMENT '消息标题',
  `text` mediumtext NOT NULL COMMENT '正文',
  `time` datetime NOT NULL COMMENT '通知时间',
  `is_read` tinyint(1) NOT NULL COMMENT '是否已读消息',
  `supplement` varchar(256) DEFAULT NULL COMMENT '附带的内容，可能是要跳转到的ID',
  PRIMARY KEY (`id`),
  KEY `user_notice_user_info_id_fk` (`goal_user_id`),
  CONSTRAINT `user_notice_user_info_id_fk` FOREIGN KEY (`goal_user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收到的通知';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notice`
--

LOCK TABLES `user_notice` WRITE;
/*!40000 ALTER TABLE `user_notice` DISABLE KEYS */;
INSERT INTO `user_notice` VALUES (1,1650889078710849537,'system','欢迎来到NullPointerException!','期待你为社区带来的改变！','2023-04-27 17:42:57',1,NULL),(1654424302270877698,1,'system','问题 测试问题1 网站 已被管理员删除','管理员已将该问题连同答案一起删除，请确认你已准守社区的规则。','2023-05-05 17:54:26',0,NULL),(1654428321815035906,2,'system','您在问题 这个网站存在的意义是什么 下的一条回答已被管理员删除','管理员已将该答案删除，请确认你已准守社区的规则。<br/>您的回答：<br/># 再回答一波','2023-05-05 18:10:24',0,'2'),(1654440282271301633,1650889078710849537,'system','问题 1 已被管理员删除','管理员已将该问题连同答案一起删除，请确认你已准守社区的规则。','2023-05-05 18:57:56',1,NULL),(1654440305629396994,1650889078710849537,'system','文章 标题 已被管理员删除','管理员已将该文章连同回复一起删除，请确认你已准守社区的规则。','2023-05-05 18:58:02',1,NULL),(1654453029855846402,1650889078710849537,'system','文章 2222 已被管理员删除','管理员已将该文章连同回复一起删除，请确认你已准守社区的规则。','2023-05-05 19:48:35',1,NULL),(1654453075221438466,2,'system','您在问题 试试发文章功能 下的一条回复已被管理员删除','管理员已将该答案该回复，请确认你已准守社区的规则。<br/>您的回复：<br/>回复~','2023-05-05 19:48:46',0,'1'),(1655208218569265153,3,'system','问题 这个网站存在的意义是什么 已被管理员删除','管理员已将该问题连同答案一起删除，请确认你已准守社区的规则。','2023-05-07 21:49:26',0,NULL),(1655209278226612226,1650889078710849537,'system','您在问题 标题 下的一条回复已被管理员删除','管理员已将该答案该回复，请确认你已准守社区的规则。<br/>您的回复：<br/>哈哈','2023-05-07 21:53:39',0,'1652699489386921985'),(1655252194923958274,1,'question_answer','问题 Java中有什么好用的单元测试框架？ 收到来自 Sodacooky 的回答','最佳回答：**我不知道**','2023-05-08 00:44:11',0,'1655251790580469761');
/*!40000 ALTER TABLE `user_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_question_subscription`
--

DROP TABLE IF EXISTS `user_question_subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_question_subscription` (
  `id` bigint NOT NULL COMMENT '订阅ID',
  `user_id` bigint NOT NULL COMMENT '订阅人ID',
  `question_id` bigint NOT NULL COMMENT '所订阅的问题ID',
  `time` datetime NOT NULL COMMENT '订阅时间',
  PRIMARY KEY (`id`),
  KEY `user_question_subscription_question_info_id_fk` (`question_id`),
  KEY `user_question_subscription_user_info_id_fk` (`user_id`),
  CONSTRAINT `user_question_subscription_question_info_id_fk` FOREIGN KEY (`question_id`) REFERENCES `question_info` (`id`),
  CONSTRAINT `user_question_subscription_user_info_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关注的问题';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_question_subscription`
--

LOCK TABLES `user_question_subscription` WRITE;
/*!40000 ALTER TABLE `user_question_subscription` DISABLE KEYS */;
INSERT INTO `user_question_subscription` VALUES (1655519230346465281,1650889078710849537,1655251790580469761,'2023-05-08 18:25:17');
/*!40000 ALTER TABLE `user_question_subscription` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-08 18:47:50
