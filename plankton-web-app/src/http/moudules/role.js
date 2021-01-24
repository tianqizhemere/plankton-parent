import axios from '../axios'

/*
 * 角色管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/system/role/save',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/system/role/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/system/role/findPage',
        method: 'get',
        params
    })
}
// 查询全部
export const findAll = () => {
    return axios({
        url: '/system/role/findAll',
        method: 'get'
    })
}
// 查询角色菜单集合
export const findRoleMenus = (params) => {
    return axios({
        url: '/system/role/findRoleMenus',
        method: 'get',
        params
    })
}
// 保存角色菜单集合
export const saveRoleMenus = (data) => {
    return axios({
        url: '/system/role/saveRoleMenus',
        method: 'post',
        data
    })
}
