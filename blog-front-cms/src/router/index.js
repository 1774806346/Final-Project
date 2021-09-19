import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/sysindex'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: 'Readme file', requireAuth: true },
            children: [
                {
                    path: '/sysindex',
                    name:'sysindex',
                    component: () => import(/* webpackChunkName: "SysIndex" */ '../components/page/SysIndex.vue'),
                    meta: { title: 'The system\'s front page', requireAuth: true }
                },
                {
                    path: '/friendlist',
                    name:'friendlist',
                    component: () => import(/* webpackChunkName: "friendlist" */ '../components/page/FriendList.vue'),
                    meta: { title: 'Friends Link', requireAuth: true  }
                },
                {
                    path: '/articles',
                    name:'articles',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/Articles.vue'),
                    meta: { title: 'The article lists', requireAuth: true  }
                },
                {
                    // BlogEdit
                    path: '/BlogEdit/:blog_id',
                    name:'BlogEdit',
                    component: () => import(/* webpackChunkName: "BlogEdit" */ '../components/page/BlogEdit.vue'),
                    meta: { title: 'The article editor', requireAuth: true  }
                },
                {
                    // BlogEdit
                    path: '/BlogEdit',
                    name:'BlogRealse',
                    component: () => import(/* webpackChunkName: "BlogEdit" */ '../components/page/BlogEdit.vue'),
                    meta: { title: 'The article writing', requireAuth: true  }
                },
                {
                    path: '/blogtag',
                    name:'blogtag',
                    component: () => import(/* webpackChunkName: "blogtag" */ '../components/page/BlogTag.vue'),
                    meta: { title: 'Tags directory', requireAuth: true  }
                },
                {
                    path: '/category',
                    name:'category',
                    component: () => import(/* webpackChunkName: "category" */ '../components/page/CateGory.vue'),
                    meta: { title: 'catalog', requireAuth: true  }
                },
                {
                    path: '/comments',
                    name:'comments',
                    component: () => import(/* webpackChunkName: "category" */ '../components/page/Comments.vue'),
                    meta: { title: 'Comments on the management', requireAuth: true  }
                },
                {
                    path: '/moment',
                    name:'moment',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/Moment.vue'),
                    meta: { title: 'Dynamic list', requireAuth: true  }
                },
                {
                    path:'/joblogs',
                    name:'joblogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Joblogs.vue'),
                    meta: { title: 'Job log', requireAuth: true  }
                },
                {
                    path:'/loginlogs',
                    name:'loginlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Loginlogs.vue'),
                    meta: { title: 'Log on to log', requireAuth: true  }
                },
                {
                    path:'/operationlogs',
                    name:'operationlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Operationlogs.vue'),
                    meta: { title: 'The operation log', requireAuth: true  }
                },
                {
                    path:'/errorlogs',
                    name:'errorlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Errorlogs.vue'),
                    meta: { title: 'Abnormal log', requireAuth: true  }
                },
                {
                    path:'/vistorlogs',
                    name:'vistorlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Vistorlogs.vue'),
                    meta: { title: 'Access log', requireAuth: true  }
                },
                {
                    path:'/SiteSetting',
                    name:'SiteSetting',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/SiteSetting.vue'),
                    meta: { title: 'Site set up', requireAuth: true  }
                },
                {
                    path:'/ImageBed',
                    name:'ImageBed',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/ImageBed.vue'),
                    meta: { title: 'Yards cloud bed', requireAuth: true  }
                },
                {
                    path: '/404',
                    name:'404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' , requireAuth: true }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: 'The login' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
