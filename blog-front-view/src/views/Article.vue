<template>
  <div class="home">
    <el-row id="artList" type="flex" justify="space-around">
      <el-col :span="screenWidth>750?16:24">
        <el-row class="art-item">
          <el-card>
            <div id="artcle-info" :style="{'background-image': 'url('+blog.blog_cover_image+')'}">
              <h2 class="text-center">
                <strong style=" color: #ffffff;">{{blog.blog_title}}</strong>
              </h2>

              <div class="text-center timeAndView">
                <span class="article-time">
                  <i class="el-icon-time"></i>
                  published on：
                  <span>{{blog.create_time}}</span>
                </span>
                &nbsp;|&nbsp;
                <span class="article-views">
                  <i class="el-icon-view"></i>
                  read：
                  <span>{{blog.blog_visits}}</span>
                </span>
                <span class="article-views">
                  <img class="love-icon" src="../assets/love2.png" alt />
                  Likes：
                  <span>{{blog.blog_like}}</span>
                </span>
                <span class="article-views">
                  <i class="el-icon-chat-dot-round"></i>
                  comments：
                  <span>{{blog.comment_cnt}}</span>
                </span>
              </div>
              <p class="abstract">{{blog.blog_summary}}</p>
            </div>
            <div
              id="artcle-content"
              class="typo js-toc-content m-padded-tb-small line-numbers match-braces rainbow-braces"
              v-html="blog.blog_content"
            ></div>

            <div id="articlebtn">
              <el-row>
                <el-tooltip class="love" content="Like" placement="top">
                  <img @click="LikeBlog" class="icon" src="../assets/dianzan.png" alt />
                </el-tooltip>
                <el-popover placement="top" width="200" trigger="click">
                  <el-avatar
                    shape="square"
                    :size="200"
                    fit="fill"
                    :src="$store.state.siteInfo.reward"
                  ></el-avatar>
                  <svg
                    slot="reference"
                    style="width: 2.25em!important;height: 2.25em!important;"
                    t="1614349031867"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="6413"
                    width="200"
                    height="200"
                  >
                    <path d="M427.2 444h169.6V480H427.2z" fill="#FF5500" p-id="6414" />
                    <path
                      d="M512 0C229.6 0 0 229.6 0 512s229.6 512 512 512 512-229.6 512-512S794.4 0 512 0zM319.2 359.2h86.4c-7.2-14.4-16-27.2-25.6-40l31.2-11.2c11.2 14.4 20.8 31.2 28.8 51.2h54.4V304h35.2v55.2H584c10.4-16.8 20-34.4 28-52l32 11.2c-6.4 13.6-14.4 27.2-24 40.8h84.8v92h-33.6v-63.2H352v64.8h-32.8V359.2z m82.4 202.4v99.2h-32.8V532H656v123.2h-32.8V561.6H401.6z m-6.4-57.6V420h234.4V504H395.2z m95.2 183.2c-24 22.4-78.4 36.8-161.6 42.4l-13.6-30.4c74.4-3.2 123.2-14.4 146.4-32 20.8-17.6 33.6-47.2 36.8-88l33.6 2.4c-6.4 52.8-20 87.2-41.6 105.6z m196.8 45.6c-53.6-24-108-42.4-164.8-56l16.8-24.8C600 665.6 656 683.2 706.4 704l-19.2 28.8z"
                      fill="#FF5500"
                      p-id="6415"
                    />
                  </svg>
                </el-popover>
              </el-row>
            </div>
            <div id="statement">
              <p>
                <span style="color:#3399ea;">
                  <em>{{$store.state.siteInfo.headInfo}}</em>
                </span>
              </p>
              <div class="item">Author：{{$store.state.siteInfo.blogName}}</div>
              <div class="item">
                Ariticle link：
                <a href>{{url}}</a>
              </div>
              <div class="item">Copyright statement: in addition to the special statement of all articles in this blog, please indicate the source of reprint!</div>
            </div>
            <commentList :objectData="{...propsData}" />
          </el-card>
        </el-row>
      </el-col>
      <el-col :span="6" class="hidden-sm-and-down" id="side">
        <div class="item">
          <Introduction />
        </div>
        <div class="item">
          <categorys />
        </div>
        <div class="item">
          <tags />
        </div>
        <div class="item">
          <FriendSider />
        </div>
        <div class="item is-position-fixed">

							<Tocbot />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapState} from "vuex";
import Tocbot from "@/components/Tocbot";
export default {
  name: "articledetail",
  data() {
    return {
      propsData: {
        blog_id: 0,
        page: 0
      },
      blog: {},
      screenWidth: document.body.clientWidth,
      url: window.location.href
    };
  },
  components:{
    Tocbot
  },
  computed: {
			blogId() {
				return parseInt(this.$route.params.blogId)
      },
      ...mapState(['siteInfo'])
		},
  beforeRouteEnter(to, from, next) {

			next(vm => {

				vm.$store.dispatch('setIsBlogRenderComplete', false)
			})
		},
		beforeRouteLeave(to, from, next) {

			tocbot.destroy()
			next()
		},
		beforeRouteUpdate(to, from, next) {

			if (to.path !== from.path) {
        this.getBlog(to.params.blogId)
       // console.log(to.params.blogId)

				this.$store.dispatch('setIsBlogRenderComplete', false)
				next()
			}
		},
  methods: {
    getBlog(blogId = this.blogId) {
      const _this = this;
      this.$axios.get("/blog/" + blogId).then(res => {
        // console.log(res.data.data)
        if(res.data.code===200){
                  _this.blog = res.data.data;
        _this.propsData.blog_id = res.data.data.blog_id;
        _this.$nextTick(() => {
          Prism.highlightAll();
          this.$store.dispatch('setIsBlogRenderComplete', true)
        });
          document.title = this.blog.blog_title+this.siteInfo.webTitleSuffix
        }

      });
      this.scrollToTop();
      // console.log(blogId);
    },
    setCookie(key, value, expiredays) {
      var exdate = new Date();
      exdate.setDate(exdate.getDate() + expiredays);
      document.cookie =
        key +
        "=" +
        escape(value) +
        (expiredays == null ? "" : ";expires=" + exdate.toGMTString());
    },
    getCookie(key) {
      if (document.cookie.length) {
        var cookies = " " + document.cookie;
        var start = cookies.indexOf(" " + key + "=");
        if (start == -1) {
          return null;
        }
        var end = cookies.indexOf(";", start);
        if (end == -1) {
          end = cookies.length;
        }
        end -= start;
        var cookie = cookies.substr(start, end);
        return unescape(
          cookie.substr(
            cookie.indexOf("=") + 1,
            cookie.length - cookie.indexOf("=") + 1
          )
        );
      } else {
        return null;
      }
    },
    LikeBlog() {
      const blogId = this.$route.params.blogId;
      const _this = this;
      if (_this.getCookie("likeblog" + blogId) == null) {
        this.$axios.post("/likeBlog/" + blogId).then(res => {
          _this.$message.success(res.data.msg);
          _this.setCookie("likeblog" + blogId, blogId, 365);
        });
      } else {
        _this.$message.warning("Liked");
      }
    }
  },
  created(){
    this.getBlog();
  },
  mounted() {
    const that = this;
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth;
        that.screenWidth = window.screenWidth;
      })();
    };
  },
  watch: {
    screenWidth(val) {

      if (!this.timer) {

        this.screenWidth = val;
        this.timer = true;
        let that = this;
        setTimeout(function() {

          that.timer = false;
        }, 400);
      }
    }
  }
};
</script>

<style>
.is-position-fixed {
    position: -webkit-sticky!important;
    position: sticky!important;
    top: 60px;
}
a {
  color: #3399ea;
}
#side .item {
  margin-bottom: 30px;
}
#artcle-info {
  padding: 20px;
  background-size: cover;
  background-repeat: no-repeat;
  margin-bottom: 40px;
}
.article-views {
  padding-right: 10px;
}
#artcle-info .abstract {
  color: #ffffff;
  border-left: 3px solid #f56c6c;
  padding: 10px;
  background-color: rgba(126, 129, 135, 0.3);
}

#artcle-info .timeAndView {
  padding: 20px;
  line-height: 30px;
  font-size: 16px;
  color: #ffffff;
}
#articlebtn {
  text-align: center;
  margin-bottom: 40px;
}
pre.has {
  color: #ffffff;
  background-color: rgba(0, 0, 0, 0.8);
}
img.has {
  width: 100%;
}
.icon {
  cursor: pointer;
  display: inline-block;
  vertical-align: top;
  font-size: 18px;
  width: 40px;
  height: 40.4px;
  line-height: 42px;
  border-radius: 42px;
  color: rgba(0, 0, 0, 0.5);
  background: rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
  font-style: normal;
  margin: 0 3.2px;
}
#statement {
  border-left: 3px solid #f56c6c;
  padding: 20px;
  background-color: #ebeef5;
}
@media screen and (min-width: 320px) and (max-width: 750px) {
  .art-item {
    margin-top: 40px;
    position: relative;
    width: 312px;
  }
}
</style>