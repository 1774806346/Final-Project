import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // theme
// import './assets/css/theme-green/index.css'; // green theme
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';
import 'default-passive-events'
import store from './store/store'
import './permission.js' //router
import axios from '@/plugins/axios'

Vue.prototype.$axios = axios //
import VueClipboard from 'vue-clipboard2'

Vue.use(VueClipboard)

Vue.config.productionTip = false;
Vue.use(ElementUI, {
    size: 'small'
});




new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
