import {createApp} from 'vue'

//import element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//import element plus icons
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//import mavon
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

//import router
import Router from "@/router";

//import main vue component
import App from './App.vue'

//create vue instance
const app = createApp(App)

//use element plus
app.use(ElementPlus)
//add all icons to vue instance
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
//use mavon
app.use(mavonEditor)

//add router to vue
app.use(Router)

//mount
app.mount('#app')
