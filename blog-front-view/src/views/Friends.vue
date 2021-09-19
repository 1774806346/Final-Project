<template>
  <el-row :gutter="20">
    <el-col :span="screenWidth>750?20:24" :offset="screenWidth>750?2:0">
      <el-card class="friends">
        <div align="center">
          <span class="effect">🎉friends link</span>
        </div>
        <div class="friends-group">
          <h2 class="friend-header">Technology bosses</h2>
          <div class="friend-content">
            <a
              class="friend-card"
              v-for="friend in friends"
              :key="friend.link_id"
              :href="friend.link_url"
            >
              <div class="friend-left">
                <img class="avatar" :src="friend.avatar" />
              </div>
              <div class="friend-right">
                <p class="friend-name">{{friend.link_name}}</p>
                <div class="friend-tags-wrapper">
                  <p class="tags">{{friend.link_info}}</p>
                </div>
              </div>
            </a>
          </div>
        </div>
        <div class="container">
          <h1 class="fwarning" align="center">How to use Friends Link</h1>
          <ul class="list">
            <div
              class="f-margin-left-mini f-margin-tb-tiny"
              style="font-size: large;font-weight: bold"
            >Website requirement</div>
            <br />
            <li
              class="f-margin-left-big f-margin-tb-tiny"
              style="font-size: medium"
            >Without politically sensitive content, the website should be able to access for a long time</li>
            <li
              class="f-margin-left-big f-margin-tb-tiny"
              style="font-size: medium"
            >More than 10 personal original articles, new articles will be updated within two months</li>
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Original blog, technology blog is preferred</li>
            <li
              class="f-margin-left-big f-margin-tb-tiny"
              style="font-size: medium"
            >Need to exchange friends chain, first add this site to your website, and then according to the following format, send me an email or message to me in the message board ~</li>
            <br />
            <div
              class="f-margin-left-mini f-margin-tb-tiny"
              style="font-size: large;font-weight: bold"
            >Application format</div>
            <br />
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Blog Title:{{siteInfo.blogName}}</li>
            <li
              class="f-margin-left-big f-margin-tb-tiny"
              style="font-size: medium"
            >Blog address: this blog address</li>
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Profile picture Address:{{siteInfo.headAvatar}}</li>
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Site description:{{siteInfo.headInfo}}</li>
            <div
              class="f-margin-left-mini f-margin-tb-tiny"
              style="font-size: large;font-weight: bold"
            >To apply for</div>
            <br />
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Method 1: Leave a comment below to apply</li>
            <li class="f-margin-left-big f-margin-tb-tiny" style="font-size: medium">Way two: home page sidebar click exchange friend chain</li>
          </ul>
        </div>
        <commentList :objectData="{...propsData}" />
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "friends",
      computed: {
    ...mapState(["siteInfo"])
  },
  data() {
    return {
      propsData: {
        blog_id: 0,
        page: 1
      },
      friends: {},
      screenWidth: document.body.clientWidth
    };
  },
  methods: {
    getFriends() {
      const _this = this;
      this.$axios.get("/allFriends").then(res => {
        //  console.log(res.data.data);
        _this.friends = res.data.data;
      });
    }
  },
    mounted() {
    const that = this
    that.getFriends();
     window.onresize = () => {
        return (() => {
            window.screenWidth = document.body.clientWidth
            that.screenWidth = window.screenWidth
        })()
    }
  },
  watch:{
    screenWidth(val){

        if(!this.timer){

            this.screenWidth = val
            this.timer = true
            let that = this
            setTimeout(function(){

                that.timer = false
            },400)
        }
    }
}
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.container {
  display: block;
  max-width: 100% !important;
}
.f-margin-left-big {
  margin-left: 8em !important;
}
.fwarning {
  margin-top: 100px;
}
.f-margin-tb-tiny {
  margin-top: 0.3em !important;
  margin-bottom: 0.3em !important;
}
.effect {
  display: inline;
  padding: 10px 20px;
  border-radius: 20px;
  font-family: "Arial Rounded MT Bold", "Helvetica Rounded", Arial, sans-serif;
  font-size: 2em;
  letter-spacing: 0.2em;
  color: #fff;
  font-weight: 700;
  background: linear-gradient(to bottom, #49b1f5 0, #49b1f5 100%);
}
.friends .friends-group .friend-content {
  display: flex;
  flex-wrap: wrap;
  margin: -8px;
  border-radius: 8px;
  align-items: flex-start;
  line-height: 1.3;
}
.friends .friends-group .friend-content .friend-card {
  display: flex;
  border-radius: 4px;
  padding: 8px 0px;
  margin: calc(68px) 8px 8px;
  color: #fff;
  background: #49b1f5;
  box-shadow: 0 2px 20px #49b1f5;
  place-content: flex-start;
  flex-direction: column;
  width: calc(25% - 16px);
}
@media screen and (max-width: 1024px) {
  .friends {
    margin-top: 50px;
  }
  .friends .friends-group .friend-content .friend-card {
    width: calc(25% - 16px);
  }
}
@media screen and (max-width: 768px) {
  .friends .friends-group .friend-content .friend-card {
    width: calc(33.3333% - 16px);
  }
}
@media screen and (max-width: 500px) {
  .friends .friends-group .friend-content .friend-card {
    width: calc(50% - 16px);
  }
}
.friends
  .friends-group
  .friend-content
  .friend-card:hover
  .friend-left
  .avatar {
  transform: scale(1.2) rotate(12deg);
  box-shadow: rgba(0, 0, 0, 0.1) 0px 2px 4px 0px,
    rgba(0, 0, 0, 0.1) 0px 4px 8px 0px, rgba(0, 0, 0, 0.1) 0px 8px 16px 0px;
}
.friends .friends-group .friend-content .friend-card .friend-left {
  display: flex;
  align-self: center;
}
.friends .friends-group .friend-content .friend-card .friend-left .avatar {
  width: 64px;
  height: 64px;
  transition: all 0.28s ease 0s;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 1px 2px 0px;
  margin: calc(-52px) 8px 4px;
  border-radius: 100%;
  border: 2px solid rgb(255, 255, 255);
  background: rgb(255, 255, 255);
}
.friends .friends-group .friend-content .friend-card .friend-right {
  margin: 4px 8px;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.friends .friends-group .friend-content .friend-card .friend-right p {
  text-align: center;
}
.friends
  .friends-group
  .friend-content
  .friend-card
  .friend-right
  .friend-tags-wrapper {
  display: -webkit-box;
  transition: all 0.28s ease 0s;
  margin-left: -2px;
  word-break: break-all;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.friends .friends-group .friend-content .friend-card .friend-right p {
  margin: 0px;
}
.friends
  .friends-group
  .friend-content
  .friend-card
  .friend-right
  p.friend-name {
  font-size: 0.8125rem;
  padding-top: 4px;
  font-weight: 700;
}
.friends .friends-group .friend-content .friend-card .friend-right p.tags {
  font-size: 0.78125rem;
  display: inline;
  background: 0px 0px;
  overflow-wrap: break-word;
  padding-right: 4px;
}
</style>