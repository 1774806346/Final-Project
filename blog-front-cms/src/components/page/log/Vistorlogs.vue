<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> Access log
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">reset</el-button>
            </div>
            <el-table :data="logList" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="ip" label="IP"></el-table-column>
                <el-table-column prop="ipSource" label="IP" show-overflow-tooltip></el-table-column>
                <el-table-column prop="operatetype" label="Operation type"></el-table-column>
                <el-table-column prop="description" label="Methods described"></el-table-column>
                <el-table-column prop="os" label="The operating system"></el-table-column>
                <el-table-column prop="browser" label="The browser" show-overflow-tooltip></el-table-column>
                <el-table-column label="操作耗时" width="100">
                    <template v-slot="scope">
                        <el-tag size="small">{{ scope.row.spendTime }}ms</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="url" label="Request link" show-overflow-tooltip></el-table-column>
                <el-table-column label="Operating results" width="80">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.operateresult=='正常'" size="small" effect="dark">successful</el-tag>
                        <el-tag v-else size="small" effect="dark" type="danger">failure</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="operatedate" label="Operating time"></el-table-column>
                <el-table-column label="operation" width="180" align="center">
                    <template slot-scope="scope">
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
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="query.currentPage"
                    :page-sizes="[10, 20, 30, 50]"
                    :page-size="query.pageSize"
                    :page-count="total"
                    layout="total, sizes, prev, pager, next, jumper"
                    background
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'vistorlogs',
    inject: ['reload'],
    data() {
        return {
            query: {
                classification: 0,
                currentPage: 1,
                pageSize: 10
            },
            logList: [],
            total: 0
        };
    },
    mounted() {
        this.getData();
    },
    methods: {
        getData() {
            const _this = this;
            this.$axios
                .post('/admin/logList/query', _this.query, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.logList = res.data.data;
                    _this.query.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                })
                .catch(err => {
                    this.$message.error('Don\'t try again. No access');
                });
        },

        handleDelete(index, row) {

            this.$confirm('Are you sure you want to delete it?', 'prompt', {
                type: 'warning'
            })
                .then(() => {
                    this.$axios
                        .delete('/admin/deleteLog', {
                            params: {
                                id: row.id
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
                            this.$message.error('Don\'t try again. No access');
                        });
                })
                .catch(() => {});
        },
        refreshData() {
            this.getData();
        },
        handleSizeChange(newSize) {
            this.query.pageSize = newSize;
            this.getData();
        },
        handleCurrentChange(newPage) {
            this.query.currentPage = newPage;
            this.getData();
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
/* Upload image */
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
