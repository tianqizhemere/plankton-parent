import axios from '../axios'

/*
 * 字典管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/system/dictionaries/save',
        method: 'post',
        data
    })
}
// 修改
export const edit = (data) => {
    return axios({
        url: '/system/dictionaries/update',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/system/dictionaries/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/system/dictionaries/list',
        method: 'get',
        params
    })
}
// 树形查询
export const findDeptTree = () => {
    return axios({
        url: '/system/dictionaries/findTree',
        method: 'get'
    })
}
