import requests from "@/api/requests";

export function getArticle(articleId) {
  return requests.get("/service-article/getArticle", {
    params: { articleId: articleId },
  });
}

export function getArticleReply(articleId, page) {
  return requests.get("/service-article/getReplyOf", {
    params: { articleId: articleId, page: page },
  });
}
