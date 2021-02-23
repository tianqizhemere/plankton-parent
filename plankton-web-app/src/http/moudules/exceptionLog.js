import axios from '../axios'

/*
 * 异常日志管理模块
 */

// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/system/exceptionLog/page',
        method: 'get',
        params
    })
}
