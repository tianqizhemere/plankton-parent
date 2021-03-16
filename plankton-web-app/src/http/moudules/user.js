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

// 修改
export const edit = (data) => {
    return axios({
        url: '/system/user/update',
        method: 'post',
        data
    })
}

// 根据用户名查询用户信息
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

// 获取在线用户
export const online = (params) => {
    return axios({
        url: '/system/user/online',
        method: 'get',
        params
    })
}

// 踢出用户
export const kickOut = (data) => {
    return axios({
        url: '/system/user/kickOut',
        method: 'post',
        data
    })
}
