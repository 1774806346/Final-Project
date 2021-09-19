<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'sysindex',
                    title: 'system Home'
                },
                {
                    icon: 'el-icon-edit',
                    index: '1',
                    title: 'The article management',
                    subs: [
                        {
                            index: 'articles',
                            title: 'The article lists'
                        },
                        {
                            index: 'BlogEdit',
                            title: 'Published articles'
                        }
                    ]
                },
                {
                    icon: 'el-icon-s-help',
                    index: 'moment',
                    title: 'Dynamic management'
                },
                {
                    icon: 'el-icon-lx-addressbook',
                    index: 'friendlist',
                    title: 'Friends Link'
                },
                {
                    icon: 'el-icon-lx-tag',
                    index: 'blogtag',
                    title: 'Tags directory'
                },
                {
                    icon: 'el-icon-notebook-2',
                    index: 'category',
                    title: 'catalog'
                },
                {
                    icon: 'el-icon-chat-dot-round',
                    index: 'comments',
                    title: 'Comments  management'
                },
                {
                    icon: 'el-icon-picture',
                    index: 'ImageBed',
                    title: 'The warehouse figure bed'
                },
                 {
                    icon: 'el-icon-tickets',
                    index: '7',
                    title: 'Site management',
                    subs: [
                        {
                            index: 'SiteSetting',
                            title: 'Site set up'
                        }
                    ]
                },
                {
                    icon: 'el-icon-tickets',
                    index: '8',
                    title: 'Log management',
                    subs: [
                        {
                            index: 'joblogs',
                            title: 'Job log'
                        },
                        {
                            index: 'loginlogs',
                            title: 'Log on to log'
                        },
                        {
                            index: 'operationlogs',
                            title: 'The operation log'
                        },
                        {
                            index: 'errorlogs',
                            title: 'Abnormal log'
                        },
                        {
                            index: 'vistorlogs',
                            title: 'Access log'
                        }
                    ]
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {

        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
