<template>
    <div id="app">
        <router-view v-if="isRouterAlive"></router-view>
    </div>
</template>
<script>
export default {
    name: 'App',
    provide() {
        //Provide variables in parent components and inject variables in child components.
        return {
            reload: this.reload
        };
    },
    data() {
        return {
            isRouterAlive: true //A variable that controls whether the view is displayed
        };
    },
    methods: {
        reload() {
            this.isRouterAlive = false; //Turn off,
            this.$nextTick(function() {
                this.isRouterAlive = true; //And on again
            });
        }
    }
};
</script>
<style>
@import './assets/css/main.css';
/* Dark theme */
@import './assets/css/color-dark.css';
/* Light green theme */
/* @import "./assets/css/theme-green/color-green.css";  */
::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}
</style>