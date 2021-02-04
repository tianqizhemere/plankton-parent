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

// 数据操作类型
export const getOperationType = () => {
    return axios({
        url: '/system/operationType/list',
        method: 'get'
    })
}