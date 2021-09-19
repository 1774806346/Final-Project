import axios from 'axios'
import Element from "element-ui";
axios.defaults.baseURL = 'http://localhost:8089'
import {Message} from "element-ui"
let CancelToken = axios.CancelToken
axios.interceptors.request.use(config => {

    const userJson = window.localStorage.getItem('userInfo') || '{}'
    const user = JSON.parse(userJson)
    if (userJson !== '{}' && user.roles !== 'admin' && config.method !== 'get') {
        config.cancelToken = new CancelToken(function executor(cancel) {
            cancel('Demo mode, no operation allowed')
        })
        return config
    }
    return config
})
axios.interceptors.response.use(response => {
    const res = response.data;
    console.log("The rear intercept")

    if (res.code === 200 || res.code === 404) {
        return response
    } else {

        Element.Message({
            message: response.data.msg,
            type: 'error',
            duration: 2 * 1000
        })

        return Promise.reject(response.data.msg)
    }
},
    error => {
      //  console.log('err==>' + error)// for debug
            // if (error.response.status === 401) {
            //     store.commit('REMOVE_INFO');
            //     router.push({
            //         path: '/login'
            //     });
            //     error.message = 're';
            // }
            // if (error.response.status === 403) {
            //     error.message = 'access';
            // }

              Message.error(error)
        return Promise.reject(error)
    })
export default axios