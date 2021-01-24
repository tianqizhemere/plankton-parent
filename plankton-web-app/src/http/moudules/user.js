import axios from '../axios'

/*
 * 用户管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/system/user/save',
        method: 'post',
        data
    })
}
export const findByName = (params) => {
    return axios({
        url: '/system/user/findByName',
        method: 'get',
        params
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/system/user/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (params) => {
    console.log(params)
    return axios({
        url: '/system/user/list',
        method: 'get',
        params
    })
}
// 查找用户的菜单权限标识集合
export const findPermissions = (params) => {
    return axios({
        url: '/system/user/findPermissions',
        method: 'get',
        params
    })
}
