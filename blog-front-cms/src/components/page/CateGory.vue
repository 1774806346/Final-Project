<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> catalog
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
                >Add the classification</el-button>
                <el-input
                    v-model="query.category_name"
                    placeholder="Category name"
                    class="handle-input mr10"
                ></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">Search</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">Reset</el-button>
            </div>
            <el-table
                :data="categoryList"
                border
                class="table"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="category_id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="category_name" label="Category name"></el-table-column>
                <el-table-column label="operation" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >Edit</el-button>
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
            <el-form ref="categoryform" :model="categoryform" label-width="70px">
                <el-form-item label="Category name">
                    <el-input v-model="categoryform.category_name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">Cancel</el-button>
                <el-button type="primary" @click="saveEdit">confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'category',
    inject: ['reload'],
    data() {
        return {
            query: {
                category_name: ''
            },
            editVisible: false,
            categoryform: {
                category_id: null,
                category_name: ''
            },
            categoryList: [],
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
                .get('/admin/categoryList', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.categoryList = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // Trigger search button
        handleSearch() {
            const _this = this;
            _this.categoryList = [];
            this.$axios.get('/getCateGoryByName/' + _this.query.category_name).then(res => {
                if (res.data.code == 200) {
                    _this.categoryList.push(res.data.data);
                    this.$message.success(res.data.msg);
                    // console.log(res.data.data)
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        // delete
        handleDelete(index, row) {
            // Second confirmation delete
            this.$confirm('Are you sure you want to delete？', 'prompt', {
                type: 'warning'
            })
                .then(() => {
                    this.$axios
                        .delete('/admin/deleteCategory', {
                            params: {
                                category_id: row.category_id
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
        // edit
        handleEdit(index, row) {
            this.categoryform = row;
            this.isAdd = false;
            this.editVisible = true;
        },
        // save edit
        saveEdit() {
            const _this = this;
            //Modify the classification
            if (!_this.isAdd) {
                this.$axios
                    .put('/admin/updateCategory', _this.categoryform, {
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
                //Add the classification
                this.$axios
                    .post('/admin/saveCategory', _this.categoryform, {
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
        //Add operation
        handleAdd() {
            this.categoryform = {
                category_name: ''
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
