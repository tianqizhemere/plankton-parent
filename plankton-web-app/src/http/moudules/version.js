import axios from '../axios'

/*
 * 版本管理模块
 */
// 保存
export const save = (data) => {
    return axios({
        url: '/system/version/save',
        method: 'post',
        data
    })
}
// 修改
export const edit = (data) => {
    return axios({
        url: '/system/version/update',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/system/version/delete',
        method: 'post',
        data
    })
}
// 上传文件
export const upload = (data) => {
    return axios({
        url: '/system/attach/uploadFile',
        method: 'post',
        data
    })
}
// 删除文件
export const deleteFile = (data) => {
    return axios({
        url: '/system/attach/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (params) => {
    return axios({
        url: '/system/version/list',
        method: 'get',
        params
    })
}
