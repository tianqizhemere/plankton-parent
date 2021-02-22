import axios from '../axios'

/*
 * 外置应用模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/notice/notice/save',
        method: 'post',
        data
    })
}
// 修改
export const edit = (data) => {
    return axios({
        url: '/notice/notice/update',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/notice/notice/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/notice/notice/page',
        method: 'get',
        params
    })
}
