import axios from '../axios'

/*
 * 菜单管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/system/menu/save',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/system/menu/delete',
        method: 'post',
        data
    })
}
// 查找导航菜单树
export const findNavTree = (params) => {
    return axios({
        url: '/system/menu/getNavTree',
        method: 'get',
        params
    })
}
