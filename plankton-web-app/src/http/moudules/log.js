import axios from '../axios'

/*
 * 日志管理模块
 */

// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/system/operationLog/list',
        method: 'get',
        params
    })
}
