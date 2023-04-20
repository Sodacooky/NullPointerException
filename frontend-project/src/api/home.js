import requests, { baseUrl } from "@/api/requests";

export function getLatestQuestion(page, queryTime) {
  return requests.get("/service-question/home/latest", {
    params: { page: page, queryTime: queryTime },
  });
}

export function getWeeklyQuestion() {
  return requests.get("/service-question/home/weekly");
}

export function getMonthlyQuestion() {
  return requests.get("/service-question/home/monthly");
}

export function getLatestArticle(page, queryTime) {
  return requests.get("/service-article/home/latest", {
    params: { page: page, queryTime: queryTime },
  });
}

export function getWeeklyArticle() {
  return requests.get("/service-article/home/weekly");
}

export function getMonthlyArticle() {
  return requests.get("/service-article/home/monthly");
}

export function getSiteState() {
  return requests.get("/service-miscellaneous/home/siteState");
}

export function getAds() {
  return requests.get("/service-miscellaneous/home/adv");
}

export function getAdsImageUrl(filename) {
  return baseUrl + "/service-miscellaneous/home/advImage/" + filename;
}

export function getHotCategories() {
  return requests.get("/service-miscellaneous/home/hotCategories");
}

export function getAnnouncement() {
  return requests.get("/service-miscellaneous/home/announcement");
}
