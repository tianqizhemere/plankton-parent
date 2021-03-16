import axios from '../axios'

/*
 * redis客户端信息
 */

// redis客户端信息
export const info = (params) => {
    return axios({
        url: '/system/redis/info',
        method: 'get',
        params
    })
}

// 获取 redis key 数量
export const keysSize = (params) => {
    return axios({
        url: '/system/redis/keysSize',
        method: 'get',
        params
    })
}

// 获取 redis 内存信息
export const memoryInfo = (params) => {
    return axios({
        url: '/system/redis/memoryInfo',
        method: 'get',
        params
    })
}
