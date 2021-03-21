import axios from '../axios'

/*
 * 登录日志
 */

// 获取登录访问次数
export const visitCount = (params) => {
    return axios({
        url: '/system/loginLog/visitCount',
        method: 'get',
        params
    })
}
