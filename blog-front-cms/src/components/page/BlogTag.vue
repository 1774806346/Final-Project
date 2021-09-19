<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> Tags directory
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
                >add tags</el-button>
                <el-input v-model="query.tag_name" placeholder="Tag name" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">search</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">Reset</el-button>
            </div>
            <el-table :data="tagList" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="tag_id" label="ID" width="55" align="center"></el-table-column>
                        <el-table-column prop="tag_name" label="Tag name"></el-table-column>
                <el-table-column label="operation" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >editor</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >Delete</el-button>
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


        <el-dialog title="Edit" :visible.sync="editVisible" width="30%">
            <el-form ref="tagform" :model="tagform" label-width="70px">
                <el-form-item label="Tag name">
                    <el-input v-model="tagform.tag_name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">cancel</el-button>
                <el-button type="primary" @click="saveEdit">confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'blogtag',
    inject: ['reload'],
    data() {
        return {
            query: {
                tag_name: ''
            },
            editVisible: false,
            tagform: {
                tag_id: null,
                tag_name: ''
            },
            tagList: [],
            currentPage: 1,
            total: 0,
            fileList: '',
            isAdd: true
        };
    },
    mounted() {
        this.getData(1);
    },
    methods: {
        getData(currentPage) {
            const _this = this;
            this.$axios
                .get('/admin/tagList', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.tagList = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },

        handleSearch() {
            const _this = this;
            _this.tagList = [];
            this.$axios.get('/getTagByName/' + _this.query.tag_name).then(res => {
                if (res.data.code == 200) {
                    _this.tagList.push(res.data.data);
                    this.$message.success(res.data.msg);
                    //console.log(res.data.data)
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        // delete
        handleDelete(index, row) {

            this.$confirm('Are you sure you want to delete？', 'prompt', {
                type: 'warning'
            })
                .then(() => {
                    this.$axios
                        .delete('/admin/deleteTag', {
                            params: {
                                tag_id: row.tag_id
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

        handleEdit(index, row) {
            this.tagform = row;
            this.isAdd = false;
            this.editVisible = true;
        },

        saveEdit() {
            const _this = this;

            if (!_this.isAdd) {
                this.$axios
                    .put('/admin/updateTag', _this.tagform, {
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
            } else {

                this.$axios
                    .post('/admin/saveTag', _this.tagform, {
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
            _this.editVisible = false;
        },

        handleAdd() {
            this.tagform = {
                tag_name: ''
            };
            this.isAdd = true;
            this.editVisible = true;
        },
        refreshData() {
            this.getData(1);
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
