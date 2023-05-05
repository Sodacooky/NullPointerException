import {createApp} from "vue";

//import element plus
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
//import element plus icons
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
//import mavon
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
//import axios
import axios from "axios";
import VueAxios from "vue-axios";
//import hljs
import hljs from "highlight.js";
import "highlight.js/styles/atom-one-dark.css"; //样式
//import router
import Router from "@/router";

//import main vue component
import App from "./App.vue";

//create vue instance
const app = createApp(App);

//use element plus
app.use(ElementPlus);
//add all icons to vue instance
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
//use mavon
app.use(mavonEditor);
//add router to vue
app.use(Router);
//use axios
app.use(VueAxios, axios);

//创建v-highlight全局指令
app.directive("highlight", function (el) {
  let blocks = el.querySelectorAll("pre code");
  blocks.forEach((block) => {
    //hljs.highlightBlock(block);
    hljs.highlightElement(block);
  });
});

//mount
app.mount("#app");
