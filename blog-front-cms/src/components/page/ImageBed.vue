<template>
    <div class="main-container">
        <div class="main-content">
            <el-card class>
                <el-upload
                    class="upload"
                    drag
                    ref="upload"
                    action="string"
                    accept="image/jpeg, image/png, image/jpg"
                    :before-upload="onBeforeUploadImage"
                    :http-request="UploadImage"
                    :on-change="fileChange"
                    :file-list="fileList"
                    multiple
                >
                    <i class="el-icon-upload" style="color:#1e90ff;"></i>
                    <div class="el-upload__text">
                        Drag the file here, or
                        <em>Click on the upload</em>
                    </div>
                    <div
                        class="el-upload__tip"
                        style=" text-align: center;"
                        slot="tip"
                    >Only JPG/PNG files can be uploaded and no more than 5MB can be uploaded</div>
                </el-upload>
                <el-progress :stroke-width="16" :percentage="progressPercent"></el-progress>
            </el-card>

            <div v-infinite-scroll="load" infinite-scroll-disabled="disabled">
                <Waterfall
                    ref="waterfall"
                    :list="list"
                    :gutter="10"
                    :width="240"
                    :breakpoints="{
            1200: { //When screen width is less than or equal to 1200
              rowPerView: 4,
            },
            800: { //当The screen width must be less than or equal to 800
              rowPerView: 3,
            },
            500: { //When screen width is less than or equal to 500
              rowPerView: 2,
            }
          }"
                    backgroundColor="#E4E7ED"
                >
                    <template slot="item" slot-scope="props">
                        <div class="card">
                            <div
                                class="cover"
                                :style="initCardStyle(props)"
                                @click="handleClick(props.data)"
                            >
                                <img
                                    :src="props.data.download_url"
                                    alt
                                    @load="$refs.waterfall.refresh"
                                />
                            </div>

                            <div class="name">
                                <p>{{props.data.name }}</p>
                            </div>
                            <div class="menus">
                                <p
                                    data-title="copy"
                                    v-clipboard:copy="props.data.download_url"
                                    v-clipboard:success="onCopy"
                                    v-clipboard:error="onError"
                                />
                                <p data-title="delete" @click="handleDelete(props.data)" />
                            </div>
                        </div>
                    </template>
                </Waterfall>
            </div>
        </div>
    </div>
</template>

<script>
import Waterfall from 'vue-waterfall-plugin';

export default {
    name: 'ImageBed',
    components: {
        Waterfall
    },
    data() {
        return {
            list: [],
            loading: false,
            fileList: [],
            progressPercent: 0
        };
    },
    computed: {
        noMore() {
            if(this.list.length){
return this.list.length >= 200;
            }
            return false
        },
        disabled() {
            return this.loading || this.noMore;
        }
    },
    mounted() {
    },
    methods: {
        async load() {
            this.loading = true;
            await this.addNewList();
            this.loading = false;
        },
        addNewList() {
            const _this = this;
            this.$axios
                .get('/admin/allImageByGiteeFromGitee', {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.list = res.data.data;
                });
        },

        initCardStyle(props) {
            if (this.isSetInitStyle) {
                return {
                    width: `${props.data.itemWidth - 20}px`,
                    height: `${((props.data.itemWidth - 20) / props.data.width) * props.data.height}px`,
                    backgroundColor: props.data.blankColor
                };
            }
        },
        /**
         * Images click
         */
        handleClick(item) {
            this.$message.info("Figure bed");
        },
        /**
         * delete
         */
        handleDelete(item) {
            this.delImage(item.sha);

        },
        onCopy(e) {
            this.$message.success('The content has been copied to the clipboard！');
        },

        onError(e) {
            this.$message.error('Sorry, replication failed！');
        },
        delImage(name) {
            const _this = this;

            this.$confirm('Are you sure you want to delete?', 'prompt', {
                type: 'warning'
            }).then(() => {
            this.$axios
                .delete('/admin/delImgFromGitee', {
                    params: {
                        imgPath: name
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code === 200) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error('Delete failed');
                    }
                    _this.load()
                });
                }).catch(() => {});
        },
        onBeforeUploadImage(file) {
            const isIMAGE = file.type === 'image/jpeg' || 'image/jpg' || 'image/png';
            const isLt1M = file.size / 1024 / 1024 < 5;
            if (!isIMAGE) {
                this.$message.error('Upload files in image format only!');
            }
            if (!isLt1M) {
                this.$message.error('The uploaded file size cannot exceed 5MB!');
            }
            return isIMAGE && isLt1M;
        },
        UploadImage(param) {
            const formData = new FormData();
            const _this=this;
            formData.append('file', param.file);
            _this.$axios
                .post(
                    '/admin/uploadImgtoGitee',
                    formData,
                    {
                        headers: {
                            Authorization: localStorage.getItem('token')
                        },
                         'Content-Type': 'multipart/form-data',
                        onUploadProgress: progressEvent => {
                            if(progressEvent.lengthComputable){
                                  _this.progressPercent = Number((progressEvent.loaded / progressEvent.total * 100).toFixed(2))
                            }
                          
                        }
                    },
                
                )
                .then(res => {
                    console.log(res)
                    this.$message.success(res.data.msg);
                    _this.load()

                })
                .catch(err => {
                    this.$message.error('upload failed');
                });
        },
        fileChange(file) {
            this.$refs.upload.clearFiles();
            this.logo = file.raw;
            this.fileList = [{ name: file.name, url: file.url }];
        }
    }
};
</script>

<style scoped>
.main-container {
    display: flex;
}
.main-container .main-content {
    flex: 1;
    height: 100vh;
    overflow: auto;
    z-index: 0;
}
.main-container .main-content .upload {
    border-radius: 5px;
    border: 1px solid #ebeef5;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    text-align: center;
    padding: 50px;
    margin-bottom: 50px;
    z-index: -1;
    background: url(../../assets/img/comment-1.gif) right bottom no-repeat;
   
}
.main-container .slide-menu {
    flex: 0 0 200px;
    width: 200px;
}
.main-container .slide-menu .operations {
    position: fixed;
    right: 0;
    width: 200px;
    top: 0;
    bottom: 0;
    box-sizing: border-box;
    padding: 20px;
}
.main-container .slide-menu .operations .item {
    margin-bottom: 20px;
}
.main-container .slide-menu .operations .item .title {
    padding-bottom: 10px;
    text-align: left;
    color: #fff;
}
.card {
    background: #fff;
    border-radius: 5px;
    overflow: hidden;
    cursor: pointer;
    position: relative;
    transition: 0.2s;
    top: 0;
}
.card:hover {
    top: -3px;
}
.card .cover {
    margin: 10px 10px 0 10px;
}
.card .cover img {
    display: block;
    width: 100%;
}
.card .name {
    background: #fff;
    color: #666;
    font-weight: 600;
    padding: 10px 20px;
    font-size: 14px;
}
.card .menus {
    padding: 10px;
    border-top: 1px solid #e7e7e7;
    text-align: right;
}
.card .menus p {
    position: relative;
    display: inline-block;
    padding: 4px 10px;
    text-decoration: none;
    text-align: center;
    cursor: pointer;
    user-select: none;
    color: white;
    font-size: 12px;
    margin-left: 10px;
}
.card .menus p::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: linear-gradient(135deg, #6e8efb, #a777e3);
    border-radius: 4px;
    transition: box-shadow 0.5s ease, transform 0.2s ease;
    will-change: transform;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0)) rotateY(var(--ry, 0)) translateZ(var(--tz, -12px));
}
.card .menus p:hover::before {
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.card .menus p::after {
    position: relative;
    display: inline-block;
    content: attr(data-title);
    transition: transform 0.2s ease;
    font-weight: bold;
    letter-spacing: 0.01em;
    will-change: transform;
    transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0)) rotateY(var(--ry, 0));
}
</style>