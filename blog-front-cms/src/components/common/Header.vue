<template>
    <div class="header">

        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="logo">Blog system</div>
        <div class="header-right">
            <div class="header-user-con">

                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`cancel full screen`:`full screen`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>


                <div class="user-avator">
                    <img :src="user.avatar" />
                </div>

                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{user.username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <a target="_blank" @click="openDialog">
                            <el-dropdown-item>change password</el-dropdown-item>
                        </a>
                        <el-dropdown-item divided command="loginout">quit</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <el-dialog title="change password" :visible.sync="dialogFormVisible" width="40%">
            <el-form :model="form" :rules="rules" ref="form">
                <el-form-item label="user name" :label-width="formLabelWidth">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="old password" :label-width="formLabelWidth">
                    <el-input v-model="form.oldpassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label="new password" :label-width="formLabelWidth" prop="newpassword">
                    <el-input v-model="form.newpassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label="confirm password" :label-width="formLabelWidth" prop="checkpassword">
                    <el-input v-model="form.checkpassword" type="password"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">cancel</el-button>
                <el-button type="primary" @click="toChangePwd('form')">confirm</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import bus from '../common/bus';
export default {
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('please enter password'));
            } else {
                if (this.form.checkpassword !== '') {
                    this.$refs.form.validateField('checkpassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('try enter again'));
            } else if (value !== this.form.newpassword) {
                callback(new Error('two password need to be same!'));
            } else {
                callback();
            }
        };
        return {
            dialogFormVisible: false,
            collapse: false,
            fullscreen: false,
            user: {
                userid: '',
                username: '',
                avatar: ''
            },
            form: {
                username: '',
                oldpassword: '',
                newpassword: '',
                checkpassword: ''
            },
            rules: {
                newpassword: [
                    { required: true, message: 'please reenter password again', trigger: 'blur' },
                    { min: 6, max: 16, message: '6-16 number', trigger: 'blur' },
                    { validator: validatePass, trigger: 'blur' }
                ],
                checkpassword: [
                    { required: true, message: 're-enter password', trigger: 'blur' },
                    { min: 6, max: 16, message: '6-16 number', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur', required: true }
                ]
            },
            formLabelWidth: '100px'
        };
    },
    computed: {},
    methods: {

        handleCommand(command) {
            if (command == 'loginout') {
                const _this = this;
                this.$axios
                    .get('/user/logout', {
                        headers: {
                            Authorization: localStorage.getItem('token')
                        }
                    })
                    .then(res => {
                        _this.$message.success('quit successful');
                        _this.$store.commit('REMOVE_INFO');
                        _this.$router.push('/login');
                    });
            }
        },

        collapseChage() {
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },
        // full screen
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        },
        openDialog() {
            //this.$router.push('/login');
            this.dialogFormVisible = true;
        },
        //change password
        toChangePwd(formName) {
            const _this = this;
            const username = _this.form.username;
            const oldpassword = _this.form.oldpassword;
            const newpassword = _this.form.newpassword;
            const params = {
                userName: username,
                oldPassword: oldpassword,
                newPassword: newpassword
            };
            console.log(params)
            // console.log(username);
            this.$refs[formName].validate(valid => {
                if (valid) {
                    _this.$axios.post('/user/changePassword',params, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                    }
                    ).then(res => {
                        this.$message({
                            message: res.data.msg,
                            type: 'success'
                        });
                        _this.$message.success('quit successful');
                        _this.$store.commit('REMOVE_INFO');
                        _this.$router.push('/login');
                    }).catch(err =>{
                             this.$message.error("do not try again");
                        });
                } else {
                    // console.log('error submit!!');
                    return false;
                }
            });
            this.dialogFormVisible = false;
        }
    },
    mounted() {
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    },
    created() {
        if (this.$store.getters.getUser.username) {
            this.user.userid = this.$store.getters.getUser.userid;
            this.user.username = this.$store.getters.getUser.username;
            this.user.avatar = this.$store.getters.getUser.avatar;
            // console.log(this.$store.getters.getUser.username);
        }
    }
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>
