<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-text"></i> The article lists
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    class="handle-plus mr10"
                    @click="handleAdd"
                >Add the article</el-button>
                <el-input v-model="query.title" placeholder="The article title" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">search</el-button>

                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">reset</el-button>
            </div>
            <el-table
                :data="blogArticles"
                border
                class="table"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="blog_id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="blog_title" label="The article title" width="200"></el-table-column>
                <el-table-column label="The article illustrated" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.blog_cover_image"
                            :preview-src-list="[scope.row.blog_cover_image]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column property="is_top" align="center" label="Is placed at the top">
                    <template slot-scope="scope">
                        <el-switch
                            :active-value="1"
                            :inactive-value="0"
                            v-model="scope.row.is_top"
                            @change="changeStatus(scope.$index,scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="state" align="center">
                    <template slot-scope="scope">
                        <el-tag
                            :type="scope.row.blog_status===0?'success':(scope.row.blog_status===1?'danger':'')"
                        >{{scope.row.blog_status==0?'Published successfully ':' awaits review'}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="create_time" label="Release time" width="160"></el-table-column>
                <el-table-column prop="update_time" align="center" label="Update time" width="160"></el-table-column>
                <el-table-column label="operation" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="gotoEdit(scope.$index,scope.row.blog_id)"
                        >The editor</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >delete</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    @current-change="getData"
                    :current-page="currentPage"
                    :page-count="total"
                    layout="prev, pager, next"
                    background
                    hide-on-single-page
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'articles',
    inject: ['reload'],
    data() {
        return {
            query: {
                title: ''
            },
            currentPage: 1,
            blogArticles: [],
            total: 0
        };
    },
    mounted() {
        this.getData(1);
    },
    methods: {
        getData(currentPage) {
            const _this = this;
            _this.getBlogData(currentPage);
        },
        getBlogData(currentPage) {
            const _this = this;
            this.$axios
                .get('/admin/blogList', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.blogArticles = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // Trigger search button
        handleSearch() {
            const _this = this;
            const title = _this.query.title;
            this.$axios
                .get('/searchBlog/' + title, {
                    params: {
                        currentPage: _this.currentPage
                    }
                })
                .then(res => {
                    _this.blogArticles = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // delete
        handleDelete(index, row) {

            this.$confirm('Are you sure you want to delete?', 'prompt', {
                type: 'warning'
            })
                .then(() => {
                    //console.log(row.blog_id);
                    this.$axios
                        .delete('/admin/deleteBlog', {
                            params: {
                                blog_id: row.blog_id
                            },
                            headers: {
                                Authorization: localStorage.getItem('token')
                            }
                        })
                        .then(res => {
                            if (res.data.code == 200) {
                                this.$message.success(res.data.msg);
                                this.reload();
                            } else {
                                this.$message.error(res.data.msg);
                            }
                        })
                        .catch(err => {
                            this.$message.error('Don\'t try again. There\'s no access');
                        });
                })
                .catch(() => {});
        },
        gotoEdit(index, blog_id) {
            this.$router.push({
                path: '/BlogEdit',
                query: {
                    blog_id: blog_id
                }
            });
        },
        //make top
        changeSwitch(data) {
           // console.log(data);
        },

        handleAdd() {
            this.$router.push('/BlogEdit');
        },
        refreshData() {
            this.getData(1);
        },
        changeStatus(index, row) {
            const _this = this;
            const blog_id = row.blog_id;
            const is_top = row.is_top;
            const params = {
                blog_id,
                is_top
            };
            this.$axios
                .put('/admin/blog/isTop', params, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        this.$message.success(res.data.msg);
                        this.reload();
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('Don\'t try again. There\'s no access');
                });
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
